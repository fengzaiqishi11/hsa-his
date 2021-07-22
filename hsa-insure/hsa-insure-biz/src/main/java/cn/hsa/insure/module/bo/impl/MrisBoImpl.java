package cn.hsa.insure.module.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.insure.xiangtan.mris.MrisFunction;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureMrisAdvicePatientInfoDTO;
import cn.hsa.module.insure.mris.bo.MrisBo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.bo.impl
 * @Class_name: MrisBoImpl
 * @Describe(描述): 病案首页医保开放统一接口 Bo实现层
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/09 9:01
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
public class MrisBoImpl extends HsafBO implements MrisBo {
    @Resource
    private MrisFunction mrisFunction;

    /**
     * @Method getAdvicePateintInfo
     * @Desrciption 提取录入医嘱信息
     * @params [insureMrisAdvicePatientInfoDTO]
     * @Author 廖继广
     * @Date 2020/11/05 11:17
     * @Return java.util.Map
     **/
    @Override
    public Map<String, Object> getAdvicePateintInfo(InsureMrisAdvicePatientInfoDTO insureMrisAdvicePatientInfoDTO) {
        return mrisFunction.BIZC131271(insureMrisAdvicePatientInfoDTO);
    }

    /**
     * @Method insertMrisHomeInfo
     * @Desrciption 病案信息上传
     * @params [param]
     * @Author 廖继广
     * @Date 2020/11/05 11:17
     * @Return java.util.Map
     **/
    @Override
    public Boolean insertMrisHomeInfo(Map param) {
        return mrisFunction.BIZC320004(param);
    }

    /**
     * @Method selectMrisHomeInfo
     * @Desrciption 病案信息查询
     * @params [param]
     * @Author 廖继广
     * @Date 2020/11/05 11:17
     * @Return java.util.Map
     **/
    @Override
    public Map<String,Object> selectMrisHomeInfo(InsureMrisAdvicePatientInfoDTO insureMrisAdvicePatientInfoDTO) {
        return mrisFunction.BIZC320005(insureMrisAdvicePatientInfoDTO);
    }

    /**
     * @Method deleteMrisHomeInfo
     * @Desrciption 删除病案信息
     * @params [param]
     * @Author 廖继广
     * @Date 2020/11/05 11:17
     * @Return java.util.Map
     **/
    @Override
    public Boolean deleteMrisHomeInfo(InsureMrisAdvicePatientInfoDTO insureMrisAdvicePatientInfoDTO) {
        return mrisFunction.BIZC320006(insureMrisAdvicePatientInfoDTO);
    }
}
