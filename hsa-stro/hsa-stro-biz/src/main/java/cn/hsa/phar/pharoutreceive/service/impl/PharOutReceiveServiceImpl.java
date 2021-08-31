package cn.hsa.phar.pharoutreceive.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.phar.pharoutreceive.bo.PharOutReceiveBo;
import cn.hsa.module.phar.pharoutreceive.service.PharOutReceiveService;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.phar.pharoutreceive.service.impl
 * @Class_name: PharOutReceiveServiceImpl
 * @Describe(描述):门诊领药申请信息ServiceImpl层
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/09/16 15:39
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/stro/pharOutReceive")
@Service("pharOutReceiveService_provider")
public class PharOutReceiveServiceImpl extends HsafService implements PharOutReceiveService {

    @Resource
    private PharOutReceiveBo pharOutReceiveBo;

    /**
     * @Menthod batchInsert
     * @Desrciption  新增门诊领药申请信息
     * @param param 参数
     * @Author Ou·Mr
     * @Date 2020/9/16 15:13
     * @Return int 受影响的行数
     */
    @Override
    public WrapperResponse batchInsert(Map param) {
        return pharOutReceiveBo.batchInsert(MapUtils.get(param,"pharOutReceiveDOList")) > 0 ? WrapperResponse.success("成功",null) : WrapperResponse.fail("失败",null);
    }
}
