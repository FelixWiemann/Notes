package com.example.felix.notizen.views;

import android.content.Context;
import android.util.Log;

import com.example.felix.notizen.objects.Notes.cImageNote;
import com.example.felix.notizen.objects.Notes.cTaskNote;
import com.example.felix.notizen.objects.Notes.cTextNote;
import com.example.felix.notizen.objects.Task.cTask;
import com.example.felix.notizen.objects.cStorageObject;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Felix on 11.11.2018.
 */

public class cNoteDisplayViewFactory {

    private static final String TAG = "Display View Factory";

    private static cNoteDisplayViewFactory instance;

    private HashMap<Class<?>, Class <? extends cNoteDisplayView<?>>> objectToDisplayView;

    private cNoteDisplayViewFactory() {
        super();
        objectToDisplayView = new HashMap<>();
        registerNewDisplayObject(cTextNote.class, cNoteView.class);
        registerNewDisplayObject(cTask.class, cTaskView.class);
        registerNewDisplayObject(cTaskNote.class, cTaskNoteView.class);
        registerNewDisplayObject(cImageNote.class, cImageView.class);
    }

    private cNoteDisplayView<?> constructView(Context context, Class<?> object) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (objectToDisplayView.containsKey(object)) {
            return objectToDisplayView.get(object).getConstructor(Context.class).newInstance(context);
        }

        Class<?> superClazz = object.getSuperclass();
        if (superClazz != Object.class){
            return constructView(context, superClazz);
        }

        return null;
    }

    private void registerNewDisplayObject(Class<? extends cStorageObject> objectClass, Class<? extends cNoteDisplayView<?>> displayClass){
        objectToDisplayView.put(objectClass, displayClass);
    }

    public static cNoteDisplayViewFactory getInstance(){
        return instance;
    }

    public static cNoteDisplayView<?> getView(Context context, cStorageObject object){
        if (instance == null){
            instance = new cNoteDisplayViewFactory();
        }
        cNoteDisplayView toBeCreated = null;
        try {
            toBeCreated = instance.constructView(context, object.getClass());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            Log.e(TAG, "constructing Note Display failed", e);
        }
        if(toBeCreated == null) {
            Log.e(TAG, "trying to display Note that cannot be displayed: ");
            toBeCreated = new cNoteView(context);
            toBeCreated.setNoteToDisplay(new cTextNote(UUID.randomUUID(),"ERROR","sth. went Wrong"));
        }else {
            toBeCreated.setNoteToDisplay(object);
        }
        return toBeCreated;
    }
}
