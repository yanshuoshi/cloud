package com.yss.baseservice.domain.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yss.baseservice.application.system.dto.SysUserDto;
import com.yss.baseservice.domain.system.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统用户mapper
 * @author wangqi
 * @date 2021/11/1
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 用户列表
     * @param username 用户名
     * @param mobile   联系方式
     * @param realName  真实姓名
     * @param sex   性别
     * @param rowBounds 分页
     * @param  organizationId 组织id
     * @return 用户列表
     */
    List<SysUserDto> selectSysUserList(@Param("username") String username, @Param("realName") String realName, @Param("mobile") String mobile, @Param("sex") String sex, @Param("organizationId") Integer organizationId, RowBounds rowBounds);

    /**
     * 根据id查询用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    SysUserDto selectSysUser(@Param("userId") String userId);

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 用户信息
     */
    SysUserDto getUserByName(@Param("username") String username);

    /**
     * 根据userid查询用户信息
     * @param userId
     * @return
     */
    SysUser selectByUserId(@Param("userId")String userId);
}
