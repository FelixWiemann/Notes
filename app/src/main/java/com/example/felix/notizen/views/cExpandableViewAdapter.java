package com.example.felix.notizen.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.objects.cStorageObject;
import com.example.felix.notizen.views.viewsort.ViewFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felix on 21.06.2018.
 */

public class cExpandableViewAdapter extends BaseAdapter {

    private ArrayList<DatabaseStorable> displayed = new ArrayList<>();

    private ArrayList<DatabaseStorable> currentlyHidden = new ArrayList<>();

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return displayed.size();
        //return list.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return displayed.get(position);
        //return list.get(position);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        cStorageObject objectToDisplay = (cStorageObject)getItem(position);
        // if view is already created, just update the data
        if(convertView == null || currentlyHidden.contains((DatabaseStorable) getItem(position))) {
            // otherwise create a new view and return that
            return new ExpandableView(parent.getContext(),objectToDisplay);
        }else {
            objectToDisplay.updateData();
            return (ExpandableView) getItem(position);
        }
    }

    /**
     * permanently removes a Storable from the adapter
     * @param object
     */
    public void remove(DatabaseStorable object){
        displayed.remove(object);
    }

    /**
     * add an object to the adapter to be displayed
     * @param object
     */
    public void add(DatabaseStorable object){
        displayed.add(object);
        super.notifyDataSetChanged();
    }

    /**
     * adds all items from the list to the adapter
     * @param list
     */
    public void add(List<DatabaseStorable> list){
        displayed.addAll(list);
        super.notifyDataSetChanged();
    }

    /**
     * returns a List of all objects currently stored in the adapter, also the hidden ones!     *
     * @return
     */
    public ArrayList<DatabaseStorable> getAllObjects(){
        ArrayList<DatabaseStorable> allObjects = new ArrayList<>();
        allObjects.addAll(displayed);
        allObjects.addAll(currentlyHidden);
        return allObjects;
    }


    /**
     *
     */
    public void sort(){
        super.notifyDataSetChanged();
    }

    /**
     * filters the data based on the given filter
     * List view listening to the adapter will be updated afterwards.
     *
     * the filtered out objects will not be discarded, with the FilterShowAll, all could be shown again.
     * @param filter
     */
    public void filter(ViewFilter filter){
        ArrayList <DatabaseStorable> tempShow = new ArrayList<>();
        ArrayList <DatabaseStorable> tempHide = new ArrayList<>();

        for (DatabaseStorable storable: displayed) {
            if (filter.filter(storable)){
                tempShow.add(storable);
            }else{
                tempHide.add(storable);
            }
        }
        for (DatabaseStorable storable:currentlyHidden){
            if (filter.filter(storable)){
                tempShow.add(storable);
            }else{
                tempHide.add(storable);
            }
        }
        currentlyHidden = tempHide;
        displayed = tempShow;

        super.notifyDataSetChanged();
    }

}
