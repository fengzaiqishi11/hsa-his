package cn.hsa.module.insure.outpt.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.outpt.dto.InsureReckonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.outpt.service
 * @Class_name: InsureReckonService
 * @Describe: 统一支付平台---清算申请
 * @Author: liaojiguang
 * @Eamil: jiguang.liao@powersi.com.cn
 * @Date: 2021/9/9 14:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/

@FeignClient(value = "hsa-insure")
public interface InsureReckonService {

    /**清算查询
     * @Method queryInsureReckonInfo
     * @Desrciption
     * @param map
     * @Author liaojiguang
     * @Date   2021/9/9 19:45
     * @Return
     **/
    @GetMapping("serive/insure/reckon/queryInsureReckonInfo")
    WrapperResponse<PageDTO> queryInsureReckonInfo(Map<String,Object> map);

    /**清算新增
     * @Method addInsureReckonInfo
     * @Desrciption
     * @param map
     * @Author liaojiguang
     * @Date   2021/9/9 19:45
     * @Return
     **/
    @PostMapping("serive/insure/reckon/addInsureReckonInfo")
    WrapperResponse<Boolean> addInsureReckonInfo(Map<String,Object> map);

    /**清算编辑
     * @Method updateInsureReckonInfo
     * @Desrciption
     * @param map
     * @Author liaojiguang
     * @Date   2021/9/9 19:45
     * @Return
     **/
    @PostMapping("serive/insure/reckon/updateInsureReckonInfo")
    WrapperResponse<Boolean> updateInsureReckonInfo(Map<String,Object> map);

    /**清算申请
     * @Method updateToInsureReckon
     * @Desrciption
     * @param map
     * @Author liaojiguang
     * @Date   2021/9/9 19:45
     * @Return
     **/
    @PostMapping("serive/insure/reckon/updateToInsureReckon")
    WrapperResponse<Boolean> updateToInsureReckon(Map<String,Object> map);

    /**清算撤销申请
     * @Method updateToRevokeInsureReckon
     * @Desrciption
     * @param map
     * @Author liaojiguang
     * @Date   2021/9/9 19:45
     * @Return
     **/
    @PostMapping("serive/insure/reckon/updateToRevokeInsureReckon")
    WrapperResponse<Boolean> updateToRevokeInsureReckon(Map<String,Object> map);

    /**医药机构清算申请 - 删除
     * @Method queryInsureReckonInfo
     * @Desrciption
     * @param selectMap
     * @Author liaojiguang
     * @Date   2021/9/9 17:31
     * @Return
     **/
    @PostMapping("serive/insure/reckon/deleteInsureReckon")
    WrapperResponse<Boolean> deleteInsureReckon(Map<String, Object> selectMap);
}
