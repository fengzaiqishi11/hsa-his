package cn.hsa.module.outpt.prescribe.dao;

import cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDetailDTO;

import java.util.List;


/**
 *@Package_name: cn.hsa.module.outpt.prescribe.dao
 *@Class_name: OutptPrescribeTempDao
 *@Describe: 处方模板数据访问层
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/19 14:33
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptPrescribeTempDAO {

    /**
    * @Method getOutptPrescribeTempDTOById
    * @Desrciption 单个查询
    * @param outptPrescribeTempDTO
    * @Author liuqi1
    * @Date   2020/8/19 14:43
    * @Return cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDTO
    **/
    OutptPrescribeTempDTO getOutptPrescribeTempDTOById(OutptPrescribeTempDTO outptPrescribeTempDTO);

    /**
    * @Method queryOutptPrescribeTempDTOPage
    * @Desrciption 分页查询
    * @param outptPrescribeTempDTO
    * @Author liuqi1
    * @Date   2020/8/19 14:44
    * @Return java.util.List<cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDTO>
    **/
    List<OutptPrescribeTempDTO> queryOutptPrescribeTempDTOPage(OutptPrescribeTempDTO outptPrescribeTempDTO);

    /**
     * @Method queryOutptPrescribeTempDetails
     * @Desrciption 查询模板明细
     * @param outptPrescribeTempDTO
     * @Author zengfeng
     * @Date   2020/9/19 14:44
     * @Return java.util.List<cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDetailDTO>
     **/
    List<OutptPrescribeTempDetailDTO> queryOutptPrescribeTempDetails(OutptPrescribeTempDTO outptPrescribeTempDTO);

    /**
    * @Method insertOutptPrescribeTempDTO
    * @Desrciption 单个新增
    * @param outptPrescribeTempDTO
    * @Author liuqi1
    * @Date   2020/8/19 14:45
    * @Return int
    **/
    int insertOutptPrescribeTempDTO(OutptPrescribeTempDTO outptPrescribeTempDTO);

    /**
    * @Method updateOutptPrescribeTempDTO
    * @Desrciption 单个更改
    * @param outptPrescribeTempDTO
    * @Author liuqi1
    * @Date   2020/8/19 14:46
    * @Return int
    **/
    int updateOutptPrescribeTempDTO(OutptPrescribeTempDTO outptPrescribeTempDTO);

    /**
     * @Method queryOutptPrescribeTempDTO
     * @Desrciption 查询模板信息
     * @param outptPrescribeTempDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return java.util.List<cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDTO>
     **/
    List<OutptPrescribeTempDTO> queryOutptPrescribeTempDTO(OutptPrescribeTempDTO outptPrescribeTempDTO);

    /**
     * @Method insertOutptPrescribeTempDetail
     * @Desrciption 保存模板明细
     * @param outptPrescribeTempDetailDTOList
     * @Author zengfeng
     * @Date   2020/9/19 14:45
     * @Return int
     **/
    int insertOutptPrescribeTempDetail(List<OutptPrescribeTempDetailDTO> outptPrescribeTempDetailDTOList);

    /**
     * @Method deletePrescribeTempDetail
     * @Desrciption 删除模板明细
     * @param outptPrescribeTempDTO
     * @Author zengfeng
     * @Date   2020/10/19 14:45
     * @Return int
     **/
    int deletePrescribeTempDetail(OutptPrescribeTempDTO outptPrescribeTempDTO);
}