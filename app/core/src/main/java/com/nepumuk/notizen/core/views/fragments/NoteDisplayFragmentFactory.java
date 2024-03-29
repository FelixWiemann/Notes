package com.nepumuk.notizen.core.views.fragments;

import android.util.Log;

import com.nepumuk.notizen.core.utils.db_access.DatabaseStorable;

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

    private static final String TAG = "NoteDisplayFrgmntFac";

    private static final NoteDisplayFragmentFactory factory = new NoteDisplayFragmentFactory();

    private final HashMap<Class<? extends DatabaseStorable>, Class <? extends NoteDisplayFragment>> storableToFragment;

    private NoteDisplayFragmentFactory() {
        super();
        storableToFragment = new HashMap<>();
    }

    public static void addMapping(Class<? extends DatabaseStorable> storableType, Class<? extends NoteDisplayFragment> fragmentType){
        factory.addStorableTypeToFragment(storableType, fragmentType);
    }

    private void addStorableTypeToFragment(Class<? extends DatabaseStorable> storableType, Class<? extends NoteDisplayFragment> fragmentType){
        storableToFragment.put(storableType, fragmentType);
    }

    private NoteDisplayFragment<DatabaseStorable> getFragmentForStorableType(Class<? extends DatabaseStorable> storableType) throws InstantiationException, IllegalAccessException {
        if (storableToFragment.containsKey(storableType)) {
            return storableToFragment.get(storableType).newInstance();
        }
        Class superClazz = storableType.getSuperclass();
        if (superClazz != Object.class){
            return getFragmentForStorableType(superClazz);
        }
        return null;
    }


    /**
     * generates a NoteDisplayFragment suitable for the given DatabaseStorable
     * <br/>
     * <br/>
     * will try the super class, if for the given DatabaseStorable a Fragment cannot be created
     *
     * @param noteType storable for which a Fragment needs to be created
     * @return generated Fragment
     */
    public static NoteDisplayFragment<DatabaseStorable> generateFragment(DatabaseStorable noteType){
        NoteDisplayFragment<DatabaseStorable> fragment = null;
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
