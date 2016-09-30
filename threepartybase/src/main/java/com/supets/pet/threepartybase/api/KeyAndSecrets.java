package com.supets.pet.threepartybase.api;


//<!--注意需要替换你的各部分平台key-->

public interface KeyAndSecrets {

    //wechat
    String WEIXINAPPID = "替换你的应用";
    String WEIXIN_SCOPE = "snsapi_userinfo";
    String WEIXIN_APPSECRET = "替换你的应用";

    //sina
    String SINAWEIBO_APPKEY = "替换你的应用";
    String SINAWEIBO_REDIRECT_URI = "https://api.weibo.com/oauth2/default.html";
    String SINAWEIBO_AppSecret = "替换你的应用";
    String SINAWEIBO_SCOPE = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";

    //QQ
    String QQ_APPID = "替换你的应用";
    String QQ_SCOPE = "all";
}
