package com.example.felix.notizen.views;

import android.content.Context;
import android.util.Log;

import com.example.felix.notizen.objects.Notes.cTextNote;
import com.example.felix.notizen.objects.cStorageObject;

import java.util.UUID;

/**
 * Created by Felix on 11.11.2018.
 */

public class cNoteDisplayViewFactory {

    private static final String TAG = "Display View Factory";

    private cNoteDisplayViewFactory(){}

    public static cNoteDisplayView getView(Context context, cStorageObject object){
        cNoteDisplayView toBeCreated = null;
        String message = "NULL OBJECT";
        if (object!=null){
            switch (object.getClass().getCanonicalName()) {
                case "com.example.felix.notizen.objects.Notes.cTextNote":
                    toBeCreated = new cNoteView(context);
                    break;
                case "com.example.felix.notizen.objects.Task.cTask":
                    toBeCreated = new cTaskView(context);
                    break;
                case "com.example.felix.notizen.objects.Notes.cTaskNote":
                    toBeCreated = new cTaskNoteView(context);
                    break;
                case "com.example.felix.notizen.objects.Notes.cImageNote":
                    toBeCreated = new cImageView(context);
                    break;
                default:
                    message = object.getClass().getCanonicalName();
            }
            if (toBeCreated != null){
                toBeCreated.setNoteToDisplay(object);
                return toBeCreated;
            }
        }
        Log.w(TAG, "trying to display Note that cannot be displayed: "+message);
        toBeCreated = new cNoteView(context);
        toBeCreated.setNoteToDisplay(new cTextNote(UUID.randomUUID(),"ERROR","sth. went Wrong"));
        return toBeCreated;
    }
}
