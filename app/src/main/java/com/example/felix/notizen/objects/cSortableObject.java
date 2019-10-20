package com.example.felix.notizen.objects;

import com.example.felix.notizen.views.viewsort.SortAble;
import com.example.felix.notizen.views.viewsort.SortCategory;

import java.util.HashMap;

/**
 * object which handles the sorting of data
 *
 * you have a sort category and add an runnable to
 * get the value of that category for sorting
 *
 * e.g.
 * > addSortable(SortCatgory.Title, new SortAble<String>() {
 *             public String getData() {
 *                 return getTitle();
 *             }
 *         });)
 * would return the title on call getSortable(Title);
 *
 * TODO add filtering
 * TODO should think about moving it below in inheritance hierarchy
 */
public class cSortableObject extends cJSONObject {

    /**
     * map to hold all sortables
     */
    private HashMap<SortCategory, SortAble<?>> sortAbleHashMap = new HashMap<>();


    public cSortableObject() {
        super();
    }

    /**
     * adds a sortable with given category and runnable
     * @param category given category
     * @param sortAble of type that shall be returned
     */
    public void addSortable(SortCategory category, SortAble<?> sortAble){
        sortAbleHashMap.put(category, sortAble);
    }

    /**
     * returns the date of the sortable of the given category.
     * will therefore run the Sortable that is associated with the category
     * @param category category of the wanted data
     * @return data object specified by category
     */
    public Object getSortable(SortCategory category){
        // prevent nullptr, if no entry at that position
        if (!sortAbleHashMap.containsKey(category)
                ||sortAbleHashMap.get(category)==null) {
            return null;
        }else {
            return sortAbleHashMap.get(category).getData();
        }
    }

}
