package com.nepumuk.notizen.core.utils;

import android.content.Context;
import android.content.pm.ShortcutManager;
import android.os.Build;

import com.nepumuk.notizen.core.objects.storable_factory.DefaultStorableStrategy;
import com.nepumuk.notizen.core.utils.db_access.DatabaseStorable;

import java.util.HashMap;

/**
 * helper class for handling shortcuts
 * it handles the minimum required version code internally
 * if calls where made to the shortcut helper even though the required API is not available, calls will be ignored
 */
public class ShortCutHelper {
    /**
     * shortcut id for creating a new text note
     */
    public static String ID_NEW_TEXT_NOTE = "new_text_note";
    /**
     * shortcut id for creating a new task note
     */
    public static String ID_NEW_TASK_NOTE = "new_task_note";

    private ShortcutManager shortcutManager;

    /**
     * creates a new shortcut helper form given context
     * @param context of which the shortcut helper shall be created from
     */
    public ShortCutHelper (Context context) {
        super();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            shortcutManager = context.getSystemService(ShortcutManager.class);
        }
    }

    /**
     * reports the usage of the shortcut with given ID
     * @param ID of shortcut whose usage shall be reported
     */
    public void reportUsage(String ID){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            shortcutManager.reportShortcutUsed(ID);
        }
    }

    static HashMap<String, DefaultStorableStrategy<DatabaseStorable>> strategyMap = new HashMap<>();
    static HashMap<String, String> shortCutIdMap = new HashMap<>();

    public DatabaseStorable createAndReportUsage(String ShortCutVariableName){
        if (strategyMap.containsKey(ShortCutVariableName)) {
            reportUsage(shortCutIdMap.get(ShortCutVariableName));
            return strategyMap.get(ShortCutVariableName).createDefault();
        }
        // if this exception comes, check if case-sensitive error
        // or argument was not added with registerShortcut
        throw new IllegalArgumentException("Cannot create DataBaseStorable of type " + ShortCutVariableName);
    }

    public static void registerShortcut(DefaultStorableStrategy strategy, String ShortCutID, String ShortCutVariableName){
        strategyMap.put(ShortCutVariableName,strategy);
        shortCutIdMap.put(ShortCutVariableName,ShortCutID);
    }

}
