package cn.hsa.insure.unifiedpay.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureItemDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import cn.hsa.module.insure.module.entity.*;
import cn.hsa.module.insure.outpt.bo.InsureUnifiedPayRestBO;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayRestService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.service.impl
 * @class_name: InsureUnifiedPayRestServiceImpl
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/3/12 15:14
 * @Company: 创智和宇
 **/
@Slf4j
@HsafRestPath("/service/insure/insureUnifiedPayRest")
@Service("insureUnifiedPayRestService_provider")
public class InsureUnifiedPayRestServiceImpl extends HsafService implements InsureUnifiedPayRestService {

    @Resource
    private InsureUnifiedPayRestBO insureUnifiedPayRestBO;

    /**
     * @param map
     * @Method downLoadItem
     * @Desrciption 医保通一支付平台，项目匹配下载接口
     * @Parammap
     * @Author fuhui
     * @Date 2021/3/12 15:12
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> UP_1317(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedPayRestBO.UP_1317(map));
    }

    /**
     * @param map
     * @Method uploadItem
     * @Desrciption 医保统一支付平台;项目对照上传
     * @Param insureItemDTO
     * @Author fuhui
     * @Date 2021/3/15 17:21
     * @Return Boolean
     */
    @Override
    public WrapperResponse<Map<String,Object> > uploadItem(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedPayRestBO.UP_3301(map));
    }

    @Override
    public WrapperResponse<Map<String, Object>> insertDownloadItem(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedPayRestBO.insertDownloadItem(map));
    }

    @Override
    public WrapperResponse<Map<String, Object>> UP_9162(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedPayRestBO.UP_9162(map));
    }

    /**
     * @param map
     * @Method UP_3302
     * @Desrciption 医保统一支付平台：项目对照撤销
     * @Param map
     * @Author fuhui
     * @Date 2021/3/29 16:41
     * @Date 2021/3/29 16:41
     * @Return Boolean
     */
    @Override
    public WrapperResponse<Map<String,Object> > UP_3302(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedPayRestBO.UP_3302(map));
    }

    /**
     * @param map
     * @Method queryPageUnifiedItem
     * @Desrciption 医保统一支付平台：分页查询医保下载回来的数据
     * @Param
     * @Author fuhui
     * @Date 2021/4/10 1:28
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryPageUnifiedItem(Map map) {
        InsureItemDTO insureItemDTO = MapUtils.get(map,"insureItemDTO");
        return WrapperResponse.success(insureUnifiedPayRestBO.queryPageUnifiedItem(insureItemDTO));
    }

    /**
     * @param map
     * @Method insertUnifiedHandMatch
     * @Desrciption 医保统一支付平台：手动匹配医保项目信息和医院his的项目信息
     * @Param
     * @Author fuhui
     * @Date 2021/4/10 1:45
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> insertUnifiedHandMatch(Map map) {
        InsureItemMatchDTO itemMatchDTO = MapUtils.get(map,"insureItemMatchDTO");
        return WrapperResponse.success(insureUnifiedPayRestBO.insertUnifiedHandMatch(itemMatchDTO));
    }

    /**
     * @param map
     * @Method queryInsureItemAll
     * @Desrciption :查询，调用长沙医保返回回来的项目数据
     * @Param insureItemDTO：itemName:项目名称，itemCode编码
     * @Author fuhui
     * @Date 2021/1/27 11:17
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryInsureItemAll(Map map) {
        InsureItemDTO insureItemDTO = MapUtils.get(map,"insureItemDTO");
        return WrapperResponse.success(insureUnifiedPayRestBO.queryInsureItemAll(insureItemDTO));
    }

    /**
     * @Method queryHospItemPage
     * @Desrciption  查询his医院项目数据 药品，项目，材料，疾病
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/4/11 15:48
     * @Return
     **/
    @Override
    public WrapperResponse<PageDTO> queryHospItemPage(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedPayRestBO.queryHospItemPage(map));
    }

    /**
     * @param map
     * @Method insertUnifiedAutoMatch
     * @Desrciption 医保统一支付平台：自动匹配医保项目信息和医院his的项目信息
     * @Param
     * @Author fuhui
     * @Date 2021/4/10 1:45
     * @Return
     */
    @Override
    public WrapperResponse<Integer> insertUnifiedAutoMatch(Map map) {
        InsureItemMatchDTO itemMatchDTO = MapUtils.get(map,"insureItemMatchDTO");
        return WrapperResponse.success(insureUnifiedPayRestBO.insertUnifiedAutoMatch(itemMatchDTO));
    }

    /**
     * @param map
     * @Method queryPageInsureItemMatch
     * @Desrciption 医保统一支付平台：显示匹配数据项
     * @Param
     * @Author fuhui
     * @Date 2021/4/12 21:51
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryPageInsureItemMatch(Map<String, Object> map) {
        InsureItemMatchDTO itemMatchDTO = MapUtils.get(map,"insureItemMatchDTO");
        return WrapperResponse.success(insureUnifiedPayRestBO.queryPageInsureItemMatch(itemMatchDTO));
    }

    /**
     * @param map
     * @Method selectSettleQuery
     * @Desrciption 医保统一支付：结算单查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/13 20:14
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> selectSettleQuery(Map<String, Object> map) {
        return WrapperResponse.success( insureUnifiedPayRestBO.selectSettleQuery(map));
    }

    /**
     * @param map
     * @Method selectPersonTreatment
     * @Desrciption 医保统一支付;待遇检查
     * @Param
     * @Author fuhui
     * @Date 2021/4/13 20:22
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> selectPersonTreatment(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedPayRestBO.selectPersonTreatment(map));
    }

    /**
     * @param map
     * @Method getMedisnInfo
     * @Desrciption 医保统一支付;医疗机构信息获取
     * @Param map
     * @Author fuhui
     * @Date 2021/4/13 20:28
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> getMedisnInfo(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedPayRestBO.getMedisnInfo(map));
    }

    /**
     * @param map
     * @Method insertUnifiedDict
     * @Desrciption 查询下载医保统一支付码表
     * @Param
     * @Author fuhui
     * @Date 2021/4/14 23:28
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> insertUnifiedDict(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedPayRestBO.insertUnifiedDict(map));
    }

    /**
     * @param map
     * @Method queryMedicnInfo
     * @Desrciption 通过功能号查询 医保信息
     * 1316：医疗目录与医保目录匹配信息查询
     * 1318：医保目录限价信息查询
     * 1319：医保目录先自付比例信息查询
     * @Param map
     * @Author fuhui
     * @Date 2021/4/16 9:03
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> queryMedicnInfo(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedPayRestBO.insertMedicnInfo(map));
    }

    /**
     * @param map
     * @Method queryPageInsureUnifiedLimitPrice
     * @Desrciption 分页查询所有的目录限价信息
     * @Param
     * @Author fuhui
     * @Date 2021/4/21 19:16
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryPageInsureUnifiedLimitPrice(Map<String, Object> map) {
        InsureUnifiedLimitPriceDO insureUnifiedLimitPriceDO =  MapUtils.get(map,"insureUnifiedLimitPriceDO");
        return WrapperResponse.success(insureUnifiedPayRestBO.queryPageInsureUnifiedLimitPrice(insureUnifiedLimitPriceDO));
    }

    /**
     * @param map
     * @Method queryPageInsureUnifiedMatch
     * @Desrciption 分页查询所有医疗目录与医保目录匹配信息
     * @Param
     * @Author fuhui
     * @Date 2021/4/21 19:16
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryPageInsureUnifiedMatch(Map<String, Object> map) {
        InsureUnifiedMatchDO insureUnifiedMatchDO = MapUtils.get(map,"insureUnifiedMatchDO");
        return WrapperResponse.success(insureUnifiedPayRestBO.queryPageInsureUnifiedMatch(insureUnifiedMatchDO));
    }

    /**
     * @Method queryPageInsureUnifiedMatch
     * @Desrciption  分页查询医保目录信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/21 19:16
     * @Return
     **/
    @Override
    public WrapperResponse<PageDTO> queryPageInsureUnifiedDirectory(Map<String, Object> map) {
        InsureDirectoryInfoDO insureDirectoryInfoDO = MapUtils.get(map,"insureDirectoryInfoDO");
        return WrapperResponse.success(insureUnifiedPayRestBO.queryPageInsureUnifiedDirectory(insureDirectoryInfoDO));
    }

    /**
     * @Method queryPageInsureUnifiedMatch
     * @Desrciption  分页查询所有医保目录先自付比例信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/21 19:16
     * @Return
     **/
    @Override
    public WrapperResponse<PageDTO> queryPageInsureUnifiedRatio(Map<String, Object> map) {
        InsureUnifidRatioDO insureUnifidRatioDO = MapUtils.get(map,"insureUnifidRatioDO");
        return WrapperResponse.success(insureUnifiedPayRestBO.queryPageInsureUnifiedRatio(insureUnifidRatioDO));
    }

    /**
     * @param map
     * @Method queryPageInsureUnifiedMatch
     * @Desrciption 【1317】医药机构目录匹配信息查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/21 19:16
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryPageInsureMedicinesMatch(Map<String, Object> map) {
        InsureUnifiedMatchMedicinsDO insureUnifiedMatchMedicinsDO = MapUtils.get(map,"insureUnifiedMatchMedicinsDO");
        return WrapperResponse.success(insureUnifiedPayRestBO.queryPageInsureMedicinesMatch(insureUnifiedMatchMedicinsDO));
    }

    /**
     * @Method insertInsureMatch
     * @Desrciption  自动匹配医保项目信息和医院his的项目信息
     * @Param
     *
     * @Author liaojiguang
     * @Date   2021/05/27 10:55
     * @Return
     **/
    @Override
    public WrapperResponse<Integer> insertInsureMatch(Map map) {
        InsureItemMatchDTO insureItemMatchDTO = MapUtils.get(map,"insureItemMatchDTO");
        return WrapperResponse.success(insureUnifiedPayRestBO.insertInsureMatch(insureItemMatchDTO));
    }

    /**
     * @param map
     * @Method insertUnifiedItem
     * @Desrciption 医保统一只支付平台 新增项目新增功能
     * @Param insureItemDTO:医保项目表
     * @Author fuhui
     * @Date 2021/6/15 11:29
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> insertInsureUnifiedItem(Map map) {
        InsureItemDTO insureItemDTO  = MapUtils.get(map,"insureItemDTO");
        return WrapperResponse.success(insureUnifiedPayRestBO.insertUnifiedItem(insureItemDTO));
    }

    /**
     * @Author 医保二部-张金平
     * @Date 2022-05-05 11:36
     * @Description 分页查询所有民族药品目录信息
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     */
    @Override
    public WrapperResponse<PageDTO> queryPageInsureUnifiedNationDrug(Map<String, Object> map) {
        InsureUnifiedNationDrugDO insureUnifiedNationDrugDO = MapUtils.get(map,"insureUnifiedNationDrugDO");
        return WrapperResponse.success(insureUnifiedPayRestBO.queryPageInsureUnifiedNationDrug(insureUnifiedNationDrugDO));
    }
    /**
     * @param map
     * @Method getMedisnInfo
     * @Desrciption 医保统一支付;医疗机构信息获取
     * @Param map
     * @Author fuhui
     * @Date 2021/4/13 20:28
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> getMedisnInfoByMedisnInName(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedPayRestBO.getMedisnInfoByMedisnInName(map));
    }
}
