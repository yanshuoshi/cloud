package com.yss.baseservice.domain.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yss.baseservice.application.system.dto.OrganizationDto;
import com.yss.baseservice.application.system.dto.OrganizationStudentDto;
import com.yss.baseservice.domain.system.entity.Organization;
import com.yss.baseservice.domain.system.entity.SysUser;
import com.yss.baseservice.domain.system.mapper.OrganizationMapper;
import com.yss.baseservice.domain.system.mapper.StudentMapper;
import com.yss.baseservice.domain.system.mapper.SysUserMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 组织机构领域层
 *
 * @author lijianbin
 * @date 2021-09-17 10:52
 **/
@Service
public class OrganizationDomainService {
    private final OrganizationMapper organizationMapper;
    private final SysUserMapper sysUserMapper;
    private final StudentMapper studentMapper;

    public OrganizationDomainService(OrganizationMapper organizationMapper,
                                     StudentMapper studentMapper,
                                     SysUserMapper sysUserMapper) {
        this.organizationMapper = organizationMapper;
        this.sysUserMapper = sysUserMapper;
        this.studentMapper = studentMapper;
    }

    /**
     * 查询组织
     *
     * @param organizationName 组织名称
     * @return List<OrganizationDto>
     */
    public List<OrganizationDto> selectOrganizationList(String organizationName) {
        return this.organizationMapper.selectOrganizationList(organizationName);
    }

    /**
     * 获取所有组织
     *
     * @return 组织机构
     */
    public List<OrganizationDto> selectOrganization() {
        List<OrganizationDto> organizationDtos = this.organizationMapper.selectOrganizationList(null);
        if (CollectionUtils.isNotEmpty(organizationDtos)) {
            for (OrganizationDto organizationDto : organizationDtos) {
                Integer integer = sysUserMapper.selectCount(new QueryWrapper<SysUser>().eq("status", 1).eq("organization_id", organizationDto.getId()));
                organizationDto.setCount(integer);
            }
        }
        return organizationDtos;
    }

    /**
     * 新增组织
     *
     * @param parentId         父级id
     * @param organizationName 名称
     * @param userId           创建用户id
     */
    public void organizationSave(Integer parentId, String organizationName, String userId) {
        Organization organization = Organization.createOrganization(parentId, organizationName, userId);
        organization.insert();
    }

    /**
     * 修改组织
     *
     * @param organizationId   主键id
     * @param organizationName 名称
     */
    public void organizationUpdate(Integer organizationId, String organizationName, Integer parentId) {
        Organization organization = Organization.updateOrganization(organizationId, organizationName, parentId);
        organization.updateById();
    }

    /**
     * 删除组织
     *
     * @param organizationId 主键id
     */
    public void organizationRemove(Integer organizationId) {
        this.organizationMapper.deleteOrganization(organizationId);
    }

    /**
     * 查询所有班级所有学生
     *
     * @return
     */
    public List<OrganizationStudentDto> getAllStudent(String name) {
        List<OrganizationStudentDto> organizationStudentDto = organizationMapper.getAllStudent(null);
        // if (CollectionUtils.isNotEmpty(organizationStudentDto)) {
        //     organizationStudentDto.forEach(organizationStudentDto1 -> {
        //         QueryWrapper<Student> select = new QueryWrapper<Student>()
        //                 .eq("organization_id", organizationStudentDto1.getId())
        //                 .select("student_id", "student_name").eq("status",1);
        //         if (StringUtils.isNotBlank(name)) {
        //             select.like("student_name", name);
        //         }
        //         List<Map<String, Object>> mapList = studentMapper.selectMaps(select);
        //         List<Map<String, Object>> stu = new ArrayList<>();
        //         if(CollectionUtils.isNotEmpty(mapList)){
        //             mapList.forEach(map -> {
        //                 Map<String,Object> map1 = new HashMap<>();
        //                 map1.put("id",map.get("studentId"));
        //                 map1.put("organizationName",map.get("studentName"));
        //                 stu.add(map1);
        //             });
        //         }
        //         organizationStudentDto1.setStus(stu);
        //     });
        // }
        return organizationStudentDto;
    }
}
