package com.yssj.utils;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;

/**
 * Helper for determining if the configuration has changed in an interesting
 * way so we need to rebuild the app list.
 */
public class InterestingConfigChanges {

	final Configuration mLastConfiguration = new Configuration();
    int mLastDensity;

    // (configChanges&(ActivityInfo.CONFIG_LOCALE
    // |ActivityInfo.CONFIG_UI_MODE|ActivityInfo.CONFIG_SCREEN_LAYOUT)) != 0
    // 的意思是 如果configChanges和他们不�?样的话，就说明改变了
    //
    public boolean applyNewConfig(Resources res) {
        int configChanges = mLastConfiguration.updateFrom(res.getConfiguration());
        boolean densityChanged = mLastDensity != res.getDisplayMetrics().densityDpi;
        if (densityChanged || (configChanges&(ActivityInfo.CONFIG_LOCALE
                |ActivityInfo.CONFIG_UI_MODE|ActivityInfo.CONFIG_SCREEN_LAYOUT)) != 0) {
            mLastDensity = res.getDisplayMetrics().densityDpi;
            return true;
        }
        return false;
    }
}
