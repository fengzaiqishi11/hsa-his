package cn.hsa.center.profilefile.controller;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.profilefile.dto.CenterProfileFileDTO;
import cn.hsa.module.center.profilefile.service.CenterProfileFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.center
 * @Class_name: CenterProfileFileController
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/8/11 9:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/center/profileFile")
@Slf4j
public class CenterProfileFileController extends BaseController {

    /**
     * 个人档案维护dubbo消费者接口
     */
    @Resource
    private CenterProfileFileService centerProfileFileService_consumer;

    /**
     * @Method getById
     * @Desrciption
     * @Param
     * [centerProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:48
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.profilefile.dto.CenterProfileFileDTO>
     **/
    @GetMapping("/getById")
    public WrapperResponse<CenterProfileFileDTO> getById(CenterProfileFileDTO centerProfileFileDTO){
        return centerProfileFileService_consumer.getById(centerProfileFileDTO);
    }

    /**
     * @Method queryAll
     * @Desrciption
     * @Param
     * [centerProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:48
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.center.profilefile.dto.CenterProfileFileDTO>>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<CenterProfileFileDTO>> queryAll(CenterProfileFileDTO centerProfileFileDTO){
        return centerProfileFileService_consumer.queryAll(centerProfileFileDTO);
    }

    /**
     * @Method queryPage
     * @Desrciption
     * @Param
     * [centerProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:48
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(CenterProfileFileDTO centerProfileFileDTO){
        return centerProfileFileService_consumer.queryPage(centerProfileFileDTO);
    }
}
