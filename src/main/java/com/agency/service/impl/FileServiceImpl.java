package com.njupt.agency.service.impl;


import com.google.common.collect.*;
import com.njupt.agency.service.*;
import com.njupt.agency.util.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;

import java.io.*;
import java.util.*;


/**
 * @author ch33o
 */
@Service("iFileService")
@Slf4j
public class FileServiceImpl  implements IFileService {

//    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);


//    @Autowired todo
//    private VideoMapper videoMapper;

    @Override
    public String upload(MultipartFile file, String path){
        String fileName = file.getOriginalFilename();
        //扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        log.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);
        //避免重名所以hash重命名

        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path,uploadFileName);

        try {
            file.transferTo(targetFile);
            //文件上传成功

            // 将targetFile上传到ftp服务器上
            boolean flag = FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //已经上传到ftp服务器上

            // 上传完之后,删除upload下面的文件
            targetFile.delete();
            if(flag){
                //todo 成功后 文件名存到数据库 就有了id
//            videoMapper.insert(uploadFileName);
            }
        } catch (IOException e) {
            log.error("上传文件异常",e);
            return null;
        }
        return targetFile.getName();
    }

}
