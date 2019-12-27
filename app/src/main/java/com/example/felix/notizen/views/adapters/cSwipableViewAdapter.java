package com.example.felix.notizen.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.felix.notizen.views.ExpandableView;
import com.example.felix.notizen.views.ExpandableViewAdapter;
import com.example.felix.notizen.views.OnListItemInPositionClickListener;
import com.example.felix.notizen.views.SwipableView;

/**
 * Created by Felix on 21.06.2018.
 */
public class cSwipableViewAdapter extends ExpandableViewAdapter {

    public OnListItemInPositionClickListener onClickListenerLeft = null;
    public OnListItemInPositionClickListener onClickListenerRight = null;

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
        ExpandableView v;
        SwipableView newView;
        if (convertView instanceof SwipableView) {
            // let the lower adapter handle creation of the main view
            newView = (SwipableView) convertView;
            newView.MainView = super.getView(position, ((SwipableView) convertView).MainView, parent);
            // make sure the data is up to date
            ((ExpandableView) newView.MainView).getObject().updateData();
        }else {
            // did not get a swipable, create one with from the view created by super
            v = (ExpandableView) super.getView(position, convertView, parent);
            newView = new SwipableView(parent.getContext());
            // otherwise create a new view and return that
            newView.setMainView(v);
        }

        if (!newView.hasOnClickListeners()){
            // set on click for deletion
            newView.setOnClickListeners(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListenerLeft!=null){
                        onClickListenerLeft.onClick(position);
                    }
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListenerRight!=null){
                        onClickListenerRight.onClick(position);
                    }
                }
            });
        }
        return newView;
    }
}
