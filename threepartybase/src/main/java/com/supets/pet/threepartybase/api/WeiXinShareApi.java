package com.supets.pet.threepartybase.api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.supets.pet.threepartybase.R;
import com.supets.pet.threepartybase.utils.ContextUtils;
import com.supets.pet.threepartybase.utils.ImageUtils;
import com.supets.pet.threepartybase.utils.ToastUtils;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;

import java.io.File;

public class WeiXinShareApi {

    public static final int THUMB_SIZE = 150;
    public static final int MAX_WEIXIN_PHOTO_SIZE = 32 * 1024; // 32KB

    public static void shareWebPage(String transaction,
                                    String title,
                                    String description,
                                    String imageUrl,
                                    String webpageUrl,
                                    Bitmap bmp,
                                    boolean toMoments) {

        if (TextUtils.isEmpty(imageUrl)) {
            return;
        }
        if (!WeiXinAuthApi.isWXAPPInstalled()) {
            ToastUtils.showToastMessage(ContextUtils.getApplication()
                    .getString(R.string.Weixin_not_install_notify));
            return;
        }

        if (!WeiXinAuthApi.getWXAPI().isWXAppSupportAPI()) {
            ToastUtils.showToastMessage(ContextUtils.getApplication()
                    .getString(R.string.Weixin_not_support_API));
            return;
        }

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = webpageUrl;

        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE,
                THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = ImageUtils.Bitmap2Bytes(thumbBmp,
                MAX_WEIXIN_PHOTO_SIZE);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = transaction;
        req.message = msg;
        if (toMoments) {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }

        WeiXinAuthApi.getWXAPI().sendReq(req);

    }


    public static void shareLocalImageToSNS(String path,
                                            String description,
                                            String transaction,
                                            boolean isfriends) {
        if (!WeiXinAuthApi.isWXAPPInstalled()) {
            ToastUtils.showToastMessage(ContextUtils.getApplication()
                    .getString(R.string.Weixin_not_install_notify));
            return;
        }

        if (!WeiXinAuthApi.getWXAPI().isWXAppSupportAPI()) {
            ToastUtils.showToastMessage(ContextUtils.getApplication()
                    .getString(R.string.Weixin_not_support_API));
            return;
        }

        File file = new File(path);
        if (!file.exists()) {
            return;
        }

        WXImageObject imgObj = new WXImageObject();
        imgObj.setImagePath(path);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        msg.description = description;

        Bitmap bmp = BitmapFactory.decodeFile(path);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE,
                THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = ImageUtils.Bitmap2Bytes(thumbBmp,
                MAX_WEIXIN_PHOTO_SIZE);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = transaction;
        req.message = msg;
        if (isfriends) {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        WeiXinAuthApi.getWXAPI().sendReq(req);
    }
}
