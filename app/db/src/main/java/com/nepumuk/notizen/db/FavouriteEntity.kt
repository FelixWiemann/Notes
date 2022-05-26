package com.nepumuk.notizen.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
internal data class FavouriteEntity (
         @PrimaryKey val NoteId:String
        )
{
    companion object{
        fun from(fav: Favourite): FavouriteEntity {
            return FavouriteEntity(fav.noteId)
        }
    }

    fun toFavourite(): Favourite {
        return Favourite(NoteId)
    }
}