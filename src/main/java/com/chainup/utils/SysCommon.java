package com.chainup.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class SysCommon {
    private static String SysUrl;
    @Value("${sysUrl}")
    private void setSysUrl(String sysUrl) {
        SysUrl = sysUrl;
    }

    public static String getSysUrl(){
        return SysUrl;
    }

}
