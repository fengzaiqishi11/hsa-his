package cn.hsa.module.insure.module.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.module.dto.InsureAccidentalInjuryDTO;
import cn.hsa.module.insure.module.dto.InsureDiseaseRecordDTO;
import cn.hsa.module.insure.module.dto.InsureInptRecordDTO;
import cn.hsa.module.insure.module.dto.InsureSpecialRecordDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.module.bo
 * @class_name: InsurePersonnalRecordBO
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/3/30 16:04
 * @Company: 创智和宇
 **/
public interface InsurePersonnalRecordBO {

    /**
     * @param
     * @Method insertInsureDiseaseRecord
     * @Desrciption 医保统一支付平台：慢特病备案
     * @Param insureDiseaseRecordDTO
     * @Author fuhui
     * @Date 2021/3/30 16:13
     * @Return Boolean
     */
    Boolean insertInsureDiseaseRecord(InsureDiseaseRecordDTO insureDiseaseRecordDTO);

    /**
     * @Method deleteInsureDiseaseRecord
     * @Desrciption  医保统一支付平台：慢特病备案撤销
     * @Param insureDiseaseRecordDTO
     *
     * @Author fuhui
     * @Date   2021/3/30 16:13
     * @Return Boolean
     **/
    Boolean deleteInsureDiseaseRecord(InsureDiseaseRecordDTO insureDiseaseRecordDTO);

    /**
     * @param map
     * @Method queryAllInsureDiseaseRecord
     * @Desrciption 医保统一支付平台：慢特病备查查询
     * @Param
     * @Author fuhui
     * @Date 2021/3/30 16:32
     * @Return
     */
    PageDTO queryPageInsureDiseaseRecord(Map<String, Object> map);


    /**
     * @param map
     * @Method insertInsureFixRecord
     * @Desrciption 医保统一支付平台：新增人员定点备案
     * @Param
     * @Author fuhui
     * @Date 2021/3/30 17:36
     * @Return
     */
    Boolean insertInsureFixRecord(Map<String, Object> map);

    /**
     * @param map
     * @Method insertInsureFixRecord
     * @Desrciption 医保统一支付平台：撤销人员定点备案
     * @Param
     * @Author fuhui
     * @Date 2021/3/30 17:36
     * @Return
     */
    Boolean deleteInsureFixRecord(Map<String, Object> map);

    /**
     * @param map
     * @Method queryAllInsureDiseaseRecord
     * @Desrciption 医保统一支付平台：慢特病备查查询
     * @Param
     * @Author fuhui
     * @Date 2021/3/30 16:32
     * @Return
     */
    PageDTO queryPageInsureFixRecord(Map<String, Object> map);

    /**
     * @param insureInptRecordDTO
     * @Method queryPageInptRecord
     * @Desrciption 住院备案查询
     * @Param insureInptRecordDTO
     * @Author fuhui
     * @Date 2021/4/7 10:26
     * @Return PageDTO
     */
    PageDTO queryPageInptRecord(InsureInptRecordDTO insureInptRecordDTO);

    /**
     * @param insureInptRecordDTO
     * @Method insertInptRecord
     * @Desrciption 新增人员转院备案
     * @Param insureInptRecordDTO
     * @Author fuhui
     * @Date 2021/4/7 10:30
     * @Return Boolean
     */
    Boolean insertInptRecord(InsureInptRecordDTO insureInptRecordDTO);

    /**
     * @param insureInptRecordDTO
     * @Method deleteInptRecord
     * @Desrciption 医保统一支付平台：转院备案撤销
     * @Param insureInptRecordDTO
     * @Author fuhui
     * @Date 2021/3/30 16:13
     * @Return Boolean
     */
    Boolean deleteInptRecord(InsureInptRecordDTO insureInptRecordDTO);

    /**
     * @Description: 门诊两病备案
     * @Param: [insureDiseaseRecordDTO]
     * @return: java.lang.Boolean
     * @Author: zhangxuan
     * @Date: 2021-04-29
     */
    Boolean insertOuptTowDiseRecord(InsureDiseaseRecordDTO insureDiseaseRecordDTO);

    /**
     * @Description: 门诊两病备案撤销
     * @Param: [insureDiseaseRecordDTO]
     * @return: java.lang.Boolean
     * @Author: zhangxuan
     * @Date: 2021-04-29
     */
    Boolean deleteOutptTwoDiseRecord(InsureDiseaseRecordDTO insureDiseaseRecordDTO);

    /**
     * @Description: 门诊两病备案查询
     * @Param: [insureDiseaseRecordDTO]
     * @return: cn.hsa.base.PageDTO
     * @Author: zhangxuan
     * @Date: 2021-04-29
     */
    PageDTO queryPageOutptTwoDiseRecord(InsureDiseaseRecordDTO insureDiseaseRecordDTO);

    /**
     * @Description: 门诊两病信息查询
     * @Param: [map]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: zhangxuan
     * @Date: 2021-04-30
     */
    Map<String, Object> queryOutptTwoDiseInfo(Map<String, Object> map);

    /**
     * @param map
     * @Method insertSpecialOutptRecord
     * @Desrciption 江西省：门诊单病种备案
     * @Param
     * @Author fuhui
     * @Date 2021/11/25 10:33
     * @Return
     */
    Boolean insertSpecialOutptRecord(Map<String, Object> map);

    /**
     * @param map
     * @Method insertSpecialOutptRecord
     * @Desrciption 江西省：门诊单病种备案撤销
     * @Param
     * @Author fuhui
     * @Date 2021/11/25 10:33
     * @Return
     */
    Boolean deleteSpecialOutptRecord(Map<String, Object> map);

    /**
     * @param map
     * @Method querySpecialOutptRecord
     * @Desrciption 江西省：门诊单病种备案登记查询
     * @Param
     * @Author fuhui
     * @Date 2021/11/25 10:33
     * @Return
     */
    PageDTO querySpecialOutptRecord(Map<String, Object> map);

    /**
     * @param insureSpecialRecordDTO
     * @Method queryPage
     * @Desrciption 江西省：门诊单病种备案分页查询（his）
     * @Param
     * @Author fuhui
     * @Date 2021/11/29 10:24
     * @Return
     */
    PageDTO queryPageSpecialRecord(InsureSpecialRecordDTO insureSpecialRecordDTO);

    /**
     * @Author 医保二部-张金平
     * @Date 2022-05-07 10:33
     * @Description 人员意外伤害备案
     * @param insureAccidentalInjuryDTO
     * @return cn.hsa.base.PageDTO
     */
    PageDTO queryPageInsureAccidentInjureRecord(InsureAccidentalInjuryDTO insureAccidentalInjuryDTO);
    /**
     * @Author 医保二部-张金平
     * @Date 2022-05-07 10:27
     * @Description 人员意外伤害备案
     * @param insureAccidentalInjuryDTO
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     */
    Boolean insertInsureAccidentInjureRecord(InsureAccidentalInjuryDTO insureAccidentalInjuryDTO);
}
