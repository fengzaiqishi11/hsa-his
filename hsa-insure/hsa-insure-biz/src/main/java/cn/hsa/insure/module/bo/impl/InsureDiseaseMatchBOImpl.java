package cn.hsa.insure.module.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.Transpond;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.bd.service.BaseDiseaseService;
import cn.hsa.module.insure.module.bo.InsureDiseaseMatchBO;
import cn.hsa.module.insure.module.dao.InsureDiseaseMatchDAO;
import cn.hsa.module.insure.module.dto.InsureDiseaseDTO;
import cn.hsa.module.insure.module.dto.InsureDiseaseMatchDTO;
import cn.hsa.module.insure.module.entity.InsureDiseaseMatchDO;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.insure.module.bo.impl
 * @Class_name:: InsureDiseaseMatchBOImpl
 * @Description:
 * @Author: liaojunjie
 * @Date: 2020/11/9 16:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class InsureDiseaseMatchBOImpl extends HsafBO implements InsureDiseaseMatchBO {

    @Resource
    InsureDiseaseMatchDAO insureDiseaseMatchDAO;

    @Resource
    BaseDiseaseService baseDiseaseService;

    @Resource
    private Transpond transpond;

    private int count = 1;

    /**
     * @Method queryPage
     * @Desrciption 分页查询
     * @Param [insureDiseaseMatchDTO]
     * @Author liaojunjie
     * @Date 2020/11/7 11:11
     * @Return java.util.List<cn.hsa.module.insure.insureDiseaseMatch.dto.InsureDiseaseMatchDTO>
     **/
    @Override
    public PageDTO queryPage(InsureDiseaseMatchDTO insureDiseaseMatchDTO) {
        //设置分页信息
        PageHelper.startPage(insureDiseaseMatchDTO.getPageNo(), insureDiseaseMatchDTO.getPageSize());
        List<InsureDiseaseMatchDTO> insureDiseaseMatchDTOS = insureDiseaseMatchDAO.queryPageOrAll(insureDiseaseMatchDTO);
        return PageDTO.of(insureDiseaseMatchDTOS);
    }

    /**
     * @Method queryAll
     * @Desrciption 查询全部
     * @Param [insureDiseaseMatchDTO]
     * @Author liaojunjie
     * @Date 2020/12/1 11:36
     * @Return java.util.List<cn.hsa.module.insure.module.dto.InsureDiseaseMatchDTO>
     **/
    @Override
    public List<InsureDiseaseMatchDTO> queryAll(InsureDiseaseMatchDTO insureDiseaseMatchDTO) {
        List<InsureDiseaseMatchDTO> insureDiseaseMatchDTOS = insureDiseaseMatchDAO.queryPageOrAll(insureDiseaseMatchDTO);
        return insureDiseaseMatchDTOS;
    }

    @Override
    public List<InsureDiseaseMatchDO> queryAll(InsureDiseaseMatchDO insureDiseaseMatchDO) {
        List<InsureDiseaseMatchDO> insureDiseaseMatchDOS = insureDiseaseMatchDAO.queryPageOrAllByDO(insureDiseaseMatchDO);
        return insureDiseaseMatchDOS;
    }

    /**
     * @Method addDiseaseMatch
     * @Desrciption 疾病生成
     * 1.当前库存在的ID数据进行update（hosp_Disease_name、hosp_Disease_code、hosp_Disease_type、hosp_Disease_spec、hosp_Disease_unit_code、hosp_Disease_prep_code、hosp_Disease_price）
     * 2.查询当前库中ID对应不上的数据进行新增
     * @Param [insureDiseaseMatchDTO]
     * @Author liaojunjie
     * @Date 2020/11/7 11:11
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean addDiseaseMatch(InsureDiseaseMatchDTO insureDiseaseMatchDTO) {
        InsureDiseaseMatchDTO temp = new InsureDiseaseMatchDTO();
        temp.setHospCode(insureDiseaseMatchDTO.getHospCode());
        temp.setInsureRegCode(insureDiseaseMatchDTO.getInsureRegCode());
        List<InsureDiseaseMatchDTO> insureDiseaseMatchDTOS = insureDiseaseMatchDAO.queryPageOrAll(temp);
        // 拿出现在所有存在的疾病Id集合
        List<String> diseaseIds = insureDiseaseMatchDTOS.stream().map(InsureDiseaseMatchDTO::getHospIllnessId).collect(Collectors.toList());

        Map map = new HashMap();
        BaseDiseaseDTO baseDiseaseDTO = new BaseDiseaseDTO();
        baseDiseaseDTO.setHospCode(insureDiseaseMatchDTO.getHospCode());
        map.put("hospCode", insureDiseaseMatchDTO.getHospCode());
        map.put("baseDiseaseDTO", baseDiseaseDTO);

        List<BaseDiseaseDTO> oldDiseases = baseDiseaseService.queryAll(map).getData();
        List<String> oldDiseaseIds = oldDiseases.stream().map(BaseDiseaseDTO::getId).collect(Collectors.toList());

        //需要新增的疾病
        oldDiseaseIds.removeAll(diseaseIds);

        //需要新增的疾病
        List<BaseDiseaseDTO> collect2 = new ArrayList<>();
        if (!ListUtils.isEmpty(oldDiseaseIds)) {
            baseDiseaseDTO.setIds(oldDiseaseIds);
            map.put("baseDiseaseDTO", baseDiseaseDTO);
            collect2 = baseDiseaseService.queryAll(map).getData();
            for (BaseDiseaseDTO d : collect2) {
                d.setInsureRegCode(insureDiseaseMatchDTO.getInsureRegCode());
                d.setInsureId(SnowflakeUtils.getId());
                d.setCrteId(insureDiseaseMatchDTO.getCrteId());
                d.setCrteName(insureDiseaseMatchDTO.getCrteName());
            }
        }

        if (!ListUtils.isEmpty(collect2)) {
            saveBatchList(collect2);
        }

        return true;
    }

    /**
     * @Method deleteDiseaseMatch
     * @Desrciption 清空某医保机构联合医院生成的数据
     * @Param [insureDiseaseMatchDTO]
     * @Author liaojunjie
     * @Date 2020/12/1 11:36
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean deleteDiseaseMatch(InsureDiseaseMatchDTO insureDiseaseMatchDTO) {
        return insureDiseaseMatchDAO.deleteHospDisease(insureDiseaseMatchDTO);
    }

    /**
     * @Method addDownload
     * @Desrciption 将下载后的数据导入项目匹配表
     * @Param [insureDiseaseMatchDTO]
     * @Author liaojunjie
     * @Date 2020/11/7 11:10
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean addDownload(InsureDiseaseMatchDTO insureDiseaseMatchDTO) {
        Map<String, String> map = new HashMap();
        String firstrow = "0";
        String lastrow = "99999999";
        map.put("condition", "disease");
        map.put("hospCode", insureDiseaseMatchDTO.getHospCode());
        map.put("insureRegCode", insureDiseaseMatchDTO.getInsureRegCode());
        map.put("firstrow", firstrow);
        map.put("lastrow", lastrow);


        Map<String, Object> disease = transpond.to(insureDiseaseMatchDTO.getHospCode(), insureDiseaseMatchDTO.getInsureRegCode(), Constant.FUNCTION.BIZC110118, map);
        //对返回的数据进行处理
        handleDownload(disease, insureDiseaseMatchDTO);
        return true;
    }

    /**
     * @Method queryInptDiseaseInfoByVisitId
     * @Desrciption 获取住院诊断信息
     * @Param [paramsMap]
     * @Author 廖继广
     * @Date  2021-03-15
     * @Return java.util.map
     **/
    @Override
    public List<Map<String, Object>> queryInptDiseaseInfoByVisitId(Map<String, Object> paramsMap) {
        return insureDiseaseMatchDAO.queryInptDiseaseInfoByVisitId(paramsMap);
    }

    /**
     * @param insureDiseaseMatchDTOList
     * @Method insertBatchDisease
     * @Desrciption 批量同步基础数据表的数据到医保疾病匹配表
     * @Param
     * @Author fuhui
     * @Date 2021/3/27 22:52
     * @Return
     */
    @Override
    public Integer insertBatchDisease(List<InsureDiseaseMatchDTO> insureDiseaseMatchDTOList) {
        return insureDiseaseMatchDAO.insertMatchDisease(insureDiseaseMatchDTOList);
    }

    /**
     * @param insureDiseaseDTO
     * @Method updateDisease
     * @Desrciption 修改医保疾病匹配信息
     * @Param diseaseDTO
     * @Author fuhui
     * @Date 2021/4/8 17:05
     * @Return Boolean
     */
    @Override
    public Boolean updateDisease(InsureDiseaseMatchDTO insureDiseaseDTO) {
        BaseDiseaseDTO baseDiseaseDTO =new BaseDiseaseDTO();
        baseDiseaseDTO.setId(insureDiseaseDTO.getHospIllnessId());
        baseDiseaseDTO.setHospCode(insureDiseaseDTO.getHospCode());
        baseDiseaseDTO.setIsMatch(insureDiseaseDTO.getIsMatch());
        Boolean update = insureDiseaseMatchDAO.updateDisease(insureDiseaseDTO);
        if(update == true){
            List<InsureDiseaseMatchDTO> insureDiseaseMatchDTOList = insureDiseaseMatchDAO.selectByCode(insureDiseaseDTO);
            if(insureDiseaseMatchDTOList.size() > 1){
                throw new AppException(insureDiseaseDTO.getHospIllnessCode()+"疾病编码已存在，不允许修改");
            }
        }
        return true;
    }

    /**
     * @param insureDiseaseDTO
     * @Method updateDisease
     * @Desrciption 删除医保疾病匹配信息
     * @Param diseaseDTO
     * @Author fuhui
     * @Date 2021/4/8 17:05
     * @Return Boolean
     */
    @Override
    public Boolean deleteInsureDisease(InsureDiseaseMatchDTO insureDiseaseDTO) {
        return insureDiseaseMatchDAO.deleteInsureDisease(insureDiseaseDTO);
    }

    /**
     * @param insureDiseaseDTO
     * @Method queryPageInsureDisease
     * @Desrciption 查询医保的病种编码
     * @Param insureDiseaseDTO
     * @Author fuhui
     * @Date 2021/5/19 17:33
     * @Return
     */
    @Override
    public PageDTO queryPageInsureDisease(InsureDiseaseDTO insureDiseaseDTO) {
        PageHelper.startPage(insureDiseaseDTO.getPageNo(),insureDiseaseDTO.getPageSize());
        List<InsureDiseaseDTO> insureDiseaseDTOList= insureDiseaseMatchDAO.queryPageInsureDisease(insureDiseaseDTO);
        return PageDTO.of(insureDiseaseDTOList);
    }

    @Override
    public PageDTO queryUnMacthAllPage(InsureDiseaseMatchDTO insureDiseaseMatchDTO) {
        PageHelper.startPage(insureDiseaseMatchDTO.getPageNo(),insureDiseaseMatchDTO.getPageSize());
        List<Map<String,Object>> insureDiseaseDTOList= insureDiseaseMatchDAO.queryUnMacthAllPage(insureDiseaseMatchDTO);
        return PageDTO.of(insureDiseaseDTOList);
    }


    Boolean handleDownload(Map<String, Object> diseaseMap, InsureDiseaseMatchDTO insureDiseaseMatchDTO) {
        if (diseaseMap.get("pageinfo") == null) {
            return true;
        }
        //查询出已录入匹配表的医院的信息
        InsureDiseaseMatchDTO temp = new InsureDiseaseMatchDTO();
        temp.setHospCode(insureDiseaseMatchDTO.getHospCode());
        temp.setInsureRegCode(insureDiseaseMatchDTO.getInsureRegCode());
        List<InsureDiseaseMatchDTO> insureDiseaseMatchDTOS = insureDiseaseMatchDAO.queryPageOrAll(temp);

        if (!ListUtils.isEmpty(insureDiseaseMatchDTOS)) {
            Map<String, InsureDiseaseMatchDTO> collect = insureDiseaseMatchDTOS.stream().collect(Collectors.toMap(InsureDiseaseMatchDTO::getHospIllnessCode, Function.identity()));

            List itemList = (List) diseaseMap.get("pageinfo");
            List<InsureDiseaseMatchDTO> diseases = new ArrayList<>();
            for (int i = 0; i < itemList.size(); i++) {
                Map tempMap = (Map) itemList.get(i);
                // 拿到疾病icd10编码
                String hospDiseaseId = (String) tempMap.get("aka120");
                if (StringUtils.isNotEmpty(hospDiseaseId)) {
                    //如果匹配上
                    if (collect.containsKey(hospDiseaseId)) {
                        InsureDiseaseMatchDTO disease = collect.get(hospDiseaseId);
                        //中心疾病编码
                        if (handNull(tempMap.get("aka120"))) {
                            disease.setInsureIllnessId((String) tempMap.get("aka120"));
                        }
                        //中心疾病名称
                        if (handNull(tempMap.get("aka121"))) {
                            disease.setInsureIllnessName((String) tempMap.get("aka121"));
                        }
                        //icd10编码
                        if (handNull(tempMap.get("aka120"))) {
                            disease.setInsureIllnessCode((String) tempMap.get("aka120"));
                        }
                        disease.setIsMatch("1");
                        disease.setIsTrans("1");
                        disease.setAuditCode("1");
                        //拼音码
//                        if (handNull(tempMap.get("aka020"))) {
//                            disease.((String) tempMap.get("aka020"));
//                        }
                        //五笔码
//                        if (handNull(tempMap.get("aka021"))) {
//                            disease.((String) tempMap.get("aka021"));
//                        }
                        //生效日期
//                        if (handNull(tempMap.get("aae030"))) {
//                            try {
//                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                                Date date = simpleDateFormat.parse((String) tempMap.get("aae030"));
//                                disease.setTakeDate(date);
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
                        //失效日期
//                        if (handNull(tempMap.get("aae031"))) {
//                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                            Date date = null;
//                            try {
//                                date = simpleDateFormat.parse((String) tempMap.get("aae031"));
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//                            disease.set(date);
//                        }
                        diseases.add(disease);
                    }
                }
            }
            if (!ListUtils.isEmpty(diseases)) {
                List list = new ArrayList<>();
                for (int i = 0; i <diseases.size() ; i++) {
                    list.add(diseases.get(i));
                    if ((i + 1) % 1000 == 0) {
                        this.insureDiseaseMatchDAO.updateInsureDiseaseMatchS(list);
                        //重新初始
                        list = new ArrayList<>();
                    } else if (i == diseases.size() - 1 && i % 1000 != 0) {
                        this.insureDiseaseMatchDAO.updateInsureDiseaseMatchS(list);
                    }
                }
            }

        }

        return true;
    }

    public Boolean saveBatchList(List<BaseDiseaseDTO> dataList) {
        insureDiseaseMatchDAO.insertHospDisease(dataList);
        return true;
    }
    Boolean handNull(Object object) {
        if (object == null) {
            return false;
        } else {
            return true;
        }
    }

}
