package com.yss.baseservice.application.system;

import com.yss.baseservice.api.system.request.DictDataListReq;
import com.yss.baseservice.api.system.request.DictDataReq;
import com.yss.baseservice.api.system.request.DictDataUpdateReq;
import com.yss.baseservice.application.system.dto.DictDataDto;
import com.yss.baseservice.domain.other.WorkService;
import com.yss.baseservice.domain.other.models.TPlanReq;
import com.yss.baseservice.domain.system.DictDataDomainService;
import com.yss.baseservice.domain.system.entity.DictData;
import feign.FeignException;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 字典数据
 * @author wangqi
 * @date 2021/11/3
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DictDataApplication {

    private final DictDataDomainService dictDataDomainService;
    private final WorkService workService;


    /**
     * 查询字典数据列表
     * @param req 列表请求类
     * @return List<DictDataDto>
     */
    public List<DictDataDto> selectDictDataList(DictDataListReq req){
        return this.dictDataDomainService.selectDictDataList(req.getDictType(),req.getDictLabel(),req.getStatus(),req.buildRowBounds());
    }

    /**
     * 新增字典数据
     * @param req 新增请求类
     * @param userId 用户id
     */
    @GlobalTransactional
    public void addDictData(DictDataReq req, String userId){
        log.info("Seata全局事务id=================>{}", RootContext.getXID());
        this.dictDataDomainService.addDictData(req.getDictType(),req.getDictLabel(),req.getDictValue(),req.getDictSort(),req.getStatus(),req.getRemark(),userId);
//        try {
            workService.saveTPlan(new TPlanReq(null,"a",new Date(),new Date(),new Date(),new Date(), Arrays.asList(1,2,3)));
//        } catch (FeignException e) {
//            throw new RuntimeException(e.getMessage(), e);
//        }
    }

    /**
     * 修改字典数据
     * @param req 修改请求类
     */
    public void updateDictData(DictDataUpdateReq req){
        this.dictDataDomainService.updateDictData(req.getDictDataId(),req.getDictType(),req.getDictLabel(),req.getDictValue(),req.getDictSort(),req.getStatus(),req.getRemark());
    }


    /**
     * 删除字典数据
     * @param dictDataId 字典数据id
     */
    public void deleteDictData(Integer dictDataId){
        this.dictDataDomainService.deleteDictData(dictDataId);
    }

    /**
     * 根据类型查询字典下拉框数据
     * @param dictType 字典类型
     * @return  List<DictData>
     */
    public List<DictData>dictDataSelect(String dictType){
        return  this.dictDataDomainService.dictDataSelect(dictType);
    }
}
