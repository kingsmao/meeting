package com.chainup.action;

import com.chainup.core.config.RequestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author QX
 * @date 2020/10/16
 */
@Slf4j
@RestController
public class HealthController extends BaseController {

    @GetMapping("/health")
    public RequestResult<String> health() {
        return success("Server Health 大力哥！");
    }


    @GetMapping("/hack")
    public String health2() {
        log.debug("{}", System.currentTimeMillis());
        log.info("I am health");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1000000000; i++) {
            builder.append("alibababababbababab");
        }
        return builder.toString();
    }

}
