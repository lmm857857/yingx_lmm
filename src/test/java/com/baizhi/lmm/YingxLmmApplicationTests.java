package com.baizhi.lmm;

import com.baizhi.lmm.dao.AdminDao;
import com.baizhi.lmm.dao.UserDao;
import com.baizhi.lmm.entity.Admin;
import com.baizhi.lmm.entity.User;
import com.baizhi.lmm.entity.UserExample;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class YingxLmmApplicationTests {

    @Resource
    AdminDao adminDao;

    @Resource
    UserDao userDao;
    //通用Mapper测试练习
    //查所有
    @Test
    public void testUser1(){
        List<User> users = userDao.selectAll();
        users.forEach(user -> System.out.println(user));

    }
    //根据id查询
    @Test
    public void testUser2(){
        User user = userDao.selectByPrimaryKey("4");
        System.out.println(user);
    }
    //条件查询
    @Test
    public void testUser3(){
        //设置查询条件
        UserExample example = new UserExample();
        //example.createCriteria().andIdEqualTo("4");//id为4
        //example.createCriteria().andIdNotEqualTo("4");//id不为4的
        //example.createCriteria().andIdBetween("1","3");//id为1和3之间(闭区间)
        //example.createCriteria().andIdNotBetween("1","3");//id不在1和3之间
        //example.createCriteria().andIdIsNull();//id为null
        //example.createCriteria().andIdIsNotNull();//id不为null
        //example.createCriteria().andIdLike("%2%");//模糊
        //example.createCriteria().andIdNotLike("%2%");  // 模糊条件
        //example.createCriteria().andIdGreaterThan("1");  // >  id(大于) 多少
        //example.createCriteria().andIdGreaterThanOrEqualTo("1"); //  >=  id(大于等于) 多少
        //example.createCriteria().andIdLessThan("1"); //  <  id(小于) 多少
        //example.createCriteria().andIdLessThanOrEqualTo("1"); //  <=  id(小于等于) 多少
        //example.createCriteria().andIdIn(Arrays.asList("1","2","3")); //id满足集合数据的数据
        //example.createCriteria().andIdNotIn(Arrays.asList("1","2","3"));//id不满足集合数据的数据
        //example.setOrderByClause("upload_time desc" ); //排序查询
        List<User> users = userDao.selectByExample(example);
        users.forEach(user -> System.out.println(user));
    }
    //查询一条数据
    @Test
    public void testUser4(){
        //设置查询条件对象
        User user = new User();
        user.setId("4");
        //查询数据
        User one = userDao.selectOne(user);
        System.out.println(one);
    }
    //根据Example查询一条数据
    @Test
    public void testUser5(){
        //设置查询条件
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo("4");
        //查询数据
        User one = userDao.selectOneByExample(example);
        System.out.println(one);
    }
    //分页查询
    @Test
    public void testUser6(){
        //相当于是一个条件，没有条件对所有数据进行分页
        UserExample example = new UserExample();
        //分页查询： 参数：忽略几条,获取几条数据
        RowBounds rowBounds = new RowBounds(0, 2);
        //查询数据
        List<User> users = userDao.selectByExampleAndRowBounds(example, rowBounds);
        users.forEach(user -> System.out.println(user));
    }
    //查询数量
    @Test
    public void testUser7(){
        User user = new User();
        user.setStatus("1");
        int i = userDao.selectCount(user);
        System.out.println(i);
    }
    //查询数量(在example中设置条件，返回数量)
    @Test
    public void testUser8(){
        UserExample example = new UserExample();
        example.createCriteria().andUsernameLike("%明%");
        int i = userDao.selectCountByExample(example);
        System.out.println(i);
    }

    //插入

    //根据对象插入数据
    @Test
    public void testUser9(){
        //封装对象数据
        User user = new User("3","毛利兰","hah1","hah1","hah1","hah1","hah1",new Date(),"女","河南");
        //修改数据
        userDao.insert(user);
    }
    //可选择插入数据
    @Test
    public void testUser10(){
        //封装对象数据
        User user = new User();
        user.setId("5");
        user.setUsername("阿笠博士");
        user.setStatus("1");
        //修改数据
        userDao.insertSelective(user);
    }

    //修改

    //根据主键修改数据
    @Test
    public void testUser11(){
        //设置要修改的数据
        User user = new User();
        user.setId("5");
        user.setPhone("112121");
        //修改数据 对象中没有赋值的字段数据会变为空
        userDao.updateByPrimaryKey(user);
    }
    //根据主键修改数据
    @Test
    public void testUser12(){
        //设置要修改的数据
        User user = new User();
        user.setId("5");
        user.setUsername("阿笠博士");
        //修改数据 对象中没有赋值的字段数据不会变为空
        userDao.updateByPrimaryKeySelective(user);
    }
    //根据条件修改数据
    @Test
    public void testUser13(){
        //设置要修改的数据
        User user = new User();
        user.setSign("laotou");
        //添加修改的条件
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo("5");
        //修改数据 对象中没有赋值的字段数据会变为空
        userDao.updateByExample(user,example);
    }
    //根据条件修改数据
    @Test
    public void testUser14(){
        //设置要修改的数据
        User user = new User();
        user.setUsername("阿笠博士");
        //添加修改的条件
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo("5");
        //修改数据 对象中没有赋值的字段数据会变为空
        userDao.updateByExampleSelective(user,example);
    }

    //删除
    //根据id删除
    @Test
    public void testUser15(){
        userDao.deleteByPrimaryKey("5");
    }
    //根据条件删除数据
    @Test
    public void testUser16(){
        //删除的条件
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo("毛利兰");
        //修改数据
        userDao.deleteByExample(example);
    }
    //根据条件删除数据
    @Test
    public void testUser17(){
        //删除的条件
        User user = new User();
        user.setUsername("江户川柯南");
        //修改数据
        userDao.delete(user);
    }
    //根据id查询数据是否存在 返回值为布尔类型
    @Test
    public void testUser18(){
        boolean b = userDao.existsWithPrimaryKey("3");
        System.out.println(b);
    }
    /*
    @Test
    public void testUser(){

        UserExample example = new UserExample();
       // MBG通用mapper测试
        //条件对象（id查询）
        //example.createCriteria().andIdEqualTo("1");
        List<User> users = userMapper.selectByExample(example);
        users.forEach(user -> System.out.println(user));

    }

    @Test
    public void insertUser() {
        //插入
        UserExample example = new UserExample();
        User user = new User("4",null,"hah1","hah1","hah1","hah1","hah1",new Date());
        userMapper.insert(user);
    }

    @Test
    public void updateUser() {
        //修改
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo("5");
        User user = new User();
        user.setId("55");
        user.setUsername("江户川柯南");
        //未给值为空
        userMapper.updateByExample(user,example);
    }
    @Test
    public void updateUser2() {
        //修改
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo("4");
        User user = new User();
        user.setUsername("江户川柯南");
        //只修改要修改的值
        userMapper.updateByExampleSelective(user,example);
    }
    @Test
    public void deleteUser() {
        //条件对象
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo("55");
        //删除
        userMapper.deleteByExample(example);
    }

    @Test
    public void queryCount() {
        UserExample example = new UserExample();
        long l = userMapper.countByExample(example);
        System.out.println(l);
    }
    */
    @Test
    public void contextLoads() {
        Admin admin = adminDao.queryByUsername("admin");
        System.out.println(admin);
    }

}
