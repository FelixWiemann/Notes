package com.example.felix.notizen.BackEnd.JsonManager;

import android.util.JsonReader;

import com.example.felix.notizen.BackEnd.cNoteMaster;
import com.example.felix.notizen.FrontEnd.Notes.cNote;
import com.example.felix.notizen.FrontEnd.Notes.cTextNote;
import com.example.felix.notizen.FrontEnd.cJSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Manager for JSON file containing all data saved in this application.
 * JSON file shall look like template
 *
 * Used as singleton
 */
public class cJsonManager extends cJSONObject {

    /**
     * Location of JSON file
     * To be set at construction of JSON_READER
     */
    private String aJSON_FILE_LOCATION = "";

    /**
     * singleton instance
     */
    private static cJsonManager jsonManagerInstance = new cJsonManager(UUID.randomUUID(),"JSON Manager");

    private cJsonManager(UUID mID, String mTitle) {
        super(mID, mTitle);
    }

    /**
     * get the instance of the JSON manager
     * @return JSON manager instance
     */
    public static cJsonManager getInstance(){
        return jsonManagerInstance;
    }



    /**
     * init the JSON manager. shall be set at start of application
     * @param pJSON_FILE_LOCATION location of the JSON-file
     */
    public void init(String pJSON_FILE_LOCATION){
        // TODO: discuss whether file should be constant or set at beginning of application
        aJSON_FILE_LOCATION = pJSON_FILE_LOCATION;
    }

    /**
     * read JSON file
     * does not return anything, as read instances are saved at runtime into runtime data manager
     * @throws cJsonManagerException in case something goes wrong reading the data
     */
    public void read_JSON() throws cJsonManagerException{
        // input stream used for reading data
        InputStream inputStream = null;
        try {
            // open file at file location + opening the stream at the position
            File f = new File(aJSON_FILE_LOCATION);
            inputStream = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            // catch exception, set own exception for better logging into log file
            throw new cJsonManagerException("cJsonManager readJSON()",cJsonManagerException.aFILE_NOT_FOUND_EXCEPTION,e);
        }
        // init reader
        JsonReader reader = null;
        try {
            // open reader
            reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // catch exception
            throw new cJsonManagerException("cJsonManager readJSON()",cJsonManagerException.aUNSUPPORTED_ENCODING,e);
        }
        try {
            // read the file
            read(reader);
        } catch (cJsonManagerException e) {
            throw new cJsonManagerException("cJsonManager readJSON()",cJsonManagerException.aREAD_FAILED,e);
        } finally {
            try {
                // close reader
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * actually read the things
     * @param reader reader to read with
     * @throws cJsonManagerException ex
     */
    private void read(JsonReader reader) throws cJsonManagerException{
        try {
            // begin reading the base object
            reader.beginObject();
            // while reading next objects
            while (reader.hasNext()) {
                // get the next name
                String switcher = reader.nextName();
                // and switch over them
                switch (switcher){
                    // running all tasks
                    case "TASKS":
                        // not jet implemented, skip
                        reader.skipValue();
                        break;
                       // readTasks(reader);
                    case "NOTES":
                        // read all notes
                        readNodes(reader);
                        break;
                }
            }
            // finally end object
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (cJsonManagerException e) {
            throw new cJsonManagerException("read","failed",e);
        }
    }

    /**
     * read all tasks
     * @param reader treader to read with
     * @throws cJsonManagerException
     */
    private void readTasks(JsonReader reader)throws cJsonManagerException{
        try{
            reader.beginObject();
            // currently skipped, as not implemented
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }/* catch (cJsonManagerException e) {
            throw new cJsonManagerException("read","failed",e);
        }*/
    }

    /**
     * read all nodes
     * @param reader reader to read with
     * @throws cJsonManagerException
     */
    private void readNodes(JsonReader reader)throws cJsonManagerException{
        try{
            // begin reader objects
            reader.beginObject();
            // find type of node
            while (reader.hasNext()) {
                String switcher = reader.nextName();
                switch (switcher){
                    // read all text notes
                    case "cTextNote":
                        reader.beginArray();
                        while (reader.hasNext()){
                            readTextNote(reader);
                        }
                        reader.endArray();
                        break;
                    // read all image notes
                    case "cImageNote":
                        reader.skipValue();
                        break;
                        //readImageNote(reader);
                }
            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (cJsonManagerException e) {
            throw new cJsonManagerException("read","failed",e);
        }
    }

    /**
     * read text note
     * @param reader reader to read with
     * @throws cJsonManagerException
     */
    private void readTextNote(JsonReader reader) throws cJsonManagerException{
        try {
            // begin text note object
            reader.beginObject();
            // init vars
            String ID = "",Title="",Text="";
            long CreationDate=0,LastChangeDate=0;
            // get all vars
            while (reader.hasNext()) {
                String switcher = reader.nextName();
                switch (switcher){
                    // TODO use constants
                    case "ID":
                        ID = reader.nextString();
                        break;
                    case "TITLE":
                        Title = reader.nextString();
                        break;
                    case cNote.aJSON_CREATION_DATE:
                        CreationDate = reader.nextLong();
                        break;
                    case cNote.aJSON_LAST_CHANGE_DATE:
                        LastChangeDate = reader.nextLong();
                        break;
                    // TODO add Text
                    default:
                        reader.skipValue();
                }
            }
            // build note
            cTextNote note = new cTextNote(UUID.fromString(ID),Title,Text);
            try {
                // set creation date, as already existent note
                note.setCreationDate(CreationDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // set last changed date
            note.setLastChangeDate(LastChangeDate);
            // end object
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readImageNote(JsonReader reader) throws cJsonManagerException {
        //TODO read image note
    }

    public void writeJSON(){
        String header = "{\n";
        String _Tasks = "\"TASKS\":{\n";
        String _Notes = "\"NOTES\":{\n";
        cNoteMaster noteMaster = cNoteMaster.getInstance();
        ArrayList<cNote> notes;
        FileOutputStream outputStream;
        try {
            File f = new File(aJSON_FILE_LOCATION);
            outputStream = new FileOutputStream(f);

            // write begin of file:
            outputStream.write(header.getBytes());
            outputStream.write(_Tasks.getBytes());
            outputStream.write("},".getBytes());
            outputStream.write(_Notes.getBytes());
            outputStream.write("\"cTextNote\": [".getBytes());
            //write TextNotes
            notes = noteMaster.getNotesOfType("cTextNote");
            for (int i = 0; i < notes.size(); i++) {
                outputStream.write(notes.get(i).generateJSONString().getBytes());
                if (i!=notes.size()-1){
                    outputStream.write(",\n".getBytes());
                }
            }
            outputStream.write("]\n}\n}".getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String generateJSONString() {
        return null;
    }
}
