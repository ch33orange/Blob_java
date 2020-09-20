package com.njupt.agency.util;

import lombok.extern.slf4j.*;
import org.apache.commons.net.ftp.*;

import java.io.*;
import java.util.*;

/**
 * @author ch33o
 */
@Slf4j
public class FTPUtil {

//    private static final log log = logFactory.getlog(FTPUtil.class);

    private static String ftpIp = PropertiesUtil.getProperty("ftp.server.ip");
    private static String ftpUser = PropertiesUtil.getProperty("ftp.user");
    private static String ftpPass = PropertiesUtil.getProperty("ftp.pass");
    private static String ftpPrefix = PropertiesUtil.getProperty("ftp.server.http.prefix");
    private String ip;
    private int port;
    private String user;
    private String pwd;
    private FTPClient ftpClient;
    public FTPUtil(String ip, int port, String user, String pwd) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }

    public static boolean uploadFile(List<File> fileList) throws IOException {
        FTPUtil ftpUtil = new FTPUtil(ftpIp, 21, ftpUser, ftpPass);
        log.info("开始连接FTP服务器");
        boolean result = ftpUtil.uploadFile("video", fileList);
        log.info("开始连接FTP服务器,结束上传,上传结果:{}", result);
        return result;
    }

    public static String getFtpIp() {
        return ftpIp;
    }

    public static void setFtpIp(String ftpIp) {
        FTPUtil.ftpIp = ftpIp;
    }

    public static String getFtpUser() {
        return ftpUser;
    }

    public static void setFtpUser(String ftpUser) {
        FTPUtil.ftpUser = ftpUser;
    }

    public static String getFtpPass() {
        return ftpPass;
    }

    public static void setFtpPass(String ftpPass) {
        FTPUtil.ftpPass = ftpPass;
    }

    public static String getFtpPrefix() {
        return ftpPrefix;
    }

    public static void setFtpPrefix(String ftpPrefix) {
        FTPUtil.ftpPrefix = ftpPrefix;
    }

    public boolean uploadFile(String remotePath, List<File> fileList) throws IOException {
        boolean uploaded = false;
        FileInputStream fileInputStream = null;
        //连接FTP服务器
        if (connectServer(this.getIp(), this.getPort(), this.getUser(), this.getPwd())) {
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024 * 1024 * 100); //100M左右
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                for (File fileItem : fileList) {
                    fileInputStream = new FileInputStream(fileItem);
                    ftpClient.storeFile(fileItem.getName(), fileInputStream);
                }
                uploaded = true;
            } catch (IOException e) {
                log.error("上传文件异常!", e);
                uploaded = false;
            } finally {
                fileInputStream.close();
                ftpClient.disconnect();
            }
        }
        return uploaded;
    }

    private boolean connectServer(String ip, int port, String user, String pwd) {

        boolean isSuccess = false;
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(ip);
            isSuccess = ftpClient.login(user, pwd);
            if (!isSuccess) {
                log.error("连接FTP服务器异常!");
                throw new IOException();
            }
        } catch (IOException e) {
            log.error("连接FTP服务器异常!", e);
            e.printStackTrace();
        }
        return isSuccess;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }


}
