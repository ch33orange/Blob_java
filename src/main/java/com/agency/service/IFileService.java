package com.agency.service;

import org.springframework.web.multipart.*;

import javax.servlet.http.*;

/**
 * @author ch33o
 */
public interface IFileService {

    String upload(MultipartFile file, String path);

    public String getVideoById(Integer fileId);

    public boolean sendBlob(HttpServletRequest request, HttpServletResponse response, String realFileName);

}
