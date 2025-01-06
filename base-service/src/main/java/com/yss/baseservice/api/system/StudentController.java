package com.yss.baseservice.api.system;

import com.common.base.BaseController;
import com.common.base.Response;
import com.github.pagehelper.PageInfo;
import com.yss.baseservice.api.system.request.ChangePasswordStudentReq;
import com.yss.baseservice.api.system.request.StudentListReq;
import com.yss.baseservice.api.system.request.StudentReq;
import com.yss.baseservice.api.system.request.StudentUpdateReq;
import com.yss.baseservice.application.system.StudentApplication;
import com.yss.baseservice.application.system.dto.StudentDto;
import com.yss.baseservice.infrastructure.common.aspect.OperLog;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 学生管理
 *
 * @author lijianbin
 * @date 2021-09-17 11:31
 **/
@RestController
@RequestMapping("/system/student/")
public class StudentController extends BaseController {
    private final StudentApplication studentApplication;

    public StudentController(StudentApplication studentApplication) {
        this.studentApplication = studentApplication;
    }

    /**
     * 学生分页列表
     */
    @GetMapping("studentList")
    public Response studentList(@Validated StudentListReq req) {
        List<StudentDto> list = studentApplication.studentList(req.getStudentNumber(), req.getStudentName(), req.getStudentSex(),
                req.getOrganizationId(), req.buildRowBounds());
        return Response.ok(new PageInfo<>(list));
    }

    /**
     * 添加学生
     */
    @PostMapping("addStudent")
    @OperLog(operModule = "学生管理", operType = "CREATE")
    public Response studentSave(@RequestBody @Validated StudentReq req) {
       String studentId = studentApplication.studentSave(req, this.getUserId());
        return Response.ok(studentId);
    }

    /**
     * 修改学生信息
     */
    @PostMapping("updateStudent")
    public Response studentUpdate(@RequestBody @Validated StudentUpdateReq req) {
        studentApplication.updateStudent(req,this.getUserId());
        return Response.ok();
    }

    /**
     * 重置学生密码
     */
    @PutMapping("studentPwdReset")
    @OperLog(operModule = "学生管理-重置密码", operType = "UPDATE")
    public Response studentPwdReset(@RequestParam String studentId) {
        studentApplication.studentPwdReset(studentId);
        return Response.ok();
    }


    /**
     * 修改密码
     *
     * @param req 修改密码请求类
     */
    @PostMapping("/changePassword")
    public Response changePassword(@RequestBody @Validated ChangePasswordStudentReq req) {
        studentApplication.changePassword(req.getStudentId(), req.getOldPassword(), req.getNewPassword());
        return Response.ok();
    }

    /**
     * 删除学生
     */
    @DeleteMapping("removeStudent")
    @OperLog(operModule = "学生管理", operType = "DELETE")
    public Response studentRemove(@RequestParam String studentId) {
        studentApplication.removeStudent(studentId);
        return Response.ok();
    }

    /**
     * 导入学生
     */
    @PostMapping("importStudent")
    @OperLog(operModule = "学生管理-导入学生", operType = "CREATE")
    public Response importStudent(MultipartFile file, Integer organizationId) {
        studentApplication.importStudent(file, organizationId, this.getUserId());
        return Response.ok();
    }

    /**
     * 导出学生
     */
    @GetMapping("studentExport")
    public Response studentExport(@Validated StudentListReq req) {
        return Response.ok(studentApplication.studentExport(req));
    }


    /**
     * 导入学生照片（批量）
     *
     * @param files 照片
     */
    @PostMapping("headUrlImport")
    @OperLog(operModule = "学生管理-导入学生照片", operType = "UPDATE")
    public Response headUrlImport(MultipartFile[] files) {
        studentApplication.headUrlImport(files, null);
        return Response.ok();
    }

}
