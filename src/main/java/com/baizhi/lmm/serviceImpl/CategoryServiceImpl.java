package com.baizhi.lmm.serviceImpl;

import com.baizhi.lmm.annotation.AddLog;
import com.baizhi.lmm.dao.CategoryMapper;
import com.baizhi.lmm.entity.Category;
import com.baizhi.lmm.entity.CategoryExample;
import com.baizhi.lmm.po.CategoryPo;
import com.baizhi.lmm.service.CategoryService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Resource
    CategoryMapper categoryMapper;
    //查询所有的一级类别
    @Override
    public HashMap<String, Object> queryByOnePage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //封装数据
        //总条数   records   一级类别总条数
        CategoryExample example = new CategoryExample();
        example.createCriteria().andLevelsEqualTo(1);
        Integer records = categoryMapper.selectCountByExample(example);
        map.put("records",records);


        //总页数   total   总条数/每页展示条数  是否有余数
        Integer total = records % rows==0? records/rows:records/rows+1;
        map.put("total",total);

        //当前页   page
        map.put("page",page);

        //数据     rows
        //参数  忽略条数,获取几条
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows",categories);

        return map;
    }
    //查询一级类别对应的二级类别
    @Override
    public HashMap<String, Object> queryByTwoPage(Integer page, Integer rows, String parentId) {

        HashMap<String, Object> map = new HashMap<>();

        //封装数据
        //总条数   records   二级类别总条数
        CategoryExample example = new CategoryExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        Integer records = categoryMapper.selectCountByExample(example);
        map.put("records",records);


        //总页数   total   总条数/每页展示条数  是否有余数
        Integer total = records % rows==0? records/rows:records/rows+1;
        map.put("total",total);

        //当前页   page
        map.put("page",page);

        //数据     rows
        //参数  忽略条数,获取几条
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows",categories);

        return map;
    }
    //添加类别
    @AddLog(value = "添加类别")
    @Override
    public void add(Category category) {
        //判断是否一级
        if(category.getParentId()==null){
            category.setLevels(1);
        }else {
            //添加二级
            category.setLevels(2);
        }
        category.setId(UUID.randomUUID().toString());
        //执行添加
        categoryMapper.insert(category);
    }
    //修改
    @AddLog(value = "修改类别")
    @Override
    public void update(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    //删除类别
    @AddLog(value = "删除类别")
    @Override
    public HashMap<String,Object> delete(Category category) {
        HashMap<String, Object> map = new HashMap<>();
        //根据类别对象查询具体信息
        Category cate = categoryMapper.selectOne(category);
        //判断删除的是一级类别 还是二级
        if(cate.getLevels()==1){
            //判断以及类别下是否有二级类别
            CategoryExample example = new CategoryExample();
            //查询条件
            example.createCriteria().andParentIdEqualTo(cate.getId());
            int count = categoryMapper.selectCountByExample(example);
            if(count==0){
                //为0 直接删除
                categoryMapper.deleteByPrimaryKey(category);
                map.put("status","200");
                map.put("message","删除成功");
            }else {
                //有二级 返回错误信息
                map.put("status","400");
                map.put("message","删除失败，该类别下有子类别");
            }
        }else {
            //判断二级类别下是否有视频
            //if(){//有 不能删}else{//没有 直接删除}
            categoryMapper.deleteByPrimaryKey(category);
            map.put("status","200");
            map.put("message","删除成功");
        }

        return map;
    }
    //查询所有类别，供app分类展示
    @Override
    public List<CategoryPo> queryCateVideoList(){
        List<CategoryPo> categoryPos = categoryMapper.quertAllOne();
        for (CategoryPo categorySelfPo : categoryPos) {
            List<CategoryPo> categoryPos1 = categoryMapper.quertAllTwo(categorySelfPo.getId());
            categorySelfPo.setCategoryList(categoryPos1);
        }
        return categoryPos;
    }
}
