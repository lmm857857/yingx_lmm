package com.baizhi.lmm.serviceImpl;

import com.baizhi.lmm.dao.LogMapper;
import com.baizhi.lmm.entity.Log;
import com.baizhi.lmm.entity.LogExample;
import com.baizhi.lmm.entity.User;
import com.baizhi.lmm.entity.UserExample;
import com.baizhi.lmm.service.LogService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


@Service
@Transactional
public class LogServiceImpl implements LogService {

    @Resource
    LogMapper logMapper;


    @Override
    public HashMap<String, Object> showAllLog(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //封装数据
        //总条数   records
        LogExample example = new LogExample();
        Integer records = logMapper.selectCountByExample(example);
        map.put("records",records);
        //总页数  totals    总条数/每页展示条数
        Integer totals = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total",totals);
        //当前页  page
        map.put("page",page);
        //数据   rows   分页
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Log> logs = logMapper.selectByRowBounds(new Log(), rowBounds);
        map.put("rows",logs);
        return map;
    }

}
