package com.zihui.cwoa.financial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 *  财务相关页面
 */
@Controller
@RequestMapping("/fin")
public class FinancialHtmlController {

    @RequestMapping("/queryMoneyFlowByVo")
    public String queryMoneyFlowByVo(){
        return "financial/queryMoneyFlowByVo";
    }

    @RequestMapping("/editMoneyFlowHtml")
    public String editMoneyFlow(){
        return "financial/editMoneyFlow";
    }
}
