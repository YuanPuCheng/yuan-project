package com.zihui.cwoa.routine.service;

import com.zihui.cwoa.routine.dao.rw_tripMapper;
import com.zihui.cwoa.routine.pojo.rw_trip;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class rw_tripService {


    @Resource
    private rw_tripMapper tripMapper;


    public int deleteByPrimaryKey(Integer tripId){
        return tripMapper.deleteByPrimaryKey(tripId);
    };

    public int insertSelective(rw_trip record){
        return tripMapper.insertSelective(record);
    };

    public rw_trip selectByPrimaryKey(Integer tripId){
        return tripMapper.selectByPrimaryKey(tripId);
    };

    public int updateByPrimaryKeySelective(rw_trip record){
        return tripMapper.updateByPrimaryKeySelective(record);
    };

    public List<rw_trip> selectTripByUserId( Integer userId){
        return tripMapper.selectTripByUserId(userId);
    };

    public rw_trip selectTripByUserIdAndTime( Integer userId, String tripTime){
        return tripMapper.selectTripByUserIdAndTime(userId,tripTime);
    };

    public int updateTripByUserIdAndTime(rw_trip record){
        return tripMapper.updateTripByUserIdAndTime(record);
    };

    public int deleteByUserIdAndTime(rw_trip record){
        return tripMapper.deleteByUserIdAndTime(record);
    };
}
