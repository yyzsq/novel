
package com.gemframework.modules.perkit.oss;

import com.gemframework.common.annotation.Log;
import com.gemframework.common.constant.DictionaryKeys;
import com.gemframework.common.constant.GemModules;
import com.gemframework.modules.perkit.BaseController;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.entity.bo.OssBucket;
import com.gemframework.model.entity.bo.OssObject;
import com.gemframework.model.entity.po.Dictionary;
import com.gemframework.model.enums.ErrorCode;
import com.gemframework.model.enums.OperateType;
import com.gemframework.service.oss.QiniuyunOssService;
import com.gemframework.service.DictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


/**
 * @Title: QiniuyunOssController
 * @Package: com.gemframework.modules.perkit.oss
 * @Date: 2020-04-28 23:16:54
 * @Version: v1.0
 * @Description: 七牛云OSS存储对象
 * @Author: nine QQ 769990999
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Slf4j
@RestController
@RequestMapping(GemModules.PreKit.PATH_OSS+"/qiniuyun")
public class QiniuyunOssController extends BaseController {

    private static final String moduleName = "七牛云存储对象";

    @Autowired
    QiniuyunOssService qiniuyunOssService;

    @Autowired
    DictionaryService dictionaryService;

    /**
     * 获取OSS存储桶列表
     * @return
     */
    @GetMapping("/listBuckets")
//    @RequiresPermissions("qiniuyunOss:listBuckets")
    public BaseResultData listBuckets() {
        List<OssBucket> list = qiniuyunOssService.listBuckets();
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 获取阿里云OSS文件列表
     * @return
     */
    @GetMapping("/listObjects")
//    @RequiresPermissions("qiniuyunOss:listObjects")
    public BaseResultData listObjects(String bucketName) {
        List<OssObject> list = qiniuyunOssService.listObjects(bucketName);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 上传文件
     * @return
     */
    @Log(type = OperateType.ALTER,value = "上传"+moduleName)
    @PostMapping("/upload")
    @RequiresPermissions("qiniuyunOss:upload")
    public BaseResultData upload(String bucketName,MultipartFile file) throws IOException {
        String path = dictionaryService.getMapValue(DictionaryKeys.OSS_ALIYUN_CFG.KEY, DictionaryKeys.OSS_ALIYUN_CFG.REMOTE_FILE_PATH);
        String fileUrl = qiniuyunOssService.uploadFile();
        return BaseResultData.SUCCESS(fileUrl);
    }

    /**
     * 删除文件
     * @return
     */
    @Log(type = OperateType.ALTER,value = "删除"+moduleName)
    @PostMapping("/delete")
//    @RequiresPermissions("qiniuyunOss:delete")
    public BaseResultData delete(String bucketName,String key) {
        if(!qiniuyunOssService.deleteFile(bucketName,key)){
            return BaseResultData.ERROR(ErrorCode.ALIYUN_OSS_DELETE_FAIL);
        }
        return BaseResultData.SUCCESS();
    }

    /**
     * 配置
     * @return
     */
    @GetMapping("/cfg")
//    @RequiresPermissions("qiniuyunOss:cfg")
    public BaseResultData cfg() {
        Dictionary dictionary = dictionaryService.getByKey(DictionaryKeys.OSS_QINIUYUN_CFG.KEY);
        return BaseResultData.SUCCESS(dictionary);
    }

    /**
     * 配置
     * @return
     */
    @PostMapping("/set")
//    @RequiresPermissions("qiniuyunOss:set")
    public BaseResultData set(Dictionary dictionary) {
        dictionaryService.updateById(dictionary);
        return BaseResultData.SUCCESS();
    }


}