package com.chainup.cacha;

import com.chainup.entity.SystemEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Paceage:com.kingmao.dog.appointment.cacha
 * Description:
 * Date:2019/2/22
 * Author: KingMao
 **/
@Component
@Slf4j
public class SysCacha {
    private static SystemEntity systemEntity;

    public static SystemEntity getSystemEntity() {
        return systemEntity;
    }

    public static void setSystemEntity(SystemEntity systemEntity) {
        SysCacha.systemEntity = systemEntity;
    }

    private static String accessToken = null;

    public static void refreshToken(String token){
        accessToken = token;
        log.info("token刷新成功！");
    }

    public static String getAccessToken(){
        return accessToken;
    }
}
