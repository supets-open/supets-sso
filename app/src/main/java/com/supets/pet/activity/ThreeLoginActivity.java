package com.supets.pet.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

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
import com.supets.pet.threepartybase.model.WeiXinUserInfo;
import com.supets.pet.threepartybase.utils.LoginPlatForm;
import com.supets.pet.threepartybase.utils.OauthListener;
import com.supets.pet.threepartybase.utils.OauthLoginListener;
import com.supets.pet.wxapi.WXEntryActivity;


public class ThreeLoginActivity extends BaseActivity implements
        OnClickListener {
    private MyReceiver mReceiver;
    private TextView mProressbar;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        mProressbar.setVisibility(View.GONE);

        int id = view.getId();
        switch (id) {
            case R.id.qq:
                QQAuthApi.login(this, oauth, oauthlogin);
                break;
            case R.id.weibo:
                WeiBoAuthApi.login(this, oauth, oauthlogin);
                break;
            case R.id.weixin:
                WeiXinAuthApi.login();
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
     * QQ，WeiBo，WeiXin登录成功回调
     */
    private OauthLoginListener oauthlogin = new OauthLoginListener() {

        @Override
        public void OauthLoginSuccess(final AuthToken token, final AuthUser user) {

            handler.post(new Runnable() {

                @Override
                public void run() {

                    String uuid = "";//三方用户唯一ID
                    String name = "";
                    int type = token.authtype;
                    switch (type) {
                        case LoginPlatForm.QQZONE_PLATPORM:
                            uuid = ((QQToken) token).getOpenid();
                            name = ((QQUserInfo) user).getNickname();
                            break;

                        case LoginPlatForm.WECHAT_PLATPORM:
                            uuid = ((WeiXinUserInfo) user).getUnionid();
                            name = ((WeiXinUserInfo) user).getNickname();
                            break;
                        case LoginPlatForm.WEIBO_PLATPORM:
                            uuid = ((WeiBoToken) token).getUid();
                            name = ((WeiBoUserInfo) user).getName();

                            break;
                    }
                    mProressbar.setText("状态:登录成功\n" + "ID:" + uuid + "\n昵称:" + name);
                }
            });
        }

        @Override
        public void OauthLoginFail() {
            handler.post(new Runnable() {

                @Override
                public void run() {
                    mProressbar.setText("登录失败");
                }
            });

        }
    };

    /**
     * QQ，微博授权回调
     */
    private OauthListener oauth = new OauthListener() {

        @Override
        public void OauthSuccess(AuthToken obj) {
            mProressbar.setText("正在为你登录");
            mProressbar.setVisibility(View.VISIBLE);
        }

        @Override
        public void OauthFail() {
            Toast.makeText(getApplicationContext(), "授权失败", Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void OauthCancel() {
            Toast.makeText(getApplicationContext(), "取消授权", Toast.LENGTH_SHORT)
                    .show();
        }
    };

    /**
     * 微信授权广播回调
     *
     * @author user
     */
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (WXEntryActivity.ACTION_WX_LOGIN_SUCEESS.equals(intent.getAction())) {
                final String code = intent.getStringExtra("code");
                mProressbar.setText("正在为你登录");
                mProressbar.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        WeiXinAuthApi.getOauthAcces(code, oauthlogin);
                    }
                }).start();
            }
        }
    }

}
