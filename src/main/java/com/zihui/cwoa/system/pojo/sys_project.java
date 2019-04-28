package com.zihui.cwoa.system.pojo;

import java.util.List;

public class sys_project {
    private Integer projectId;

    private String projectName;  //项目名称

    private String projectIntroduction;  //项目介绍

    private String projectAddress; //项目地址

    private Integer status;  //状态

    private String startTime;  //开始时间

    private String endTime;//结束时间

    private String ts;

    private String attachment;  //附件

    private Double projectMoney;  //项目总金额

    private Integer projectSchedule;  //项目进度

    private String progressIntroduction;   //进度介绍

    private String cooperationCorporate;  //合作公司名称

    private String legalRepresentative;  //法定代表人

    private Integer projectUserId;  //项目负责人

    private String tempVar1;

    private String tempVar2;

    private Integer tempInt1;

    private Integer tempInt2;

    private List<pro_schedule> schedules;//进度表

    private sys_user user;//用户


    public List<pro_schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<pro_schedule> schedules) {
        this.schedules = schedules;
    }

    public sys_user getUser() {
        return user;
    }

    public void setUser(sys_user user) {
        this.user = user;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getProjectIntroduction() {
        return projectIntroduction;
    }

    public void setProjectIntroduction(String projectIntroduction) {
        this.projectIntroduction = projectIntroduction == null ? null : projectIntroduction.trim();
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress == null ? null : projectAddress.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Double getProjectMoney() {
        return projectMoney;
    }

    public void setProjectMoney(Double projectMoney) {
        this.projectMoney = projectMoney;
    }

    public Integer getProjectSchedule() {
        return projectSchedule;
    }

    public void setProjectSchedule(Integer projectSchedule) {
        this.projectSchedule = projectSchedule;
    }

    public String getProgressIntroduction() {
        return progressIntroduction;
    }

    public void setProgressIntroduction(String progressIntroduction) {
        this.progressIntroduction = progressIntroduction;
    }

    public String getCooperationCorporate() {
        return cooperationCorporate;
    }

    public void setCooperationCorporate(String cooperationCorporate) {
        this.cooperationCorporate = cooperationCorporate;
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

    public Integer getProjectUserId() {
        return projectUserId;
    }

    public void setProjectUserId(Integer projectUserId) {
        this.projectUserId = projectUserId;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts == null ? null : ts.trim();
    }

    public String getTempVar1() {
        return tempVar1;
    }

    public void setTempVar1(String tempVar1) {
        this.tempVar1 = tempVar1 == null ? null : tempVar1.trim();
    }

    public String getTempVar2() {
        return tempVar2;
    }

    public void setTempVar2(String tempVar2) {
        this.tempVar2 = tempVar2 == null ? null : tempVar2.trim();
    }

    public Integer getTempInt1() {
        return tempInt1;
    }

    public void setTempInt1(Integer tempInt1) {
        this.tempInt1 = tempInt1;
    }

    public Integer getTempInt2() {
        return tempInt2;
    }

    public void setTempInt2(Integer tempInt2) {
        this.tempInt2 = tempInt2;
    }

    @Override
    public String toString() {
        return "sys_project{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", projectIntroduction='" + projectIntroduction + '\'' +
                ", projectAddress='" + projectAddress + '\'' +
                ", status=" + status +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", ts='" + ts + '\'' +
                ", attachment='" + attachment + '\'' +
                ", projectMoney=" + projectMoney +
                ", projectSchedule=" + projectSchedule +
                ", progressIntroduction='" + progressIntroduction + '\'' +
                ", cooperationCorporate='" + cooperationCorporate + '\'' +
                ", legalRepresentative='" + legalRepresentative + '\'' +
                ", projectUserId=" + projectUserId +
                ", tempVar1='" + tempVar1 + '\'' +
                ", tempVar2='" + tempVar2 + '\'' +
                ", tempInt1=" + tempInt1 +
                ", tempInt2=" + tempInt2 +
                ", schedules=" + schedules +
                ", user=" + user +
                '}';
    }
}