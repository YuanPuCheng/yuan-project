package com.zihui.cwoa.politics.pojo;

/**
 * 车辆使用信息实体类
 */
public class CarUse {

    //用车信息id
    private String id;

    //使用车辆id
    private String use_car;

    //用车人id
    private String use_user;

    //用车理由
    private String use_reason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUse_car() {
        return use_car;
    }

    public void setUse_car(String use_car) {
        this.use_car = use_car;
    }

    public String getUse_user() {
        return use_user;
    }

    public void setUse_user(String use_user) {
        this.use_user = use_user;
    }

    public String getUse_reason() {
        return use_reason;
    }

    public void setUse_reason(String use_reason) {
        this.use_reason = use_reason;
    }
}
