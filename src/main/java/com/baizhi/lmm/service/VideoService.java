package com.baizhi.lmm.service;

import com.baizhi.lmm.entity.Video;
import com.baizhi.lmm.vo.VideoVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface VideoService {
    //分页查询展示视频
    HashMap<String, Object> queryByPage(Integer page, Integer rows);
    //添加视频
    String add(Video video);
    //文件上传（上传到阿里云）
    void uploadVdieo(MultipartFile path, String id, HttpServletRequest request);
    void uploadVdieos(MultipartFile path, String id, HttpServletRequest request);
    //修改视频
    void update(Video video);
    //删除视频
    HashMap<String, Object> delete(Video video);
    //app首页展示
    List<VideoVo> queryByReleaseTime();
}
