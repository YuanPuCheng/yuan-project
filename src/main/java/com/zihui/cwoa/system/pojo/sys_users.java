package com.zihui.cwoa.system.pojo;

import java.io.Serializable;

public class sys_users implements Serializable{

    private static final long serialVersionUID = -3686720987631869507L;

    private Integer userId;//用户id

    private String userCode;//用户工号  做登录用

    private String userName;//用户名称

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "sys_users{" +
                "userId=" + userId +
                ", userCode='" + userCode + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
