package com.supets.pet.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.supets.pet.R;
import com.supets.pet.threepartybase.api.QQShareApi;
import com.supets.pet.threepartybase.api.WeiBoShareApi;
import com.supets.pet.threepartybase.api.WeiXinShareApi;
import com.supets.pet.threepartybase.model.AuthToken;
import com.supets.pet.threepartybase.utils.OauthListener;
import com.supets.pet.threepartybase.utils.ToastUtils;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

public class ThreeShareActivity extends BaseActivity implements
        OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        findViewById(R.id.qq).setOnClickListener(this);
        findViewById(R.id.weibo).setOnClickListener(this);
        findViewById(R.id.weixin).setOnClickListener(this);
        findViewById(R.id.weixin2).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String imageUrl = "http://s6.sinaimg.cn/mw690/001oRhKZgy6VwpYErFH65&690";
        String webpageUrl = "http://image.baidu.com/search/index?tn=baiduimage&ps=1&ct=201326592&lm=-1&cl=2&nc=1&ie=utf-8&word=ds";
        String title = "标题";
        final String content = "内容";
        String transaction="transaction";//可以自定义用来传递数据，作用相当bundle

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);

        int id = view.getId();
        switch (id) {
            case R.id.qq:
                qqzone(imageUrl, webpageUrl, title, content);
                break;
            case R.id.weibo:
                weibo(content);
                break;
            case R.id.weixin:
                WeiXinShareApi.shareWebPage(transaction, title, content, imageUrl, webpageUrl, bitmap, false);
                break;
            case R.id.weixin2:
                WeiXinShareApi.shareWebPage(transaction, title, content, imageUrl, webpageUrl, bitmap, true);
                break;
            default:
                break;
        }
    }

    private void weibo(final String content) {
        WeiBoShareApi.checkOauthValid(this, new OauthListener() {

            @Override
            public void OauthSuccess(AuthToken obj) {
                WeiBoShareApi.shareToWeibo(ThreeShareActivity.this, content, null, new RequestListener() {
                    @Override
                    public void onComplete(String s) {
                        ToastUtils.showToastMessage(R.string.share_success);
                    }

                    @Override
                    public void onWeiboException(WeiboException e) {
                        ToastUtils.showToastMessage(R.string.share_fail);
                    }
                });
            }

            @Override
            public void OauthFail() {
                ToastUtils.showToastMessage(R.string.share_fail);
            }

            @Override
            public void OauthCancel() {
                ToastUtils.showToastMessage(R.string.share_fail);
            }
        });
    }

    private void qqzone(String imageUrl, String webpageUrl, String title, String content) {
        QQShareApi.shareWebPage(this, imageUrl, webpageUrl, title, content, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                ToastUtils.showToastMessage(R.string.share_success);
            }

            @Override
            public void onError(UiError uiError) {
                ToastUtils.showToastMessage(R.string.share_fail);
            }

            @Override
            public void onCancel() {
                ToastUtils.showToastMessage(R.string.share_fail);
            }
        });
    }

}
