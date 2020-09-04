package com.baizhi.lmm.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class poiTeacher {
    //@Excel easyPoi注解 可配置各种参数
    @ExcelIgnore //不展示
    private String id;
    @Excel(name = "名字",needMerge = true)
    private String name;
    @Excel(name = "年龄",needMerge = true)
    private Integer age;
    //@Excel(name = "生日",format = "yyyy-MM-dd",needMerge = true)
    @ExcelIgnore
    private Date date;

    //关系属性
    @ExcelCollection(name = "学生信息")
    private List<poiEmp> emps;
}
