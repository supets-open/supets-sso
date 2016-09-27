package com.supets.pet;

import android.app.Application;

import com.supets.pet.threepartybase.utils.ContextUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.OkHttpClient;

public class MYApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		ContextUtils.init(this);
		OkHttpUtils.initClient(new OkHttpClient.Builder().build());
	}

}
