package cn.hsa.interf.extract.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.interf.extract.bo.ExtractConsumptionBO;
import cn.hsa.module.interf.extract.dao.ExtraConsumptionDAO;
import cn.hsa.module.interf.extract.dto.ExtractConsumptionDTO;
import cn.hsa.module.interf.extract.dto.ExtractStroInvoicingDetailDTO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingMonthlyDTO;
import cn.hsa.util.FastJsonUtils;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
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
            }
            resultList.add(resultMap);
        }
        // 手动分页
        return PageDTO.ofByManual(resultList,extractConsumptionDTO.getPageNo(),extractConsumptionDTO.getPageSize());
    }

    @Override
    public PageDTO extractStroInvoicingDetailDTO(ExtractStroInvoicingDetailDTO extractStroInvoicingDetailDTO) {

        if(StringUtils.isEmpty(extractStroInvoicingDetailDTO.getBizId())){
            throw new AppException("库位不能为空!");
        }
        if (StringUtils.isEmpty(extractStroInvoicingDetailDTO.getBuyOrSell())){
            throw new AppException("统计选项不能为空");
        }

        //   type 0:药库实时进销存查询,1:药房实时进销存查询
        List<ExtractStroInvoicingDetailDTO> list = null ;
        PageHelper.startPage(extractStroInvoicingDetailDTO.getPageNo(),extractStroInvoicingDetailDTO.getPageSize());
        if("0".equals(extractStroInvoicingDetailDTO.getType())){
             //0：零售价，1：购进价
            if("0".equals(extractStroInvoicingDetailDTO.getBuyOrSell())){
                list = extraConsumptionDAO.queryStroInvoicingSell(extractStroInvoicingDetailDTO);
            }else{
                list = extraConsumptionDAO.queryStroInvoicingBuy(extractStroInvoicingDetailDTO);
            }


        }else if("1".equals(extractStroInvoicingDetailDTO.getType())){
            //0：零售价，1：购进价
            if("0".equals(extractStroInvoicingDetailDTO.getBuyOrSell())){
                list = extraConsumptionDAO.queryRoomInvoicingSell(extractStroInvoicingDetailDTO);
            }else{
                list = extraConsumptionDAO.queryRoomInvoicingBuy(extractStroInvoicingDetailDTO);
            }
        }
        List<ExtractStroInvoicingDetailDTO> newList  = null ;

        // 筛选ItemId 为空的数据  说明这段时间内无次药品的数据，需要找到对于药品最新的一条记录作为数据
        if(!ListUtils.isEmpty(list)){
            //保留 itemId不为空的
            newList = list.stream().filter(o ->StringUtils.isNotEmpty(o.getItemId())).collect(Collectors.toList());
            //替换 itemName为空的
            List<String> ids = list.stream().filter(o ->StringUtils.isEmpty(o.getItemId())).map(ExtractStroInvoicingDetailDTO::getId).collect(Collectors.toList());
            if(!ListUtils.isEmpty(ids)){
                extractStroInvoicingDetailDTO.setIds(ids);
                newList.addAll(extraConsumptionDAO.queryExtraInvoicingByItemId(extractStroInvoicingDetailDTO));
            }
            list.clear();
            list.addAll(newList) ;
        }

        return PageDTO.of(list);
    }
}
