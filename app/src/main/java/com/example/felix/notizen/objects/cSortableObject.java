package com.example.felix.notizen.objects;

import com.example.felix.notizen.objects.filtersort.SortAble;
import com.example.felix.notizen.objects.filtersort.SortCategory;

import java.util.HashMap;

/**
 * object which handles the sorting of data
 * <p>
 * you have a sort category and add an runnable to
 * get the value of that category for sorting
 * <p>
 * e.g.
 * <pre>{@code
 * addSortable(SortCatgory.Title, new SortAble<String>() {<p>
 *             public String getData() {<p>
 *                 return getTitle();
 *             }
 *         });
 * }</pre>
 * would return the title on call getSortable(Title);<p>
 * The return value to that may be used for sorting the given object in list objects
 */
/*
 * TODO add filtering
 * TODO should think about moving it below in inheritance hierarchy
 */
public class cSortableObject extends cJSONObject {

    /**
     * map to hold all sortables, needs to be private
     */
    private HashMap<SortCategory<?>, SortAble<?>> sortAbleHashMap = new HashMap<>();


    cSortableObject() {
        super();
    }

    /**
     * adds a sortable with given category and runnable
     * @param category given category
     * @param sortAble of type that shall be returned
     */
    protected <T> void addSortable(SortCategory<T> category, SortAble<T> sortAble){
        // having SortCategory and SortableType be the same,
        // we make sure that the return type is the correct one, when getting the sortable value
        sortAbleHashMap.put(category, sortAble);
    }

    /**
     * returns the value of the sortable of the given category.
     * will therefore run the Sortable that is associated with the category
     *
     * typed getSortable removes the need to cast every time getSortable is used
     *
     * @param category category of the wanted data
     * @return data object specified by category
     */
    public <T> T getSortable(SortCategory<T> category){
        // prevent nullptr, if no entry at that position
        if (!sortAbleHashMap.containsKey(category)
                ||sortAbleHashMap.get(category)==null) {
            return null;
        }else {
            // inspection suppressed -> setting it only via getter makes sure it is not happening.
            //noinspection unchecked
            return (T) sortAbleHashMap.get(category).getData();
        }
    }

}
