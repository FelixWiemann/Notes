package com.nepumuk.notizen;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.nepumuk.notizen.objects.notes.TaskNote;
import com.nepumuk.notizen.objects.notes.TextNote;
import com.nepumuk.notizen.objects.storable_factory.StorableFactory;
import com.nepumuk.notizen.settings.Settings;
import com.nepumuk.notizen.utils.ContextManager;
import com.nepumuk.notizen.utils.ContextManagerException;
import com.nepumuk.notizen.utils.MainViewModel;
import com.nepumuk.notizen.utils.db_access.DatabaseStorable;

import java.util.Objects;
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
        setContentView(R.layout.activity_main);
        model = new ViewModelProvider(this).get(MainViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavHostFragment navHost = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_nav_host);
        NavHostController navController = (NavHostController) navHost.getNavController();
        //navController.enableOnBackPressed(true);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph())
                        .setOpenableLayout(findViewById(R.id.drawerLayout))
                        .build();


        NavigationUI.setupWithNavController(toolbar, navController,findViewById(R.id.drawerLayout));
        NavigationUI.setupActionBarWithNavController(this, navController,appBarConfiguration);

        DatabaseStorable fromIntent = IntentHandler.StorableFromIntent(getIntent());
        if (fromIntent != null) {
            try {
                navController.createDeepLink().setDestination(R.id.editNoteFragment).setArguments(getIntent().getExtras()).createPendingIntent().send();
            } catch (PendingIntent.CanceledException e) {
                Log.e(TAG, "onCreate: could not navigate to intent", e);
            }
        }
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

    public static class IntentHandler{
        public static DatabaseStorable StorableFromIntent(Intent intent){
            if (intent == null || intent.getAction()==null) return null;
            switch (intent.getAction()){
                case Intent.ACTION_SEND:
                    return handleMimeType(intent);
                default:
                    // intent doesn't have data
                    return null;
            }
        }

        public static DatabaseStorable handleExtra(Bundle extra){
            String text = extra.containsKey(Intent.EXTRA_TEXT) ? extra.getString(Intent.EXTRA_TEXT) : "retrieving data failed";
            // empty default title for easier title manipulation
            String title = extra.containsKey(Intent.EXTRA_TITLE) ? extra.getString(Intent.EXTRA_TITLE) : "";
            return new TextNote(UUID.randomUUID(), title, text);
        }

        private static DatabaseStorable handleMimeType(Intent intent){
            final String MIME_TYPE = intent.getType();
            // could not determine Mime-Type
            if (MIME_TYPE == null) return StorableFactory.getDefaultStorable();
            if (MIME_TYPE.startsWith("text/")){
                handleExtra(Objects.requireNonNull(intent.getExtras()));
            }
            // could not be handled, return default storable
            return StorableFactory.getDefaultStorable();
        }
    }
}
