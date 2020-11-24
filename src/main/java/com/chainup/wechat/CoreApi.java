package com.chainup.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chainup.action.BaseController;
import com.chainup.core.config.RequestResult;
import com.chainup.core.domain.AccessToken;
import com.chainup.core.dto.MeetingDto;
import com.chainup.service.UserService;
import com.chainup.utils.CoreUrl;
import com.chainup.utils.DateUtil;
import com.chainup.utils.HttpUtil;
import com.google.common.collect.Maps;
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
                //这块缺少授权机  todo  没有获取用户信息操作
                if (!userService.isUserExist(openid)) {
                    userService.createUser("", openid, "");
                }
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
            log.info("发送消息:" + textMsg);
            jsonStr = HttpUtil.executeJsonParamHttpPost(CoreUrl.sendsubscribeMessageURL() + AccessToken.getAccessToken(), textMsg);
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
/*    public static void main(String[] args) {
        MeetingDto meetingDto = MeetingDto.builder().openId("oA-SP4lOq6GOeJp_5OoqyhSjzGBI")
                .meetingSubject("需求评审")
                .roomName("香港")
                .timeRange("10:20 - 11:30")
                .departmentName("saas")
                .userName("大力出奇迹").build();
        CoreApi.sendTemplateMessage(makeUpsubscribeMessage(meetingDto));
        //System.out.println(makeUpsubscribeMessage(meetingDto));
    }*/

    public static String makeUpsubscribeMessage(MeetingDto meetingDto) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("touser",meetingDto.getOpenId());
        data.put("template_id",(CoreUrl.getSubscribeMessageTemplateId()));
        data.put("miniprogram_state","developer");
        data.put("lang","zh");
        Map<String, Object> innerDate = Maps.newHashMap();
        Map<String, Object> thingDate1 = Maps.newHashMap();
        Map<String, Object> valueDate1 = Maps.newHashMap();
        valueDate1.put("value", meetingDto.getMeetingSubject());
        thingDate1.put("thing1", valueDate1);
        innerDate.put("thing1", thingDate1);

        Map<String, Object> thingDate2 = Maps.newHashMap();
        Map<String, Object> valueDate2 = Maps.newHashMap();
        valueDate2.put("value", meetingDto.getRoomName());
        thingDate2.put("thing2", valueDate2);
        innerDate.put("thing2", thingDate2);


        Map<String, Object> thingDate3 = Maps.newHashMap();
        Map<String, Object> valueDate3 = Maps.newHashMap();
        valueDate3.put("value", "今天 " + meetingDto.getTimeRange());
        thingDate3.put("thing3", valueDate3);
        innerDate.put("character_string3", thingDate3);

        Map<String, Object> thingDate4 = Maps.newHashMap();
        Map<String, Object> valueDate4 = Maps.newHashMap();
        valueDate4.put("value", meetingDto.getDepartmentName());
        thingDate4.put("thing4", valueDate4);
        innerDate.put("thing4", thingDate4);

        Map<String, Object> thingDate5 = Maps.newHashMap();
        Map<String, Object> valueDate5 = Maps.newHashMap();
        valueDate5.put("value", meetingDto.getUserName());
        thingDate5.put("thing5", valueDate5);
        innerDate.put("thing5", thingDate5);
        data.put("data", innerDate);

        return JSON.toJSONString(data);
    }
}