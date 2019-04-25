package com.zihui.cwoa.financial.pojo;

/**
 * 项目单月请款和报销详情查询结果实体类
 */
public class ProjectMonthDetail {
    //项目名称
    private String project_name;
    private String flow_year;
    private String flow_month;
    //总请款
    private String flow_money_out;
    //总报销
    private String flow_money_in;
    private String user_name;
    private String flow_date;
    private String user_id;
    private String flow_process_id;

    public String getFlow_process_id() {
        return flow_process_id;
    }

    public void setFlow_process_id(String flow_process_id) {
        this.flow_process_id = flow_process_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getFlow_year() {
        return flow_year;
    }

    public void setFlow_year(String flow_year) {
        this.flow_year = flow_year;
    }

    public String getFlow_month() {
        return flow_month;
    }

    public void setFlow_month(String flow_month) {
        this.flow_month = flow_month;
    }

    public String getFlow_money_out() {
        return flow_money_out;
    }

    public void setFlow_money_out(String flow_money_out) {
        this.flow_money_out = flow_money_out;
    }

    public String getFlow_money_in() {
        return flow_money_in;
    }

    public void setFlow_money_in(String flow_money_in) {
        this.flow_money_in = flow_money_in;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFlow_date() {
        return flow_date;
    }

    public void setFlow_date(String flow_date) {
        this.flow_date = flow_date;
    }
}
