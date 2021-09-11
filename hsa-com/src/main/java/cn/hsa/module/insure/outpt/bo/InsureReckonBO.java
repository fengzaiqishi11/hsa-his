package cn.hsa.module.insure.outpt.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.outpt.dto.InsureReckonDTO;
import cn.hsa.module.insure.outpt.dto.InsureReversalTradeDTO;

import java.util.Map;

/**
 *@Package_name: cn.hsa.module.insure.outpt.bo
 *@Class_name: InsureReckonBO
 *@Describe: 医保统一支付平台,清算申请
 *@Author: liaojiguang
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2021/4/12 11:14
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InsureReckonBO {

    /**清算查询
     * @Method queryInsureReckonInfo
     * @Desrciption
     * @param insureReckonDTO
     * @Author liaojiguang
     * @Date   2021/9/9 19:45
     * @Return
     **/
    PageDTO queryInsureReckonInfo(InsureReckonDTO insureReckonDTO);

    /**清算新增
     * @Method addInsureReckonInfo
     * @Desrciption
     * @param insureReckonDTO
     * @Author liaojiguang
     * @Date   2021/9/9 19:45
     * @Return
     **/

    Boolean addInsureReckonInfo(InsureReckonDTO insureReckonDTO);

    /**清算编辑
     * @Method updateInsureReckonInfo
     * @Desrciption
     * @param insureReckonDTO
     * @Author liaojiguang
     * @Date   2021/9/9 19:45
     * @Return
     **/
    Boolean updateInsureReckonInfo(InsureReckonDTO insureReckonDTO);

    /**清算申请
     * @Method updateToInsureReckon
     * @Desrciption
     * @param insureReckonDTO
     * @Author liaojiguang
     * @Date   2021/9/9 19:45
     * @Return
     **/
    Boolean updateToInsureReckon(InsureReckonDTO insureReckonDTO);

    /**清算撤销申请
     * @Method updateToRevokeInsureReckon
     * @Desrciption
     * @param insureReckonDTO
     * @Author liaojiguang
     * @Date   2021/9/9 19:45
     * @Return
     **/
    Boolean updateToRevokeInsureReckon(InsureReckonDTO insureReckonDTO);

    /**
     * 医药机构清算申请 - 删除
     *
     * @param insureReckonDTO
     * @Method deleteInsureReckon
     * @Desrciption
     * @Author liaojiguang
     * @Date 2021/9/9 17:31
     * @Return
     **/
    Boolean deleteInsureReckon(InsureReckonDTO insureReckonDTO);
}
