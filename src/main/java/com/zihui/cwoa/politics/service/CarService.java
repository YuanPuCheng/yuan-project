package com.zihui.cwoa.politics.service;

import com.zihui.cwoa.politics.dao.CarMapper;
import com.zihui.cwoa.politics.pojo.CarStatus;
import com.zihui.cwoa.politics.pojo.CarUse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("CarService")
public class CarService {

    @Autowired
    private CarMapper carMapper;

    /**
     *  新增车辆信息
     */
    public boolean insertCar(Map<String,Object> map){
        return carMapper.insertCar(map)>0;
    }

    /**
     *  查询车辆信息
     */
    public Map<String,Object> queryCar(int size,int page,int limit){
        Map<String,Object> map =new HashMap<>();
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("count",size);
        map.put("data",carMapper.queryCar(page,limit));
        return map;
    }

    /**
     *  查询车辆信息数量
     */
    public int countCar(){
        return carMapper.countCar();
    }

    /**
     *  查询车辆使用信息
     */
    public Map<String,Object> queryCarUse(String carId,int size,int page,int limit){
        Map<String,Object> map =new HashMap<>();
        map.put("carId",carId);
        map.put("page",page);
        map.put("limit",limit);
        Map<String,Object> data =new HashMap<>();
        data.put("code",0);
        data.put("msg","请求成功");
        data.put("count",size);
        data.put("data",carMapper.queryCarUse(map));
        return data;
    }

    /**
     *  查询车辆使用信息总数
     */
    public Integer countCarUse(String carId){
        return carMapper.countCarUse(carId);
    }
    /**
     *  删除车辆
     */
    public boolean deleteCar(String carId){
        return carMapper.deleteCar(carId)>0;
    }

    /**
     *  批量删除车辆
     */
    public boolean deleteManyCar(String idArray){
        String[] split = idArray.split(",");
        return carMapper.deleteManyCar(split)>0;
    }

    /**
     *  编辑车辆信息
     */
    public boolean updateCar(Map<String,Object> map){
        return carMapper.updateCar(map)>0;
    }

    /**
     *  查询空闲的车辆
     */
    public List<Map<String,Object>> getFreeCar(){
        return carMapper.getFreeCar();
    }

    /**
     *  添加用车信息
     */
    public int insertCarUse(CarUse carUse){
        return carMapper.insertCarUse(carUse);
    }

    /**
     *  改变车辆状态
     */
    public void updateCarStatus(CarStatus carStatus){
        carMapper.updateCarStatus(carStatus);
    }

    /**
     *  获取可还车辆
     */
    public List<Map<String,Object>> getReturnCar(String userId){
        return carMapper.getReturnCar(userId);
    }

    /**
     *  获取正在使用某辆车的用户id
     */
    public String getNowUseId(String carId){
        return carMapper.getNowUseId(carId);
    }

    /**
     *  更新还车信息
     */
    public int updateCarUse(String useId){
        return carMapper.updateCarUse(useId);
    }
}
