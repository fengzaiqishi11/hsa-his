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

    /**
     * @Method insertSpecialOutptRecord
     * @Desrciption  江西省：门诊单病种备案
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/25 10:33
     * @Return
     **/
    WrapperResponse<Boolean> insertSpecialOutptRecord(Map<String, Object> map);

    /**
     * @Method insertSpecialOutptRecord
     * @Desrciption  江西省：门诊单病种备案撤销
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/25 10:33
     * @Return
     **/
    WrapperResponse<Boolean> deleteSpecialOutptRecord(Map<String, Object> map);

    /**
     * @Method querySpecialOutptRecord
     * @Desrciption  江西省：门诊单病种备案登记查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/25 10:33
     * @Return
     **/
    WrapperResponse<PageDTO> querySpecialOutptRecord(Map<String, Object> map);

    /**
     * @Method queryPageSpecialRecord
     * @Desrciption  江西省：门诊单病种备案分页查询（his）
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/29 10:24
     * @Return
     **/
    WrapperResponse<PageDTO> queryPageSpecialRecord(Map<String, Object> map);

    /**
     * @Author 医保二部-张金平
     * @Date 2022-05-07 10:27
     * @Description 人员意外伤害备案
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     */
    WrapperResponse<PageDTO> queryPageInsureAccidentInjureRecord(Map<String, Object> map);
    /**
     * @Author 医保二部-张金平
     * @Date 2022-05-07 10:27
     * @Description 人员意外伤害备案
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     */
    WrapperResponse<Boolean> insertInsureAccidentInjureRecord(Map map);
}
