package com.example.felix.notizen;

import com.example.felix.notizen.BackEnd.Logger.cNoteLogger;
import com.example.felix.notizen.FrontEnd.Notes.cTextNote;

import java.io.File;

/**
 * Created as part of notes in package com.example.felix.notizen
 * by Felix "nepumuk" Wiemann on 04/06/17.
 */
@SuppressWarnings("unused")
public class main {
    public static void main(String [] args)
    {
        String path = "/home/felix/log.txt";
        cNoteLogger logger = cNoteLogger.getInstance();
        logger.init(path,4);
        cTextNote textNote= new cTextNote("id","title","message");
        textNote.deleteNote();
        logger.flush();
    }
}
