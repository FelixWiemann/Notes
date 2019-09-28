package com.example.felix.notizen.views.fragments;

import android.util.Log;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;

public class NoteDisplayFragmentFactory {

    private static final String TAG = "NoteDisplayFragmentFactory";

    public static NoteDisplayFragment generateFragment(DatabaseStorable noteType){
        NoteDisplayFragment fragment;
        switch (noteType.getClass().getCanonicalName()){
            case "com.example.felix.notizen.objects.Notes.cTextNote":
                fragment =  new TextNoteFragment();
                break;
            case "com.example.felix.notizen.objects.Task.cTask":
            case "com.example.felix.notizen.objects.Notes.cImageNote":
            default:
                fragment = new NotImplementedFragment();
                Log.e(TAG, "could not generate fragment for " + noteType.getClass().getCanonicalName());
        }
        return fragment;
    }

}
