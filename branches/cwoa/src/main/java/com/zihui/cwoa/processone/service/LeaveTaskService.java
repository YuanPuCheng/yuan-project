package com.zihui.cwoa.processone.service;

import com.zihui.cwoa.processone.dao.LeaveTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("leaveTask")
public class LeaveTaskService {

    @Autowired
    private LeaveTaskMapper leaveTaskMapper;

    /**
     *  插入请假 出差记录
     *  @param list 记录集合
     */
    public void insertLeave(List<String> list){
        leaveTaskMapper.insertLeave(list);
    }
}
