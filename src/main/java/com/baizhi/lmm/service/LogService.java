package com.baizhi.lmm.service;

import java.util.HashMap;

public interface LogService {
    //查所有日志信息
    HashMap<String, Object> showAllLog(Integer page, Integer rows);
}
