package cn.hsa.module.inpt.criticalvalues.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.criticalvalues.dto.CriticalValueItemDTO;
import cn.hsa.module.inpt.criticalvalues.dto.CriticalValuesDTO;

import java.util.List;

public interface CriticalValuesBO {

    /**
     * @Method: updateCriticalValue()
     * @Description: 修改危急值
     * @Param: criticalValuesDTO
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:14
     * @Return: boolean
     */
   PageDTO queryPage(CriticalValuesDTO criticalValuesDTO);

    /**
     * @Method: updateCriticalValue()
     * @Description: 修改危急值
     * @Param: criticalValuesDTO
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:14
     * @Return: boolean
     */
    boolean updateCriticalValue(List<CriticalValuesDTO> criticalValuesDTO);


    /**
     * @Method: queryItemByVisitId()
     * @Description: 根据就诊查看检查项目类型，查询医嘱类型是lis医技的
     * @Param: criticalValuesDTO
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:14
     * @Return: boolean
     */
    PageDTO queryItemByVisitId(CriticalValueItemDTO criticalValueItemDTO);
}
