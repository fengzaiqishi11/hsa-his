package cn.hsa.insure.module.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.mris.bo.MrisBo;
import cn.hsa.module.insure.mris.service.MrisService;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.service.impl
 * @Class_name: MrisServiceImpl
 * @Describe(描述): 病案首页医保开放统一接口 service实现层
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/09 9:10
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/insure/mris")
@Service("mrisService_provider")
public class MrisServiceImpl extends HsafService implements MrisService {
    @Resource
    MrisBo mrisBo;

    /**
     * @Method getAdvicePateintInfo
     * @Desrciption 医保提取待录入医嘱信息
     * @param param
     * @Author 廖继广
     * @Date   2020/11/05 11:11
     * @Return java.util.Map
     **/
    @Override
    public Map<String, Object> getAdvicePateintInfo(Map param) {
        return mrisBo.getAdvicePateintInfo(MapUtils.get(param,"insureMrisAdvicePatientInfoDTO"));
    }

    /**
     * @Method insertMrisHomeInfo
     * @Desrciption 病案信息上传
     * @param param
     * @Author 廖继广
     * @Date   2020/11/05 11:11
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean insertMrisHomeInfo(Map param) {
        return mrisBo.insertMrisHomeInfo(param);
    }

    /**
     * @Method selectMrisHomeInfo
     * @Desrciption 查询病案信息
     * @param param
     * @Author 廖继广
     * @Date   2020/11/05 11:11
     * @Return java.util.Map
     **/
    @Override
    public Map<String,Object> selectMrisHomeInfo(Map param) {
        return mrisBo.selectMrisHomeInfo(MapUtils.get(param,"insureMrisAdvicePatientInfoDTO"));
    }

    /**
     * @Method selectMrisHomeInfo
     * @Desrciption 删除病案信息
     * @param param
     * @Author 廖继广
     * @Date   2020/11/05 11:11
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean deleteMrisHomeInfo(Map param) {
        return mrisBo.deleteMrisHomeInfo(MapUtils.get(param,"insureMrisAdvicePatientInfoDTO"));
    }

}
