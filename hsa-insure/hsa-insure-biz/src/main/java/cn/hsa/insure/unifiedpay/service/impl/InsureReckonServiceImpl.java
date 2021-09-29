package cn.hsa.insure.unifiedpay.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.outpt.bo.InsureReckonBO;
import cn.hsa.module.insure.outpt.service.InsureReckonService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.insure.unifiedpay.service.impl
 *@Class_name: insureUnifiedPayReversalTradeServiceImpl
 *@Describe: 统一支付平台---冲正交易
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2021/4/12 11:11
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/insure/insureReckon")
@Service("insureReckonService_provider")
public class InsureReckonServiceImpl extends HsafService implements InsureReckonService {

    @Resource
    InsureReckonBO insureReckonBO;

    /**
     * 清算查询
     *
     * @param map
     * @Method queryInsureReckonInfo
     * @Desrciption
     * @Author liaojiguang
     * @Date 2021/9/9 19:45
     * @Return
     **/
    @Override
    public WrapperResponse<PageDTO> queryInsureReckonInfo(Map<String,Object> map) {
        return WrapperResponse.success(insureReckonBO.queryInsureReckonInfo(MapUtils.get(map,"insureReckonDTO")));
    }

    /**
     * 清算新增
     *
     * @param map
     * @Method addInsureReckonInfo
     * @Desrciption
     * @Author liaojiguang
     * @Date 2021/9/9 19:45
     * @Return
     **/
    @Override
    public WrapperResponse<Boolean> addInsureReckonInfo(Map<String,Object> map) {
        return WrapperResponse.success(insureReckonBO.addInsureReckonInfo(MapUtils.get(map,"insureReckonDTO")));
    }

    /**
     * 清算编辑
     *
     * @param map
     * @Method updateInsureReckonInfo
     * @Desrciption
     * @Author liaojiguang
     * @Date 2021/9/9 19:45
     * @Return
     **/
    @Override
    public WrapperResponse<Boolean> updateInsureReckonInfo(Map<String,Object> map) {
        return WrapperResponse.success(insureReckonBO.updateInsureReckonInfo(MapUtils.get(map,"insureReckonDTO")));
    }

    /**
     * 清算申请
     *
     * @param map
     * @Method updateToInsureReckon
     * @Desrciption
     * @Author liaojiguang
     * @Date 2021/9/9 19:45
     * @Return
     **/
    @Override
    public WrapperResponse<Boolean> updateToInsureReckon(Map<String,Object> map) {
        return WrapperResponse.success(insureReckonBO.updateToInsureReckon(MapUtils.get(map,"insureReckonDTO")));
    }

    /**
     * 清算撤销申请
     *
     * @param map
     * @Method updateToRevokeInsureReckon
     * @Desrciption
     * @Author liaojiguang
     * @Date 2021/9/9 19:45
     * @Return
     **/
    @Override
    public WrapperResponse<Boolean> updateToRevokeInsureReckon(Map<String,Object> map) {
        return WrapperResponse.success(insureReckonBO.updateToRevokeInsureReckon(MapUtils.get(map,"insureReckonDTO")));
    }

    /**
     * 医药机构清算申请 - 删除
     *
     * @param map
     * @Method deleteInsureReckon
     * @Desrciption
     * @Author liaojiguang
     * @Date 2021/9/9 17:31
     * @Return
     **/
    @Override
    public WrapperResponse<Boolean> deleteInsureReckon(Map<String, Object> map) {
        return WrapperResponse.success(insureReckonBO.deleteInsureReckon(MapUtils.get(map,"insureReckonDTO")));

    }

    /**
     * 医药机构清算申请 - 获取清算机构
     *
     * @param selectMap
     * @Method getInsureClrOptinsByRegCode
     * @Desrciption
     * @Author liaojiguang
     * @Date 2021/9/9 17:31
     * @Return
     **/
    @Override
    public WrapperResponse<List<String>> getInsureClrOptinsByRegCode(Map<String, Object> selectMap) {
        return WrapperResponse.success(insureReckonBO.getInsureClrOptinsByRegCode(MapUtils.get(selectMap,"insureReckonDTO")));
    }

    /**
     * 医疗机构月结算申请汇总信息分页查询-3693
     *
     * @param selectMap
     * @Method queryInsureMonSettleApplyInfo
     * @Desrciption
     * @Author liaojiguang
     * @Date 2021/9/9 17:31
     * @Return
     **/
    @Override
    public WrapperResponse<PageDTO> queryInsureMonSettleApplyInfo(Map<String, Object> selectMap) {
        return WrapperResponse.success(insureReckonBO.queryInsureMonSettleApplyInfo(MapUtils.get(selectMap,"insureReckonDTO")));
    }

    /**
     * 获取清算机构 -3694
     *
     * @param selectMap
     * @Method queryInsureClrOptinsInfo
     * @Desrciption
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @Override
    public WrapperResponse<PageDTO> queryInsureClrOptinsInfo(Map<String, Object> selectMap) {
        return WrapperResponse.success(insureReckonBO.queryInsureClrOptinsInfo(MapUtils.get(selectMap,"insureReckonDTO")));
    }

    /**
     * 获取清算汇总明细 -3695
     *
     * @param selectMap
     * @Method queryInsureSettleApplyInfo
     * @Desrciption 获取清算汇总明细
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @Override
    public WrapperResponse<PageDTO> queryInsureSettleApplyInfo(Map<String, Object> selectMap) {
        return WrapperResponse.success(insureReckonBO.queryInsureSettleApplyInfo(MapUtils.get(selectMap,"insureReckonDTO")));
    }

    /**
     * 获取暂扣明细信息 -3696
     *
     * @param selectMap
     * @Method queryInsureDetDetlList
     * @Desrciption 获取暂扣明细信息
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @Override
    public WrapperResponse<PageDTO> queryInsureDetDetlList(Map<String, Object> selectMap) {
        return WrapperResponse.success(insureReckonBO.queryInsureDetDetlList(MapUtils.get(selectMap,"insureReckonDTO")));
    }

    /**
     * 医疗机构月结算报表pdf文档 -3697
     *
     * @param selectMap
     * @Method getImportClredReportPdf
     * @Desrciption 医疗机构月结算报表pdf文档
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @Override
    public WrapperResponse<Map<String,Object>> getImportClredReportPdf(Map<String, Object> selectMap) {
        return WrapperResponse.success(insureReckonBO.getImportClredReportPdf(MapUtils.get(selectMap,"insureReckonDTO")));
    }

    /**
     * 获取拨付单信息 - 3407
     *
     * @param selectMap
     * @Method queryInsureAppropriationList
     * @Desrciption 获取拨付单信息
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @Override
    public WrapperResponse<PageDTO> queryInsureAppropriationList(Map<String, Object> selectMap) {
        return WrapperResponse.success(insureReckonBO.queryInsureAppropriationList(MapUtils.get(selectMap,"insureReckonDTO")));
    }

    /**
     * 获取基金明细信息 - 3702
     *
     * @param selectMap
     * @Method queryInsureDetailFundList
     * @Desrciption 获取基金明细信息
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @Override
    public WrapperResponse<PageDTO> queryInsureDetailFundList(Map<String, Object> selectMap) {
        return WrapperResponse.success(insureReckonBO.queryInsureDetailFundList(MapUtils.get(selectMap,"insureReckonDTO")));
    }

    /**
     * 获取结算明细信息 - 3703
     *
     * @param selectMap
     * @Method queryInsureSetlDetlList
     * @Desrciption 获取结算明细信息
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @Override
    public WrapperResponse<PageDTO> queryInsureSetlDetlList(Map<String, Object> selectMap) {
        return WrapperResponse.success(insureReckonBO.queryInsureSetlDetlList(MapUtils.get(selectMap,"insureReckonDTO")));

    }


}
