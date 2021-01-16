package com.nepumuk.notizen.core.views.adapters;

import android.view.View;

import com.nepumuk.notizen.core.views.SwipableView;


public interface OnSwipeableClickListener {
    void onClick(View clickedOn, SwipableView parentView);
}
