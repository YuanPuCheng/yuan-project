package com.zihui.cwoa.politics.cartask;

import com.zihui.cwoa.politics.pojo.CarStatus;
import com.zihui.cwoa.politics.pojo.CarUse;
import com.zihui.cwoa.politics.service.CarService;
import com.zihui.cwoa.system.config.SpringUtil;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class GetCarTaskListener implements TaskListener {

    /**
     *  用车申请监听任务
     */
    public void notify(DelegateTask delegateTask) {
        String userId = (String) delegateTask.getVariable("userId");
        String carId = (String) delegateTask.getVariable("carId");
        List<Map> processSummary = (List<Map>) delegateTask.getVariable("processSummary");
        String reason="";
        for (Map map: processSummary) {
            String indexName= (String) map.get("indexName");
            if("用车理由".equals(indexName)){
                reason= (String) map.get("indexValue");

            }
        }
        CarUse carUse=new CarUse();
        carUse.setUse_car(carId);
        carUse.setUse_user(userId);
        carUse.setUse_reason(reason);
        CarService carService= (CarService) SpringUtil.getObject("CarService");
        boolean flag = carService.insertCarUse(carUse)>0;
        if(flag){
            CarStatus carStatus=new CarStatus();
            carStatus.setUseId(carUse.getId());
            carStatus.setCarId(carId);
            carStatus.setCarStatus("1");
            carStatus.setNowUser(userId);
            carService.updateCarStatus(carStatus);
        }
    }
}
