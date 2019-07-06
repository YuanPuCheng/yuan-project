package com.zihui.cwoa.routine.pojo;

import com.zihui.cwoa.system.pojo.sys_users;

import java.util.List;

public class rw_mail {
    private Integer mailId;

    private Integer senderUser;

    private String mailTheme;

    private String mailContent;

    private String attachment;

    private String sendTime;

    private Integer state;

    private Integer starStatus;

    private sys_users users;

    private List<rw_mail_user> mailUsers;

    public List<rw_mail_user> getMailUsers() {
        return mailUsers;
    }

    public void setMailUsers(List<rw_mail_user> mailUsers) {
        this.mailUsers = mailUsers;
    }

    public sys_users getUsers() {
        return users;
    }

    public void setUsers(sys_users users) {
        this.users = users;
    }

    public Integer getMailId() {
        return mailId;
    }

    public void setMailId(Integer mailId) {
        this.mailId = mailId;
    }

    public Integer getSenderUser() {
        return senderUser;
    }

    public void setSenderUser(Integer senderUser) {
        this.senderUser = senderUser;
    }

    public String getMailTheme() {
        return mailTheme;
    }

    public void setMailTheme(String mailTheme) {
        this.mailTheme = mailTheme == null ? null : mailTheme.trim();
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent == null ? null : mailContent.trim();
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment == null ? null : attachment.trim();
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime == null ? null : sendTime.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getStarStatus() {
        return starStatus;
    }

    public void setStarStatus(Integer starStatus) {
        this.starStatus = starStatus;
    }


    @Override
    public String toString() {
        return "rw_mail{" +
                "mailId=" + mailId +
                ", senderUser=" + senderUser +
                ", mailTheme='" + mailTheme + '\'' +
                ", mailContent='" + mailContent + '\'' +
                ", attachment='" + attachment + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", state=" + state +
                ", starStatus=" + starStatus +
                ", users=" + users +
                ", mailUsers=" + mailUsers +
                '}';
    }
}