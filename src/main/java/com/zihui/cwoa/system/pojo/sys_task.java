package com.zihui.cwoa.system.pojo;

public class sys_task {
    private Integer taskId;

    private Integer taskUserId;

    private Integer taskUserIdChan;

    private String taskRemark;

    private Integer taskStatus;

    private String taskSuggestion;

    private String attachment;

    private String attachmentChan;

    private String taskStartTime;

    private String taskEndTime;

    private String ts;

    private String tempVar1;

    private String tempVar2;

    private Integer tempInt1;

    private Integer tempInt2;

    private sys_users user;

    private sys_users userChan;

    public sys_users getUser() {
        return user;
    }

    public void setUser(sys_users user) {
        this.user = user;
    }

    public sys_users getUserChan() {
        return userChan;
    }

    public void setUserChan(sys_users userChan) {
        this.userChan = userChan;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getTaskUserId() {
        return taskUserId;
    }

    public void setTaskUserId(Integer taskUserId) {
        this.taskUserId = taskUserId;
    }

    public Integer getTaskUserIdChan() {
        return taskUserIdChan;
    }

    public void setTaskUserIdChan(Integer taskUserIdChan) {
        this.taskUserIdChan = taskUserIdChan;
    }

    public String getTaskRemark() {
        return taskRemark;
    }

    public void setTaskRemark(String taskRemark) {
        this.taskRemark = taskRemark == null ? null : taskRemark.trim();
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskSuggestion() {
        return taskSuggestion;
    }

    public void setTaskSuggestion(String taskSuggestion) {
        this.taskSuggestion = taskSuggestion == null ? null : taskSuggestion.trim();
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment == null ? null : attachment.trim();
    }

    public String getAttachmentChan() {
        return attachmentChan;
    }

    public void setAttachmentChan(String attachmentChan) {
        this.attachmentChan = attachmentChan == null ? null : attachmentChan.trim();
    }

    public String getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(String taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public String getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(String taskEndTime) {
        this.taskEndTime = taskEndTime;
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
        return "sys_task{" +
                "taskId=" + taskId +
                ", taskUserId=" + taskUserId +
                ", taskUserIdChan=" + taskUserIdChan +
                ", taskRemark='" + taskRemark + '\'' +
                ", taskStatus=" + taskStatus +
                ", taskSuggestion='" + taskSuggestion + '\'' +
                ", attachment='" + attachment + '\'' +
                ", attachmentChan='" + attachmentChan + '\'' +
                ", taskStartTime='" + taskStartTime + '\'' +
                ", taskEndTime='" + taskEndTime + '\'' +
                ", ts='" + ts + '\'' +
                ", tempVar1='" + tempVar1 + '\'' +
                ", tempVar2='" + tempVar2 + '\'' +
                ", tempInt1=" + tempInt1 +
                ", tempInt2=" + tempInt2 +
                ", user=" + user +
                ", userChan=" + userChan +
                '}';
    }
}