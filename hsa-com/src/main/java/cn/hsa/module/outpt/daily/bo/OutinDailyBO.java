package cn.hsa.module.outpt.daily.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.outpt.daily.dto.OutinDailyDTO;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.daily.bo
 * @Class_name: OutinDaliyBO
 * @Description: 日结缴款BO层
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/09/24 10:06
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface OutinDailyBO {
    /**
     * 新增数据
     *
     * @param outinDailyDTO
     * @return 实例对象
     */
    boolean insert(OutinDailyDTO outinDailyDTO);

    /**
     * 确认缴款
     *
     * @param outinDailyDTO
     * @return 实例对象
     */
    boolean update(OutinDailyDTO outinDailyDTO);

    /**
     * 取消缴款
     *
     * @param outinDailyDTO
     * @return 实例对象
     */
    boolean delete(OutinDailyDTO outinDailyDTO);

    /**
     * @Method 分页查询日结缴款主表
     * @Description
     *
     * @Param outinDailyDTO
     *
     * @Author zhongming
     * @Date 2020/9/24 11:58
     * @Return
     **/
    PageDTO queryPage(OutinDailyDTO outinDailyDTO);

    /**
     * @Method 获取最后一次缴款信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 14:54
     * @Return
     **/
    OutinDailyDTO getLastDaily(OutinDailyDTO outinDailyDTO);

    /**
     * @Method 日结缴款 - 缴款报表
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/9/30 16:33
     * @Return 
     **/
    Map<String, Object> getOutinDaily(OutinDailyDTO outinDailyDTO);

    /**
     * @Method 日结缴款 - 结算列表
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/11/2 14:57
     * @Return 
     **/
    PageDTO querySettle(OutinDailyDTO outinDailyDTO);

    /**
     * @Method 日结缴款 - 预交金列表
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/11/2 15:00
     * @Return 
     **/
    PageDTO queryAdvancePay(OutinDailyDTO outinDailyDTO);

    /**
     * @Method 日结缴款 - 预交金冲抵列表
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/11/2 15:07
     * @Return 
     **/
    PageDTO queryAdvancePayCd(OutinDailyDTO outinDailyDTO);

    /**
     * @Description: 日结缴款 - 一卡通充值、退款明细
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/8/23 16:43
     * @Return
     */
    PageDTO queryCardConsumePay(OutinDailyDTO outinDailyDTO);

    /**
     * @Description: 日结缴款 - 一卡通消费明细
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/8/23 16:43
     * @Return
     */
    PageDTO queryCardCzOrTkPay(OutinDailyDTO outinDailyDTO);
}
