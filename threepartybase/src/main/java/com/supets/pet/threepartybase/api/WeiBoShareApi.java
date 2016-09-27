package com.supets.pet.threepartybase.api;

import android.app.Activity;
import android.graphics.Bitmap;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.supets.pet.threepartybase.model.AuthToken;
import com.supets.pet.threepartybase.model.WeiBoToken;
import com.supets.pet.threepartybase.storage.WeiboPref;
import com.supets.pet.threepartybase.utils.OauthListener;

public class WeiBoShareApi {

	public static void shareToWeibo(Activity activity, String content,  Bitmap bitmap, RequestListener listener) {
		Oauth2AccessToken accesstoken = WeiboPref.readOauth2AccessToken();
		StatusesAPI statusApi = new StatusesAPI(activity, KeyAndSecrets.SINAWEIBO_APPKEY,accesstoken);
		statusApi.upload(content, bitmap, null, null, listener);
	}

	public static void checkOauthValid(Activity activity,final OauthListener mOauth){
		if (WeiboPref.isSinaWeiboAuthorized()){
			mOauth.OauthSuccess(WeiboPref.readAccessToken());
		}else{
			WeiBoAuthApi.login(activity,new OauthListener() {
				@Override
				public void OauthSuccess(AuthToken token) {
					WeiboPref.saveAccessToken((WeiBoToken) token);
					mOauth.OauthSuccess(WeiboPref.readAccessToken());
				}

				@Override
				public void OauthFail() {
					mOauth.OauthFail();
				}

				@Override
				public void OauthCancel() {
					mOauth.OauthFail();
				}
			},null);
		}

	}
}
