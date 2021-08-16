package cn.hsa.base.card.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.card.bo.BaseCardBO;
import cn.hsa.module.base.card.dto.BaseCardDTO;
import cn.hsa.module.base.card.dto.BaseCardRechargeChangeDTO;
import cn.hsa.module.base.card.service.BaseCardService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.card.service.impl
 * @Class_name: BaseCardServiceImpl
 * @Describe: 一卡通service实现类
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-05-11 15:59
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Service("baseCardService_provider")
@Slf4j
@HsafRestPath("/service/base/baseCard")
public class BaseCardServiceImpl extends HsafService implements BaseCardService {

    @Resource
    private BaseCardBO baseCardBO;

    /**
     * @Menthod: queryCardPage
     * @Desrciption: 分页查询一卡通列表数据
     * @Param: baseCardDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-11 16:20
     * @Return: PageDTO
     **/
    @Override
    public WrapperResponse<PageDTO> queryCardPage(Map map) {
        return WrapperResponse.success(baseCardBO.queryCardPage(MapUtils.get(map, "baseCardDTO")));
    }

    /**
     * @Menthod: getCardByProId
     * @Desrciption: 根据档案id查询出所有发卡记录
     * @Param: baseCardDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-11 16:20
     * @Return: List<BaseCardDTO>
     **/
    @Override
    public WrapperResponse<List<BaseCardDTO>> getCardByProId(Map map) {
        return WrapperResponse.success(baseCardBO.getCardByProId(MapUtils.get(map, "baseCardDTO")));
    }

    /**
     * @Menthod: saveCard
     * @Desrciption: 发卡
     * @Param: baseCardDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-11 16:20
     * @Return: Boolean
     **/
    @Override
    public WrapperResponse<Boolean> saveCard(Map map) {
        return WrapperResponse.success(baseCardBO.saveCard(MapUtils.get(map, "baseCardDTO")));
    }

    /**
     * @Menthod: updateStatusCode
     * @Desrciption: 状态更改---1挂失，2冻结，3注销，4确认挂失，5作废
     * @Param: baseCardDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-11 16:20
     * @Return: Boolean
     **/
    @Override
    public WrapperResponse<Boolean> updateStatusCode(Map map) {
        return WrapperResponse.success(baseCardBO.updateStatusCode(MapUtils.get(map, "baseCardDTO"), MapUtils.get(map, "baseCardChangeDO")));
    }

    /**
     * @Menthod: resetPwd
     * @Desrciption: 密码重置
     * @Param: baseCardDTO
     * @Author: caoliang
     * @Email: caoliang@powersi.com.cn
     * @Date: 2021-06-03 10:20
     * @Return: Boolean
     **/
    @Override
    public WrapperResponse<Boolean> updatePwd(Map map) {
        return WrapperResponse.success(baseCardBO.updatePwd(MapUtils.get(map, "baseCardDTO")));
    }

    /**
     * @Menthod: saveInCharge
     * @Desrciption: 一卡通充值
     * @Param: baseCardRechargeChangeDO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com.cn
     * @Date: 2021-08-06 10:10
     * @Return: Boolean
     **/
    @Override
    public WrapperResponse<Boolean> saveInCharge(Map map) {
        return WrapperResponse.success(baseCardBO.saveInCharge(MapUtils.get(map, "baseCardRechargeChangeDO")));
    }

    /**
     * @Menthod: saveCardRefund
     * @Desrciption: 一卡通退费
     * @Param: baseCardRechargeChangeDO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com.cn
     * @Date: 2021-08-06 10:13
     * @Return: Boolean
     **/
    @Override
    public WrapperResponse<Boolean> saveCardRefund(Map map) {
        return WrapperResponse.success(baseCardBO.saveCardRefund(MapUtils.get(map, "baseCardRechargeChangeDO")));
    }

    /**
     * @Menthod: getCardChargeInfoByProId
     * @Desrciption: 根据档案id查询一卡通信息
     * @Param: baseCardDTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-08-06 16:20
     * @Return: BaseCardRechargeChangeDTO
     **/
    @Override
    public WrapperResponse<BaseCardRechargeChangeDTO> getRechargeChangeInfo(Map map) {
        return WrapperResponse.success(baseCardBO.getRechargeChangeInfo(map));
    }

    /**
     * @Menthod: getCardChargeInfoList
     * @Desrciption: 查询一卡通充值退费记录
     * @Param: baseCardDTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-08-07 14:05
     * @Return: List<BaseCardRechargeChangeDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> getRechargeChangeInfoList(Map map) {
        BaseCardRechargeChangeDTO baseCardRechargeChangeDTO=(BaseCardRechargeChangeDTO)map.get("baseCardRechargeChangeDTO");
        return WrapperResponse.success(baseCardBO.getRechargeChangeInfoList(baseCardRechargeChangeDTO));
    }


    /**
     * @Menthod: queryPaitentPage
     * @Desrciption: 分页查询档案信息
     * @Param: baseCardDTO
     * @Author: liuliyun
     * @Email: liuliyun@powersi.com
     * @Date: 2021-08-07 16:25
     * @Return: PageDTO
     **/
    @Override
    public WrapperResponse<PageDTO> queryPaitentPage(Map map) {
        return WrapperResponse.success(baseCardBO.queryPaitentPage(MapUtils.get(map, "baseCardDTO")));
    }


    /**
     * @Menthod: queryPaitentCardRechargeInfoList
     * @Desrciption: 分页查询一卡通异动信息
     * @Param: baseCardRechargeChangeDTO
     * @Author: liuliyun
     * @Email: liuliyun@powersi.com
     * @Date: 2021-08-10 16:45
     * @Return: PageDTO
     **/
    @Override
    public WrapperResponse<PageDTO> queryPaitentCardRechargeInfoList(Map map) {
        return WrapperResponse.success(baseCardBO.queryPaitentCardRechargeInfoList(MapUtils.get(map, "baseCardRechargeChangeDTO")));
    }

}
