package cn.hsa.module.outpt.fees.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
   * 手术补记账模块服务
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/5/26 20:14
   * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
**/

@FeignClient(value = "hsa-outpt")
public interface OutptCostKeepAccountsService {

    /***
     * 批量插入计费项目信息到门诊计费表中
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/26 20:09
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    WrapperResponse<Boolean> insertOutptCostBatch(Map<String,Object> map);

    /***
       * 更新门诊病人的手术费用
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/31 13:39
    **/
    WrapperResponse<Boolean> updateOperInfoRecord(Map<String,Object> map);
}
