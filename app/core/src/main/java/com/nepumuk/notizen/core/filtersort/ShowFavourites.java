package com.nepumuk.notizen.core.filtersort;

import androidx.annotation.NonNull;

import com.nepumuk.notizen.core.objects.IdObject;
import com.nepumuk.notizen.db.FavouriteRepository;

import org.jetbrains.annotations.NotNull;

public class ShowFavourites<T extends IdObject> extends ViewFilter<T>{

    FavouriteRepository favouriteRepository;

    public ShowFavourites(){
        super();
        this.favouriteRepository = FavouriteRepository.INSTANCE;
    }

    /**
     * return true, if the object needs to be displayed, false otherwise
     * default behaviour is to show all existing ones.
     *
     * @param toFilter Object that shall be decided
     * @return true, if it should be shown, false if filtered out
     */
    @Override
    public boolean filter(@NonNull @NotNull T toFilter) {
        String idString = toFilter.getID();
        return favouriteRepository.exists(idString);
    }
}
