package com.zihui.cwoa.politics.pojo;

/**
 * 车辆状态实体类
 */
public class CarStatus {

    //车辆id
    private String carId;

    //车辆状态
    private String carStatus;

    //用车人id
    private String nowUser;

    //用车信息id
    private String useId;

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(String carStatus) {
        this.carStatus = carStatus;
    }

    public String getNowUser() {
        return nowUser;
    }

    public void setNowUser(String nowUser) {
        this.nowUser = nowUser;
    }

    public String getUseId() {
        return useId;
    }

    public void setUseId(String useId) {
        this.useId = useId;
    }
}
