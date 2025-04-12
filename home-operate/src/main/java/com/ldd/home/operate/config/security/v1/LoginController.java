package com.ldd.home.operate.config.security.v1;

import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.utils.JWTUtil;
import com.ldd.home.operate.config.redis.RedisKeyConstant;
import com.ldd.home.operate.entity.SysUser;
import com.ldd.home.operate.entity.req.PasswordReq;
import com.ldd.home.operate.service.ISysUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Slf4j
public class LoginController {

    @Autowired
    ISysUserService userService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Result login(@RequestBody SysUser anonymousUser, HttpServletResponse resp){
        log.info("login入参：{}",anonymousUser);
        SysUser user = (SysUser) userService.getUserByUsername(anonymousUser.getUsercode());
        Result result =  Result.success("登录成功");
        if(user!=null
                && passwordEncoder.matches(anonymousUser.getPassword()+user.getSalt(),user.getPassword())){
            Map claims = new HashMap();
            claims.put("userId",user.getUserId());
            SecretKey secretKey = JWTUtil.HS512secretKey();
            String token = JWTUtil.token(claims,secretKey);
            user.setSecretKey(secretKey);
            user.setToken(token);
            resp.addHeader("Authorization",token);
            redisTemplate.opsForValue().set(
                    RedisKeyConstant.SYS_SESSION+":"+token,
                    user,
                    Duration.ofMinutes(30));
        }else{
            result =  Result.fail401("账号或密码错误！");
        }
        log.info("login出参：{}",result);
        return result;
    }

    @PostMapping("/logout")
    public Result logout( HttpServletRequest req){
        log.info("logout入参。");
        SysUser user = HomeContextHolderStrategy.getUser();
        redisTemplate.delete(RedisKeyConstant.SYS_SESSION+":"+user.getToken());
        Result result =  Result.success("登出成功");
        log.info("logout出参：{}",result);
        return result;
    }

    @PostMapping("/getLoginUser")
    public Result getLoginUser(){
        log.info("getLoginUser入参。");
        SysUser sessionUser = HomeContextHolderStrategy.getUser();
        SysUser user = SysUser.builder()
                .usercode(sessionUser.getUsercode())
                .username(sessionUser.getUsername())
                .phone(sessionUser.getPhone())
                .viewRoleNames(sessionUser.getViewRoleNames())
                .signature(sessionUser.getSignature())
                .build();
        Result result =  Result.successData("查询成功",user);
        log.info("getLoginUser出参：{}",result);
        return result;
    }

    @PostMapping("/updPassword")
    public Result updPassword(@RequestBody PasswordReq req) throws BusinessException {
        log.info("updPassword into params:",req);
        if(!Objects.equals(req.getNewPass(),req.getNewPassAgain())){
            throw new BusinessException("两次输入的密码不一致");
        }
        Result result = userService.updPassword(req);
        log.info("updPassword outer params：{}",result);
        return result;
    }

}
