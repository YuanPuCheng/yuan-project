package com.zihui.cwoa.system.pojo;

import java.util.List;

public class sys_user {
    private Integer userId;

    private String userCode;

    private String userPassword;

    private Integer status;

    private String userName;

    private String email;

    private String createTime;

    private String errorCount;

    private Integer departmentId;

    private String images;

    private String phone;

    private String ts;

    private String ip;

    private Integer projectId;

    private String tempVar1;

    private String tempVar2;

    private String tempVar3;

    private Integer tempInt1;

    private Integer tempInt2;

    private Integer tempInt3;

    private String sex;

    private String age;

    private String lastTime;

    private String loginTime;

    private String bankCardNum;

    private String idNum;

    private sys_project project;

    private List<sys_role> roles;

    private sys_department department;


    public sys_project getProject() {
        return project;
    }

    public void setProject(sys_project project) {
        this.project = project;
    }

    public List<sys_role> getRoles() {
        return roles;
    }

    public void setRoles(List<sys_role> roles) {
        this.roles = roles;
    }

    public sys_department getDepartment() {
        return department;
    }

    public void setDepartment(sys_department department) {
        this.department = department;
    }

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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(String errorCount) {
        this.errorCount = errorCount == null ? null : errorCount.trim();
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images == null ? null : images.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts == null ? null : ts.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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

    public String getTempVar3() {
        return tempVar3;
    }

    public void setTempVar3(String tempVar3) {
        this.tempVar3 = tempVar3 == null ? null : tempVar3.trim();
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

    public Integer getTempInt3() {
        return tempInt3;
    }

    public void setTempInt3(Integer tempInt3) {
        this.tempInt3 = tempInt3;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age == null ? null : age.trim();
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime == null ? null : lastTime.trim();
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime == null ? null : loginTime.trim();
    }

    public String getBankCardNum() {
        return bankCardNum;
    }

    public void setBankCardNum(String bankCardNum) {
        this.bankCardNum = bankCardNum == null ? null : bankCardNum.trim();
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum == null ? null : idNum.trim();
    }


    @Override
    public String toString() {
        return "sys_user{" +
                "userId=" + userId +
                ", userCode='" + userCode + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", status=" + status +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", createTime='" + createTime + '\'' +
                ", errorCount='" + errorCount + '\'' +
                ", departmentId=" + departmentId +
                ", images='" + images + '\'' +
                ", phone='" + phone + '\'' +
                ", ts='" + ts + '\'' +
                ", ip='" + ip + '\'' +
                ", projectId=" + projectId +
                ", tempVar1='" + tempVar1 + '\'' +
                ", tempVar2='" + tempVar2 + '\'' +
                ", tempVar3='" + tempVar3 + '\'' +
                ", tempInt1=" + tempInt1 +
                ", tempInt2=" + tempInt2 +
                ", tempInt3=" + tempInt3 +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", lastTime='" + lastTime + '\'' +
                ", loginTime='" + loginTime + '\'' +
                ", bankCardNum='" + bankCardNum + '\'' +
                ", idNum='" + idNum + '\'' +
                ", project=" + project +
                ", roles=" + roles +
                ", department=" + department +
                '}';
    }
}