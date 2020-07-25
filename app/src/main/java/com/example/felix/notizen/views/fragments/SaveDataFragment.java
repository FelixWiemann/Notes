package com.example.felix.notizen.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.felix.notizen.R;


/**
 * fragment for user interaction whether he/she wants the unsaved data to be dismissed or saved
 *
 * it will dismiss itself, once a user interaction has taken place
 * it will also notify the SaveDataFragmentListener added in setListener of the users choice.
 */
public class SaveDataFragment extends DialogFragment {

    Button saveExit;
    Button cancelExit;
    Button discardExit;


    SaveDataFragmentListener listener;

    public void setListener(SaveDataFragmentListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.save_data_fragment,container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        saveExit = view.findViewById(R.id.bt_save_exit);
        saveExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.saveAndExit();
                SaveDataFragment.this.dismiss();
            }
        });
        cancelExit = view.findViewById(R.id.bt_cancel_exit);
        cancelExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.cancelExit();
                SaveDataFragment.this.dismiss();
            }
        });
        discardExit = view.findViewById(R.id.bt_discard_exit);
        discardExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.discardAndExit();
                SaveDataFragment.this.dismiss();
            }
        });
    }
}
