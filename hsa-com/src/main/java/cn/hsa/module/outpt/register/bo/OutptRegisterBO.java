package cn.hsa.module.outpt.register.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyCostDTO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO;
import cn.hsa.module.outpt.queue.dto.OutptDoctorQueueDto;
import cn.hsa.module.outpt.register.dto.OutptRegisterDTO;

import java.util.List;
import java.util.Map;


/**
 * @Package_name: cn.hsa.module.base.outpo.register.bo
 * @Class_name: OutpoRegisterBO
 * @Describe: 门诊挂号实现层接口
 * @Author: liaojiguang
 * @Eamil: jiguang.liao@powersi.com.cn
 * @Date: 2020/8/10 17:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptRegisterBO {
    /**
     * @Menthod queryRegisterInfoByParamsPage
     * @Desrciption 根据参数获取挂号信息-用于退号列表展示
     * @param outpoRegisterDTO  入参
     * @Author liaojiguang
     * @Date   2020/8/10 17:40
     * @Return cn.hsa.module.base.outpo.register.dto.OutpoRegisterDTO
     **/
    PageDTO queryRegisterInfoByParamsPage(OutptRegisterDTO outpoRegisterDTO);

    /**
     * @Menthod updateOutRegister
     * @Desrciption 门诊退号
     * @Param [map] 挂号id,hospCode
     * @Author lioajiguang
     * @Date   2020/8/11 11:41:23
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean> 成功/失败
     **/
    Boolean updateOutRegister(Map map);

    /**
     * @Method outRegisterSettle
     * @Desrciption 门诊挂号结算
       @params [map]
     * @Author chenjun
     * @Date   2020/8/18 15:43
     * @Return java.lang.Boolean
    **/
	Map<String, String> saveOutRegisterSettle(Map map);

    /**
     * @Method queryOutptDoctorList
     * @Desrciption 查询挂号类别关联的医生队列信息
       @params [outptClassifyDTO]
     * @Author chenjun
     * @Date   2020/8/20 15:26
     * @Return java.util.List<cn.hsa.module.outpt.queue.dto.OutptDoctorQueueDto>
    **/
    List<OutptDoctorQueueDto> queryOutptDoctorList(OutptClassifyDTO outptClassifyDTO);

    /**
     * @Method queryRegisterCostList
     * @Desrciption 查询挂号类别关联的费用
       @params [outptClassifyDTO]
     * @Author chenjun
     * @Date   2020/8/20 15:27
     * @Return java.util.List<cn.hsa.module.outpt.outptclassify.dto.OutptClassifyCostDTO>
    **/
    List<OutptClassifyCostDTO> queryRegisterCostList(OutptClassifyDTO outptClassifyDTO);

    List<BaseDeptDTO> queryOutptDeptClassify(OutptClassifyDTO outptClassifyDTO);

    /**
     * @Method updateCostPreferential
     * @Desrciption 处理优惠金额
       @params [map]
     * @Author chenjun
     * @Date   2020/9/4 11:36
     * @Return java.lang.Boolean
    **/
    List<OutptClassifyCostDTO> updateCostPreferential(Map map);

    List<OutptRegisterDTO> queryRegisterInfoByCertno(Map<String, Object> map);

   OutptRegisterDTO  getOutptRegisterByVisitId(Map<String, Object> map);
}
