package com.nepumuk.notizen.core.filtersort;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.nepumuk.notizen.core.objects.IdObject;
import com.nepumuk.notizen.db.Favourite;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShowFavourites<T extends IdObject> extends ViewFilter<T>{

    private List<Favourite> ids;
    LiveData<List<Favourite>> data ;

    public ShowFavourites(List<Favourite> ids){
        super();
        this.ids = ids;
    }

    public ShowFavourites(Fragment owner, LiveData<List<Favourite>> data){
        super();
        ids = data.getValue();
        this.data = data;
        data.observe(owner, (list) -> ids = list);
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
        for (Favourite fav:ids) {
            if (fav.getNoteId().equals(idString)) return true;
        }
        return false;
    }
}
