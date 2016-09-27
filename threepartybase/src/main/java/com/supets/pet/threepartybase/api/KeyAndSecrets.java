package com.supets.pet.threepartybase.api;

/**
 * ThreePartyPlatform
 *
 * @user lihongjiang
 * @description
 * @date 2016/9/7
 * @updatetime 2016/9/7
 */
public interface KeyAndSecrets {

    // 微信APP ID
    String WEIXINAPPID = "XXXXXX";
    String WEIXIN_SCOPE = "snsapi_userinfo";
    String WEIXIN_APPSECRET = "XXXXX";
    String WEIXIN_MERCHANT_ID = "XXXXX";

    // sina weibo APP KEY
    String SINAWEIBO_APPKEY = "XXXXX";
    String SINAWEIBO_REDIRECT_URI = "https://api.weibo.com/oauth2/default.html";
    String SINAWEIBO_AppSecret = "XXXXXXX";
    String SINAWEIBO_SCOPE = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";

    // qq APP ID
    String QQ_APPID = "XXXXXX";
    String QQ_SCOPE = "all";
}
