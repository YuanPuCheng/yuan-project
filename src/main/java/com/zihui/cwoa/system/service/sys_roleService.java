package com.zihui.cwoa.system.service;


import com.zihui.cwoa.system.common.Basecommon;
import com.zihui.cwoa.system.common.CallbackResult;
import com.zihui.cwoa.system.common.RedisUtils;
import com.zihui.cwoa.system.dao.sys_roleMapper;
import com.zihui.cwoa.system.dao.sys_role_menuMapper;
import com.zihui.cwoa.system.pojo.sys_role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class sys_roleService {


    @Resource
    private sys_roleMapper roleMapper;
    @Resource
    private sys_role_menuMapper roleMenuMapper;
    @Resource
    private RedisUtils redisUtils;

    public int deleteByPrimaryKey(Integer roleId){
        redisUtils.deleteCache("user*");
        redisUtils.deleteCache("role*");
        return roleMapper.deleteByPrimaryKey(roleId);
    };


    public int insertSelective(sys_role record){
        redisUtils.deleteCache("user*");
        redisUtils.deleteCache("role*");
        return insertSelective(record);
    };

    public sys_role selectByPrimaryKey(Integer roleId){
        return roleMapper.selectByPrimaryKey(roleId);
    };

    public int  updateByPrimaryKeySelective(sys_role record){
        redisUtils.deleteCache("user*");
        redisUtils.deleteCache("role*");
        return roleMapper.updateByPrimaryKeySelective(record);
    };

    public List<sys_role> selcetRoleByUserId(Integer userId){
        return roleMapper.selcetRoleByUserId(userId);
    };

    public List<sys_role> selectRolebySelect(Integer roleId){
        return roleMapper.selectRolebySelect(roleId);
    };

    public List<sys_role> selectRoleByPage(Integer page, Integer limit){
        if(page==1){
            page =0;
        }else{
            page = (page-1)*limit;
        }
        return roleMapper.selectRoleByPage(page,limit);
    };

    public Integer selectRoleByPageCount(){
        return roleMapper.selectRoleByPageCount();
    };

    public CallbackResult del(Integer roleId){
        CallbackResult result =new CallbackResult();
        try {
            roleMapper.deleteByPrimaryKey(roleId);
            roleMenuMapper.deleteMenuByRoleId(roleId);

        }catch (Exception e){
            e.printStackTrace();
            result.setResult(400);
            result.setMessage("删除失败");
            return result;
        }

        result.setResult(200);
        result.setMessage("删除成功");
        return result;

    }
    public CallbackResult deletes(String roleIds){
        CallbackResult result =new CallbackResult();


        String []id = roleIds.split(",");
        for (String roleId:id){
            if(!Basecommon.isNullStr(roleId)){
                try {
                    roleMapper.deleteByPrimaryKey(Integer.parseInt(roleId));
                    roleMenuMapper.deleteMenuByRoleId(Integer.parseInt(roleId));
                }catch (Exception e){
                    e.printStackTrace();
                    result.setResult(400);
                    result.setMessage("删除失败");
                    return result;
                }

            }
        }


        result.setResult(200);
        result.setMessage("删除成功");
        return result;
    }


    public CallbackResult add(String roleName, Integer roleLevel, Integer roleParent, String menuId){
        sys_role role = new sys_role();
        CallbackResult result =new CallbackResult();
        role.setRoleName(roleName);
        role.setRoleParentId(roleParent);
        role.setRoleLevel(roleLevel);
        try {
            roleMapper.insertSelective(role);
            String [] menus = menuId.split(",");
            for (String id:menus){
                if(!Basecommon.isNullStr(id)){
                    roleMenuMapper.insertRoleAndMenu(role.getRoleId(),Integer.parseInt(id));
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            result.setResult(400);
            result.setMessage("添加失败");
            return result;
        }

        result.setResult(200);
        result.setMessage("添加成功");
        return result;
    }

    public CallbackResult edit(sys_role role, String menuIds,String oldmenuIds){
        CallbackResult result =new CallbackResult();
        try {
            roleMapper.updateByPrimaryKeySelective(role);
            Set<String> in = new HashSet();//新增
            Set<String> de = new HashSet();//删除
               if(!Basecommon.isNullStr(menuIds)){
                    String menu []= menuIds.split(",");//1,2
                    String olds [] = oldmenuIds.split(",");//1


                    Set set1 = new HashSet();
                    Set set2 = new HashSet();

                    for(String d:menu){
                        if(!Basecommon.isNullStr(d)){
                            set1.add(d);
                        }

                    }
                    for (String dd:olds){
                        if(!Basecommon.isNullStr(dd)){
                            set2.add(dd);
                        }

                    }
                    in.addAll(set1);
                    in.removeAll(set2);
                    de.addAll(set2);
                    de.removeAll(set1);
                   try {
                       for (String menuid:de){
                           int  count = roleMenuMapper.deleteRoleAndMenu(role.getRoleId(),Integer.parseInt(menuid));
                       }
                       for (String menuid:in){
                           int  count= roleMenuMapper.insertRoleAndMenu(role.getRoleId(),Integer.parseInt(menuid));
                       }
                   }catch (Exception e){
                       result.setResult(400);
                       result.setMessage("添加权限失败");
                       return result;

                   }
                }


        }catch (Exception e){
            e.printStackTrace();
            result.setResult(400);
            result.setMessage("修改失败");
            return result;
        }

        result.setResult(200);
        result.setMessage("修改成功");


        return result;
    }

    public List<sys_role> selectRoleToUser(Integer userId){
        return roleMapper.selectRoleToUser(userId);
    };


}
