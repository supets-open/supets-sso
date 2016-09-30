package com.supets.pet.threepartybase.utils;

import android.content.Context;

public class ContextUtils {

    private static Context mApplication;

    public static void init(Context context) {
        mApplication = context;
    }

    public static Context getApplication() {
        return mApplication;
    }
}
