package com.chainup.entity;

import java.io.Serializable;
import java.util.Date;

/**
* @author LiLi
*/
public class Meeting implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 会议名称/主题
     */
    private String name;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 会议室id
     */
    private Integer roomId;

    /**
     * 参会部门id
     */
    private Integer departmentId;

    /**
     * 会议开始时间
     */
    private Date beginTime;

    /**
     * 会议结束时间
     */
    private Date endTime;

    /**
     * 0=未预定；1=进行中；2=已结束
     */
    private Byte status;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 修改时间
     */
    private Date mtime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    public static class Builder {
        private Meeting obj;

        public Builder() {
            this.obj = new Meeting();
        }

        public Builder id(Integer id) {
            obj.id = id;
            return this;
        }

        public Builder name(String name) {
            obj.name = name;
            return this;
        }

        public Builder userId(Integer userId) {
            obj.userId = userId;
            return this;
        }

        public Builder roomId(Integer roomId) {
            obj.roomId = roomId;
            return this;
        }

        public Builder departmentId(Integer departmentId) {
            obj.departmentId = departmentId;
            return this;
        }

        public Builder beginTime(Date beginTime) {
            obj.beginTime = beginTime;
            return this;
        }

        public Builder endTime(Date endTime) {
            obj.endTime = endTime;
            return this;
        }

        public Builder status(Byte status) {
            obj.status = status;
            return this;
        }

        public Builder ctime(Date ctime) {
            obj.ctime = ctime;
            return this;
        }

        public Builder mtime(Date mtime) {
            obj.mtime = mtime;
            return this;
        }

        public Meeting build() {
            return this.obj;
        }
    }
}