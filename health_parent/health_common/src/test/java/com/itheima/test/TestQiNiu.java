package com.itheima.test;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName TestQiNiu
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/11/30 12:18
 * @Version V1.0
 */
public class TestQiNiu {

    // 测试通过路径完成上传
    // @Test
    // public void update(){
    //     //构造一个带指定 Region 对象的配置类
    //     Configuration cfg = new Configuration(Zone.zone0());
    //     //...其他参数参考类注释
    //     UploadManager uploadManager = new UploadManager(cfg);
    //     //...生成上传凭证，然后准备上传
    //     String accessKey = "liyKTcQC5TP1LrhgZH6Xem8zqMXbEtVgfAINP53v";
    //     String secretKey = "f5zpuzKAPceEMG77-EK3XbwqgOBRDXDawG4UHRta";
    //     String bucket = "itcast-health80";
    //     //如果是Windows情况下，格式是 D:\\qiniu\\test.png
    //     String localFilePath = "D:/123.jpg";
    //     //默认不指定key的情况下，以文件内容的hash值作为文件名
    //     String key = null;
    //     Auth auth = Auth.create(accessKey, secretKey);
    //     String upToken = auth.uploadToken(bucket);
    //     try {
    //         Response response = uploadManager.put(localFilePath, key, upToken);
    //         //解析上传成功的结果
    //         DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
    //         System.out.println(putRet.key); // 存放到服务器的图片名称
    //         System.out.println(putRet.hash);
    //     } catch (QiniuException ex) {
    //         Response r = ex.response;
    //         System.err.println(r.toString());
    //         try {
    //             System.err.println(r.bodyString());
    //         } catch (QiniuException ex2) {
    //             //ignore
    //         }
    //     }
    // }

    // 使用字节数组
    @Test
    public void upload2(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "liyKTcQC5TP1LrhgZH6Xem8zqMXbEtVgfAINP53v";
        String secretKey = "f5zpuzKAPceEMG77-EK3XbwqgOBRDXDawG4UHRta";
        String bucket = "itcast-health80";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        try {
            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8"); // 图片转换成字节的数组
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(uploadBytes, key, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (UnsupportedEncodingException ex) {
            //ignore
        }
    }

    // 删除图片
    @Test
    public void delete(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...生成上传凭证，然后准备上传
        String accessKey = "liyKTcQC5TP1LrhgZH6Xem8zqMXbEtVgfAINP53v";
        String secretKey = "f5zpuzKAPceEMG77-EK3XbwqgOBRDXDawG4UHRta";
        String bucket = "itcast-health80";
        String key = "FuM1Sa5TtL_ekLsdkYWcf5pyjKGu";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }
}
