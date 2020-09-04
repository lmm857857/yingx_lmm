package com.baizhi.lmm.service;

import com.baizhi.lmm.entity.User;
import com.baizhi.lmm.po.Models;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface UserService {
    //分页查询
    HashMap queryByPage(Integer page, Integer rows);
    //用户添加
    String add(User user);
    //文件上传
    void uploadUser(MultipartFile headImg, String id, HttpServletRequest request);
    //文件上传阿里云
    void uploadUserAliyun(MultipartFile headImg, String id, HttpServletRequest request);
    //文件上传阿里云（封装工具类）
    void uploadUserAliyuns(MultipartFile headImg, String id, HttpServletRequest request);
    //修改状态
    void update(User user);
    //删除
    void delete(User user);
    //查出所有用户，并使用EasyPOI导出
    String  ExportUserByEasyPOI(String path);
    //使用EasyPOI将用户导入
    String  ImportUserByEasyPOI(String path);
    //查询各个月份男性和女性的注册人数
    HashMap<String, List<String>> selectManOrWomanCount();
    //查询每个城市男性和女性的注册人数
    ArrayList<Models> selectManOrWomanByCity();
}
