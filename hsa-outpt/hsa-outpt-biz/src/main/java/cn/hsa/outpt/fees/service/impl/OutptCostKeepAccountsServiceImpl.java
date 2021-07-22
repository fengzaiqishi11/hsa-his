package cn.hsa.outpt.fees.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.bo.OuptCostKeepAccountsBO;
import cn.hsa.module.outpt.fees.service.OutptCostKeepAccountsService;
import cn.hsa.util.MapUtils;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author luonianxin
 */
@Slf4j
@HsafRestPath("/service/outpt/outptCostKeepAccounts")
@Service("outptCostKeepAccounts_provider")
public class OutptCostKeepAccountsServiceImpl extends HsafService implements OutptCostKeepAccountsService {
    @Resource
    private OuptCostKeepAccountsBO ouptCostKeepAccountsBO;
    /***
     * 批量插入计费项目信息到门诊计费表中
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/26 20:09
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     *
     * @param map 传递参数cn.hsa.module.inpt.doctor.dto.OutptCostDTO;
     * */
    @Override
    public WrapperResponse<Boolean> insertOutptCostBatch(Map<String, Object> map) {
        List<OutptCostDTO> outptCostDTOs = MapUtils.get(map,"outptCostDTOs");
        return WrapperResponse.success(ouptCostKeepAccountsBO.insertOutptCostBatch(outptCostDTOs));
    }

    /***
     * 更新门诊病人的手术费用
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/31 13:39
     * @param map 更新参数
     * */
    @Override
    public WrapperResponse<Boolean> updateOperInfoRecord(Map<String, Object> map) {
        return WrapperResponse.success(ouptCostKeepAccountsBO.updateOperInfoRecord(map));
    }
}
