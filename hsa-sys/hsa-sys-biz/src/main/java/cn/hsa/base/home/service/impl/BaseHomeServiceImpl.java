package cn.hsa.base.home.service.impl;


import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.home.bo.BaseHomeBo;
import cn.hsa.module.base.home.service.BaseHomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 *@Package_name: cn.hsa.base.home.service.impl
 *@Class_name: BaseHomeServiceImpl
 *@Describe: 首页数据查询
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/10/29 9:48
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/base/home")
@Slf4j
@Service("baseHomeService_provider")
public class BaseHomeServiceImpl extends HsafService implements BaseHomeService {

    @Resource
    BaseHomeBo baseHomeBo;

    /**
    * @Method queryHomeShowData
    * @Desrciption 首页数据查询入口
    * @param map
    * @Author liuqi1
    * @Date   2020/10/29 9:52
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    @Override
    public WrapperResponse<Map<String, Object>> queryHomeShowData(Map<String, Object> map) {
        Map<String, Object> resultMap = baseHomeBo.queryHomeShowData(map);
        return WrapperResponse.success(resultMap);
    }
}
