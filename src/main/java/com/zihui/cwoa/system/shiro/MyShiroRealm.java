package com.zihui.cwoa.system.shiro;

import com.zihui.cwoa.system.common.Basecommon;
import com.zihui.cwoa.system.common.Common;
import com.zihui.cwoa.system.common.DateUtils;
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
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
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
    @Lazy
    private sys_userService user_service;
    @Resource
    private sys_menuService menuService;
    @Resource
    private sys_roleService roleService;

    @Resource
    private HttpServletRequest request;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        sys_user user =(sys_user)principalCollection.getPrimaryPrincipal();
        Set roleSet = new HashSet();
        Set menusSet = new HashSet();
        List<Integer> role_id  = new ArrayList<>();


        //查询用户包含角色
        List<sys_role> roles =roleService.selcetRoleByUserId(user.getUserId());
        for (sys_role role: roles) {
            roleSet.add(role.getRoleCode());//添加添加角色编码
            role_id.add(role.getRoleId());//把角色id存入list
        }
        //查询用户的菜单，也就是权限
        List<sys_menu> menus = menuService.selectMenuByRoleId(role_id);

        for(sys_menu u:menus){
            menusSet.add(u.getMenuCode());
        }
        log.info("所属部门编码"+roleSet.toString());
        log.info("所属菜单权限编码"+menusSet.toString());

        //告诉Shiro用户拥有的角色与权限
        simpleAuthorizationInfo.addRoles(roleSet);
        simpleAuthorizationInfo.addStringPermissions(menusSet);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        RememberMeAuthenticationToken r = (RememberMeAuthenticationToken) token;
        HostAuthenticationToken s = (HostAuthenticationToken) token;
        // 1、登录认证的方法需要先执行，需要用他来判断登录的用户信息是否合法
        String usercode = (String) token.getPrincipal();    // 取得用户工号
        String password = new String((char[]) token.getCredentials());//获取密码
        Boolean remember = r.isRememberMe();//获取记住我
        String ip = s.getHost();//获取Ip
        log.info("获取用户名" + usercode + "记住我" + remember + "主机" + ip);
        // 需要通过用户名取得用户的完整信息，利用业务层操作
        sys_user user = null;
        try {
            user = user_service.selectUserByCode(usercode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user == null) {
            throw new UnknownAccountException("该用户名称不存在！");
        } else {    // 进行密码的验证处理
            if (user.getStatus() == 1) {
                throw new LockedAccountException("该工号已锁定，请联系管理员！");
            }
            log.info("获取登录密码" + password);
            Object result = new SimpleHash("MD5", password, usercode);
            String pass = user.getUserPassword();
            log.info(result.toString() + "|" + pass);
            // 将数据库中的密码与输入的密码进行比较，这样就可以确定当前用户是否可以正常登录
            if (!result.toString().equals(pass)) {
                sys_user erroruser = new sys_user();
                String errorcount = user.getErrorCount();
                if (Basecommon.isNullStr(errorcount)) {
                    errorcount = "0";
                }
                int count = Integer.parseInt(errorcount);
                if (count > 6) {
                    erroruser.setStatus(1);
                }
                erroruser.setTs(DateUtils.getDate());
                erroruser.setErrorCount(String.valueOf(count + 1));
                erroruser.setUserId(user.getUserId());
                erroruser.setIp(ip);
                user_service.updateByPrimaryKeySelective(erroruser);
                log.info("密码错误");
                throw new IncorrectCredentialsException("密码错误");
            } else {

                HttpSession session = request.getSession();
                session.setAttribute("user",user);
                sys_user userlastdate = new sys_user();
                userlastdate.setUserId(user.getUserId());
                userlastdate.setErrorCount("0");
                userlastdate.setLoginTime(DateUtils.getDate());
                userlastdate.setLastTime(user.getLoginTime());
                userlastdate.setIp(ip);
                user_service.updateByPrimaryKeySelective(userlastdate);
                SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getUserPassword(), getName());
                info.setCredentialsSalt(ByteSource.Util.bytes(usercode));
                return info;
            }
        }
    }

    public static void main(String[] args){

        System.out.println( new SimpleHash("MD5","123456","yuan").toString());
    }
}
