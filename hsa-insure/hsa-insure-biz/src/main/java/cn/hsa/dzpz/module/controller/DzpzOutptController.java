package cn.hsa.dzpz.module.controller;

import cn.hsa.dzpz.module.service.impl.DzpzOutptServiceImpl;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.dzpz.module.controller
 * @Class_name: DzpzOutptController
 * @Describe:
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2021/2/6 12:25
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@Slf4j
@RequestMapping("/service/insure/dzpzOutpt")
public class DzpzOutptController {

    @Resource
    DzpzOutptServiceImpl dzpzOutptService;

    /**
     * @Menthod saveUpLoadFeeResult
     * @Desrciption 医保结算成功的回调接口
     * @param param 请求参数
     * @Author xingyu.xie
     * @Date 2020/11/26 10:07
     * @Return java.util.Map<String,Object><java.lang.String,java.lang.Object>
     */
    @PostMapping("/saveUpLoadFeeResult")
    public WrapperResponse saveUpLoadFeeResult(@RequestBody Map<String,Object> param) {
        return dzpzOutptService.saveUpLoadFeeResult(param);
    }
}
