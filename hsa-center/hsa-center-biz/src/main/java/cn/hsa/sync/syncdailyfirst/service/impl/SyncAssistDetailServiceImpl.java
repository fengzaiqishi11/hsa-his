package cn.hsa.sync.syncdailyfirst.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.syncassistdetail.bo.SyncAssistDetailBO;
import cn.hsa.module.center.syncassistdetail.dto.SyncAssistDetailDTO;
import cn.hsa.module.center.syncassistdetail.service.SyncAssistDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

;

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
public class SyncAssistDetailServiceImpl extends HsafService implements SyncAssistDetailService {

    @Resource
    private SyncAssistDetailBO syncAssistDetailBO;



    @HsafRestPath(value = "/queryAll", method = RequestMethod.POST)
    @Override
    public WrapperResponse<List<SyncAssistDetailDTO>> queryAll(SyncAssistDetailDTO syncAssistDetailDTO) {


        return WrapperResponse.success(syncAssistDetailBO.queryAll(syncAssistDetailDTO));
    }


    @HsafRestPath(value = "/insert", method = RequestMethod.POST)
    @Override
    public WrapperResponse<Boolean> insert(List<SyncAssistDetailDTO> syncAssistDetailDTO) {
        syncAssistDetailBO.insert(syncAssistDetailDTO);
        return WrapperResponse.success(true);
    }


    @HsafRestPath(value = "/deleteById", method = RequestMethod.GET)
    @Override
    public WrapperResponse<Boolean> deleteById(SyncAssistDetailDTO syncAssistDetailDTO) {
        syncAssistDetailBO.deleteBylist(syncAssistDetailDTO);
        return WrapperResponse.success(true);
    }

    @Override
    public WrapperResponse<PageDTO> queryPage(SyncAssistDetailDTO syncAssistDetailDTO) {
        PageDTO dto = syncAssistDetailBO.queryPage(syncAssistDetailDTO);
        return WrapperResponse.success(dto);
    }


}
