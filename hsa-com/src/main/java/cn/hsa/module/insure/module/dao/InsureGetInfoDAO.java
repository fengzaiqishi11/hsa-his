package cn.hsa.module.insure.module.dao;

import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InsureGetInfoDAO {

    /**
     * @Method getSetlInfo
     * @Desrciption 结算清单信息
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-10 15:21
     * @Return cn.hsa.module.insure.module.dto.InsureSettleInfoDTO
     **/
    InsureSettleInfoDTO getSetlInfo(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method getOutSetlInfo
     * @Desrciption 结算清单信息
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-10 15:21
     * @Return cn.hsa.module.insure.module.dto.InsureSettleInfoDTO
     **/
    InsureSettleInfoDTO getOutSetlInfo(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryPayinfo
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 21:28
     * @Return java.util.List<cn.hsa.module.insure.module.dto.PayInfoDTO>
     **/
    List<InsureIndividualFundDTO> queryPayinfo(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryPayinfo
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 21:23
     * @Return java.util.List<cn.hsa.module.insure.module.dto.PayInfoDTO>
     **/
    List<OpspdiseInfoDTO> queryOpspdiseinfo(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method selectIsSetlleFee
     * @Desrciption  查询已经结算完成的费用明细集合
     * @Param
     *
     * @Author caoliang
     * @Date   2021/7/20 17:35
     * @Return
     **/
    List<Map<String, Object>> selectIsSetlleFee(Map<String, Object> map);

    /**
     * @Method queryDiseinfo
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 21:26
     * @Return java.util.List<cn.hsa.module.insure.module.dto.DiseInfoDTO>
     **/
    List<DiseInfoDTO> queryDiseinfo(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryIteminfo
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 21:26
     * @Return java.util.List<cn.hsa.module.insure.module.dto.ItemInfoDTO>
     **/
    List<ItemInfoDTO> queryIteminfo(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryOprninfo
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 21:26
     * @Return java.util.List<cn.hsa.module.insure.module.dto.OprninfoDTO>
     **/
    List<OprninfoDTO> queryOprninfo(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryIcuinfo
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 21:27
     * @Return java.util.List<cn.hsa.module.insure.module.dto.IcuInfoDTO>
     **/
    List<IcuInfoDTO> queryIcuinfo(Map<String,Object> map);

    /**
     * @Method getInsureCost
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 23:09
     * @Return java.util.List<cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO>
     **/
    InsureIndividualCostDTO queryInsureCost(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryOutCost
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 17:28
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>
     **/
    List<InptCostDTO> queryOutCost(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryInCost
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 17:28
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>
     **/
    List<InptCostDTO> queryInCost(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryAll
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 17:28
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>
     **/
    List<InsureUploadCostDTO> queryAll(InsureSettleInfoDTO insureSettleInfoDTO);


    /**
     * @Method queryID
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author yuelong.chen
     * @Date   2021-08-19 20:27
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>
     **/
    int queryID(InsureSettleInfoDTO insureSettleInfoDTO);



    /**
     * @Method deleteByVisitID
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author yuelong.chen
     * @Date   2021-08-19 20:27
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>
     **/
    int deleteByVisitID(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryVisit
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 19:10
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
     **/
    List<InptVisitDTO> queryVisit(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryInsureSettle
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 19:10
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InsureSettleInfoDTO>
     *
     * @return*/
    List<PayInfoDTO> queryInsureSettle(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryInsure
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 19:10
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.ItemInfoDTO>
     **/
    List<ItemInfoDTO> queryInsure(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method insertCost
     * @Desrciption
     * @Param
     * [InsureUpladCostDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 19:10
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
     **/
    int insertCost(@Param("list")List<InsureUploadCostDTO> insureUploadCostList);

    /**
     * @Method insertSettleInfo
     * @Desrciption
     * @Param
     * [InsureUpladCostDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 19:10
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
     **/
    int insertSettleInfo(Map map);

    String getSetlInfoLocal(InsureSettleInfoDTO insureSettleInfoDTO);


    Map<String,Object> getSettleInfo(InsureSettleInfoDTO insureSettleInfoDTO);



    List<InptCostDTO> queryInptMatchPage(InsureSettleInfoDTO insureSettleInfoDTO);

    List<InptCostDTO> queryOutMatchPage(InsureSettleInfoDTO insureSettleInfoDTO);

    List<InptCostDTO> queryInptUnMatchPage(InsureSettleInfoDTO insureSettleInfoDTO);

    List<InptCostDTO> queryOutUnMatchPage(InsureSettleInfoDTO insureSettleInfoDTO);

    OutinInvoiceDTO getInvoiceNo(Map<String, Object> orderMap);


    String querySettleNo(Map<String, Object> orderMap);

    List<OperInfoRecordDTO> selectOperInfo(Map<String, Object> map);

    /**
     * @Method selectOprninfo
     * @Desrciption   医疗保障基金结算清单 -- 查询重症监护信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/3 15:44
     * @Return
     **/
    List<Map<String, Object>> selectIcuInfo(Map<String, Object> map);


    Map<String, Object> getInptPatientInfo(Map<String, Object> map);

    Map<String, Object> getOutptPatientInfo(Map<String, Object> map);

    /**
     * @Method insertOperInfo
     * @Desrciption  处理医疗保障基金结算清单 -- 保存手术信息
     * @Param  map
     *
     * @Author fuhui
     * @Date   2021/11/2 16:04
     * @Return
     **/
    void insertOperInfo(@Param("opernInfoList") List<Map<String, Object>> opernInfoList);

    /**
     * @Method insertIcuInfo
     * @Desrciption   处理医疗保障基金结算清单 -- 重症监护信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/2 16:13
     * @Return
     **/
    void insertIcuInfo(@Param("icuinfoList") List<Map<String, Object>> icuinfoList);

    /**
     * @Method insertItemInfo
     * @Desrciption  处理医疗保障基金结算清单 -- 收费项目信息等级
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/2 17:01
     * @Return
     **/
    void insertItemInfo(@Param("groupListMap") List<Map<String, Object>> groupListMap);

    /**
     * @Method handerPayInfo
     * @Desrciption  调用结算信息查询接口  -- 处理基金支付信息
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/11/2 8:46
     * @Return
     **/
    void insertPayInfo(@Param("payInfoList") List<Map<String, Object>> payInfoList);

    /**
     * @Method deleteIcuInfo
     * @Desrciption  删除医疗保障基金结算清单 --- 重症监护信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/2 19:04
     * @Return
     **/
    void deleteIcuInfo(Map<String, Object> map);

    /**
     * @Method deletePayInfo
     * @Desrciption  删除医疗保障基金结算清单 --- 基金支付信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/2 19:06
     * @Return
     **/
    void deletePayInfo(Map<String, Object> map);

    /**
     * @Method insertOpspdiseinfo
     * @Desrciption  医疗保障基金结算清单 ---  门诊慢特病诊断信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/2 19:12
     * @Return
     **/
    void insertOpspdiseinfo(@Param("mapList") List<Map<String, Object>> mapList);

    /**
     * @Method deleteOpspdiseinfo
     * @Desrciption  删除医疗保障基金结算清单 --- 门诊慢特病诊断信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/2 19:17
     * @Return
     **/
    void deleteOpspdiseinfo(Map<String, Object> map);

    /**
     * @Method selectHanderPayInfo
     * @Desrciption  医疗保障基金结算清单 查询 基金支付信息节点
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/2 19:20
     * @Return
     **/
    List<Map<String, Object>> selectHanderPayInfo(Map<String, Object> map);

    /**
     * @Method insertOpspdiseinfo
     * @Desrciption   处理医疗保障基金结算清单 -- 住院诊断信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/2 16:13
     * @Return
     **/
    void updateDiseaseInfo(@Param("diseaseInfoList") List<Map<String, Object>> diseaseInfoList);

    /**
     * @Method selectItemInfo
     * @Desrciption 医疗保障基金结算清单 -- 查询收费项目信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/3 14:01
     * @Return
    **/
    List<Map<String, Object>> selectItemInfo(Map<String, Object> map);
    
    /**
     * @Method qureryItemInfo
     * @Desrciption  医疗保障基金结算清单 - 查询收费项目信息
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/11/3 15:32 
     * @Return 
    **/
    List<Map<String, Object>> qureryItemInfo(Map<String, Object> map);

    /**
     * @Method selectOprninfo
     * @Desrciption   医疗保障基金结算清单 - 查询手术信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/3 15:42
     * @Return
    **/
    List<Map<String, Object>> selectOprninfo(Map<String, Object> map);


    /**
     * @Method selectOprninfo
     * @Desrciption   医疗保障基金结算清单 -- 查询住院诊断信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/3 15:44
     * @Return
     **/
    List<Map<String, Object>> selectOpsdiseaseInfo(Map<String, Object> map);
    
    /**
     * @Method selectDiseaseInfo
     * @Desrciption   医疗保障基金结算清单 -- 查询住院诊断信息
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/11/3 16:03 
     * @Return 
    **/
    List<Map<String, Object>> selectDiseaseInfo(Map<String, Object> map);
    
    /**
     * @Method selectBaseSetlInfo
     * @Desrciption  医疗保障基金结算清单 --结算清单信息
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/11/3 16:40 
     * @Return 
    **/
    InsureSettleInfoDTO selectBaseSetlInfo(Map<String, Object> map);

    /**
     * @Method selectBillNo
     * @Desrciption  查询住院票据号码
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/3 20:08
     * @Return
    **/
    Map<String, Object> selectInptBillNo(Map<String, Object> map);

    /**
     * @Method selectBillNo
     * @Desrciption  查询门诊票据号码
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/3 20:08
     * @Return
     **/
    Map<String, Object> selectOutptBillNo(Map<String, Object> map);

    /**
     * @Method insertSetleInfo
     * @Desrciption  医疗保障基金结算清单 : 保存结算清单信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/3 20:33
     * @Return
    **/
    void insertSetleInfo(Map<String, Object> setlInfoMap);

    /**
     * @Method updateInsureGetInfo
     * @Desrciption  结算清单上传完以后 更新结算清单流水号
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/3 20:54
     * @Return
    **/
    void updateInsureGetInfo(Map<String, Object> map);
    
    /**
     * @Method  查询医疗保障结算信息
     * @Desrciption  
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/11/4 14:04 
     * @Return 
    **/
    InsureSettleInfoDTO querySettlInfo(Map<String, Object> map);

    /**
     * @Method deleteSettleInfo
     * @Desrciption  医疗保障基金结算清单 ： 删除结算清单信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/4 14:20
     * @Return
    **/
    void deleteSettleInfo(Map<String, Object> map);
    
    /**
     * @Method deleteOprninfo
     * @Desrciption  医疗保障基金结算清单 ： 删除手术操作信息
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/11/4 15:22
     * @Return 
    **/
    void deleteOprninfo(Map<String, Object> map);

    /**
     * @Method deleteOprninfo
     * @Desrciption  医疗保障基金结算清单 ： 删除收费项目信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/4 15:22
     * @Return
     **/
    void deleteItemInfo(Map<String, Object> map);

    /**
     * @Method deleteOprninfo
     * @Desrciption  医疗保障基金结算清单 ： 删除住院诊断信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/4 15:22
     * @Return
     **/

    void deleteDiseaseInfo(Map<String, Object> map);
    
    /**
     * @Method selectMriBaseInfo
     * @Desrciption  根据就诊id  查询病案首页信息
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/11/5 10:17 
     * @Return 
    **/
    Map<String, Object> selectMriBaseInfo(Map<String, Object> map);

    /**
     * @Method selectMriOperInfo
     * @Desrciption  查询病案首页手术信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/5 11:00
     * @Return
    **/
    List<OperInfoRecordDTO> selectMriOperInfo(Map<String, Object> map);
    
    /**
     * @Method deleteDisease
     * @Desrciption  
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/11/5 17:15
     * @Return 
    **/
    void deleteDisease(Map<String, Object> map);

    /**
     * @Method
     * @Desrciption
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/5 17:23
     * @Return
    **/
    void insertDisease(@Param("inptDiagnoseDTOList") List<Map<String,Object>> inptDiagnoseDTOList);

    /**
     * @Method selectIcuInfoForMap
     * @Desrciption   重症监护信息  转驼峰
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/6 15:07
     * @Return
    **/
    List<Map<String, Object>> selectIcuInfoForMap(Map<String, Object> map);

    List<Map<String, Object>> selectOprninfoForMap(Map<String, Object> map);

    List<InptDiagnoseDTO>  selectDiseinfoForMap(Map<String, Object> map);

    List<Map<String, Object>> selectOpspdiseinfoForMap(Map<String, Object> map);


    List<Map<String, Object>> selectPayinfoForMap(Map<String, Object> map);

    /**
     * @Method deleteOperInfo
     * @Desrciption  删除手术信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/8 14:24
     * @Return
    **/
    void deleteOperInfo(Map<String, Object> map);

    /**
     * @Method deleteSetleInfo
     * @Desrciption  删除结算清单信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/8 14:23
     * @Return
    **/
    void deleteSetleInfo(Map<String, Object> setlInfoMap);
    
    /**
     * @Method selectRefldeptDept
     * @Desrciption  查询转院科室
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/11/8 14:23
     * @Return 
    **/
    String selectRefldeptDept(Map<String, Object> map);

    /**
     * @param map
     * @Method queryPage
     * @Desrciption 查询结算清单左侧人员类别信息
     * @Param
     * @Author fuhui
     * @Date 2021/11/9 15:29
     * @Return
     */
    List<Map<String, Object>> querySetlePage(Map<String, Object> map);

    /**
     * @Method selectInsureIndividual
     * @Desrciption  查询患者登记时保存的门特病种信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/9 16:07
     * @Return
    **/
    InsureIndividualVisitDTO selectInsureIndividual(Map<String, Object> map);
}
