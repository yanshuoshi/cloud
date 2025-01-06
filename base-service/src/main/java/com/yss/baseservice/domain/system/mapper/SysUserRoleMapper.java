package com.yss.baseservice.domain.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yss.baseservice.domain.system.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户角色关联仓储层
 * @author wangqi
 * @date  2021/11/2
 */
@Repository
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    /**
     * 批量保存
     * @param list  用户角色关联
     */
    void insertAll(@Param("list") List<SysUserRole> list);

    /**
     * 删除用户角色关联关系
     * @param userId
     * @param roleId
     */
    void deleteByUserOrRole(@Param("userId") String userId,@Param("roleId") Integer roleId);
}
