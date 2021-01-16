package com.nepumuk.notizen.core.views.fragments;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 *
 */
// TODO is there a better possibility to interact with a FAB from a Fragment? Is it even good design to do this?
public interface FabProvider {
    FloatingActionButton getFab();
}
