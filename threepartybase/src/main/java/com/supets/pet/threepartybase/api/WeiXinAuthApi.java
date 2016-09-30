package com.supets.pet.threepartybase.api;


import android.content.Context;
import android.text.TextUtils;

import com.supets.pet.threepartybase.R;
import com.supets.pet.threepartybase.model.WeiXinToken;
import com.supets.pet.threepartybase.utils.ContextUtils;
import com.supets.pet.threepartybase.utils.OauthLoginListener;
import com.supets.pet.threepartybase.utils.ToastUtils;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import okhttp3.Call;

public class WeiXinAuthApi {

    private static IWXAPI mWXAPI;

    private static void registerToWeiXin(Context ctx) {
        mWXAPI = WXAPIFactory.createWXAPI(ctx, KeyAndSecrets.WEIXINAPPID, true);
        mWXAPI.registerApp(KeyAndSecrets.WEIXINAPPID);
    }

    public static IWXAPI getWXAPI() {
        if (mWXAPI == null) {
            registerToWeiXin(ContextUtils.getApplication());
        }
        return mWXAPI;
    }

    public static boolean isWXAPPInstalled() {
        if (getWXAPI() == null) {
            ToastUtils.showToastMessage(R.string.threepartybase_weixin_register_fail);
            return false;
        }
        return getWXAPI().isWXAppInstalled();
    }

    public static void auth() {
        login();
    }

    public static void login() {

        if (!isWXAPPInstalled()) {
            ToastUtils.showToastMessage(R.string.threepartybase_weixin_not_install_notify_login);
            return;
        }
        if (!mWXAPI.isWXAppSupportAPI()) {
            ToastUtils.showToastMessage(R.string.threepartybase_weixin_not_support_API);
            return;
        }
        //登录的第一步：请求链接code，请求成功后发送广播通知
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = KeyAndSecrets.WEIXIN_SCOPE;
        req.state = "none";
        req.openId = "";
        mWXAPI.sendReq(req);
    }


    public static void getOauthAcces(String code, final OauthLoginListener oauth) {
        String url = "https://api.weixin.qq.com/" + "sns/oauth2/access_token?";
        OkHttpUtils.get().url(url)
                .addParams("appid", KeyAndSecrets.WEIXINAPPID)
                .addParams("secret", KeyAndSecrets.WEIXIN_APPSECRET)
                .addParams("code", code)
                .addParams("grant_type", "authorization_code")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        oauth.OauthLoginFail();
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        WeiXinToken mWeiXinToken = JSonUtil.fromJson(s, WeiXinToken.class);
                        if (mWeiXinToken != null && !TextUtils.isEmpty(mWeiXinToken.access_token) && !TextUtils.isEmpty(mWeiXinToken.openid)) {
                            WeiXinLoginApi.getUserInfo(mWeiXinToken, oauth);
                        } else {
                            oauth.OauthLoginFail();
                        }
                    }
                });
    }

}
