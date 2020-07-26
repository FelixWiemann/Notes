package com.nepumuk.notizen.views.fragments;

import android.support.design.widget.FloatingActionButton;

/**
 *
 */
// TODO is there a better possibility to interact with a FAB from a Fragment? Is it even good design to do this?
public interface FabProvider {
    FloatingActionButton getFab();
}
