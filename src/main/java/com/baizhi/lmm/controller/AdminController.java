package com.baizhi.lmm.controller;


import com.baizhi.lmm.entity.Admin;
import com.baizhi.lmm.service.AdminService;
import com.baizhi.lmm.util.ImageCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;


@Controller
@RequestMapping("admin")
public class AdminController {

    @Resource
    AdminService adminService;

    //获取验证码
    @RequestMapping("getImageCode")
    public void getImageCode(HttpServletRequest request, HttpServletResponse response){
        //1.根据验证码工具类获取随机字符
        String code = ImageCodeUtil.getSecurityCode();
        System.out.println("验证码："+code);
        //2.存储随机字符
        request.getSession().setAttribute("imageCode","code");
        //3.根据随机字符生成图片
        BufferedImage image = ImageCodeUtil.createImage(code);
        //4.将图片响应到页面
        try {
            ImageIO.write(image,"png",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //管理员登陆
    @RequestMapping("login")
    @ResponseBody
    public HashMap<String,Object> login(Admin admin, String enCode){
        System.out.println(admin + "---" + enCode);
        //调用业务层方法
        HashMap<String, Object> map = adminService.login(admin, enCode);
        return map;
    }

    //管理员退出
    @RequestMapping("logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("admin");
        return "redirect:/login/login.jsp";
    }
}
