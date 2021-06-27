package com.nepumuk.notizen.core.favourites;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Favourite {
    @PrimaryKey
    @ColumnInfo(name = "note_id")
    @NonNull
    public String NoteId;
}
