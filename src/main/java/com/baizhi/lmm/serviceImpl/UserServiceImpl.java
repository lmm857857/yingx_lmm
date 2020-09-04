package com.baizhi.lmm.serviceImpl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baizhi.lmm.annotation.AddLog;
import com.baizhi.lmm.dao.UserDao;
import com.baizhi.lmm.entity.User;
import com.baizhi.lmm.entity.UserExample;
import com.baizhi.lmm.entity.VideoExample;
import com.baizhi.lmm.po.City;
import com.baizhi.lmm.po.Models;
import com.baizhi.lmm.po.MonthAndSex;
import com.baizhi.lmm.service.UserService;
import com.baizhi.lmm.util.AliyunOssUtil;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    UserDao userDao;

    @Override
    public HashMap queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //封装数据
        //总条数   records
        UserExample example = new UserExample();
        Integer records = userDao.selectCountByExample(example);
        map.put("records",records);
        //总页数   total   总条数/每页展示条数  是否有余数
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total",total);
        //当前页   page
        map.put("page",page);
        //数据     rows
        //参数  忽略条数,获取几条
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<User> users = userDao.selectByRowBounds(new User(), rowBounds);
        map.put("rows",users);
        return map;
    }

    //用户添加
    @AddLog(value = "用户添加")
    @Override
    public String add(User user) {
        //UUID
        String uid = UUID.randomUUID().toString();
        user.setId(uid);
        //默认状态
        user.setStatus("1");
        //注册时间
        user.setCreateDate(new Date());
        userDao.insert(user);
        return uid;
    }

    //文件上传
    @Override
    public void uploadUser(MultipartFile headImg, String id, HttpServletRequest request) {
        //1.根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");

        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }

        //2获取文件名
        String filename = headImg.getOriginalFilename();

        String newName=new Date().getTime()+"-"+filename;

        try {
            //3.文件上传
            headImg.transferTo(new File(realPath,newName));

            //4.图片修改
            //修改的条件
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(id);

            User user = new User();
            user.setHeadImg(newName); //设置修改的结果

            //修改
            userDao.updateByExampleSelective(user,example);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //文件上传阿里云（byte数组上传）
    @Override
    public void uploadUserAliyun(MultipartFile headImg, String id, HttpServletRequest request) {
        byte[] bytes =null;
        try {
            bytes = headImg.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取文件名
        String filename = headImg.getOriginalFilename();
        String newName = new Date().getTime()+"-"+filename;
        //文件上传至阿里云
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4GHypJXuUaSouaBGmFJ1";
        String accessKeySecret = "WrsRdSGAj7lbAL5cJMT3m5UxQmLy5I";
        String bucket="yingxue-ming";   //存储空间名
        String fileName=newName;  //指定上传文件名  可以指定上传目录

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,accessKeySecret);

        // 上传Byte数组。
        ossClient.putObject(bucket, fileName, new ByteArrayInputStream(bytes));

        // 关闭OSSClient。
        ossClient.shutdown();


        //2.图片信息的修改
        //修改的条件
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(id);

        User user = new User();
        user.setHeadImg("http://yingxue-ming.oss-cn-beijing.aliyuncs.com/"+newName);  //设置修改的结果   网络路径
        //http://yingxue-ming.oss-cn-beijing.aliyuncs.com/
        //修改
        userDao.updateByExampleSelective(user,example);
    }


    //文件上传阿里云（byte数组上传、封装工具类）
    @AddLog(value = "头像上传")
    @Override
    public void uploadUserAliyuns(MultipartFile headImg, String id, HttpServletRequest request) {
        //获取文件名
        String filename = headImg.getOriginalFilename();
        String newName=new Date().getTime()+"-"+filename;
        //1.文件上传至阿里云
        AliyunOssUtil.uploadFileBytes(headImg,newName);
        //2.图片信息的修改
        //修改的条件
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(id);

        User user = new User();
        user.setHeadImg("http://yingxue-ming.oss-cn-beijing.aliyuncs.com/"+newName);  //设置修改的结果   网络路径
        //
        //修改
        userDao.updateByExampleSelective(user,example);
    }
    @AddLog(value = "用户修改")
    @Override
    public void update(User user) {

        userDao.updateByPrimaryKeySelective(user);
    }

    @AddLog(value = "用户删除")
    @Override
    public void delete(User user) {
        HashMap<String, Object> map = new HashMap<>();
        try {

            //设置条件
            VideoExample example = new VideoExample();
            example.createCriteria().andIdEqualTo(user.getId());
            //根据id查询用户数据
            User user1 = userDao.selectOneByExample(example);

            //1.删除数据
            userDao.deleteByExample(example);

            //字符串拆分
            String[] pathSplit = user1.getHeadImg().split("/");

            String HeadImg=pathSplit[pathSplit.length-2]+"/"+pathSplit[pathSplit.length-1];

            System.out.println(HeadImg);

            /*2.删除头像
             * 删除阿里云文件
             * 参数：
             *   bucker: 存储空间名
             *   fileName:文件名    目录名/文件名
             * */
            AliyunOssUtil.delete("yingxue-ming",HeadImg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //查出所有用户，并使用EasyPOI导出
    @Override
    public String ExportUserByEasyPOI(String path) {
        if(!path.contains(".xls")){
            return "导出失败";
        }
        List<User> users = userDao.selectAll();
        //从阿里云拿图片？
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户表", "用户信息"), User.class, users);
        try {
            workbook.write(new FileOutputStream(new File(path)));
            workbook.close();
            return "导出成功";
        } catch (IOException e) {
            e.printStackTrace();
            return "导出失败";
        }
    }

    //使用EasyPOI将用户导入
    @Override
    public String ImportUserByEasyPOI(String path) {
        if(!path.contains(".xls")){
            return "导入失败";
        }
        //配置导入参数
        ImportParams params = new ImportParams();
        params.setTitleRows(1);  //标题占几行 默认0
        params.setHeadRows(1);  //表头占几行  默认1
        try {
            //获取导入的文件
            FileInputStream inputStream = new FileInputStream(new File(path));
            //导入    参数：读入流,实体类映射
            List<User> userList = ExcelImportUtil.importExcel(inputStream,User.class, params);
            //遍历
            for (User user : userList) {
                user.setId(UUID.randomUUID().toString());
                user.setCreateDate(new Date());
                userDao.insert(user);
            }
            return "导入成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "导入失败";
        }
    }
    //查询各个月份男性和女性的注册人数
    @Override
    public HashMap<String,List<String>> selectManOrWomanCount() {
        HashMap<String, List<String>> map = new HashMap<>();
        ArrayList<String> month = new ArrayList<>();
        ArrayList<String> boys = new ArrayList<>();
        ArrayList<String> womans = new ArrayList<>();
        List<MonthAndSex> monthAndSexes = userDao.selectManCountByCreateDate();
        for (MonthAndSex monthAndSex : monthAndSexes) {
            month.add(monthAndSex.getMonth());//月份
            boys.add(monthAndSex.getCount());//男姓
        }
        List<MonthAndSex> monthAndSexes1 = userDao.selectWomanCountByCreateDate();
        for (MonthAndSex monthAndSex : monthAndSexes1) {
            womans.add(monthAndSex.getCount());//女性
        }
        map.put("month",month);
        map.put("boys",boys);
        map.put("womans",womans);
        return map;
    }

    //查询每个城市男性和女性的注册人数
    @Override
    public ArrayList<Models> selectManOrWomanByCity() {
        //查男性
        List<City> manCities = userDao.selectManCountByCity();
        //查女性
        List<City> womanCities = userDao.selectWomanCountByCity();
        ArrayList<Models> models = new ArrayList<>();
        models.add(new Models("男性",manCities));
        models.add(new Models("女性",womanCities));
        return models;
    }
}
