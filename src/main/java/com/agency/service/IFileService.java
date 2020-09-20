package com.njupt.agency.service;

import org.springframework.web.multipart.*;

/**
 * @author ch33o
 */
public interface IFileService {

    String upload(MultipartFile file, String path);

}
