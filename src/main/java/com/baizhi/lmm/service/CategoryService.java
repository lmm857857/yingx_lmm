package com.baizhi.lmm.service;

import com.baizhi.lmm.entity.Category;
import com.baizhi.lmm.po.CategoryPo;

import java.util.HashMap;
import java.util.List;

public interface CategoryService {
    //查询一级类别
    HashMap<String,Object> queryByOnePage(Integer page, Integer rows);
    //查询一级类别对应的二级类别
    HashMap<String,Object> queryByTwoPage(Integer page,Integer rows,String parentId);
    //添加一级类别
    void add(Category category);
    //修改一级或二级类别
    void update(Category category);
    //删除一级 二级类被
    HashMap<String,Object> delete(Category category);
    //查询所有类别，供app分类展示
    public List<CategoryPo> queryCateVideoList();
}
