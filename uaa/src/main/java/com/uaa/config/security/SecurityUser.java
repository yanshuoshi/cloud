package com.uaa.config.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * security用户
 * @author lijianbin
 * @date 2021-07-12 09:42
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityUser implements UserDetails {
    /** 用户id */
    private String userId;
    /** 账号 */
    private String username;
    /** 密码 */
    private String password;
    /** 类型 */
    private String type;
    /** 角色 */
    private List<String> roleList;
    /** 权限 */
    private List<String> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (CollectionUtils.isNotEmpty(authorities)) {
            return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 登录用户
     * @param userId    用户id
     * @param username  账号
     * @param roleList  角色
     * @param roleList  权限
     */
    public static SecurityUser loginUser(String userId, String username, String password, List<String> roleList, List<String> authorities) {
        return SecurityUser.builder().userId(userId).username(username).password(password).roleList(roleList).authorities(authorities).build();
    }

    /**
     * 账户是否过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否被锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账户是否可用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
