package com.zihui.cwoa.system.service;


import com.zihui.cwoa.system.dao.sys_department_menuMapper;
import com.zihui.cwoa.system.pojo.sys_department_menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sys_department_menuService {


    @Resource
    private sys_department_menuMapper department_menuMapper;

    /**
     *  根据部门菜单id删除记录
     *  @param id id
     *  @return int 0=删除失败
     */
    public int deleteByPrimaryKey(Integer id){
        return department_menuMapper.deleteByPrimaryKey(id);
    }
    /**
     *  新增部门菜单
     *  @param departmentId id
     *   @param menuId
     *  @return int 0=新增失败
     */
    public int insert(Integer departmentId, Integer menuId){
        return department_menuMapper.insert(departmentId,menuId);
    }
    /**
     *  根据部门菜单id查询关联信息
     *  @param id 传入用户id
     *  @return 返回部门菜单对象 1条记录
     */
    public sys_department_menu selectByPrimaryKey(Integer id){
        return department_menuMapper.selectByPrimaryKey(id);
    }
    /**
     *  根据部门菜单id修改记录
     *  @param record 包含要修改的id
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
    public List<Integer> selectMenuIdByUserId(Integer userId){
        return department_menuMapper.selectMenuIdByUserId(userId);
    }

    /**
     *  根据部门id查询菜单id
     *  @param departmentId 一个部门id
     *  @return menuId 返回对于的菜单id
     */
    public List<Integer> selectMenuIdByDeparId(@Param("departmentId") Integer departmentId){
        return department_menuMapper.selectMenuIdByDeparId(departmentId);
    };

    /**
     *  删除部门菜单关联表
     *  @param departmentId 一个部门id
     *  @param menuId 一个菜单id
     *  @return
     */
    public int deleteByDeparIdAndMenuId(@Param("departmentId") Integer departmentId,@Param("menuId") Integer menuId){
        return department_menuMapper.deleteByDeparIdAndMenuId(departmentId,menuId);
    };
}
