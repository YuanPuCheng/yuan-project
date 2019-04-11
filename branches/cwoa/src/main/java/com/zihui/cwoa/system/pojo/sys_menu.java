package com.zihui.cwoa.system.pojo;

import java.util.List;

/**
 *  权限菜单表
 */
public class sys_menu {

    private Integer menuId;//菜单id

    private String parentId;//上级菜单id

    private String menuName;//菜单名称

    private Integer status;//状态  0 正常 1 注销

    private String menuCode;//菜单编码  shiro注解对应这里

    private Integer menuType;//菜单类型  1 虚拟功能，如系统管理 2 操作权限，如新增修改

    private Integer menuUrl;//菜单url

    private String ts;//时间戳

    private String tempVar1;

    private String tempVar2;

    private Integer tempInt1;

    private Integer tempInt2;

    private List<sys_menu> menus;//下级菜单 如新增修改

    public List<sys_menu> getMenus() {
        return menus;
    }

    public void setMenus(List<sys_menu> menus) {
        this.menus = menus;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode == null ? null : menuCode.trim();
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public Integer getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(Integer menuUrl) {
        this.menuUrl = menuUrl;
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
        return "sys_menu{" +
                "menuId='" + menuId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", menuName='" + menuName + '\'' +
                ", status=" + status +
                ", menuCode='" + menuCode + '\'' +
                ", menuType=" + menuType +
                ", menuUrl=" + menuUrl +
                ", ts='" + ts + '\'' +
                ", tempVar1='" + tempVar1 + '\'' +
                ", tempVar2='" + tempVar2 + '\'' +
                ", tempInt1=" + tempInt1 +
                ", tempInt2=" + tempInt2 +
                '}';
    }
}