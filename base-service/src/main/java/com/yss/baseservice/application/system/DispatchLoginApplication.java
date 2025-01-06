package com.yss.baseservice.application.system;

import com.yss.baseservice.domain.system.LoginMoreDomainService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


/**
 * 调度端登录
 * @author wangqi
 * @date 2022/3/24
 */
@Component
public class DispatchLoginApplication implements UserDetailsService {

    private final LoginMoreDomainService loginMoreDomainService;

    public DispatchLoginApplication(LoginMoreDomainService loginMoreDomainService) {
        this.loginMoreDomainService = loginMoreDomainService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return loginMoreDomainService.getSecurityUser(s);
    }
}
