package com.zihui.cwoa.politics.service;

import com.zihui.cwoa.politics.dao.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
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
    public List<Map<String,Object>> queryCarUse(String carId,int page,int limit){
        return carMapper.queryCarUse(carId,page,limit);
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

}
