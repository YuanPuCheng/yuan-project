package com.zihui.cwoa.financial.dao;

import com.zihui.cwoa.financial.pojo.ProjectInAndOut;
import com.zihui.cwoa.financial.pojo.ProjectMonthDetail;
import com.zihui.cwoa.financial.pojo.ProjectMonthInAndOut;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FinancialMapper {


    /**
     *  查询所有项目的总请款和总报销
     *  @return 查询结果
     */
    List<ProjectInAndOut> queryProjectAllInAndOut();

    /**
     *  查询项目单月总请款和总报销
     *  @return 查询结果
     */
    List<ProjectMonthInAndOut> queryProjectMonthInAndOut(String project_name);


    /**
     *  查询项目单月请款详情
     *  @return 查询结果
     */
    List<ProjectMonthDetail> queryProjectMonthOutDetail(String project_name,String year,String month);

    /**
     *  查询项目单月报销详情
     *  @return 查询结果
     */
    List<ProjectMonthDetail> queryProjectMonthInDetail(String project_name,String year,String month);
}
