package cn.hsa.module.insure.mris.bo;

import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureMrisAdvicePatientInfoDTO;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.mris.bo
 * @Class_name: MrisBo
 * @Describe(描述): 病案首页医保统一开放调用 Bo 接口
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/09 9:26
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface MrisBo {
    /**
     * @Method getAdvicePateintInfo
     * @Desrciption 提取待录入医嘱人员信息
     * @params [param]
     * @Author 廖继广
     * @Date 2020/11/05 11:41
     * @Return java.uti.Map
     **/
    Map<String,Object> getAdvicePateintInfo(InsureMrisAdvicePatientInfoDTO insureMrisAdvicePatientInfoDTO);

    /**
     * @Method insertMrisHomeInfo
     * @Desrciption 病案首页录入
     * @params [param]
     * @Author 廖继广
     * @Date 2020/11/05 11:41
     * @Return Boolean
     **/
    Boolean insertMrisHomeInfo(Map param);

    /**
     * @Method selectMrisHomeInfo
     * @Desrciption 病案首页查询
     * @params [insureMrisAdvicePatientInfoDTO]
     * @Author 廖继广
     * @Date 2020/10/29 11:41
     * @Return java.util.Map<java.lang.String,java.String.Object>
     **/
    Map<String,Object> selectMrisHomeInfo (InsureMrisAdvicePatientInfoDTO insureMrisAdvicePatientInfoDTO);

    /**
     * @Method deleteMrisHomeInfo
     * @Desrciption 病案首页删除
     * @params [insureMrisAdvicePatientInfoDTO]
     * @Author 廖继广
     * @Date 2020/11/05 10:50
     * @Return java.lang.Boolean
     **/
    Boolean deleteMrisHomeInfo(InsureMrisAdvicePatientInfoDTO insureMrisAdvicePatientInfoDTO);

}
