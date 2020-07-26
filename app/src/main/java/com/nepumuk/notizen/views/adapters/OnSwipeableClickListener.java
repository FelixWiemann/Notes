package com.nepumuk.notizen.views.adapters;

import android.view.View;

import com.nepumuk.notizen.views.SwipableView;

public interface OnSwipeableClickListener {
    void onClick(View clickedOn, SwipableView parentView);
}
