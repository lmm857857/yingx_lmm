package com.baizhi.lmm.controller;

import com.baizhi.lmm.annotation.AddLog;
import com.baizhi.lmm.entity.Video;
import com.baizhi.lmm.service.VideoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("video")
public class VideoController {

    @Resource
    VideoService videoService;

    @ResponseBody
    @RequestMapping("queryByPage")
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = videoService.queryByPage(page, rows);
        return map;
    }
    @ResponseBody
    @RequestMapping("edit")
    public Object edit(Video video, String oper) {

        if (oper.equals("add")) {
            String id = videoService.add(video);
            return id;
        }

        if (oper.equals("edit")) {
            videoService.update(video);
        }

        if (oper.equals("del")) {
            HashMap<String, Object> map = videoService.delete(video);
            return map;
        }

        return "";
    }

    @ResponseBody
    @RequestMapping("uploadVdieo")
    public void uploadVdieo(MultipartFile path, String id, HttpServletRequest request) {
        videoService.uploadVdieos(path, id, request);
    }
}
