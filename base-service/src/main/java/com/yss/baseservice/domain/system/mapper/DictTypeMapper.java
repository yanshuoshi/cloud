package com.yss.baseservice.domain.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yss.baseservice.application.system.dto.DictTypeDto;
import com.yss.baseservice.domain.system.entity.DictType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 数据字典
 * @author wangqi
 * @date 2021/12/3
 */
@Repository
public interface DictTypeMapper extends BaseMapper<DictType> {
    /**
     * 查询数据字典列表
     * @param dictName 字典名称
     * @param dictType 字典类型
     * @param status   状态
     * @param rowBounds 分页
     * @return  List<DictTypeDto>
     */
    List<DictTypeDto> selectDictTypeList(@Param("dictName") String dictName, @Param("dictType") String dictType, @Param("status") Integer status, RowBounds rowBounds);
}
