package cn.hsa.outpt.daily.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.daily.bo.OutinDailyBO;
import cn.hsa.module.outpt.daily.dto.OutinDailyDTO;
import cn.hsa.module.outpt.daily.service.OutinDailyService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.daily.service.impl
 * @Class_name: OutptDaliyServiceImpl
 * @Description: 门诊日结缴款ServiceImpl层
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/09/24 10:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/outpt/outinDailyService")
@Slf4j
@Service("outinDailyService_provider")
public class OutinDailyServiceImpl implements OutinDailyService {
    @Resource
    private OutinDailyBO outinDailyBO;

    /**
     * @Method 新增日结缴款
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/9/25 23:15
     * @Return 
     **/
    @Override
    public WrapperResponse<Boolean> insert(Map map) {
        return WrapperResponse.success(outinDailyBO.insert(MapUtils.get(map, "outinDailyDTO")));
    }

    /**
     * @Method 确认缴款
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 14:54
     * @Return
     **/
    @Override
    public WrapperResponse<Boolean> update(Map map) {
        return WrapperResponse.success(outinDailyBO.update(MapUtils.get(map, "outinDailyDTO")));
    }

    /**
     * @Method 取消缴款
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 14:54
     * @Return
     **/
    @Override
    public WrapperResponse<Boolean> delete(Map map) {
        return WrapperResponse.success(outinDailyBO.delete(MapUtils.get(map, "outinDailyDTO")));
    }

    /**
     * @Method 查询最近一次日结信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 14:54
     * @Return
     **/
    @Override
    public WrapperResponse<OutinDailyDTO> getLastDaily(Map map) {
        return WrapperResponse.success(outinDailyBO.getLastDaily(MapUtils.get(map, "outinDailyDTO")));
    }

    /**
     * @Method 分页查询日结主表信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 14:54
     * @Return
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        return WrapperResponse.success(outinDailyBO.queryPage(MapUtils.get(map, "outinDailyDTO")));
    }

    /**
     * @Method 日结缴款 - 缴款报表
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/9/30 16:33
     * @Return 
     **/
    @Override
    public WrapperResponse<Map<String, Object>> getOutinDaily(Map map) {
        return WrapperResponse.success(outinDailyBO.getOutinDaily(MapUtils.get(map, "outinDailyDTO")));
    }

    /**
     * @Method 日结缴款 - 结算明细
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/30 16:33
     * @Return
     **/
    @Override
    public WrapperResponse<PageDTO> querySettle(Map map) {
        return WrapperResponse.success(outinDailyBO.querySettle(MapUtils.get(map, "outinDailyDTO")));
    }

    /**
     * @Method 日结缴款 - 预交金明细
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/30 16:33
     * @Return
     **/
    @Override
    public WrapperResponse<PageDTO> queryAdvancePay(Map map) {
        return WrapperResponse.success(outinDailyBO.queryAdvancePay(MapUtils.get(map, "outinDailyDTO")));
    }

    /**
     * @Method 日结缴款 - 预交金冲抵明细
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/30 16:33
     * @Return
     **/
    @Override
    public WrapperResponse<PageDTO> queryAdvancePayCd(Map map) {
        return WrapperResponse.success(outinDailyBO.queryAdvancePayCd(MapUtils.get(map, "outinDailyDTO")));
    }
}
