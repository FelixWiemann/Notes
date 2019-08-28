package com.example.felix.notizen.objects.views;

import android.content.Context;

import com.example.felix.notizen.Utils.Logger.cNoteLogger;
import com.example.felix.notizen.objects.Notes.cTextNote;
import com.example.felix.notizen.objects.cStorageObject;

import java.util.UUID;

/**
 * Created by Felix on 11.11.2018.
 */

public class cNoteDisplayViewFactory {

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
        cNoteLogger.getInstance().logWarning("trying to display Note that cannot be displayed: "+message);
        toBeCreated = new cNoteView(context);
        toBeCreated.setNoteToDisplay(new cTextNote(UUID.randomUUID(),"ERROR","sth. went Wrong"));
        return toBeCreated;
    }
}
