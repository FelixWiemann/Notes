package com.example.felix.notizen.cust_views;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.os.Build;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A basic Camera preview class
 * TODO use camera2
 */
@SuppressWarnings({"Depreciated", "deprecation"})
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {
    private static final String TAG = "CAMERA_PREVIEW";
    private SurfaceHolder mHolder;
    private Camera mCamera;
    //private int[][] pixels;
    //private Camera.Size previewSize;
    private Context currentContext;
    private static final String LOG_TAG = "CameraPreview";

    /**
     * turns on the flashlight of the camera, if param == true and turns it off, if param == false
     *
     * @param on turn light on
     */
    @SuppressWarnings("unused")
    public void useFlash(boolean on) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        Camera.Parameters p = mCamera.getParameters();
        if (on) {
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        } else {
            p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        }
        mCamera.setParameters(p);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unused")
    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        //inflate(getContext(), R.layout.cv_layout_camera_preview, this);
        currentContext = context;
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        currentContext = context;

    }

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        currentContext = context;
    }

    public CameraPreview(Context context) {
        super(context);
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        currentContext = context;
    }

    /**
     * init the camera
     *
     * @param camera to use for preview
     */
    private void init(Camera camera) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        // set local camera
        mCamera = camera;
        // set camera rotation
        setImageRotation();
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        // TODO check for orientation of different devices
        mCamera.setDisplayOrientation(90);
        //set camera to continually auto-focus
        Camera.Parameters params = mCamera.getParameters();
        //*EDIT*//params.setFocusMode("continuous-picture");
        //It is better to use defined constraints as opposed to String, thanks to AbdelHady
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        mCamera.setParameters(params);
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
            // The Surface has been created, now tell the camera where to draw the preview.
            mCamera.setPreviewDisplay(holder);
            mCamera.setPreviewCallback(this);
            // and start preview
            mCamera.startPreview();
        } catch (IOException e) {
            Log.e(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        // empty. Take care of releasing the Camera preview in your activity.
        mCamera.release();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
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
        //Canvas canvas = null;


        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.setPreviewCallback(this);
            //previewSize = mCamera.getParameters().getPreviewSize();
            mCamera.startPreview();

        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    /**
     * set the camera of the preview
     *
     * @param newCamera new camera
     */
    public void setCamera(Camera newCamera) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        init(newCamera);
    }

    /**
     * used for pixel manipulation, currently not used
     */
    @SuppressWarnings("unused")
    void decodeYUV420SP(byte[] yuv420sp, int width, int height) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
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
        //pixels = rgb;
    }

    /**
     * closes the camera
     */
    @SuppressWarnings("unused")
    public void closeCamera() {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        mCamera.release();
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        //transforms NV21 pixel data into RGB pixels
        //mCamera.autoFocus();
        //decodeYUV420SP(data, previewSize.width, previewSize.height);
    }

    private boolean isTakingPicture = false;

    /**
     * take picture
     */
    public void takePicture() {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
            @Override
            public void onShutter() {
                Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());

            }
        };
        Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());

            }
        };
        Camera.PictureCallback pictureCallbackJpeg = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
                String s = savePicture(data);
                //TODO save image link in DB
                Log.i(LOG_TAG, "image saved at: " + s);
                isTakingPicture = false;
                // TODO remove preview, add restart on view
                mCamera.startPreview();
                Log.i(LOG_TAG, "preview restarted");

            }
        };
        // if already taking picture, then can't take a second picture -> camera already in use
        if (!isTakingPicture) {
            mCamera.setPreviewCallback(null);
            mCamera.takePicture(shutterCallback, pictureCallback, pictureCallbackJpeg);
            Log.i(LOG_TAG, "pic");
            isTakingPicture = true;
        }


    }

    private static final String FilePrefix = "NoteImage_";
    private static final String FileType = ".jpeg";

    @SuppressLint("SimpleDateFormat")
    public String savePicture(byte[] dataToSave) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        // Warning suppressed, user can, but is not supposed to se the names
        // Date only used for single identifier
        String fileName = FilePrefix + timeStamp + FileType;
        return savePicture(dataToSave, fileName);


    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        takePicture();
        mCamera.startPreview();
        return true;
    }

    public String savePicture(byte[] dataToSave, String filename) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        File file;
        if (isExternalStorageWritable()) {
            file = new File(currentContext.getExternalFilesDir("images"), filename);
        } else {
            file = new File(currentContext.getFilesDir(), filename);
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(dataToSave);
            outputStream.close();
            Log.i(LOG_TAG, "Image saved");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    @SuppressWarnings("unused")
    public boolean isExternalStorageReadable() {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public void setImageRotation() {
        //STEP #1: Get rotation degrees
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, info);
        int rotation = ((Activity) currentContext).getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break; //Natural orientation
            case Surface.ROTATION_90:
                degrees = 90;
                break; //Landscape left
            case Surface.ROTATION_180:
                degrees = 180;
                break;//Upside down
            case Surface.ROTATION_270:
                degrees = 270;
                break;//Landscape right
        }
        int rotate = (info.orientation - degrees + 360) % 360;

        //STEP #2: Set the 'rotation' parameter
        Camera.Parameters params = mCamera.getParameters();
        params.setRotation(rotate);
        mCamera.setParameters(params);
    }

    public void onPause() {
        mCamera.release();
    }

    public void onResume() {
        try {
            mCamera.reconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onButtonClick(View view) {
    }
}
