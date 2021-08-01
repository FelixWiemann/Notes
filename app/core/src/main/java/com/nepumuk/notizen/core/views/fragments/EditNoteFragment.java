package com.nepumuk.notizen.core.views.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nepumuk.notizen.core.R;
import com.nepumuk.notizen.core.favourites.Favourite;
import com.nepumuk.notizen.core.objects.StorageObject;
import com.nepumuk.notizen.core.toolbar.InterceptableNavigationToolbar;
import com.nepumuk.notizen.core.utils.BackgroundWorker;
import com.nepumuk.notizen.core.utils.ResourceManger;
import com.nepumuk.notizen.core.utils.ShortCutHelper;
import com.nepumuk.notizen.core.utils.db_access.AppDataBaseHelper;
import com.nepumuk.notizen.core.utils.db_access.DatabaseStorable;
import com.nepumuk.notizen.core.views.SearchView;
import com.nepumuk.notizen.core.views.ToolbarProvider;

import org.jetbrains.annotations.NotNull;

public class EditNoteFragment extends Fragment implements SaveDataFragmentListener , FabProvider, InterceptableNavigationToolbar.NavigationUpInterceptor {


    private static final String TAG = "EditNoteFragment";
    /**
     * view model containing the data displayed in this activity
     */
    private EditNoteViewModel<DatabaseStorable> mViewModel;

    /**
     * original data that was sent to this activity,
     * stored as STRING to avoid the cloning thingy
     */
    private String originalData = "";

    InterceptableNavigationToolbar toolbar;

    /**
     * state if the content has been changed
     */
    private boolean wasChanged = false;

    private boolean isFav = false;

    private final String BUNDLE_WAS_CHANGED = "BUNDLE_WAS_CHANGED";
    private final String BUNDLE_ORIGINAL_DATA = "BUNDLE_ORIGINAL_DATA";

    OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
        @Override
        public void handleOnBackPressed() {
            if (!saveDialogIfChanged()) {
                return;
            }
            exit();
        }
    };

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState!=null) {
            outState.putBoolean(BUNDLE_WAS_CHANGED, wasChanged);
            outState.putString(BUNDLE_ORIGINAL_DATA,originalData);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This callback will only be called when MyFragment is at least Started.
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        if (savedInstanceState!=null) {
            wasChanged = savedInstanceState.getBoolean(BUNDLE_WAS_CHANGED, false);
            originalData = savedInstanceState.getString(BUNDLE_ORIGINAL_DATA, "");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO better way to get the toolbar
        if (toolbar==null) {
            toolbar = ((ToolbarProvider) requireActivity()).getToolbar();
        }
        toolbar.addInterceptUpNavigationListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        toolbar.removeInterceptUpNavigationListener(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content =  inflater.inflate(R.layout.fragment_edit_note, container, false);
        initFragment(content);
        setHasOptionsMenu(true);
        return content;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();

    }

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getChildFragmentManager().addFragmentOnAttachListener((fragmentManager, fragment) -> {
            if(fragment instanceof RequiresFabFragment) {
                ((RequiresFabFragment) fragment).registerFabProvider(this);
            }
        });
    }

    public void discard() {
        mViewModel.getSaveState().save = false;
        mViewModel.update();
    }

    /**
     * saves the data and exits the activity to the caller
     * setting the result of the currently stored data in the view model
     */
    private void save(){
        mViewModel.getSaveState().save = true;
        mViewModel.update();
    }

    private void exit(){
        // Handle the back button event
        Navigation.findNavController(requireView()).navigateUp();
        // hide keyboard
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        originalData = "";
    }

    @Override
    public void saveAndExit() {
        save();
        exit();
    }

    @Override
    public void cancelExit() {
        // do nothing, as cancel was called
    }

    @Override
    public void discardAndExit() {
        discard();
        exit();
    }

    FloatingActionButton fabToBeProvided;

    @Override
    public FloatingActionButton getFab() {
        return fabToBeProvided;
    }

    /**
     * loads the data from the intent and initializes the view model
     */
    private void loadData(){

        mViewModel = new ViewModelProvider(requireActivity()).get(EditNoteViewModel.class);
        mViewModel.observe(this, o -> wasChanged = true);

        if (!mViewModel.isValueSet()) {
            DatabaseStorable data = new ShortCutHelper(getContext()).createAndReportUsage(EditNoteFragmentArgs.fromBundle(requireArguments()).getType());
            EditNoteViewModel.SaveState<DatabaseStorable> saveState = new EditNoteViewModel.SaveState<>(data);
            // using replace to make sure we have a value immediately after setting
            mViewModel.replace(saveState);
            mViewModel.getSaveState().origin = EditNoteViewModel.SaveState.Origin.EDITOR;
        }
        if ("".equals(originalData)) {
            originalData = mViewModel.getValue().getDataString();
        }
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NoteDisplayFragment<StorageObject> headerFragment = new NoteDisplayHeaderFragment();
        // replace possible previously added fragments
        fragmentTransaction.replace(R.id.fragmentHeader, headerFragment);
        NoteDisplayFragment<DatabaseStorable> fragmentContent = NoteDisplayFragmentFactory.generateFragment(mViewModel.getValue());
        // replace possible previously added fragments
        fragmentTransaction.replace(R.id.fragmentHolder, fragmentContent);
        fragmentTransaction.commit();

        new BackgroundWorker(()-> {
            isFav = AppDataBaseHelper.getFavouriteDao().findFavourite(mViewModel.getValue().getId()) != null;
            changeFavouriteIcon(isFav);
        }).start();
    }

    /**
     * init the fragment with the created content view
     * @param content already created content view
     */
    private void initFragment(@NonNull View content){
        // setup fragments
        if (fabToBeProvided == null){
            fabToBeProvided = content.findViewById(R.id.provided_fab);
            fabToBeProvided.hide();
        }
    }

    /**
     * returns false, if user has not made a decision yet
     * @return true if user wants to change
     */
    public boolean saveDialogIfChanged(){
        // if it was changed and the original deviates from the currently stored values
        if (wasChanged && !originalData.equals(mViewModel.getValue().getDataString())){
            // ask for save
            SaveDataFragment fragment = new SaveDataFragment();
            fragment.setListener(this);
            fragment.show(getChildFragmentManager(),TAG);
            // and do nothing until user has made his choice
            return false;
        }
        return true;
    }

    public EditNoteViewModel<DatabaseStorable> getModel(){
        return mViewModel;
    }

    Menu menu;

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.edit_note_fragment_menu, menu);
        this.menu = menu;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.mnu_edit_note_cancel) {
            discard();
            exit();
            return true;
        } else if (itemId == R.id.mnu_edit_note_save) {
            save();
            exit();
            return true;
        } else if (itemId == R.id.mnu_edit_note_fav){
            new BackgroundWorker(()-> {
                if (isFav) {
                    AppDataBaseHelper.getFavouriteDao().delete(new Favourite(mViewModel.getValue().getId()));
                    isFav = false;
                } else {
                    AppDataBaseHelper.getFavouriteDao().createOrUpdate(new Favourite(mViewModel.getValue().getId()));
                    isFav = true;
                }
                changeFavouriteIcon(isFav);
            }).start();
            return true;
        }

        return NavigationUI.onNavDestinationSelected(item, Navigation.findNavController(requireView())) || super.onOptionsItemSelected(item);
    }

    public void changeFavouriteIcon(boolean isFav){
        new BackgroundWorker(true,() -> {
            if (menu != null) {
                MenuItem item = menu.findItem(R.id.mnu_edit_note_fav);
                if (item != null) {
                    if (isFav){
                        item.setIcon(R.drawable.ic_favorite);
                    }else {
                        item.setIcon(R.drawable.ic_favorite_border);
                    }
                }
            }
        }).start();
    }

    private boolean SeachFieldVisible = false;
    private SearchView.SearchWatcher watcher;
    private String SearchHintText="";

    public void setSearchVisible(boolean visible){
        SeachFieldVisible = visible;
    }
    public void setSearchVisible(boolean visible, SearchView.SearchWatcher watcher, @StringRes int SearchHintText){
        setSearchVisible(visible, watcher);
        this.SearchHintText= ResourceManger.getString(SearchHintText);
    }
    public void setSearchVisible(boolean visible, SearchView.SearchWatcher watcher){
        SeachFieldVisible = visible;
        this.watcher = watcher;
    }

    /**
     * Prepare the Fragment host's standard options menu to be displayed.  This is
     * called right before the menu is shown, every time it is shown.  You can
     * use this method to efficiently enable/disable items or otherwise
     * dynamically modify the contents.  See
     * {@link Activity#onPrepareOptionsMenu(Menu) Activity.onPrepareOptionsMenu}
     * for more information.
     *
     * @param menu The options menu as last shown or first initialized by
     *             onCreateOptionsMenu().
     * @see #setHasOptionsMenu
     * @see #onCreateOptionsMenu
     */
    @Override
    public void onPrepareOptionsMenu(@NonNull @NotNull Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        if (searchItem!=null) {
            searchItem.setVisible(SeachFieldVisible);
            ((SearchView) searchItem.getActionView()).watcher = watcher;
            if (!SearchHintText.equals("")) {
                ((SearchView) searchItem.getActionView()).setQueryHint(SearchHintText);
            }
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean interceptUpNav(View view) {
        return !saveDialogIfChanged();
    }
}
