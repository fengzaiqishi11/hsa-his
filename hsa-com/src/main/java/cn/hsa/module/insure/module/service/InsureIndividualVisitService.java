package cn.hsa.module.insure.module.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.entity.InsureIndividualVisitDO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.insureIndividualVisit.service
 * @Class_name: InsureIndividualVisitService
 * @Describe(描述): 医保就诊信息表 service层
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/18 19:26
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-insure")
public interface InsureIndividualVisitService {
    
    /**
     * @Menthod addInsureIndividualVisit
     * @Desrciption 新增医保就诊信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/18 20:16 
     * @Return int 受影响行数
     */
    InsureIndividualVisitDO addInsureIndividualVisit(Map<String,Object> param);

    /**
     * @Menthod findByCondition
     * @Desrciption 查询医保就诊信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/11/29 15:57
     * @Return java.util.List<cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO>
     */
    List<InsureIndividualVisitDTO> findByCondition(Map<String,Object> param);

    /**
     * @Menthod editInsureIndividualVisit
     * @Desrciption 编辑医保就诊信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/1 14:58
     * @Return int 受影响行数
     */
    int editInsureIndividualVisit(Map<String,Object> param);

    /**
     * @Menthod insertIndividualVisit
     * @Desrciption 新增医保就诊信息
     * @param insertMap 请求参数
     * @Author 廖继广
     * @Date 2020/12/21 14:58
     * @Return Boolean
     */
    Boolean insertIndividualVisit(Map<String, Object> insertMap);



    /**
     * @Menthod insertIndividualVisit
     * @Desrciption 新增医保就诊信息
     * @param param 请求参数
     * @Author 廖继广
     * @Date 2020/12/21 14:58
     * @Return Boolean
     */
    Boolean deleteByVisitId(Map<String, Object> param);


    /**
     * @Method
     * @Desrciption
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/4 9:02
     * @Return
     **/
    InsureIndividualVisitDTO getInsureIndividualVisitById(Map<String, Object> insureVisitParam);

    /**
     * @Method deleteInsureVisitById
     * @Desrciption  退费以后，取消门诊挂号登记
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/16 19:50
     * @Return
     **/
    WrapperResponse<Boolean> deleteInsureVisitById(Map<String, Object> map);

    /**
     * @Method updateInsureInidivdual
     * @Desrciption  修改医保病人信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/5/20 22:41
     * @Return
    **/
    WrapperResponse<Boolean> updateInsureInidivdual(Map<String,Object> map);

    /**
     * @Method selectInsureInfo
     * @Desrciption  查询医保就诊信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/7/30 15:32
     * @Return
    **/
    WrapperResponse<InsureIndividualVisitDTO> selectInsureInfo(Map<String,Object> map);
}
