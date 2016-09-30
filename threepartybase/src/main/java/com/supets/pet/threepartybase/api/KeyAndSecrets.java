package com.supets.pet.threepartybase.api;


//<!--注意需要替换你的各部分平台key-->

public interface KeyAndSecrets {

    //wechat
    String WEIXINAPPID = "wxae4f486adfe33890";
    String WEIXIN_SCOPE = "snsapi_userinfo";
    String WEIXIN_APPSECRET = "e05ab5d117ba1274101666d818e8a83f";

    //sina
    String SINAWEIBO_APPKEY = "2542052748";
    String SINAWEIBO_REDIRECT_URI = "https://api.weibo.com/oauth2/default.html";
    String SINAWEIBO_AppSecret = "3201744bab19c98a69cb663f27ee24fb";
    String SINAWEIBO_SCOPE = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";

    //QQ
    String QQ_APPID = "1105254618";
    String QQ_SCOPE = "all";
}
