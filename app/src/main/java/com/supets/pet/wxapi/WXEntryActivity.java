package com.supets.pet.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.supets.pet.R;
import com.supets.pet.threepartybase.api.WeiXinAuthApi;
import com.supets.pet.threepartybase.utils.ToastUtils;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelbase.BaseResp.ErrCode;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    public static final String ACTION_WX_LOGIN_SUCEESS = "ACTION_WX_LOGIN_SUCEESS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WeiXinAuthApi.getWXAPI().handleIntent(
                getIntent(), this);
    }

    @Override
    public void onReq(com.tencent.mm.sdk.modelbase.BaseReq arg0) {

    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.getType()) {
            case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                handleShareResult(resp);
                break;
            case ConstantsAPI.COMMAND_SENDAUTH:// 微信的第三方登录的回调
                handleLoginResult(resp);
                break;
            default:
                break;
        }
        finish();
    }

    private void handleLoginResult(BaseResp resp) {
        String code = ((SendAuth.Resp) resp).code;
        if (code != null) {
            Log.v("code:", code);
            Intent action = new Intent();
            action.setAction(ACTION_WX_LOGIN_SUCEESS);
            action.putExtra("code", code);

            LocalBroadcastManager.getInstance(this).sendBroadcast(action);
        }

        int errorCode = resp.errCode;
        if (errorCode != ErrCode.ERR_OK) {
            int resid = errorCode == ErrCode.ERR_USER_CANCEL ? R.string.Weixin_login_cancel : R.string.Weixin_login_fail;
            ToastUtils.showToastMessage(getString(resid));
        }
    }

    private void handleShareResult(BaseResp resp) {
        int errorCode = resp.errCode;
        int resid = errorCode == ErrCode.ERR_OK ? R.string.Weixin_share_success
                : (errorCode == ErrCode.ERR_USER_CANCEL ? R.string.Weixin_share_cancel : R.string.Weixin_share_fail);
        ToastUtils.showToastMessage(getString(resid));
    }
}
