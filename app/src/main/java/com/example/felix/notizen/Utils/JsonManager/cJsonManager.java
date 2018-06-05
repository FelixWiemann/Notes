package com.example.felix.notizen.Utils.JsonManager;

import android.util.JsonReader;

import com.example.felix.notizen.Utils.Logger.cNoteLogger;
import com.example.felix.notizen.Utils.cBaseException;
import com.example.felix.notizen.Utils.cNoteMaster;
import com.example.felix.notizen.objects.Notes.cNote;
import com.example.felix.notizen.objects.Notes.cTextNote;
import com.example.felix.notizen.objects.cJSONObject;
import com.example.felix.notizen.Settings.cSetting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Manager for JSON file containing all data saved in this application.
 * JSON file shall look like template
 *
 * Used as singleton
 */
public class cJsonManager extends cJSONObject  {

    /**
     * Location of JSON file
     * To be set at construction of JSON_READER
     */
    private String aJSON_FILE_LOCATION = "";

    /**
     * singleton instance
     */
    private static cJsonManager jsonManagerInstance = new cJsonManager(UUID.randomUUID(),"JSON Manager");

    private cNoteLogger noteLogger;

    private cNoteMaster master;

    private boolean isInitialized = false;

    private cJsonManager(UUID mID, String mTitle) {
        super(mID, mTitle);
    }

    /**
     * get the instance of the JSON manager
     * the instance gets initialized before returning
     * @return JSON manager instance
     */
    public static cJsonManager getInstance(){
        // initialize instance before returning
        jsonManagerInstance.init();
        return jsonManagerInstance;
    }


    /**
     * init the JSON manager. shall be set at start of application
     * init JSON manager with settings of the application
     */
    public void init() {
        cSetting settings = cSetting.getInstance();
        // init, but do not re init!
        init(settings.getSettingString(cSetting.aJSON_LOCATION),false);
    }

    /**
     * init the JSON manager. shall be set at start of application
     * only gets initialized on first call, after that initialization gets skipped.
     *
     * @param pJSON_FILE_LOCATION location of the JSON-file
     * @param reInit flag whether to reinitialize the manager
     */
    private void init(String pJSON_FILE_LOCATION, boolean reInit){
        // check whether is initialized or reInit is true
        // if already init or reInit is false, skip
        if (!isInitialized|reInit) {
            noteLogger = cNoteLogger.getInstance();
            noteLogger.logDebug("JSON Manager init");
            // discuss whether file should be constant or set at beginning of application
            // -> set at beginning of application; to make setting by user to use internal/external/cloud possible
            aJSON_FILE_LOCATION = pJSON_FILE_LOCATION;
            noteLogger.logInfo("JSON file at: " + pJSON_FILE_LOCATION);
            master = cNoteMaster.getInstance();
            // set initialization to true
            isInitialized = true;
        }else{
            noteLogger.logDebug("skipping initialization");
        }
    }

    /**
     * read JSON file
     * does not return anything, as read instances are saved at runtime into runtime data manager
     * @throws cJsonManagerException in case something goes wrong reading the data
     */
    public void read_JSON() throws cJsonManagerException{
        noteLogger.logDebug("JSON Manager read JSON");
        // input stream used for reading data
        InputStream inputStream;
        // init reader
        JsonReader reader = null;
        try {
            // open file at file location + opening the stream at the position
            File f = new File(aJSON_FILE_LOCATION);
            if (!f.exists()){
                noteLogger.logDebug("file not existent, skipping read");
                return;
            }
            inputStream = new FileInputStream(f);
            // open reader
            reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            // read the file
            read(reader);
        }
        catch (cJsonManagerException | IOException e) {
            cJsonManagerException ex = new cJsonManagerException("cJsonManager readJSON()",cJsonManagerException.aREAD_FAILED,e);
            throw ex;
        } finally {
            try {
                if (reader!=null){
                    // close reader
                    reader.close();
                }
            } catch (IOException e) {
                cJsonManagerException ex = new cJsonManagerException("cJsonManager readJSON()",cJsonManagerException.aREAD_FAILED,e);
                throw ex;
            }
        }
        noteLogger.logDebug("JSON Manager read finished");
    }

    /**
     * actually read the things
     * @param reader reader to read with
     * @throws cJsonManagerException ex
     */
    private void read(JsonReader reader) throws cJsonManagerException{
        try {
            noteLogger.logDebug("JSON read file begin");
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
                        noteLogger.logDebug("JSON reading tasks");
                        // not jet implemented, skip
                        reader.skipValue();
                        break;
                       // readTasks(reader);
                    case "NOTES":
                        noteLogger.logDebug("JSON reading notes");
                        // read all notes
                        readNodes(reader);
                        break;
                }
            }
            // finally end object
            reader.endObject();
            noteLogger.logDebug("JSON reading file finished");
        } catch (IOException e) {
            cJsonManagerException exception = new cJsonManagerException("JSON Manager read tasks",cJsonManagerException.aREAD_FAILED,e);
            throw exception;
        } catch (cJsonManagerException e) {
            cJsonManagerException exception = new cJsonManagerException("JSON Manager read tasks",cJsonManagerException.aREAD_FAILED,e);
            throw exception;
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
            readTasks(reader);
            // currently skipped, as not implemented
            reader.endObject();
        } catch (IOException | cJsonManagerException e) {
            cJsonManagerException exception = new cJsonManagerException("JSON Manager read tasks",cJsonManagerException.aREAD_TASKS_FAILED,e);
            throw exception;
        }
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
                        noteLogger.logDebug("JSON reading TextNotes");
                        reader.beginArray();
                        while (reader.hasNext()){
                            readTextNote(reader);
                        }
                        reader.endArray();
                        break;
                    // read all image notes
                    case "cImageNote":
                        noteLogger.logDebug("JSON reading Image Notes");
                        reader.skipValue();
                        break;
                        //readImageNote(reader);
                }
            }
            reader.endObject();
        } catch (IOException e) {
            cJsonManagerException exception = new cJsonManagerException("JSON Manager read notes",cJsonManagerException.aREAD_NOTES_FAILED,e);
            throw exception;
        } catch (cJsonManagerException e) {
            cJsonManagerException exception = new cJsonManagerException("JSON Manager read notes",cJsonManagerException.aREAD_NOTES_FAILED,e);
            throw exception;
        }
    }

    /**
     * read text note
     * @param reader reader to read with
     * @throws cJsonManagerException
     */
    private void readTextNote(JsonReader reader) throws cJsonManagerException{
        try {
            noteLogger.logDebug("JSON begin reading text note");
            // begin text note object
            reader.beginObject();
            // init vars
            String ID = "",Title="",Text="";
            long CreationDate=0,LastChangeDate=0;
            // get all vars
            while (reader.hasNext()) {
                String switcher = reader.nextName();
                switch (switcher){
                    case aJSON_ID:
                        ID = reader.nextString();
                        break;
                    case aJSON_Title:
                        Title = reader.nextString();
                        break;
                    case cNote.aJSON_CREATION_DATE:
                        CreationDate = reader.nextLong();
                        break;
                    case cNote.aJSON_LAST_CHANGE_DATE:
                        LastChangeDate = reader.nextLong();
                        break;
                    case cTextNote.aJSON_TEXT:
                        Text = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                }
            }
            noteLogger.logDebug("text note reading finished, building...");
            // build note
            cTextNote note = new cTextNote(UUID.fromString(ID),Title,true,Text);
            // set creation date, as already existent note
            note.setCreationDate(CreationDate);
            // set last changed date
            note.setLastChangeDate(LastChangeDate);
            cNoteMaster c = cNoteMaster.getInstance();
            c.addNote(note);
            noteLogger.logDebug("...building finished");
            // end object
            reader.endObject();
        } catch (IOException e) {
            cJsonManagerException ex = new cJsonManagerException("JSON Manager read Text Note",cJsonManagerException.aREAD_TEXT_NOTE_FAILED,e);
            throw ex;
        } catch (cBaseException e) {
            cJsonManagerException ex = new cJsonManagerException("JSON Manager read Text Note",cJsonManagerException.aREAD_TEXT_NOTE_FAILED,e);
            throw ex;
        }
    }

    /**
     *
     * @param reader
     * @throws cJsonManagerException
     */
    private void readImageNote(JsonReader reader) throws cJsonManagerException {
        //TODO read image note
    }

    /**
     * write the JSON-file
     * @throws cJsonManagerException if something goes wrong
     */
    public void writeJSON() throws cJsonManagerException {
        noteLogger.logDebug("JSON Manager writing JSON");
        String header = "{\n";
        FileOutputStream outputStream;
        try {
            File f = new File(aJSON_FILE_LOCATION);
            outputStream = new FileOutputStream(f);

            // write begin of file:
            outputStream.write(header.getBytes());
            write_Tasks(outputStream);
            outputStream.write(aJSON_COMMA.getBytes());
            outputStream.write(aJSON_NEW_LINE.getBytes());
            write_Notes(outputStream);
            outputStream.write("\n}".getBytes());
            outputStream.close();
        } catch (IOException | cJsonManagerException ex){
            cJsonManagerException exception = new cJsonManagerException("JSON Manager write Objects of Type",cJsonManagerException.aWRITE_JSON_FAILED,ex);
            throw exception;
        }
        noteLogger.logDebug("JSON Manager writing finished");
    }

    /**
     * write all notes
     * @param stream to write in
     * @throws cJsonManagerException if something goes wrong
     */
    private void write_Notes(OutputStream stream) throws cJsonManagerException {
        noteLogger.logDebug("writing notes");
        try {
            String lTypeHeader = aJSON_FIELD_SIGN + "NOTES" + aJSON_FIELD_SIGN + aJSON_SEP;
            stream.write(lTypeHeader.getBytes());
            stream.write(aJSON_OBJ_BEGIN.getBytes());
            stream.write(aJSON_NEW_LINE.getBytes());
            write_Objects_Of_Type("cTextNote",stream);
            // TODO write all Note types
            stream.write(aJSON_OBJ_END.getBytes());
        }
        catch (IOException ex){
            cJsonManagerException exception = new cJsonManagerException("JSON Manager write Objects of Type",cJsonManagerException.aWRITE_NOTES_FAILED,ex);
            throw exception;
        }


    }

    /**
     * write all tasks
     * @param stream to write in
     * @throws cJsonManagerException if something goes wrong
     */
    private void write_Tasks(OutputStream stream) throws cJsonManagerException {
        noteLogger.logDebug("writing tasks");
        try {
            String lTypeHeader = aJSON_FIELD_SIGN + "TASKS" + aJSON_FIELD_SIGN + aJSON_SEP;
            stream.write(lTypeHeader.getBytes());
            stream.write(aJSON_OBJ_BEGIN.getBytes());
            stream.write(aJSON_NEW_LINE.getBytes());
            // TODO write all Task types
            stream.write(aJSON_OBJ_END.getBytes());
        }
        catch (IOException ex){
            cJsonManagerException exception = new cJsonManagerException("JSON Manager write Objects of Type",cJsonManagerException.aWRITING_TASKS_FAILED,ex);
            throw exception;
        }
    }

    /**
     * writes all objects stored in notes master of the given type in JSON-format in the JSON file
     * @param Type of objects to be stored
     * @param stream of the file.
     * @throws cJsonManagerException if something goes wrong writing the type
     */
    private void write_Objects_Of_Type(String Type,OutputStream stream) throws cJsonManagerException {
        try {
            // get all Objects of type Type
            ArrayList<cJSONObject> notes;
            notes = master.getNotesOfType(Type);
            // write Object type
            String lTypeHeader = aJSON_FIELD_SIGN + Type + aJSON_FIELD_SIGN + aJSON_SEP;
            stream.write(lTypeHeader.getBytes());
            // begin array of objects
            stream.write(aJSON_ARRAY_BEGIN.getBytes());
            stream.write(aJSON_NEW_LINE.getBytes());
            // loop over every object
            for (int i = 0; i < notes.size(); i++) {
                // write Object
                write_JSON_Note(notes.get(i), stream);
                if (i != notes.size() - 1) {
                    // if not last object, separate with comma
                    stream.write(aJSON_COMMA.getBytes());
                }
                // write new line regardless, of type
                stream.write(aJSON_NEW_LINE.getBytes());
            }
            // end array
            stream.write(aJSON_ARRAY_END.getBytes());
        }
        catch (IOException ex){
            cJsonManagerException exception = new cJsonManagerException("JSON Manager write Objects of Type",cJsonManagerException.aWRITING_OBJECTS_OF_TYPE_FAILED,ex);
            throw exception;
        }

    }

    /**
     *
     * @param objectToWrite
     * @param stream
     * @throws IOException
     */
    private void write_JSON_Note(cJSONObject objectToWrite, OutputStream stream) throws IOException {
        stream.write(objectToWrite.generateJSONString().getBytes());
    }


    /**
     *
     * @return
     */
    @Override
    public String generateJSONString() {
        return null;
    }
}
