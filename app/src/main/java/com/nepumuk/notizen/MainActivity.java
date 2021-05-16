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

import com.nepumuk.notizen.core.objects.storable_factory.DefaultStorableStrategy;
import com.nepumuk.notizen.core.settings.Settings;
import com.nepumuk.notizen.core.toolbar.InterceptableNavigationToolbar;
import com.nepumuk.notizen.core.utils.ContextManager;
import com.nepumuk.notizen.core.utils.ContextManagerException;
import com.nepumuk.notizen.core.utils.MainViewModel;
import com.nepumuk.notizen.core.utils.db_access.DatabaseStorable;
import com.nepumuk.notizen.core.views.ToolbarProvider;
import com.nepumuk.notizen.tasks.objects.TaskNote;
import com.nepumuk.notizen.textnotes.objects.TextNote;

import java.util.Objects;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements ToolbarProvider {

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
        try {
            // Module setup
            // TODO module manifests, aab to detect which modules are installed and used
            //  check latest before creating image notes with camera!
            com.nepumuk.notizen.core.Main.initModule();
            com.nepumuk.notizen.tasks.Main.initModule();
            com.nepumuk.notizen.textnotes.Main.initModule();
        }catch (IllegalArgumentException ex){
            Log.e(TAG, "onCreate: modules already set up", ex);
        }
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

    @Override
    public InterceptableNavigationToolbar getToolbar() {
        return findViewById(R.id.toolbar);
    }

    public static class IntentHandler implements DefaultStorableStrategy<DatabaseStorable> {

        private Bundle extra;

        public IntentHandler(Bundle extra) {
            super();
            this.extra = extra;
        }

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
            if (MIME_TYPE == null) return null;
            if (MIME_TYPE.startsWith("text/")){
                return handleExtra(Objects.requireNonNull(intent.getExtras()));
            }
            // could not be handled, return default storable
            return null;
        }

        @Override
        public DatabaseStorable createDefault() {
            return handleExtra(extra);
        }
    }
}
