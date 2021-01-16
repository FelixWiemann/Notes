package com.nepumuk.notizen.core.views.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.nepumuk.notizen.core.R;


/**
 * fragment for user interaction whether he/she wants the unsaved data to be dismissed or saved
 *
 * it will dismiss itself, once a user interaction has taken place
 * it will also notify the SaveDataFragmentListener added in setListener of the users choice.
 */
public class SaveDataFragment extends DialogFragment {

    SaveDataFragmentListener listener;

    public void setListener(SaveDataFragmentListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setMessage(R.string.unchanged_data)
                .setNegativeButton(R.string.discard_exit,
                        ((dialog, which) -> listener.discardAndExit()))
                .setPositiveButton(R.string.save_and_exit,
                        ((dialog, which) -> listener.saveAndExit()))
                .setNeutralButton(R.string.action_cancel,
                        ((dialog, which) -> listener.cancelExit()))
                .create();
    }

}
