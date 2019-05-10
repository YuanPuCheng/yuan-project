package com.zihui.cwoa.financial.pojo;

import com.zihui.cwoa.system.pojo.sys_users;

import java.util.List;

/**
 * 角色及该角色下的所有用户
 */
public class RoleAllUser {

    private String role_id;

    private String role_name;

    private List<sys_users> list;

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public List<sys_users> getList() {
        return list;
    }

    public void setList(List<sys_users> list) {
        this.list = list;
    }
}
