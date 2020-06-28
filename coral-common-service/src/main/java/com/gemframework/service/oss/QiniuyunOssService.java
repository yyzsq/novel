package com.gemframework.service.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.model.*;
import com.gemframework.common.constant.DictionaryKeys;
import com.gemframework.common.utils.GemDateUtils;
import com.gemframework.model.entity.bo.OssBucket;
import com.gemframework.model.entity.bo.OssObject;
import com.gemframework.model.enums.OssStorageType;
import com.gemframework.service.DictionaryService;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class QiniuyunOssService {


    /**
     * 缺省的最大上传文件大小：5M
     */
    private final int DEFAULT_MAXIMUM_FILE_SIZE = 5;


    @Autowired
    DictionaryService dictionaryService;


    public String getAccessKeyId(){
        return "K48UhI6KpHys18pFPJ0rFBf98PG7wjs6FLD7G5rO";
//        return dictionaryService.getMapValue(DictionaryKeys.OSS_ALIYUN_CFG.KEY,DictionaryKeys.OSS_ALIYUN_CFG.ACCESS_KEY_ID);
    }
    public String getAccessKeySecret(){
        return "YduvifTiVDc88u1aPXzsABpt_IOtSlf1wX3lg-Ww";
//        return dictionaryService.getMapValue(DictionaryKeys.OSS_ALIYUN_CFG.KEY,DictionaryKeys.OSS_ALIYUN_CFG.ACCESS_KEY_SECRET);
    }

    public String getCDNHost(){
        return dictionaryService.getMapValue(DictionaryKeys.OSS_QINIUYUN_CFG.KEY,DictionaryKeys.OSS_QINIUYUN_CFG.CDN_HOST);
    }



    /**
     * 获取上传文件大小配置
     * @return
     */
    private long getMaximumFileSizeAllowed() {
        // 缓存单位是M
        String maxFileSize = dictionaryService.getMapValue(DictionaryKeys.OSS_ALIYUN_CFG.KEY,DictionaryKeys.OSS_ALIYUN_CFG.MAXIMUM_FILE_SIZE);
        if(maxFileSize == null || StringUtils.isEmpty(maxFileSize)) {
            return DEFAULT_MAXIMUM_FILE_SIZE * 1024L * 1024L;
        }else {
            return Long.valueOf(maxFileSize.trim()) * 1024L * 1024L;
        }
    }


    /**
     * 所有文件列表
     */
    public List<OssObject> listObjects(String bucketName) {
        List<OssObject> list = Lists.newArrayList();
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        Auth auth = Auth.create(getAccessKeyId(), getAccessKeySecret());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //文件名前缀
        String prefix = "";
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";
        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucketName, prefix, limit, delimiter);
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items) {
                double size = new BigDecimal(item.fsize/(1024)).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                OssObject ossObject = OssObject.builder()
                        .name(item.key.substring(item.key.lastIndexOf("/")+1))
                        .bucketName(bucketName)
                        .fileSize(size + "KB")
                        .contentType(item.mimeType)
                        .keyPath(item.key)
                        .fileUrl(getCDNHost()+"/"+item.key)
                        .build();
                list.add(ossObject);
            }
        }
        return list;
    }

    /**
     * 所有存储桶
     */
    public List<OssBucket> listBuckets() {
        List<OssBucket> list = new ArrayList<>();
        String value = dictionaryService.getMapValue(DictionaryKeys.OSS_QINIUYUN_CFG.KEY,DictionaryKeys.OSS_QINIUYUN_CFG.BUCKETS_NAME);
        String[] bucketsName = value.split(",");
        if(bucketsName != null && bucketsName.length>0){
            for (String bucketName : bucketsName) {
                OssBucket ossBucket = OssBucket.builder()
                        .name(bucketName)
                        .build();
                list.add(ossBucket);
            }
        }
        return list;
    }

    /**
     * 上传文件
     * @return
     */
    public String uploadFile() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region1());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "your access key";
        String secretKey = "your secret key";
        String bucket = "your bucket name";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "/home/qiniu/test.png";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
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

        return null;
    }

    /**
     * 字节数组上传
     * @return
     */
    public String uploadByte() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "your access key";
        String secretKey = "your secret key";
        String bucket = "your bucket name";

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        try {
            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
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

        return null;
    }

    /**
     * 数据流上传
     * @return
     */
    public String uploadInputStream() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "your access key";
        String secretKey = "your secret key";
        String bucket = "your bucket name";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        try {
            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(byteInputStream,key,upToken,null, null);
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

        return null;
    }


    /**
     * 获取访问存储桶访问URL
     * @return
     */
    private String getCDNHostUrl(String bucketName) {
        return null;
    }

    /**
     * 删除文件根据文件URL
     * @param bucketName
     * @param key
     * @return
     */
    public boolean deleteFile(String bucketName,String key) {
        log.info("Start to delete file from OSS.{}", key);
        OSSClient ossClient = null;
        try{
            ossClient.deleteObject(bucketName, key);
            return true;
        }catch (Exception e){
           log.error("Delete file error.", e);
        } finally {
            if(ossClient != null) {
                ossClient.shutdown();
            }
        }
        return false;
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     * @param fileName 文件名
     * @return 文件的contentType
     */
    private static String getContentType(String fileName) {
        log.info("getContentType:" + fileName);
        // 文件的后缀名
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if (".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)
                || ".png".equalsIgnoreCase(fileExtension)) {
            return "image/jpeg";
        }
        if (".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if (".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        if (".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if (".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if (".pdf".equalsIgnoreCase(fileExtension)) {
            return "application/pdf";
        }
        // 默认返回类型
        return "null";
    }

    public static void main(String[] args) {
    }
}