package com.baizhi.lmm.dao;


import com.baizhi.lmm.entity.Video;
import com.baizhi.lmm.po.VideoPo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideoMapper extends Mapper<Video> {
    List<VideoPo> queryByReleaseTime();
}