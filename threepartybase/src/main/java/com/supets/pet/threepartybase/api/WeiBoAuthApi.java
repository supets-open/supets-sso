package com.supets.pet.threepartybase.api;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.supets.pet.threepartybase.model.WeiBoToken;
import com.supets.pet.threepartybase.model.WeiBoUserInfo;
import com.supets.pet.threepartybase.storage.WeiboPref;
import com.supets.pet.threepartybase.utils.LoginPlatForm;
import com.supets.pet.threepartybase.utils.OauthListener;
import com.supets.pet.threepartybase.utils.OauthLoginListener;

/**
 * ThreePartyPlatform
 *
 * @user lihongjiang
 * @description
 * @date 2016/9/10
 * @updatetime 2016/9/10
 */
public class WeiBoAuthApi {

    private static SsoHandler mSinaWeiboSsoHandler;

    public static void login(final Activity activity,
                             final OauthListener listener,
                             final OauthLoginListener oauth) {
        getSsoHandler(activity).authorize(new WeiboAuthListener() {

            @Override
            public void onWeiboException(WeiboException arg0) {
                listener.OauthFail();
            }

            @Override
            public void onComplete(Bundle values) {
                final Oauth2AccessToken accessToken = Oauth2AccessToken
                        .parseAccessToken(values);

                if (accessToken.isSessionValid()) {
                    final WeiBoToken token = new WeiBoToken();
                    token.access_token = accessToken.getToken();
                    token.uid = accessToken.getUid();
                    token.refresh_token = accessToken.getRefreshToken();
                    token.expires_time = accessToken.getExpiresTime();
                    // 正在为你登录，请稍候
                    listener.OauthSuccess(token);

                    if (oauth!=null){
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                WeiBoLoginApi.getUserInfo(activity,accessToken,token, oauth);
                            }
                        }).start();
                    }
                    Log.e("Login", "success");
                } else {
                    listener.OauthFail();
                }
            }

            @Override
            public void onCancel() {
                listener.OauthCancel();
            }

        });
    }

    public static SsoHandler getSsoHandler(Activity activity) {
        AuthInfo auth = new AuthInfo(activity,
                KeyAndSecrets.SINAWEIBO_APPKEY,
                KeyAndSecrets.SINAWEIBO_REDIRECT_URI,
                KeyAndSecrets.SINAWEIBO_SCOPE);
        mSinaWeiboSsoHandler = new SsoHandler(activity, auth);
        return mSinaWeiboSsoHandler;
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mSinaWeiboSsoHandler != null) {
            mSinaWeiboSsoHandler.authorizeCallBack(requestCode, resultCode, data);
            mSinaWeiboSsoHandler = null;
        }
    }

}
