package cn.hsa.outpt.collblood.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.module.medic.apply.dto.MedicalApplyDTO;
import cn.hsa.module.outpt.collblood.bo.OutptCollBloodBO;
import cn.hsa.module.outpt.collblood.dao.OutptCollBloodDAO;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.outpt.collblood.bo.impl
 * @Class_name: OutptCollBloodBOImpl
 * @Describe: 门诊采血bo实现
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-04-28 11:50
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class OutptCollBloodBOImpl implements OutptCollBloodBO {

    @Resource
    private OutptCollBloodDAO outptCollBloodDAO;

    /**
     * @Menthod: queryCollBlood
     * @Desrciption: 查询门诊采血列表数据
     * @Param: medicalApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-28 15:18
     * @Return: PageDTO
     **/
    @Override
    public PageDTO queryCollBlood(MedicalApplyDTO medicalApplyDTO) {
        PageHelper.startPage(medicalApplyDTO.getPageNo(), medicalApplyDTO.getPageSize());
        List<MedicalApplyDTO> list = outptCollBloodDAO.queryCollBlood(medicalApplyDTO);
        return PageDTO.of(list);
    }

    /**
     * @Menthod: saveCollBlood
     * @Desrciption: 保存门诊采血数据
     * @Param: medicalApplyDTOList
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-28 15:18
     * @Return: Boolean
     **/
    @Override
    public Boolean saveCollBlood(List<MedicalApplyDTO> medicalApplyDTOList) {
        return outptCollBloodDAO.saveCollBlood(medicalApplyDTOList) > 0;
    }
}
