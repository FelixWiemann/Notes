package com.nepumuk.notizen.core.favourites;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class Favourite {

    public Favourite() {
        super();
    }

    public Favourite(@NotNull String id) {
        super();
        NoteId = id;
    }

    @PrimaryKey
    @ColumnInfo(name = "note_id")
    @NonNull
    public String NoteId;
}
