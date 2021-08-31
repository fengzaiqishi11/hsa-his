package cn.hsa.base.rate.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.rate.bo.BaseRateBO;
import cn.hsa.module.base.rate.dto.BaseRateDTO;
import cn.hsa.module.base.rate.service.BaseRateService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.rate.StockInQueryService.impl
 * @class_name: BaseRateServiceImpl
 * @Description:
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/30 20:18
 * @Company: 创智和宇
 **/
@HsafRestPath("/api/base/rate")
@Slf4j
@Service("baseRateService_provider")
public class BaseRateServiceImpl extends HsafService implements BaseRateService {

    /**
     * 医嘱频率的bo层接口
     */
    @Resource
    private BaseRateBO baseRateBO;

    /**
     * @Method getById()
     * @Description 查询医嘱频率
     * @Param map
     * 1、id：医嘱频率表主键ID
     * 2、hospCode 医院编码
     * @Author fuhui
     * @Date 2020/7/13 10:23
     * @Return BaseWardDTO
     **/
    @Override
    @HsafRestPath(value = "/getById", method = RequestMethod.GET)
    public WrapperResponse<BaseRateDTO> getById(Map map) {
        BaseRateDTO baseRateDTO = MapUtils.get(map, "baseRateDTO");
        return WrapperResponse.success(baseRateBO.getById(baseRateDTO));
    }

    /**
     * @Method queryPage()
     * @Description 分页查医嘱频率信息
     * @Param 1、 baseRateDTO：医嘱频率数据参数对象
     * @Author fuhui
     * @Date 2020/7/13 10:23
     * @Return BaseWardDTO
     **/
    @Override
    @HsafRestPath(value = "/queryPage", method = RequestMethod.GET)
    public WrapperResponse<PageDTO> queryPage(Map map) {
        BaseRateDTO baseRateDTO = MapUtils.get(map, "baseRateDTO");
        PageDTO pageDTO = baseRateBO.queryPage(baseRateDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Method insert()
     * @Description 新增医嘱频率信息
     * @Param 1、baseRateDTO：医嘱频率数据参数对象
     * @Author fuhui
     * @Date 2020/7/13 10:23
     * @Return boolean
     **/
    @Override
    @HsafRestPath(value = "/insert", method = RequestMethod.POST)
    public WrapperResponse<Boolean> insert(Map map) {
        BaseRateDTO baseRateDTO = MapUtils.get(map, "baseRateDTO");
        try {
            // 参数校验
            if (baseRateDTO== null) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            return WrapperResponse.success(baseRateBO.insert(baseRateDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method update()
     * @Description 修改医嘱频率信息
     * @Param 1、baseRateDTO：医嘱频率数据参数对象
     * @Author fuhui
     * @Date 2020/7/13 10:23
     * @Return boolean
     **/
    @Override
    @HsafRestPath(value = "/update", method = RequestMethod.PUT)
    public WrapperResponse<Boolean> update(Map map) {
        BaseRateDTO baseRateDTO = MapUtils.get(map, "baseRateDTO");
        try {
            // 参数校验
            if (baseRateDTO== null) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            return WrapperResponse.success(baseRateBO.update(baseRateDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }

    }


    @Override
    @HsafRestPath(value = "/updateIsValid", method = RequestMethod.POST)
    public WrapperResponse<Boolean> updateIsValid(Map map) {
        BaseRateDTO baseRateDTO = MapUtils.get(map, "baseRateDTO");
        try {
            // 参数校验
            if (baseRateDTO== null) {
                return WrapperResponse.error(400,"修改标识符参数不能为空",null);
            }
            return WrapperResponse.success(baseRateBO.updateIsValid(baseRateDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }

    }

    /**
     * @Method queryAll()
     * @Description 查询全部医嘱频率
     * @Param
     * 1、baseRateDTO
     * @Author fuhui
     * @Date 2020/7/13 14:44
     * @Return BaseRateDTO
     **/
    @Override
    @HsafRestPath(value = "/queryAll", method = RequestMethod.GET)
    public WrapperResponse<List<BaseRateDTO>> queryAll(Map map) {
        BaseRateDTO baseRateDTO = MapUtils.get(map, "baseRateDTO");
        return WrapperResponse.success(baseRateBO.queryAll(baseRateDTO));
    }

    /**
     * @Method getByRateCode()
     * @Desrciption 根据频率编码查询医嘱频率信息
     * @Param hospCode医院编码,code:频率编码
     *
     * @Author fuhui
     * @Date   2020/10/22 17:37
     * @Return 频率id
     **/
    @Override
    @HsafRestPath(value = "/getByRateCode", method = RequestMethod.GET)
    public WrapperResponse<String> getByRateCode(Map map) {
        BaseRateDTO baseRateDTO = MapUtils.get(map, "baseRateDTO");
        return WrapperResponse.success(baseRateBO.getByRateCode(baseRateDTO));
    }
}
