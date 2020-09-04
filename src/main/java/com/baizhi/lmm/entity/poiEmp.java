package com.baizhi.lmm.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class poiEmp {
    //@Excel easyPoi注解 可配置各种参数
    @Excel(name = "ID")
    private String id;
    @Excel(name = "名字")
    private String name;
    @Excel(name = "年龄")
    private Integer age;
    @Excel(name = "生日",format = "yyyy-MM-dd")
    private Date date;
}
