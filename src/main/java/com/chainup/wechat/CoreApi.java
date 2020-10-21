package com.chainup.wechat;

import com.alibaba.fastjson.JSONObject;
import com.chainup.action.BaseController;
import com.chainup.core.config.RequestResult;
import com.chainup.core.domain.AccessToken;
import com.chainup.service.UserService;
import com.chainup.utils.CoreUrl;
import com.chainup.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/core")
public class CoreApi extends BaseController {

    @Value("${mini.appid}")
    private String appid;
    @Value("${mini.secret}")
    private String secret;

    @Autowired
    private UserService userService;


    @GetMapping("/saveClientInfo.do")
    public RequestResult<String> saveClientInfo(@RequestParam(required = false) String openid,
                                                @RequestParam(required = false) String nickName,
                                                @RequestParam(required = false) String wxImg) {
        log.info("saveClientInfo openId:{},nickName:{},wxImg:{}", openid, nickName, wxImg);
        if (StringUtils.isBlank(openid)) {
            return error("openId不能为空");
        }
        if (!userService.isUserExist(openid)) {
            userService.createUser(nickName, openid, wxImg);
        } else {
            log.warn("用户已存在，openId:{}", openid);
        }
        return success();
    }


    /**
     * 根据小程序wx.login的code获取openid
     *
     * @param code 临时登录凭证 code
     * @return
     */
    @GetMapping("/code2Session.do")
    public RequestResult<Map<String, Object>> code2Session(@RequestParam(name = "code") String code) {
        log.info("/code2Session.do 获取code：" + code);
        String result = null;
        String openid = null;
        String sessionKey = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            result = HttpUtil.doHttpsGetJson(CoreUrl.getCode2SessionURL(appid, secret, code));
            log.info("code2Session result：" + result);
            JSONObject jsonStr = JSONObject.parseObject(result);
            if (jsonStr.containsKey("openid")) {
                openid = jsonStr.getString("openid");
                sessionKey = jsonStr.getString("session_key");
                map.put("openid", openid);
                map.put("session_key", sessionKey);
                log.info("openid:{},session_key:{}", openid, sessionKey);
            }
        } catch (Exception e) {
            log.error("请求小程序API发生错误", e);
        }
        return success(map);
    }

    /**
     * 向客户发送模板消息
     *
     * @param textMsg
     * @return
     */
    public static String sendTemplateMessage(String textMsg) {
        log.info("进入到发送模板消息方法");
        String jsonStr = "";
        try {
            jsonStr = HttpUtil.executeJsonParamHttpPost(CoreUrl.sendTemplateMessageURL() + AccessToken.getAccessToken(), textMsg);
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            //System.out.println(new GsonBuilder().serializeNulls().setPrettyPrinting().create().toJson(jsonObject));
            log.info("返回消息:" + jsonStr);
            if (jsonObject.getString("errcode").equals("0")) {
                log.info("发送模板消息成功！：");
                return "success";
            }
        } catch (Exception e) {
            log.info("调取微信接口向用户发送模板消息出错：" + jsonStr);
        }
        return "fail";
    }
}