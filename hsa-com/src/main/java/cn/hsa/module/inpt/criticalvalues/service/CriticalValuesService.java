package cn.hsa.module.inpt.criticalvalues.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.criticalvalues.dto.CriticalValuesDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @PackageName: cn.hsa.module.base.dept.entity
 * @Class_name: BaseDeptDO
 * @Description: 危急值服务层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/01/07 15:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/

@FeignClient(value = "hsa-inpt")
public interface CriticalValuesService {


    /**
     * @Method: queryPage()
     * @Description: 根据就诊id,医院编码查询危急值数据
     * @Param: baseDeptDTO
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2021/1/7 14:14
     * @Return:
     */
    @GetMapping("/service/inpt/criticalValues/queryPage")
    WrapperResponse<PageDTO>queryPage(Map map);

    /**
     * @Method: updateCriticalValue()
     * @Description: 修改危急值
     * @Param: criticalValuesDTO
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:14
     * @Return: boolean
     */
    @PostMapping("/service/inpt/criticalValues/updateCriticalValue")
    WrapperResponse<Boolean> updateCriticalValue(Map map);

    /**
     * @Method: queryItemByVisitId()
     * @Description: 根据就诊查看检查项目类型，查询医嘱类型是lis医技的
     * @Param: criticalValuesDTO
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:14
     * @Return: boolean
     */
    @GetMapping("/service/inpt/criticalValues/queryItemByVisitId")
    WrapperResponse<PageDTO> queryItemByVisitId(Map map);
}
