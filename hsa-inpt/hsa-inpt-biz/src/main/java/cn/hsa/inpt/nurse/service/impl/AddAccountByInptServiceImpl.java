package cn.hsa.inpt.nurse.service.impl;
/**
 * @description
 * @author liuqi1
 * @date 2020/9/3
 */

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.nurse.bo.AddAccountByInptBO;
import cn.hsa.module.inpt.nurse.service.AddAccountByInptService;
import cn.hsa.module.oper.operInforecord.dao.OperInfoRecordDAO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.oper.operpatientrecord.service.OperPatientRecordService;
import cn.hsa.util.BigDecimalUtils;
import cn.hsa.util.Constants;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.inpt.nurse.service.impl
 *@Class_name: AddAccountByInptServiceImpl
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/3 11:28
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/inpt/nurse")
@Service("addAccountByInptService_provider")
public class AddAccountByInptServiceImpl extends HsafService implements AddAccountByInptService {

    @Resource
    AddAccountByInptBO addAccountByInptBO;

    @Resource
    OperInfoRecordDAO operInfoRecordDAO;

    /** 病人类型共两种，1.门诊病人 **/
    final String outpatient = "门诊病人";
    /** 病人类型共两种，2.住院病人 **/
    final String inpatient = "住院病人";

    /**
    * @Method queryPatientInfoPage
    * @Desrciption 分页查询患者信息
    * @param map
    * @Author liuqi1
    * @Date   2020/9/4 10:37
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
    **/
    @Override
    public WrapperResponse<PageDTO> queryPatientInfoPage(Map<String, Object> map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
        PageDTO pageDTO = addAccountByInptBO.queryPatientInfoPage(inptVisitDTO);
        return WrapperResponse.success(pageDTO);
    }


    /**
     * @Method queryPatientInfoPage
     * @Desrciption 根据预警线等参数分页查询患者信息
     * @param map
     * @Author xingyu.xie
     * @Date   2020/9/4 10:37
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPatientInfoPageByMoney(Map<String, Object> map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
        PageDTO pageDTO = addAccountByInptBO.queryPatientInfoPageByMoney(inptVisitDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
    * @Method saveAddAccountByInpt
    * @Desrciption 根据病人类型来分别保存住院补记帐
    * @param map 计费的项目
    * @Author liuqi1
    * @Date   2020/9/3 11:34
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> saveAddAccountByInpt(Map<String, Object> map) {
        Boolean isSuccess =  addAccountByInptBO.saveAddAccountByInpt(map);
        return WrapperResponse.success(isSuccess);
    }

    /**
       * 手术补记账新接口
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/31 10:31
    **/
    @Override
    public WrapperResponse<Boolean> saveAddSurgicalAccounting(Map<String, Object> map) {
        List<InptCostDTO> list = MapUtils.get(map,"inptCostDTOs");

        String userId = MapUtils.get(map,"userId");
        String userName = MapUtils.get(map,"userName");
        InptCostDTO inptCostDTO = list.get(0);
        Boolean isSuccess = null;
        if(inptCostDTO.getInptOrOutpt() == null){
            throw new AppException("病人类型为空,请检查！");
        }
        if(outpatient.equals(inptCostDTO.getInptOrOutpt())) {
            isSuccess = addAccountByInptBO.saveAddAccountByOutpt(map);
        }
        if(inpatient.equals(inptCostDTO.getInptOrOutpt())) {
            isSuccess = addAccountByInptBO.saveAddAccountByInpt(map);
        }
        // 更新手术病人的计费状态
        OperInfoRecordDTO operInfoRecordDTO = new OperInfoRecordDTO();
        operInfoRecordDTO.setId(inptCostDTO.getSourceId());
        operInfoRecordDTO.setHospCode(inptCostDTO.getHospCode());
        OperInfoRecordDTO tmpOperDTO = operInfoRecordDAO.getOperInfoById(operInfoRecordDTO);

        return WrapperResponse.success(isSuccess);
    }

    /**
    * @Method queryBackCostWithAddAccountPage
    * @Desrciption 补记账管理退费查询
    * @param map
    * @Author liuqi1
    * @Date   2020/9/4 14:50
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryBackCostWithAddAccountPage(Map<String, Object> map) {
        InptCostDTO inptCostDTO = MapUtils.get(map, "inptCostDTO");
        PageDTO pageDTO = addAccountByInptBO.queryBackCostWithAddAccountPage(inptCostDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @param map
     * @Method queryBackCostWithAddAccountPage
     * @Desrciption 根据visitId and soureTypeCode 查询这个病人录入的长期费用补记账
     * @Author pengbo
     * @Date 2021/5/24 14:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryLongCostByVistIdAndSoureTypeCode(Map<String, Object> map) {
        InptCostDTO inptCostDTO = MapUtils.get(map, "inptCostDTO");
        PageDTO pageDTO = addAccountByInptBO.queryLongCostByVistIdAndSoureTypeCode(inptCostDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @param map
     * @Method queryBackCostWithAddAccountPage
     * @Desrciption 补记账长期费用管理保存
     * @Author pengbo
     * @Date 2021/5/24 14:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<Boolean> saveAccountRepairLong(Map<String, Object> map) {
        return addAccountByInptBO.saveAccountRepairLong(map);
    }

    @Override
    public WrapperResponse<PageDTO> queryBabyPatientInfoPage(Map<String, Object> map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
        PageDTO pageDTO = addAccountByInptBO.queryBabyPatientInfoPage(inptVisitDTO);
        return WrapperResponse.success(pageDTO);
    }

    @Override
    public WrapperResponse<Boolean> updateAccountRepairLong(Map<String, Object> map) {
        return addAccountByInptBO.updateAccountRepairLong(map);
    }

}
