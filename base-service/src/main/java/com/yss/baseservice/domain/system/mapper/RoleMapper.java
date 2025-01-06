package com.yss.baseservice.domain.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yss.baseservice.application.system.dto.RoleByUserIdDto;
import com.yss.baseservice.application.system.dto.RoleCheckDto;
import com.yss.baseservice.application.system.dto.RoleDto;
import com.yss.baseservice.domain.system.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 角色仓储层
 * @author lijianbin
 * @date 2021-09-17 16:46
 **/
@Repository
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据条件查询角色列表
     * @param roleName 角色名称
     * @param roleKey 角色标识
     * @param rowBounds 分页
     * @return  List<RoleDto>
     */
    List<RoleDto> selectRoleList(@Param("roleName") String roleName, @Param("roleKey")String roleKey, RowBounds rowBounds);

    /**
     * 查询角色列表（分配角色使用）
     * @param userId 用户id
     * @return List<RoleCheckDto>
     */
    List<RoleCheckDto> roleAll(@Param("userId") String userId);

    /**
     * 用户登录查询权限
     *
     * @return
     */
    List<RoleByUserIdDto> selectMenuByUserId(@Param("userId")String userId);
}
