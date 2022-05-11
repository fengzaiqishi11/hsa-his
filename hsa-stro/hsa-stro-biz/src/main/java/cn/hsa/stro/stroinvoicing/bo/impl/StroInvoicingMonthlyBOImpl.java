package cn.hsa.stro.stroinvoicing.bo.impl;

import cn.hsa.module.stro.stroinvoicing.bo.StroInvoicingMonthlyBO;
import cn.hsa.module.stro.stroinvoicing.dao.StroInvoicingMonthlyDAO;
import cn.hsa.module.stro.stroinvoicing.dao.StroInvoicingMonthlyDetailDAO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingMonthlyDTO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingMonthlyDetailDTO;
import cn.hsa.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.ResourceTransactionManager;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Class_name: StroInvoicingMonthlyBOImpl
 * @Describe: 月度进销存bo
 * @Author: zhangguorui
 * @Date: 2022/3/18 9:42
 **/
@Component
@Slf4j
public class StroInvoicingMonthlyBOImpl implements StroInvoicingMonthlyBO {
    @Resource
    private StroInvoicingMonthlyDAO stroInvoicingMonthlyDAO;
    @Resource
    private StroInvoicingMonthlyDetailDAO stroInvoicingMonthlyDetailDAO;
    /**
     * 开启事务
     */
    @Resource
    private ResourceTransactionManager transactionManager;

    /**
     * @Meth: copyStroInvoicing
     * @Description: 同步进销存数据
     * @Param: [map]
     * @return: boolean
     * @Author: zhangguorui
     * @Date: 2022/3/18
     */
    @Override
    public boolean insertCopyStroInvoicing(Map map) {
        // 获得同步日期
        String nowDate = MapUtils.get(map, "date");
        String hospCode = MapUtils.get(map, "hospCode");
        // 取出当前主表中最近的更新时间
        Date recentTime = stroInvoicingMonthlyDAO.queryRecentlyUpdateTime(DateUtils.parse(nowDate, DateUtils.Y_M_D), hospCode);
        while (DateUtils.dateCompareAndEquals(recentTime, DateUtils.parse(nowDate, DateUtils.Y_M_D))) {// 最近执行时间小于当前时间
            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("date", recentTime);
            requestMap.put("hospCode", hospCode);
            insertBusiness(requestMap);
            recentTime = DateUtils.dateAdd(recentTime, 1);
        }
        return true;
    }

    /**
     * @Meth: business
     * @Description: 同步的业务代码
     * @Param: [map]
     * @return: void
     * @Author: zhangguorui
     * @Date: 2022/4/7
     */
    void insertBusiness(Map map) {

        // 获得同步的日期
        Date date = MapUtils.get(map, "date");
        String hospCode = MapUtils.get(map, "hospCode");
        // 封装请求参数
        StroInvoicingMonthlyDetailDTO stroInvoicingMonthlyDetailDTO = new StroInvoicingMonthlyDetailDTO();
        stroInvoicingMonthlyDetailDTO.setHospCode(hospCode);
        stroInvoicingMonthlyDetailDTO.setNowDate(date);
        // 查询出所有的项目id
        List<String> itemIds = stroInvoicingMonthlyDetailDAO.queryItemIdList(stroInvoicingMonthlyDetailDTO);
        // todo 1.获得药库需要同步的进销存
        List<StroInvoicingMonthlyDetailDTO> stroInvoicingMonthlyDetails =
                getStroInvoicingMonthlyDetails(stroInvoicingMonthlyDetailDTO, itemIds);
        // todo 2.获得药房需要同步的进销存
        List<StroInvoicingMonthlyDetailDTO> stroInvoicingPharMonthlyDetails =
                getStroInvoicingPharMonthlyDetails(date, hospCode, itemIds);
        // todo 3.插入月度进销存表
        // 组合数据
        List<StroInvoicingMonthlyDetailDTO> stroInvoicingDetails =
                ListUtils.union(stroInvoicingMonthlyDetails, stroInvoicingPharMonthlyDetails);
        // 查询月度进销存主表这个月度的记录，分组：item_id、biz_id、crteTime、stroPhar
        StroInvoicingMonthlyDTO stroInvoicingMonthlyDTO = new StroInvoicingMonthlyDTO();
        stroInvoicingMonthlyDTO.setHospCode(hospCode);
        stroInvoicingMonthlyDTO.setNowDate(date);
        TransactionStatus status = null;
        try {
            // 开启独立新事务
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
            status = transactionManager.getTransaction(def);
            List<StroInvoicingMonthlyDTO> stroMonthlyDTOS = stroInvoicingMonthlyDAO.queryAllByDate(stroInvoicingMonthlyDTO);
            if (null == stroMonthlyDTOS) {
                stroMonthlyDTOS = new ArrayList<>();
            }
            // 因为两个集合的数据比较多，双重for效率太低，决定用map
            Map<String, String> stroMonthlyMap = stroMonthlyDTOS.stream().collect(
                    Collectors.toMap(
                            (k -> k.getItemId() + k.getBizId() + DateUtils.format(k.getCrteTime(), DateUtils.Y_M) + k.getStroPhar()),
                            StroInvoicingMonthlyDTO::getId, (oldValue, newValue) -> newValue));
            //未存在的
            List<StroInvoicingMonthlyDetailDTO> notExistMainDataList = new ArrayList<>();
            /**
             * 1.如果某个项目在某个科室下面，而且这个月份已经登记到了月度进销存主表，那么她的明细可以直接setMonthlyId
             * 2.反之，该明细需要生成主表数据，且setMonthlyId，为了不再次遍历，浪费时间和空间，我直接在流中设置了setMonthlyId,
             * 等会就可以利用封装好的list拷贝 直接把未存在的明细集合拷贝成将要新插入的主表集合。
             */
            stroInvoicingDetails.stream().forEach(x -> {
                x.setId(SnowflakeUtils.getId());
                String key = x.getItemId() + x.getBizId() + DateUtils.format(x.getCrteTime(), DateUtils.Y_M) + x.getStroPhar();
                String monthlyId = stroMonthlyMap.get(key);
                if (StringUtils.isNotEmpty(monthlyId)) {
                    x.setMonthlyId(monthlyId);
                } else {
                    x.setMonthlyId(SnowflakeUtils.getId());
                    x.setModifyTime(DateUtils.getNow());
                    notExistMainDataList.add(x);
                }
            });
            // 拷贝
            List<StroInvoicingMonthlyDTO> stroInvoicingMonthlyDTOS = ListUtils.
                    copyList(notExistMainDataList, StroInvoicingMonthlyDTO.class);
            //插入主表,注意主键要用monthlyId这个值，这样就不用再遍历了
            if (!ListUtils.isEmpty(stroInvoicingMonthlyDTOS)){
                stroInvoicingMonthlyDAO.insertBatch(stroInvoicingMonthlyDTOS);
            }
            //插入明细表，插入明细表之前，先删除当前日期的数据，防止重复
            if (!ListUtils.isEmpty(stroInvoicingDetails)){
                stroInvoicingMonthlyDetailDAO.deleteBatch(hospCode, date);
                stroInvoicingMonthlyDetailDAO.insertBatch(stroInvoicingDetails);
            }
            // todo 4.更新主表
            stroInvoicingMonthlyDAO.updateByDate(hospCode, date);
            // 提交独立事务
            transactionManager.commit(status);
        } catch (Exception e) {
            if (status != null) {
                transactionManager.rollback(status);
            }
            throw new RuntimeException("同步进销存异常，原因：" + e.getMessage());
        }
    }

    /**
     * @Meth: getStroInvoicingPharMonthlyDetails
     * @Description: 药房：
     * 1.查询当天产生的所有进销存，根据项目id、存储方式、科室id进行分组
     * 2.查询库存中，当天没有出现过记录的最近一次的进销存
     * @Param: [date, hospCode, itemIds]
     * @return: java.util.List<cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingMonthlyDetailDTO>
     * @Author: zhangguorui
     * @Date: 2022/3/29
     */
    private List<StroInvoicingMonthlyDetailDTO> getStroInvoicingPharMonthlyDetails(
            Date date, String hospCode, List<String> itemIds) {
        // 封装参数，可以继续使用上面的stroInvoicingMonthlyDetailDTO，但是为了后续维护，决定解耦
        StroInvoicingMonthlyDetailDTO pharInvoicingMonthlyDetailDTO = new StroInvoicingMonthlyDetailDTO();
        pharInvoicingMonthlyDetailDTO.setHospCode(hospCode);
        pharInvoicingMonthlyDetailDTO.setNowDate(date);
        // 1.查询当天产生的所有进销存，根据项目id、存储方式、科室id进行分组
        List<StroInvoicingMonthlyDetailDTO> allStroPharInvoicingByDateList = stroInvoicingMonthlyDetailDAO.
                getAllPharStroInvoicingByDate(pharInvoicingMonthlyDetailDTO);
        // 防止空指针
        if (null == allStroPharInvoicingByDateList) {
            allStroPharInvoicingByDateList = new ArrayList<>();
        }
        // 过滤出当天有进销存的项目
        List<String> existingPharItems = allStroPharInvoicingByDateList.stream().map(StroInvoicingMonthlyDetailDTO::getItemId)
                .collect(Collectors.toList());
        // 过滤当天没有产生的项目 = itemIds - existingPharItems
        List<String> notNxistingPharItems = itemIds.stream().filter(x -> !existingPharItems.contains(x))
                .collect(Collectors.toList());
        pharInvoicingMonthlyDetailDTO.setItemIds(notNxistingPharItems);
        // 2.查询库存中，当天没有出现过记录的最近一次的进销存，只需要取上期的期末当作期初，期末还是上期的期末
        List<StroInvoicingMonthlyDetailDTO> recentlyStroPharInvoicingByDateList = stroInvoicingMonthlyDetailDAO.
                getRecentlyStroPharInvoicingByDate(pharInvoicingMonthlyDetailDTO);
        // 合并两个list
        List<StroInvoicingMonthlyDetailDTO> stroInvoicingMonthlyDetailDTOs = ListUtils.
                union(allStroPharInvoicingByDateList, recentlyStroPharInvoicingByDateList);
        return stroInvoicingMonthlyDetailDTOs;
    }

    /**
     * @Meth: getStroInvoicingMonthlyDetails
     * @Description: 获得药库需要同步的进销存集合
     * 1.查询当天产生的所有进销存，根据项目id、存储方式、科室id进行分组
     * 2.查询库存中，当天没有出现过记录的最近一次的进销存
     * @Param: [stroInvoicingMonthlyDetailDTO, itemIds]
     * @return: java.util.List<cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingMonthlyDetailDTO>
     * @Author: zhangguorui
     * @Date: 2022/3/29
     */
    private List<StroInvoicingMonthlyDetailDTO> getStroInvoicingMonthlyDetails(
            StroInvoicingMonthlyDetailDTO stroInvoicingMonthlyDetailDTO, List<String> itemIds) {
        // 1.查询当天产生的所有进销存，根据项目id、存储方式、科室id进行分组
        List<StroInvoicingMonthlyDetailDTO> allStroInvoicingByDateList = stroInvoicingMonthlyDetailDAO.
                getAllStroInvoicingByDate(stroInvoicingMonthlyDetailDTO);
        // 防止空指针
        if (null == allStroInvoicingByDateList) {
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
                ListUtils.union(recentlyStroInvoicingByDateList,allStroInvoicingByDateList);
        return stroInvoicingMonthlyDetails;
    }

    public static void main(String[] args) {

//        long startTime = System.currentTimeMillis(); //获取开始时间
//
//
//        long endTime = System.currentTimeMillis(); //获取结束时间
//
//        System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); //输出程序运行时间
        Date now = DateUtils.getNow();
        Date now2 = DateUtils.getNow();
        boolean b = DateUtils.dateCompareAndEquals(now, now2);
        System.out.println(b);
        System.out.println(now == now2);

    }

}
