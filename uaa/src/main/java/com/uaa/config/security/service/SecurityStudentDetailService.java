package com.uaa.config.security.service;


import com.alibaba.fastjson.JSON;
import com.uaa.entity.Student;
import com.uaa.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


/**
 */
@Service
@Slf4j
public class SecurityStudentDetailService implements UserDetailsService {

    @Autowired
    private StudentService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
//        Student user = userService.getUserByUsername(username);
//        if (user == null) {
//            return null;
//        }
        String[] authorities = new String[0];
        //身份令牌
        String principal = JSON.toJSONString("");
        UserDetails build = User.withUsername(principal).password("$2a$10$gQP6tr/pFIeQpvm39hsTrO.I2a.nP0Ffc20WjW7sfA22/rdeaPVSu").authorities(authorities).build();
        System.out.println(build.toString());
        return build;
    }
}
