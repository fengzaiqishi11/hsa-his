package cn.hsa.util;

import java.util.*;

/**
 * @Package_ame: cn.hsa.util
 * @Class_name: ListUtils
 * @Description: list集合工具类
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 21:54
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public class ListUtils {
    /**
     * @Method 求两个集合合集
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/7/27 17:24
     * @Return 
     **/
    public static List union(List list1, List list2) {
        list1.addAll(list2);
        return list1;
    }

    /**
     * @Method 求两个集合的并集
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/7/27 17:25
     * @Return 
     **/
    public static List unionAll(List list1, List list2) {
        list1.removeAll(list2);
        list1.addAll(list2);
        return list1;
    }

    /**
     * @Method 求两个集合的交集
     * @Description
     * 最优法   利用hash这种很有用的数据结构来实现。我们知道，hash的特点之一就是不允许有重复元素，即hash表中的元素都是唯一的。
     * 所以，我们的思路就是：先把第一个集合的所有元素都放进hashSet中，时间复杂度O(M)；再把第二个集合中的元素放进hashSet中，如果有重复元素，就是这2个集合的交集，时间复杂度为O(N)。即总的时间复杂度从O(M*N)降低到了O(M+N)。
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 17:25
     * @Return
     **/
    public static List intersection(List list1, List list2) {
        List commonList = new ArrayList<>();
        Set hashSet = new HashSet<>();
        Collections.addAll(hashSet, list1);

        for (Object o : list2) {
            if (!hashSet.add(o)) {
                commonList.add(o);
            }
        }
        return commonList;
    }

    /**
     * @Method 获取指定索引数据
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/7/27 17:29
     * @Return 
     **/
    public static <T> T get(List list, int i) {
        return (T) list.get(i);
    }

    /**
     * @Method 删除集合中指定位置数据
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 17:32
     * @Return
     **/
    public static void remove(List list, int i) {
        list.remove(i);
    }

    /**
     * @Method 删除集合中指定数据
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 17:32
     * @Return
     **/
    public static void remove(List list, Object o) {
        list.remove(o);
    }

    /**
     * @Method 删除集合中指定集合数据
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 17:32
     * @Return
     **/
    public static void remove(List list, Collection c) {
        list.removeAll(c);
    }

    /**
     * @Method 集合排序-升序
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 17:32
     * @Return
     **/
    public static void sort(List list) {
        Collections.sort(list);
    }

    /**
     * @Method 集合排序-降序
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 17:32
     * @Return
     **/
    public static void reverse(List list) {
        Collections.reverse(list);
    }

    /**
     * @Method List集合是否为空
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 17:32
     * @Return
     **/
    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    /**
    * @Method startPage
    * @Desrciption  List分页工具
    * @param list 待分页的数据
    * @param pageNum 页码
    * @param pageSize 每页多少条数据
    * @Author liuqi1
    * @Date   2020/12/10 17:29
    * @Return java.util.List
    **/
    public static List startPage(List list, Integer pageNum,Integer pageSize) {
        if (list == null) {
            return null;
        }
        if (list.size() == 0) {
            return null;
        }

        Integer count = list.size(); // 记录总数
        Integer pageCount = 0; // 页数
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }

        int fromIndex = 0; // 开始索引
        int toIndex = 0; // 结束索引

        if (pageNum != pageCount) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }

        List pageList = list.subList(fromIndex, toIndex);

        return pageList;
    }



}

