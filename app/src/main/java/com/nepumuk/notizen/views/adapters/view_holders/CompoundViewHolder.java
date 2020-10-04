package com.nepumuk.notizen.views.adapters.view_holders;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.objects.StorageObject;
import com.nepumuk.notizen.views.note_views.NoteDisplayView;

import java.util.HashMap;

public class CompoundViewHolder<T extends StorageObject> extends ViewHolderInterface<T> {

    /**
     * expansion state of an Expandable View
     */
    enum ExpandState{
        /**
         * is expanded, State = 1
         */
        EXPANDED(1),
        /**
         * is shrinked, State = 0
         */
        SHRINKED(0),
        /**
         * is just inflated, hasn't changed the state yet.
         * State = -1
         */
        FIRSTINFLATE(-1);

        /**
         * state of the enum value
         * 1 = expanded
         * 0 = shrinked
         * -1 =  first time inflated
         */
        public final int State;
        ExpandState(int state){
            State = state;
        }
    }

    private final int aSizeUnExpanded = 200;
    private final int aSizeExpanded = 650;


    ExpandState currentState = ExpandState.FIRSTINFLATE;

    private final HashMap<Class,ViewHolderInterface<T>> interfaces;

    private final Button expandButton;


    public CompoundViewHolder(@NonNull View itemView) {
        super(itemView);
        interfaces = new HashMap<>();
        expandButton = itemView.findViewById(R.id.expand_button);
        expandButton.setOnClickListener(view -> invertShrink());
    }

    @Override
    public void bind(T toBind) {
        for (ViewHolderInterface<T> vhf: interfaces.values()) {
            vhf.bind(toBind);
        }
        currentState = ExpandState.FIRSTINFLATE;
        invertShrink();
    }

    public void addViewHolderInterface(ViewHolderInterface<T> viewHolderInterface, Class clazz){
        interfaces.put(clazz, viewHolderInterface);
    }

    public ViewHolderInterface<T> getViewHolder(Class clazz){
        return interfaces.get(clazz);
    }

    public ViewHolderInterface[] getViewHolders(float dX){
        ViewHolderInterface[] array = new ViewHolderInterface[interfaces.size()];
        for (int i = 0; i < array.length; i++) {
            if (interfaces.values().toArray()[i] instanceof SwipableViewHolder) {
                SwipableViewHolder swipableHolder = (SwipableViewHolder) interfaces.values().toArray()[i];
                swipableHolder.setBackgroundVisibility(dX);
                array[i] = swipableHolder.viewHolderInterface;
            }else {
                array[i] = (ViewHolderInterface<T>) interfaces.values().toArray()[i];
            }
        }
        return array;
    }

    /**
     * inverts the shrinking state
     */
    private void invertShrink(){
        if (currentState== ExpandState.FIRSTINFLATE){
            currentState = ExpandState.EXPANDED;
        }
        if (currentState == ExpandState.EXPANDED){
            setHeight(aSizeUnExpanded);
            currentState = ExpandState.SHRINKED;
            if (itemView instanceof NoteDisplayView){
                ((NoteDisplayView) itemView).onShrink();
            }
        }else {
            setHeight(aSizeExpanded);
            // TODO get expand size of child and limit it to max size depending on Screen size
            currentState = ExpandState.EXPANDED;
            if (itemView instanceof NoteDisplayView){
                ((NoteDisplayView) itemView).onExpand();
            }
        }
        // TODO animate
        expandButton.setRotationX((currentState.State*180)%360);
    }

    private void setHeight(int newHeight){
        itemView.getLayoutParams().height = newHeight;
        itemView.requestLayout();
    }

}
