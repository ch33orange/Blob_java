package com.njupt.agency.util;

import lombok.extern.slf4j.*;
import org.apache.commons.lang3.*;

import javax.servlet.http.*;

/**
 * @author ch33o
 */
@Slf4j
public class CookieUtil {

    private final static String COOKIE_DOMAIN = "localhost"; //.bluer.net.cn

    private final static String COOKIE_NAME = "BluerMall_login_token";


    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                log.info("read cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    log.info("return cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    //X:domain="bluer.net.cn        不让加 .  怎么办
    //a:A.bluer.net.cn              cookie:domin=A.happymmall.com:path="/"
    //b:B.bluer.net.cn              cookie:domin=B.happymmall.com:path="/"
    //c:A.bluer.net.cn/test/cc      cookie:domin=A.happymmall.com:path="/test/cc"
    //d:A.bluer.net.cn/test/dd      cookie:domin=A.happymmall.com:path="/test/dd"
    //e:A.bluer.net.cn/test         cookie:domin=A.happymmall.com:path="/test"

    public static void writeLoginToken(HttpServletResponse httpServletResponse, String token) {
        Cookie ck = new Cookie(COOKIE_NAME, token);
        //不知道为什么设置domain就添加不了
        ck.setDomain(COOKIE_DOMAIN);
        //代表设置在根目录
        ck.setPath("/");
        ck.setHttpOnly(true);
        //单位是秒 -1代表永久
        //如果maxage不设置,cookie不会写入硬盘,而是写在内存,只在当前页面有效
        ck.setMaxAge(60 * 30);
        log.info("write cookie:{},cookieValue:{}", ck.getName(), ck.getValue());
        httpServletResponse.addCookie(ck);
        return;
    }


    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    //设置成0,代表删除此cookie
                    ck.setMaxAge(0);
                    log.info("del cookie:{},cookieValue:{}", ck.getName(), ck.getValue());
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }


}
