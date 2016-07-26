package com.example.felix.notizen.CustViews;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Camera;
import android.os.Build;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A basic Camera preview class
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {
    private static final String TAG = "CAMERA_PREVIEW";
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private int[][] pixels;
    private Camera.Size previewSize;
    private Context currentContext;

    public void flashOn(boolean on) {
        Camera.Parameters p = mCamera.getParameters();


        if (on) {
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        } else {
            p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        }
        mCamera.setParameters(p);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        //inflate(getContext(), R.layout.cv_layout_camera_preview, this);
        currentContext = context;
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        currentContext = context;

    }

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        currentContext = context;
    }

    public CameraPreview(Context context) {
        super(context);
        currentContext = context;
    }

    public CameraPreview(Context context, Camera camera) {
        super(context);
        currentContext = context;
        mCamera = camera;
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceCreated(mHolder);


    }

    private void init(Camera camera) {
        mCamera = camera;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mCamera.setDisplayOrientation(90);
        //set camera to continually auto-focus
        Camera.Parameters params = mCamera.getParameters();
        //*EDIT*//params.setFocusMode("continuous-picture");
        //It is better to use defined constraints as opposed to String, thanks to AbdelHady
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        mCamera.setParameters(params);
        // TODO check for orientation of different devices
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.setPreviewCallback(this);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
        //mCamera.release();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.


        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        mCamera.getParameters().getPreviewSize();
        // reformatting changes here
        Canvas canvas = null;


        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.setPreviewCallback(this);
            previewSize = mCamera.getParameters().getPreviewSize();
            mCamera.startPreview();

        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    public void setCamera(Camera cameraInstance) {
        init(cameraInstance);
    }


    void decodeYUV420SP(byte[] yuv420sp, int width, int height) {

        final int frameSize = width * height;
        int[][] rgb = new int[frameSize][4];

        for (int j = 0, yp = 0; j < height; j++) {
            int uvp = frameSize + (j >> 1) * width, u = 0, v = 0;
            for (int i = 0; i < width; i++, yp++) {
                int y = (0xff & ((int) yuv420sp[yp])) - 16;
                if (y < 0)
                    y = 0;
                if ((i & 1) == 0) {
                    v = (0xff & yuv420sp[uvp++]) - 128;
                    u = (0xff & yuv420sp[uvp++]) - 128;
                }

                int y1192 = 1192 * y;
                int r = (y1192 + 1634 * v);
                int g = (y1192 - 833 * v - 400 * u);
                int b = (y1192 + 2066 * u);

                if (r < 0) r = 0;
                else if (r > 262143)
                    r = 262143;
                if (g < 0) g = 0;
                else if (g > 262143)
                    g = 262143;
                if (b < 0) b = 0;
                else if (b > 262143)
                    b = 262143;

                //rgb[yp] = 0xff000000 | ((r << 6) & 0xff0000) | ((g >> 2) & 0xff00) | ((b >> 10) & 0xff);
                rgb[yp][0] = 0xff;
                rgb[yp][1] = (r << 6) & 0xff;
                rgb[yp][2] = (g >> 2) & 0xff;
                rgb[yp][3] = (b >> 10) & 0xff;

            }
        }
        pixels = rgb;
    }

    public void closeCam() {
        mCamera.release();
    }


    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        //transforms NV21 pixel data into RGB pixels
        //mCamera.autoFocus();
        decodeYUV420SP(data, previewSize.width, previewSize.height);
    }

    public void takePicture() {
        Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
            @Override
            public void onShutter() {

            }
        };
        Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

            }
        };
        Camera.PictureCallback pCJpeg = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                String s = savePicture(data);

            }
        };

        mCamera.takePicture(shutterCallback, pictureCallback, pCJpeg);
        mCamera.startPreview();
    }

    private final String FilePrefix = "NoteImage_";
    private final String FileType = ".jpeg";

    public String savePicture(byte[] dataToSave) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = FilePrefix + timeStamp + FileType;
        return savePicture(dataToSave, fileName);


    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        takePicture();
        return true;
    }

    public String savePicture(byte[] dataToSave, String filename) {
        File file = null;
        if (isExternalStorageWritable()) {
            file = new File(currentContext.getExternalFilesDir("images"), filename);
        } else {
            file = new File(currentContext.getFilesDir(), filename);
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(dataToSave);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

}
