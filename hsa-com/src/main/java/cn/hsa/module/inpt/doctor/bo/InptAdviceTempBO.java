package cn.hsa.module.inpt.doctor.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDetailTempDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceTempDTO;

import java.util.List;

/**
 *@Package_name: cn.hsa.doctor.advice.bo.impl
 *@Class_name: InptAdviceTempBOImpl
 *@Describe: 医嘱模板业务层BO
 *@Author: zengfeng
 *@Eamil: zengfeng@powersi.com.cn
 *@Date: 2020-10-20 19:46:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InptAdviceTempBO {

    /**
     * @Method queryInptAdviceTemp
     * @Desrciption 医嘱模板分页查询
     * @param inptAdviceTempDTO
     * @Author zengfeng
     * @Date   2020/10/20 19:46:53
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryInptAdviceTempPage(InptAdviceTempDTO inptAdviceTempDTO);



    /**
     * @Method queryInptAdviceTemp
     * @Desrciption 医嘱模板查询
     * @param inptAdviceTempDTO
     * @Author zengfeng
     * @Date   2020/10/20 19:46:53
     * @Return cn.hsa.base.PageDTO
     **/
    List<InptAdviceTempDTO> queryInptAdviceTemp(InptAdviceTempDTO inptAdviceTempDTO);

    /**
     * @Method queryInptAdviceDetailTemp
     * @Desrciption 医嘱模板明细查询
     * @param inptAdviceTempDTO
     * @Author zengfeng
     * @Date   2020/10/20 19:46:53
     * @Return cn.hsa.base.PageDTO
     **/
    List<InptAdviceDetailTempDTO> queryInptAdviceDetailTemp(InptAdviceTempDTO inptAdviceTempDTO);

    /**
     * @Method saveAdciceTemp
     * @Desrciption 保存医嘱模板
     * @param inptAdviceTempDTO
     * @Author zengfeng
     * @Date   2020/10/20 19:46:53
     * @Return java.lang.Boolean
     **/
    boolean saveAdciceTemp(InptAdviceTempDTO inptAdviceTempDTO);

    /**
     * @Method updateAdviceTemp
     * @Desrciption 审核、删除医嘱模板
     * @param inptAdviceTempDTO 医嘱模板信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    boolean updateAdviceTemp(InptAdviceTempDTO inptAdviceTempDTO);
}
