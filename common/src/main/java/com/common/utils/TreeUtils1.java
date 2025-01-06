package com.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 树结构
 *
 * @author lijianbin
 * @date 2021-09-17 10:21
 **/
public class TreeUtils1 {

    private TreeUtils1() {
    }

    /**
     * 根据父节点的ID获取所有子节点
     */
    public static <T extends Tree1> List<Tree1> getChildPerms(List<T> list, String parentId) {
        List<Tree1> returnList = new ArrayList<>();
        for (Tree1 t : list) {
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId().equals(parentId)) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private static <T extends Tree1> void recursionFn(List<T> list, Tree1 t) {
        // 得到子节点列表
        List<Tree1> childList = getChildList(list, t);
        t.setChildren(childList);
        for (Tree1 tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private static <T extends Tree1> List<Tree1> getChildList(List<T> list, Tree1 t) {
        List<Tree1> tList = new ArrayList<>();
        for (Tree1 n : list) {
            if (n.getParentId().equals(t.getId())) {
                tList.add(n);
            }
        }
        return tList;
    }

    /**
     * 判断是否有子节点
     */
    private static <T extends Tree1> boolean hasChild(List<T> list, Tree1 t) {
        return !getChildList(list, t).isEmpty();
    }
}
