package com.yss.baseservice.domain.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yss.baseservice.application.system.dto.RoleNumberPermissionUrlDto;
import com.yss.baseservice.domain.system.entity.RolePermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色权限仓储层
 * @author lijianbin
 * @date 2021-09-17 17:07
 **/
@Repository
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    /**
     * 批量保存
     * @param list  角色权限
     */
    void insertAll(@Param("list") List<RolePermission> list);

    /**
     * 查询角色权限关联
     * @return  角色权限
     */
    List<RoleNumberPermissionUrlDto> selectPermissionAll();
}
