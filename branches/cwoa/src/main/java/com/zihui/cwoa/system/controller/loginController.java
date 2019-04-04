package com.zihui.cwoa.system.controller;

import com.zihui.cwoa.system.common.CallbackResult;
import com.zihui.cwoa.system.common.Common;
import com.zihui.cwoa.system.dao.sys_department_menuMapper;
import com.zihui.cwoa.system.pojo.sys_department;
import com.zihui.cwoa.system.pojo.sys_menu;
import com.zihui.cwoa.system.pojo.sys_user;
import com.zihui.cwoa.system.service.sys_departmentService;
import com.zihui.cwoa.system.service.sys_menuService;
import com.zihui.cwoa.system.service.sys_userService;
import com.zihui.cwoa.system.service.sys_user_departmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.core.io.ResourceLoader;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/user")
public class loginController {
    private static Logger log = Logger.getLogger(loginController.class);

    @Resource
    private sys_userService user_service;
    @Resource
    private sys_department_menuMapper department_menuMapper;
    @Resource
    private sys_departmentService departmentService;
    @Resource
    private sys_menuService menuService;
    @Resource
    private sys_user_departmentService user_departmentService;

    private final ResourceLoader resourceLoader;

    @Autowired
    public loginController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @RequestMapping(value = "/")
    public ModelAndView login(){
        ModelAndView view = new ModelAndView();
        view.addObject("name","admin");

        view.addObject("11","11");
        view.setViewName("login");

        return view;
    }

    @RequestMapping(value = "/file")
    public ModelAndView f( HttpServletRequest request){
        HttpSession session = request.getSession();
        ModelAndView view = new ModelAndView();
        String captchaId = (String) session.getAttribute("vrifyCode");
        log.info("session"+captchaId);
        view.setViewName("filetwo");
        return view;
    }

    //文件上传
    @RequestMapping(value = "/upload")
    @ResponseBody
    public String upload(HttpServletRequest request,
                         @RequestParam("userCode") String userCode,
                         @RequestParam("userPassword") String userPassword,
                         @RequestParam("userName") String userName,
                         @RequestParam("file") MultipartFile file) throws IOException {
            // 接收参数description
            System.out.println(userCode+userPassword+userName);
            sys_user user = new sys_user();
            user.setUserId("1");

            String filename = Common.upload(file);
            user.setImages(filename);
            user_service.updateByPrimaryKeySelective(user);
            return "success";
    }

    @RequestMapping(value = "/show")
    public ResponseEntity user(String fileName) throws IOException {
        try {
            // 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
            return ResponseEntity.ok(resourceLoader.getResource("file:///" + Common.IMG_PATH + fileName));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @RequestMapping(value = "/user")
    public ModelAndView t(){
        sys_user user = user_service.selectByPrimaryKey("1");
        ModelAndView view = new ModelAndView();
        view.addObject("user",user);
        view.setViewName("index");
        return view;
    }



    @RequestMapping(value = "/loginuser" , method = RequestMethod.POST)
    @ResponseBody
    public CallbackResult userlogin(HttpSession session, @RequestParam String usercode,
                                    @RequestParam String password, @RequestParam String vrifyCode, Model model) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        CallbackResult result = new CallbackResult();
        System.out.println("进入验证"+usercode+vrifyCode);
        //String captchaId = (String) session.getAttribute("vrifyCode");
        String captchaId =(String) request.getSession().getAttribute("vrifyCode");
        //String parameter = httpServletRequest.getParameter("vrifyCode");
        System.out.println("Session  验证码 "+captchaId+" 参数验证码 "+vrifyCode);
        if (!captchaId.equals(vrifyCode)) {
            log.warn("验证码不正确");
            result.setResult(400);
            result.setMessage("验证码不正确");
            return result;
        }

        try {
            UsernamePasswordToken token = new UsernamePasswordToken(usercode, password,false);
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
        result.setResult(200);
        result.setMessage("登录成功");
        return result;
    }

    @RequestMapping(value = "/logoutuser")
    @ResponseBody
    public String loginlout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "退出成功";
    }


    @RequestMapping(value = "/add")
    @RequiresRoles("1")
    @ResponseBody
    public String add (){
        System.out.println("进入add方法");

        return "add方法";
    }

    @RequestMapping(value = "/delete")
    @RequiresRoles("delete1")
    @ResponseBody
    public String delete (){
        System.out.println("delete1");

        return "delete1方法";
    }


    @RequestMapping( value = "/insert")
    @RequiresPermissions("2")
    @ResponseBody
    public String insert (){
        System.out.println("进入insert方法");

        return "insert方法";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUser")
    @ResponseBody
    public Map error(){
        Map map =new HashMap();
       // List<sys_department> department=departmentService.selectDepartment("1");
        sys_user user1 =user_service.selectDepartmentToUser("1");
        List<String> s = new ArrayList<>();
        s.add("1");
        s.add("2");
        List<sys_department> ss = departmentService.selectMenu(s);
        map.put("user1",user1);
        map.put("sys_department",ss);
        return map;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/index")
    @ResponseBody
    public Map d(){
        Map map =new HashMap();
        List<Integer> a = new ArrayList<>();
        a.add(1);
         List<sys_menu> m =menuService.selectMenuByMenuId(a);
        map.put("m",m);
        return map;
    }



}
