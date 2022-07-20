package cn.hsa.interf.extract.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.interf.extract.bo.ExtractConsumptionBO;
import cn.hsa.module.interf.extract.dao.ExtraConsumptionDAO;
import cn.hsa.module.interf.extract.dto.ExtractConsumptionDTO;
import cn.hsa.util.FastJsonUtils;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
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
        if (StringUtils.isEmpty(extractConsumptionDTO.getSummaryType())){
            throw new AppException("汇总类型不能为空");
        }
        if (ListUtils.isEmpty(extractConsumptionDTO.getDeptList())){
            throw new AppException("筛选科室不能为空");
        }
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
        List<Map> resultList = new ArrayList<>();
        for (ExtractConsumptionDTO extractConsumption: extractConsumptions) {
            Map<String, Object> resultMap = FastJsonUtils.newBeanToMap(extractConsumption);
            List<ExtractConsumptionDTO> consumptions = MapUtils.get(comsumptionMap, extractConsumption.getItemId());
            for (ExtractConsumptionDTO e: consumptions) {
                // 封装属性，行转列
                resultMap.put( "consumNum" + e.getBizId(),e.getConsumNum());// 消耗数量
                resultMap.put( "sellPriceAll" + e.getBizId(),e.getSellPriceAll());// 零售金额
                resultMap.put( "avgSellPrice" + e.getBizId(),e.getAvgSellPrice());// 平均售价
                resultMap.put( "avgBuyPrice" + e.getBizId(),e.getAvgBuyPrice());// 成本价
                resultMap.put( "buyPriceAll" + e.getBizId(),e.getBuyPriceAll());// 成本金额
                resultMap.put( "profitPrice" + e.getBizId(),e.getProfitPrice());// 盈利
                resultList.add(resultMap);
            }
        }
        // 手动分页
        return PageDTO.ofByManual(resultList,extractConsumptionDTO.getPageNo(),extractConsumptionDTO.getPageSize());
    }
}
