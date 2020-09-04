package com.baizhi.lmm.controller;


import com.baizhi.lmm.entity.User;
import com.baizhi.lmm.po.Models;
import com.baizhi.lmm.service.UserService;
import com.baizhi.lmm.util.AliyunSendPhoneUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Resource
    UserService userService;

    //分页查询
    @RequestMapping("queryByPage")
    @ResponseBody
    public HashMap<String,Object> queryByPage(Integer page,Integer rows){
        HashMap<String,Object> map = userService.queryByPage(page, rows);
        return map;
    }

    @RequestMapping("edit")
    @ResponseBody
    public String edit(User user, String oper) {
        String uid = null;
        if (oper.equals("add")) {
            uid = userService.add(user);
        }
        if (oper.equals("edit")) {
            userService.update(user);
        }
        if (oper.equals("del")) {
            userService.delete(user);
        }
        return uid;
    }

    //文件上传
    @RequestMapping("uploadUser")
    public void uploadUser(MultipartFile headImg, String id, HttpServletRequest request) {

        //userService.uploadUser(headImg,id,request); 上传到本地
        //userService.uploadUserAliyun(headImg,id,request); 上传阿里云
        userService.uploadUserAliyuns(headImg,id,request);//封装工具类上传阿里云
    }


    @RequestMapping("sendPhoneCode")
    @ResponseBody
    public String sendPhoneCode(String phone){

        //获取随机数
        String random = AliyunSendPhoneUtil.getRandom(6);

        System.out.println("存储验证码："+random);

        //发送验证码
        String message = AliyunSendPhoneUtil.sendCode(phone, random);

        System.out.println(message);
        return message;
    }
    //使用EasyPOI导出用户数据
    @RequestMapping("exportUser")
    @ResponseBody
    public String exportUser(String path){
        String s = userService.ExportUserByEasyPOI(path);
        return s;
    }
    //使用EasyPOI导入用户数据
    @RequestMapping("importUser")
    @ResponseBody
    public String importUser(String path){
        String s = userService.ImportUserByEasyPOI(path);
        return s;
    }
    //查询各个月份男性和女性的注册人数
    @RequestMapping("/userStatistics")
    @ResponseBody
    public HashMap<String,List<String>> userStatistics(){
        HashMap<String, List<String>> stringListHashMap = userService.selectManOrWomanCount();
        return stringListHashMap;
    }
    //查询每个城市男性和女性的人数
    @RequestMapping("/userDistribution")
    @ResponseBody
    public List<Models> userDistribution(){
        ArrayList<Models> models = userService.selectManOrWomanByCity();
        return models;
    }
}
