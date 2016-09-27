package com.supets.pet.threepartybase.storage;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.supets.pet.threepartybase.model.WeiBoToken;

public class WeiboPref extends BasePref {
	
	private static final String TOKEN = "token";
	private static final String UID = "uid";
	private static final String EXPIRE_TIME = "expire";

	private static final String Name="weibo_config";

	public static boolean isSinaWeiboAuthorized() {
		SharedPreferences preferences = getPref(Name);
		
		String token = preferences.getString(TOKEN, null);
		long expire = preferences.getLong(EXPIRE_TIME, -1);
		long now = System.currentTimeMillis();
		
		if (TextUtils.isEmpty(token) || now >= expire) {
			return false;
		}
	
		return true;
	}
	
	public static void saveAccessToken(WeiBoToken accessToken) {
		Editor editor = edit(Name);
		editor = editor.putString(TOKEN, accessToken.getAccess_token());
		editor = editor.putString(UID, accessToken.getUid());
		long expire = System.currentTimeMillis() + accessToken.getExpires_time() * 1000;
		editor = editor.putLong(EXPIRE_TIME, expire);
		editor.commit();
	}
	
	public static WeiBoToken readAccessToken() {
		SharedPreferences preferences = getPref(Name);

		String token = preferences.getString(TOKEN, null);
		String uid = preferences.getString(UID, null);

		WeiBoToken accessToken = new WeiBoToken();
		accessToken.setAccess_token(token);
		accessToken.setUid(uid);

		return accessToken;
	}

	public static Oauth2AccessToken readOauth2AccessToken() {
		SharedPreferences preferences = getPref(Name);

		String token = preferences.getString(TOKEN, null);
		String uid = preferences.getString(UID, null);

		Oauth2AccessToken accessToken = new Oauth2AccessToken();
		accessToken.setToken(token);
		accessToken.setUid(uid);

		return accessToken;
	}



	public static void clear() {
		BasePref.clear(Name);
	}

}
