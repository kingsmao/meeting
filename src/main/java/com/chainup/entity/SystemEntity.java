package com.chainup.entity;


/**
 * Paceage:com.kingmao.dog.appointment.provider
 * Description:
 * Date:2019/2/25
 * Author: KingMao
 **/
public class SystemEntity extends SystemSetting {
    private Integer totalTime;   //总工作时间  早9晚9 = 12

    private Integer lefTime;   //剩余时间   现在6点，9点下班， = (9 - 6) * 60

    private Integer earnTime;  //能效时间   = 剩余时间 * 工人数量

    private Integer consumeTime = 0; //消耗时间 两只狗需要耗时2小时

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public Integer getLefTime() {
        return lefTime;
    }

    public void setLefTime(Integer lefTime) {
        this.lefTime = lefTime;
    }

    public Integer getEarnTime() {
        return earnTime;
    }

    public void setEarnTime(Integer earnTime) {
        this.earnTime = earnTime;
    }

    public Integer getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(Integer consumeTime) {
        this.consumeTime = consumeTime;
    }

    @Override
    public String toString() {
        return "SystemEntity{" +
                "totalTime=" + totalTime +
                ", lefTime=" + lefTime +
                ", earnTime=" + earnTime +
                ", consumeTime=" + consumeTime +
                '}';
    }
}
