package com.yss.baseservice.application.system;

import com.yss.baseservice.api.system.request.DictTypeReq;
import com.yss.baseservice.api.system.request.DictTypeUpdateReq;
import com.yss.baseservice.application.system.dto.DictTypeDto;
import com.yss.baseservice.domain.system.DictTypeDomainService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 数据字典
 * @author wangqi
 * @date 2021/12/3
 */
@Component
public class DictTypeApplication {

    private final DictTypeDomainService dictTypeDomainService;

    public DictTypeApplication(DictTypeDomainService dictTypeDomainService) {
        this.dictTypeDomainService = dictTypeDomainService;
    }

    /**
     * 查询数据字典列表
     * @param dictName 字典名称
     * @param dictType 字典类型
     * @param status   状态
     * @param rowBounds 分页
     * @return  List<DictTypeDto>
     */
    public List<DictTypeDto> selectDictTypeList(String dictName, String dictType, Integer status, RowBounds rowBounds){
        return  this.dictTypeDomainService.selectDictTypeList(dictName,dictType,status,rowBounds);
    }


    /**
     * 新增字典
     * @param req 新增请求类
     * @param userId 用户id
     */
    public void addDictType(DictTypeReq req, String userId){
        this.dictTypeDomainService.addDictType(req.getDictName(),req.getDictType(),req.getStatus(),req.getRemark(),userId);
    }

    /**
     * 修改数据字典
     * @param req 修改请求类
     */
    public void updateDictType(DictTypeUpdateReq req){
        this.dictTypeDomainService.updateDictType(req.getDictId(),req.getDictName(),req.getDictType(),req.getStatus(),req.getRemark());
    }

    /**
     * 删除字典
     * @param dictType 字典类型
     */
    public void deleteDictType(String dictType){
        this.dictTypeDomainService.deleteDictType(dictType);
    }
}
