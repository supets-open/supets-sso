package com.supets.pet.threepartybase.api;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.supets.pet.threepartybase.R;
import com.supets.pet.threepartybase.model.QQToken;
import com.supets.pet.threepartybase.utils.ContextUtils;
import com.supets.pet.threepartybase.utils.OauthListener;
import com.supets.pet.threepartybase.utils.OauthLoginListener;
import com.supets.pet.threepartybase.utils.ToastUtils;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * ThreePartyPlatform
 *
 * @user lihongjiang
 * @description
 * @date 2016/9/10
 * @updatetime 2016/9/10
 */
public class QQAuthApi {


    private static Tencent mTencent;

    protected static boolean isQQInstalled() {
        boolean result = false;
        PackageManager packageManager = ContextUtils.getApplication().getPackageManager();
        if (packageManager != null) {
            try {
                ApplicationInfo info = packageManager.getApplicationInfo("com.tencent.mobileqq",
                        PackageManager.GET_META_DATA);
                if (info != null) {
                    result = true;
                }
            } catch (PackageManager.NameNotFoundException e) {
                result = false;
            }
        }
        return result;
    }

    protected static Tencent getTencent() {
        if (mTencent == null) {
            mTencent = Tencent.createInstance(KeyAndSecrets.QQ_APPID, ContextUtils.getApplication());
        }
        return mTencent;
    }

    public static void login(final Activity activity,
                             final OauthListener listener, final OauthLoginListener oauth) {

        if (!isQQInstalled()) {
            ToastUtils.showToastMessage(R.string.QQ_not_install_notify);
            return;
        }

        if (getTencent() != null) {
            // 本地配置文件注销
            getTencent().logout(activity);
            getTencent().login(activity, KeyAndSecrets.QQ_SCOPE, new IUiListener() {

                @Override
                public void onError(UiError arg0) {
                    listener.OauthFail();
                }

                @Override
                public void onComplete(Object arg0) {

                    JSONObject obj = (JSONObject) arg0;
                    Log.v("qq:", obj.toString());
                    try {
                        QQToken token = new QQToken();
                        token.setAccess_token(obj.getString("access_token"));
                        token.setPay_token(obj.getString("pay_token"));
                        token.setOpenid(obj.getString("openid"));
                        token.setExpires_in(obj.getString("expires_in"));

                        if (token != null && token.getAccess_token() != null) {
                            listener.OauthSuccess(token);
                            if (oauth != null) {
                                QQLoginApi.getUserInfo(activity, oauth, token);
                            }
                        }
                    } catch (Exception e) {
                        listener.OauthFail();
                    }
                }

                @Override
                public void onCancel() {
                    listener.OauthCancel();
                }
            });
        }
    }

    public static void auth(Activity activity, OauthListener listener) {
        login(activity, listener,null);
    }
}
