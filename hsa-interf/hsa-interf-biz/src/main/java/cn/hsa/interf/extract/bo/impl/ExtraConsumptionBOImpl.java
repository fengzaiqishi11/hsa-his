package cn.hsa.interf.extract.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.module.interf.extract.bo.ExtractConsumptionBO;
import cn.hsa.module.interf.extract.dao.ExtraConsumptionDAO;
import cn.hsa.module.interf.extract.dto.ExtractConsumptionDTO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingMonthlyDTO;
import cn.hsa.util.MapUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author gory
 * @date 2022/7/12 9:46
 */
@Component
public class ExtraConsumptionBOImpl implements ExtractConsumptionBO {
    @Resource
    private ExtraConsumptionDAO extraConsumptionDAO;
    /**
     * @Author gory
     * @Description 查询消耗报表
     * 1. 先按项目id进行分组 获得这段时间内，涉及到库房的数据
     * 2. 再按项目id和科室id进行分组，获得list
     * 3. 把第二步获得的list 根据项目id 进行分组，获得Map<String,List>,key是项目id
     * 4. 遍历第一步中的集合数据，通过第三步中获得的Map获取到每一个项目下面的list，进行add
     * 5. 返回数据
     * @Date 2022/7/12 9:46
     * @Param [extractConsumptionDTO]
     **/
    @Override
    public PageDTO queryExtractConsumptions(ExtractConsumptionDTO extractConsumptionDTO) {
        PageHelper.startPage(extractConsumptionDTO.getPageNo(),extractConsumptionDTO.getPageSize());
        // todo 1.根据项目id进行分组汇总
        List<ExtractConsumptionDTO> extractConsumptions = extraConsumptionDAO.
                queryExtractConsumptionsByItemId(extractConsumptionDTO);
        // todo 2.根据项目id和科室id进行分组
        List<ExtractConsumptionDTO> extractConsumptionDTOS = extraConsumptionDAO.
                queryExtractConsumptions(extractConsumptionDTO);
        // todo 3. 把extractConsumptionDTOS进行分组，key为item_id
        Map<String, List<ExtractConsumptionDTO>> comsumptionMap = extractConsumptionDTOS.stream().
                collect(Collectors.groupingBy(ExtractConsumptionDTO::getItemId));
        // todo 4. 遍历第一步中的集合数据，通过第三步中获得的Map获取到每一个项目下面的list，进行add
        for (ExtractConsumptionDTO extractConsumption: extractConsumptions) {
            List<ExtractConsumptionDTO> consumptions = MapUtils.get(comsumptionMap, extractConsumption.getItemId());
            for (ExtractConsumptionDTO e: consumptions) {
                Map<String, ExtractConsumptionDTO> extractConsumptionMap = new HashMap<>();
                extractConsumptionMap.put(e.getBizId(),e);
                extractConsumption.setExtractConMap(extractConsumptionMap);
            }
        }
        return PageDTO.of(extractConsumptions);
    }
}
