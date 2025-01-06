package com.yss.baseservice.domain.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yss.baseservice.application.system.dto.DictDataDto;
import com.yss.baseservice.domain.system.entity.DictData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 字典数据
 * @author wangqi
 * @date 2021/12/3
 */
@Repository
public interface DictDataMapper extends BaseMapper<DictData> {

    /**
     * 查询字段数据列表
     * @param dictType 字典类型
     * @param dictLabel 字典标签
     * @param status 状态
     * @param rowBounds 分页
     * @return   List<DictDataDto>
     */
    List<DictDataDto> selectDictDataList(@Param("dictType") String dictType, @Param("dictLabel") String dictLabel, @Param("status") Integer status, RowBounds rowBounds);
}
