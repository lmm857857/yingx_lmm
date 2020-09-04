package com.baizhi.lmm;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import org.junit.Test;

import java.io.File;
import java.util.List;


public class AliyunTests {

    // Endpoint以杭州为例，其它Region请按实际情况填写。
    String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    String accessKeyId = "LTAI4GHypJXuUaSouaBGmFJ1";
    String accessKeySecret = "WrsRdSGAj7lbAL5cJMT3m5UxQmLy5I";

    //创建存储空间
    @Test
    public void createBucket() {

        String bucketName = "yingx-cs";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建存储空间。
        ossClient.createBucket(bucketName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //上传
    //本地文件简单上传
    @Test
    public void uploadFile() {

        String bucket="yingxue-ming";//存储空间名
        String fileName="photo/宾利.jpg";//指定上传文件名  可以指定上传目录
        String localFile="C:\\Users\\Administrator\\Pictures\\Camera Roll\\宾利.jpg";//指定本地文件路径

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, new File(localFile));

        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);

        // 上传文件
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //查所有bucket
    @Test
    public void queryAllBucket(){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 列举存储空间。
        List<Bucket> buckets = ossClient.listBuckets();
        for (Bucket bucket : buckets) {
            System.out.println(" - " + bucket.getName());
        }

        // 关闭OSSClient。
        ossClient.shutdown();
    }
    //文件下载到本地
    @Test
    public void downLoad(){
        String bucketName = "yingxue-ming";
        String objectName = "photo/宾利.jpg";
        String localFile="C:\\Users\\Administrator\\Pictures\\Camera Roll\\测试.jpg";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFile));

        // 关闭OSSClient。
        ossClient.shutdown();
    }
    //删除文件
    @Test
    public void delete(){
        String bucketName = "yingxue-ming";
        String objectName = "photo/宾利.jpg";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

}