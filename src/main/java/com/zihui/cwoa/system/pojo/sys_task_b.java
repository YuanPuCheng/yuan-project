package com.zihui.cwoa.system.pojo;

public class sys_task_b {
    private Integer taskBId;

    private Integer taskId;

    private Integer taskUserIdChan;

    private String taskSuggestion;

    private Integer taskAttachment;

    private String taskLookTime;

    private String taskEndTime;

    private Integer taskStatus;

    private String tempVar1;

    private Integer tempInt1;

    private sys_users users;

    private sys_file file;

    public sys_file getFile() {
        return file;
    }

    public void setFile(sys_file file) {
        this.file = file;
    }

    public sys_users getUsers() {
        return users;
    }

    public void setUsers(sys_users users) {
        this.users = users;
    }

    public Integer getTaskBId() {
        return taskBId;
    }

    public void setTaskBId(Integer taskBId) {
        this.taskBId = taskBId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getTaskUserIdChan() {
        return taskUserIdChan;
    }

    public void setTaskUserIdChan(Integer taskUserIdChan) {
        this.taskUserIdChan = taskUserIdChan;
    }

    public String getTaskSuggestion() {
        return taskSuggestion;
    }

    public void setTaskSuggestion(String taskSuggestion) {
        this.taskSuggestion = taskSuggestion == null ? null : taskSuggestion.trim();
    }

    public Integer getTaskAttachment() {
        return taskAttachment;
    }

    public void setTaskAttachment(Integer taskAttachment) {
        this.taskAttachment = taskAttachment;
    }

    public String getTaskLookTime() {
        return taskLookTime;
    }

    public void setTaskLookTime(String taskLookTime) {
        this.taskLookTime = taskLookTime == null ? null : taskLookTime.trim();
    }

    public String getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(String taskEndTime) {
        this.taskEndTime = taskEndTime == null ? null : taskEndTime.trim();
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTempVar1() {
        return tempVar1;
    }

    public void setTempVar1(String tempVar1) {
        this.tempVar1 = tempVar1 == null ? null : tempVar1.trim();
    }

    public Integer getTempInt1() {
        return tempInt1;
    }

    public void setTempInt1(Integer tempInt1) {
        this.tempInt1 = tempInt1;
    }


}