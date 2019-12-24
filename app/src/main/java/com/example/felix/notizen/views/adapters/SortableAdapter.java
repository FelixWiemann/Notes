package com.example.felix.notizen.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.objects.cStorageObject;
import com.example.felix.notizen.views.cNoteDisplayView;
import com.example.felix.notizen.views.cNoteDisplayViewFactory;
import com.example.felix.notizen.views.viewsort.FilterShowAll;
import com.example.felix.notizen.views.viewsort.ViewFilter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Adapter which allows sorting and filtering of it's content
 */
public class SortableAdapter extends BaseAdapter {
    private ArrayList<DatabaseStorable> displayed = new ArrayList<>();
    private ArrayList<DatabaseStorable> currentlyHidden = new ArrayList<>();
    private Comparator<DatabaseStorable> currentComparator;
    private ViewFilter currentFilter;

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return displayed.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public DatabaseStorable getItem(int position) {
        return displayed.get(position);
    }

    /**
     * Get the row id associated with the specified position in the displayed.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }


    /**
     * sort the data based on the currently set comparator
     */
    public void sort(){
        this.displayed.sort(currentComparator);
        this.notifyDataSetChanged();
    }

    /**
     * sort the data based on the given comparator.
     * this also sets it for future #SortableAdapter::sort() calls
     */
    public void sort(Comparator<DatabaseStorable> sortBy){
        currentComparator = sortBy;
        sort();
    }

    /**
     * filters the data based on the given filter
     * List view listening to the adapter will be updated afterwards.
     *
     * the filtered out objects will not be discarded, with the FilterShowAll, all could be shown again.
     * @param filter
     */
    public void filter(ViewFilter filter){
        currentFilter = filter;
        filter();
    }

    /**
     * filter with the previously set filter in filter(ViewFilter)
     * if no filter is selected, FilterShowAll is applied
     */
    public void filter(){

        if (currentFilter == null){
            currentFilter = new FilterShowAll();
        }

        ArrayList<DatabaseStorable> tempShow = new ArrayList<>();
        ArrayList<DatabaseStorable> tempHide = new ArrayList<>();

        for (DatabaseStorable storable: displayed) {
            if (currentFilter.filter(storable)){
                tempShow.add(storable);
            }else{
                tempHide.add(storable);
            }
        }
        for (DatabaseStorable storable:currentlyHidden){
            if (currentFilter.filter(storable)){
                tempShow.add(storable);
            }else{
                tempHide.add(storable);
            }
        }
        currentlyHidden = tempHide;
        displayed = tempShow;

        notifyDataSetChanged();
    }

    /**
     * clears the adapter from all it's content
     */
    public void clear(){
        clearFilter();
        clearSort();
        currentlyHidden.clear();
        displayed.clear();
        super.notifyDataSetChanged();
    }
    /**
     * adds all items from the list to the adapter
     * @param toAdd
     */
    public void addAll(List<DatabaseStorable> toAdd){
        displayed.addAll(toAdd);
        super.notifyDataSetChanged();
    }
    /**
     * add an object to the adapter to be displayed
     * @param toAdd
     */
    public void add(DatabaseStorable toAdd){
        displayed.add(toAdd);
        super.notifyDataSetChanged();
    }

    /**
     * clears the currently set filter
     */
    public void clearFilter(){
        currentFilter = null;
    }

    /**
     * clears the currently set sorting
     */
    public void clearSort(){
        currentComparator = null;
    }

    /**
     * returns a List of all objects currently stored in the adapter, also the hidden ones!     *
     * @return
     */
    public List<DatabaseStorable> getAllObjects(){
        ArrayList<DatabaseStorable> allObjects = new ArrayList<>();
        allObjects.addAll(displayed);
        allObjects.addAll(currentlyHidden);
        return allObjects;
    }

    /**
     * permanently removes a Storable from the adapter
     * @param object
     */
    public void remove(DatabaseStorable object){
        displayed.remove(object);
        currentlyHidden.remove(object);
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        cStorageObject objectToDisplay = (cStorageObject)getItem(position);
        // if view is already created, just update the data
        boolean createNewView = false;

        if(convertView == null){
            // if it is null, definitely create a new one
            createNewView = true;
        }else{
            if (convertView instanceof cNoteDisplayView){
                // if the objects are not the same, they might have been deleted, create a new view
                createNewView = !(((cNoteDisplayView)convertView).getContent().equals(objectToDisplay));
            }else  {
                // if it's not an Expandable view, create a new view
                createNewView = true;
            }
        }
        if (createNewView){
            return cNoteDisplayViewFactory.getView(parent.getContext(),objectToDisplay);
        }else {
            objectToDisplay.updateData();
            return convertView;
        }
    }

    /**
     * clears the adapter and sets the given list of items
     * @param list
     */
    public void replace(List <DatabaseStorable> list){
        clear();
        addAll(list);
        filter();
        sort();
    }
}
