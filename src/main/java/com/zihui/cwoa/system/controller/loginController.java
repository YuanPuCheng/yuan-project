package com.zihui.cwoa.system.controller;

import com.zihui.cwoa.system.common.Basecommon;
import com.zihui.cwoa.system.common.CallbackResult;
import com.zihui.cwoa.system.common.Common;
import com.zihui.cwoa.system.common.DateUtils;
import com.zihui.cwoa.system.pojo.sys_menu;
import com.zihui.cwoa.system.pojo.sys_role;
import com.zihui.cwoa.system.pojo.sys_user;
import com.zihui.cwoa.system.service.sys_departmentService;
import com.zihui.cwoa.system.service.sys_menuService;
import com.zihui.cwoa.system.service.sys_roleService;
import com.zihui.cwoa.system.service.sys_userService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.*;


@Controller
@RequestMapping(value = "/system")
public class loginController {
    private static Logger log = Logger.getLogger(loginController.class);

    @Resource
    private sys_userService user_service;
    @Resource
    private sys_roleService roleService;
    @Resource
    private sys_departmentService departmentService;
    @Resource
    private sys_menuService menuService;

    public loginController() {
    }


    @RequestMapping(value = "/cookie")
    @ResponseBody
    public CallbackResult f( HttpServletRequest request) throws UnsupportedEncodingException{
        CallbackResult result = new CallbackResult();
        HttpSession session = request.getSession();
        Map map =Common.ReadCookieMap(request);
        String usercode = (String) map.get("usercode");
        log.info(usercode);
        if(Basecommon.isNullStr(usercode)){
            result.setResult(400);
            result.setMessage("当前没有cookie");
            result.setMap(map);
        }else{
            result.setResult(200);
            result.setMessage("获取cookic成功");
            result.setMap(map);
        }

        return result;
    }



    //用户注册
    @RequestMapping(value = "/redirect")
    @ResponseBody
    public CallbackResult redirect(HttpServletRequest request,HttpSession session,@RequestParam String usercode,@RequestParam String password,
                                   @RequestParam String vrifyCode,@RequestParam String email){
        CallbackResult result = new CallbackResult();
        String emailYzm = (String) session.getAttribute("emailYzm");
        sys_user user = new sys_user();
        if(Basecommon.isNullStr(emailYzm)){
            result.setResult(400);
            result.setMessage("请先获取验证码");
            return result;
        }
        if(!vrifyCode.equals(emailYzm)){
            result.setResult(400);
            result.setMessage("验证码错误");
            return result;
        }
        Object md5password =  new SimpleHash("MD5", password, usercode);
        user.setUserCode(usercode);
        user.setUserPassword(md5password.toString());
        user.setEmail(email);
        user.setIp(Common.getIpAddr(request));
        user.setCreateTime(DateUtils.getDate());
        user.setTs(DateUtils.getDate());
        Integer s = 0;
        user.setStatus(s);
        int w =user_service.insertSelective(user);
        if(w==1){
            result.setResult(200);
            result.setMessage("注册成功");
        }else{
            result.setResult(400);
            result.setMessage("注册失败");
        }

        return result;
    }


    /**
     *  用户登录校验
     */
    @RequestMapping(value = "/toLogin")
    @ResponseBody
    public CallbackResult userlogin(HttpServletResponse response, HttpServletRequest request, String usercode,
                                    String password, String vrifyCode, String remeber, Model model, HttpSession session) {
        CallbackResult result = new CallbackResult();
        String captchaId =(String) session.getAttribute("vrifyCode");
        System.out.println("Session  验证码 "+captchaId+" 参数验证码 "+vrifyCode+"记住我"+remeber);
        if (!captchaId.equals(vrifyCode)) {
            log.warn("验证码错误");
            result.setResult(400);
            result.setMessage("验证码错误");
            return result;
        }
        try {
            //UsernamePasswordToken token = new UsernamePasswordToken(usercode, password);
            UsernamePasswordToken token = new UsernamePasswordToken(usercode, password,Common.getIpAddr(request));
            System.out.println(token);
            SecurityUtils.getSubject().login(token);

        } catch ( UnknownAccountException uae ) {
            log.warn("用户帐号不正确");
            result.setResult(400);
            result.setMessage("用户帐号不正确");
            return result;

        } catch ( IncorrectCredentialsException ice ) {
            log.warn("用户密码不正确");
            result.setResult(400);
            result.setMessage("用户密码不正确");
            return result;

        } catch ( LockedAccountException lae ) {
            log.warn("用户帐号被锁定");
            result.setResult(400);
            result.setMessage("用户帐号被锁定");
            return result;
        }
        if("true".equals(remeber)){
            log.info("设置cookie");
            try {
                Common.addCookie(response, "usercode",usercode, 180000);
                Common.addCookie(response, "password",password, 180000);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }else{
            try {
                Common.addCookie(response, "usercode",usercode, 0);
                Common.addCookie(response, "password",password, 0);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        result.setResult(200);
        result.setMessage("登录成功");
        return result;
    }



    @RequestMapping(value = "/getuser")
    @ResponseBody
    public CallbackResult error(@RequestParam String usercode){
        CallbackResult result = new CallbackResult();
        sys_user  user =user_service.selectUserByCode(usercode);
        if(user!=null){
            result.setResult(400);
            result.setMessage("该用户名已存在");
        }else {
            result.setResult(200);
            result.setMessage("该用户名正常");
        }

        return result;
    }

    @RequestMapping(value = "/getemail")
    @ResponseBody
    public CallbackResult getemail(@RequestParam String email){
        CallbackResult result = new CallbackResult();
        sys_user user = new sys_user();
        user.setEmail(email);
        sys_user  u =user_service.selectByPrimaryKey(user);
        if(u!=null){
            result.setResult(400);
            result.setMessage("该邮箱已被注册");
        }else {
            result.setResult(200);
            result.setMessage("该邮箱正常");
        }

        return result;
    }

    @RequestMapping(value = "/index")
    @ResponseBody
    public List d( HttpSession session){

        sys_user user = (sys_user) session.getAttribute("user");
        List list = new ArrayList();
        if(user==null){
            try {
                throw new Exception("当前用户未登录，请重新登录");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            List<sys_role> roles = roleService.selcetRoleByUserId(user.getUserId());
            List<Integer> roleId = new ArrayList<>();
            for(sys_role r:roles){
                roleId.add(r.getRoleId());
            }


                List<sys_menu> m =menuService.selectMenuByRoleId(roleId);
                list= Common.getmenu(m);
        }


        /*if(user!=null){
            List<Integer> menuId=department_menuMapper.selectMenuIdByUserId(user.getUserId());
            List<sys_menu> m =menuService.selectMenuByMenuId(menuId);
            set.add(m);
            return set;
        }*/
        return list;
    }


    @RequestMapping(value = "/forget")
    @ResponseBody
    public CallbackResult forget(@RequestParam("usercode") String usercode,
                                 @RequestParam("email")String email,
                                 @RequestParam("emailcode") String emailcode,
                                 @RequestParam("password") String password,
                                 HttpSession session){
        CallbackResult result = new CallbackResult();
        String code = (String) session.getAttribute("emailYzm");
        if(!emailcode.equals(code)){
            result.setResult(400);
            result.setMessage("验证码错误");
            return result;
        }
        sys_user user = new sys_user();
        user.setUserCode(usercode);
        user.setEmail(email);
        sys_user u = user_service.selectByPrimaryKey(user);
        if(u==null){
            result.setResult(400);
            result.setMessage("用户邮箱不正确");
            return result;
        }

        Object md5password =  new SimpleHash("MD5", password, usercode);
        sys_user userupdate = new sys_user();
        userupdate.setUserId(u.getUserId());
        userupdate.setUserPassword(md5password.toString());
        int count = user_service.updateByPrimaryKeySelective(userupdate);
        if(count==0){
            result.setResult(400);
            result.setMessage("修改失败");
            return result;
        }
        result.setResult(200);
        result.setMessage("修改成功");
        return result;
    }

}
