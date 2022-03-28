package cn.hsa.stro.stroinvoicing.bo.impl;

import cn.hsa.hsaf.core.framework.util.DateUtil;
import cn.hsa.module.stro.stroinvoicing.bo.StroInvoicingMonthlyBO;
import cn.hsa.module.stro.stroinvoicing.dao.StroInvoicingMonthlyDAO;
import cn.hsa.module.stro.stroinvoicing.dao.StroInvoicingMonthlyDetailDAO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingMonthlyDTO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingMonthlyDetailDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Class_name: StroInvoicingMonthlyBOImpl
 * @Describe:
 * @Author: zhangguorui
 * @Date: 2022/3/18 9:42
 **/
@Component
@Slf4j
public class StroInvoicingMonthlyBOImpl implements StroInvoicingMonthlyBO {
    @Resource
    private StroInvoicingMonthlyDetailDAO stroInvoicingMonthlyDAO;
    @Resource
    private StroInvoicingMonthlyDetailDAO stroInvoicingMonthlyDetailDAO;

    /**
     * @Meth: copyStroInvoicing
     * @Description: 同步进销存数据
     * 药库：
     * 1.查询当天产生的所有进销存，根据项目id、存储方式、科室id进行分组
     * 2.查询库存中，当天没有出现过记录的最近一次的进销存
     * 药房：
     * 1.查询当天产生的所有进销存，根据项目id、存储方式、科室id进行分组
     * 2.查询库存中，当天没有出现过记录的最近一次的进销存
     * @Param: [map]
     * @return: boolean
     * @Author: zhangguorui
     * @Date: 2022/3/18
     */
    @Override
    public boolean copyStroInvoicing(Map map) {
        // 获得当天的日期
        Date date = MapUtils.get(map, "date");
        String hospCode = MapUtils.get(map, "hospCode");
        // 封装请求参数
        StroInvoicingMonthlyDetailDTO stroInvoicingMonthlyDetailDTO = new StroInvoicingMonthlyDetailDTO();
        stroInvoicingMonthlyDetailDTO.setHospCode(hospCode);
        stroInvoicingMonthlyDetailDTO.setDate(date);
        // todo 药库进销存同步begin 这里可以考虑封装成一个方法
        // 1.查询当天产生的所有进销存，根据项目id、存储方式、科室id进行分组
        List<StroInvoicingMonthlyDetailDTO> allStroInvoicingByDateList = stroInvoicingMonthlyDetailDAO.
                getAllStroInvoicingByDate(stroInvoicingMonthlyDetailDTO);
        // 查询出所有的库存，根据项目id进行分组
        List<String> itemIds = stroInvoicingMonthlyDetailDAO.queryItemIdList(stroInvoicingMonthlyDetailDTO);
        // 防止空指针
        if (null == allStroInvoicingByDateList){
            allStroInvoicingByDateList = new ArrayList<>();
        }
        // 过滤出当天有进销存的项目
        List<String> existingItems = allStroInvoicingByDateList.stream().map(StroInvoicingMonthlyDetailDTO::getItemId)
                .collect(Collectors.toList());
        // 过滤当天没有产生的项目 = itemIds - existingItems
        List<String> notNxistingItems = itemIds.stream().filter(x -> !existingItems.contains(x))
                .collect(Collectors.toList());
        // 2.查询库存中，当天没有出现过记录的最近一次的进销存
        stroInvoicingMonthlyDetailDTO.setItemIds(notNxistingItems);
        List<StroInvoicingMonthlyDetailDTO> recentlyStroInvoicingByDateList = stroInvoicingMonthlyDetailDAO.
                getRecentlyStroInvoicingByDate(stroInvoicingMonthlyDetailDTO);
        // 合并两个list
        List<StroInvoicingMonthlyDetailDTO> stroInvoicingMonthlyDetails =
                ListUtils.unionAll(allStroInvoicingByDateList, recentlyStroInvoicingByDateList);
        // todo 药库进销存同步end
        // todo 药房进销存同步begin，这里可以考虑封装成一个方法
        // 1.查询当天产生的所有进销存，根据项目id、存储方式、科室id进行分组
        List<StroInvoicingMonthlyDetailDTO> allStroPharInvoicingByDateList = stroInvoicingMonthlyDetailDAO.
                getAllPharStroInvoicingByDate(stroInvoicingMonthlyDetailDTO);

        // todo 药房进销存同步end
        return false;
    }

    public static void main(String[] args) {

        List<String> lists = new ArrayList<>();
        List<String> lists2 = new ArrayList<>();
        lists.add("1");
        lists.add("2");
        lists.add("3");
        lists.add("4");
        lists2.add("2");
        lists2.add("4");
        List<String> notNxistingItems = lists.stream().filter(x -> !lists2.contains(x))
                .collect(Collectors.toList());
        System.out.println("lists: " + lists.toString());
        System.out.println("notNxistingItems: " + notNxistingItems.toString());

    }
}
