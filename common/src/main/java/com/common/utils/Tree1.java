package com.common.utils;

import java.util.List;

/**
 * 树结构基础实现
 * @author lijianbin
 * @date 2021-09-17 10:24
 **/
public interface Tree1 {
    /**
     * 获取主键id
     * @return  主键id
     */
    String getId();

    /**
     * 获取父级id
     * @return  父级id
     */
    String getParentId();

    /**
     * 保存子集
     * @param childList 子集
     */
    void setChildren(List<Tree1> childList);
}
