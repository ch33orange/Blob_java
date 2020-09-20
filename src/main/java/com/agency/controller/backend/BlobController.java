package com.njupt.agency.controller.backend;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.*;
import com.njupt.agency.common.*;
import com.njupt.agency.service.*;
import com.njupt.agency.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;
import org.springframework.web.multipart.*;


@RestController
@RequestMapping(value="blob")
public class BlobController {


	@Autowired
	private IFileService iFileService;

	@Autowired
	private VideoService videoService;

	/**
	 * 文件上传
	 *
	 * @param httpServletRequest
	 * @param file
	 * @param request
	 * @return
	 */
    @RequestMapping("upload.do")
    @ResponseBody
//    @ManageLogin
    public ServerResponse upload(HttpServletRequest httpServletRequest, @RequestParam(value = "upload_file", required = false) MultipartFile file,
								 HttpServletRequest request) {
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息!");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.stringToObj(userJsonStr, User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请先登录!");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
        //是管理员
        //填充我们上传文件的业务逻辑
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file, path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

        Map fileMap = Maps.newHashMap();
        fileMap.put("uri", targetFileName);
        fileMap.put("url", url);

        return ServerResponse.createBySuccess(fileMap);
    }

	/**
	 * 文件用blob传到前端
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="myGetStream/{fileId}")
	public void myTry(HttpServletRequest request,HttpServletResponse response,@PathVariable("fileId") Integer fileId){

		//业务逻辑  这个需要把表建好之后 通过id查到uri
		String realFileName = videoService.getVideoById(fileId);
		//调用方法传到前端
		videoService.sendBlob(request,response,realFileName);

	}


	/**
	 * 文件用blob传到前端 原版 我把它塞到impl里了
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="getStream")
	public void getMp3or4(HttpServletRequest request,HttpServletResponse response){
		//创建文件对象
		File file = new File("F:\\972579187.mp4");		//获取文件名称s
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
		    if(agent.indexOf("FIREFOX") != -1){//火狐浏览器
		    	response.addHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("GB2312"),"ISO-8859-1"));
		    }else{//其他浏览器
		    	response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
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
		}catch(Exception e){
		    System.out.println(e.getMessage());
		} finally{
			//With great power there must come great responsibility.
		    //关闭流
		    try {
		        if(fis != null){
		            fis.close();
		        }
		    } catch (IOException e) {
		        System.out.println(e.getMessage());
		    } finally{
		        try {
		            if(os != null){
		                os.flush();
		            }
		        } catch (IOException e) {
		            System.out.println(e.getMessage());
		        } finally{
		            try {
		                if(os != null){
		                    os.close();
		                }
		            } catch (IOException e) {
		                System.out.println(e.getMessage());
		            }
		        }
		    }
		}
	}



	//end
}
