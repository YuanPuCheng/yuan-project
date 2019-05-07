package com.zihui.cwoa.system.pojo;

import java.util.List;

public class sys_task {
    private Integer taskId;

    private Integer taskUserId;

    private String taskRemark;

    private Integer taskStatus;

    private String attachment;

    private String ts;

    private String tempVar1;

    private String tempVar2;

    private Integer tempInt1;

    private Integer tempInt2;

    private List<sys_task_b> taskBs;

    private sys_users users;

    private sys_file file;

    public sys_file getFile() {
        return file;
    }

    public void setFile(sys_file file) {
        this.file = file;
    }

    public List<sys_task_b> getTaskBs() {
        return taskBs;
    }

    public void setTaskBs(List<sys_task_b> taskBs) {
        this.taskBs = taskBs;
    }

    public sys_users getUsers() {
        return users;
    }

    public void setUsers(sys_users users) {
        this.users = users;
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

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment == null ? null : attachment.trim();
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
}