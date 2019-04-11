package com.zihui.cwoa.system.service;

import com.zihui.cwoa.system.dao.sys_menuMapper;
import com.zihui.cwoa.system.pojo.sys_menu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sys_menuService {


    @Resource
    private sys_menuMapper menuMapper;

    /**
     *  根据菜单id删除记录
     *  @param menuId 传入菜单id
     *  @return int 0=删除失败
     */
    public int deleteByPrimaryKey(Integer menuId){
        return menuMapper.deleteByPrimaryKey(menuId);
    };

    /**
     *  新增菜单
     *  @param record 传入用户菜单对象
     *  @return int 0=新增失败
     */
    public int insertSelective(sys_menu record){
        return menuMapper.insertSelective(record);
    };
    /**
     *  根据菜单id查询菜单
     *  @param menuId 传入用户菜单id
     *  @return 返回菜单对象 1条记录
     */
    public sys_menu selectByPrimaryKey(Integer menuId){
        return menuMapper.selectByPrimaryKey(menuId);
    };

    /**
     *  修改菜单
     *  @param record 传入用户菜单对象，包含修改的id
     *  @return int 0=修改失败
     */
    public int updateByPrimaryKeySelective(sys_menu record){
        return menuMapper.updateByPrimaryKeySelective(record);
    };

    /**
     *  查询菜单
     *  @param menuId 传入id
     *  @return List 菜单List对象
     */
    public List<sys_menu> selectMenuByMenuId(List<Integer> menuId){
        return menuMapper.selectMenuByMenuId(menuId);
    };
}
