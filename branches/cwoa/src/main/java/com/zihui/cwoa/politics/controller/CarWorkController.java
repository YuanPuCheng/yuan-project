package com.zihui.cwoa.politics.controller;

import com.zihui.cwoa.politics.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/car")
public class CarWorkController {

    @Autowired
    private CarService carService;

    /**
     *  新增车辆
     *  @return text
     */
    @RequestMapping("/insertCar")
    @ResponseBody
    public boolean insertCar(@RequestBody Map<String, Object> map){
        return carService.insertCar(map);
    }

    /**
     *  查询车辆信息
     *  @return json
     */
    @RequestMapping("/queryCar")
    @ResponseBody
    public Map<String,Object> queryCar(int size,int page,int limit){
        page=(page-1)*limit;
        return carService.queryCar(size,page, limit);
    }

    /**
     *  查询车辆总数
     *  @return text
     */
    @RequestMapping("/countCar")
    @ResponseBody
    public int countCar(){
        return carService.countCar();
    }

    /**
     *  查询车辆使用信息
     *  @return text
     */
    @RequestMapping("/queryCarUse")
    @ResponseBody
    public Map<String,Object> queryCarUse(String carId, int size,int page, int limit){
        page=(page-1)*limit;
        return carService.queryCarUse(carId,size, page, limit);
    }

    /**
     *  查询车辆使用信息数量
     *  @return text
     */
    @RequestMapping("/countCarUse")
    @ResponseBody
    public Integer countCarUse(String carId){
        return carService.countCarUse(carId);
    }

    /**
     *  删除车辆
     *  @return text
     */
    @RequestMapping("/deleteCar")
    @ResponseBody
    public boolean deleteCar(String carId){
        return carService.deleteCar(carId);
    }

    /**
     *  批量删除车辆
     *  @return text
     */
    @RequestMapping("/deleteManyCar")
    @ResponseBody
    public boolean deleteManyCar(String idArray){
        return carService.deleteManyCar(idArray);
    }

    /**
     *  编辑更新车辆
     *  @return text
     */
    @RequestMapping("/updateCar")
    @ResponseBody
    public boolean updateCar(@RequestBody Map<String, Object> map){
        return carService.updateCar(map);
    }

    /**
     *  查询空闲的车辆
     *  @return text
     */
    @RequestMapping("/getFreeCar")
    @ResponseBody
    public List<Map<String,Object>> getFreeCar(){
        return carService.getFreeCar();
    }

    /**
     *  查询可还车辆
     *  @return text
     */
    @RequestMapping("/getReturnCar")
    @ResponseBody
    public List<Map<String,Object>> getReturnCar(String userId){
        return carService.getReturnCar(userId);
    }
}
