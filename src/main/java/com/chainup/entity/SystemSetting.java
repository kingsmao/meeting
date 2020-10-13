package com.chainup.entity;

import java.util.Date;

public class SystemSetting {
    private Integer id;

    private String shopId;

    private Date serviceStartTime;

    private Date serviceEndTime;

    private Integer timeout;

    private Integer limite;

    private Integer traineeNum;

    private Double traineeFactor;

    private Integer skillerNum;

    private Double skillerFactor;

    private Date workTime;

    private Date submitTime;

    private Integer isAppTow;

    private String startStr;

    private String endStr;

    private Integer switchStatue;


    private Integer enabled;

    public String getStartStr() {
        return startStr;
    }

    public void setStartStr(String startStr) {
        this.startStr = startStr;
    }

    public String getEndStr() {
        return endStr;
    }

    public void setEndStr(String endStr) {
        this.endStr = endStr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Date getServiceStartTime() {
        return serviceStartTime;
    }

    public void setServiceStartTime(Date serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    public Date getServiceEndTime() {
        return serviceEndTime;
    }

    public void setServiceEndTime(Date serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getLimite() {
        return limite;
    }

    public void setLimite(Integer limite) {
        this.limite = limite;
    }

    public Integer getTraineeNum() {
        return traineeNum;
    }

    public void setTraineeNum(Integer traineeNum) {
        this.traineeNum = traineeNum;
    }

    public Double getTraineeFactor() {
        return traineeFactor;
    }

    public void setTraineeFactor(Double traineeFactor) {
        this.traineeFactor = traineeFactor;
    }

    public Integer getSkillerNum() {
        return skillerNum;
    }

    public void setSkillerNum(Integer skillerNum) {
        this.skillerNum = skillerNum;
    }

    public Double getSkillerFactor() {
        return skillerFactor;
    }

    public void setSkillerFactor(Double skillerFactor) {
        this.skillerFactor = skillerFactor;
    }

    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Integer getIsAppTow() {
        return isAppTow;
    }

    public void setIsAppTow(Integer isAppTow) {
        this.isAppTow = isAppTow;
    }

    public Integer getSwitchStatue() {
        return switchStatue;
    }

    public void setSwitchStatue(Integer switchStatue) {
        this.switchStatue = switchStatue;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "SystemSetting{" +
                "id=" + id +
                ", shopId='" + shopId + '\'' +
                ", serviceStartTime=" + serviceStartTime +
                ", serviceEndTime=" + serviceEndTime +
                ", timeout=" + timeout +
                ", limite=" + limite +
                ", traineeNum=" + traineeNum +
                ", traineeFactor=" + traineeFactor +
                ", skillerNum=" + skillerNum +
                ", skillerFactor=" + skillerFactor +
                ", workTime=" + workTime +
                ", submitTime=" + submitTime +
                ", isAppTow=" + isAppTow +
                ", switchStatue=" + switchStatue +
                ", enabled=" + enabled +
                '}';
    }
}