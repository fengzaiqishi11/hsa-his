package cn.hsa.base.bac.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bac.bo.BaseAssistCalcBO;
import cn.hsa.module.base.bac.dto.BaseAssistCalcDTO;
import cn.hsa.module.base.bac.dto.BaseAssistCalcDetailDTO;
import cn.hsa.module.base.bac.service.BaseAssistCalcService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_ame: cn.hsa.base.bfc.service.impl
 * @Class_name: BaseFinanceClassifyserviceImpl
 * @Description: 辅助计费service接口实现层（提供给dubbo调用）
 * @Author: ljh
 * @Email: 307753910@qq.com
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/base/syncassist")
@Slf4j
@Service("baseAssistCalcService_provider")
public class BaseAssistCalcServicelmpl extends HsafService implements BaseAssistCalcService {
    @Resource
    private BaseAssistCalcBO baseAssistCalcBO;

    /**
     * @Menthod queryAll
     * @Desrciption  新增数据
     * @param map
     * @Author ljh
     * @Date   2020/8/6 10:46
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.syncassist.dto.SyncassistDTO>>
     **/
    @HsafRestPath(value = "/queryAll", method = RequestMethod.POST)
    @Override
    public WrapperResponse<List<BaseAssistCalcDTO>> queryAll(Map map) {
        List<BaseAssistCalcDTO> dto = baseAssistCalcBO.queryAll(MapUtils.get(map,"bean"));
        return WrapperResponse.success(dto);
    }

    /**
     * @Menthod insert
     * @Desrciption  修改
     * @param map
     * @Author ljh
     * @Date   2020/8/6 10:46
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @HsafRestPath(value = "/insert", method = RequestMethod.POST)
    @Override
    public WrapperResponse<Boolean> insert(Map map) {

        int dto = baseAssistCalcBO.insert(MapUtils.get(map,"bean"));

        return WrapperResponse.success(dto > 0);
    }

    /**
     * @Menthod update
     * @Desrciption  查询
     * @param map
     * @Author ljh
     * @Date   2020/8/6 10:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @HsafRestPath(value = "/update", method = RequestMethod.POST)
    @Override
    public WrapperResponse<Boolean> update(Map map) {
        int dto = baseAssistCalcBO.update(MapUtils.get(map,"bean"));
        return WrapperResponse.success(dto > 0);
    }

      /**
     * @Menthod deleteById
     * @Desrciption  查询
     * @param map
     * @Author ljh
     * @Date   2020/8/6 10:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @HsafRestPath(value = "/updateStatus", method = RequestMethod.POST)
    @Override
    public WrapperResponse<Boolean> updateStatus(Map map) {

        baseAssistCalcBO.updateStatus(MapUtils.get(map,"bean"));
        return WrapperResponse.success(true);
    }

    /**
     * @Menthod queryPage
     * @Desrciption  查询
     * @param map
     * @Author ljh
     * @Date   2020/8/6 10:44
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @HsafRestPath(value = "/queryPage", method = RequestMethod.POST)
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        PageDTO dto = baseAssistCalcBO.queryPage(MapUtils.get(map,"bean"));
        return WrapperResponse.success(dto);
    }
    /**
     * @Menthod detailqueryPage
     * @Desrciption 删除
     * @param map
     * @Author ljh
     * @Date   2020/8/6 10:43
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @HsafRestPath(value = "/DetailqueryPage", method = RequestMethod.POST)
    @Override
    public WrapperResponse<PageDTO> detailqueryPage(Map map) {
        PageDTO dto = baseAssistCalcBO.detailqueryPage(MapUtils.get(map,"bean"));
        return WrapperResponse.success(dto);
    }

    /**
     * @Method: queryAssists
     * @Description: 查询辅助计费
     * @Param: [baseAssistCalcDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/21 14:12
     * @Return: java.util.List<cn.hsa.module.base.bac.dto.BaseAssistCalcDTO>
     **/
    @Override
    public WrapperResponse<List<BaseAssistCalcDTO>> queryAssists(Map map) {
        return WrapperResponse.success(baseAssistCalcBO.queryAssists(MapUtils.get(map,"baseAssistCalcDTO")));
    }

    /**
     * @Method: queryAssistDetails
     * @Description: 根据编码获取辅助计费明细
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/21 15:30
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bac.dto.BaseAssistCalcDetailDTO>>
     **/
    @Override
    public WrapperResponse<List<BaseAssistCalcDetailDTO>> queryAssistDetails(Map map) {
        return WrapperResponse.success(baseAssistCalcBO.queryAssistDetails(MapUtils.get(map,"baseAssistCalcDTO")));
    }
}
