package com.yss.baseservice.api.system;

import com.common.base.BaseController;
import com.common.base.Response;
import com.common.utils.Tree;
import com.common.utils.Tree1;
import com.yss.baseservice.api.system.request.OrganizationListReq;
import com.yss.baseservice.api.system.request.OrganizationReq;
import com.yss.baseservice.api.system.request.OrganizationUpdateReq;
import com.yss.baseservice.application.system.OrganizationApplication;
import com.yss.baseservice.infrastructure.common.aspect.OperLog;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 组织机构管理
 * @author lijianbin
 * @date 2021-09-17 09:13
 **/
@RestController
@RequestMapping("/system/organization/")
public class OrganizationController extends BaseController {
    private final OrganizationApplication organizationApplication;

    public OrganizationController(OrganizationApplication organizationApplication) {
        this.organizationApplication = organizationApplication;
    }


    /**
     *  获取组织树
     * @param req 请求类
     */
    @GetMapping("organizationList")
    public Response organizationList(@Validated OrganizationListReq req) {
        List<Tree> list = organizationApplication.selectOrganizationList(req.getOrganizationName());
        return Response.ok(list);
    }

    /**
     * 组织机构树
     */
    @GetMapping("organizationTree")
    public Response organizationTree() {
        List<Tree> list = organizationApplication.organizationTree();
        return Response.ok(list);
    }

    /**
     * 新增组织机构
     */
    @PostMapping("organizationSave")
    @OperLog(operModule = "组织管理",operType = "CREATE")
    public Response organizationSave(@RequestBody @Validated OrganizationReq req) {
        organizationApplication.organizationSave(req.getParentId(), req.getOrganizationName() , this.getUserId());
        return Response.ok();
    }

    /**
     * 修改组织机构
     */
    @PostMapping("organizationUpdate")
    @OperLog(operModule = "组织管理",operType = "UPDATE")
    public Response organizationUpdate(@RequestBody @Validated OrganizationUpdateReq req) {
        organizationApplication.organizationUpdate(req.getId(), req.getOrganizationName(),req.getParentId());
        return Response.ok();
    }

    /**
     * 删除组织机构
     */
    @DeleteMapping("organizationRemove")
    @OperLog(operModule = "组织管理",operType = "DELETE")
    public Response organizationRemove(@RequestParam Integer id) {
        organizationApplication.organizationRemove(id);
        return Response.ok();
    }

    /**
     * 查询所有班级所有学生
     *
     * @return
     */
    @GetMapping("all")
    public Response getAllStudent(String name) {
        List<Tree1> list = organizationApplication.getAllStudent(name);
        return Response.ok(list);
    }

}
