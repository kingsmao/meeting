package com.chainup.action;

import com.alibaba.fastjson.JSON;
import com.chainup.core.config.ExceptionCode;
import com.chainup.core.config.RequestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public abstract class BaseController {

    @Autowired
    private HttpServletRequest request;


    protected static final String APPLICATION_JSON = "application/json; charset=utf-8";

    <T> RequestResult<T> success(T t) {
        RequestResult<T> rr = new RequestResult<>();
        rr.setData(t);
        String json = JSON.toJSONString(rr);
        String requestURI = request.getRequestURI();
        log.info("requestURI:{}, result json: {}", requestURI, json);
        return rr;
    }

    /**
     * 生成失败响应
     *
     * @param msg 失败原因
     * @return
     */
    <T> RequestResult<T> error(String msg) {
        RequestResult<T> rr = new RequestResult<>(ExceptionCode.INTERNAL_ERROR.getCode(), msg);
        String requestURI = request.getRequestURI();
        log.error("requestURI:{}, result json: {}", requestURI, JSON.toJSONString(rr));
        return rr;
    }

    /**
     * @param e
     * @param <T>
     * @return
     */
    public <T> RequestResult<T> error(ExceptionCode e) {
        String requestURI = request.getRequestURI();
        log.error("requestURI:{}, result json: {}", requestURI, e.getMessage());
        return new RequestResult<>(e.getCode(), e.getMessage());
    }

    /**
     * 生成成功响应
     *
     * @return
     */
    <T> RequestResult<T> success() {
        RequestResult<T> rr = new RequestResult<>();
        String requestURI = request.getRequestURI();
        log.info("requestURI:{}, result json: {}", requestURI, JSON.toJSONString(rr));
        return rr;
    }

}
