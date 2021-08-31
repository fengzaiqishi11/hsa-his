package cn.hsa.sync.rate.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.rate.bo.SyncRateBO;
import cn.hsa.module.sync.rate.dto.SyncRateDTO;
import cn.hsa.module.sync.rate.service.SyncRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Package_name: cn.hsa.base.rate.StockInQueryService.impl
 * @class_name: SyncRateServiceImpl
 * @Description:
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/30 20:18
 * @Company: 创智和宇
 **/
@HsafRestPath("/api/center/rate")
@Slf4j
@Service("centerRateService_provider")
public class SyncRateServiceImpl extends HsafService implements SyncRateService {

    /**
     * 医嘱频率的bo层接口
     */
    @Resource
    private SyncRateBO syncRateBO;


    /**
     * @Method getById()
     * @Description 查询医嘱频率
     *
     * @Param  map
     * 1、id：医嘱频率表主键ID
     * 2、hospCode 医院编码
     * @Author fuhui
     * @Date 2020/7/13 10:23
     * @Return BaseWardDTO
     **/
    @Override
    @HsafRestPath(value = "/getById", method = RequestMethod.GET)
    public WrapperResponse<SyncRateDTO> getById(SyncRateDTO syncRateDTO) {
        try {
            return WrapperResponse.success(syncRateBO.getById(syncRateDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }

    }

    /**
     * @Method queryPage()
     * @Description 分页查医嘱频率信息
     *
     * @Param
     * 1、 syncRateDTO：医嘱频率数据参数对象
     * @Author fuhui
     * @Date 2020/7/13 10:23
     * @Return BaseWardDTO
     **/
    @Override
    @HsafRestPath(value = "/queryPage", method = RequestMethod.GET)
    public WrapperResponse<PageDTO> queryPage(SyncRateDTO syncRateDTO) {
        PageDTO pageDTO = syncRateBO.queryPage(syncRateDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Method insert()
     * @Description 新增医嘱频率信息
     *
     * @Param
     * 1、syncRateDTO：医嘱频率数据参数对象
     *
     * @Author fuhui
     * @Date 2020/7/13 10:23
     * @Return boolean
     **/
    @Override
    @HsafRestPath(value = "/insert", method = RequestMethod.POST)
    public WrapperResponse<Boolean> insert(SyncRateDTO syncRateDTO) {
        try {
            // 参数校验
            return WrapperResponse.success( syncRateBO.insert(syncRateDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }

    }

    /**
     * @Method update()
     * @Description 修改医嘱频率信息
     *
     * @Param
     * 1、syncRateDTO：医嘱频率数据参数对象
     *
     * @Author fuhui
     * @Date 2020/7/13 10:23
     * @Return boolean
     **/
    @Override
    @HsafRestPath(value = "/update", method = RequestMethod.POST)
    public WrapperResponse<Boolean> update(SyncRateDTO syncRateDTO) {
        try {
            return WrapperResponse.success( syncRateBO.update(syncRateDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method: 修改有效标识符
     * @Description:
     * @Param: syncRateDTO：医嘱频率数据参数对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/23 11:38
     * @Return: boolean
     */
    @Override
    @HsafRestPath(value = "/updateIsValid", method = RequestMethod.POST)
    public WrapperResponse<Boolean> updateIsValid(SyncRateDTO syncRateDTO) {
        try {
            return WrapperResponse.success( syncRateBO.updateIsValid(syncRateDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method queryAll()
     * @Description 查询医嘱频率
     * @Param
     * 1、syncRateDTO
     * @Author fuhui
     * @Date 2020/7/13 14:44
     * @Return BaseRateDTO
     **/
    @Override
    @HsafRestPath(value = "/queryAll", method = RequestMethod.GET)
    public WrapperResponse<List<SyncRateDTO>> queryAll(SyncRateDTO syncRateDTO) {
        return WrapperResponse.success(syncRateBO.queryAll(syncRateDTO));
    }
}
