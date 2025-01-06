package com.yss.baseservice.api.system;

import com.common.base.BaseController;
import com.common.base.Response;
import com.github.pagehelper.PageInfo;
import com.yss.baseservice.api.system.request.DictTypeListReq;
import com.yss.baseservice.api.system.request.DictTypeReq;
import com.yss.baseservice.api.system.request.DictTypeUpdateReq;
import com.yss.baseservice.application.system.DictTypeApplication;
import com.yss.baseservice.application.system.dto.DictTypeDto;
import com.yss.baseservice.infrastructure.common.aspect.OperLog;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典
 * @author wangqi
 * @date 2021/12/3
 */
@RestController
@RequestMapping("/system/dict")
public class DictTypeController extends BaseController {

    private final DictTypeApplication dictTypeApplication;

    public DictTypeController(DictTypeApplication dictTypeApplication) {
        this.dictTypeApplication = dictTypeApplication;
    }

    /**
     * 字典列表
     * @param req 列表请求类
     */
    @GetMapping("/dictTypeList")
    public Response dictTypeList(@Validated DictTypeListReq req) {
        List<DictTypeDto> list = dictTypeApplication.selectDictTypeList(req.getDictName(),req.getDictType(), req.getStatus(), req.buildRowBounds());
        return Response.ok(new PageInfo<>(list));
    }

    /**
     * 新增数据字典
     * @param req 字典请求类
     */
    @PostMapping("/addDictType")
    @OperLog(operModule = "数据字典-添加数据字典",operType = "CREATE")
    public Response addDictType(@RequestBody @Validated DictTypeReq req) {
        dictTypeApplication.addDictType(req, this.getUserId());
        return Response.ok();
    }

    /**
     * 修改数据字典
     * @param req 修改请求类
     */
    @PostMapping("/updateDictType")
    @OperLog(operModule = "数据字典-修改数据字典",operType = "UPDATE")
    public Response updateDictType(@RequestBody @Validated DictTypeUpdateReq req) {
        dictTypeApplication.updateDictType(req);
        return Response.ok();
    }

    /**
     * 删除字典
     * @param dictType 字典类型
     */
    @DeleteMapping("/deleteDictType")
    @OperLog(operModule = "数据字典-删除数据字典",operType = "DELETE")
    public Response deleteDictType(@RequestParam String dictType) {
        dictTypeApplication.deleteDictType(dictType);
        return Response.ok();
    }




}
