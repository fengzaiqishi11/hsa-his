package cn.hsa.module.inpt.doctor.dao;

import cn.hsa.module.inpt.doctor.dto.InptAdviceDetailTempDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceTempDTO;

import java.util.List;


/**
 *@Package_name: cn.hsa.module.inpt.dao
 *@Class_name: InptAdviceTempDAO
 *@Describe: 医嘱模板数据访问层
 *@Author: zengfeng
 *@Eamil: zengfeng@powersi.com.cn
 *@Date: 2020-10-20 19:46:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InptAdviceTempDAO {

    /**
    * @Method queryInptAdviceTemp
    * @Desrciption 查询医嘱模板
    * @param inptAdviceTempDTO
    * @Author zengfeng
    * @Date   2020-10-20  20:51
    * @Return java.util.List<cn.hsa.module.inpt.dto.InptAdviceDTO>
    **/
    List<InptAdviceTempDTO> queryInptAdviceTemp(InptAdviceTempDTO inptAdviceTempDTO);



    /**
     * @Method queryInptAdviceTemp
     * @Desrciption 获取医嘱模板
     * @param inptAdviceTempDTO
     * @Author zengfeng
     * @Date   2020-10-20  20:51
     * @Return java.util.List<cn.hsa.module.inpt.dto.InptAdviceDTO>
     **/
    InptAdviceTempDTO getInptAdviceTemp(InptAdviceTempDTO inptAdviceTempDTO);

    /**
     * @Method queryInptAdviceDetailTemp
     * @Desrciption 查询医嘱模板明细查询
     * @param inptAdviceTempDTO
     * @Author zengfeng
     * @Date   2020-10-20  20:51
     * @Return java.util.List<cn.hsa.module.inpt.dto.InptAdviceDTO>
     **/
    List<InptAdviceDetailTempDTO> queryInptAdviceDetailTemp(InptAdviceTempDTO inptAdviceTempDTO);

    /**
    * @Method insertBathInptAdviceTemp
    * @Desrciption 新增医嘱模板
    * @param inptAdviceTempDTO
    * @Author zengfeng
    * @Date   2020-10-20  20:51
    * @Return int
    **/
    int insertInptAdviceTemp(InptAdviceTempDTO inptAdviceTempDTO);

    /**
     * @Method insertBathInptAdviceDetailTemp
     * @Desrciption 新增医嘱模板明细数据
     * @param inptAdviceDetailTempList
     * @Author zengfeng
     * @Date   2020-10-20  20:51
     * @Return int
     **/
    int insertBathInptAdviceDetailTemp(List<InptAdviceDetailTempDTO> inptAdviceDetailTempList);

    /**
    * @Method updateAdviceTemp
    * @Desrciption 修改医嘱模板
    * @param inptAdviceTempDTO
    * @Author zengfeng
    * @Date   2020-10-20  20:51
    * @Return int
    **/
    int updateAdviceTemp(InptAdviceTempDTO inptAdviceTempDTO);

    /**
     * @Method deleteInptAdviceTemp
     * @Desrciption 删除医嘱模板
     * @param inptAdviceTempDTO
     * @Author zengfeng
     * @Date   2020-10-20  20:51
     * @Return int
     **/
    int deleteInptAdviceTemp(InptAdviceTempDTO inptAdviceTempDTO);

    /**
     * @Method deletePrescribeTempDetail
     * @Desrciption 删除医嘱模板明细
     * @param inptAdviceTempDTO
     * @Author zengfeng
     * @Date   2020-10-20  20:51
     * @Return int
     **/
    int deletePrescribeTempDetail(InptAdviceTempDTO inptAdviceTempDTO);

    /**
     * @Method checkIsAuditCode
     * @Desrciption 获取最大组合
     * @param inptAdviceTempDTO
     * @Author zengfeng
     * @Date   2020-10-20  20:51
     * @Return int
     **/
    int checkIsAuditCode(InptAdviceTempDTO inptAdviceTempDTO);

}