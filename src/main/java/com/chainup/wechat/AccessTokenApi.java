package com.chainup.wechat;

import com.chainup.core.domain.AccessToken;
import com.chainup.utils.CoreUrl;
import com.chainup.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
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

    public void getToken() {

        String tokenResponse = HttpUtil.doHttpsGetJson(CoreUrl.getAccessTokenURL(appid, secret));

        if (StringUtils.isNotBlank(tokenResponse)) {
            JSONObject jsonObject = JSONObject.fromObject(tokenResponse);
            String accessToken = jsonObject.getString("access_token");
            if (StringUtils.isNotBlank(accessToken)) {
                AccessToken.refresh(accessToken);
                log.info("获取accesstoken成功！---------" + accessToken);
            }
        }
    }
}
