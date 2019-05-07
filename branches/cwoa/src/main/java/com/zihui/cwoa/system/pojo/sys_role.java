package com.zihui.cwoa.system.pojo;

import java.util.List;

public class sys_role {
    private Integer roleId;

    private String roleName;

    private Integer roleLevel;

    private List<sys_users> users;

    public List<sys_users> getUsers() {
        return users;
    }

    public void setUsers(List<sys_users> users) {
        this.users = users;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public Integer getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(Integer roleLevel) {
        this.roleLevel = roleLevel;
    }

    @Override
    public String toString() {
        return "sys_role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleLevel=" + roleLevel +
                ", users=" + users +
                '}';
    }
}