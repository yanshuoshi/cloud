package com.yss.baseservice.domain.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yss.baseservice.application.system.dto.StudentDto;
import com.yss.baseservice.application.system.dto.StudentExportDto;
import com.yss.baseservice.application.system.dto.StudentForAppDto;
import com.yss.baseservice.domain.system.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 学生仓储层
 * @author lijianbin
 * @date 2021-09-17 13:59
 **/
@Repository
public interface StudentMapper extends BaseMapper<Student> {
    /**
     * 学生分页列表
     * @param studentNumber 用户名
     * @param studentName   姓名
     * @param studentSex     性别
     * @param organizationId  组织id
     * @param rowBounds 分页
     * @return  学生列表
     */
    List<StudentDto> selectStudentList(@Param("studentNumber") String studentNumber, @Param("studentName") String studentName,
                                       @Param("studentSex") String studentSex , @Param("organizationId") Integer organizationId, RowBounds rowBounds);

    /**
     * 批量导入用户
     * @param studentList   学生信息
     */
    void insertAll(@Param("studentList") List<Student> studentList);

    /**
     * 导出学生信息
     * @param studentNumber 学号
     * @param studentName 学生姓名
     * @param studentSex 性别
     * @param organizationId 组织id
     * @return 学生信息列表
     */
    List<StudentExportDto> selectStudentExport(@Param("studentNumber") String studentNumber, @Param("studentName") String studentName,
                                               @Param("studentSex") String studentSex, @Param("organizationId") Integer organizationId);

    /**
     * 导入照片
     * @param studentNameList  学生姓名集合
     */
    void updateHeadUrlAll(@Param("studentNameList") List studentNameList);



    /**
     * 选择同学 （app）
     * @param organizationId 组织id
     * @param studentName 学生名
     * @return  List<StudentForAppDto>
     */
    List<StudentForAppDto> getStudentList(@Param("organizationId") Integer organizationId, @Param("studentName") String studentName);

    StudentDto selectDetail(@Param("username") String username);

    /**
     * 根据学号查询学生信息
     * @param username
     * @return
     */
    Map<String, Object> selectByNumber(@Param("username") String username);
}
