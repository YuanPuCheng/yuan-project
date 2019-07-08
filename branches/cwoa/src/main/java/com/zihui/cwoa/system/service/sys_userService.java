package com.zihui.cwoa.system.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zihui.cwoa.system.common.RedisUtils;
import com.zihui.cwoa.system.dao.sys_userMapper;
import com.zihui.cwoa.system.pojo.sys_user;
import com.zihui.cwoa.system.pojo.sys_users;
import org.apache.log4j.Logger;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CacheConfig(cacheNames = "sys_userServiceCache")
@Service
public class sys_userService {

    public static Logger logger = Logger.getLogger(sys_userService.class);
    public final String URL = "http://t.weather.sojson.com/api/weather/city/101240101";//天气接口1
    public final String TQURL = "http://wthrcdn.etouch.cn/weather_mini?citykey=101240101";//天气接口2
    @Resource
    private sys_userMapper userMapper;

    @Resource
    private RedisUtils redisUtils;

    @Cacheable(key = "'userList'")
    public List<sys_users> selectUserBySelect(){
        return userMapper.selectUserBySelect();
    }

    public int deleteByPrimaryKey(Integer userId){
        try {
            redisUtils.deleteCache("user*");
        }catch (Exception e){
            logger.error(e);
        }
        return userMapper.deleteByPrimaryKey(userId);
    }

    public int insertSelective(sys_user record){
        try {
            redisUtils.deleteCache("user*");
        }catch (Exception e){
            logger.error(e);
        }
        return userMapper.insertSelective(record);
    }

    public sys_user selectByPrimaryKey(sys_user record){
        return userMapper.selectByPrimaryKey(record);
    }

    public int updateByPrimaryKeySelective(sys_user record){
        return userMapper.updateByPrimaryKeySelective(record);
    }

    public sys_user selectUserByCode(String userCode){
        return userMapper.selectUserByCode(userCode);
    }

    /**
     *  根据条件查询当前角色、项目下所有用户
     *  @param roleId 角色id  可空
     *  @param projectId 项目id 可空
     *  @return list
     */
    public List<sys_users> userRoleQuery(Integer roleId, Integer projectId){
        return userMapper.userRoleQuery(roleId,projectId);
    }

    public sys_user selectUserInfo(Integer userId){
        return userMapper.selectUserInfo(userId);
    }
    public List<sys_user> selectUserByPage(sys_user user,Integer page,Integer limit){
        return userMapper.selectUserByPage(user,page,limit);
    }

    public Integer selectUserByPageCount(sys_user user){
        return userMapper.selectUserByPageCount(user);
    }


    public List<sys_users> selectUserAndProject(){
        return userMapper.selectUserAndProject();
    }

    @Cacheable(key = "'getTq'")
    public Map getTp(){
        logger.info("-----------------------getTQ--------");
        Map map = new HashMap();
        RestTemplate template = new RestTemplate();
        ResponseEntity data =template.getForEntity(URL,String.class);
        JSONObject json =JSONObject.parseObject(data.getBody().toString());//将接口返回的数据转为JSON
        JSONObject json1 =(JSONObject)json.get("data");
        JSONArray array =(JSONArray)json1.get("forecast");
        List<String> da =new ArrayList();
        List<Integer> height =new  ArrayList();
        List<Integer> di =new  ArrayList();
        for(int i=0;i<array.size();i++){
            JSONObject object = (JSONObject)array.get(i);
            String high =(String) object.get("high");
            String d =(String) object.get("low");
            high = high.substring(high.indexOf(" ")+1,high.indexOf("."));
            d=d.substring(d.indexOf(" ")+1,d.indexOf("."));
            da.add(object.get("date").toString()+"号");
            height.add(Integer.parseInt(high));
            di.add(Integer.parseInt(d));
        }
        map.put("data",da);
        map.put("high",height);
        map.put("low",di);
        return map;
    }
}
