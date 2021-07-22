package cn.hsa.sync.syncassist.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.syncassist.bo.SyncassistBO;
import cn.hsa.module.center.syncassist.dto.SyncassistDTO;
import cn.hsa.module.center.syncassist.dto.SyncassistDetailDTO;
import cn.hsa.module.center.syncassist.service.SyncassistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

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
public class SyncassistServicelmpl extends HsafService implements SyncassistService {
    @Resource
    private SyncassistBO syncassistBO;

    /**
     * @Menthod queryAll
     * @Desrciption  查询
     * @param syncassistDTO
     * @Author ljh
     * @Date   2020/8/6 10:46
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.syncassist.dto.SyncassistDTO>>
     **/
    @HsafRestPath(value = "/queryAll", method = RequestMethod.POST)
    @Override
    public WrapperResponse<List<SyncassistDTO>> queryAll(SyncassistDTO syncassistDTO) {
        List<SyncassistDTO> dto = syncassistBO.queryAll(syncassistDTO);
        return WrapperResponse.success(dto);
    }

    /**
     * @Menthod insert
     * @Desrciption  新增
     * @param syncassistDTO
     * @Author ljh
     * @Date   2020/8/6 10:46
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @HsafRestPath(value = "/insert", method = RequestMethod.POST)
    @Override
    public WrapperResponse<Boolean> insert(SyncassistDTO syncassistDTO) {

        int dto = syncassistBO.insert(syncassistDTO);

        return WrapperResponse.success(dto > 0);
    }

    /**
     * @Menthod update
     * @Desrciption  修改
     * @param syncassistDTO
     * @Author ljh
     * @Date   2020/8/6 10:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @HsafRestPath(value = "/update", method = RequestMethod.POST)
    @Override
    public WrapperResponse<Boolean> update(SyncassistDTO syncassistDTO) {
        int dto = syncassistBO.update(syncassistDTO);
        return WrapperResponse.success(dto > 0);
    }

      /**
     * @Menthod deleteById
     * @Desrciption  删除
     * @param syncassistDTO
     * @Author ljh
     * @Date   2020/8/6 10:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
      @HsafRestPath(value = "/updateStatus", method = RequestMethod.POST)
    @Override
      public WrapperResponse<Boolean> updateStatus(SyncassistDTO syncassistDTO) {

          syncassistBO.updateStatus(syncassistDTO);
        return WrapperResponse.success(true);
    }

    /**
     * @Menthod queryPage
     * @Desrciption  查询
     * @param syncassistDTO
     * @Author ljh
     * @Date   2020/8/6 10:44
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @HsafRestPath(value = "/queryPage", method = RequestMethod.POST)
    @Override
    public WrapperResponse<PageDTO> queryPage(SyncassistDTO syncassistDTO) {
        PageDTO dto = syncassistBO.queryPage(syncassistDTO);
        return WrapperResponse.success(dto);
    }
    /**
     * @Menthod detailqueryPage
     * @Desrciption 查询
     * @param
     * @Author ljh
     * @Date   2020/8/6 10:43
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @HsafRestPath(value = "/DetailqueryPage", method = RequestMethod.POST)
    @Override
    public WrapperResponse<PageDTO> detailqueryPage(SyncassistDetailDTO syncassistDetailDTO) {
        PageDTO dto = syncassistBO.detailqueryPage(syncassistDetailDTO);
        return WrapperResponse.success(dto);
    }
}
