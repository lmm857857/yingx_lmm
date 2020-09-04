package com.baizhi.lmm.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "yx_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Excel(name = "ID")
    private String id;
    @Excel(name = "用户名")
    private String username;
    @Excel(name = "手机号")
    private String phone;
    @Column(name="head_img")
    @Excel(name = "头像",type = 2)
    private String headImg;
    @Excel(name = "标签")
    private String sign;
    @Excel(name = "微信")
    private String wechat;
    @Excel(name = "状态")
    private String status;
    @Excel(name = "注册时间",format = "yyyy-MM-dd")
    @Column(name="create_date")
    private Date createDate;
    @Excel(name = "性别")
    private String sex;
    @Excel(name = "省份")
    private String province;

}