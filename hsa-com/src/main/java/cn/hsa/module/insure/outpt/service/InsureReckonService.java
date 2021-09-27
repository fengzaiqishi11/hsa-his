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

    /**医药机构清算申请 - 获取清算机构
     * @Method getInsureClrOptinsByRegCode
     * @Desrciption
     * @param selectMap
     * @Author liaojiguang
     * @Date   2021/9/9 17:31
     * @Return
     **/
    @GetMapping("serive/insure/reckon/getInsureClrOptinsByRegCode")
    WrapperResponse<List<String>> getInsureClrOptinsByRegCode(Map<String, Object> selectMap);

    /**医疗机构月结算申请汇总信息分页查询-3693
     * @Method queryInsureMonSettleApplyInfo
     * @Desrciption
     * @param selectMap
     * @Author liaojiguang
     * @Date   2021/9/9 17:31
     * @Return
     **/
    @GetMapping("serive/insure/reckon/queryInsureMonSettleApplyInfo")
    WrapperResponse<PageDTO> queryInsureMonSettleApplyInfo(Map<String, Object> selectMap);

    /**获取清算机构 -3694
     * @Method queryInsureClrOptinsInfo
     * @Desrciption
     * @param selectMap
     * @Author liaojiguang
     * @Date   2021/9/22 09:15
     * @Return
     **/
    @GetMapping("serive/insure/reckon/queryInsureClrOptinsInfo")
    WrapperResponse<PageDTO> queryInsureClrOptinsInfo(Map<String, Object> selectMap);

    /** 获取清算汇总明细 -3695
     * @param selectMap
     * @Method queryInsureSettleApplyInfo
     * @Desrciption 获取清算汇总明细
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @GetMapping("serive/insure/reckon/queryInsureSettleApplyInfo")
    WrapperResponse<PageDTO> queryInsureSettleApplyInfo(Map<String, Object> selectMap);

    /** 获取暂扣明细信息 -3696
     * @param selectMap
     * @Method queryInsureDetDetlList
     * @Desrciption 获取暂扣明细信息
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @GetMapping("serive/insure/reckon/queryInsureDetDetlList")
    WrapperResponse<PageDTO> queryInsureDetDetlList(Map<String, Object> selectMap);

    /** 医疗机构月结算报表pdf文档 -3697
     * @param selectMap
     * @Method getImportClredReportPdf
     * @Desrciption 医疗机构月结算报表pdf文档
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @GetMapping("serive/insure/reckon/getImportClredReportPdf")
    WrapperResponse<Map<String,Object>> getImportClredReportPdf(Map<String, Object> selectMap);

    /** 获取拨付单信息 - 3407
     * @param selectMap
     * @Method queryInsureAppropriationList
     * @Desrciption 获取拨付单信息
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @GetMapping("serive/insure/reckon/queryInsureAppropriationList")
    WrapperResponse<PageDTO> queryInsureAppropriationList(Map<String, Object> selectMap);

    /** 获取基金明细信息 - 3702
     * @param selectMap
     * @Method queryInsureDetailFundList
     * @Desrciption 获取基金明细信息
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @GetMapping("serive/insure/reckon/queryInsureDetailFundList")
    WrapperResponse<PageDTO> queryInsureDetailFundList(Map<String, Object> selectMap);

    /** 获取结算明细信息 - 3703
     * @param selectMap
     * @Method queryInsureSetlDetlList
     * @Desrciption 获取结算明细信息
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @GetMapping("serive/insure/reckon/queryInsureSetlDetlList")
    WrapperResponse<PageDTO> queryInsureSetlDetlList(Map<String, Object> selectMap);
}
