package com.yss.baseservice.domain.system;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.common.base.BaseException;
import com.yss.baseservice.application.system.dto.DictDataDto;
import com.yss.baseservice.domain.system.entity.DictData;
import com.yss.baseservice.domain.system.mapper.DictDataMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典数据
 * @author wangqi
 * @date 2021/12/3
 */
@Service
public class DictDataDomainService {

    private final DictDataMapper dictDataMapper;
    private final RedisTemplate<String,String> redisTemplate;

    public DictDataDomainService(DictDataMapper dictDataMapper, RedisTemplate<String, String> redisTemplate) {
        this.dictDataMapper = dictDataMapper;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 查询字段数据列表
     * @param dictType 字典类型
     * @param dictLabel 字典标签
     * @param status 状态
     * @param rowBounds 分页
     * @return   List<DictDataDto>
     */
    public List<DictDataDto> selectDictDataList(String dictType, String dictLabel, Integer status, RowBounds rowBounds){
        return this.dictDataMapper.selectDictDataList(dictType,dictLabel,status,rowBounds);
    }

    /**
     * 新增字典数据
     * @param dictType 字典类型
     * @param dictLabel 字典标签
     * @param dictValue 字典键值
     * @param dictSort 字典排序
     * @param status 状态
     * @param remark 备注
     * @param userId 用户id
     */
    public void addDictData(String dictType,String dictLabel,String dictValue,Integer dictSort,Integer status,String remark,String userId){
        DictData dictData = this.dictDataMapper.selectOne(new QueryWrapper<DictData>().eq("dict_value",dictValue));
        if(null != dictData){
            throw  new BaseException(500,"该字典键值已存在，不允许重复添加");
        }
        dictData = DictData.createDictData(dictType,dictLabel,dictValue,dictSort,status,remark,userId);
        dictData.insert();
        loadingDictCache();
    }

    /**
     * 修改字典数据
     * @param dictType 字典类型
     * @param dictLabel 字典标签
     * @param dictValue 字典键值
     * @param dictSort 字典排序
     * @param status 状态
     * @param remark 备注
     * @param dictDataId 主键id
     */
    public void updateDictData(Integer dictDataId ,String  dictType, String dictLabel, String dictValue,Integer dictSort,Integer status,String remark){
        DictData dictData = DictData.updateDictData(dictDataId,dictType,dictLabel,dictValue,dictSort,status,remark);
        dictData.updateById();
        loadingDictCache();
    }

    /**
     * 删除字典数据
     * @param dictDataId 字典数据id
     */
    public void deleteDictData(Integer dictDataId){
        this.dictDataMapper.deleteById(dictDataId);
        loadingDictCache();
    }


    /**
     * 查询字典下拉框数据
     * @param dictType 字典类型
     * @return  List<DictData>
     */
    public List<DictData> dictDataSelect(String dictType){
        return  this.dictDataMapper.selectList(new QueryWrapper<DictData>().eq("dict_type",dictType).eq("status",1));
    }



    /**
     * 项目启动时，初始化字典到缓存
     */
    @PostConstruct
    public void init(){
        loadingDictCache();
    }

    /**
     *  将字典缓存
     */
    private void loadingDictCache(){
        QueryWrapper<DictData> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("dict_type","dict_label","dict_value");
        queryWrapper.eq("status",1);
        List<DictData> list = this.dictDataMapper.selectList(queryWrapper);
        Map<String, List<DictData>> prodMap= list.stream().collect(Collectors.groupingBy(DictData::getDictType));
        redisTemplate.opsForValue().set("dict", JSON.toJSONString(prodMap));
    }
}
