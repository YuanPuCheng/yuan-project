package com.zihui.cwoa.politics.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CarMapper {

    /**
     *  新增车辆信息
     */
    int insertCar(Map<String,Object> map);

    /**
     *  查询车辆使用信息
     */
    List<Map<String,Object>> queryCarUse(String carId,int page,int limit);

    /**
     *  查询车辆信息
     */
    List<Map<String,Object>> queryCar(int page,int limit);

    /**
     *  更新车辆信息
     */
    int updateCar(Map<String,Object> map);

    /**
     *  查询车辆信息总数
     */
    int countCar();

    /**
     *  删除车辆信息
     */
    int deleteCar(String carId);

    /**
     *  批量删除车辆信息
     */
    int deleteManyCar(String[] split);

}
