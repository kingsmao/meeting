package com.chainup.utils;

/**
 * Paceage:com.kingmao.dog.utils
 * Description:
 * Date:2019/3/4
 * Author: KingMao
 **/
public class CoreUrl {

    /**
     * 获取用户open_id
     *
     * @param appid
     * @param secret
     * @param code
     * @return
     */
    public static String getCode2SessionURL(String appid, String secret, String code) {
        return "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
    }

    /**
     * @Deprecated  该接口已被官方废弃
     * @return
     */
    @Deprecated
    public static String sendTemplateMessageURL() {
        return "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=";
    }

    /**
     * 向用户推送订阅消息
     * @return
     */
    public static String sendsubscribeMessageURL() {
        return "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=";
    }

    public static String getAccessTokenURL(String appid, String secret) {
        return "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
    }

    /**
     * 消息订阅推送的模板id
     * @return
     */
    public static String getSubscribeMessageTemplateId() {
        return "g4llUD-SEs_CT8VsiIjDnRJ4wj7pKbnSD6nxIoS2iyY";
    }
}
