package com.ross53.cobar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping(value = "/")
public class TestController {
    @RequestMapping("/")
    public ModelAndView index(Map<String,Object> map){
        map.put("name","calvin welcome...");
        System.out.println("comming sooo.....");
        return new ModelAndView("index",map);//返回的内容就是templetes下面文件的名称
    }
}