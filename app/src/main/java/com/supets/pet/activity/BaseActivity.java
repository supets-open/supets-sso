package com.supets.pet.activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.supets.pet.threepartybase.api.WeiBoAuthApi;


public class BaseActivity extends FragmentActivity {

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		WeiBoAuthApi.onActivityResult(requestCode, resultCode, data);
	}

}
