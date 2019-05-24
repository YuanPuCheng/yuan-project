package com.zihui.cwoa.routine.controller;


import com.zihui.cwoa.routine.pojo.rw_trip;
import com.zihui.cwoa.routine.service.rw_tripService;
import com.zihui.cwoa.system.common.CallbackResult;
import com.zihui.cwoa.system.common.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping(value = "/trip")
public class TripController {
    private static Logger log = Logger.getLogger(TripController.class);

    @Resource
    private rw_tripService tripService;
    @RequestMapping(value = "/tripindex")
    public String a(){
        return "trip/trip";
    }

    @RequestMapping(value = "/trip")
    @ResponseBody
    public Map<String,String> trip(@RequestParam Integer userId){
        Map<String,String> map = new HashMap<>();
        List<rw_trip> list = new ArrayList<>();
        try {
             list =tripService.selectTripByUserId(userId);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(list.size()!=0){
            log.info("有值");
            for(rw_trip r: list){
                map.put(r.getTripTime(),r.getTripContent());
            }
        }
        return map;
    }

    @RequestMapping(value = "addoredit")
    @ResponseBody
    public CallbackResult addoredit(rw_trip trip){
        CallbackResult result = new CallbackResult();
        log.info(trip.toString());
        rw_trip r = tripService.selectTripByUserIdAndTime(trip.getTripUserId(),trip.getTripTime());
        //判断是否存在数据，有数据修改，没数据添加
        if(r!=null){
            log.info("修改");
            try {
                tripService.updateTripByUserIdAndTime(trip);
            }catch (Exception e){
                e.printStackTrace();
                result.setResult(400);
                result.setMessage("修改失败");
                return result;
            }

            result.setResult(200);
            result.setMessage("修改成功");
            return result;
        }else{
            log.info("添加");
            try {
                tripService.insertSelective(trip);
            }catch (Exception e){
                e.printStackTrace();
                result.setResult(400);
                result.setMessage("添加失败");
                return result;
            }
            result.setResult(200);
            result.setMessage("添加成功");
            return result;
        }
    }

    @RequestMapping(value = "del")
    @ResponseBody
    public CallbackResult del(rw_trip trip){
        CallbackResult result = new CallbackResult();
        try{
            tripService.deleteByUserIdAndTime(trip);
        }catch (Exception e){
            e.printStackTrace();
            result.setResult(400);
            result.setMessage("删除失败");
            return result;
        }
        result.setResult(200);
        result.setMessage("删除成功");
        return result;
    }


}
