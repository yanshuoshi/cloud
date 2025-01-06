package com.yss.baseservice.api.system;

import com.common.base.BaseController;
import com.common.base.Response;
import com.yss.baseservice.api.system.request.DictDataListReq;
import com.yss.baseservice.api.system.request.DictDataReq;
import com.yss.baseservice.api.system.request.DictDataUpdateReq;
import com.yss.baseservice.application.system.DictDataApplication;
import com.yss.baseservice.application.system.dto.DictDataDto;
import com.yss.baseservice.domain.system.entity.DictData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.yss.baseservice.infrastructure.common.aspect.OperLog;

import java.util.List;


/**
 * 字典数据
 * @author wangqi
 * @date 2021/12/3
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/dictData")
public class DictDataController extends BaseController {

    private final DictDataApplication dictDataApplication;
    private final RedisTemplate<String, String> redisTemplate;



    /**
     * 字典数据列表
     * @param req 列表请求类
     */
    @GetMapping("/dictDataList")
    public Response dictDataList(@Validated DictDataListReq req) {
        List<DictDataDto> list = this.dictDataApplication.selectDictDataList(req);
        return Response.ok(list);
    }

    /**
     * 添加字典数据
     * @param req 添加请求类
     */
    @PostMapping("/addDictData")
    @OperLog(operModule = "字典数据-添加字典数据",operType = "CREATE")
    public Response addDictData(@RequestBody @Validated DictDataReq req) {
        this.dictDataApplication.addDictData(req,this.getUserId());
        return Response.ok();
    }

    /**
     * 修改字典数据
     * @param req 修改请求类
     */
    @PostMapping("/updateDictData")
    @OperLog(operModule = "字典数据-修改字典数据",operType = "UPDATE")
    public Response updateDictData(@RequestBody @Validated DictDataUpdateReq req) {
        this.dictDataApplication.updateDictData(req);
        return Response.ok();
    }


    /**
     * 删除字典数据
     * @param dictDataId 字典数据id
     */
    @DeleteMapping("deleteDictData")
    @OperLog(operModule = "字典数据-删除字典数据",operType = "DELETE")
    public Response deleteDictData(@RequestParam  Integer dictDataId) {
        this.dictDataApplication.deleteDictData(dictDataId);
        return Response.ok();
    }

    /**
     * 通过类型查询对应下拉框数据
     * @param dictType 字典数据类型
     */
    @GetMapping("/dictDataSelect")
    public Response dictDataSelect(String dictType) {
        List<DictData> list = this.dictDataApplication.dictDataSelect(dictType);
        return Response.ok(list);
    }







}
