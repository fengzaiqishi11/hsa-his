package cn.hsa.insure.drgdip.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.module.drgdip.service.DrgDipQulityInfoLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@HsafRestPath("/service/insure/insureDrgDipQulityInfoLog")
@Service("insureDrgDipQulityInfoLog_provider")
public class DrgDipQulityInfoLogServiceImpl extends HsafService implements DrgDipQulityInfoLogService {
}
