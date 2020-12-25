package com.nepumuk.notizen.objects.storable_factory;

import com.nepumuk.notizen.utils.db_access.DatabaseStorable;

/**
 * strategy that should handle the creation of an default DatabaseStorable
 * <p></p>
 * any implementation should hold a strategy to fall back on, when a new storable is needed but for
 * some reason could not created (e.g. if data is invalid, missing, corrupt, etc.)
 */
public interface DefaultStorableStrategy<T extends DatabaseStorable> {

    /**
     * default title to be used in the different strategies
     */
    String DEFAULT_TITLE = "";

    /**
     * shall create a new default Storable
     * @return new default storable
     */
    T createDefault();

}
