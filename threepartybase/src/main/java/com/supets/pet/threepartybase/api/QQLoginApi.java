package com.supets.pet.threepartybase.api;

import android.app.Activity;
import android.util.Log;

import com.supets.pet.threepartybase.model.QQToken;
import com.supets.pet.threepartybase.model.QQUserInfo;
import com.supets.pet.threepartybase.utils.LoginPlatForm;
import com.supets.pet.threepartybase.utils.OauthLoginListener;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

public class QQLoginApi {

	public static void getUserInfo(final Activity activity,
			final OauthLoginListener oauth, final QQToken token) {
		QQAuthApi.getTencent().setOpenId(token.getOpenid());
		QQAuthApi.getTencent().setAccessToken(token.getAccess_token(),
				token.getExpires_in() + "");

		UserInfo info = new UserInfo(activity, QQAuthApi.getTencent().getQQToken());
		info.getUserInfo(new IUiListener() {

			@Override
			public void onError(UiError arg0) {
				oauth.OauthLoginFail();
			}

			@Override
			public void onComplete(Object arg0) {
				try {
					JSONObject obj = (JSONObject) arg0;
					Log.v("qq-user", obj.toString());
					QQUserInfo info = new QQUserInfo();
					info.gender = obj.getString("gender");
					info.nickname = obj.getString("nickname");
					info.figureurl_qq_1 = obj.getString("figureurl_qq_1");
					info.figureurl_qq_2 = obj.getString("figureurl_qq_2");
					if (info != null && info.nickname != null) {
						token.authtype = LoginPlatForm.QQZONE_PLATPORM;
						info.authtype = LoginPlatForm.QQZONE_PLATPORM;
						oauth.OauthLoginSuccess(token, info);
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				oauth.OauthLoginFail();
			}

			@Override
			public void onCancel() {
				oauth.OauthLoginFail();
			}
		});
	}
}
