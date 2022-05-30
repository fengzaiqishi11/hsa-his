package cn.hsa.center.profilefile.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.profilefile.bo.CenterProfileFileBO;
import cn.hsa.module.center.profilefile.dto.CenterProfileFileDTO;
import cn.hsa.module.center.profilefile.service.CenterProfileFileService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.center.profilefile.service.impl
 * @Class_name: OutptProfileFileServiceImpl
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/8/11 9:08
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/center/profileFile")
@Slf4j
@Service("centerProfileFileService_provider")
public class CenterProfileFileServiceImpl extends HsafService implements CenterProfileFileService {

    /**
     * 个人档案bo接口
     */
    @Resource
    private CenterProfileFileBO centerProfileFileBO;

    /**
     * @Method getById
     * @Desrciption
     * @Param
     * [centerProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:50
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.profilefile.dto.CenterProfileFileDTO>
     **/
    @Override
    @GetMapping("/service/center/profileFile/getById")
    public WrapperResponse<CenterProfileFileDTO> getById(CenterProfileFileDTO centerProfileFileDTO) {
        return  WrapperResponse.success(centerProfileFileBO.getById(centerProfileFileDTO));
    }

    /**
     * @Method queryAll
     * @Desrciption
     * @Param
     * [centerProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:50
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.center.profilefile.dto.CenterProfileFileDTO>>
     **/
    @Override
    @GetMapping("/service/center/profileFile/queryAll")
    public WrapperResponse<List<CenterProfileFileDTO>> queryAll(CenterProfileFileDTO centerProfileFileDTO) {
        return WrapperResponse.success(centerProfileFileBO.queryAll(centerProfileFileDTO));
    }

    /**
     * @Method queryPage
     * @Desrciption
     * @Param
     * [centerProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:50
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    @GetMapping("/service/center/profileFile/queryPage")
    public WrapperResponse<PageDTO> queryPage(CenterProfileFileDTO centerProfileFileDTO) {
        return WrapperResponse.success(centerProfileFileBO.queryPage(centerProfileFileDTO));
    }
}
