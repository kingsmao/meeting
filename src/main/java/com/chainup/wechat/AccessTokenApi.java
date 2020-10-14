package com.chainup.wechat;

import com.chainup.cacha.SysCacha;
import com.chainup.utils.CoreUrl;
import com.chainup.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author QX
 * @date 2020/10/14
 */
@Component
@Slf4j
public class AccessTokenApi {
    @Value("${mini.appid}")
    private String appid;
    @Value("${mini.secret}")
    private String secret;

    public void getToken(){
        String result = null;
        String accessToken = "";
        result = HttpUtil.doHttpsGetJson(CoreUrl.getAccessTokenURL(appid,secret));

        if (null != result && !"".equals(result)) {
            JSONObject jsonObject = JSONObject.fromObject(result);
            accessToken = jsonObject.getString("access_token");
        }

        if (null != accessToken && !"".equals(accessToken)) {
            SysCacha.refreshToken(accessToken);
            log.info("获取accesstoken成功！---------" + accessToken);
        }
    }
}
