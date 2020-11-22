package com.chainup.core.config;


public enum ExceptionCode {
    /**
     * 成功
     */
    SUCCESS(0, "OK"),

    PARAM_ERROR(1, "参数错误"),

    /**
     * 内部错误
     */
    INTERNAL_ERROR(-1, "内部错误"),


    MEETING_CONFLICT(2, "会议室冲突"),


    USER_NOT_EXIST(3, "用户不存在");


    private int code;
    private String msg;

    ExceptionCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ExceptionCode parseCode(int code) {
        for (ExceptionCode value : values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return ExceptionCode.SUCCESS;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
    }

}
