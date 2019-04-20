package com.zihui.cwoa.system.controller;

import com.zihui.cwoa.system.common.Basecommon;
import com.zihui.cwoa.system.common.CallbackResult;
import com.zihui.cwoa.system.common.DateUtils;
import com.zihui.cwoa.system.dao.sys_department_menuMapper;
import com.zihui.cwoa.system.pojo.sys_user;
import com.zihui.cwoa.system.service.sys_departmentService;
import com.zihui.cwoa.system.service.sys_menuService;
import com.zihui.cwoa.system.service.sys_userService;
import com.zihui.cwoa.system.service.sys_user_departmentService;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static Logger log = Logger.getLogger(UserController.class);

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

    //查看我的个人信息
    @RequestMapping(value = "/getuserinfo")
    @ResponseBody
    public sys_user user(HttpSession session){
        sys_user user =(sys_user) session.getAttribute("user");

        return user_service.selectDepartmentToUser(10);
    }
    /**
     *  根据条件查询用户列表
     */
    @RequestMapping(value = "/getuser")
    @ResponseBody
    public ConcurrentMap getuser(sys_user user){
        CallbackResult result = new CallbackResult();

       //user.setTempVar3(department);//代表部门id
        List<sys_user> list = user_service.selectUserDepar(user);
        //Integer count = user_service.selectUserCount(user);
        ConcurrentMap concurrentMap = new ConcurrentHashMap<String,Object>();
        //PageInfo<sys_user> info = new PageInfo<>(list);
        concurrentMap.put("count", list.size());
        concurrentMap.put("data", list);
        concurrentMap.put("code", 0);
        concurrentMap.put("msg", "成功");
        return concurrentMap;
    }

    /**
     *  根据条件查询用户列表分页
     */
  /*  @RequestMapping(value = "/getuserPage")
    @ResponseBody
    public ConcurrentMap getuserPage(sys_user user,Integer page, Integer limit){
        CallbackResult result = new CallbackResult();
        ConcurrentMap concurrentMap = new ConcurrentHashMap<String,Object>();
        if(page==1){
            page =0;
        }else{
            page = (page-1)*limit;
        }
        List<sys_user> list = user_service.selectUserDepar(user,page,limit);


        Integer count = user_service.selectUserCount(user);

        concurrentMap.put("count", count);
        concurrentMap.put("data", list);
        concurrentMap.put("code", 0);
        concurrentMap.put("msg", "成功");
        return concurrentMap;
    }*/

    @RequestMapping(value = "/de")
    @ResponseBody
    @RequiresPermissions("1")
    public CallbackResult de(){
        CallbackResult result = new CallbackResult();


        result.setResult(200);
        result.setMessage("修改成功");
        return result;
    }


    /**
     *  添加用户基本信息
     */
    @RequestMapping(value = "/userinfo")
    @ResponseBody
    public CallbackResult userinfo(sys_user user){
        CallbackResult result = new CallbackResult();

        log.info(user.toString());

        int count = user_service.updateByPrimaryKeySelective(user);
        result.setResult(200);
        result.setMessage("修改成功");
        return result;
    }

    /**
     *  根据用户工号删除用户
     */
    @RequestMapping(value="/delete")
    @ResponseBody
    public CallbackResult delete(@RequestParam String usercodes){
        CallbackResult result = new CallbackResult();
        String a[] = usercodes.split(",");
        StringBuffer mess = new StringBuffer();
        try{
            for (String usercode:a) {
                log.info(usercode);
                int count =user_service.deleteByUserCode(usercode);
                if(count==0){
                    mess.append("工号："+usercode+",删除失败。");
                    result.setResult(400);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            result.setResult(400);
            result.setMessage(mess.toString());
        }
        if(result.getResult()!=400){
            result.setResult(200);
            result.setMessage("删除成功");
        }
        return result;

    }

    /**
     *  修改密码
     *  */
    @RequestMapping(value = "/updatepass")
    @ResponseBody
    public CallbackResult updatepass(@RequestParam String password,@RequestParam String repassword,
                                     HttpSession session){
        CallbackResult result = new CallbackResult();
        sys_user user = (sys_user) session.getAttribute("user");
        //得到加密后的密码
        Object md5password =  new SimpleHash("MD5", password, user.getUserCode());
        log.info(user.getUserPassword()+"||||"+md5password.toString());
        //与原密码进行比较
        if(!user.getUserPassword().equals(md5password.toString())){
            result.setResult(400);
            result.setMessage("当前密码与工号不一致");
            return result;
        }
        sys_user up = new sys_user();
        //加密新密码
        Object re =  new SimpleHash("MD5", repassword, user.getUserCode());
        up.setUserId(user.getUserId());
        up.setUserPassword(re.toString());
        int count =user_service.updateByPrimaryKeySelective(up);
        if(count==0){
            result.setResult(400);
            result.setMessage("修改失败");
            return result;
        }
        result.setResult(400);
        result.setMessage("修改成功");
        return result;
    }



    /**
     *  添加用户
     */
    @RequestMapping(value="/add")
    @ResponseBody
    public CallbackResult add(sys_user user){
        CallbackResult result = new CallbackResult();
        String depar = user.getTempVar2();
        log.info(user.toString());
        user.setTempVar2(null);
        user.setCreateTime(DateUtils.getDate());
        Object md5password =  new SimpleHash("MD5", user.getUserPassword(), user.getUserCode());
        user.setUserPassword(md5password.toString());
        user_service.insertSelective(user);
        log.info(user.getUserId());

        if(!Basecommon.isNullStr(depar)) {
         String depars []=   depar.split(",");
            for (String a:depars){
                user_departmentService.insertUserDepar(user.getUserId(), Integer.parseInt(a));
            }
        }
        result.setResult(200);
        result.setMessage("添加成功");
        return result;

    }


    /**
     *  修改用户
     */
    @RequestMapping(value="/edit")
    @ResponseBody
    public CallbackResult edit(sys_user user){
        CallbackResult result = new CallbackResult();
        String depar = user.getTempVar2();
        String old = user.getTempVar3();
        log.info(user.toString());
        user.setTempVar2(null);
        try {
            user_service.updateByPrimaryKeySelective(user);
        }catch (Exception e){
            e.printStackTrace();
            result.setResult(400);
            result.setMessage("修改失败");
            return result;
        }

        Set<String> in = new HashSet();//新增
        Set<String> de = new HashSet();//删除
        Set set1 = new HashSet();
        Set set2 = new HashSet();
            String depars []= depar.split(",");//1,2
            String olds [] = old.split(",");//1
            for(String d:depars){
                set1.add(d);
            }
            for (String dd:olds){
                set2.add(dd);
        }
        in.addAll(set1);
        in.removeAll(set2);
        de.addAll(set2);
        de.removeAll(set1);

       log.info("新增：" + in);
        log.info("删除：" + de);

        for(String del:de){
            user_departmentService.deleteUserDepar(user.getUserId(),Integer.parseInt(del));
        }
        for(String ins:in){
            user_departmentService.insertUserDepar(user.getUserId(),Integer.parseInt(ins));
        }

        result.setResult(200);
        result.setMessage("修改成功");
        return result;

    }

    public static void main(String[] args) {
        String depar= "1,2,4";
        String old = "1,2,3";
        Set in =new HashSet();//新增
        Set up = new HashSet();
        Set de = new HashSet();//dele
        String []xxx = depar.split(",");
        String []ddd = old.split(",");

        //list2.removeAll(list1);
        //list4.removeAll(list3);
        //System.out.println(list1.toString());
       // System.out.println(list3.toString());


           /* String depars []= depar.split(",");//1,2
            String olds [] = old.split(",");//1
            for(String d:depars){

                if(old.indexOf(d)==-1){
                    in.add(d);
                }
            }
            for (String dd:olds){
                if(depar.indexOf(dd)==-1){
                    de.add(dd);
                }
                }
*/
        }
    }
