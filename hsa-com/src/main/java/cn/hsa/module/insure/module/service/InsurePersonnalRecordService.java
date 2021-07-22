package cn.hsa.module.insure.module.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureDiseaseRecordDTO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.module.service
 * @class_name: InsurePersonnalRecordService
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/3/30 16:04
 * @Company: 创智和宇
 **/
@FeignClient(value = "hsa-insure")
public interface InsurePersonnalRecordService {

    /**
     * @Method deleteInsureDiseaseRecord
     * @Desrciption  医保统一支付平台：慢特病备案撤销
     * @Param insureDiseaseRecordDTO
     *
     * @Author fuhui
     * @Date   2021/3/30 16:13
     * @Return Boolean
     **/
    WrapperResponse<Boolean> deleteInsureDiseaseRecord(Map map);

    /**
     * @Method insertInsureDiseaseRecord
     * @Desrciption  医保统一支付平台：慢特病备案
     * @Param insureDiseaseRecordDTO
     *
     * @Author fuhui
     * @Date   2021/3/30 16:13
     * @Return Boolean
     **/
    WrapperResponse<Boolean> insertInsureDiseaseRecord(Map map);

    /**
     * @Method queryAllInsureDiseaseRecord
     * @Desrciption  医保统一支付平台：慢特病备查查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/30 16:32
     * @Return
     **/
    WrapperResponse<PageDTO> queryPageInsureDiseaseRecord(Map<String, Object> map);

    /**
     * @Method insertInsureFixRecord
     * @Desrciption  医保统一支付平台：新增人员定点备案
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/30 17:36
     * @Return
     **/
    WrapperResponse<Boolean> insertInsureFixRecord(Map<String, Object> map);

    /**
     * @Method insertInsureFixRecord
     * @Desrciption  医保统一支付平台：撤销人员定点备案
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/30 17:36
     * @Return
     **/
    WrapperResponse<Boolean> deleteInsureFixRecord(Map<String, Object> map);

    /**
     * @Method queryAllInsureDiseaseRecord
     * @Desrciption  医保统一支付平台：慢特病备查查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/30 16:32
     * @Return
     **/
    WrapperResponse<PageDTO> queryPageInsureFixRecord(Map<String, Object> map);

    /**
     * @Method queryPageInptRecord
     * @Desrciption  住院备案查询
     * @Param insureInptRecordDTO
     *
     * @Author fuhui
     * @Date   2021/4/7 10:26
     * @Return PageDTO
     **/
    WrapperResponse<PageDTO> queryPageInptRecord(Map<String, Object> map);

    /**
     * @Method insertInptRecord
     * @Desrciption  新增人员转院备案
     * @Param insureInptRecordDTO
     *
     * @Author fuhui
     * @Date   2021/4/7 10:30
     * @Return Boolean
     **/
    WrapperResponse<Boolean> insertInptRecord(Map<String, Object> map);

    /**
     * @Method deleteInptRecord
     * @Desrciption  医保统一支付平台：转院备案撤销
     * @Param insureInptRecordDTO
     *
     * @Author fuhui
     * @Date   2021/3/30 16:13
     * @Return Boolean
     **/
    WrapperResponse<Boolean> deleteInptRecord(Map<String, Object> map);

    /**
     * @Description: 门诊两病备案
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangxuan
     * @Date: 2021-04-29
     */
    WrapperResponse<Boolean> insertOuptTowDiseRecord(Map map);

    /**
     * @Description: 门诊两病备案撤销
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangxuan
     * @Date: 2021-04-29
     */
    WrapperResponse<Boolean> deleteOutptTwoDiseRecord(Map map);

    /**
     * @Description: 门诊两病备案查询
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.insure.module.dto.InsureDiseaseRecordDTO>
     * @Author: zhangxuan
     * @Date: 2021-04-29
     */
    WrapperResponse<PageDTO> queryPageOutptTwoDiseRecord(Map map);

    /**
     * @Description: 门诊两病备案查询
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     * @Author: zhangxuan
     * @Date: 2021-04-30
     */
    WrapperResponse<Map<String, Object>> queryOutptTwoDiseInfo(Map<String, Object> map);
}
