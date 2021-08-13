package cn.hsa.module.base.card.dao;

import cn.hsa.module.base.card.dto.BaseCardDTO;
import cn.hsa.module.base.card.dto.BaseCardRechargeChangeDTO;
import cn.hsa.module.base.card.entity.BaseCardChangeDO;
import cn.hsa.module.base.card.entity.BaseCardRechargeChangeDO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.card.dao
 * @Class_name: BaseCardDAO
 * @Describe: 一卡通dao
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-05-11 16:03
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BaseCardDAO {
    /**
     * @Menthod: queryCardPage
     * @Desrciption: 分页查询一卡通列表数据
     * @Param: baseCardDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-11 16:20
     * @Return: PageDTO
     **/
    List<BaseCardDTO> queryCardPage(BaseCardDTO baseCardDTO);

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
     * @Menthod: insertCard
     * @Desrciption: 发卡
     * @Param: baseCardDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-11 16:20
     * @Return: Boolean
     **/
    Integer insertCard(BaseCardDTO baseCardDTO);

    /**
     * @Menthod: updateStatusCode
     * @Desrciption: 状态更改---1挂失，2冻结，3注销，4确认挂失，5作废
     * @Param: baseCardDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-11 16:20
     * @Return: Boolean
     **/
    Integer updateStatusCode(BaseCardDTO baseCardDTO);

    /**
     * @Menthod: resetPwd
     * @Desrciption: 密码重置
     * @Param: baseCardDTO
     * @Author: caoliang
     * @Email: caoliang@powersi.com.cn
     * @Date: 2021-06-03 10:20
     * @Return: Boolean
     **/
    Integer updatePwd(BaseCardDTO baseCardDTO);

    /**
     * @Menthod: queryCardByNo
     * @Desrciption: 根据IC卡类型和卡号校验是否重复
     * @Param: baseCardDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-11 16:20
     * @Return: BaseCardDTO
     **/
    BaseCardDTO queryCardByNo(BaseCardDTO baseCardDTO);

    /**
     * @Description: 一卡通异动表写入记录
     * @Param: 
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/8/5 15:17
     * @Return 
     */
    int insertBaseCardChange(BaseCardChangeDO baseCardChangeDO);

    /**
     * @Description: 一卡通充值异动写入记录
     * @Param:
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date 2021/08/06 10:55
     * @Return
     */
    int insertBaseCardRechargeChange(BaseCardRechargeChangeDO cardRechargeChangeDO);


    /**
     * @Description: 一卡通充值异动写入记录
     * @Param:
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date 2021/08/06 10:55
     * @Return
     */
    BaseCardRechargeChangeDTO findCardRechargeInfoById(Map map);


    /**
     * @Description: 查询一卡通充值记录
     * @Param:
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date 2021/08/07 13:54
     * @Return
     */
    List<BaseCardRechargeChangeDTO> findCardRechargeInfoList(BaseCardRechargeChangeDTO baseCardRechargeChangeDTO);

    /**
     * @Menthod: queryPaitentPage
     * @Desrciption: 分页查询档案信息
     * @Param: baseCardDTO
     * @Author: liuliyun
     * @Email: liuliyun@powersi.com
     * @Date: 2021-08-07 16:31
     * @Return: PageDTO
     **/
    List<BaseCardDTO> queryPaitentPage(BaseCardDTO baseCardDTO);

    /**
     * @Menthod: queryPaitentCardRechargeInfoList
     * @Desrciption: 分页查询一卡通异动信息
     * @Param: cardRechargeChangeDTO
     * @Author: liuliyun
     * @Email: liuliyun@powersi.com
     * @Date: 2021-08-10 16:26
     * @Return: PageDTO
     **/
    List<BaseCardRechargeChangeDTO> queryPaitentCardRechargeInfoList(BaseCardRechargeChangeDTO cardRechargeChangeDTO);

    Boolean updateCardAccountBalance(BaseCardDTO baseCardDTO);

}
