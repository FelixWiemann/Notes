package com.example.felix.notizen.objects;

import com.example.felix.notizen.views.viewsort.SortAble;
import com.example.felix.notizen.views.viewsort.SortCategory;

import java.util.HashMap;
import java.util.UUID;

public class cSortableObject extends cIdObject {

    HashMap<SortCategory, SortAble<?>> sortAbleHashMap = new HashMap<>();

    public cSortableObject(UUID mID, String mTitle) {
        super(mID, mTitle);
    }

    public cSortableObject() {
        super();
    }

    public void addSortable(SortCategory category, SortAble<?> sortAble){
        sortAbleHashMap.put(category, sortAble);
    }

    public Object getSortable(SortCategory category){
        // prevent nullptr, if no entry at that position
        if (!sortAbleHashMap.containsKey(category)&&sortAbleHashMap.get(category)!=null) {
            return null;
        }else {
            return sortAbleHashMap.get(category).getData();
        }
    }

}
