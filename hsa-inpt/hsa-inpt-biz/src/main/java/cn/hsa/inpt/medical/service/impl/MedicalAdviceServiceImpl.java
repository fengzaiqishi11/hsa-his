package cn.hsa.inpt.medical.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.medical.bo.MedicalAdviceBO;
import cn.hsa.module.inpt.medical.service.MedicalAdviceService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
* @Package_name: cn.hsa.inpt.medical.service.impl
* @class_name: MedicalAdviceServiceImpl
* @Description: 医嘱核收service实现类
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/9/9 11:15
* @Company: 创智和宇
**/
@Slf4j
@HsafRestPath("/service/inpt/medical")
@Service("medicalAdviceService_provider")
public class MedicalAdviceServiceImpl extends HsafService implements MedicalAdviceService {

    @Resource
    private MedicalAdviceBO medicalAdviceBO;

    /**
     * @Method: getMedicalAdvices
     * @Description: 获取未核收医嘱列表
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/9 11:18
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> getMedicalAdvices(Map map) {
        return WrapperResponse.success(medicalAdviceBO.getMedicalAdvices(MapUtils.get(map,"medicalAdviceDTO")));
    }

    /**
     * @Method: acceptMedicalAdvices
     * @Description: 医嘱核收
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/9 14:30
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> acceptMedicalAdvices(Map map) {
        return WrapperResponse.success(medicalAdviceBO.modifyAcceptMedicalAdvices(MapUtils.get(map,"medicalAdviceDTO")));
    }

    /**
     * @Method: refuseMedicalAdvices
     * @Description: 医嘱拒收
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/9 14:13
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> refuseMedicalAdvices(Map map) {
        return WrapperResponse.success(medicalAdviceBO.modifyRefuseMedicalAdvices(MapUtils.get(map,"medicalAdviceDTO")));
    }

    /**
     * @Method: longCost
     * @Description: 长期费用滚动
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/31 14:33
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> longCost(Map map) {
        return WrapperResponse.success(medicalAdviceBO.modifyLongCost(MapUtils.get(map,"medicalAdviceDTO")));
    }
    /**
     * @Method: updateAdviceInChecked
     * @Description: 修改医嘱信息，核收人，核对签名人，核收状态
     * isChecked: 0：未核收，1：已核对，2：已核收未核对，3：核对退回
     * @Param: [medicalAdviceDTO]
     * @Author: pengbo
     * @Date: 2022/08/24 16:25
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateAdviceInChecked(Map<String, Object> map) {
        return WrapperResponse.success(medicalAdviceBO.updateAdviceInChecked(MapUtils.get(map,"medicalAdviceDTO")));
    }

    @Override
    public WrapperResponse<PageDTO> getMedicalAdvicesNew(Map<String, Object> map) {
        return WrapperResponse.success(medicalAdviceBO.getMedicalAdvicesNew(MapUtils.get(map,"medicalAdviceDTO")));
    }
}
