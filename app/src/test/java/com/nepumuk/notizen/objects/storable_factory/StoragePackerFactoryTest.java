package com.nepumuk.notizen.objects.storable_factory;

import android.content.Intent;

import com.nepumuk.notizen.objects.UnpackingDataError;
import com.nepumuk.notizen.objects.UnpackingDataException;
import com.nepumuk.notizen.utils.db_access.DatabaseStorable;
import com.nepumuk.notizen.objects.notes.TaskNote;
import com.nepumuk.notizen.objects.notes.TextNote;
import com.nepumuk.notizen.objects.storable_factory.StorableFactory;
import com.nepumuk.notizen.testutils.AndroidTest;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.matches;

public class StoragePackerFactoryTest extends AndroidTest {


    @Test(expected = UnpackingDataException.class)
    public void createFromData_expectClassNotFound() throws Exception {
         StorableFactory.createFromData("","","",1);
    }

    @Test(expected = UnpackingDataError.class)
    public void createFromData_expectNoDatabaseStorable() throws Exception {
        StorableFactory.createFromData("2563c779-7e46-4003-927b-1ff36077b285","com.nepumuk.notizen.objects.IdObject","{\"title\":\"test title\",\"idString\":\"2563c779-7e46-4003-927b-1ff36077b285\"}",1);
    }

    @Test(expected = UnpackingDataError.class)
    public void createFromData_expectAssertionError() throws Exception {
        StorableFactory.createFromData("2563c779-7e46-4003-927b-1ff36077b285","com.nepumuk.notizen.objects.notes.TaskNote","{\"titleas\":\"test title\",\"idString\":\"2563c779-7e46-4003-927b-1ff36077b285\"}",1);
    }

    @Test()
    public void createFromData() throws Exception {
        TaskNote note = (TaskNote) StorableFactory.createFromData("2563c779-7e46-4003-927b-1ff36077b285","com.nepumuk.notizen.objects.notes.TaskNote","{\"title\":\"test title\",\"idString\":\"2563c779-7e46-4003-927b-1ff36077b285\"}",1);
        assertEquals(note.getTitle(), "test title");
        assertEquals(note.getIdString(),"2563c779-7e46-4003-927b-1ff36077b285");
    }


    @Test
    public void addToIntent() {
        Intent prevIntent = new Intent("");
        StorableFactory.addToIntent(prevIntent,null);

        Mockito.verify(prevIntent,Mockito.never()).putExtra(anyString(),anyString());

        prevIntent = new Intent();
        TextNote note = new TextNote(UUID.fromString("2563c779-7e46-4003-927b-1ff36077b285"),"title","message");
        StorableFactory.addToIntent(prevIntent,note);
        Mockito.verify(prevIntent,Mockito.times(1)).putExtra(matches("INTENT_NAME_NOTE_ID"),anyString());
        Mockito.verify(prevIntent,Mockito.times(1)).putExtra(matches("INTENT_NAME_NOTE_DATA"),anyString());
        Mockito.verify(prevIntent,Mockito.times(1)).putExtra(matches("INTENT_NAME_NOTE_TYPE"),anyString());
        Mockito.verify(prevIntent,Mockito.times(1)).putExtra(matches("INTENT_NAME_NOTE_VERSION"),anyInt());
    }

    @Test
    public void storableFromIntent() throws Exception {
        Intent prevIntent = new Intent();
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
        assertNull(StorableFactory.storableFromIntent(null));
    }
}