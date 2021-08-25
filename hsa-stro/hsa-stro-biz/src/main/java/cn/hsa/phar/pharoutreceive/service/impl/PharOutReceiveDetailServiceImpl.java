package cn.hsa.phar.pharoutreceive.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.phar.pharoutreceive.bo.PharOutReceiveDetailBo;
import cn.hsa.module.phar.pharoutreceive.entity.PharOutReceiveDetailDO;
import cn.hsa.module.phar.pharoutreceive.service.PharOutReceiveDetailService;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.phar.pharoutreceive.service.impl
 * @Class_name: PharOutReceiveDetailServiceImpl
 * @Describe(描述):门诊领药申请明细信息ServiceImpl层
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/09/16 15:39
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/stro/pharOutReceiveDetail")
@Service("pharOutReceiveDetailService_provider")
public class PharOutReceiveDetailServiceImpl extends HsafService implements PharOutReceiveDetailService {

    @Resource
    private PharOutReceiveDetailBo pharOutReceiveDetailBo;

    @Override
    public WrapperResponse batchInsert(Map param) {
        List<PharOutReceiveDetailDO> pharOutReceiveDetailDOList = MapUtils.get(param,"pharOutReceiveDetailDOList");
        return pharOutReceiveDetailBo.batchInsert(pharOutReceiveDetailDOList) > 0 ? WrapperResponse.success("成功",null) : WrapperResponse.fail("失败",null);
    }
}
