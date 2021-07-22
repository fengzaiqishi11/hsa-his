package cn.hsa.base.pym.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.module.base.pym.bo.PymBo;
import cn.hsa.module.base.pym.service.PymService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_ame: cn.hsa.base.pym.service.impl
 * @Class_name: hsa-his
 * @Description:
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2020/12/6  16:49
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@HsafRestPath("/service/base/pym")
@Service("pymService_provider")
public class PymServiceImpl extends HsafService implements PymService {

    @Resource
    private PymBo pymBo;

    @Override
    public Boolean updatePym(Map map) {
        return pymBo.updatePym(map);
    }
}
