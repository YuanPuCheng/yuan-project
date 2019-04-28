package com.zihui.cwoa.system.pojo;


/**
 * 项目进度表
 */

public class pro_schedule {
    private Integer scheduleId; //进度id

    private Integer projectId;//关联项目id

    private String schStartTime;//

    private String schEndTime;

    private String schContent;//进度内容

    private String ts;

    private String tempVar1;

    private String tempVar2;

    private Integer tempInt1;

    private Integer tempInt2;

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getSchStartTime() {
        return schStartTime;
    }

    public void setSchStartTime(String schStartTime) {
        this.schStartTime = schStartTime;
    }

    public String getSchEndTime() {
        return schEndTime;
    }

    public void setSchEndTime(String schEndTime) {
        this.schEndTime = schEndTime;
    }

    public String getSchContent() {
        return schContent;
    }

    public void setSchContent(String schContent) {
        this.schContent = schContent;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getTempVar1() {
        return tempVar1;
    }

    public void setTempVar1(String tempVar1) {
        this.tempVar1 = tempVar1;
    }

    public String getTempVar2() {
        return tempVar2;
    }

    public void setTempVar2(String tempVar2) {
        this.tempVar2 = tempVar2;
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
        return "pro_schedule{" +
                "scheduleId=" + scheduleId +
                ", projectId=" + projectId +
                ", schStartTime='" + schStartTime + '\'' +
                ", schEndTime='" + schEndTime + '\'' +
                ", schContent='" + schContent + '\'' +
                ", ts='" + ts + '\'' +
                ", tempVar1='" + tempVar1 + '\'' +
                ", tempVar2='" + tempVar2 + '\'' +
                ", tempInt1=" + tempInt1 +
                ", tempInt2=" + tempInt2 +
                '}';
    }
}