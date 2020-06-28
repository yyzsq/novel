package com.gemframework.modules.perkit;


import com.gemframework.common.constant.GemModules;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(GemModules.Extend.PATH_PRE+"/upload")
public class FileUploadController extends BaseController {

    @Value("${file.uploadFolder}")
    private String uploadFolder;
    //映射地址
    @Value("${file.url}")
    private String url;

    @Value("${wpost}")
    private String wpost;



    @PostMapping(value = "uploadFile")
    public BaseResultData uploadImage(@RequestParam("file") MultipartFile file ,HttpServletRequest request) {

        if (!file.isEmpty()) {
            String ip = request.getServerName();
            //拼装地址前缀  http://localhost:8081/+staticAccessPath+/+图片名称
            String fileUrl=null;
            try {
                String http=request.getScheme();
                String qurl = getUrl(http,ip, wpost, url);
                String fileurl = uploadFile(uploadFolder, file);
                fileUrl=qurl+fileurl;
            }catch (Exception e){
                e.printStackTrace();
            }
            return BaseResultData.SUCCESS(fileUrl);
        } else {
            return BaseResultData.ERROR(ErrorCode.FILE_UPLOAD_ERROR);
        }
    }

    public static String  uploadFile(String path, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 文件上传后的路径
        fileName = UUID.randomUUID() + suffixName;
        path=path+"/"+new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        String uppath=path+"/"+fileName;
        File targetFile = new File(uppath);
        file.transferTo(targetFile);
        return  new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"/"+fileName;
    }

    public static String  getUrl(String http,String ip,String wpost,String staticAccessPath) {
        String url=http+"://"+ip+":"+wpost+"/"+staticAccessPath+staticAccessPath;
        return  url;
    }


 /*   //其他方法就是简单的逻辑判断
    public static File fileToDest(MultipartFile file,String destDir){
        if(file==null){
            return null;
        }else{
            //目标文件路径值
            String destUrl=destDir+"/"+file.getOriginalFilename();
            File dest=new File(destUrl);
            try {
                File dir=new File(destDir);
                //创建文件所在的父目录
                if(!dir.exists()){
                    dir.mkdirs();
                }
                //判断目标文件是否存在，若不存在则创建一个空的文件
                if(!dest.exists()){
                    dest.createNewFile();
                }
                //复制源文件到目标文件下
                file.transferTo(dest);
                return dest;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }
    }*/
}
