package com.nepumuk.notizen.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * {@link Toolbar} that allows the injection of {@link NavigationUpInterceptor}s
 * <p></p>they will be called before the original {@link android.view.View.OnClickListener} set in {@link #setNavigationOnClickListener(OnClickListener)}
 * <p></p>this allows the {@link NavigationUpInterceptor} to intercept the Navigation-Up action and possibly prevent the up action from happening.
 * <p></p> if all attached {@link NavigationUpInterceptor} have accepted the Up Navigation, {@link androidx.navigation.Navigation} will be able to handle it.
 */
public class InterceptableNavigationToolbar extends Toolbar {
    public InterceptableNavigationToolbar(Context context) {
        super(context);
        init();
    }

    public InterceptableNavigationToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InterceptableNavigationToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    OnClickListenerManager manager;
    private void init(){
        manager = new OnClickListenerManager();
        // set the super NavigationOnClickListener to our manager, as all onNavigationClicks shall be handled by him
        super.setNavigationOnClickListener(manager);
    }

    /**
     * <p>add a {@link NavigationUpInterceptor} that will be consulted before the up-action will be handled by {@link androidx.navigation.Navigation}</p>
     * <p>ATTENTION: Interceptors MUST be removed, when it's lifecycle comes to an end. Otherwise there will be a memory leak!</p>
     * <p>e.g. if the interceptor is part of a fragment, add the interceptor when needed, but remove it latest in {@link Fragment#onStop()}, as then the fragment is not visible anymore</p>
     * @param interceptor to be added
     */
    public void addInterceptUpNavigationListener(NavigationUpInterceptor interceptor){
        manager.addInterceptor(interceptor);
    }

    /**
     * <p>remove a previously added {@link NavigationUpInterceptor}</p>
     * <p>ATTENTION: Interceptors MUST be removed, when it's lifecycle comes to an end. Otherwise there will be a memory leak!</p>
     * <p>e.g. if the interceptor is part of a fragment, add the interceptor when needed, but remove it latest in {@link Fragment#onStop()}, as then the fragment is not visible anymore</p>
     * @param interceptor to be removed
     */
    public void removeInterceptUpNavigationListener(NavigationUpInterceptor interceptor){
        manager.removeInterceptor(interceptor);
    }

    @Override
    public void setNavigationOnClickListener(OnClickListener listener) {
        // delegate the listener handling to our manager
        // but do NOT call the super.setNavigationOnClickListener
        manager.setListener(listener);
    }

    static class OnClickListenerManager implements  OnClickListener{
        private OnClickListener listener;
        private ArrayList<NavigationUpInterceptor> listeners = new ArrayList<>();

        public void setListener(OnClickListener listener){
            this.listener = listener;
        }

        public void addInterceptor(NavigationUpInterceptor interceptor){
            listeners.add(interceptor);
        }
        public void removeInterceptor(NavigationUpInterceptor interceptor){
            listeners.remove(interceptor);
        }

        @Override
        public void onClick(View v) {
            // ask all interceptors first if we can actually perform the up
            int size = listeners.size();
            for (int i = 0; i<size;i++){
                if (listeners.get(i).interceptUpNav(v)){
                    // if any interceptor decided we cannot, return
                    return;
                }
            }
            // no interceptors wanted to intercept, the listener added by Navigation can be called
            listener.onClick(v);
        }
    }

    /**
     * A NavigationUpInterceptor may be used to intercept any Up-Navigation attempts by the user
     */
    public interface NavigationUpInterceptor {
        /**
         * this will be called by any click actions on the Navigation-View of the toolbar.
         * the NavigationUpInterceptor may decide here, whether the up-action is actually allowed, or if other actions need to be taken first.
         * if the action is allowed, return false. if the action shall be aborted, return false
         * @param view that was clicked on
         * @return whether the up-action is allowed
         */
        boolean interceptUpNav(View view);
    }

}
