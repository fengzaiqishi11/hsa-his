package cn.hsa.base.bdc.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bdc.bo.BaseDailyfirstCalcBO;
import cn.hsa.module.base.bdc.dto.BaseDailyfirstCalcDTO;
import cn.hsa.module.base.bdc.service.BaseDailyfirstCalcService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description: 首日计费service接口实现层（提供给dubbo调用）
 * @Author: ljh
 * @Email:
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/base/syncassistdetail")
@Slf4j
@Service("baseDailyfirstCalcService_provider")
public class BaseDailyfirstCalcServiceImpl extends HsafService implements BaseDailyfirstCalcService {

    @Resource
    private BaseDailyfirstCalcBO baseDailyfirstCalcBO;

    /**
     * @Package_name: cn.hsa.base.syncdailyfirst.service.impl
     * @Class_name:: BaseDailyfirstCalcServiceImpl
     * @Description: 查询
     * @Author: ljh
     * @Date: 2020/8/26 19:25
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    @HsafRestPath(value = "/queryAll", method = RequestMethod.POST)
    @Override
    public WrapperResponse<List<BaseDailyfirstCalcDTO>> queryAll(Map map) {


        return WrapperResponse.success(baseDailyfirstCalcBO.queryAll(MapUtils.get(map,"bean")));
    }

    /**
     * @Package_name: cn.hsa.base.syncdailyfirst.service.impl
     * @Class_name:: BaseDailyfirstCalcServiceImpl
     * @Description: 新增
     * @Author: ljh
     * @Date: 2020/8/26 19:26
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    @HsafRestPath(value = "/insert", method = RequestMethod.POST)
    @Override
    public WrapperResponse<Boolean> insert(Map map) {
        baseDailyfirstCalcBO.insert(MapUtils.get(map,"bean"));
        return WrapperResponse.success(true);
    }

    /**
     * @Package_name: cn.hsa.base.syncdailyfirst.service.impl
     * @Class_name:: BaseDailyfirstCalcServiceImpl
     * @Description: 删除
     * @Author: ljh
     * @Date: 2020/8/26 19:26
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    @HsafRestPath(value = "/deleteById", method = RequestMethod.GET)
    @Override
    public WrapperResponse<Boolean> deleteById(Map map) {
        baseDailyfirstCalcBO.deleteBylist(MapUtils.get(map,"bean"));
        return WrapperResponse.success(true);
    }

    /**
     * @Package_name: cn.hsa.base.syncdailyfirst.service.impl
     * @Class_name:: BaseDailyfirstCalcServiceImpl
     * @Description: 分页
     * @Author: ljh
     * @Date: 2020/8/26 19:26
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    @HsafRestPath(value = "/queryPage", method = RequestMethod.GET)
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        PageDTO dto = baseDailyfirstCalcBO.queryPage(MapUtils.get(map,"bean"));
        return WrapperResponse.success(dto);
    }

    /**
     * @Method: queryDaily
     * @Description: 获取每日首次计费信息
     * @Param: [dailyMap]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/24 11:36
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bdc.dto.BaseDailyfirstCalcDTO>
     **/
    @HsafRestPath(value = "/queryDaily", method = RequestMethod.GET)
    @Override
    public WrapperResponse<List<BaseDailyfirstCalcDTO>> queryDaily(Map dailyMap) {
        return WrapperResponse.success(baseDailyfirstCalcBO.queryDaily(MapUtils.get(dailyMap,"dailyfirstCalcDTO")));
    }
}
