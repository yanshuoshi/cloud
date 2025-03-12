package com.uaa.config.security.config;

import com.uaa.config.security.service.SecurityStudentDetailService;
import com.uaa.config.security.service.SecurityUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 自定义身份验证提供者，用于根据用户类型选择正确的UserDetailsService进行身份验证。
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SecurityUserDetailService securityUserDetailService;
    @Autowired
    private SecurityStudentDetailService securityStudentDetailService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        UserDetailsService userDetailsService;
        if (authentication.getDetails() instanceof WebAuthenticationDetails) {
            // 授权码模式
            userDetailsService = securityUserDetailService;
        } else {
            // 密码模式
            Map<String, Object> details = (Map) authentication.getDetails(); // 获取用户类型
            if ("admin".equals(details.get("userType"))) {
                userDetailsService = securityUserDetailService;
            } else if ("stu".equals(details.get("userType"))) {
                userDetailsService = securityStudentDetailService;
            } else {
                throw new BadCredentialsException("Invalid user type");
            }
        }
        // 使用正确的UserDetailsService进行验证
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(
                username,
                token.getCredentials(),
                userDetails.getAuthorities()
        );
    }

    /**
     * 判断此AuthenticationProvider是否支持指定类型的Authentication。
     *
     * @param authentication 提供的认证信息类型
     * @return 是否支持此类型的Authentication
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
