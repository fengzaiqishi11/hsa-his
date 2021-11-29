package cn.hsa.module.interf.phys.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

public interface PhysRegService {

    /**
     * @Description: 获取体检者登记信息
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     * @Author: zhangxuan
     * @Date: 2021-07-05
     */
    @PostMapping("/service/interf/phys/RegPhysInfo")
    WrapperResponse<Map> regPhysInfo(Map map);
    /**
     * @Method addVisitByPhys
     * @Desrciption 新增登记一次就要 同步一次就诊的数据
     * @Param [physRegisterDTO]
     * @Author zhangguorui
     * @Date   2021/7/14 13:56
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    @PostMapping("/service/interf/phys/addVisitByPhys")
    WrapperResponse<Boolean> addVisitByPhys(Map map);
    /**
     * @Method addOrUpdateOutptCost
     * @Desrciption 批量插入费用表
     * @Param [settleDTOS]
     * @Author zhangguorui
     * @Date 2021/7/14 15:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    @PostMapping("/service/interf/phys/addOrUpdateOutptCost")
    WrapperResponse<Boolean> addOrUpdateOutptCost(Map map);

    /**
     * @Description: 体检组合同步到his项目表
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangxuan
     * @Date: 2021-11-25
     */
    @PostMapping("/service/interf/phys/insertPhysGroup")
    WrapperResponse<Boolean> insertPhysGroup(Map map);


    /**
     * @Description: 插入退费申请
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangxuan
     * @Date: 2021-11-25
     */
    @PostMapping("/service/interf/phys/insertReturn")
    WrapperResponse<Boolean> insertReturn(Map map);
}
