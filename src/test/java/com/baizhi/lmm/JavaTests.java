package com.baizhi.lmm;

import com.baizhi.lmm.dao.VideoMapper;
import com.baizhi.lmm.po.VideoPo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaTests {

    @Resource
    VideoMapper videoMapper;

    @Test
    public void testquery(){
        List<VideoPo> videoPos = videoMapper.queryByReleaseTime();
        videoPos.forEach(videoPo -> System.out.println(videoPo));
    }

}
