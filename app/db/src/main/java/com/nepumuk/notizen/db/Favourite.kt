package com.nepumuk.notizen.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

data class Favourite (val noteId:String){}

open class FavouriteRepository(private val dao: FavouriteDAO = AppDataBaseHelper.getInstance().appDataBase.favouriteDAO()) {

    val liveFavourite: LiveData<List<Favourite>> = Transformations.map(dao.all){ it ->
        it.map { it.toFavourite() }
    }

    fun exists(fav :Favourite) :Boolean{
        return liveFavourite.value!=null && liveFavourite.value!!.contains(fav)
    }

    fun get(id :String): Favourite {
        return dao.findFavourite(id).toFavourite();
    }

    fun delete(idString: String){
        dao.delete(FavouriteEntity(idString))
    }

    fun delete(fav :Favourite){
        dao.delete(FavouriteEntity.from(fav))
    }

    fun createOrUpdate(idString: String){
        dao.createOrUpdate(FavouriteEntity(idString))
    }

    fun createOrUpdate(fav: Favourite){
        dao.createOrUpdate(FavouriteEntity.from(fav))
    }

    fun exists(id: String):Boolean{
        return exists(Favourite(id));
    }
}