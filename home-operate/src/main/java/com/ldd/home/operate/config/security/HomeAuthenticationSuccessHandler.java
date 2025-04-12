package com.ldd.home.operate.config.security;

import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.utils.HttpServletResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class HomeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        /*
        当认证管理器返回未完全认证的用户时，认证过滤器仍会当做成功登录处理，
        该系统不需要游客登录，因此在未完全认证时，禁止跳转成功页面
        重写成功处理逻辑
         */
        if(!authentication.isAuthenticated()){
            HttpServletResponseUtil.writeJson(response, Result.fail("用户名或密码错误！"));
        }
    }
}
