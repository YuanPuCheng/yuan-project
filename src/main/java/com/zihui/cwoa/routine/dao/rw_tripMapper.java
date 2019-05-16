package com.zihui.cwoa.routine.dao;


import com.zihui.cwoa.routine.pojo.rw_trip;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface rw_tripMapper {

    int deleteByPrimaryKey(Integer tripId);

    int insertSelective(rw_trip record);

    rw_trip selectByPrimaryKey(Integer tripId);

    int updateByPrimaryKeySelective(rw_trip record);

    List<rw_trip> selectTripByUserId(@Param("userId") Integer userId);

    rw_trip selectTripByUserIdAndTime(@Param("userId") Integer userId,@Param("tripTime") String tripTime);

    int updateTripByUserIdAndTime(rw_trip record);

    int deleteByUserIdAndTime(rw_trip record);

}