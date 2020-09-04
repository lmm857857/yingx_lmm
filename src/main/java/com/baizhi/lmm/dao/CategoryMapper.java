package com.baizhi.lmm.dao;

import com.baizhi.lmm.entity.Category;
import com.baizhi.lmm.po.CategoryPo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CategoryMapper extends Mapper<Category>{
    //供app接口使用
    //查询一级类别
    List<CategoryPo> quertAllOne();
    //根据一级查二级
    List<CategoryPo> quertAllTwo(String parentId);
    //查询该一级类别下共有多少条二级类别
    Integer findAllSecondCount(String parentId);
}