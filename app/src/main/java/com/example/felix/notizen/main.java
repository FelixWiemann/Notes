package com.example.felix.notizen;

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
        cTextNote textNote= new cTextNote(UUID.randomUUID(),"dis da title","dis da message");
        cImageNote imageNote = new cImageNote(UUID.randomUUID(), "t", "none");
        /*cNoteMaster master = cNoteMaster.getInstance();
        master.addNote(textNote);
        master.addNote(new cTextNote(UUID.randomUUID(),"dis da","dis da"));
        master.addNote(imageNote);

        System.out.print(textNote.toJson());

        ArrayList<cTextNote> a = master.getNotesOfType(new cTextNote(null,null,null).aTYPE);
        int l = a.size();*/


        //cDBHelper master  = cDBHelper.getInstance();
        //master.insert(textNote);
        //master.insert(imageNote);
        //master.delete(textNote);
        //master.delete(imageNote);

        imageNote.deleteNote();
        textNote.deleteNote();
    }
}
