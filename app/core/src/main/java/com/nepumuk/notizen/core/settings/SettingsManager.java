package com.nepumuk.notizen.core.settings;

import java.util.ArrayList;

public class SettingsManager {

    private static final SettingsManager mInstance = new SettingsManager();

    public static SettingsManager getInstance(){
        return mInstance;
    }

    private ArrayList<Setting> SettingsList = new ArrayList<>();

    private SettingsManager(){
        SettingsList.add(ThemeSetting.getInstance());
    }

    public void init(){
        for (Setting setting:SettingsList) {
            setting.apply();
        }
    }

}
