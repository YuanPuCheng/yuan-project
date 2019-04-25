package com.zihui.cwoa.financial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fin")
public class FinancialHtmlController {

    @RequestMapping("/queryProAllInAndOut")
    public String queryProjectAllInAndOut(){
        return "financial/queryProjectAllInAndOut";
    }

    @RequestMapping("/queryProMonthInAndOut")
    public String queryProjectMonthInAndOut(){
        return "financial/queryProjectMonthInAndOut";
    }

    @RequestMapping("/queryProMonthOutDetail")
    public String queryProjectMonthOutDetail(){
        return "financial/queryProjectMonthOutDetail";
    }

    @RequestMapping("/queryProMonthInDetail")
    public String queryProjectMonthInDetail(){
        return "financial/queryProjectMonthInDetail";
    }
}
