package com.zihui.cwoa.processone.service;

import com.zihui.cwoa.financial.pojo.ProjectMonthDetail;
import com.zihui.cwoa.processone.dao.HumanMapper;
import com.zihui.cwoa.processone.dao.QueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HumanService {

    @Autowired
    private HumanMapper humanMapper;

    /**
     *  查询请假出差记录
     *  @return 查询结果
     */
    public Map<String,Object> queryLeaveByVo(int size,String userCode, String project, String leaveYear,
                                             String leaveMonth, int page, int limit){
        List<Map<String,Object>> queryLeaveByVo =
                humanMapper.queryLeaveByVo(userCode,project,leaveYear,leaveMonth,page,limit);
        Map<String,Object> map =new HashMap<>();
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("count",size);
        map.put("data",queryLeaveByVo);
        return map;
    }

    /**
     *  查询请假出差记录条数
     *  @return 查询结果
     */
    public Integer countLeaveByVo(String userCode, String project, String leaveYear, String leaveMonth){
        return humanMapper.countLeaveByVo(userCode,project,leaveYear,leaveMonth);
    }

    /**
     *  查询用户的出差请假记录详情
     *  @return 查询结果
     */
    public List<Map<String,Object>> queryLeaveDetail(String userCode, String project,
                                               String leaveYear, String leaveMonth){
        return humanMapper.queryLeaveDetail(userCode,project,leaveYear,leaveMonth);
    }
}
