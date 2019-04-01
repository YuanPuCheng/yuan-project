package com.zihui.cwoa.system.pojo;


/**
 *  部门关联菜单表
 */

public class sys_department_menu {

    private String id;

    private String departmentId;//部门id

    private String menuId;//菜单id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }



    @Override
    public String toString() {
        return "sys_department_menu{" +
                "id='" + id + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", menuId='" + menuId + '\'' +
                '}';
    }
}