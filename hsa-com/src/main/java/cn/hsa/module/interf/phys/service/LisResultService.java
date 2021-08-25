package cn.hsa.module.interf.phys.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

public interface LisResultService {

    /**
     * @Description: 保存lis结果
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangxuan
     * @Date: 2021-07-05
     */
    @PostMapping("/service/lis/saveLisResult")
    Boolean saveLisResult(Map map);


    /**
     * @Description: lis结果数据存库
     * @Param: [map]
     * @return: java.lang.Boolean
     * @Author: zhangxuan
     * @Date: 2021-07-09
     */
    @PostMapping("/service/lis/insertLisResult")
    Map insertLisResult(Map map);

    /**
     * @Description: 医嘱目录信息
     * @Param: [map]
     * @return: java.util.Map
     * @Author: zhangxuan
     * @Date: 2021-07-19
     */
    @PostMapping("service/lis/queryAdvice")
    Map queryAdvice(Map map);

    /**
     * @Description: 科室信息
     * @Param: [map]
     * @return: java.util.Map
     * @Author: zhangxuan
     * @Date: 2021-07-19
     */
    @PostMapping("service/lis/queryDept")
    Map queryDept(Map map);

    /**
     * @Description: 用户信息
     * @Param: [map]
     * @return: java.util.Map
     * @Author: zhangxuan
     * @Date: 2021-07-19
     */
    @PostMapping("service/lis/queryUser")
    Map queryUser(Map map);
}
