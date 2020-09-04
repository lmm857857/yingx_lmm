package com.baizhi.lmm.controller;

import com.baizhi.lmm.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;

@Controller
@RequestMapping("log")
public class LogController {
    @Resource
    LogService logService;

    @RequestMapping("showAll")
    @ResponseBody
    public HashMap<String, Object> showAll(Integer page,Integer rows){
        //查询所有日志
        HashMap<String, Object> map = logService.showAllLog(page, rows);
        return map;
    }
}
