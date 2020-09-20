package com.agency.service.impl;


import com.google.common.collect.*;
import com.agency.service.*;
import com.agency.util.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;

import javax.servlet.http.*;
import java.io.*;
import java.net.*;
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

    /**
     * 上传文件
     * @param file
     * @param path
     * @return
     */
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


    /**
     * 通过视频的id 获取uri即文件名
     * @param fileId
     * @return
     */
    @Override
    public String getVideoById(Integer fileId){
//        String realFileName = videoMapper.selectById(fileId);
//        return realFileName;
        return null;
    }

    /**
     * 传送blob文件给前端 防止url被发现
     * @param request
     * @param response
     * @param realFileName
     * @return
     */
    @Override
    public boolean sendBlob(HttpServletRequest request, HttpServletResponse response, String realFileName) {
        boolean flag = false;

        //创建文件对象
//		File file = new File("F:\\972579187.mp4");		//获取文件名称s

        File file = new File(File.separator + "ftpfile" + File.separator + realFileName);        //获取文件名称s
        String fileName = file.getName();
        //导出文件
        String agent = request.getHeader("user-agent");
        BufferedInputStream fis = null;
        OutputStream os = null;
        try {
            fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer;
            buffer = new byte[fis.available()];
            fis.read(buffer);
            response.reset();
            //由于火狐和其他浏览器显示名称的方式不相同，需要进行不同的编码处理
            if (agent.indexOf("FIREFOX") != -1) {//火狐浏览器
                response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GB2312"), "ISO-8859-1"));
            } else {//其他浏览器
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            }
            //设置response编码
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Length", "" + file.length());
            //设置输出文件类型
            response.setContentType("video/mp4");
            //获取response输出流
            os = response.getOutputStream();
            // 输出文件
            os.write(buffer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            //With great power there must come great responsibility.
            //关闭流
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    if (os != null) {
                        os.flush();
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } finally {
                    try {
                        if (os != null) {
                            os.close();
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            flag = true;
        }
        return flag;
    }


}
