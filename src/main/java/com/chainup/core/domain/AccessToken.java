package com.chainup.core.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class AccessToken {

    private static String accessToken = null;

    public static void refresh(String token) {
        accessToken = token;
        log.info("token刷新成功！");
    }

    public static String getAccessToken() {
        return accessToken;
    }
}
