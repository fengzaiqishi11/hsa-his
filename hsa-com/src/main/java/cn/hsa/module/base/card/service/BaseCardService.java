package cn.hsa.module.base.card.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.card.dto.BaseCardDTO;
import cn.hsa.module.base.card.dto.BaseCardRechargeChangeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.card.service
 * @Class_name: BaseCardService
 * @Describe: 一卡通service
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-05-11 15:57
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "his-base")
public interface BaseCardService {
    /**
     * @Menthod: queryCardPage
     * @Desrciption: 分页查询一卡通列表数据
     * @Param: baseCardDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-11 16:20
     * @Return: PageDTO
     **/
    @GetMapping("/service/base/baseCard/queryCardPage")
    WrapperResponse<PageDTO> queryCardPage(Map map);

    /**
     * @Menthod: getCardByProId
     * @Desrciption: 根据档案id查询出所有发卡记录
     * @Param: baseCardDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-11 16:20
     * @Return: List<BaseCardDTO>
     **/
    @GetMapping("/service/base/baseCard/getCardByProId")
    WrapperResponse<List<BaseCardDTO>> getCardByProId(Map map);

    /**
     * @Menthod: saveCard
     * @Desrciption: 发卡
     * @Param: baseCardDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-11 16:20
     * @Return: Boolean
     **/
    @PostMapping("/service/base/baseCard/saveCard")
    WrapperResponse<Boolean> saveCard(Map map);

    /**
     * @Menthod: updateStatusCode
     * @Desrciption: 状态更改---1挂失，2冻结，3注销，4确认挂失，5作废
     * @Param: baseCardDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-11 16:20
     * @Return: Boolean
     **/
    @PostMapping("/service/base/baseCard/updateStatusCode")
    WrapperResponse<Boolean> updateStatusCode(Map map);

    /**
     * @Menthod: resetPwd
     * @Desrciption: 密码重置
     * @Param: baseCardDTO
     * @Author: caoliang
     * @Email: caoliang@powersi.com.cn
     * @Date: 2021-06-03 10:20
     * @Return: Boolean
     **/
    @PostMapping("/service/base/baseCard/resetPwd")
    WrapperResponse<Boolean> updatePwd(Map map);

    /**
     * @Menthod: saveInCharge
     * @Desrciption: 一卡通充值
     * @Param: map
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-08-06 10:15
     * @Return: Boolean
     **/
    @PostMapping("/service/base/baseCard/saveInCharge")
    WrapperResponse<Boolean> saveInCharge(Map map);


    /**
     * @Menthod: saveInCharge
     * @Desrciption: 一卡通退费
     * @Param: map
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-08-06 10:20
     * @Return: Boolean
     **/
    @PostMapping("/service/base/baseCard/saveCardRefund")
    WrapperResponse<Boolean> saveCardRefund(Map map);

    /**
     * @Menthod: getRechargeChangeInfo
     * @Desrciption: 一卡通余额信息查询
     * @Param: map
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-08-06 14:27
     * @Return: BaseCardRechargeChangeDTO
     **/
    @PostMapping("/service/base/baseCard/getRechargeChangeInfo")
    WrapperResponse<BaseCardRechargeChangeDTO> getRechargeChangeInfo(Map map);


    /**
     * @Menthod: getRechargeChangeInfoList
     * @Desrciption: 一卡通充值历史记录
     * @Param: map
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-08-07 14:12
     * @Return: List<BaseCardRechargeChangeDTO>
     **/
    @PostMapping("/service/base/baseCard/getRechargeChangeInfoList")
    WrapperResponse<PageDTO> getRechargeChangeInfoList(Map map);


    /**
     * @Menthod: queryPaitentPage
     * @Desrciption: 分页查询档案病人信息
     * @Param: baseCardDTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-08-07 16:27
     * @Return: PageDTO
     **/
    @GetMapping("/service/base/baseCard/queryPaitentPage")
    WrapperResponse<PageDTO> queryPaitentPage(Map map);

    /**
     * @Menthod: queryPaitentCardRechargeInfoList
     * @Desrciption: 分页查询一卡通异动信息
     * @Param: baseCardRechargeChangeDTO
     * @Author: liuliyun
     * @Email: liuliyun@powersi.com
     * @Date: 2021-08-10 16:37
     * @Return: PageDTO
     **/
    @GetMapping("/service/base/baseCard/queryPaitentCardRechargeInfoList")
    WrapperResponse<PageDTO> queryPaitentCardRechargeInfoList(Map map);

}
