package com.chainup.entity;

import java.io.Serializable;
import java.util.Date;

/**
* @author LiLi
*/
public class Room implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 房间名字
     */
    private String name;

    /**
     * 房间描述，以|竖线分隔，例如30|白板|投影仪
     */
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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
        private Room obj;

        public Builder() {
            this.obj = new Room();
        }

        public Builder id(Integer id) {
            obj.id = id;
            return this;
        }

        public Builder name(String name) {
            obj.name = name;
            return this;
        }

        public Builder description(String description) {
            obj.description = description;
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

        public Room build() {
            return this.obj;
        }
    }
}