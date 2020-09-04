package com.baizhi.lmm;

import com.baizhi.lmm.dao.AdminDao;
import com.baizhi.lmm.dao.VideoMapper;
import com.baizhi.lmm.entity.Admin;
import com.baizhi.lmm.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class adminTest {

    @Resource
    AdminDao adminDao;
    @Resource
    AdminService adminService;
    @Resource
    VideoMapper videoMapper;


    @Test
    public void contextLoads() {
        Admin admin = adminDao.queryByUsername("admin");
        System.out.println(admin);
    }


}
