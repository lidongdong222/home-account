package com.ldd.home.operate.config.security;

import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.utils.HttpServletResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class HomeLoginFilter extends GenericFilterBean {
    public static final String DEFAULT_LOGIN_PAGE_URL = "/login";

    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
            .getContextHolderStrategy();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Authentication authentication = getAuthentication();
        if(isLoginUrlRequest(request)){
            if (!request.getMethod().equals("POST")) {
                HttpServletResponseUtil.writeJson((HttpServletResponse) servletResponse,Result.fail404("页面不存在！"));
                return;
            }
            if(authentication!=null && authentication.isAuthenticated()){
                HttpServletResponseUtil.writeJson((HttpServletResponse) servletResponse,Result.success("用户已登录！"));
                return;
            }

        }else{
            if(authentication==null || !authentication.isAuthenticated()){
                HttpServletResponseUtil.writeJson((HttpServletResponse) servletResponse,Result.fail401("用户未登录！"));
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    private Authentication getAuthentication() {
        Authentication authentication = this.securityContextHolderStrategy.getContext().getAuthentication();
        return authentication;
    }

    private boolean isLoginUrlRequest(HttpServletRequest request) {
        return matches(request, DEFAULT_LOGIN_PAGE_URL);
    }

    private boolean matches(HttpServletRequest request, String url) {
        if (url == null) {
            return false;
        }
        String uri = request.getRequestURI();
        int pathParamIndex = uri.indexOf(';');
        if (pathParamIndex > 0) {
            // strip everything after the first semi-colon
            uri = uri.substring(0, pathParamIndex);
        }
        if (request.getQueryString() != null) {
            uri += "?" + request.getQueryString();
        }
        if ("".equals(request.getContextPath())) {
            return uri.equals(url);
        }
        return uri.equals(request.getContextPath() + url);
    }
}
