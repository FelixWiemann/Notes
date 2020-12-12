package com.nepumuk.notizen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.nepumuk.notizen.objects.notes.TaskNote;
import com.nepumuk.notizen.objects.notes.TextNote;
import com.nepumuk.notizen.objects.storable_factory.StorableFactory;
import com.nepumuk.notizen.settings.Settings;
import com.nepumuk.notizen.settings.SettingsFragment;
import com.nepumuk.notizen.utils.ContextManager;
import com.nepumuk.notizen.utils.ContextManagerException;
import com.nepumuk.notizen.utils.MainViewModel;
import com.nepumuk.notizen.utils.db_access.DatabaseStorable;
import com.nepumuk.notizen.views.fragments.MainFragment;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAINACTIVITY";
    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            ContextManager.getInstance().setUp(this.getApplicationContext());
        } catch (ContextManagerException e) {
            Log.e(TAG, "onCreate: error during context setup", e);
        }
        // create settings
        SettingsFragment settingsFragment = new SettingsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_view_content,settingsFragment).commit();

        // setup main fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainFragment mainFragment = new MainFragment();
        fragmentTransaction.add(R.id.main_fragment_placeholder, mainFragment);
        fragmentTransaction.commit();

        model = new ViewModelProvider(this).get(MainViewModel.class);
        setContentView(R.layout.activity_main);
        DatabaseStorable fromIntent = IntentHandler.StorableFromIntent(getIntent());
        if (fromIntent != null) mainFragment.callEditNoteActivityForResult(fromIntent);
    }


    @Override
    protected void onStop() {
        super.onStop();
        // TODO update view model data on click
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            model.getData().getValue().values().forEach(storable -> {
                if (storable instanceof TaskNote) model.updateData(storable);
            });
        }
        Settings.unregisterOnSharedPreferenceListeners(this);
    }

    private static class IntentHandler{
        static DatabaseStorable StorableFromIntent(Intent intent){
            if (intent == null || intent.getAction()==null) return null;
            switch (intent.getAction()){
                case Intent.ACTION_SEND:
                    return handleMimeType(intent);
                default:
                    // intent doesn't have data
                    return null;
            }
        }

        private static DatabaseStorable handleMimeType(Intent intent){
            final String MIME_TYPE = intent.getType();
            // could not determine Mime-Type
            if (MIME_TYPE == null) return StorableFactory.getDefaultStorable();
            if (MIME_TYPE.startsWith("text/")){
                String text = intent.hasExtra(Intent.EXTRA_TEXT) ? intent.getStringExtra(Intent.EXTRA_TEXT) : "retrieving data failed";
                // empty default title for easier title manipulation
                String title = intent.hasExtra(Intent.EXTRA_TITLE) ? intent.getStringExtra(Intent.EXTRA_TITLE) : "";
                return new TextNote(UUID.randomUUID(), title, text);
            }
            // could not be handled, return default storable
            return StorableFactory.getDefaultStorable();
        }
    }
}
