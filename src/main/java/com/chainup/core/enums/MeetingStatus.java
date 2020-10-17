package com.chainup.core.enums;

/**
 * 会议状态
 *
 * @author lili
 * @date 2020/10/17 17:05
 * @see
 * @since
 */
public enum MeetingStatus {

    NOT_START(0, "未开始"),
    RUNNING(1, "进行中"),
    FINISHED(2, "已结束");

    private int status;
    private String description;

    MeetingStatus(int status, String description) {
        this.status = status;
        this.description = description;
    }


    MeetingStatus(int status) {
        this.status = status;
    }

    public int status() {
        return status;
    }


    public byte byteStatus() {
        return (byte) status;
    }

}
