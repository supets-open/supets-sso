package com.supets.pet.threepartybase.api;

import android.app.Activity;
import android.os.Bundle;

import com.supets.pet.threepartybase.R;
import com.supets.pet.threepartybase.utils.ContextUtils;
import com.supets.pet.threepartybase.utils.ToastUtils;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;


public class QQShareApi {

    // 分享图片到qq空间
    public static void shareLocalPhotoToQQ(Activity activity, String path, String content,String webpageUrl, IUiListener listener) {
        if (!QQAuthApi.isQQInstalled()) {
            ToastUtils.showToastMessage(R.string.QQ_not_install_notify);
            return;
        }
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, content);// 必填
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,webpageUrl);// 必填
        ArrayList<String> pathURLs = new ArrayList<String>();
        pathURLs.add(path);
        params.putStringArrayList(QQShare.SHARE_TO_QQ_IMAGE_URL, pathURLs);

        Tencent tencent = Tencent.createInstance(KeyAndSecrets.QQ_APPID, ContextUtils.getApplication());
        if (tencent != null) {
            tencent.shareToQzone(activity, params, listener);
        }
    }

    // 分享链接到qq空间
    public static void shareWebPage(Activity activity, String imageUrl, String webpageUrl, String title, String text,
                                    IUiListener listener) {
        if (!QQAuthApi.isQQInstalled()) {
            ToastUtils.showToastMessage(R.string.QQ_not_install_notify);
            return;
        }

        Bundle params = new Bundle();
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, webpageUrl);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, text);

        ArrayList<String> imageUrls = new ArrayList<String>();
        imageUrls.add(imageUrl.replaceFirst("file://", ""));
        params.putStringArrayList(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);

        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, activity.getString(R.string.app_name));
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);

        Tencent tencent = Tencent.createInstance(KeyAndSecrets.QQ_APPID, activity);
        tencent.shareToQzone(activity, params, listener);
    }
}

