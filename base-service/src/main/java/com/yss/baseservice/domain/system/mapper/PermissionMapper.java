package com.yss.baseservice.domain.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yss.baseservice.domain.system.entity.Permission;
import org.springframework.stereotype.Repository;

/**
 * 权限仓储层
 * @author lijianbin
 * @date 2021-09-18 09:33
 **/
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {
}
