package com.yss.baseservice.domain.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.base.BaseException;
import com.yss.baseservice.application.system.dto.SysUserDto;
import com.yss.baseservice.domain.system.entity.SysUser;
import com.yss.baseservice.domain.system.mapper.SysUserMapper;
import com.yss.baseservice.domain.system.mapper.SysUserRoleMapper;
import com.yss.baseservice.infrastructure.common.ienum.SexEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统用户 service
 * @author wangqi
 * @date  2021/11/1
 */
@Slf4j
@Service
public class SysUserDomainService extends ServiceImpl<SysUserMapper, SysUser> {

    private final SysUserMapper sysUserMapper;
    private final SysUserRoleMapper sysUserRoleMapper;

    public SysUserDomainService(SysUserMapper sysUserMapper, SysUserRoleMapper sysUserRoleMapper) {
        this.sysUserMapper = sysUserMapper;
        this.sysUserRoleMapper = sysUserRoleMapper;
    }

    /**
     * 查询系统用户
     * @param username 账号
     * @param mobile 联系方式
     * @param rowBounds 分页
     * @return 用户列表
     */
    public List<SysUserDto> selectSysUserList(String username, String realName, String mobile, String sex, Integer organizationId, RowBounds rowBounds){
        return  sysUserMapper.selectSysUserList(username,realName,mobile,sex,organizationId,rowBounds);
    }

    /**
     * 添加系统用户
     * @param realName 真实姓名
     * @param username 用户名
     * @param password 密码
     * @param sex 性别
     * @param mobile 联系方式
     * @param headurl 头像
     * @param organizationId 组织id
     * @param userId 当前用户id
     */
    public String saveUser(String realName, String username, String password, SexEnum sex, String mobile, String headurl,
                           Integer organizationId, String userId ){
        // 查看该用户名是否存在
        SysUser sysUser = this.sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("username",username).eq("status", 1));
        if (sysUser != null) {
            throw new BaseException(500, "用户已存在");
        }
        // 保存用户
        sysUser = SysUser.createSysUser(realName, username, password, sex, mobile, headurl, organizationId, userId);
        sysUser.insert();
        return sysUser.getUserId();
    }


    /**
     * 修改系统用户
     * @param userId 主键id
     * @param realName 真实姓名
     * @param sex 性别
     * @param mobile 联系方式
     * @param headurl 头像
     * @param organizationId 组织id
     */
    public void updateUser(String userId, String realName, SexEnum sex, String mobile, String headurl, Integer organizationId ){
         SysUser sysUser = SysUser.updateSysUser(userId,realName,sex,mobile,headurl,organizationId);
         sysUser.update(new UpdateWrapper<SysUser>()
                 .eq("user_id",userId)
                 .set("real_name",realName)
                 .set("sex",sex)
                 .set("mobile",mobile)
                 .set("headurl",headurl)
                 .set("organization_id",organizationId)
         );
    }

    /**
     * 删除用户
     * @param userId 用户id
     */
    public void deleteUser(String userId){
        this.update(new UpdateWrapper<SysUser>() .eq("user_id",userId) .set("status",0));
        // SysUser sysUser = SysUser.deleteSysUser(userId);
        // sysUserMapper.updateById(sysUser);
        //删除用户之后需要删除用户角色关联关系
        sysUserRoleMapper.deleteByUserOrRole(userId,null);
    }

    /**
     * 重置密码
     * @param userId 用户id
     */
    public void passwordReset(String userId){
        SysUser sysUser = SysUser.passwordReset(userId,"123");
        sysUser.updateById();
    }

    /**
     *  修改密码
     * @param userId 用户id
     * @param oldPassword 老密码
     * @param newPassword 新密码
     */
    public  void changePassword(String userId,String oldPassword,String newPassword){
        SysUser sysUser = this.sysUserMapper.selectByUserId(userId);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(oldPassword,sysUser.getPassword())) {
            sysUser.setPassword(encoder.encode(newPassword));
            this.update(new UpdateWrapper<SysUser>().eq("user_id",userId).set("password",encoder.encode(newPassword)));
            // this.sysUserMapper.updateById(sysUser);
        } else {
            throw new BaseException(500, "原密码不正确");
        }

    }

    /**
     * 根据用户名查询
     * @param name 用户名
     * @return 用户信息
     */
    public SysUser selectUserByName(String name){
        return this.sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("username",name).eq("status",1));
    }

    /**
     * 根据id查询用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    public SysUserDto selectSysUser(String userId){
        return this.sysUserMapper.selectSysUser(userId);
    }

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 用户信息
     */
    public SysUserDto getUserByName(String username){
        return this.sysUserMapper.getUserByName(username);
    }

    /**
     * 根据钉钉id查询用户信息
     *
     * @param unionid 钉钉id
     * @return 用户信息
     */
    public SysUser getUserByUnionid(String unionid) {
       return sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("status",1).eq("unionid",unionid));
    }
}
