package com.ldd.home.operate.config.security;

import com.ldd.home.operate.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * spring-security 认证管理
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String[] PERMIT_ALL_URL = new String[]{
            "/login","/logout"
    };

    @Autowired
    ISysUserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * csrf暂且关闭，等后续研究启用
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        HomeUserDetailsServiceImpl homeUserDetailsService = new HomeUserDetailsServiceImpl(userService);
        HomeAuthenticationManager homeAuthenticationManager = new HomeAuthenticationManager(passwordEncoder, homeUserDetailsService);
                HomeUsernamePasswordAuthenticationFiter homeUsernamePasswordAuthenticationFiter =
                new HomeUsernamePasswordAuthenticationFiter(homeAuthenticationManager);
//        homeUsernamePasswordAuthenticationFiter.setAuthenticationSuccessHandler(new HomeAuthenticationSuccessHandler());
        homeUsernamePasswordAuthenticationFiter.setAuthenticationFailureHandler(new HomeAuthenticationFailureHandler());

//        http
//                .csrf(c->c.disable())
//                .httpBasic(Customizer.withDefaults())
//                .formLogin(c->c.usernameParameter("usercode").passwordParameter("password"))
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers(PERMIT_ALL_URL).permitAll()
//                        .anyRequest().authenticated()
//                )
//                .authenticationManager(homeAuthenticationManager)
//                .userDetailsService(homeUserDetailsService)



//                .addFilterAt(new HomeLoginFilter(), DefaultLoginPageGeneratingFilter.class)
//                .addFilterAt(new CheckAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class)
//                .addFilterAt(homeUsernamePasswordAuthenticationFiter,
//                        UsernamePasswordAuthenticationFilter.class)
//                ;
        http.csrf(c->c.disable())
                .formLogin(c->c.disable())
                .httpBasic(c->c.disable())
                .logout(c->c.logoutUrl("/NoUseSecurityLogout"))
                .authorizeHttpRequests(authorize->authorize.anyRequest().permitAll());
        return http.build();
    }


}
