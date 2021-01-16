package com.nepumuk.notizen.core.utils;

import android.content.res.Resources;
import androidx.annotation.StringRes;

import com.nepumuk.notizen.core.utils.ContextManager;

/**
 * wrapper for Resources.getSystem()
 *
 * makes mocking for testing easier
 */
public class ResourceManger {

    /**
     * wrapper for {@link Resources#getString(int)}
     * @param resourceId string resource
     * @return string value
     */
    public static String getString(@StringRes int resourceId){
        return ContextManager.getInstance().getContext().getString(resourceId);
    }

}
