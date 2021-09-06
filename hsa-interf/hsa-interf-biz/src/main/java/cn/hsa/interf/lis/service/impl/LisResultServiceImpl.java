package cn.hsa.interf.lis.service.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.phys.bo.LisResultBO;
import cn.hsa.module.interf.phys.service.LisResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@HsafRestPath("/service/interf/lis")
@Slf4j
@Service("lisResultService_provider")
public class LisResultServiceImpl extends HsafBO implements LisResultService {

    @Resource
    private LisResultBO lisResultBO;

    /**
     * @Description: 保存lis结果
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangxuan
     * @Date: 2021-07-05
     */
    @Override
    public Boolean saveLisResult(Map map) {
        return lisResultBO.saveLisResult(map);
    }

    /**
     * @Description: lis结果数据存库
     * @Param: [map]
     * @return: java.lang.Boolean
     * @Author: zhangxuan
     * @Date: 2021-07-09
     */
    @Override
    public Map insertLisResult(Map map) {
        return lisResultBO.insertLisResult(map);
    }

    /**
    * @Description: 查询没有结果的lis申请单
    * @Param:
    * @return:
    * @Author: zhangxuan
    * @Date: 2021-09-04
    */
    @Override
    public Map queryNoResultLis(Map map){
        return lisResultBO.queryNoResultLis(map);
    }

    /**
     * @Description: 医嘱目录信息
     * @Param: [map]
     * @return: java.util.Map
     * @Author: zhangxuan
     * @Date: 2021-07-19
     */
    @Override
    public Map queryAdvice(Map map){
        return lisResultBO.queryAdvice(map);
    }

    /**
     * @Description: 科室信息
     * @Param: [map]
     * @return: java.util.Map
     * @Author: zhangxuan
     * @Date: 2021-07-19
     */
    @Override
    public Map queryDept(Map map){
        return lisResultBO.queryDept(map);
    }

    /**
     * @Description: 用户信息
     * @Param: [map]
     * @return: java.util.Map
     * @Author: zhangxuan
     * @Date: 2021-07-19
     */
    @Override
    public Map queryUser(Map map){
        return lisResultBO.queryUser(map);
    }
}
