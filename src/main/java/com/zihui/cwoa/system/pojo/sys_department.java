package com.zihui.cwoa.system.pojo;

import java.util.List;

public class sys_department {

    private Integer departmentId;//部门id

    private String departmentName;//部门名称

    private String departmentCode;//部门编码

    private Integer status;//状态 0 正常 1 注销

    private String ts;//时间戳 每次更新修改成当前时间

    private List<sys_menu> menus;

    public List<sys_menu> getMenus() {
        return menus;
    }

    public void setMenus(List<sys_menu> menus) {
        this.menus = menus;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode == null ? null : departmentCode.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts == null ? null : ts.trim();
    }





    @Override
    public String toString() {
        return "sys_department{" +
                "departmentId='" + departmentId + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", departmentCode='" + departmentCode + '\'' +
                ", status=" + status +
                ", ts='" + ts + '\'' +
                '}';
    }
}