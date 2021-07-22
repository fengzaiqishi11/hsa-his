package cn.hsa.module.insure.module.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.module.dto.InsureDiseaseRecordDTO;
import cn.hsa.module.insure.module.dto.InsureInptRecordDTO;

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

}
