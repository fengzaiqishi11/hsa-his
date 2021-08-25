package cn.hsa.module.base.card.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.card.dto.BaseCardDTO;
import cn.hsa.module.base.card.dto.BaseCardRechargeChangeDTO;
import cn.hsa.module.base.card.entity.BaseCardChangeDO;
import cn.hsa.module.base.card.entity.BaseCardRechargeChangeDO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.card.bo
 * @Class_name: BaseCardBO
 * @Describe: 一卡通bo
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-05-11 16:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BaseCardBO {
    /**
     * @Menthod: queryCardPage
     * @Desrciption: 分页查询一卡通列表数据
     * @Param: baseCardDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-11 16:20
     * @Return: PageDTO
     **/
    PageDTO queryCardPage(BaseCardDTO baseCardDTO);

    /**
     * @Menthod: getCardByProId
     * @Desrciption: 根据档案id查询出所有发卡记录
     * @Param: baseCardDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-11 16:20
     * @Return: List<BaseCardDTO>
     **/
    List<BaseCardDTO> getCardByProId(BaseCardDTO baseCardDTO);

    /**
     * @Menthod: saveCard
     * @Desrciption: 发卡
     * @Param: baseCardDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-11 16:20
     * @Return: Boolean
     **/
    Boolean saveCard(BaseCardDTO baseCardDTO);

    /**
     * @Menthod: updateStatusCode
     * @Desrciption: 状态更改---1挂失，2冻结，3注销，4确认挂失，5作废
     * @Param: baseCardDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-11 16:20
     * @Return: Boolean
     **/
    Boolean updateStatusCode(BaseCardDTO baseCardDTO, BaseCardChangeDO baseCardChangeDO);

    /**
     * @Menthod: resetPwd
     * @Desrciption: 密码重置
     * @Param: baseCardDTO
     * @Author: caoliang
     * @Email: caoliang@powersi.com.cn
     * @Date: 2021-06-03 10:20
     * @Return: Boolean
     **/
    Boolean updatePwd(BaseCardDTO baseCardDTO);

    /**
     * @Menthod: saveInCharge
     * @Desrciption: 一卡通充值
     * @Param: cardRechargeChangeDO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com.cn
     * @Date: 2021-08-06 10:17
     * @Return: Boolean
     **/
    Boolean saveInCharge(BaseCardRechargeChangeDO cardRechargeChangeDO);


    /**
     * @Menthod: saveCardRefund
     * @Desrciption: 一卡通退费
     * @Param: cardRechargeChangeDO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com.cn
     * @Date: 2021-08-06 10:18
     * @Return: Boolean
     **/
    Boolean saveCardRefund(BaseCardRechargeChangeDO cardRechargeChangeDO);

    BaseCardRechargeChangeDTO getRechargeChangeInfo(Map map);

    PageDTO getRechargeChangeInfoList(BaseCardRechargeChangeDTO baseCardRechargeChangeDTO);


    /**
     * @Menthod: queryPaitentPage
     * @Desrciption: 分页查询档案信息
     * @Param: baseCardDTO
     * @Author: liuliyun
     * @Email: liuliyun@powersi.com
     * @Date: 2021-08-07 16:25
     * @Return: PageDTO
     **/
    PageDTO queryPaitentPage(BaseCardDTO baseCardDTO);

    /**
     * @Menthod: queryPaitentCardRechargeInfoList
     * @Desrciption: 分页查询一卡通异动信息
     * @Param: baseCardRechargeChangeDTO
     * @Author: liuliyun
     * @Email: liuliyun@powersi.com
     * @Date: 2021-08-10 16:34
     * @Return: PageDTO
     **/
    List<BaseCardRechargeChangeDTO> queryPaitentCardRechargeInfoList(BaseCardRechargeChangeDTO baseCardRechargeChangeDTO);

}
