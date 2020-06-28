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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class AliyunOssService {


    /**
     * 缺省的最大上传文件大小：5M
     */
    private final int DEFAULT_MAXIMUM_FILE_SIZE = 5;

    @Autowired
    DictionaryService dictionaryService;

    public String getEndpoint(){
        return dictionaryService.getMapValue(DictionaryKeys.OSS_ALIYUN_CFG.KEY,DictionaryKeys.OSS_ALIYUN_CFG.ENDPOINT);
    }
    public String getAccessKeyId(){
        return dictionaryService.getMapValue(DictionaryKeys.OSS_ALIYUN_CFG.KEY,DictionaryKeys.OSS_ALIYUN_CFG.ACCESS_KEY_ID);
    }
    public String getAccessKeySecret(){
        return dictionaryService.getMapValue(DictionaryKeys.OSS_ALIYUN_CFG.KEY,DictionaryKeys.OSS_ALIYUN_CFG.ACCESS_KEY_SECRET);
    }

    public String getCDNHost(){
        return dictionaryService.getMapValue(DictionaryKeys.OSS_ALIYUN_CFG.KEY,DictionaryKeys.OSS_ALIYUN_CFG.CDN_HOST);
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
        OSSClient ossClient = new OSSClient(getEndpoint(), getAccessKeyId(), getAccessKeySecret());
        ObjectListing objectListing = ossClient.listObjects(bucketName);
        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            double size = new BigDecimal((float)objectSummary.getSize()/(1024)).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
            OssObject ossObject = OssObject.builder()
                    .name(objectSummary.getKey().substring(objectSummary.getKey().lastIndexOf("/")+1))
                    .bucketName(objectSummary.getBucketName())
                    .fileSize(size + "KB")
                    .contentType(getContentType(objectSummary.getKey().substring(objectSummary.getKey().lastIndexOf("/")+1)))
                    .keyPath(objectSummary.getKey())
                    .storageType(OssStorageType.valueOf(objectSummary.getStorageClass()))
                    .fileUrl(getCDNHostUrl(objectSummary.getBucketName()).concat(objectSummary.getKey()))
                    .lastModified(objectSummary.getLastModified())
                    .build();
            list.add(ossObject);
        }
        return list;
    }

    /**
     * 所有存储桶
     */
    public List<OssBucket> listBuckets() {
        List<OssBucket> list = Lists.newArrayList();
        OSSClient ossClient = new OSSClient(getEndpoint(), getAccessKeyId(), getAccessKeySecret());
        ListBucketsRequest listBucketsRequest = new ListBucketsRequest();
        listBucketsRequest.setMaxKeys(500);
        for (Bucket bucket : ossClient.listBuckets()) {
            OssBucket ossBucket = OssBucket.builder()
                    .name(bucket.getName())
                    .location(bucket.getLocation())
                    .storageType(OssStorageType.valueOf(bucket.getStorageClass().name()))
                    .createDate(bucket.getCreationDate())
                    .build();
            list.add(ossBucket);
        }
        return list;
    }


    /**
     * 基础上传接口
     * @param fileName
     * @param bucketName
     * @param filePath
     * @param inputStream
     * @return
     */
    public String baseUpload(String fileName, String bucketName, String filePath, InputStream inputStream) {
        log.info("Start to upload file....");
        if(StringUtils.isEmpty(fileName) || inputStream == null) {
            log.error("Filename Or inputStream is lack when upload file.");
            return null;
        }
        if(StringUtils.isEmpty(filePath)) {
            log.warn("File path is lack when upload file but we automatically generated");
            String dateCategory = GemDateUtils.format(new Date(), "yyyyMMdd");
            filePath = "/".concat(dateCategory).concat("/");
        }
        String fileUrl;
        OSSClient ossClient = null;
        try{
            // If the upload file size exceeds the limit
            long maxSizeAllowed = getMaximumFileSizeAllowed();
            if(Long.valueOf(inputStream.available()) > maxSizeAllowed) {
                log.error("Uploaded file is too big.");
                return null;
            }
            // Create OSS instance
            ossClient = new OSSClient(getEndpoint(), getAccessKeyId(), getAccessKeySecret());
            // Create bucket if not exists
            if (!ossClient.doesBucketExist(bucketName)) {
                log.info("Bucket '{}' is not exists and create it now.", bucketName);
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
            // File path format
            if(filePath.startsWith("/")) {
                filePath = filePath.replace("/","");
            }
            if(!filePath.endsWith("/")) {
                filePath = filePath.concat("/");
            }

            StringBuilder buffer = new StringBuilder();
            buffer.append(filePath).append(fileName);
            filePath = buffer.toString();
            log.info("After format the file url is {}", filePath);
            // Upload file and set ACL
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, filePath, inputStream));
            ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);//设置公共读取权限
            if(result != null) {
                log.info("Upload result:{}", result.getETag());
                log.info("Upload file {} successfully.", fileName);
            }

            fileUrl = getHostUrl(bucketName).concat(filePath);
            if(!getCDNHost().isEmpty()){
                fileUrl = getCDNHost().concat("/").concat(filePath);
            }
            log.info("Call path is {}", fileUrl);

        }catch (Exception e){
            log.error("Upload file failed.", e);
            fileUrl = null;
        }finally {
            if(ossClient != null) {
                ossClient.shutdown();
            }
        }
        return fileUrl;
    }


    /**
     * 上传文件
     * @param fileName
     * @param filePath
     * @param file
     * @return
     */
    public String uploadFile(String fileName, String bucketName, String filePath, File file) {
        if(file == null) {
            log.warn("File is lack when upload.");
            return null;
        }
        if(StringUtils.isEmpty(fileName)) {
            log.warn("File name is lack when upload file but we automatically generated");
            String uuidFileName = UUID.randomUUID().toString().replace("-", "");
            String fname = file.getName();
            String suffix = fname.substring(fname.lastIndexOf("."), fname.length());
            fileName = uuidFileName.concat(suffix);
        }
        InputStream inputStream = null;
        String fileUrl = null;
        try{
            inputStream = new FileInputStream(file);
            fileUrl = baseUpload(fileName, bucketName, filePath, inputStream);
        }catch (Exception e){
            log.error("Upload file error.", e);
        }finally {
            IOUtils.safeClose(inputStream);
        }
        return fileUrl;
    }

    /**
     * 获取存储桶访问URL
     * @return
     */
    private String getHostUrl(String bucketName) {
        String hostUrl = "";
        if(this.getEndpoint().startsWith("http://")) {
            hostUrl = "http://".concat(bucketName).concat(".")
                    .concat(this.getEndpoint().replace("http://", "")).concat("/");
        } else if (this.getEndpoint().startsWith("https://")) {
            hostUrl = "https://".concat(bucketName).concat(".")
                    .concat(this.getEndpoint().replace("https://", "")).concat("/");
        }
        return hostUrl;
    }

    /**
     * 获取访问存储桶访问URL
     * @return
     */
    private String getCDNHostUrl(String bucketName) {
        String hostCDNUrl = "";
        if(this.getEndpoint().startsWith("http://")) {
            hostCDNUrl = "http://".concat(bucketName).concat(".")
                    .concat(this.getEndpoint().replace("http://", "")).concat("/");
        } else if (this.getEndpoint().startsWith("https://")) {
            hostCDNUrl = "https://".concat(bucketName).concat(".")
                    .concat(this.getEndpoint().replace("https://", "")).concat("/");
        }

        if(!getCDNHost().equals("")){
            hostCDNUrl = getCDNHost().concat("/");
        }
        return hostCDNUrl;
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
            ossClient = new OSSClient(getEndpoint(), getAccessKeyId(), getAccessKeySecret());
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
        AliyunOssService aliyunOssService = new AliyunOssService();
        List<OssBucket> list = aliyunOssService.listBuckets();
        if (list != null && list.size()>0){
            for(OssBucket ossBucket:list){
                if(ossBucket.getName().equals("gemos")){
                    log.info("===>"+aliyunOssService.getHostUrl(ossBucket.getName()));
                    aliyunOssService.listObjects(ossBucket.getName());
                }
            }
        }
//        log.info("最大上传体积："+aliyunOssService.getMaximumFileSizeAllowed()+"bit");
        File file = new File("C:\\Users\\Administrator\\Desktop\\tr069.txt");
        String fileUrl = aliyunOssService.uploadFile("zhangyshtest.txt","gemos","app",file);
//        log.info("fileUrl="+fileUrl);
    }
}