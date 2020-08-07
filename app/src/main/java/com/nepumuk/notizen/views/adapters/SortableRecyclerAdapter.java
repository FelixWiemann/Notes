package com.nepumuk.notizen.views.adapters;

import com.nepumuk.notizen.objects.StorageObject;
import com.nepumuk.notizen.objects.filtersort.FilterShowAll;
import com.nepumuk.notizen.objects.filtersort.ViewFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortableRecyclerAdapter<T extends StorageObject> extends BaseRecyclerAdapter<T> {

    private ArrayList<T> filteredOut;

    private ViewFilter<T> currentFilter;

    private Comparator<T> currentComparator;


    public SortableRecyclerAdapter(List<T> itemList, int SortOrder) {
        super(itemList, SortOrder);
        filteredOut = new ArrayList<>();
    }

    /**
     * filters the data based on the given filter
     * List view listening to the adapter will be updated afterwards.
     *
     * the filtered out objects will not be discarded, with the FilterShowAll, all could be shown again.
     * @param filter
     */
    public void filter(ViewFilter<T> filter){
        currentFilter = filter;
        filter();
    }

    /**
     * filter with the previously set filter in filter(ViewFilter)
     * if no filter is selected, FilterShowAll is applied
     */
    public void filter(){

        if (currentFilter == null){
            currentFilter = new FilterShowAll<>();
        }

        ArrayList<T> tmpShow = new ArrayList<>();
        ArrayList<T> tmpHide = new ArrayList<>();
        currentFilter.filter(filteredOut, tmpShow, tmpHide);
        currentFilter.filter(itemList, tmpShow, tmpHide);
        filteredOut = tmpHide;
        itemList = tmpShow;
        notifyDataSetChanged();
    }

    /**
     * clears the currently set filter
     */
    public void clearFilter(){
        currentFilter = null;
        filter();
    }


    /**
     * sort the data based on the currently set comparator
     */
    public void sort(){
        if (currentComparator == null) return;
        Collections.sort(itemList, currentComparator);
        notifyDataSetChanged();
    }

    /**
     * sort the data based on the given comparator.
     * this also sets it for future #SortableAdapter::sort() calls
     */
    public void sort(Comparator<T> sortBy){
        currentComparator = sortBy;
        sort();
    }


    @Override
    public void clear() {
        super.clear();
        filteredOut.clear();
    }

    @Override
    public ArrayList<T> getAllObjects() {
        ArrayList<T> allItems = new ArrayList<>(filteredOut);
        allItems.addAll(super.getAllObjects());
        return allItems;
    }

    @Override
    public void remove(T object) {
        super.remove(object);
        filteredOut.remove(object);
    }

    @Override
    public void replace(List<T> newList) {
        super.replace(newList);
        filter();
        sort();
    }
}
