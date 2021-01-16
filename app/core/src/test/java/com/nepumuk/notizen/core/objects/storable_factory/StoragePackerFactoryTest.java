package com.nepumuk.notizen.core.objects.storable_factory;

import com.nepumuk.notizen.core.objects.UnpackingDataError;
import com.nepumuk.notizen.core.objects.UnpackingDataException;
import com.nepumuk.notizen.core.testutils.AndroidTest;

import org.junit.Ignore;
import org.junit.Test;

public class StoragePackerFactoryTest extends AndroidTest {


    @Test(expected = UnpackingDataException.class)
    public void createFromData_expectClassNotFound() throws Exception {
         StorableFactory.createFromData("","","",1);
    }

    @Test(expected = UnpackingDataError.class)
    public void createFromData_expectNoDatabaseStorable() throws Exception {
        StorableFactory.createFromData("2563c779-7e46-4003-927b-1ff36077b285","com.nepumuk.notizen.core.objects.IdObject","{\"title\":\"test title\",\"idString\":\"2563c779-7e46-4003-927b-1ff36077b285\"}",1);
    }

    @Test(expected = UnpackingDataError.class)
    public void createFromData_expectAssertionError() throws Exception {
        StorableFactory.createFromData("2563c779-7e46-4003-927b-1ff36077b285","com.nepumuk.notizen.tasks.TaskNote","{\"titleas\":\"test title\",\"idString\":\"2563c779-7e46-4003-927b-1ff36077b285\"}",1);
    }

    @Test()
    @Ignore
    public void createFromData() throws Exception {
       /* TaskNote note = (TaskNote) StorableFactory.createFromData("2563c779-7e46-4003-927b-1ff36077b285","com.nepumuk.notizen.tasks.TaskNote","{\"title\":\"test title\",\"idString\":\"2563c779-7e46-4003-927b-1ff36077b285\"}",1);
        assertEquals(note.getTitle(), "test title");
        assertEquals(note.getIdString(),"2563c779-7e46-4003-927b-1ff36077b285");*/
    }


    @Test
    @Ignore
    public void addToIntent() {
       /* Intent prevIntent = new Intent("");
        StorableFactory.addToIntent(prevIntent,null);

        Mockito.verify(prevIntent,Mockito.never()).putExtra(anyString(),anyString());

        prevIntent = new Intent();
        TextNote note = new TextNote(UUID.fromString("2563c779-7e46-4003-927b-1ff36077b285"),"title","message");
        StorableFactory.addToIntent(prevIntent,note);
        Mockito.verify(prevIntent,Mockito.times(1)).putExtra(matches("INTENT_NAME_NOTE_ID"),anyString());
        Mockito.verify(prevIntent,Mockito.times(1)).putExtra(matches("INTENT_NAME_NOTE_DATA"),anyString());
        Mockito.verify(prevIntent,Mockito.times(1)).putExtra(matches("INTENT_NAME_NOTE_TYPE"),anyString());
        Mockito.verify(prevIntent,Mockito.times(1)).putExtra(matches("INTENT_NAME_NOTE_VERSION"),anyInt());*/
    }

    @Test
    @Ignore
    public void storableFromIntent() throws Exception {
        /*Intent prevIntent = new Intent();
        Mockito.when(prevIntent.hasExtra(matches("INTENT_NAME_NOTE_ID"))).thenReturn(true);
        Mockito.when(prevIntent.getStringExtra(matches("INTENT_NAME_NOTE_ID"))).thenReturn("2563c779-7e46-4003-927b-1ff36077b285");
        Mockito.when(prevIntent.getStringExtra(matches("INTENT_NAME_NOTE_DATA"))).thenReturn("{\"message\":\"message\",\"title\":\"title\",\"creationDate\":1571766729534,\"lastChangedDate\":1571766729534,\"idString\":\"2563c779-7e46-4003-927b-1ff36077b285\"}");
        Mockito.when(prevIntent.getStringExtra(matches("INTENT_NAME_NOTE_TYPE"))).thenReturn(TextNote.class.getCanonicalName());
        Mockito.when(prevIntent.getIntExtra(matches("INTENT_NAME_NOTE_VERSION"),anyInt())).thenReturn(1);
        TextNote note = new TextNote(UUID.fromString("2563c779-7e46-4003-927b-1ff36077b285"),"title","message");
        DatabaseStorable unpacked = StorableFactory.storableFromIntent(prevIntent);
        assertEquals(unpacked.getClass().getCanonicalName(), TextNote.class.getCanonicalName());
        TextNote tnote = (TextNote) unpacked;
        assertEquals(tnote.getMessage(),note.getMessage());
        assertEquals(tnote.getTitle(), note.getTitle());
        assertEquals(tnote.getIdString(), note.getIdString());
        Mockito.when(prevIntent.hasExtra(matches("INTENT_NAME_NOTE_ID"))).thenReturn(false);
        assertNull(StorableFactory.storableFromIntent(prevIntent));
        assertNull(StorableFactory.storableFromIntent(null));*/
    }
}