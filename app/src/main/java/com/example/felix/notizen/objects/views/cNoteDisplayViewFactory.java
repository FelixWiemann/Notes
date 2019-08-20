package com.example.felix.notizen.objects.views;

import android.content.Context;
import android.support.annotation.Nullable;

import com.example.felix.notizen.Utils.Logger.cNoteLogger;
import com.example.felix.notizen.objects.Displayable;
import com.example.felix.notizen.objects.Notes.cImageNote;
import com.example.felix.notizen.objects.Notes.cTextNote;
import com.example.felix.notizen.objects.Task.cTask;
import com.example.felix.notizen.objects.cIdObject;

import java.util.UUID;

/**
 * Created by Felix on 11.11.2018.
 */

public class cNoteDisplayViewFactory {

    static cNoteDisplayView getView(Context context, Displayable object){
        cNoteDisplayView toBeCreated;
        String message = "NULL OBJECT";
        if (object!=null){
            switch (object.getClass().getCanonicalName()) {
                case "com.example.felix.notizen.objects.Notes.cTextNote":
                    toBeCreated = new cNoteView(context);
                    toBeCreated.setNoteToDisplay(object);
                    return toBeCreated;
                case "com.example.felix.notizen.objects.Task.cTask":
                    toBeCreated = new cTaskView(context);
                    toBeCreated.setNoteToDisplay(object);
                    return toBeCreated;
                case "com.example.felix.notizen.objects.Notes.cImageNote":
                    toBeCreated = new cImageView(context);
                    toBeCreated.setNoteToDisplay(object);
                    return toBeCreated;
            }
            message = object.getClass().getCanonicalName();
        }
        cNoteLogger.getInstance().logWarning("trying to display Note that cannot be displayed: "+message);
        toBeCreated = new cNoteView(context);
        toBeCreated.setNoteToDisplay(new cTextNote(UUID.randomUUID(),"ERROR","sth. went Wrong"));
        return toBeCreated;
    }
}
