package cn.hsa.inpt.consultation.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.inpt.consultation.bo.InptConsultationApplyBO;
import cn.hsa.module.inpt.consultation.dao.InptConsultationApplyDAO;
import cn.hsa.module.inpt.consultation.dto.InptConsultationApplyDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.consultation.bo.impl
 * @Class_name: InptConsultationApplyBOImpl
 * @Describe: 会诊申请bo实现类
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-11-04 20:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@Component
public class InptConsultationApplyBOImpl extends HsafBO implements InptConsultationApplyBO {

    @Resource
    private InptConsultationApplyDAO inptConsultationApplyDAO;

    @Resource
    private BaseOrderRuleService baseOrderRuleService_consumer;

    /**
     * @Menthod: saveConsultationApply
     * @Desrciption: 保存会诊申请记录
     * @Param: inptConsultationApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-04 20:36
     * @Return:
     **/
    @Override
    public String saveConsultationApply(InptConsultationApplyDTO inptConsultationApplyDTO) {
        if (StringUtils.isEmpty(inptConsultationApplyDTO.getVisitId())) {
            throw new RuntimeException("未选择就诊人");
        }
        if (StringUtils.isEmpty(inptConsultationApplyDTO.getConsulType())) {
            throw new RuntimeException("未选择会诊类型");
        }
        if (StringUtils.isEmpty(inptConsultationApplyDTO.getId())) {
            inptConsultationApplyDTO.setId(SnowflakeUtils.getId());
            // 会诊申请单号
            String typeCode = "123";
            String consulNo = this.getConsulNo(typeCode, inptConsultationApplyDTO.getHospCode());
            inptConsultationApplyDTO.setConsulNo(consulNo);
            // 会诊申请状态
            inptConsultationApplyDTO.setConsulState(Constants.HZZT.BC);
            // 会诊时间
            inptConsultationApplyDTO.setConsulTime(inptConsultationApplyDTO.getConsulTime() == null ? DateUtils.getNow() : inptConsultationApplyDTO.getConsulTime());
            inptConsultationApplyDAO.insert(inptConsultationApplyDTO);
            return inptConsultationApplyDTO.getId();
        } else {
            InptConsultationApplyDTO byId = inptConsultationApplyDAO.getById(inptConsultationApplyDTO);
            if (Constants.HZZT.WC.equals(byId.getConsulState())) {
                throw new RuntimeException("【" + byId.getConsulNo() + "】已完成，不可修改！");
            }
            inptConsultationApplyDAO.update(inptConsultationApplyDTO);
            return byId.getId();
        }
    }

    /**
     * 票据规则：123
     * @param typeCode 会诊申请号规则
     * @param hospCode 医院编码
     * @return
     */
    private String getConsulNo(String typeCode, String hospCode) {
        Map map = new HashMap();
        map.put("typeCode", typeCode);
        map.put("hospCode", hospCode);
        return baseOrderRuleService_consumer.getOrderNo(map).getData();
    }

    /**
     * @Menthod: queryConsultationApply
     * @Desrciption: 分页查询会诊记录
     * @Param: inptConsultationApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-04 20:36
     * @Return:
     **/
    @Override
    public PageDTO queryConsultationApply(InptConsultationApplyDTO inptConsultationApplyDTO) {
        PageHelper.startPage(inptConsultationApplyDTO.getPageNo(), inptConsultationApplyDTO.getPageSize());
        List<InptConsultationApplyDTO> list = inptConsultationApplyDAO.queryConsultationApply(inptConsultationApplyDTO);
        return PageDTO.of(list);
    }

    /**
     * @Menthod: getById
     * @Desrciption: 分页查询会诊记录
     * @Param: inptConsultationApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-04 20:36
     * @Return:
     **/
    @Override
    public InptConsultationApplyDTO getById(InptConsultationApplyDTO inptConsultationApplyDTO) {
        return inptConsultationApplyDAO.getById(inptConsultationApplyDTO);
    }

    /**
     * @Menthod: updateStatus
     * @Desrciption: 更改会诊状态
     * @Param: inptConsultationApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-04 20:42
     * @Return:
     **/
    @Override
    public Boolean updateStatus(InptConsultationApplyDTO inptConsultationApplyDTO) {
        InptConsultationApplyDTO consultationApplyDTOById = inptConsultationApplyDAO.getById(inptConsultationApplyDTO);
        if (consultationApplyDTOById == null) {
            throw new RuntimeException("未查询到相关会诊申请记录");
        }

        if (Constants.HZZT.SH.equals(inptConsultationApplyDTO.getConsulState())) {
            // 审核
            if (Constants.HZZT.SH.equals(consultationApplyDTOById.getConsulState())) {
                throw new RuntimeException("【" + consultationApplyDTOById.getConsulNo() + "】已审核，不允许重复审核");
            }
            if (Constants.HZZT.WC.equals(consultationApplyDTOById.getConsulState())) {
                throw new RuntimeException("【" + consultationApplyDTOById.getConsulNo() + "】已完成，不可审核");
            }
            inptConsultationApplyDTO.setConsulState(Constants.HZZT.SH);
        } else if (Constants.HZZT.WC.equals(inptConsultationApplyDTO.getConsulState())) {
            // 完成
            if (Constants.HZZT.WC.equals(consultationApplyDTOById.getConsulState())) {
                throw new RuntimeException("【" + consultationApplyDTOById.getConsulNo() + "】已完成，不可重复完成");
            }
            inptConsultationApplyDTO.setConsulState(Constants.HZZT.WC);
        } else {
            // 作废，暂时不做
            inptConsultationApplyDTO.setConsulState(Constants.HZZT.ZF);
        }
        return inptConsultationApplyDAO.updateStatus(inptConsultationApplyDTO) > 0;
    }
}
