package cn.hsa.outpt.outptregisterquery.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.outptquery.bo.OutptRegisterQueryBO;
import cn.hsa.module.outpt.outptquery.dto.OutptRegisterDTO;
import cn.hsa.module.outpt.outptquery.service.OutptRegisterQueryService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.outptregisterquery.service
 * @class_name: OutptRegisterQueryServiceImpl
 * @Description: 门诊挂号查询服务实现层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/8/10 14:59
 * @Company: 创智和宇
 **/
@HsafRestPath("/service/outpt/outptregisterquery")
@Slf4j
@Service("outptRegisterQueryService_provider")
public class OutptRegisterQueryServiceImpl extends HsafService implements OutptRegisterQueryService {

    /**
     * 门诊挂号查询业务逻辑层接口
     */
    @Resource
    private OutptRegisterQueryBO outptRegisterQueryBO;


    /**
     * @Method: queryPage()
     * @Description: 门诊挂号分页查询
     * @Param: map
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/10 14:38
     * @Return: outptRegisterDTO门诊挂号数据传输对象集合
     */
    @Override
    @HsafRestPath(value = "/queryPage", method = RequestMethod.GET)
    public WrapperResponse<PageDTO> queryPage(Map map) {
        OutptRegisterDTO outptRegisterDTO = MapUtils.get(map, "outptRegisterDTO");
        return WrapperResponse.success(outptRegisterQueryBO.queryPage(outptRegisterDTO));
    }
}
