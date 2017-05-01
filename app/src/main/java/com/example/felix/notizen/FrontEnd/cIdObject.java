package com.example.felix.notizen.FrontEnd;

/**
 * Created as part of notes in package com.example.felix.notizen.FrontEnd
 * by Felix "nepumuk" Wiemann on 29/04/17.
 */
@SuppressWarnings("unused")
public class cIdObject {
    /**
     * ID string of current note, used to identify each note
     */
    private String mID;
    /**
     * title of the note
     */
    private String mTitle;

    public cIdObject(String mID, String mTitle) {
        this.mID = mID;
        this.mTitle = mTitle;
    }

    /**
     * sets the title of the note
     * @param pTitle new title
     */
    public void setTitle(String pTitle){
        mTitle = pTitle;
    }

    /**
     * sets the id of the note
     * TODO prevent illegal manipulation on runtime on existing notes!
     * @param pID new id
     */
    public void setId(String pID){
        mID = pID;
    }

    /**
     * gets the id o the note
     * @return id
     */
    public String getID(){
        return mID;
    }

    /**
     * gets the title of the note
     * @return title
     */
    public String getTitle(){
        return mTitle;
    }
}
