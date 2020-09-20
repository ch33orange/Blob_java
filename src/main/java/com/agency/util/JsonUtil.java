package com.agency.util;

import lombok.extern.slf4j.*;
import org.apache.commons.lang3.*;
import org.codehaus.jackson.map.DeserializationConfig.*;
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.map.annotate.JsonSerialize.*;
import org.codehaus.jackson.type.*;

import java.io.*;
import java.text.*;

/**
 * @author ch33o
 */
@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //对象的所有字段全部列入
        objectMapper.setSerializationInclusion(Inclusion.ALWAYS);
        //取消默认转化timestamps形式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        //忽略空Bean转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        //所有的日期格式都统一为以下的样式 即 yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));
        //忽略 在json字符串中存在,但是在java对象不存在对应属性的情况,防止错误
        objectMapper.configure(Feature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
    }

    public static <T> String objToString(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            log.warn("Parse object to String error", e);
            return null;
        }

    }

    public static <T> String objToStringPretty(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj :
                    objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (IOException e) {
            log.warn("Pretty Parse Object to String error", e);
            return null;
        }

    }

    public static <T> T stringToObj(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (Exception e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }

    public static <T> T stringToBoj(String str, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? str : objectMapper.readValue(str,
                    typeReference));
        } catch (Exception e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }

    public static <T> T stringToBoj(String str, Class<?> collectionClass,Class<?>... elementClasses) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);
        try {
            return objectMapper.readValue(str,javaType);
        } catch (Exception e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }



//    public static void main(String[] args) {
//        User user = new User();
//        user.setId(1);
//        user.setEmail("bluerMall.com");
//        String userJsonPretty = JsonUtil.objToStringPretty(user);
//        log.info("user1Json:{}", userJsonPretty);
////        User u2 = new User();
////        u2.setId(2);
////        u2.setEmail("22222bluerMall.com");
////
////        String user1Json = JsonUtil.objToString(u1);
////        String user1JsonPretty = JsonUtil.objToStringPretty(u1);
////        log.info("user1Json:{}", user1Json);
////        log.info("user1JsonPretty:{}", user1JsonPretty);
////
////        User usr = JsonUtil.stringToObj(user1Json, User.class);
////        List<User> userList = Lists.newArrayList();
////        userList.add(u1);
////        userList.add(u2);
////        String userListStr = JsonUtil.objToStringPretty(userList);
////
////        log.info("==========================");
////        log.info(userListStr);
////
////        List<User> userListObj = JsonUtil.stringToBoj(userListStr,new TypeReference<List<User>>(){} );
////        List<User> userListObj2 = JsonUtil.stringToBoj(userListStr,List.class,User.class);
//
//        System.out.println("end");
//    }

}
