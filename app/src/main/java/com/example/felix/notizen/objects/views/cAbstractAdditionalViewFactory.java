package com.example.felix.notizen.objects.views;

import android.content.Context;
import android.support.annotation.Nullable;

import com.example.felix.notizen.Utils.Logger.cNoteLogger;
import com.example.felix.notizen.objects.Notes.cTextNote;
import com.example.felix.notizen.objects.Task.cTask;
import com.example.felix.notizen.objects.cIdObject;

/**
 * Created by Felix on 11.11.2018.
 */

public class cAbstractAdditionalViewFactory {

    @Nullable
    public static cAbstractAdditionalView getView (Context context, cIdObject object){
        if (object!=null){
            switch (object.getClass().getCanonicalName()) {
                case "com.example.felix.notizen.objects.Notes.cTextNote":
                    return new cNoteView(context, (cTextNote) object);
                case "com.example.felix.notizen.objects.Task.cTask":
                    return new cTaskView(context, (cTask) object);
            }
        }
        cNoteLogger.getInstance().logWarning("trying to display Note that cannot be displayed: "+object.getClass().getCanonicalName());
        // TODO return default null view
        return null;
    }
}
