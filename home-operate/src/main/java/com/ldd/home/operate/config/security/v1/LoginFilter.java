package com.ldd.home.operate.config.security.v1;

import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.utils.HttpServletResponseUtil;
import com.ldd.home.operate.config.redis.RedisKeyConstant;
import com.ldd.home.operate.config.security.SecurityConfig;
import com.ldd.home.operate.entity.SysUser;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Objects;

@WebFilter
@Component
public class LoginFilter implements Filter {

    @Autowired
    HomeContextHolderStrategy homeContextHolderStrategy;
    @Autowired
    RedisTemplate redisTemplate;

    final String LOGIN_URL = "/login";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try{
            HttpServletRequest req = (HttpServletRequest) servletRequest;
            HttpServletResponse resp = (HttpServletResponse) servletResponse;
            String token = req.getHeader("Authorization");
            SysUser user = (SysUser) redisTemplate.opsForValue().get(RedisKeyConstant.SYS_SESSION+":"+token);;
            if(user!=null){
                redisTemplate.expire(RedisKeyConstant.SYS_SESSION+":"+token, Duration.ofMinutes(30));
            }
            //存放用户信息
            if(user!=null && !homeContextHolderStrategy.hasUser()){
                homeContextHolderStrategy.setUser(user);
            }
            if(!matchPermitUrl(req.getRequestURI())
                    && Objects.equals(user,null)){
                HttpServletResponseUtil.writeJson(resp,Result.fail("401","用户位未登录"));
            }else if ( matchLoginUrl(req.getMethod(),req.getRequestURI())
                    &&  user!=null){
                HttpServletResponseUtil.writeJson(resp,Result.fail("200","用户已登录"));
            }else{
                filterChain.doFilter(servletRequest,servletResponse);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            homeContextHolderStrategy.clearContext();
        }

    }

    private boolean matchLoginUrl(String method,String requestUri) {
        return Objects.equals(method,"POST")
                && Objects.equals(requestUri,LOGIN_URL);
    }

    private boolean matchPermitUrl(String contextPath) {
        for (String s : SecurityConfig.PERMIT_ALL_URL) {
            if(Objects.equals(contextPath,s)){
                return true;
            }
        }
        return false;
    }

}
