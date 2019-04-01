package com.zihui.cwoa.system.shiro;

import com.zihui.cwoa.system.pojo.*;
import com.zihui.cwoa.system.service.*;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyShiroRealm extends AuthorizingRealm {

    //告诉shiro如何根据获取到的用户信息中的密码和盐值来校验密码
    {
        //设置用于匹配密码的CredentialsMatcher
        HashedCredentialsMatcher hashMatcher = new HashedCredentialsMatcher();
        hashMatcher.setHashAlgorithmName("MD5");
        hashMatcher.setStoredCredentialsHexEncoded(true);
        hashMatcher.setHashIterations(1);
        this.setCredentialsMatcher(hashMatcher);
    }


    private static Logger log = Logger.getLogger(MyShiroRealm.class);
    @Resource
    private sys_userService user_service;
    @Resource
    private sys_departmentService departmentService;
    @Resource
    private sys_department_menuService department_menuService;
    @Resource
    private sys_user_departmentService user_departmentService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        sys_user user =(sys_user)principalCollection.getPrimaryPrincipal();
        Set departmentSet = new HashSet();
        Set menusSet = new HashSet();
        List<String> b_id  = new ArrayList<>();
        //查询用户包含角色
        sys_user user1 =user_service.selectDepartmentToUser(user.getUserId());
        for (sys_department p: user1.getDepartments()) {
            departmentSet.add(p.getDepartmentCode());//添加部门编码
            b_id.add(p.getDepartmentId());
        }
        //查询用户的菜单，也就是权限
        List<sys_department> menus =departmentService.selectMenu(b_id);
        for(sys_department d :menus){
            for(sys_menu m:d.getMenus()){
                menusSet.add(m.getMenuCode());//添加菜单编码
                for(sys_menu mm:m.getMenus()){
                    menusSet.add(mm.getMenuCode());//添加二级菜单
                    for(sys_menu mmm:mm.getMenus()){
                        menusSet.add(mmm.getMenuCode());//添加三级菜单
                    }
                }
            }
        }


        log.info("所属部门编码"+departmentSet.toString());
        log.info("所属菜单权限编码"+menusSet.toString());

        //告诉Shiro用户拥有的角色与权限
        simpleAuthorizationInfo.addRoles(departmentSet);
        simpleAuthorizationInfo.addStringPermissions(menusSet);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        // 1、登录认证的方法需要先执行，需要用他来判断登录的用户信息是否合法
        String usercode = (String) token.getPrincipal();    // 取得用户工号
        log.info("获取用户名"+usercode);
        // 需要通过用户名取得用户的完pub_user整信息，利用业务层操作
        if(usercode==null||usercode==""){
            throw new IncorrectCredentialsException("用户工号为空！");
        }
        sys_user user = null;
        try {
             user=user_service.selectUserByLogin(usercode);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user == null) {
            throw new UnknownAccountException("该用户名称不存在！");
        } else {    // 进行密码的验证处理
            String password =new String((char[]) token.getCredentials());
            log.info("获取登录密码"+password);
            Object result = new SimpleHash("MD5",password,usercode);

            log.info(result.toString());
            // 将数据库中的密码与输入的密码进行比较，这样就可以确定当前用户是否可以正常登录

            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getUserPassword(), getName());
            info.setCredentialsSalt(ByteSource.Util.bytes(usercode));
            return info;
        }
    }


    public static void main(String[] args){

        System.out.println( new SimpleHash("MD5","123","admin").toString());
    }
}
