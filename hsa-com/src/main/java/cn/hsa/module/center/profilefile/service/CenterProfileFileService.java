package cn.hsa.module.center.profilefile.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.profilefile.dto.CenterProfileFileDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.profilefile.service
 * @Class_name: CenterProfileFileService
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/8/11 8:58
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-center")
public interface CenterProfileFileService {
    /**
     * @Method getById
     * @Desrciption
     * @Param
     * [centerProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:48
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.profilefile.dto.CenterProfileFileDTO>
     **/
    @GetMapping("/service/center/profileFile/getById")
    WrapperResponse<CenterProfileFileDTO> getById(CenterProfileFileDTO centerProfileFileDTO);

    /**
     * @Method queryAll
     * @Desrciption
     * @Param
     * [centerProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:49
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.center.profilefile.dto.CenterProfileFileDTO>>
     **/
    @GetMapping("/service/center/profileFile/queryAll")
    WrapperResponse<List<CenterProfileFileDTO>> queryAll(CenterProfileFileDTO centerProfileFileDTO);

    /**
     * @Method queryPage
     * @Desrciption
     * @Param
     * [centerProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:49
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/center/profileFile/queryPage")
    WrapperResponse<PageDTO> queryPage(CenterProfileFileDTO centerProfileFileDTO);
}
