package com.zihui.cwoa.politics.cartask;

import com.zihui.cwoa.politics.pojo.CarStatus;
import com.zihui.cwoa.politics.service.CarService;
import com.zihui.cwoa.system.config.SpringUtil;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

@Component
public class ReturnCarTaskListener implements TaskListener {

    /**
     *  还车申请监听任务
     */
    public void notify(DelegateTask delegateTask) {
        String carId = (String) delegateTask.getVariable("carId");
        CarService carService= (CarService) SpringUtil.getObject("CarService");
        String nowUseId=carService.getNowUseId(carId);
        boolean flag=carService.updateCarUse(nowUseId)>0;
        if(flag){
            CarStatus carStatus=new CarStatus();
            carStatus.setUseId("");
            carStatus.setCarId(carId);
            carStatus.setCarStatus("0");
            carStatus.setNowUser("");
            carService.updateCarStatus(carStatus);
        }
    }
}
