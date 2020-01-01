package com.example.felix.notizen.views.viewsort;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;

import java.util.Objects;

public class FilterBasedOnClass extends ViewFilter {

    private Class clazz;

    public FilterBasedOnClass(Class clazz){
        this.clazz = clazz;
    }

    @Override
    public boolean filter(DatabaseStorable toFilter) {
        // make sure nothing is null & the given class in the constructor matches the class of the toFilter
        return Objects.requireNonNull(toFilter.getClass().getCanonicalName()).equalsIgnoreCase(clazz.getCanonicalName());
    }
}
