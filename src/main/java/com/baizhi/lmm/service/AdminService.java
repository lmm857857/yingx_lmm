package com.baizhi.lmm.service;

import com.baizhi.lmm.entity.Admin;

import java.util.HashMap;

public interface AdminService {

    HashMap<String, Object> login(Admin admin,String enCode);

}
