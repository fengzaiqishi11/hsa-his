package cn.hsa.module.insure.outpt.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.outpt.dto.InsureReckonDTO;
import cn.hsa.module.insure.outpt.dto.InsureReversalTradeDTO;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.util.List;
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

    /**
     * 医疗机构月结算申请汇总信息分页查询-3693
     *
     * @param insureReckonDTO
     * @Method queryInsureMonSettleApplyInfo
     * @Desrciption
     * @Author liaojiguang
     * @Date 2021/9/9 17:31
     * @Return
     **/
    PageDTO queryInsureMonSettleApplyInfo(InsureReckonDTO insureReckonDTO);

    /**
     * 获取清算机构 -3694
     *
     * @param insureReckonDTO
     * @Method queryInsureClrOptinsInfo
     * @Desrciption
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    PageDTO queryInsureClrOptinsInfo(InsureReckonDTO insureReckonDTO);

    /**
     * 获取清算汇总明细 -3695
     *
     * @param insureReckonDTO
     * @Method queryInsureSettleApplyInfo
     * @Desrciption 获取清算汇总明细
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    PageDTO queryInsureSettleApplyInfo(InsureReckonDTO insureReckonDTO);

    /**
     * 获取暂扣明细信息 -3696
     *
     * @param insureReckonDTO
     * @Method queryInsureDetDetlList
     * @Desrciption 获取暂扣明细信息
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    PageDTO queryInsureDetDetlList(InsureReckonDTO insureReckonDTO);

    /**
     * 医疗机构月结算报表pdf文档 -3697
     *
     * @param insureReckonDTO
     * @Method getImportClredReportPdf
     * @Desrciption 医疗机构月结算报表pdf文档
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    Map<String,Object> getImportClredReportPdf(InsureReckonDTO insureReckonDTO);

    /**
     * 获取拨付单信息 - 3407
     *
     * @param insureReckonDTO
     * @Method queryInsureAppropriationList
     * @Desrciption 获取拨付单信息
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    PageDTO queryInsureAppropriationList(InsureReckonDTO insureReckonDTO);

    /**
     * 获取基金明细信息 - 3702
     *
     * @param insureReckonDTO
     * @Method queryInsureDetailFundList
     * @Desrciption 获取基金明细信息
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    PageDTO queryInsureDetailFundList(InsureReckonDTO insureReckonDTO);

    /**
     * 获取结算明细信息 - 3703
     *
     * @param insureReckonDTO
     * @Method queryInsureSetlDetlList
     * @Desrciption 获取结算明细信息
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    PageDTO queryInsureSetlDetlList(InsureReckonDTO insureReckonDTO);

    /**
     * 对账汇总查询 - 3699
     *
     * @param insureReckonDTO
     * @Method queryInsureSetlDetlList
     * @Desrciption 对账汇总查询
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     * */
    PageDTO queryInsureTotlStmtInfo(InsureReckonDTO insureReckonDTO);
}
