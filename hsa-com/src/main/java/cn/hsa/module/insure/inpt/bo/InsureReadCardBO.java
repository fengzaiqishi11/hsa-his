package cn.hsa.module.insure.inpt.bo;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.inpt.bo
 * @class_name: InsureReadCardBO
 * @Description: 读身份证
 * @Author: LiaoJiGuang
 * @Email: jiguang.liao@powersi.com
 * @Date: 2021/7/29 15:17
 * @Company: 创智和宇
 **/
public interface InsureReadCardBO {

    /**
     * @Method getReadIdCard
     * @Desrciption 读身份证
     * @Param map
     * @Author LiaoJiGuang
     * @Date   2021/7/29 15:11
     * @Return
     **/
    Map<String,Object> getReadIdCard(Map<String, Object> map);

    /**
     * @Method updateReadIdCard
     * @Desrciption 修改身份证密码
     * @Param map
     * @Author LiaoJiGuang
     * @Date   2021/7/30 10:03
     * @Return
     **/
    Map<String,Object> updateReadIdCard(Map<String, Object> map);

}
