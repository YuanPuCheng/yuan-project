package com.zihui.cwoa.routine.pojo;

import com.zihui.cwoa.system.pojo.sys_users;

public class rw_mail_user {
    private Integer id;

    private Integer mailId;

    private Integer mailUser;

    private Integer status;

    private Integer lookState;

    private Integer starState;

    private sys_users userss;

    public sys_users getUserss() {
        return userss;
    }

    public void setUserss(sys_users userss) {
        this.userss = userss;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMailId() {
        return mailId;
    }

    public void setMailId(Integer mailId) {
        this.mailId = mailId;
    }

    public Integer getMailUser() {
        return mailUser;
    }

    public void setMailUser(Integer mailUser) {
        this.mailUser = mailUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLookState() {
        return lookState;
    }

    public void setLookState(Integer lookState) {
        this.lookState = lookState;
    }

    public Integer getStarState() {
        return starState;
    }

    public void setStarState(Integer starState) {
        this.starState = starState;
    }

    @Override
    public String toString() {
        return "rw_mail_user{" +
                "id=" + id +
                ", mailId=" + mailId +
                ", mailUser=" + mailUser +
                ", status=" + status +
                ", lookState=" + lookState +
                ", starState=" + starState +
                ", userss=" + userss +
                '}';
    }
}