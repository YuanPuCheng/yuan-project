package com.zihui.cwoa.system.pojo;

import java.util.List;

public class sys_role {
    private Integer roleId;

    private String roleName;

    private Integer roleLevel;

    private String roleCode;

    private Integer roleParentId;

    private sys_role parentRole;

    private List<sys_users> users;

    private List<sys_menu> menus;

    public List<sys_menu> getMenus() {
        return menus;
    }

    public void setMenus(List<sys_menu> menus) {
        this.menus = menus;
    }

    public sys_role getParentRole() {
        return parentRole;
    }

    public void setParentRole(sys_role parentRole) {
        this.parentRole = parentRole;
    }

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

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    public Integer getRoleParentId() {
        return roleParentId;
    }

    public void setRoleParentId(Integer roleParentId) {
        this.roleParentId = roleParentId;
    }
}