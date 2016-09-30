package com.supets.pet.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.supets.pet.R;
import com.supets.pet.threepartybase.api.QQAuthApi;
import com.supets.pet.threepartybase.api.WeiBoAuthApi;
import com.supets.pet.threepartybase.api.WeiXinAuthApi;
import com.supets.pet.threepartybase.model.AuthToken;
import com.supets.pet.threepartybase.model.AuthUser;
import com.supets.pet.threepartybase.model.QQToken;
import com.supets.pet.threepartybase.model.QQUserInfo;
import com.supets.pet.threepartybase.model.WeiBoToken;
import com.supets.pet.threepartybase.model.WeiBoUserInfo;
import com.supets.pet.threepartybase.model.WeiXinToken;
import com.supets.pet.threepartybase.model.WeiXinUserInfo;
import com.supets.pet.threepartybase.utils.OauthListener;
import com.supets.pet.threepartybase.utils.OauthLoginListener;
import com.supets.pet.threepartybase.utils.ToastUtils;
import com.supets.pet.wxapi.WXEntryActivity;

public class ThreeAuthActivity extends BaseActivity implements
        OnClickListener, OauthLoginListener, OauthListener {
    private MyReceiver mReceiver;
    private TextView mProressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mProressbar = (TextView) findViewById(R.id.dialog_message);

        if (mReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(
                    mReceiver);
            mReceiver = null;
        }

        mReceiver = new MyReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,
                new IntentFilter(WXEntryActivity.ACTION_WX_LOGIN_SUCEESS));

        findViewById(R.id.qq).setOnClickListener(this);
        findViewById(R.id.weibo).setOnClickListener(this);
        findViewById(R.id.weixin).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        showProgressDialog();

        int id = view.getId();
        switch (id) {
            case R.id.qq:
                QQAuthApi.auth(this, this);
                break;
            case R.id.weibo:
                WeiBoAuthApi.auth(this, this);
                break;
            case R.id.weixin:
                WeiXinAuthApi.auth();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        mReceiver = null;
        super.onDestroy();
    }

    /**
     * WeiXin授权成功回调
     */
    @Override
    public void OauthLoginSuccess(AuthToken token, AuthUser user) {
        authsuccess(token, user);
    }

    private void authsuccess(AuthToken token, AuthUser user) {
        String uuid = "";//三方用户唯一ID
        if (token instanceof QQToken) {
            uuid = ((QQToken) token).getOpenid();
        }
        if (token instanceof WeiXinToken) {
            uuid = ((WeiXinUserInfo) user).getUnionid();
        }
        if (token instanceof WeiBoToken) {
            uuid = ((WeiBoToken) token).getUid();
        }
        mProressbar.setText("状态:授权成功\n" + "ID:" + uuid);
    }

    @Override
    public void OauthLoginFail() {
        mProressbar.setText(R.string.auth_fail);
    }

    /**
     * QQ，微博授权回调
     */
    @Override
    public void OauthSuccess(AuthToken token) {
        authsuccess(token, null);
    }

    @Override
    public void OauthFail() {
        ToastUtils.showToastMessage(R.string.auth_fail);
    }

    @Override
    public void OauthCancel() {
        ToastUtils.showToastMessage(R.string.auth_cancel);
    }

    /**
     * WeiXin授权广播回调
     */
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (WXEntryActivity.ACTION_WX_LOGIN_SUCEESS.equals(intent.getAction())) {
                String code = intent.getStringExtra("code");
                showProgressDialog();

                WeiXinAuthApi.getOauthAcces(code, ThreeAuthActivity.this);
            }
        }
    }

    private void showProgressDialog() {
        mProressbar.setText("正在授权");
        mProressbar.setVisibility(View.VISIBLE);
    }

}
