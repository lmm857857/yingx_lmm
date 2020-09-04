package com.baizhi.lmm.serviceImpl;

import com.baizhi.lmm.annotation.AddLog;
import com.baizhi.lmm.dao.VideoMapper;
import com.baizhi.lmm.entity.Video;
import com.baizhi.lmm.entity.VideoExample;
import com.baizhi.lmm.po.VideoPo;
import com.baizhi.lmm.service.VideoService;
import com.baizhi.lmm.util.AliyunOssUtil;
import com.baizhi.lmm.util.InterceptVideoPhotoUtil;
import com.baizhi.lmm.vo.VideoVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {

    @Resource
    VideoMapper videoMapper;

    @Resource
    HttpSession session;

    //分页查询展示视频
    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //总条数   records
        VideoExample example = new VideoExample();
        Integer records = videoMapper.selectCountByExample(example);
        map.put("records", records);

        //总页数  totals    总条数/每页展示条数
        Integer totals = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("totals", totals);

        //当前页  page
        map.put("page", page);
        //数据   rows   分页
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Video> videos = videoMapper.selectByRowBounds(new Video(), rowBounds);
        map.put("rows", videos);

        return map;
    }
    //添加视频
    @AddLog(value = "添加视频")
    @Override
    public String add(Video video) {
        String uid = UUID.randomUUID().toString();

        video.setId(uid); //id
        video.setPublishDate(new Date());
        video.setUserId("1");
        video.setCategoryId("1");
        video.setGroupId("1");

        System.out.println("=业务层插入数据=video==" + video);

        //向mysql添加
        videoMapper.insert(video);

        //返回添加数据的id
        return uid;
    }


    @Override
    public void uploadVdieo(MultipartFile path, String id, HttpServletRequest request) {
       /*
       本地上传
       //1.根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/video");

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
            VideoExample example = new VideoExample();
            example.createCriteria().andIdEqualTo(id);

            Video video = new Video();
            //设置修改的结果
            video.setPath(newName);
            video.setCover(newName);
            //设置修改的结果

            //修改
            videoMapper.updateByExampleSelective(video,example);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        //上传到阿里云
        //获取文件名
//上传到阿里云

        //获取文件名
        String filename = path.getOriginalFilename();
        String newName=new Date().getTime()+"-"+filename;

        /*1.视频上传至阿里云
         *上传字节数组
         * 参数：
         *   bucket:存储空间名
         *   headImg: 指定MultipartFile类型的文件
         *   fileName:  指定上传文件名  可以指定上传目录：  目录名/文件名
         * */
        AliyunOssUtil.uploadFileBytes(path,"video/"+newName);


        //频接视频完整路径
        String netFilePath="https://yingxue-ming.oss-cn-beijing.aliyuncs.com/video/"+newName;



        /*2.截取视频第一帧做封面
         * 获取指定视频的帧并保存为图片至指定目录
         * @param videofile 源视频文件路径
         * @param framefile 截取帧的图片存放路径
         * */
        String realPath = session.getServletContext().getRealPath("/upload/cover");

        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }

        //频接本地存放路径    D:\动画.jpg
        // 1585661687777-好看.mp4
        String[] names = newName.split("\\.");
        String interceptName=names[0];
        String coverName=interceptName+".jpg";  //频接封面名字
        String coverPath= realPath+"\\"+coverName;  //频接视频封面的本地绝对路径


        //截取封面保存到本地
        try {
            InterceptVideoPhotoUtil.fetchFrame(netFilePath,coverPath);
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*3.将封面上传至阿里云
         *上传本地文件
         * 参数：
         *   bucket:  存储空间名
         *   fileName:  指定上传文件名  可以指定上传目录：  目录名/文件名
         *   localFilePath: 指定本地文件路径
         * */
        AliyunOssUtil.uploadFile("yingxue-ming","photo/"+coverName,coverPath);

        //4.删除本地文件
        File file1 = new File(coverPath);
        //判断是一个文件，并且文件存在
        if(file1.isFile()&&file1.exists()){
            //删除文件
            boolean isDel = file1.delete();
            System.out.println("删除："+isDel);
        }

        //5.修改视频信息
        //添加修改条件
        VideoExample example = new VideoExample();
        example.createCriteria().andIdEqualTo(id);

        //修改的结果
        Video video = new Video();

        video.setPath("https://yingxue-ming.oss-cn-beijing.aliyuncs.com/video/"+newName);
        video.setCover("https://yingxue-ming.oss-cn-beijing.aliyuncs.com/photo/"+coverName);

        //调用修改方法
        videoMapper.updateByExampleSelective(video,example);

    }
    @AddLog(value = "上传视频")
    @Override
    public void uploadVdieos(MultipartFile path, String id, HttpServletRequest request) {
        //上传到阿里云

        //获取文件名
        String filename = path.getOriginalFilename();
        String newName=new Date().getTime()+"-"+filename;

        /*1.视频上传至阿里云
         *上传字节数组
         * 参数：
         *   bucket:存储空间名
         *   headImg: 指定MultipartFile类型的文件
         *   fileName:  指定上传文件名  可以指定上传目录：  目录名/文件名
         * */
        AliyunOssUtil.uploadFileBytes(path,"video/"+newName);


        //频接视频完整路径
        String netFilePath="https://yingxue-ming.oss-cn-beijing.aliyuncs.com/video/"+newName;


        //拼接本地存放路径    D:\动画.jpg
        // 1585661687777-好看.mp4
        String[] names = newName.split("\\.");
        String interceptName=names[0];
        String coverName=interceptName+".jpg";  //拼接封面名字

        /*2.截取视频第一帧做封面
         * 视频截取  并上传至阿里云
         * 参数：
         *   bucker: 存储空间名
         *   fileName:远程文件文件名    目录名/文件名
         *   coverName：截取的封面名
         * */
        AliyunOssUtil.videoCoverIntercept("yingxue-ming","video/"+newName,"photo/"+coverName);


        //5.修改视频信息
        //添加修改条件
        VideoExample example = new VideoExample();
        example.createCriteria().andIdEqualTo(id);

        //修改的结果
        Video video = new Video();

        video.setPath("https://yingxue-ming.oss-cn-beijing.aliyuncs.com/video/"+newName);
        video.setCover("https://yingxue-ming.oss-cn-beijing.aliyuncs.com/photo/"+coverName);

        //调用修改方法
        videoMapper.updateByExampleSelective(video,example);

    }
    //修改视频
    @AddLog(value = "修改视频")
    @Override
    public void update(Video video) {
        System.out.println("修改：" + video);
        videoMapper.updateByPrimaryKeySelective(video);
    }
    //删除视频
    @AddLog(value = "删除视频")
    @Override
    public HashMap<String, Object> delete(Video video) {
       /* HashMap<String, Object> map = new HashMap<>();
        try {
            //根据id查询视频数据
            VideoExample example = new VideoExample();
            example.createCriteria().andIdEqualTo(video.getId());
            Video videos = videoMapper.selectOneByExample(example);

            //删除视频
            videoMapper.deleteByExample(example);
            //删除本地文件
            //根据绝对路径获取相对路径
            String realPath = session.getServletContext().getRealPath("/upload/video");
            //链接视频完整路径
            String filePath = realPath + "/" + video.getPath();

            File file = new File(filePath);
            //判断是一个文件
            if(file.isFile()&&file.exists()){
                //删除文件
                boolean isDel = file.delete();
                System.out.println("删除"+isDel);
            }
            map.put("status","200");
            map.put("message","删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status","400");
            map.put("message","删除失败");
        }
        return null;
        }*/
        HashMap<String, Object> map = new HashMap<>();
        try {

            //设置条件
            VideoExample example = new VideoExample();
            example.createCriteria().andIdEqualTo(video.getId());
            //根据id查询视频数据
            Video videos = videoMapper.selectOneByExample(example);

            //1.删除数据
            videoMapper.deleteByExample(example);


            //字符串拆分
            String[] pathSplit = videos.getPath().split("/");
            String[] coverSplit = videos.getCover().split("/");

            /*String aaa="video/"+pathSplit[4];
            String aaas="photo/"+coverSplit[4];*/
            String videoName=pathSplit[pathSplit.length-2]+"/"+pathSplit[pathSplit.length-1];
            String coverName=coverSplit[coverSplit.length-2]+"/"+coverSplit[coverSplit.length-1];

            System.out.println(videoName);
            System.out.println(coverName);


            /*2.删除视频
             * 删除阿里云文件
             * 参数：
             *   bucker: 存储空间名
             *   fileName:文件名    目录名/文件名
             * */
            AliyunOssUtil.delete("yingxue-ming",videoName);

            /*3.删除封面
             * 删除阿里云文件
             * 参数：
             *   bucker: 存储空间名
             *   fileName:文件名    目录名/文件名
             * */
            AliyunOssUtil.delete("yingxue-ming",coverName);

            map.put("status", "200");
            map.put("message", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "400");
            map.put("message", "删除失败");
        }
        return map;
    }

    @Override
    public List<VideoVo> queryByReleaseTime() {
        List<VideoPo> videoPos = videoMapper.queryByReleaseTime();

        ArrayList<VideoVo> videoVoList = new ArrayList<>();


        //点赞数
        //遍历视频id
        for (VideoPo v : videoPos) {
            String id = v.getId();
            //根据视频id查询点赞数
            Integer likeCount=24;
           //vo赋值
            VideoVo videoVo = new VideoVo(
                    v.getId(), v.getVTitle(), v.getVBrief(), v.getVPath(),
                    v.getVCover(), v.getVPublishDate(), likeCount, v.getCateName(), v.getHeadImg()
            );
            videoVoList.add(videoVo);
        }
        return videoVoList;
    }
}
