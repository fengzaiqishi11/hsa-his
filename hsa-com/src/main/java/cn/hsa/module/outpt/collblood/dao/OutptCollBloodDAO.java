package cn.hsa.module.outpt.collblood.dao;

import cn.hsa.module.medic.apply.dto.MedicalApplyDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.outpt.collblood.dao
 * @Class_name: OutptCollBloodDAO
 * @Describe: TODO
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-04-28 11:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface OutptCollBloodDAO {
    /**
     * @Menthod: queryCollBlood
     * @Desrciption: 查询门诊采血列表数据
     * @Param: medicalApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-28 15:18
     * @Return: List<MedicalApplyDTO>
     **/
    List<MedicalApplyDTO> queryCollBlood(MedicalApplyDTO medicalApplyDTO);

    /**
     * @Menthod: saveCollBlood
     * @Desrciption: 保存门诊采血数据
     * @Param: medicalApplyDTOList
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-28 15:18
     * @Return: Boolean
     **/
    int saveCollBlood(@Param("list") List<MedicalApplyDTO> medicalApplyDTOList);
}
