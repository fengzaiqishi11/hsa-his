package cn.hsa.module.insure.module.dao;

import cn.hsa.module.insure.module.dto.*;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.module.dao
 * @class_name: InsureDiseaseRecordDAO
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/3/30 14:23
 * @Company: 创智和宇
 **/
public interface InsureDiseaseRecordDAO {

    /**
     * @Method insertRecordDisease
     * @Desrciption 医保统一支付平台：新增人员慢特病备案
     * @Param insureDiseaseRecordDTO
     *
     * @Author fuhui
     * @Date   2021/3/30 14:41
     * @Return
    **/
    void insertRecordDisease(InsureDiseaseRecordDTO insureDiseaseRecordDTO);

    /**
     * @Method insertRecordDisease
     * @Desrciption 医保统一支付平台：撤销人员慢特病备案
     * @Param insureDiseaseRecordDTO
     *
     * @Author fuhui
     * @Date   2021/3/30 14:41
     * @Return
     **/
    void deleteRecordDisease(InsureDiseaseRecordDTO insureDiseaseRecordDTO);

    /**
     * @Method insertInsureFixPersonnalRecord
     * @Desrciption  医保统一支付平台：人员定点备案
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/30 15:26
     * @Return
    **/
    void insertInsureFixPersonnalRecord(InsureFixPersonnalRecordDTO fixPersonnalRecordDTO);

    /**
     * @Method insertInsureFixPersonnalRecord
     * @Desrciption  医保统一支付平台：人员定点备案撤销
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/30 15:26
     * @Return
     **/
    void deleteInsureFixPersonnalRecord(InsureFixPersonnalRecordDTO fixPersonnalRecordDTO);

    List<InsureDiseaseRecordDTO> queryPage(InsureDiseaseRecordDTO diseaseRecordDTO);

    /**
     * @param fixPersonnalRecordDTO
     * @Method queryAllInsureDiseaseRecord
     * @Desrciption 医保统一支付平台：慢特病备查查询
     * @Param
     * @Author fuhui
     * @Date 2021/3/30 16:32
     * @Return
     */
    List<InsureFixPersonnalRecordDTO> queryPageInsureFixRecord(InsureFixPersonnalRecordDTO fixPersonnalRecordDTO);

    /**
     * @Method getById
     * @Desrciption  查询患者的备案信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/1 17:31
     * @Return
    **/
    InsureDiseaseRecordDTO getById(InsureDiseaseRecordDTO insureDiseaseRecordDTO);

    /**
     * @Method getFixRecordById
     * @Desrciption  根据就诊id 查询人员定点备案信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/2 9:19
     * @Return
    **/
    InsureFixPersonnalRecordDTO getFixRecordById(InsureFixPersonnalRecordDTO fixPersonnalRecordDTO);

    /**
     * @Method insertInptRecord
     * @Desrciption 医保统一支付平台： 人员定点备案新增
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/7 9:58
     * @Return
    **/
    void insertInptRecord(InsureInptRecordDTO insureInptRecordDTO);

    /**
     * @Method getInptRecordById
     * @Desrciption  医保统一支付平台：根据申报流水号查找转院备案信息
     * @Param inptRecordDTO
     *
     * @Author fuhui
     * @Date   2021/4/7 10:04
     * @Return InsureInptRecordDTO
    **/
    InsureInptRecordDTO getInptRecordById(InsureInptRecordDTO inptRecordDTO);

    /**
     * @Method getInptRecordById
     * @Desrciption  医保统一支付平台：删除转院备案信息
     * @Param inptRecordDTO
     *
     * @Author fuhui
     * @Date   2021/4/7 10:04
     * @Return InsureInptRecordDTO
     **/
    void deleteInsureInptRecord(InsureInptRecordDTO inptRecordDTO);
    
    /**
     * @Method queryPageInptRecord
     * @Desrciption  查询转院备案信息
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/4/7 11:35 
     * @Return 
    **/
    List<InsureInptRecordDTO> queryPageInptRecord(InsureInptRecordDTO insureInptRecordDTO);

    /**
     * @Method queryInptRecordNo
     * @Desrciption  查询人员定点备案排序号
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/7 16:57
     * @Return
    **/
    String queryInptRecordNo(InsureFixPersonnalRecordDTO fixPersonnalRecordDTO);

    /**
     * @Description: 门诊两病备案查询
     * @Param: [insureDiseaseRecordDTO]
     * @return: java.util.List<cn.hsa.module.insure.module.dto.InsureDiseaseRecordDTO>
     * @Author: zhangxuan
     * @Date: 2021-04-29
     */
    List<InsureDiseaseRecordDTO> queryPageOutptTwoDiseRecord(InsureDiseaseRecordDTO insureDiseaseRecordDTO);

    /**
     * @Description: 门诊两病备案
     * @Param: [insureDiseaseRecordDTO]
     * @return: cn.hsa.module.insure.module.dto.InsureDiseaseRecordDTO
     * @Author: zhangxuan
     * @Date: 2021-04-29
     */
    int insertOuptTowDiseRecord(InsureDiseaseRecordDTO insureDiseaseRecordDTO);

    /**
     * @Description: 门诊两病备案撤销
     * @Param: [insureDiseaseRecordDTO]
     * @return: cn.hsa.module.insure.module.dto.InsureDiseaseRecordDTO
     * @Author: zhangxuan
     * @Date: 2021-04-30
     */
    int deleteOuptTwoDiseRecord(InsureDiseaseRecordDTO insureDiseaseRecordDTO);

    /**
     * @Description: 查询备案信息
     * @Param: [insureDiseaseRecordDTO]
     * @return: cn.hsa.module.insure.module.dto.InsureDiseaseRecordDTO
     * @Author: zhangxuan
     * @Date: 2021-04-30
     */
    InsureDiseaseRecordDTO getByIdTwoDise(InsureDiseaseRecordDTO insureDiseaseRecordDTO);

    /**
     * @Method insertSpecialOutptRecord
     * @Desrciption  保留门诊单病种备案新
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/25 11:19
     * @Return
    **/
    void insertSpecialOutptRecord(Map<String, Object> map);

    /**
     * @param map
     * @Method insertSpecialOutptRecord
     * @Desrciption 江西省：门诊单病种备案撤销
     * @Param
     * @Author fuhui
     * @Date 2021/11/25 10:33
     * @Return
     */
    void deleteSpecialOutptRecord(Map<String, Object> map);

    /**
     * @param insureSpecialRecordDTO
     * @Method queryPage
     * @Desrciption 江西省：门诊单病种备案分页查询（his）
     * @Param
     * @Author fuhui
     * @Date 2021/11/29 10:24
     * @Return
     */
    List<InsureSpecialRecordDTO> queryPageSpecialRecord(InsureSpecialRecordDTO insureSpecialRecordDTO);

    /**
     * @Author 医保二部-张金平
     * @Date 2022-05-07 10:34
     * @Description 人员意外伤害备案
     * @param insureAccidentalInjuryDTO
     * @return java.util.List<cn.hsa.module.insure.module.dto.InsureAccidentalInjuryDTO>
     */
    List<InsureAccidentalInjuryDTO> queryPageInsureAccidentInjureRecord(InsureAccidentalInjuryDTO insureAccidentalInjuryDTO);

    void insertInsureNationDrug(InsureAccidentalInjuryDTO insureAccidentalInjuryDTO);

    void insertRecordAccidentInjury(InsureAccidentalInjuryDTO insureAccidentalInjuryDTO);
}
