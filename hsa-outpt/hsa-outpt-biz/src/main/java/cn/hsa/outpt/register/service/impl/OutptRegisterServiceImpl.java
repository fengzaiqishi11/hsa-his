package cn.hsa.outpt.register.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyCostDTO;
import cn.hsa.module.outpt.queue.dto.OutptDoctorQueueDto;
import cn.hsa.module.outpt.register.bo.OutptRegisterBO;
import cn.hsa.module.outpt.register.dto.OutptRegisterDTO;
import cn.hsa.module.outpt.register.service.OutptRegisterService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.register.service.impl
 * @Class_name: OutptRegisterServiceImpl
 * @Describe: 门诊挂号Service实现层
 * @Author: liaojiguang
 * @Eamil: jiguang.liao@powersi.com.cn
 * @Date: 2020/8/11 10:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/outpt/register")
@Slf4j
@Service("outptRegisterService_provider")
public class OutptRegisterServiceImpl extends HsafService implements OutptRegisterService {

    @Resource
    OutptRegisterBO outptRegisterBO;

    /**
     * @Menthod queryRegisterInfoByParamsPage
     * @Desrciption   根据参数获取挂号信息
     * @param map
     * @Author liaojiguang
     * @Date   2020/8/11 11:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryRegisterInfoByParamsPage(Map<String, Object> map) {
        PageDTO pageDTO = outptRegisterBO.queryRegisterInfoByParamsPage(MapUtils.get(map,"outptRegisterDTO"));
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Menthod updateOutRegister
     * @Desrciption 门诊退号
     * @Param [map] 挂号id,hospCode
     * @Author lioajiguang
     * @Date   2020/8/11 11:41:23
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean> 成功/失败
     **/
    @Override
    public WrapperResponse<Boolean> updateOutRegister(Map map) {
        return WrapperResponse.success(outptRegisterBO.updateOutRegister(map));
    }

    @Override
    public WrapperResponse<Map<String, String>> saveOutRegisterSettle(Map map) {
        return WrapperResponse.success(outptRegisterBO.saveOutRegisterSettle(map));
    }

    @Override
    public WrapperResponse<List<OutptDoctorQueueDto>> queryOutptDoctorList(Map map) {
        List<OutptDoctorQueueDto> list = outptRegisterBO.queryOutptDoctorList(MapUtils.get(map,"outptClassifyDTO"));
        return WrapperResponse.success(list);
    }

    @Override
    public WrapperResponse<List<OutptClassifyCostDTO>> queryRegisterCostList(Map map) {
        List<OutptClassifyCostDTO> list = outptRegisterBO.queryRegisterCostList(MapUtils.get(map,"outptClassifyDTO"));
        return WrapperResponse.success(list);
    }

    @Override
    public WrapperResponse<List<BaseDeptDTO>> queryOutptDeptClassify(Map map) {
        List<BaseDeptDTO> list = outptRegisterBO.queryOutptDeptClassify(MapUtils.get(map,"outptClassifyDTO"));
        return WrapperResponse.success(list);
    }

    @Override
    public WrapperResponse<List<OutptClassifyCostDTO>> updateCostPreferential(Map map) {
        return WrapperResponse.success(outptRegisterBO.updateCostPreferential(map));
    }

    @Override
    public List<OutptRegisterDTO> queryRegisterInfoByCertno(Map<String, Object> map) {
        return outptRegisterBO.queryRegisterInfoByCertno(map);
    }

    @Override
    public  OutptRegisterDTO  getOutptRegisterByVisitId(Map<String, Object> map) {
        return outptRegisterBO.getOutptRegisterByVisitId(map);
    }
}
