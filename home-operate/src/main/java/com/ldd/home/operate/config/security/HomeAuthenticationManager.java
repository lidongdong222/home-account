package com.ldd.home.operate.config.security;

import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.utils.ExceptionUtil;
import com.ldd.home.operate.common.utils.StrUtil;
import com.ldd.home.operate.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class HomeAuthenticationManager implements AuthenticationManager {

    HomeUserDetailsServiceImpl userDetailsService;

    PasswordEncoder passwordEncoder;

    public HomeAuthenticationManager(PasswordEncoder passwordEncoder,HomeUserDetailsServiceImpl userDetailsService){
        this.passwordEncoder=passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SysUser user = (SysUser) userDetailsService.loadUserByUsername(StrUtil.toStr(authentication.getPrincipal()));
        try {
            if(user!=null && passwordEncoder.matches(StrUtil.toStr(authentication.getCredentials())+user.getSalt(),user.getPassword())){
                return new UsernamePasswordAuthenticationToken(
                        user.getUsercode(),authentication.getCredentials(),user.getAuthorities());
            }
        } catch (Exception e) {
            log.error(e instanceof BusinessException ?e.getMessage(): ExceptionUtil.getExceptionCause(e));
            throw new AuthenticationServiceException(e.getMessage());
        }
        return authentication ;
    }

    public static void main(String[] args) throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        System.out.println(IdUtil.getUUid());
        //3dccc5f9ecad4a78bb67681120d1e2de
//        String AESKey = "iMzXEZGlAACxtMNDKc2/JA==";
//        System.out.println(new BCryptPasswordEncoder().encode("home1234!"+AESKey));
//        System.out.println(AESUtil.generateAESKey());
//        System.out.println(new BCryptPasswordEncoder().upgradeEncoding("$2a$10$mLeyBzJ8ssxorho2Srdk3.ztmIp76Rf097RK.aB.7DZkLnW/s/iIS"));
//        System.out.println(AESUtil.encryptAES("$2a$10$WJf4Ck5g7cH7AOBVpk9iJ.k9EFVb6b2ir6T4T.bcXLH/MZ2V2B8kW",AESKey));
        System.out.println(passwordEncoder.encode(StrUtil.toStr("home2024!")+"iMzXEZGlAACxtMNDKc2/JA=="));
        System.out.println(passwordEncoder.matches(StrUtil.toStr("home2024!")+"iMzXEZGlAACxtMNDKc2/JA==",
                "$2a$10$3dW5mLcZby8TyBerbU.GA.Pep3/nAbREv4wG7RiZ5j2Z/Edb27CKS"));


    }
}
