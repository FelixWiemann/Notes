package com.example.felix.notizen.views.adapters;

import android.view.View;

import com.example.felix.notizen.views.SwipableView;

public interface OnSwipeableClickListener {
    void onClick(View clickedOn, SwipableView parentView);
}
