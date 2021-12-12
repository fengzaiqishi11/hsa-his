package cn.hsa.module.interf.home.bo;


import java.util.Map;

/**
 *@Package_name: cn.hsa.module.home.home.bo
 *@Class_name: BaseHomeBo
 *@Describe: 首页数据查询
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/10/29 9:06
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BaseHomeBo {

    /**
     * @Method queryHomeShowData
     * @Desrciption 首页数据查询
     * @param map
     * @Author liuqi1
     * @Date   2020/10/29 10:25
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> queryHomeShowData(Map<String, Object> map);
}
