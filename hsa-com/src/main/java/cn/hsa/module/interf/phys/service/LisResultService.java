package cn.hsa.module.interf.phys.service;

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
     * @Description: lis结果数据存库
     * @Param: [map]
     * @return: java.lang.Boolean
     * @Author: zhangxuan
     * @Date: 2021-09-09
     */
    @PostMapping("/service/lis/insertDXLisResult")
    Map insertDXLisResult(Map map);

    /**
    * @Description: 查询没有结果的申请单的医嘱id
    * @Param:
    * @return:
    * @Author: zhangxuan
    * @Date: 2021-09-11
    */
    @PostMapping("/service/lis/queryDXNoResult")
    List<String> queryDXNoResult(Map map);

    /**
     * @Description: 查询退费的医嘱id
     * @Param:
     * @return:
     * @Author: zhangxuan
     * @Date: 2021-09-11
     */
    @PostMapping("/service/lis/queryDXBackResult")
    List<String> queryDXBackResult(Map map);

    /**
    * @Description: 查询没有结果的lis申请单
    * @Param:
    * @return:
    * @Author: zhangxuan
    * @Date: 2021-09-04
    */
    @PostMapping("/service/lis/updateNoResultLis")
    Map updateNoResultLis(Map map);

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
