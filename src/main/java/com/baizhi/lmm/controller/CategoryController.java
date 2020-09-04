package com.baizhi.lmm.controller;


import com.baizhi.lmm.entity.Category;
import com.baizhi.lmm.service.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Resource
    CategoryService categoryService;

    //分页查询一级类别
    @RequestMapping("queryByOnePage")
    public HashMap<String,Object> queryByOnePage(Integer page, Integer rows){
        return categoryService.queryByOnePage(page, rows);
    }
    //查询一级类别对应的二级类别
    @RequestMapping("queryByTwoPage")
    public HashMap<String,Object> queryByTwoPage(Integer page,Integer rows,String parentId){
        return categoryService.queryByTwoPage(page, rows, parentId);
    }


    @RequestMapping("edit")
    @ResponseBody
    public Object edit(Category category, String oper) {
        String uid = null;
        if (oper.equals("add")) {
            categoryService.add(category);
        }
        if (oper.equals("edit")) {
            categoryService.update(category);
        }
        if (oper.equals("del")) {
            HashMap<String, Object> map = categoryService.delete(category);
            System.out.println(map.get("message"));
            return map;
        }
        return null;
    }
}
