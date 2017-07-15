package com.example.felix.notizen.BackEnd;

/**
 * Singleton as master entry point in the database saving all notes
 * database structure:
 * table master:
 * UUID of note as key, type of note as value.
 * table of each note-type according to fields inside the notes
 *
 * cNoteMaster manages master table
 * provides interface to different sql-table managers for each note
 *
 *
 * Created as part of notes in package com.example.felix.notizen.BackEnd
 * by Felix "nepumuk" Wiemann on 03/06/17.
 *
 */
@SuppressWarnings("unused")
public class cNoteMaster {



    /**
     * singleton instance made available for use everywhere
     */
    private static cNoteMaster singletonInstance = new cNoteMaster();

    /**
     * get the singleton instance of the note master
     * @return
     */
    public static cNoteMaster getInstance() {
        return singletonInstance;
    }

    private cNoteMaster() {
    }

}
