package com.zihui.cwoa.system.pojo;

import java.util.List;

/**
 *  用户表
 */
public class sys_user {

    private Integer userId;//用户id

    private String userCode;//用户工号  做登录用

    private String userPassword;//用户密码

    private String salt;//盐 加密

    private Integer status;//状态 0 正常 1注销

    private String userName;//用户名称

    private String email;//邮箱

    private String createTime;//创建时间

    private String errorCount;//登录失败次数

    private String roleId;//部门id

    private String images;//头像路径

    private String phone;//手机号

    private String ts;//时间戳

    private String ip;//登录ip

    private String sex;//性别

    private String age;//年龄

    private String lastTime;//上次登录时间

    private String loginTime;//登录时间

    private String idNum; //身份证号

    private String bankCardNum;//银行卡号

    private String tempVar1;

    private String tempVar2;

    private String tempVar3;

    private Integer tempInt1;// 项目表id

    private Integer tempInt2;

    private Integer tempInt3;

    private List<sys_department> departments;//部门集合

    private sys_project project;//项目对象

    public sys_project getProject() {
        return project;
    }

    public void setProject(sys_project project) {
        this.project = project;
    }

    public List<sys_department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<sys_department> departments) {
        this.departments = departments;
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
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getBankCardNum() {
        return bankCardNum;
    }

    public void setBankCardNum(String bankCardNum) {
        this.bankCardNum = bankCardNum;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
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
        this.phone = phone;
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

    @Override
    public String toString() {
        return "sys_user{" +
                "userId=" + userId +
                ", userCode='" + userCode + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", salt='" + salt + '\'' +
                ", status=" + status +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", createTime='" + createTime + '\'' +
                ", errorCount='" + errorCount + '\'' +
                ", roleId='" + roleId + '\'' +
                ", images='" + images + '\'' +
                ", phone='" + phone + '\'' +
                ", ts='" + ts + '\'' +
                ", ip='" + ip + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", lastTime='" + lastTime + '\'' +
                ", loginTime='" + loginTime + '\'' +
                ", idNum='" + idNum + '\'' +
                ", bankCardNum='" + bankCardNum + '\'' +
                ", tempVar1='" + tempVar1 + '\'' +
                ", tempVar2='" + tempVar2 + '\'' +
                ", tempVar3='" + tempVar3 + '\'' +
                ", tempInt1=" + tempInt1 +
                ", tempInt2=" + tempInt2 +
                ", tempInt3=" + tempInt3 +
                ", departments=" + departments +
                ", project=" + project +
                '}';
    }
}