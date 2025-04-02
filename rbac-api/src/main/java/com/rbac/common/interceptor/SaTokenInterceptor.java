package com.rbac.common.interceptor;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.rbac.common.config.IgnoreUrlsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Sa-token拦截器
 */
@Slf4j
@Configuration
public class SaTokenInterceptor implements WebMvcConfigurer {

    @Resource
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaRouteInterceptor((req, resp, handler) -> {
            List<String> list = ignoreUrlsConfig.getUrls();
            SaRouter.match(Collections.singletonList("/**"), list, StpUtil::checkLogin);
            // 路径认证
        })).addPathPatterns("/**");
    }
}
