package com.nepumuk.notizen.views;

import android.content.Context;
import android.util.Log;

import com.nepumuk.notizen.objects.notes.ImageNote;
import com.nepumuk.notizen.objects.notes.TaskNote;
import com.nepumuk.notizen.objects.notes.TextNote;
import com.nepumuk.notizen.objects.tasks.Task;
import com.nepumuk.notizen.objects.StorageObject;
import com.nepumuk.notizen.views.note_views.ImageNoteView;
import com.nepumuk.notizen.views.note_views.NoteDisplayView;
import com.nepumuk.notizen.views.note_views.NoteView;
import com.nepumuk.notizen.views.note_views.TaskNoteView;
import com.nepumuk.notizen.views.note_views.TaskView;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Felix on 11.11.2018.
 */

public class NoteDisplayViewFactory {

    private static final String TAG = "Display View Factory";

    private static NoteDisplayViewFactory instance;

    private HashMap<Class<?>, Class <? extends NoteDisplayView<?>>> objectToDisplayView;

    private NoteDisplayViewFactory() {
        super();
        objectToDisplayView = new HashMap<>();
        registerNewDisplayObject(TextNote.class, NoteView.class);
        registerNewDisplayObject(Task.class, TaskView.class);
        registerNewDisplayObject(TaskNote.class, TaskNoteView.class);
        registerNewDisplayObject(ImageNote.class, ImageNoteView.class);
    }

    private NoteDisplayView<?> constructView(Context context, Class<?> object) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (objectToDisplayView.containsKey(object)) {
            return objectToDisplayView.get(object).getConstructor(Context.class).newInstance(context);
        }

        Class<?> superClazz = object.getSuperclass();
        if (superClazz != Object.class){
            return constructView(context, superClazz);
        }

        return null;
    }

    private void registerNewDisplayObject(Class<? extends StorageObject> objectClass, Class<? extends NoteDisplayView<?>> displayClass){
        objectToDisplayView.put(objectClass, displayClass);
    }

    public static NoteDisplayViewFactory getInstance(){
        return instance;
    }

    public static NoteDisplayView<?> getView(Context context, StorageObject object){
        if (instance == null){
            instance = new NoteDisplayViewFactory();
        }
        NoteDisplayView toBeCreated = null;
        try {
            toBeCreated = instance.constructView(context, object.getClass());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            Log.e(TAG, "constructing Note Display failed", e);
        }
        if(toBeCreated == null) {
            Log.e(TAG, "trying to display Note that cannot be displayed: ");
            toBeCreated = new NoteView(context);
            toBeCreated.setNoteToDisplay(new TextNote(UUID.randomUUID(),"ERROR","sth. went Wrong"));
        }else {
            toBeCreated.setNoteToDisplay(object);
        }
        return toBeCreated;
    }
}
