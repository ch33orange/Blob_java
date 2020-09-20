package com.njupt.agency.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

/**
 * @email chivseg-hao@qq.com
 * @author:ch33orange
 * @date: 2020/9/12
 * @time: 20:28
 */
@Controller
public class IndexController {
    //主界面
    @GetMapping("/index")
    public String index(){
        return "test";
    }

}