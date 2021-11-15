package cn.hsa.module.outpt.prescribe.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDTO;

import java.util.List;

/**
 *@Package_name: cn.hsa.module.outpt.prescribe.bo
 *@Class_name: OutptPrescribeTempBO
 *@Describe: 处方模板
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/19 14:56
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptPrescribeTempBO {

    /**
    * @Method queryOutptPrescribeTempDTOPage
    * @Desrciption 分页查询
    * @param outptPrescribeTempDTO
    * @Author liuqi1
    * @Date   2020/8/19 15:01
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryOutptPrescribeTempDTOPage(OutptPrescribeTempDTO outptPrescribeTempDTO);

    /**
     * @Menthod queryOutptPrescribeTempDTO
     * @Desrciption  查询模板信息
     * @param outptPrescribeTempDTO
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<OutptPrescribeTempDTO>
     **/
    List<OutptPrescribeTempDTO> queryOutptPrescribeTempDTO(OutptPrescribeTempDTO outptPrescribeTempDTO);

    /**
     * @Menthod queryOutptPrescribeTempDTO
     * @Desrciption  查询模板明细信息
     * @param outptPrescribeTempDTO
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<OutptPrescribeTempDTO>
     **/
    PageDTO queryOutptPrescribeTempDetails(OutptPrescribeTempDTO outptPrescribeTempDTO);

    /*
     * @Menthod savePrescribeTemp
     * @Desrciption  保存处方模板信息
     * @param outptPrescribeDTO 处方明细
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return boolean
     */
    boolean savePrescribeTemp(OutptPrescribeTempDTO outptPrescribeTempDTO);

    /**
     * @Method updateOutptPrescribeTempDTO
     * @Desrciption 修改模板信息
     * @param outptPrescribeTempDTO
     * @Author zengfeng
     * @Date   2020/9/19 14:44
     * @Return boolean
     **/
    boolean updateOutptPrescribeTempDTO(OutptPrescribeTempDTO outptPrescribeTempDTO);

    /**
     * @Method updateTempAudit
     * @Desrciption 模板取消审核
     * @param outptPrescribeTempDTO
     * @Author liuliyun
     * @Date   2021/11/10 17:16
     * @Return boolean
     **/
    boolean updateTempAudit(OutptPrescribeTempDTO outptPrescribeTempDTO);
}
