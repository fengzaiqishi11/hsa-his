package cn.hsa.inpt.criticalvalues.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.criticalvalues.bo.CriticalValuesBO;
import cn.hsa.module.inpt.criticalvalues.dto.CriticalValueItemDTO;
import cn.hsa.module.inpt.criticalvalues.dto.CriticalValuesDTO;
import cn.hsa.module.inpt.criticalvalues.service.CriticalValuesService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@HsafRestPath("/service/inpt/criticalValues")
@Slf4j
@Service("criticalValuesService_provider")
public class CriticalValuesServiceImpl extends HsafService implements CriticalValuesService {

    @Resource
    private CriticalValuesBO criticalValuesBO;

    /**
     * @Method: queryPage()
     * @Description: 根据就诊id,医院编码查询危急值数据
     * @Param: baseDeptDTO
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2021/1/7 14:14
     * @Return: PageDTO
     */
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        CriticalValuesDTO criticalValuesDTO = MapUtils.get(map,"criticalValuesDTO");
        return WrapperResponse.success(criticalValuesBO.queryPage(criticalValuesDTO));
    }

    /**
     * @Method: updateCriticalValue()
     * @Description: 修改危急值
     * @Param: criticalValuesDTO
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:14
     * @Return: boolean
     */
    @Override
    public WrapperResponse<Boolean> updateCriticalValue(Map map) {
        List<CriticalValuesDTO> criticalValuesDTOList = MapUtils.get(map,"criticalValuesDTOList");
        return WrapperResponse.success(criticalValuesBO.updateCriticalValue(criticalValuesDTOList));
    }

    /**
     * @Method: queryItemByVisitId()
     * @Description: 根据就诊查看检查项目类型，查询医嘱类型是lis医技的
     * @Param: criticalValuesDTO
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:14
     * @Return: boolean
     */
    @Override
    public WrapperResponse<PageDTO> queryItemByVisitId(Map map) {
       CriticalValueItemDTO criticalValueItemDTO =  MapUtils.get(map,"criticalValueItemDTO");
        return WrapperResponse.success(criticalValuesBO.queryItemByVisitId(criticalValueItemDTO));
    }
}
