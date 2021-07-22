package cn.hsa.module.oper.operpatientrecord.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
   * @Describe: OperPatientRecordDTO 表服务
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/4/23 13:50
   * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
**/
@FeignClient(value = "hsa-oper")
public interface OperPatientRecordService {

    /**
       * @Param map 微服务调用参数
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/4/23 13:52
       * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
    **/
    WrapperResponse<PageDTO> queryOperPatientPage(Map map);
    /**
       * 获取非手术疾病信息
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/24 15:13
    **/
    WrapperResponse<PageDTO> getNonSurgicalDiseasesInfo(Map map);
}
