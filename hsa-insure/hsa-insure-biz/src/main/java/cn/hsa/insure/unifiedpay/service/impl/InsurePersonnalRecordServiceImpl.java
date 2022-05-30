package cn.hsa.insure.unifiedpay.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.bo.InsurePersonnalRecordBO;
import cn.hsa.module.insure.module.dto.InsureAccidentalInjuryDTO;
import cn.hsa.module.insure.module.dto.InsureSpecialRecordDTO;
import cn.hsa.module.insure.module.service.InsurePersonnalRecordService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.service.impl
 * @class_name: InsurePersonnalRecordServiceImpl
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/3/30 16:07
 * @Company: 创智和宇
 **/

@Slf4j
@HsafRestPath("/service/insure/insurePersonnalRecord")
@Service("insurePersonnalRecordService_provider")
public class InsurePersonnalRecordServiceImpl extends HsafService implements InsurePersonnalRecordService {

    @Resource
    private InsurePersonnalRecordBO insurePersonnalRecordBO;
    /**
     * @param map
     * @Method deleteInsureDiseaseRecord
     * @Desrciption 医保统一支付平台：慢特病备案撤销
     * @Param insureDiseaseRecordDTO
     * @Author fuhui
     * @Date 2021/3/30 16:13
     * @Return Boolean
     */
    @Override
    public WrapperResponse<Boolean> deleteInsureDiseaseRecord(Map map) {
        return WrapperResponse.success(insurePersonnalRecordBO.deleteInsureDiseaseRecord(MapUtils.get(map,"insureDiseaseRecordDTO")));
    }

    /**
     * @param map
     * @Method insertInsureDiseaseRecord
     * @Desrciption 医保统一支付平台：慢特病备案
     * @Param insureDiseaseRecordDTO
     * @Author fuhui
     * @Date 2021/3/30 16:13
     * @Return Boolean
     */
    @Override
    public WrapperResponse<Boolean> insertInsureDiseaseRecord(Map map) {
        return WrapperResponse.success(insurePersonnalRecordBO.insertInsureDiseaseRecord(MapUtils.get(map,"insureDiseaseRecordDTO")));
    }

    /**
     * @param map
     * @Method queryAllInsureDiseaseRecord
     * @Desrciption 医保统一支付平台：慢特病备查查询
     * @Param
     * @Author fuhui
     * @Date 2021/3/30 16:32
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryPageInsureDiseaseRecord(Map<String, Object> map) {
        return WrapperResponse.success(insurePersonnalRecordBO.queryPageInsureDiseaseRecord(map));
    }

    /**
     * @param map
     * @Method insertInsureFixRecord
     * @Desrciption 医保统一支付平台：新增人员定点备案
     * @Param
     * @Author fuhui
     * @Date 2021/3/30 17:36
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> insertInsureFixRecord(Map<String, Object> map) {
        return WrapperResponse.success(insurePersonnalRecordBO.insertInsureFixRecord(map));
    }

    /**
     * @param map
     * @Method insertInsureFixRecord
     * @Desrciption 医保统一支付平台：撤销人员定点备案
     * @Param
     * @Author fuhui
     * @Date 2021/3/30 17:36
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> deleteInsureFixRecord(Map<String, Object> map) {
        return WrapperResponse.success(insurePersonnalRecordBO.deleteInsureFixRecord(map));
    }

    /**
     * @param map
     * @Method queryAllInsureDiseaseRecord
     * @Desrciption 医保统一支付平台：慢特病备查查询
     * @Param
     * @Author fuhui
     * @Date 2021/3/30 16:32
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryPageInsureFixRecord(Map<String, Object> map) {
        return WrapperResponse.success(insurePersonnalRecordBO.queryPageInsureFixRecord(map));
    }

    /**
     * @param map
     * @Method queryPageInptRecord
     * @Desrciption 住院备案查询
     * @Param insureInptRecordDTO
     * @Author fuhui
     * @Date 2021/4/7 10:26
     * @Return PageDTO
     */
    @Override
    public WrapperResponse<PageDTO> queryPageInptRecord(Map<String, Object> map) {
        return WrapperResponse.success(insurePersonnalRecordBO.queryPageInptRecord(MapUtils.get(map,"insureInptRecordDTO")));
    }

    /**
     * @param map
     * @Method insertInptRecord
     * @Desrciption 新增人员转院备案
     * @Param insureInptRecordDTO
     * @Author fuhui
     * @Date 2021/4/7 10:30
     * @Return Boolean
     */
    @Override
    public WrapperResponse<Boolean> insertInptRecord(Map<String, Object> map) {
        return WrapperResponse.success(insurePersonnalRecordBO.insertInptRecord(MapUtils.get(map,"insureInptRecordDTO")));
    }

    /**
     * @param map
     * @Method deleteInptRecord
     * @Desrciption 医保统一支付平台：转院备案撤销
     * @Param insureInptRecordDTO
     * @Author fuhui
     * @Date 2021/3/30 16:13
     * @Return Boolean
     */
    @Override
    public WrapperResponse<Boolean> deleteInptRecord(Map<String, Object> map) {
        return WrapperResponse.success(insurePersonnalRecordBO.deleteInptRecord(MapUtils.get(map,"insureInptRecordDTO")));
    }

    /**
     * @Description: 门诊两病备案
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangxuan
     * @Date: 2021-04-29
     */
    @Override
    public WrapperResponse<Boolean> insertOuptTowDiseRecord(Map map){
        return WrapperResponse.success(insurePersonnalRecordBO.insertOuptTowDiseRecord(MapUtils.get(map,"insureDiseaseRecordDTO")));
    }

    /**
     * @Description: 门诊两病备案撤销
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangxuan
     * @Date: 2021-04-29
     */
    @Override
    public WrapperResponse<Boolean> deleteOutptTwoDiseRecord(Map map){
        return WrapperResponse.success(insurePersonnalRecordBO.deleteOutptTwoDiseRecord(MapUtils.get(map,"insureDiseaseRecordDTO")));
    }

    /**
     * @Description: 门诊两病备案查询
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.insure.module.dto.InsureDiseaseRecordDTO>
     * @Author: zhangxuan
     * @Date: 2021-04-29
     */
    @Override
    public WrapperResponse<PageDTO> queryPageOutptTwoDiseRecord(Map map){
        return WrapperResponse.success(insurePersonnalRecordBO.queryPageOutptTwoDiseRecord(MapUtils.get(map,"insureDiseaseRecordDTO")));
    }

    /**
     * @Description: 门诊两病备案查询
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     * @Author: zhangxuan
     * @Date: 2021-04-30
     */
    @Override
    public WrapperResponse<Map<String, Object>> queryOutptTwoDiseInfo(Map<String, Object> map){
        return WrapperResponse.success(insurePersonnalRecordBO.queryOutptTwoDiseInfo(map));
    }

    /**
     * @param map
     * @Method insertSpecialOutptRecord
     * @Desrciption 江西省：门诊单病种备案
     * @Param
     * @Author fuhui
     * @Date 2021/11/25 10:33
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> insertSpecialOutptRecord(Map<String, Object> map) {
        return WrapperResponse.success(insurePersonnalRecordBO.insertSpecialOutptRecord(map));
    }

    /**
     * @param map
     * @Method insertSpecialOutptRecord
     * @Desrciption 江西省：门诊单病种备案撤销
     * @Param
     * @Author fuhui
     * @Date 2021/11/25 10:33
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> deleteSpecialOutptRecord(Map<String, Object> map) {
        return WrapperResponse.success(insurePersonnalRecordBO.deleteSpecialOutptRecord(map));
    }

    /**
     * @param map
     * @Method querySpecialOutptRecord
     * @Desrciption 江西省：门诊单病种备案登记查询
     * @Param
     * @Author fuhui
     * @Date 2021/11/25 10:33
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> querySpecialOutptRecord(Map<String, Object> map) {
        return WrapperResponse.success(insurePersonnalRecordBO.querySpecialOutptRecord(map));
    }

    /**
     * @param map
     * @Method queryPageSpecialRecord
     * @Desrciption 江西省：门诊单病种备案分页查询（his）
     * @Param
     * @Author fuhui
     * @Date 2021/11/29 10:24
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryPageSpecialRecord(Map<String, Object> map) {
        InsureSpecialRecordDTO insureSpecialRecordDTO = MapUtils.get(map,"insureSpecialRecordDTO");
        return WrapperResponse.success(insurePersonnalRecordBO.queryPageSpecialRecord(insureSpecialRecordDTO));
    }

    /**
     * @Author 医保二部-张金平
     * @Date 2022-05-07 10:32
     * @Description 人员意外伤害备案
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     */
    @Override
    public WrapperResponse<PageDTO> queryPageInsureAccidentInjureRecord(Map<String, Object> map) {
        InsureAccidentalInjuryDTO insureAccidentalInjuryDTO = MapUtils.get(map,"insureAccidentalInjuryDTO");
        return WrapperResponse.success(insurePersonnalRecordBO.queryPageInsureAccidentInjureRecord(insureAccidentalInjuryDTO));
    }
    /**
     * @Author 医保二部-张金平
     * @Date 2022-05-07 10:27
     * @Description 人员意外伤害备案
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     */
    @Override
    public WrapperResponse<Boolean> insertInsureAccidentInjureRecord(Map map) {
        return WrapperResponse.success(insurePersonnalRecordBO.insertInsureAccidentInjureRecord(MapUtils.get(map,"insureAccidentalInjuryDTO")));
    }
}
