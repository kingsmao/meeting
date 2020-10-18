package com.chainup.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chainup.cacha.SysCacha;
import com.chainup.utils.CoreUrl;
import com.chainup.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/core")
public class CoreApi {

    @Value("${mini.appid}")
    private String appid;
    @Value("${mini.secret}")
    private String secret;

//    @Autowired
//    private CustomerService customerService;


    @GetMapping("/saveClientInfo.do")
    public String code2Session(@RequestParam(required = false) String openid,
                               @RequestParam(required = false) String nickName,
                               @RequestParam(required = false) String wxImg) {
        log.info("openId:{},nickName:{},wxImg:{}", openid, nickName, wxImg);
        return "ok";
    }


    @ResponseBody
    @RequestMapping("/code2Session.do")
    public String code2Session(String code) {
        log.info("进入到core方法，获取code：" + code);
        String result = null;
        String openid = null;
        String sessionKey = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            result = HttpUtil.doHttpsGetJson(CoreUrl.getCode2SessionURL(appid, secret, code));
            log.info("请求API返回结果：" + result);
            JSONObject jsonStr = JSONObject.parseObject(result);
            if (jsonStr.containsKey("openid")) {
                openid = jsonStr.getString("openid");
                sessionKey = jsonStr.getString("session_key");
                map.put("openid", openid);
                map.put("session_key", sessionKey);
                log.info("openid:：" + openid + "session_key: " + sessionKey);
            }
        } catch (Exception e) {
            log.error("请求小程序API发生错误", e);
        }
        return JSON.toJSONString(map);
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
            jsonStr = HttpUtil.executeJsonParamHttpPost(CoreUrl.sendTemplateMessageURL() + SysCacha.getAccessToken(), textMsg);
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

    /**
     * 保存用户信息
     * @param request
     * @return
     */
    // todo 用户进入小程序时记录openid
   /* @ResponseBody
    @RequestMapping(value = "/core/saveClientInfo.do")
    public String dealuserInfo(HttpServletRequest request) {
        log.info("接收到参数：" + request.getParameter("nickName"));
        String openid = request.getParameter("openid");
        String nickName = request.getParameter("nickName");
        String wxImg = request.getParameter("wxImg");
        Client client = new Client();
        client.setOpenid(openid);
        client.setNickName(nickName);
        client.setWxImg(wxImg);
        if (customerService.insertClient(client)) {
            return "0ok0";
        } else {
            return "falisesesssss";
        }
    }*/
}