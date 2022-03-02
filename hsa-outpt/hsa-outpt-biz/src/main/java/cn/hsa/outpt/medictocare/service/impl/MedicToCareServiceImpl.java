package cn.hsa.outpt.medictocare.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.medictocare.bo.MedicToCareBO;
import cn.hsa.module.outpt.medictocare.service.MedicToCareService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author powersi
 * @create 2022-02-28 15:48
 * @desc
 **/
@HsafRestPath("/service/outpt/medicToCare")
@Slf4j
@Service("medicToCareService_provider")
public class MedicToCareServiceImpl implements MedicToCareService {

    @Resource
    private MedicToCareBO medicToCareBO;
    /**
     * @Menthod: queryPage()
     * @Desrciption: 根据条件分页查询医转养患者列表
     * @Param: outptInfusionRegisterDTO 门诊输液登记传输对象
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @date 2022/2/28 9:38
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        PageDTO medicToCareDTO = medicToCareBO.queryPage(MapUtils.get(map, "medicToCareDTO"));
        return WrapperResponse.success(medicToCareDTO);
    }

    @Override
    public WrapperResponse<PageDTO> queryHospitalPatientInfoPage(Map map) {
        return null;
    }

    /**
     * @Menthod getMedicToCareInfoById()
     * @Desrciption   根据主键id查询申请明细详细信息
     * @Param medicToCareDTO
     * @Author yuelong.chen
     * @Date   2022/2/28 9:38
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<MedicToCareDTO>
     **/
    @Override
    public WrapperResponse<Map<String, Object>> getMedicToCareInfoById(Map<String, Object> map) {
        Map<String, Object> medicToCareInfoById = medicToCareBO.getMedicToCareInfoById(map);
        return WrapperResponse.success(medicToCareInfoById);
    }


    @Override
    public WrapperResponse<PageDTO> queryMedicToCareInfoPage(Map map) {
        PageDTO medicToCareDTO = medicToCareBO.queryMedicToCareInfoPage(MapUtils.get(map, "medicToCareDTO"));
        return WrapperResponse.success(medicToCareDTO);
    }
    /**
     * @Menthod: insertMedicToCare()
     * @Desrciption: 医转养申请
     * @Param: medicToCareDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022/2/28 9:38
     * @Return: Boolean
     **/
    @Override
    public WrapperResponse<Boolean> insertMedicToCare(Map map) {
        return WrapperResponse.success(medicToCareBO.insertMedicToCare(MapUtils.get(map, "medicToCareDTO")));
    }

    @Override
    public WrapperResponse<Boolean> updateMedicToCare(Map map) {
        return null;
    }
}