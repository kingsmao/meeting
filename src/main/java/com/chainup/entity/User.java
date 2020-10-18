package com.chainup.entity;

import java.io.Serializable;
import java.util.Date;

/**
* @author LiLi
*/
public class User implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 小程序用户唯一标识
     */
    private String openId;

    /**
     * 用户所属部门，暂时没用
     */
    private Integer departmentId;

    /**
     * 用户名字
     */
    private String userName;

    /**
     * 用户昵称名字
     */
    private String nickName;

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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
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
        private User obj;

        public Builder() {
            this.obj = new User();
        }

        public Builder id(Integer id) {
            obj.id = id;
            return this;
        }

        public Builder openId(String openId) {
            obj.openId = openId;
            return this;
        }

        public Builder departmentId(Integer departmentId) {
            obj.departmentId = departmentId;
            return this;
        }

        public Builder userName(String userName) {
            obj.userName = userName;
            return this;
        }

        public Builder nickName(String nickName) {
            obj.nickName = nickName;
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

        public User build() {
            return this.obj;
        }
    }
}