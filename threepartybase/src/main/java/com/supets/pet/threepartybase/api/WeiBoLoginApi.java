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
import com.supets.pet.threepartybase.utils.LoginPlatForm;
import com.supets.pet.threepartybase.utils.OauthListener;
import com.supets.pet.threepartybase.utils.OauthLoginListener;

public class WeiBoLoginApi {

	public static void getUserInfo(final Activity activity,
								   final Oauth2AccessToken oauth2AccessToken,
								   final WeiBoToken token,
								   final OauthLoginListener oauth)  {
		new UsersAPI(activity, KeyAndSecrets.SINAWEIBO_APPKEY, oauth2AccessToken)
				.show(Long.parseLong(token.uid), new RequestListener() {
			@Override
			public void onWeiboException(WeiboException arg0) {
				oauth.OauthLoginFail();
			}
			
			@Override
			public void onComplete(String json) {
				Gson mGson = new Gson();
				WeiBoUserInfo info = mGson.fromJson(json, WeiBoUserInfo.class);
				if (info != null && info.idstr != null && info.name != null) {
					token.authtype = LoginPlatForm.WEIBO_PLATPORM;
					info.authtype = LoginPlatForm.WEIBO_PLATPORM;
					oauth.OauthLoginSuccess(token, info);
					return;
				}
			}
		});
	}
}
