package com.baizhi.lmm.dao;


import com.baizhi.lmm.entity.User;
import com.baizhi.lmm.po.City;
import com.baizhi.lmm.po.MonthAndSex;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User> {

    //查询每个月份男性的注册用户数量
    List<MonthAndSex> selectManCountByCreateDate();
    //查询每个月份女性的注册用户数量
    List<MonthAndSex> selectWomanCountByCreateDate();
    //查询每个城市男性的注册用户数量
    List<City> selectManCountByCity();
    //查询每个城市女性的注册用户数量
    List<City> selectWomanCountByCity();
}