package com.example.felix.notizen;

import com.example.felix.notizen.Utils.Logger.cNoteLogger;
import com.example.felix.notizen.Utils.Logger.cNoteLoggerException;
import com.example.felix.notizen.Utils.cNoteMaster;
import com.example.felix.notizen.objects.Notes.cImageNote;
import com.example.felix.notizen.objects.Notes.cTextNote;

import java.util.ArrayList;
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
        String path = "D:\\";
        cNoteLogger logger = cNoteLogger.getInstance();
        logger.init("", 1,1,1,false);
        cTextNote textNote= new cTextNote(UUID.randomUUID(),"dis da title","dis da message");
        cImageNote imageNote = new cImageNote(UUID.randomUUID(), "t", "none");
        cNoteMaster master = cNoteMaster.getInstance();
        master.addNote(textNote);
        master.addNote(new cTextNote(UUID.randomUUID(),"dis da","dis da"));
        master.addNote(imageNote);

        logger.logInfo(textNote.aTYPE);
        logger.logInfo(imageNote.aTYPE);

        System.out.print(textNote.toJson());

        ArrayList<cTextNote> a = master.getNotesOfType(new cTextNote(null,null,null).aTYPE);
        int l = a.size();
        for (int i = 0;i<l;i++){
            logger.logInfo(a.get(i).toJson());
        }

        logger.logInfo(textNote.toJson());

        //cDBHelper master  = cDBHelper.getInstance();
        //master.insert(textNote);
        //master.insert(imageNote);
        //master.delete(textNote);
        //master.delete(imageNote);

        imageNote.deleteNote();
        textNote.deleteNote();
        try {
            logger.flush();
        } catch (cNoteLoggerException e) {
            e.printStackTrace();
        }
    }
}
