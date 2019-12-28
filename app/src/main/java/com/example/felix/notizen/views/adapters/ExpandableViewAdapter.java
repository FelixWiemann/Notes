package com.example.felix.notizen.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.felix.notizen.objects.cStorageObject;
import com.example.felix.notizen.views.ExpandableView;
import com.example.felix.notizen.views.adapters.SortableAdapter;
import com.example.felix.notizen.views.cNoteDisplayViewFactory;

public class ExpandableViewAdapter extends SortableAdapter {
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
            if (convertView instanceof ExpandableView){
                // if the objects are not the same, they might have been deleted, create a new view
                createNewView = !(((ExpandableView)convertView).getObject().equals(objectToDisplay));
            }else  {
                // if it's not an Expandable view, create a new view
                createNewView = true;
            }
        }
        if (createNewView){
            // todo let view handling be done by sortable adapter
            return new ExpandableView(parent.getContext(), cNoteDisplayViewFactory.getView(parent.getContext(),objectToDisplay));
        }else {
            objectToDisplay.updateData();
            return convertView;
        }
    }

}
