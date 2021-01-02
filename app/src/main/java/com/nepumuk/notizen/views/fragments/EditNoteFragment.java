package com.nepumuk.notizen.views.fragments;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nepumuk.notizen.MainActivity;
import com.nepumuk.notizen.R;
import com.nepumuk.notizen.objects.StorageObject;
import com.nepumuk.notizen.objects.storable_factory.DefaultTaskNoteStrategy;
import com.nepumuk.notizen.objects.storable_factory.DefaultTextNoteStrategy;
import com.nepumuk.notizen.utils.ShortCutHelper;
import com.nepumuk.notizen.utils.db_access.DatabaseStorable;
import com.nepumuk.notizen.views.InterceptableNavigationToolbar;

import java.util.Objects;

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
    private String originalData;

    InterceptableNavigationToolbar toolbar;


    /**
     * state if the content has been changed
     */
    private boolean wasChanged = false;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This callback will only be called when MyFragment is at least Started.
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO better way to get the toolbar
        if (toolbar==null) {
            toolbar = ((InterceptableNavigationToolbar) requireActivity().findViewById(R.id.toolbar));
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
            DatabaseStorable data;
            // intents from shortcut
            switch (Objects.requireNonNull(EditNoteFragmentArgs.fromBundle(requireArguments()).getType())) {
                case "TaskNote":
                    data = DefaultTaskNoteStrategy.create();
                    // report shortcut usage
                    new ShortCutHelper(getContext()).reportUsage(ShortCutHelper.ID_NEW_TASK_NOTE);
                    break;
                case "TextNote":
                    data = DefaultTextNoteStrategy.create();
                    // report shortcut usage
                    new ShortCutHelper(getContext()).reportUsage(ShortCutHelper.ID_NEW_TEXT_NOTE);
                    break;
                default:
                    data = MainActivity.IntentHandler.handleExtra(requireArguments());
            }
            EditNoteViewModel.SaveState<DatabaseStorable> saveState = new EditNoteViewModel.SaveState<>(data);
            mViewModel.setNote(saveState);
            mViewModel.getSaveState().origin = EditNoteViewModel.SaveState.Origin.EDITOR;
        }
        originalData = mViewModel.getValue().getDataString();
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NoteDisplayFragment<StorageObject> headerFragment = new NoteDisplayHeaderFragment();
        // replace possible previously added fragments
        fragmentTransaction.replace(R.id.fragmentHeader, headerFragment);
        NoteDisplayFragment<DatabaseStorable> fragmentContent = NoteDisplayFragmentFactory.generateFragment(mViewModel.getValue());
        // replace possible previously added fragments
        fragmentTransaction.replace(R.id.fragmentHolder, fragmentContent);
        fragmentTransaction.commit();
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.edit_note_fragment_menu, menu);
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean interceptUpNav(View view) {
        return !saveDialogIfChanged();
    }
}
