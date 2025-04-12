package com.ldd.home.operate.config.security;

import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.utils.HttpServletResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class HomeAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (!request.getMethod().equals("POST")) {
            HttpServletResponseUtil.writeJson(response,Result.fail404("页面不存在！"));
            return;
        }
        HttpServletResponseUtil.writeJson(response,Result.fail("用户名或密码不正确！"));
    }

}
