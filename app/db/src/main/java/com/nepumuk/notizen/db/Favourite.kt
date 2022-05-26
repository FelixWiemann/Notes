package com.nepumuk.notizen.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

data class Favourite (val noteId:String){}

open class FavouriteRepository(private val dao: FavouriteDAO) {

    val liveFavourite: LiveData<List<Favourite>> = Transformations.map(dao.all){ it ->
        it.map { it.toFavourite() }
    }

    fun exists(fav :Favourite) :Boolean{
        return liveFavourite.value!=null && liveFavourite.value!!.contains(fav)
    }

    fun get(id :String): Favourite {
        return dao.findFavourite(id).toFavourite();
    }

    fun delete(fav :Favourite){
        dao.delete(FavouriteEntity.from(fav))
    }

    fun createOrUpdate(fav: Favourite){
        dao.createOrUpdate(FavouriteEntity.from(fav))
    }
}