package cn.hsa.module.insure.inpt.bo;

import cn.hsa.base.PageDO;
import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.module.entity.InsureUnifiedRemoteDO;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.inpt.bo
 * @class_name: InsureUnifiedRemoteBO
 * @Description: 医保统一支付平台：异地医药机构费用结算业务
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/23 12:40
 * @Company: 创智和宇
 **/
public interface InsureUnifiedRemoteBO {

    /**
     * @Method insertRemoteDetail
     * @Desrciption  提取异地清分明细:就医地使用此交易提取省内异地外来就医月度结算清分明细,供医疗机构进行确认处理
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/4/23 13:54
     * @Return
     **/
    Map<String,Object> insertRemoteDetail(Map<String, Object> map);

    /**
     * @param map
     * @Method selectRemoteConfirmResult
     * @Desrciption 异地清分结果确认:就医地使用此交易提交省内异地外来就医月度结算清分确认结果。
     * @Param map
     * @Author fuhui
     * @Date 2021/4/23 13:54
     * @Return
     */
    Map<String,Object>  updateRemoteConfirmResult(Map<String, Object> map);

    /**
     * @param map
     * @Method updateRemoteConfirmBack
     * @Desrciption 异地清分结果确认回退:就医地使用此交易回退已经提交的就医月度结算清分确认结果。
     * @Param map
     * @Author fuhui
     * @Date 2021/4/23 13:54
     * @Return
     */
    Map<String,Object>  updateRemoteConfirmBack(Map<String, Object> map);

    /**
     * @param map
     * @Method 查询异地清分明细信息
     * @Desrciption
     * @Param
     * @Author fuhui
     * @Date 2021/6/8 20:22
     * @Return
     */
    PageDTO queryPage(InsureUnifiedRemoteDO insureUnifiedRemoteDO);
}
