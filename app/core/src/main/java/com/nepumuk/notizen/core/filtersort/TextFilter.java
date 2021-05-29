package com.nepumuk.notizen.core.filtersort;

import androidx.annotation.NonNull;

import com.nepumuk.notizen.core.objects.StorageObject;

import java.util.HashMap;

public class TextFilter<T extends StorageObject> extends ViewFilter<T> {

    private static HashMap<Class, TextContainsVisitor> textMapping = new HashMap<>();

    public static <T extends StorageObject> void addMapping(Class <T> clazz, TextContainsVisitor<T> visitor){
        textMapping.put(clazz,visitor);
    }

    private final String searchPhrase;

    public TextFilter(String searchPhrase) {
        super();
        this.searchPhrase = searchPhrase.toLowerCase();
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
        return textMapping.get(toFilter.getClass()).contains(toFilter,searchPhrase);
    }

    public interface TextContainsVisitor<T extends StorageObject>{
        boolean contains(@NonNull T object, String text);
    }
}
