package com.yss.baseservice.domain.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yss.baseservice.application.system.dto.OrganizationDto;
import com.yss.baseservice.application.system.dto.OrganizationStudentDto;
import com.yss.baseservice.domain.system.entity.Organization;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 组织机构仓储层
 * @author lijianbin
 * @date 2021-09-17 10:53
 **/
@Repository
public interface OrganizationMapper extends BaseMapper<Organization> {

    /**
     * 根据部门名称查询组织
     * @param organizationName 组织名称
     * @return List<OrganizationDto>
     */
    List<OrganizationDto>  selectOrganizationList(@Param("organizationName") String organizationName);

    /**
     * 删除组织（包含下级）
     * @param organizationId 组织id
     */
    void deleteOrganization(@Param("organizationId") Integer organizationId);

    /**
     * 查询所有班级所有学生
     *
     * @return
     */
    List<OrganizationStudentDto> getAllStudent(@Param("name") String name);


    List<Integer> selectOrg(@Param("ids") String[] split);
}
