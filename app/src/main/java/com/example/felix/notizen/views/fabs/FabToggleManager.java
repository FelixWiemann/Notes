package com.example.felix.notizen.views.fabs;

import android.support.design.widget.FloatingActionButton;

import java.util.ArrayList;

public class FabToggleManager {

    /**
     * list of FABs to be toggled
     */
    private ArrayList<FloatingActionButton> listToBeSpawned;

    /**
     * current state of the FAB toggling process
     */
    private boolean hasSpawned = false;

    public FabToggleManager(){
        init();
    }

    private void init(){
        listToBeSpawned = new ArrayList<>();
    }

    /**
     * toggling the FABs visibility depending on current state
     */
    public void toggle(){
        if (hasSpawned) {
            for (FloatingActionButton fab : listToBeSpawned) {
                fab.hide();
            }
        }else{
            for (FloatingActionButton fab : listToBeSpawned) {
                fab.show();
            }
        }
        // invert
        hasSpawned = !hasSpawned;
    }

    /**
     * adds a FAB to the toggling procedure
     * and immediately sets it according to the current toggle state
     * @param fab to be added
     */
    public void addFabToToggle(FloatingActionButton fab){
        listToBeSpawned.add(fab);
        if (hasSpawned) {
            fab.show();
        }else {
            fab.hide();
        }
    }

    /**
     * removes a FAB from the list of FABs to spawn
     * @param fab to be added
     */
    public void removeFabToToggle(FloatingActionButton fab){
        listToBeSpawned.remove(fab);
    }

}
