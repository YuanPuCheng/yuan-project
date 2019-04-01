package com.zihui.cwoa.system.service;


import com.zihui.cwoa.system.dao.sys_department_menuMapper;
import com.zihui.cwoa.system.pojo.sys_department_menu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sys_department_menuService {


    @Resource
    private sys_department_menuMapper department_menuMapper;

    /**
     *  根据部门菜单id删除记录
     *  @param departmentId id
     *  @return int 0=删除失败
     */
    public int deleteByPrimaryKey(String id){
        return department_menuMapper.deleteByPrimaryKey(id);
    }
    /**
     *  新增部门菜单
     *  @param departmentId id
     *  @return int 0=新增失败
     */
    public int insert(sys_department_menu record){
        return department_menuMapper.insert(record);
    }
    /**
     *  根据部门菜单id查询关联信息
     *  @param id 传入用户id
     *  @return 返回部门菜单对象 1条记录
     */
    public sys_department_menu selectByPrimaryKey(String id){
        return department_menuMapper.selectByPrimaryKey(id);
    }
    /**
     *  根据部门菜单id修改记录
     *  @param sys_department_menu 包含要修改的id
     *  @return int 0=删除失败
     */
    public int updateByPrimaryKey(sys_department_menu record){
        return department_menuMapper.updateByPrimaryKey(record);
    }
    /**
     *  根据用户id查询菜单id
     *  @param userId 一个或多个部门id
     *  @return menuId 返回对于的菜单id
     */
    public List<String> selectMenuIdByUserId(String userId){
        return department_menuMapper.selectMenuIdByUserId(userId);
    }

}
