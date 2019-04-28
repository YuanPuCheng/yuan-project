package com.zihui.cwoa.financial.service;

import com.zihui.cwoa.financial.dao.FinancialMapper;
import com.zihui.cwoa.financial.pojo.ProjectInAndOut;
import com.zihui.cwoa.financial.pojo.ProjectMonthDetail;
import com.zihui.cwoa.financial.pojo.ProjectMonthInAndOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinancialService {

    @Autowired
    private FinancialMapper financialMapper;

    /**
     *  查询所有项目的总请款和总报销
     *  @return 查询结果
     */
    public Map<String,Object> queryProjectAllInAndOut(){
        List<ProjectInAndOut> projectInAndOuts = financialMapper.queryProjectAllInAndOut();
        Map<String,Object> map =new HashMap<>();
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("count",projectInAndOuts.size());
        map.put("data",projectInAndOuts);
        return map;
    }

    /**
     *  查询项目单月总请款和总报销
     *  @return 查询结果
     */
    public Map<String,Object> queryProjectMonthInAndOut(String project_name){
        List<ProjectMonthInAndOut> projectMonthInAndOuts =
                financialMapper.queryProjectMonthInAndOut(project_name);
        Map<String,Object> map =new HashMap<>();
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("count",projectMonthInAndOuts.size());
        map.put("data",projectMonthInAndOuts);
        return map;
    }


    /**
     *  查询项目单月请款详情
     *  @return 查询结果
     */
    public Map<String,Object> queryProjectMonthOutDetail(String project_name, String year, String month){
        List<ProjectMonthDetail> projectMonthDetails =
                financialMapper.queryProjectMonthOutDetail(project_name, year, month);
        Map<String,Object> map =new HashMap<>();
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("count",projectMonthDetails.size());
        map.put("data",projectMonthDetails);
        return map;
    }

    /**
     *  查询项目单月报销详情
     *  @return 查询结果
     */
    public Map<String,Object> queryProjectMonthInDetail(String project_name, String year, String month){
        List<ProjectMonthDetail> projectMonthDetails =
                financialMapper.queryProjectMonthInDetail(project_name, year, month);
        Map<String,Object> map =new HashMap<>();
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("count",projectMonthDetails.size());
        map.put("data",projectMonthDetails);
        return map;
    }

    /**
     *  查询所有项目的内部总报销
     *  @return 查询结果
     */
    public Map<String,Object> queryProjectAllIn(){
        List<ProjectInAndOut> queryProjectAllIn = financialMapper.queryProjectAllIn();
        Map<String,Object> map =new HashMap<>();
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("count",queryProjectAllIn.size());
        map.put("data",queryProjectAllIn);
        return map;
    }

    /**
     *  查询项目内部单月总请款和总报销
     *  @return 查询结果
     */
    public Map<String,Object> queryProjectMonthIn(String project_name){
        List<ProjectMonthInAndOut> queryProjectAllIn = financialMapper.queryProjectMonthIn(project_name);
        Map<String,Object> map =new HashMap<>();
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("count",queryProjectAllIn.size());
        map.put("data",queryProjectAllIn);
        return map;
    }

    /**
     *  查询项目内部单月报销详情
     *  @return 查询结果
     */
    public Map<String,Object> queryProMonthInDetail(String project_name,String year,String month){
        List<ProjectMonthDetail> queryProMonthInDetail =
                financialMapper.queryProMonthInDetail(project_name, year, month);
        Map<String,Object> map =new HashMap<>();
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("count",queryProMonthInDetail.size());
        map.put("data",queryProMonthInDetail);
        return map;
    }
}
