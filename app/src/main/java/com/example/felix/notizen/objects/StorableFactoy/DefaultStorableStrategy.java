package com.example.felix.notizen.objects.StorableFactoy;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;

/**
 * strategy that should handle the creation of an default DatabaseStorable
 * <p></p>
 * any implementation should hold a strategy to fall back on, when a new storable is needed but for
 * some reason could not created (e.g. if data is invalid, missing, corrupt, etc.)
 */
public interface DefaultStorableStrategy {

    /**
     * shall create a new default Storable
     * @return new default storable
     */
    DatabaseStorable createDefault();

}
