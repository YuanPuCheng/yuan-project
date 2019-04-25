package com.zihui.cwoa.financial.pojo;

/**
 * 项目单月总请款和总报销查询结果实体类
 */
public class ProjectMonthInAndOut {

    //项目名称
    private String project_name;
    private String flow_year;
    private String flow_month;
    //总请款
    private String all_out;
    //总报销
    private String all_in;

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

    public String getAll_out() {
        return all_out;
    }

    public void setAll_out(String all_out) {
        this.all_out = all_out;
    }

    public String getAll_in() {
        return all_in;
    }

    public void setAll_in(String all_in) {
        this.all_in = all_in;
    }
}
