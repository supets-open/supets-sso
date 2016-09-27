package com.supets.pet.threepartybase.api;

import com.supets.pet.threepartybase.model.WeiXinToken;
import com.supets.pet.threepartybase.model.WeiXinUserInfo;
import com.supets.pet.threepartybase.utils.LoginPlatForm;
import com.supets.pet.threepartybase.utils.OauthLoginListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class WeiXinLoginApi {

    public static void getUserInfo(final WeiXinToken token,
                                   final OauthLoginListener oauth) {//获取用户信息
        String url = "https://api.weixin.qq.com/sns/userinfo?";

        OkHttpUtils.get().url(url)
                .addParams("openid", token.openid)
                .addParams("access_token", token.access_token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        oauth.OauthLoginFail();
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        WeiXinUserInfo user = JSonUtil.fromJson(s, WeiXinUserInfo.class);
                        if (user != null && user.unionid != null && user.openid != null) {
                            user.authtype = LoginPlatForm.WECHAT_PLATPORM;
                            token.authtype = LoginPlatForm.WECHAT_PLATPORM;
                            oauth.OauthLoginSuccess(token, user);
                        }else{
                            oauth.OauthLoginFail();
                        }
                    }
                });
    }

}
