package com.nepumuk.notizen.core.filtersort;

import androidx.annotation.NonNull;

import com.nepumuk.notizen.core.objects.StorageObject;

import java.util.HashMap;

public class TextFilter<T extends StorageObject> extends ViewFilter<T> {

    public static final String SEP = "@!@!";

    private static HashMap<Class, GetTextVisitor> textMapping = new HashMap<>();

    public static <T extends StorageObject> void addMapping(Class <T> clazz, GetTextVisitor<T> visitor){
        textMapping.put(clazz,visitor);
    }

    private final String searchPhrase;

    public TextFilter(String searchPhrase) {
        super();
        this.searchPhrase = searchPhrase;
    }

    /**
     * return true, if the object needs to be displayed, false otherwise
     * default behaviour is to show all existing ones.
     *
     * @param toFilter Object that shall be decided
     * @return true, if it should be shown, false if filtered out
     */
    @Override
    public boolean filter(@NonNull StorageObject toFilter) {
        // show all if phrase is empty string
        if (searchPhrase.equals("")) return true;
        // don't show if the data is not available
        if (!textMapping.containsKey(toFilter.getClass())) return false;
        // show if matches
        return textMapping.get(toFilter.getClass()).getText(toFilter).contains(searchPhrase);
    }

    public interface GetTextVisitor <T extends StorageObject>{
        String getText(@NonNull T object);
    }
}
