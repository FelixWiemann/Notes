package com.nepumuk.notizen.settings;

import java.util.Optional;

public enum SettingKey{
    LOG_LOCATION(String.class,null),
    LOGS_TO_KEEP(Integer.class,5),
    LOG_ENTRIES_BEFORE_FLUSH(Integer.class,1),
    JSON_LOCATION(String.class,null),
    APP_DEBUG_LEVEL(Integer.class,1);

    private Class<?> clazz;
    private Optional defaultValue;

    SettingKey(Class<?> clazz, Object defaultValue ){
        this.clazz = clazz;
        this.defaultValue = Optional.ofNullable(defaultValue);
    }

    @Override
    public String toString(){
        return "SETTING_" + this.name();
    }

    public Optional getDefaultValue(){
        return defaultValue;
    }

}