package cn.hsa.module.insure.outpt.dao;

import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.outpt.dto.InsureReckonDTO;
import cn.hsa.module.insure.outpt.dto.InsureReversalTradeDTO;
import cn.hsa.module.insure.outpt.entity.InsureReckonDO;

import java.util.List;
import java.util.Map;

/**冲正交易
 *@Package_name: cn.hsa.module.insure.outpt.dao
 *@Class_name: InsureReversalTradeDAO
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2021/4/12 16:08
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InsureReckonDAO {

    /**清算查询
     * @Method queryInsureReckonInfo
     * @Desrciption
     * @param insureReckonDTO
     * @Author liaojiguang
     * @Date   2021/9/9 19:45
     * @Return
     **/
    List<InsureReckonDTO> queryInsureReckonInfoPage(InsureReckonDTO insureReckonDTO);

    /**清算新增
     * @Method addInsureReckonInfo
     * @Desrciption
     * @param insureReckonDTO
     * @Author liaojiguang
     * @Date   2021/9/9 19:45
     * @Return
     **/

    int addInsureReckonInfo(InsureReckonDTO insureReckonDTO);

    /**清算编辑
     * @Method updateInsureReckonInfo
     * @Desrciption
     * @param insureReckonDTO
     * @Author liaojiguang
     * @Date   2021/9/9 19:45
     * @Return
     **/
    int updateInsureReckonInfo(InsureReckonDTO insureReckonDTO);

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
    int deleteInsureReckon(InsureReckonDTO insureReckonDTO);

    /**
     * 医药机构清算申请 - 获取清算机构
     *
     * @param insureReckonDTO
     * @Method getInsureClrOptinsByRegCode
     * @Desrciption
     * @Author liaojiguang
     * @Date 2021/9/9 17:31
     * @Return
     **/
    List<String> getInsureClrOptinsByRegCode(InsureReckonDTO insureReckonDTO);

    InsureReckonDO getInsureUnifiedReckonById(InsureReckonDTO insureReckonDTO);
}
