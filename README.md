#  SupetsSSO

## 0 初衷
    
    不想依赖三方平台，灵活定制。
[demo.apk 下载使用](https://github.com/supets-open/supets-sso/blob/master/app-debug.apk)


![DEMO](https://github.com/supets-open/supets-sso/blob/master/doc/360%E6%89%8B%E6%9C%BA%E5%8A%A9%E6%89%8B%E6%88%AA%E5%9B%BE0929_16_28_01.png?raw=true)
![DEMO](https://github.com/supets-open/supets-sso/blob/master/doc/360%E6%89%8B%E6%9C%BA%E5%8A%A9%E6%89%8B%E6%88%AA%E5%9B%BE0929_16_32_02.png?raw=true)

## 1 特性

    1.提供QQ，微信，微博三方授权基础API
    2.获取QQ，微信，微博三方用户基础API
    3.支持QQ，微信，微博三方 分享基础API
    3.只需一行，一个回调搞定三方登录，三方授权
    
## 2 接入说明

    第一步 把weiboSdk，threepartybase 两个依赖库添加到工程。
    第二步 注册三个平台的appId填写到threepartybase工程的KeyAndSecrets类中。同时修改配置文件需要将 tencent1101525942 数字改成注册的QQ应用的APPID
    第三步 更换demo工程签名文件为你的正式签名文件，修改下demo包名为你的应用包名即可编译测试。
    
    注意事项:微信平台应用签名一定要和本地签名一致，应用包名要一致。

    混淆配置:
    # thirdbase
    -keep class com.supets.pet.threepartybase.R {*;}
    -keep class com.supets.pet.threepartybase.** {*;}
    
## 3 如有问题，详细咨询
    
    QQ：254608684
    EMAIL：254608684@qq.com
