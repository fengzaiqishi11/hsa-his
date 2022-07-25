package cn.hsa.module.interf.phys.bo;

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
     * @Description: lis结果数据存库
     * @Param: [map]
     * @return: java.lang.Boolean
     * @Author: zhangxuan
     * @Date: 2021-09-09
     */
    Map insertDXLisResult(Map map);

    /**
    * @Description: 查询无结果的申请单的医嘱id
    * @Param:
    * @return:
    * @Author: zhangxuan
    * @Date: 2021-09-11
    */
    List<String> queryDXNoResult(Map map);


    /**
     * @Description: 查询退费医嘱id
     * @Param:
     * @return:
     * @Author: zhangxuan
     * @Date: 2021-09-11
     */
    List<String> queryDXBackResult(Map map);

    /** 
    * @Description: 查询没有结果的lis申请单
    * @Param: 
    * @return: 
    * @Author: zhangxuan
    * @Date: 2021-09-04
    */ 
    Map updateNoResultLis(Map map);

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
