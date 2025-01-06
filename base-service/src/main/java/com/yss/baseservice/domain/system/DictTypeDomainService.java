package com.yss.baseservice.domain.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.common.base.BaseException;
import com.yss.baseservice.application.system.dto.DictTypeDto;
import com.yss.baseservice.domain.system.entity.DictData;
import com.yss.baseservice.domain.system.entity.DictType;
import com.yss.baseservice.domain.system.mapper.DictDataMapper;
import com.yss.baseservice.domain.system.mapper.DictTypeMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据字典
 * @author wangqi
 * @date 2021/12/3
 */
@Service
public class DictTypeDomainService {

    private final DictTypeMapper dictTypeMapper;
    private final DictDataMapper dictDataMapper;
    private final DictDataDomainService dictDataDomainService;

    public DictTypeDomainService(DictTypeMapper dictTypeMapper, DictDataMapper dictDataMapper, DictDataDomainService dictDataDomainService) {
        this.dictTypeMapper = dictTypeMapper;
        this.dictDataMapper = dictDataMapper;
        this.dictDataDomainService = dictDataDomainService;
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
        return  this.dictTypeMapper.selectDictTypeList(dictName,dictType,status,rowBounds);
    }

    /**
     * 新增数据字典
     * @param dictName 字典名称
     * @param dictType 字典类别
     * @param status 字典状态
     * @param remark 备注
     * @param userId 用户id
     */
    public void addDictType(String dictName,String dictType,Integer status,String remark ,String userId){
        DictType dictType1 = this.dictTypeMapper.selectOne(new QueryWrapper<DictType>().eq("dict_type",dictType));
        if(null != dictType1){
            throw new BaseException(500,"该字典类型已存在，不允许重复添加");
        }
        dictType1 = DictType.createDictType(dictName,dictType,status,remark,userId);
        dictType1.insert();
    }

    /**
     * 修改数据字典
     * @param dictId 字典id
     * @param dictName 字典名称
     * @param dictType 字典类型
     * @param status 字典状态
     * @param remark 备注
     */
    public void updateDictType(Integer dictId,String dictName,String dictType,Integer status,String remark){
        DictType dictTypeOld = this.dictTypeMapper.selectById(dictId);
        DictType dictType1 = DictType.updateDictType(dictId,dictName,dictType,status,remark);
        dictType1.updateById();
        UpdateWrapper<DictData> updateWrapper = new UpdateWrapper<>();
        DictData dictData = new DictData();
        //字典类型是否停用,若停用则对应字典数据也停用
        dictData.setStatus(status);
        //类型有无修改，若修改则更新字典数据表中的字典类型
        dictData.setDictType(dictType);
        updateWrapper.eq("dict_type",dictTypeOld.getDictType());
        this.dictDataMapper.update(dictData,updateWrapper);
        //更新缓存
        this.dictDataDomainService.init();
    }

    /**
     * 删除字典
     * @param dictType 字典类型
     */
    public void deleteDictType(String dictType){
        this.dictTypeMapper.delete(new QueryWrapper<DictType>().eq("dict_type",dictType));
        //删除字典主项时,删除字典数据
        this.dictDataMapper.delete(new QueryWrapper<DictData>().eq("dict_type",dictType));
        //更新缓存
        this.dictDataDomainService.init();
    }
}
