package cn.hsa.insure.module.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.module.insure.advice.service.AdviceService;
import org.springframework.stereotype.Service;

/**
 * @Package_name: cn.hsa.insure.module.service.impl
 * @Class_name: AdviceServiceImpl
 * @Describe(描述): 医嘱医保开放统一接口 service实现层
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/09 9:08
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/insure/advice")
@Service("adviceService_provider")
public class AdviceServiceImpl extends HsafService implements AdviceService {
}
