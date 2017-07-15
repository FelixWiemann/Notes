package com.example.felix.notizen;

import android.content.Context;

import com.example.felix.notizen.BackEnd.DBAccess.cDBMaster;
import com.example.felix.notizen.BackEnd.Logger.cNoteLogger;
import com.example.felix.notizen.BackEnd.Logger.cNoteLoggerException;
import com.example.felix.notizen.BackEnd.cContextManager;
import com.example.felix.notizen.FrontEnd.Notes.cImageNote;
import com.example.felix.notizen.FrontEnd.Notes.cNote;
import com.example.felix.notizen.FrontEnd.Notes.cTaskNote;
import com.example.felix.notizen.FrontEnd.Notes.cTextNote;
import com.example.felix.notizen.FrontEnd.Task.cTask;
import com.example.felix.notizen.FrontEnd.cIdObjectException;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * Main entry point for testing on PC
 * Created as part of notes in package com.example.felix.notizen
 * by Felix "nepumuk" Wiemann on 04/06/17.
 */



@SuppressWarnings("unused")
public class main {
    public static void main(String [] args)
    {
        String path = "/home/felix/";
        cNoteLogger logger = cNoteLogger.getInstance();
        cContextManager cm = cContextManager.getInstance();
        logger.init(path,5);
        cTextNote textNote= new cTextNote(UUID.randomUUID(),"title","message");
        cImageNote imageNote = new cImageNote(UUID.randomUUID(), "t", "none");
        logger.logInfo(textNote.aTYPE);
        logger.logInfo(imageNote.aTYPE);
        cDBMaster master  = cDBMaster.getInstance();
        master.insert(textNote);
        master.insert(imageNote);
        master.delete(textNote);
        master.delete(imageNote);

        imageNote.deleteNote();
        textNote.deleteNote();
        try {
            logger.flush();
        } catch (cNoteLoggerException e) {
            e.printStackTrace();
        }
    }
}
