package com.supets.pet.threepartybase.utils;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

/**
 * ThreePartyPlatform
 *
 * @user lihongjiang
 * @description
 * @date 2016/9/7
 * @updatetime 2016/9/7
 */
public class ToastUtils {

    public static void showToastMessage(int resId){
        if (resId==0){
            return;
        }
        showToastMessage(ContextUtils.getApplication().getString(resId));
    }

    public static void showToastMessage(String toastString) {
        if (TextUtils.isEmpty(toastString)) {
            return;
        }

        Toast toast = Toast.makeText(ContextUtils.getApplication(), toastString, Toast.LENGTH_SHORT);
        toast.show();
    }


    public  static void showToastMessageCENTER(String toastString) {
        if (TextUtils.isEmpty(toastString)) {
            return;
        }
        Toast toast = Toast.makeText(ContextUtils.getApplication(), toastString,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
