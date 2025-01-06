package com.yss.baseservice.application.system;


import com.common.utils.Tree;
import com.common.utils.Tree1;
import com.common.utils.TreeUtils;
import com.common.utils.TreeUtils1;
import com.yss.baseservice.application.system.dto.OrganizationDto;
import com.yss.baseservice.application.system.dto.OrganizationStudentDto;
import com.yss.baseservice.domain.system.OrganizationDomainService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 组织结构应用层
 * @author lijianbin
 * @date 2021-09-17 09:29
 **/
@Component
public class OrganizationApplication {
    private final OrganizationDomainService organizationDomainService;

    public OrganizationApplication(OrganizationDomainService organizationDomainService) {
        this.organizationDomainService = organizationDomainService;
    }


    /**
     * 列表页面需要展示的树
     * @return 组织树
     */
    public List<Tree> selectOrganizationList(String organizationName) {
        List<OrganizationDto> list = organizationDomainService.selectOrganizationList(organizationName);
        if(list.isEmpty()){
            return TreeUtils.getChildPerms(list,0);
        }
        return TreeUtils.getChildPerms(list, list.get(0).getParentId());
    }


    /**
     * 组织机构树
     */
    public List<Tree> organizationTree() {
        List<OrganizationDto> list = organizationDomainService.selectOrganization();
        if(list.isEmpty()){
            return TreeUtils.getChildPerms(list, 0);
        }
        return TreeUtils.getChildPerms(list, list.get(0).getParentId());
    }

    /**
     * 新增组织
     * @param parentId  父级id
     * @param organizationName      名称
     * @param userId    创建用户id
     */
    public void organizationSave(Integer parentId, String organizationName  ,String userId) {
        organizationDomainService.organizationSave(parentId, organizationName , userId);
    }

    /**
     * 修改组织
     * @param organizationId        主键id
     * @param organizationName      名称
     */
    public void organizationUpdate(Integer organizationId, String organizationName,Integer parentId) {
        organizationDomainService.organizationUpdate(organizationId, organizationName,parentId);
    }

    /**
     * 删除组织
     * @param id        主键id
     */
    public void organizationRemove(Integer id) {
        organizationDomainService.organizationRemove(id);
    }

    /**
     * 查询所有班级所有学生
     *
     * @return
     */
    public List<Tree1> getAllStudent(String name) {
        List<OrganizationStudentDto> list = organizationDomainService.getAllStudent(name);
        if(list.isEmpty()){
            return TreeUtils1.getChildPerms(list,"0");
        }
        return TreeUtils1.getChildPerms(list, list.get(0).getParentId());
    }

}
