package com.zihui.cwoa.politics.dao;

import com.zihui.cwoa.politics.pojo.CarStatus;
import com.zihui.cwoa.politics.pojo.CarUse;
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
    List<Map<String,Object>> queryCarUse(Map<String,Object> map);

    /**
     *  查询车辆使用信息总数
     */
    Integer countCarUse(String carId);

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

    /**
     *  查询空闲的车辆
     */
    List<Map<String,Object>> getFreeCar();

    /**
     *  添加用车信息
     */
    int insertCarUse(CarUse carUse);

    /**
     *  改变车辆状态
     */
    void updateCarStatus(CarStatus carStatus);

    /**
     *  获取可还车辆
     */
    List<Map<String,Object>> getReturnCar(String userId);

    /**
     *  获取正在使用某辆车的用户id
     */
    String getNowUseId(String carId);

    /**
     *  更新还车信息
     */
    int updateCarUse(String useId);
}
