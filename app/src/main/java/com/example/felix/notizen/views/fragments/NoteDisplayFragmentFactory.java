package com.example.felix.notizen.views.fragments;

import android.util.Log;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.objects.Notes.cTaskNote;
import com.example.felix.notizen.objects.Notes.cTextNote;

import java.util.HashMap;

/**
 * Factory for creating NoteDisplays
 *
 * <br>
 * <br>
 * add new fragment types and their corresponding object class in the constructor of the factory with a call to {@link NoteDisplayFragmentFactory#addStorableTypeToFragment(Class, Class)}
 *
 */
public class NoteDisplayFragmentFactory {

    private static final String TAG = "NoteDisplayFragmentFactory";

    private static NoteDisplayFragmentFactory factory;

    private HashMap<Class<? extends DatabaseStorable>, Class <? extends NoteDisplayFragment>> storableToFragment;

    private NoteDisplayFragmentFactory(){
        storableToFragment = new HashMap<>();
        addStorableTypeToFragment(cTaskNote.class, TaskNoteFragment.class);
        addStorableTypeToFragment(cTextNote.class, TextNoteFragment.class);
    }

    private void addStorableTypeToFragment(Class<? extends DatabaseStorable> storableType, Class<? extends NoteDisplayFragment> fragmentType){
        storableToFragment.put(storableType, fragmentType);
    }

    private NoteDisplayFragment getFragmentForStorableType(Class<? extends DatabaseStorable> storableType) throws InstantiationException, IllegalAccessException {
        if (storableToFragment.containsKey(storableType)) {
            return storableToFragment.get(storableType).newInstance();
        }
        return null;
    }


    public static NoteDisplayFragment generateFragment(DatabaseStorable noteType){
        if (factory == null){
            factory = new NoteDisplayFragmentFactory();
        }
        NoteDisplayFragment fragment = null;
        try {
            fragment = factory.getFragmentForStorableType(noteType.getClass());
        } catch (InstantiationException | IllegalAccessException e) {
            Log.e(TAG, "during fragment creation: ", e);
        }
        if (fragment==null){
            fragment = new NotImplementedFragment();
            Log.e(TAG, "could not generate fragment for " + noteType.getClass().getCanonicalName());
        }
        return fragment;
    }

}
