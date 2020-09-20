package com.njupt.agency.util;

import lombok.extern.slf4j.*;
import org.apache.commons.lang3.*;

import java.io.*;
import java.util.*;

/**
 * Created by ch33o
 * @author ch33o
 */
@Slf4j
public class PropertiesUtil {

//    private static log log = logFactory.getlog(PropertiesUtil.class);

    private static Properties props;

    //静态块 优于普通代码块 优于构造代码块
    static {
        String fileName = "application.properties";
        props = new Properties();
        try {
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
        } catch (IOException e) {
            log.error("配置文件读取异常",e);
        }
    }

    public static String getProperty(String key){
        String value = props.getProperty(key.trim());
        if(StringUtils.isBlank(value)){
            return null;
        }
        return value.trim();
    }

    public static String getProperty(String key,String defaultValue){

        String value = props.getProperty(key.trim());
        if(StringUtils.isBlank(value)){
            value = defaultValue;
        }
        return value.trim();
    }

    public static Boolean getBooleanProperty(String key){
        String  value = props.getProperty(key.trim());
        if(StringUtils.isBlank(value)){
            return null;
        }
        return Boolean.parseBoolean(value.trim());
    }

    public static Boolean getBooleanProperty(String key,Boolean defaultValue){

        String value = props.getProperty(key.trim());
        if(StringUtils.isBlank(value)){
            return defaultValue;
        }
        return  Boolean.parseBoolean(value.trim());
    }

    public static Integer getIntnProperty(String key){
        String  value = props.getProperty(key.trim());
        if(StringUtils.isBlank(value)){
            return null;
        }
        return Integer.parseInt(value.trim());
    }

    public static Integer getIntProperty(String key,int defaultValue){

        String value = props.getProperty(key.trim());
        if(StringUtils.isBlank(value)){
            return defaultValue;
        }
        return  Integer.parseInt(value.trim());
    }
}
