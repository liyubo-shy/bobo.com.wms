package com.wms.config;

import com.wms.utils.LoginInterceptor;
import com.wms.utils.RefreshTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @Author: bo
 * @DATE: 2023/4/27 3:57
 **/
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .excludePathPatterns(
                        "/user/analysisMale",
                        "/user/analysisFemale",
                        "/user/userAgeAnalysis",
                        "/record/recordInAnalysis",
                        "/record/recordOutAnalysis",
                        "/storage/analysis",
                        "/user/login",
                        "/user/clear"
                );

        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate))
                .order(0);

    }
}

