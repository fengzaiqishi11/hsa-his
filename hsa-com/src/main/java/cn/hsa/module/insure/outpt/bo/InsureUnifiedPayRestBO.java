package cn.hsa.module.insure.outpt.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureItemDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import cn.hsa.module.insure.module.entity.*;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.inpt.bo
 * @class_name: InsureUnifiedPayRestBO
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/3/12 15:10
 * @Company: 创智和宇
 **/
public interface InsureUnifiedPayRestBO {
    /**
     * @Method downLoadItem
     * @Desrciption  医保通一支付平台，项目匹配下载接口
     * @Parammap
     *
     * @Author fuhui
     * @Date   2021/3/12 15:12
     * @Return
     **/
    Map<String,Object> UP_1317(Map<String, Object> map);

    /**
     * @param map
     * @Method uploadItem
     * @Desrciption 医保统一支付平台;项目对照上传
     * @Param insureItemDTO
     * @Author fuhui
     * @Date 2021/3/15 17:21
     * @Return Boolean
     */
    Map<String,Object>  UP_3301(Map<String, Object> map);
    /**
     * @Method insertUnifiedItem
     * @Desrciption  医保统一支付平台：根据不同的功能号下载不同的目录下载接口
     *  1301：西药中成药目录下载
     *  1302中药饮片目录下载
     *  【1303】医疗机构制剂目录下载【1304】民族药品目录查询【1305】医疗服务项目目录下载
     *  【1306】医用耗材目录下载【1307】疾病与诊断目录下载【1308】手术操作目录下载
     *  【1309】门诊慢特病种目录下载【1310】按病种付费病种目录下载【1311】日间手术治疗病种目录下载
     *  【1313】肿瘤形态学目录下载【1314】中医疾病目录下载【1315】中医证候目录下载
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/9 14:06
     * @Return
     **/
    Map<String,Object> insertDownloadItem(Map<String, Object> map);

    Map<String,Object>  UP_9162(Map<String, Object> map);

    Map<String,Object>  UP_3302(Map<String, Object> map);

    /**
     * @param insureItemDTO
     * @Method queryPageUnifiedItem
     * @Desrciption 医保统一支付平台：分页查询医保下载回来的数据
     * @Param
     * @Author fuhui
     * @Date 2021/4/10 1:28
     * @Return
     */
    PageDTO queryPageUnifiedItem(InsureItemDTO insureItemDTO);

    /**
     * @param itemMatchDTO
     * @Method insertUnifiedHandMatch
     * @Desrciption 医保统一支付平台：手动匹配医保项目信息和医院his的项目信息
     * @Param
     * @Author fuhui
     * @Date 2021/4/10 1:45
     * @Return
     */
    Boolean insertUnifiedHandMatch(InsureItemMatchDTO itemMatchDTO);

    /**
     * @param insureItemDTO
     * @Method queryInsureItemAll
     * @Desrciption :查询，调用长沙医保返回回来的项目数据
     * @Param insureItemDTO：itemName:项目名称，itemCode编码
     * @Author fuhui
     * @Date 2021/1/27 11:17
     * @Return
     */
    PageDTO queryInsureItemAll(InsureItemDTO insureItemDTO);

    PageDTO queryHospItemPage(Map<String, Object> map);

    /**
     * @param itemMatchDTO
     * @Method insertUnifiedAutoMatch
     * @Desrciption 医保统一支付平台：自动匹配医保项目信息和医院his的项目信息
     * @Param
     * @Author fuhui
     * @Date 2021/4/10 1:45
     * @Return
     */
    Integer insertUnifiedAutoMatch(InsureItemMatchDTO itemMatchDTO);

    /**
     * @param itemMatchDTO
     * @Method queryPageInsureItemMatch
     * @Desrciption 医保统一支付平台：显示匹配数据项
     * @Param
     * @Author fuhui
     * @Date 2021/4/12 21:51
     * @Return
     */
    PageDTO queryPageInsureItemMatch(InsureItemMatchDTO itemMatchDTO);

    /**
     * @param map
     * @Method selectSettleQuery
     * @Desrciption 医保统一支付：结算单查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/13 20:14
     * @Return
     */
    Map<String, Object> selectSettleQuery(Map<String, Object> map);

    /**
     * @param map
     * @Method selectPersonTreatment
     * @Desrciption 医保统一支付;待遇检查
     * @Param
     * @Author fuhui
     * @Date 2021/4/13 20:22
     * @Return
     */
    Map<String, Object> selectPersonTreatment(Map<String, Object> map);

    /**
     * @param map
     * @Method getMedisnInfo
     * @Desrciption 医保统一支付;医疗机构信息获取
     * @Param map
     * @Author fuhui
     * @Date 2021/4/13 20:28
     * @Return
     */
    Map<String, Object> getMedisnInfo(Map<String, Object> map);

    /**
     * @param map
     * @Method insertUnifiedDict
     * @Desrciption 查询下载医保统一支付码表
     * @Param
     * @Author fuhui
     * @Date 2021/4/14 23:28
     * @Return
     */
    Map<String, Object> insertUnifiedDict(Map<String, Object> map);

    /**
     * @Method queryMedicnInfo
     * @Desrciption 通过功能号查询 医保信息
     *  1316：医疗目录与医保目录匹配信息查询
     *  1318：医保目录限价信息查询
     *  1319：医保目录先自付比例信息查询
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/4/16 9:03
     * @Return
     **/
    Map<String, Object> insertMedicnInfo(Map<String, Object> map);

    /**
     * @param insureUnifiedLimitPriceDO
     * @Method queryPageInsureUnifiedLimitPrice
     * @Desrciption 分页查询所有的目录限价信息
     * @Param
     * @Author fuhui
     * @Date 2021/4/21 19:16
     * @Return
     */
    PageDTO queryPageInsureUnifiedLimitPrice(InsureUnifiedLimitPriceDO insureUnifiedLimitPriceDO);

    /**
     * @param insureUnifiedMatchDO
     * @Method queryPageInsureUnifiedMatch
     * @Desrciption 分页查询所有医疗目录与医保目录匹配信息
     * @Param
     * @Author fuhui
     * @Date 2021/4/21 19:16
     * @Return
     */
    PageDTO queryPageInsureUnifiedMatch(InsureUnifiedMatchDO insureUnifiedMatchDO);

    /**
     * @Method queryPageInsureUnifiedMatch
     * @Desrciption  分页查询医保目录信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/21 19:16
     * @Return
     **/
    PageDTO queryPageInsureUnifiedDirectory (InsureDirectoryInfoDO insureDirectoryInfoDO);

    /**
     * @Method queryPageInsureUnifiedMatch
     * @Desrciption  分页查询所有医保目录先自付比例信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/21 19:16
     * @Return
     **/
    PageDTO queryPageInsureUnifiedRatio(InsureUnifidRatioDO insureUnifidRatioDO);

    /**
     * @param insureUnifiedMatchMedicinsDO
     * @Method queryPageInsureUnifiedMatch
     * @Desrciption 【1317】医药机构目录匹配信息查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/21 19:16
     * @Return
     */
    PageDTO queryPageInsureMedicinesMatch(InsureUnifiedMatchMedicinsDO insureUnifiedMatchMedicinsDO);

    /**
     * @Method insertInsureMatch
     * @Desrciption  自动匹配医保项目信息和医院his的项目信息
     * @Param
     *
     * @Author liaojiguang
     * @Date   2021/05/27 10:55
     * @Return
     **/
    Integer insertInsureMatch(InsureItemMatchDTO insureItemMatchDTO);

    /**
     * @param insureItemDTO
     * @Method insertUnifiedItem
     * @Desrciption 医保统一只支付平台 新增项目新增功能
     * @Param insureItemDTO:医保项目表
     * @Author fuhui
     * @Date 2021/6/15 11:29
     * @Return
     */
    Boolean insertUnifiedItem(InsureItemDTO insureItemDTO);

    /**
     * @Author 医保二部-张金平
     * @Date 2022-05-05 11:38
     * @Description 分页查询所有民族药品目录信息
     * @param insureUnifiedNationDrugDO
     * @return cn.hsa.base.PageDTO
     */
    PageDTO queryPageInsureUnifiedNationDrug(InsureUnifiedNationDrugDO insureUnifiedNationDrugDO);
    /**
     * @param map
     * @Method getMedisnInfo
     * @Desrciption 医保统一支付;医疗机构信息获取
     * @Param map
     * @Author fuhui
     * @Date 2021/4/13 20:28
     * @Return
     */
    PageDTO getMedisnInfoByMedisnInName(Map<String, Object> map);
}
