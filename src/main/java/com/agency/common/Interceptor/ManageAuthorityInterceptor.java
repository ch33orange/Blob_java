package com.njupt.agency.common.Interceptor;


import com.BluerMall.common.*;
import com.BluerMall.pojo.*;
import com.BluerMall.service.*;
import com.BluerMall.util.*;
import com.google.common.collect.*;
import com.njupt.agency.util.*;
import lombok.extern.slf4j.*;
import org.apache.commons.lang3.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.method.*;
import org.springframework.web.servlet.*;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;

/**
 * @author blue
 * @date 2019-9-26
 */
@Component
@Slf4j
public class ManageAuthorityInterceptor implements HandlerInterceptor {


    @Autowired
    private IUserService iUserService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("preHandle");
        //请求中Controller的拿到方法名
        boolean flag = true;
        //如果我执行的handler方法属于 method  类型转换一下
        if (handler instanceof HandlerMethod) {
            //取出method的注解看看有没有加上注解 在needlogin上
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ManageLogin manageLogin = handlerMethod.getMethodAnnotation(ManageLogin.class);
            //注解有 就要做用户登录判断 从session中取出用户信息 然后再登录
            if (manageLogin != null) {
                //解析handlerMethod
                String methodName = handlerMethod.getMethod().getName();
                String className = handlerMethod.getBean().getClass().getSimpleName();

                //解析参数,具体的参数key已经value是什么,打印日志
                StringBuffer requestParamBuffer = new StringBuffer();
                Map paramMap = request.getParameterMap();
                Iterator iterator = paramMap.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    String mapKey = (String) entry.getKey();
                    String mapValue = StringUtils.EMPTY;

                    //request这个参数的map,里面的map返回的是一个String[]
                    Object obj = entry.getValue();
                    if (obj instanceof String[]) {
                        String[] strs = (String[]) obj;
                        mapValue = Arrays.toString(strs);
                    }
                    requestParamBuffer.append(mapKey).append("=").append(mapKey);
                }
////跳过登录请求  其实不需要的
//                if (StringUtils.equals(className, "UserManageController") && StringUtils.equals(methodName, "login")) {
//                    log.info("拦截器拦截到请求,className:{},methodName:{}", className, methodName);
//                    //如果拦截到登录请求,不打印参数,参数有瓯密码,会泄露
//                    return true;
//                }
                log.info("拦截器拦截到请求,className:{},methodName:{},param:{}", className, methodName,requestParamBuffer.toString()    );
                //组装返回值
                User user = null;

                String loginToken = CookieUtil.readLoginToken(request);
                if (StringUtils.isNotEmpty(loginToken)) {
                    String userJsonStr = RedisShardedPoolUtil.get(loginToken);
                    user = JsonUtil.stringToObj(userJsonStr, User.class);
                }
                if (user == null || !iUserService.checkAdminRole(user).isSuccess()) {
                    //返回false,不会调用controller的方法
                    response.reset();//bluer note: 这里要reset,否则会报异常 getWriter() has already ben called for this response
                    response.setCharacterEncoding("UTF-8");//bluer note:这里要这是编码否则乱码
                    response.setContentType("application/json;charset=UTF-8");//bluer note:这里要返回值的类型,因为全部是json接口

                    PrintWriter out = response.getWriter();
                    //上传由于富文本的控件要求,要特殊处理返回值,这里面区分是否登录已经是否要瓯权限
                    if (user == null) {
                        if (StringUtils.equals(className, "ProductManageController") && StringUtils.equals(methodName,
                                "richTextImgUpload")) {
                            Map resultMap = Maps.newHashMap();
                            resultMap.put("success", false);
                            resultMap.put("msg", "请登录管理员!");
                            out.print(JsonUtil.objToString(resultMap));
                        } else {
                            out.print(JsonUtil.objToString(ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                                    ResponseCode.NEED_LOGIN.getDesc())));
                        }
                    } else {
                        if (StringUtils.equals(className, "ProductManageController") && StringUtils.equals(methodName,
                                "richTextImgUpload")) {
                            Map resultMap = Maps.newHashMap();
                            resultMap.put("success", false);
                            resultMap.put("msg", "无权限操作!");
                            out.print(JsonUtil.objToString(resultMap));
                        } else {
                            out.print(JsonUtil.objToString(ServerResponse.createByErrorMessage("用户无权限操作,请登录管理员!")));
                        }
                    }
                    out.flush();
                    out.close();//bluer note: 要及时关闭
                    flag = false;
                }
            }
        }
        return flag;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion");
    }
}
