package com.supets.pet.threepartybase.utils;

import android.content.Context;

/**
 * ThreePartyPlatform
 *
 * @user lihongjiang
 * @description
 * @date 2016/9/7
 * @updatetime 2016/9/7
 */
public class ContextUtils {
    private static Context mApplication;

    public static void init(Context context) {
        mApplication = context;
    }

    public static Context getApplication() {
        return mApplication;
    }
}
