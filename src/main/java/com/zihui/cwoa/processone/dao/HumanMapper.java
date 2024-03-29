package com.zihui.cwoa.processone.dao;

import com.zihui.cwoa.financial.pojo.RoleAllUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface HumanMapper {

    /**
     *  查询请假出差记录
     */
    List<Map<String,Object>> queryLeaveByVo(String userId, String project, String leaveYear,
                                            String leaveMonth, int page, int limit);

    /**
     *  查询请假出差记录条数
     */
    Integer countLeaveByVo(String userId, String project, String leaveYear, String leaveMonth);

    /**
     *  查询用户的出差请假记录详情
     */
    List<Map<String,Object>> queryLeaveDetail(String userId, String project, String leaveYear, String leaveMonth);

    /**
     *  查询所有角色及该角色下的所有用户
     */
    List<RoleAllUser> queryRoleAllUser();

    /**
     *  根据父ID查询菜单名称数组
     */
    List<String> queryMenuByParentId(String userId,String parentId);

    /**
     *  查询角色用以下拉框选择
     */
    List<Map<String,Object>> queryRoleSelect();

    /**
     *  根据用户ID查询用户部门
     */
    String selectDepartmentById(String userId);

    /**
     *  根据所有用户名字部门
     */
    List<Map<String,Object>> queryNameDepartment();
}
