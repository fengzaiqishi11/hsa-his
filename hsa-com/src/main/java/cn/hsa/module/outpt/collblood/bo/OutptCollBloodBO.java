package cn.hsa.module.outpt.collblood.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.medic.apply.dto.MedicalApplyDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.outpt.collblood.bo
 * @Class_name: OutptCollBloodBO
 * @Describe: 门诊采血bo
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-04-28 11:49
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface OutptCollBloodBO {
    /**
     * @Menthod: queryCollBlood
     * @Desrciption: 查询门诊采血列表数据
     * @Param: medicalApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-28 15:18
     * @Return: PageDTO
     **/
    PageDTO queryCollBlood(MedicalApplyDTO medicalApplyDTO);

    /**
     * @Menthod: saveCollBlood
     * @Desrciption: 保存门诊采血数据
     * @Param: medicalApplyDTOList
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-28 15:18
     * @Return: Boolean
     **/
    Boolean saveCollBlood(List<MedicalApplyDTO> medicalApplyDTOList);
}
