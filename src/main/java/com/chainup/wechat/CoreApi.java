package com.chainup.wechat;

import com.chainup.cacha.SysCacha;
import com.chainup.utils.CoreUrl;
import com.chainup.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class CoreApi {

    @Value("${mini.appid}")
    private String appid;
    @Value("${mini.secret}")
    private String secret;

   /* @Autowired
    private CustomerService customerService;*/

    @ResponseBody
    @RequestMapping("/core/code2Session.do")
    public String code2Session(String code) {
        log.info("进入到core方法，获取code：" + code);
        String result = null;
        String openid = null;
        String session_key = null;
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            result = HttpUtil.doHttpsGetJson(CoreUrl.getCode2SessionURL(appid,secret,code));
            log.info("请求API返回结果：" + result);
            JSONObject jsonStr = JSONObject.fromObject(result);
            if (jsonStr.has("openid")) {
                openid = jsonStr.getString("openid");
                session_key = jsonStr.getString("session_key");
                map.put("openid", openid);
                map.put("session_key", session_key);
                log.info("openid:：" + openid + "session_key: " + session_key);
            }
        } catch (Exception e) {
            log.error("请求小程序API发生错误", e);
        }
        return gson.toJson(map);
    }

    /**
     * 向客户发送模板消息
     * @param textMsg
     * @return
     */
    public static String sendTemplateMessage(String textMsg){
        log.info("进入到发送模板消息方法");
        String jsonStr = "";
        try {
            jsonStr = HttpUtil.executeJsonParamHttpPost(CoreUrl.sendTemplateMessageURL() + SysCacha.getAccessToken(), textMsg);
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(jsonStr).getAsJsonObject();
            //System.out.println(new GsonBuilder().serializeNulls().setPrettyPrinting().create().toJson(jsonObject));
            log.info("返回消息:" + jsonStr);
            if (jsonObject.get("errcode").getAsString().equals("0")) {
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