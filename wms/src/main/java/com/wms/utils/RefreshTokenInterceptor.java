package com.wms.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.wms.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: bo
 * @DATE: 2023/4/27 3:40
 **/
@Slf4j
public class RefreshTokenInterceptor implements HandlerInterceptor {
    private StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("authorization");
        System.out.println("进入第一层拦截");
        System.out.println("第一层token:"+token);
        if (StrUtil.isBlank(token)) {
            System.out.println("token为空,请重新登录：{}");
            return true;
        }
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(RedisConstants.LOGIN_USER_KEY + token);
        if (userMap.isEmpty()) {
            System.out.println("redis中用户为空，请重新登录,{}");
            return true;
        }
        User userDTO = BeanUtil.fillBeanWithMap(userMap, new User(), false);
        System.out.println("userDTO"+userDTO.getName());
        UserHolder.saveUser(userDTO);
        stringRedisTemplate.expire(RedisConstants.LOGIN_USER_KEY + token, RedisConstants.LOGIN_USER_TTL, TimeUnit.MINUTES);
        log.debug("通过第一层拦截器RefreshTokenInterceptor");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}
