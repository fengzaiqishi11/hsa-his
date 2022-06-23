package cn.hsa.module.insure.module.dao;

import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.insure.module.dto.InsureDiseaseDTO;
import cn.hsa.module.insure.module.dto.InsureDiseaseMatchDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import cn.hsa.module.interf.outpt.dto.Mzzd;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.insureItemMatch.dao
 * @Class_name: InsureDiseaseMatchDAO
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/11/7 10:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface InsureDiseaseMatchDAO {

    /**
     * @Method queryPageOrAll
     * @Desrciption 查询全部(可分页)
     * @Param
     * [insureDiseaseMatchDTO]
     * @Author liaojunjie
     * @Date   2020/12/1 11:37
     * @Return java.util.List<cn.hsa.module.insure.module.dto.InsureDiseaseMatchDTO>
     **/
    List<InsureDiseaseMatchDTO> queryPageOrAll(InsureDiseaseMatchDTO insureDiseaseMatchDTO);

    /**
     * @Method insertHospDisease
     * @Desrciption 疾病生成
     * @Param
     * [baseDiseaseDTOS]
     * @Author liaojunjie
     * @Date   2020/12/1 11:37
     * @Return java.lang.Boolean
     **/
    Boolean insertHospDisease(List<BaseDiseaseDTO> baseDiseaseDTOS);

    /**
     * @Method deleteHospDisease
     * @Desrciption 清空某医保机构联合医院生成的数据
     * @Param
     * [insureDiseaseMatchDTO]
     * @Author liaojunjie
     * @Date   2020/12/1 11:37
     * @Return java.lang.Boolean
     **/
    Boolean deleteHospDisease(InsureDiseaseMatchDTO insureDiseaseMatchDTO);

    Boolean updateInsureDiseaseMatchS(List<InsureDiseaseMatchDTO>insureDiseaseMatchDTOS);

    /**
     * @Menthod queryAll
     * @Desrciption  查询疾病诊断匹配信息
     * @param insureDiseaseMatchDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/12/10 11:27 
     * @Return java.util.List<cn.hsa.module.insure.module.dto.InsureDiseaseMatchDTO>
     */
    List<InsureDiseaseMatchDTO> queryAll(InsureDiseaseMatchDTO insureDiseaseMatchDTO);

    Boolean insertDiseaseMatchDAO(Map<String, Object> map);

    /**
     * @param
     * @Method cancelMatch
     * @Desrciption 1.首先根据 downLoadType 标志来区分：
     *             2.是取消疾病匹配，还是项目匹配
     * @Param
     * @Author fuhui
     * @Date 2021/1/30 14:10
     * @Return
     */
    Boolean deleteInsureMatch(Map<String,Object> map);

    /**
     * @Method insertMatchDisease
     * @Desrciption  自动匹配时候，批量新增匹配好的疾病数据
     * @Param insureDiseaseMatchDTOList
     *
     * @Author fuhui
     * @Date   2021/2/5 9:55
     * @Return  boolean
    **/
    Integer insertMatchDisease(@Param("insureDiseaseMatchDTOList") List<InsureDiseaseMatchDTO> insureDiseaseMatchDTOList);

    /**
     * @Method queryDiseaseIsMatch
     * @Desrciption  查询该疾病是否已经匹配
     * @Param  insureIllnessCode： 医保中心ICD编码  insureRegCode：医保注册编码 hospCode :医院编码 insureIllnessCode 医保中心ICD编码
     *
     * @Author fuhui
     * @Date   2021/2/5 9:51
     * @Return InsureDiseaseMatchDTO
    **/
    List<InsureDiseaseMatchDTO> queryDiseaseIsMatch(Map<String, Object> map);

    /**
     * @Method queryInptDiseaseInfoByVisitId
     * @Desrciption 获取住院诊断信息
     * @Param [paramsMap]
     * @Author 廖继广
     * @Date  2021-03-15
     * @Return java.util.map
     **/
    List<Map<String, Object>> queryInptDiseaseInfoByVisitId(Map<String, Object> paramsMap);

    /**
     * @param insureDiseaseDTO
     * @Method updateDisease
     * @Desrciption 修改医保疾病匹配信息
     * @Param diseaseDTO
     * @Author fuhui
     * @Date 2021/4/8 17:05
     * @Return Boolean
     */
    Boolean updateDisease(InsureDiseaseMatchDTO insureDiseaseDTO);

    /**
     * @param insureDiseaseDTO
     * @Method updateDisease
     * @Desrciption 删除医保疾病匹配信息
     * @Param diseaseDTO
     * @Author fuhui
     * @Date 2021/4/8 17:05
     * @Return Boolean
     */
    Boolean deleteInsureDisease(InsureDiseaseMatchDTO insureDiseaseDTO);

    /**
     * @Method insertDiseaseHandler
     * @Desrciption  医保统一支付：疾病类型的手动匹配
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/10 2:18
     * @Return
     **/
    void insertDiseaseMatch(InsureDiseaseMatchDTO insureDiseaseMatchDTO);


    List<InsureDiseaseMatchDTO> selectById(InsureDiseaseMatchDTO insureDiseaseMatchDTO);

    /**
     * @param insureDiseaseDTO
     * @Method queryPageInsureDisease
     * @Desrciption 查询医保的病种编码
     * @Param insureDiseaseDTO
     * @Author fuhui
     * @Date 2021/5/19 17:33
     * @Return
     */
    List<InsureDiseaseDTO> queryPageInsureDisease(InsureDiseaseDTO insureDiseaseDTO);

    /**
     * 查询所有未匹配审核的疾病信息
     * @param insureDiseaseMatchDTO
     * @return
     */
    List<Map<String, Object>> queryUnMacthAllPage(InsureDiseaseMatchDTO insureDiseaseMatchDTO);

    int deleteInsureDiseaseInfos(InsureDiseaseMatchDTO insureDiseaseMatchDTO);

    /**
     * @param baseDiseaseDTO
     * @Method updateBaseDisease
     * @Desrciption 回写是否匹配状态到基础疾病信息表
     * @Param baseDiseaseDTO
     * @Author liuliyun
     * @Date 2022/03/21
     * @Return Boolean
     */
    int updateBaseDisease(BaseDiseaseDTO baseDiseaseDTO);

    /**
     * @Author 产品二部-郭来
     * @Date 2022-03-30 9:47
     * @param map
     * @return cn.hsa.module.insure.module.dto.InsureDiseaseMatchDTO
     */
    List<InsureDiseaseMatchDTO> selectByHospIcdCode(Map map);

    /**
     * @Author 产品二部-郭来
     * @Date 2022-03-30 9:47
     * @param insureDiseaseMatchDTO
     * @return cn.hsa.module.insure.module.dto.InsureDiseaseMatchDTO
     */
    List<InsureDiseaseMatchDTO> selectByCode(InsureDiseaseMatchDTO insureDiseaseMatchDTO);
}
