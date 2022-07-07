package cn.hsa.interf.extract.bo.impl;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.interf.extract.bo.ExtractStroInvoicingBO;
import cn.hsa.module.interf.extract.dao.ExtractStroInvoicingDAO;
import cn.hsa.module.interf.extract.dto.ExtractDataDTO;
import cn.hsa.module.interf.extract.entity.ExtractConsumptionDetailDO;
import cn.hsa.util.*;
import cn.hutool.core.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.ResourceTransactionManager;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gory
 * @date 2022/7/6 11:26
 * 数据抽取bo层
 */
@Component
public class ExtractStroInvoicingBOImpl implements ExtractStroInvoicingBO {
    private Logger logger = LoggerFactory.getLogger(ExtractStroInvoicingBOImpl.class);
    @Resource
    private ExtractStroInvoicingDAO extractStroInvoicingDAO;
    /**
     * 开启事务
     */
    @Resource
    private ResourceTransactionManager transactionManager;
    /**
     * @Author gory
     * @Description 数据抽取bo层实现类
     * 1.同步药房药库消耗报表数据
     * 2.同步药房药库实时进销存报表数据
     * 3.同步药房药库业务报表数据
     * @Date 2022/7/6 11:29
     * @Param [map]
     **/
    @Override
    public Boolean insertDataToExtractReport(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        if (StringUtils.isEmpty(hospCode)){
            throw new AppException("同步进销存数据失败，医院编码不能为空");
        }
        logger.info("同步药房药库消耗报表数据，开始时间：" + DateUtils.getNow());
        // todo 1.同步药房药库消耗报表数据
        insertExtractConsumptionDetail(hospCode);
        logger.info("同步药房药库消耗报表数据，结束时间：" + DateUtils.getNow());
        // todo 2.同步药房药库消耗报表数据
        // todo 3.同步药房药库业务报表数据
        return true;
    }
    /**
     * @Author gory
     * @Description 同步药房药库消耗报表数据
     * 1.查询最近同步的时间
     * 2.查询需要同步的数据
     * 3.循环：开启事务，从最近的时间开始同步
     * 4.记录同步的条数，以及花费的时间
     * @Date 2022/7/6 13:51
     * @Param [hospCode]
     **/
    private void insertExtractConsumptionDetail(String hospCode) {
        long begin = System.currentTimeMillis();// 同步开始时间
        // todo 1.查询最近的同步成功时间
        ExtractDataDTO extractDataDTO = new ExtractDataDTO();
        extractDataDTO.setHospCode(hospCode)
                .setExtractType(Constants.CQLX.YFYKXH)
                .setExtractStatus(Constants.SF.S);
        Date recentlyTime = extractStroInvoicingDAO.queryRecentlyExtractTime(extractDataDTO);
        if (null == recentlyTime) {
            recentlyTime = DateUtils.parse(DateUtil.formatDate(DateUtils.getNow()), DateUtils.Y_M_D);

        }
        while (DateUtils.dateCompareAndEquals(recentlyTime, DateUtils.parse(
                DateUtil.formatDate(DateUtils.getNow()), DateUtils.Y_M_D))) {// 最近执行时间小于当前时间
            // 业务主表id
            String extractId = SnowflakeUtils.getId();
            //todo 2.查询需要同步的数据
            Map<String,Object> requestMapIncludeIncMap = new HashMap();
            requestMapIncludeIncMap.put("hospCode",hospCode);
            requestMapIncludeIncMap.put("recentlyTime",recentlyTime);
            requestMapIncludeIncMap.put("includeInc",Constants.SF.S);// 是否包含盘点
            requestMapIncludeIncMap.put("extractId",extractId);// 同步主表id
            Map<String,Object> requestMapIncludeNotIncMap = new HashMap();
            requestMapIncludeNotIncMap.put("hospCode",hospCode);
            requestMapIncludeNotIncMap.put("recentlyTime",recentlyTime);
            requestMapIncludeNotIncMap.put("includeInc",Constants.SF.F);// 是否包含盘点
            requestMapIncludeNotIncMap.put("extractId",extractId);// 同步主表id
            // 含盘点的数量
            List<ExtractConsumptionDetailDO> extractConsumptionDetails = extractStroInvoicingDAO.
                    queryStroInvoicingByComsume(requestMapIncludeIncMap);
            // 不含盘点的数量
            List<ExtractConsumptionDetailDO> extractConsumptionDetailsNoInc = extractStroInvoicingDAO.
                    queryStroInvoicingByComsume(requestMapIncludeNotIncMap);
            // 合并查询结果
            extractConsumptionDetails.addAll(extractConsumptionDetailsNoInc);
            // 设置主键
            extractConsumptionDetails.forEach(extractConsumptionDetailDO ->
                    extractConsumptionDetailDO.setId(SnowflakeUtils.getId()));
            TransactionStatus status = null;
            // todo 3.开启事务
           try{
               DefaultTransactionDefinition def = new DefaultTransactionDefinition();
               def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
               def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
               status = transactionManager.getTransaction(def);
               //插入同步主表
               long end = System.currentTimeMillis();// 同步结束时间
               ExtractDataDTO addExtractDataDTO = new ExtractDataDTO();
               addExtractDataDTO.setHospCode(hospCode)
                       .setExtractType(Constants.CQLX.YFYKXH)
                       .setExtractStatus(Constants.SF.S)
                       .setId(extractId)
                       .setConsumeTime((int) (end - begin))
                       .setExtractNum(extractConsumptionDetails == null ? 0 : extractConsumptionDetails.size())
                       .setExtractTime(DateUtils.getNow());
               extractStroInvoicingDAO.insertExtractData(addExtractDataDTO);
               // 插入明细
                extractStroInvoicingDAO.insertBatchToConsumption(extractConsumptionDetails);
               // 提交独立事务
               transactionManager.commit(status);
           } catch (Exception e) {
               if (status != null) {
                   transactionManager.rollback(status);
               }
               throw new RuntimeException("同步异常，原因：" + e.getMessage());
           }
            recentlyTime = DateUtils.dateAdd(recentlyTime, 1);
        }

    }

    public static void main(String[] args) {

    }
}
