package com.chainup.entity;

import java.io.Serializable;
import java.util.Date;

/**
* @author qinxuan
*/
public class Meeting implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 会议名称
     */
    private String name;

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
    private Date startTime;

    /**
     * 会议结束时间
     */
    private Date endTime;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 更新时间
     */
    private Date mtime;

    /**
     * 0=未开始；1=进行中；2=已结束
     */
    private Byte status;

    /**
     * 会议预定人的openid（同一个公众号/小程序下唯一）
     */
    private String userOpenid;

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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getUserOpenid() {
        return userOpenid;
    }

    public void setUserOpenid(String userOpenid) {
        this.userOpenid = userOpenid == null ? null : userOpenid.trim();
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

        public Builder roomId(Integer roomId) {
            obj.roomId = roomId;
            return this;
        }

        public Builder departmentId(Integer departmentId) {
            obj.departmentId = departmentId;
            return this;
        }

        public Builder startTime(Date startTime) {
            obj.startTime = startTime;
            return this;
        }

        public Builder endTime(Date endTime) {
            obj.endTime = endTime;
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

        public Builder status(Byte status) {
            obj.status = status;
            return this;
        }

        public Builder userOpenid(String userOpenid) {
            obj.userOpenid = userOpenid;
            return this;
        }

        public Meeting build() {
            return this.obj;
        }
    }
}