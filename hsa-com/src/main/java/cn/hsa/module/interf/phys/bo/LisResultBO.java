package cn.hsa.module.interf.phys.bo;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;

import java.util.List;
import java.util.Map;

public interface LisResultBO {

    /**
     * @Description: 上传lis结果
     * @Param: [map]
     * @return: java.lang.Boolean
     * @Author: zhangxuan
     * @Date: 2021-07-05
     */
    Boolean saveLisResult(Map map);

    /**
     * @Description: lis结果数据存库
     * @Param: [map]
     * @return: java.lang.Boolean
     * @Author: zhangxuan
     * @Date: 2021-07-09
     */
    Map insertLisResult(Map map);

    /**
     * @Description: 医嘱目录信息
     * @Param: [map]
     * @return: java.util.Map
     * @Author: zhangxuan
     * @Date: 2021-07-19
     */
    Map queryAdvice(Map map);

    /**
     * @Description: 科室信息
     * @Param: [map]
     * @return: java.util.Map
     * @Author: zhangxuan
     * @Date: 2021-07-19
     */
    Map queryDept(Map map);

    /**
     * @Description: 用户信息
     * @Param: [map]
     * @return: java.util.Map
     * @Author: zhangxuan
     * @Date: 2021-07-19
     */
    Map queryUser(Map map);
}
