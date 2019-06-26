package com.zihui.cwoa.politics.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *  车辆相关页面
 */
@Controller
@RequestMapping("/car")
public class CarHtmlController {

    @RequestMapping("/manageCarHtml")
    public String manageCarHtml(){
        return "politics/manageCar";
    }
}
