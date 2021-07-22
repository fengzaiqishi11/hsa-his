package cn.hsa.interf.phys.service.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.phys.bo.PhysRegBO;
import cn.hsa.module.interf.phys.service.PhysRegService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@HsafRestPath("/service/interf/phys")
@Slf4j
@Service("physRegService_provider")
public class PhysRegServiceImpl extends HsafBO implements PhysRegService {
    @Resource
    private PhysRegBO physRegBO;

    /**
     * @Description: 获取体检者登记信息
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     * @Author: zhangxuan
     * @Date: 2021-07-05
     */
    @Override
    public WrapperResponse<Map> regPhysInfo(Map map) {
        return physRegBO.saveRegPhysInfo(map);
    }

    /**
     * @Method addVisitByPhys
     * @Desrciption 新增登记一次就要 同步一次就诊的数据
     * @Param [physRegisterDTO]
     * @Author zhangguorui
     * @Date   2021/7/14 13:56
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    @Override
    public WrapperResponse<Boolean> addVisitByPhys(Map map) {
        return WrapperResponse.success(physRegBO.addVisitByPhys(map));
    }
    /**
     * @Method addOrUpdateOutptCost
     * @Desrciption 批量插入费用表
     * @Param [map]
     * @Author zhangguorui
     * @Date   2021/7/14 16:07
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    @Override
    public WrapperResponse<Boolean> addOrUpdateOutptCost(Map map) {
        return WrapperResponse.success(physRegBO.addOrUpdateOutptCost(map));
    }
}
