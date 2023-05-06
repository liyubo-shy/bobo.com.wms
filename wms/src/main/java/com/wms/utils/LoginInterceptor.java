package com.wms.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Author: bo
 * @DATE: 2023/4/27 3:40
 **/
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入第二层拦截");
        if (UserHolder.getUser() == null) {
            log.debug("ThreadLocal中用户为空，请重新登录，{}",UserHolder.getUser());
            response.setStatus(401);
            return false;
        }

        // 获取请求ip
        String ip;
        if (request.getHeader("x-forwarded-for") == null) {
            ip = request.getRemoteAddr();
        }else {
            ip = request.getHeader("x-forwarded-for");
        }
        log.debug("ip:"+ip);

        log.debug("通过第二层拦截器LoginInterceptor");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}
