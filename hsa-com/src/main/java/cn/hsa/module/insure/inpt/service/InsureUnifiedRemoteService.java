package cn.hsa.module.insure.inpt.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.inpt.service
 * @class_name: InsureUnifiedRemoteService
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/23 13:46
 * @Company: 创智和宇
 **/
@FeignClient(value = "hsa-insure")
public interface InsureUnifiedRemoteService {

    /**
     * @Method selectRemoteDetail
     * @Desrciption  提取异地清分明细:就医地使用此交易提取省内异地外来就医月度结算清分明细,供医疗机构进行确认处理
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/4/23 13:54
     * @Return
     **/
    WrapperResponse<Map<String,Object>> selectRemoteDetail(Map<String, Object> map);

    /**
     * @Method selectRemoteConfirmResult
     * @Desrciption  异地清分结果确认:就医地使用此交易提交省内异地外来就医月度结算清分确认结果。
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/4/23 13:54
     * @Return
     **/
    WrapperResponse<Map<String, Object>> selectRemoteConfirmResult(Map<String, Object> map);

    /**
     * @Method updateRemoteConfirmBack
     * @Desrciption  异地清分结果确认回退:就医地使用此交易回退已经提交的就医月度结算清分确认结果。
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/4/23 13:54
     * @Return
     **/
    WrapperResponse<Map<String, Object>> updateRemoteConfirmBack(Map<String, Object> map);

    /**
     * @Method 查询异地清分明细信息
     * @Desrciption
     * @Param
     *
     * @Author fuhui
     * @Date   2021/6/8 20:22
     * @Return
     **/
    WrapperResponse<PageDTO> queryPage(Map<String, Object> map);
}
