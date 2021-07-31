package com.nepumuk.notizen.core.settings;

import com.nepumuk.notizen.core.utils.ContextManager;

public abstract class Setting {

    private final String SettingsKey;
    private final String SettingsDefaultValue;

    public Setting(String SettingKey, String defaultValue) {
        super();
        this.SettingsKey = SettingKey;
        this.SettingsDefaultValue = defaultValue;
    }

    public boolean apply(){
        String value = Settings.Get(ContextManager.getInstance().getContext(), SettingsKey, SettingsDefaultValue);
        return apply(value);
    }

    /**
     * apply the new settings value
     * @param newValue new value of the setting
     */
    abstract boolean apply(String newValue);
}
