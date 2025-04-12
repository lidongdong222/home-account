package com.ldd.home.operate.config.security;

import com.ldd.home.operate.service.ISysUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class HomeUserDetailsServiceImpl implements UserDetailsService {

    ISysUserService userService;

    public HomeUserDetailsServiceImpl(ISysUserService userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.getUserByUsername(username);
    }
}
