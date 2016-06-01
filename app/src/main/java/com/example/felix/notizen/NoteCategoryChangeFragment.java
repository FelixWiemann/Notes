package com.example.felix.notizen;

import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NoteCategoryChangeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class NoteCategoryChangeFragment extends DialogFragment {

    private static final String LOG_TAG = "NoteCatChangeFrag";
    private OnFragmentInteractionListener mListener;
    private View thisView;

    public NoteCategoryChangeFragment() {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        // Required empty public constructor
    }
    public void setContext(OnFragmentInteractionListener context){
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        if (context != null) {
            mListener = context;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        // Inflate the layout for this fragment
        getDialog().setTitle(R.string.TitleCreateNewCategory);
        thisView=inflater.inflate(R.layout.fragment_note_category_change, container, false);
        return  thisView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        view.findViewById(R.id.Fragment_note_cat_save).setOnClickListener(onClickListener);
        SeekBar s = (SeekBar) thisView.findViewById(R.id.sbRed);
        s.setOnSeekBarChangeListener(onSeekBarChangeListener);
        s = (SeekBar) thisView.findViewById(R.id.sbBlue);
        s.setOnSeekBarChangeListener(onSeekBarChangeListener);
        s = (SeekBar) thisView.findViewById(R.id.sbGreen);
        s.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
            saveCats();

        }
    };



    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener =  new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
            updateBackColor(seekBar);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        }
    };

    private int[] rgbColors = {0,0,0};

    private void updateBackColor(SeekBar seekBar){
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        TextView tv;
        String s;
        switch (seekBar.getId()) {
            case R.id.sbRed:
                tv = (TextView) thisView.findViewById(R.id.tvR);
                s = "r: ";
                rgbColors[0]=seekBar.getProgress();
                break;
            case R.id.sbGreen:
                tv = (TextView) thisView.findViewById(R.id.tvG);
                s = "g: ";
                rgbColors[1]=seekBar.getProgress();
                break;
            case R.id.sbBlue:
                tv = (TextView) thisView.findViewById(R.id.tvB);
                s = "b: ";
                rgbColors[2]=seekBar.getProgress();
                break;
            default:
                throw new IllegalArgumentException();
        }
        thisView.setBackgroundColor(Color.argb(255,rgbColors[0],rgbColors[1],rgbColors[2]));
        tv.setText(String.format("%s%d", s, seekBar.getProgress()));

    }

    public void saveCats() {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        Note_Category nc = new Note_Category(((TextView) thisView.findViewById(R.id.fragment_note_cat_create_name)).getText().toString(),
                ((TextView) thisView.findViewById(R.id.fragment_note_cat_create_description)).getText().toString(),
                Color.argb(125,rgbColors[0],rgbColors[1],rgbColors[2]),1);
        mListener.onFragmentNewCategoryResult(nc);
        this.dismiss();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentNewCategoryResult(Note_Category note_category);
    }
}
