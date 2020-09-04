package com.baizhi.lmm.dao;

import com.baizhi.lmm.entity.Admin;

public interface AdminDao {

   Admin queryByUsername(String username);
}
