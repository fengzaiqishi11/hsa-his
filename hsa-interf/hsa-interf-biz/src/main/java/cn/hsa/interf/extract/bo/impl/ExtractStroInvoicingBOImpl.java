package cn.hsa.interf.extract.bo.impl;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.interf.extract.bo.ExtractStroInvoicingBO;
import cn.hsa.module.interf.extract.dao.ExtractStroInvoicingDAO;
import cn.hsa.module.interf.extract.dto.ExtractDataDTO;
import cn.hsa.module.interf.extract.entity.ExtractBusinessDO;
import cn.hsa.module.interf.extract.entity.ExtractConsumptionDetailDO;
import cn.hsa.module.interf.extract.entity.ExtractStroInvoicingDetailDO;
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
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("同步进销存数据失败，医院编码不能为空");
        }
        // todo 1.同步药房药库消耗报表数据
        logger.info("同步药房药库消耗报表数据，开始时间：" + DateUtils.getNow());
        insertExtractConsumptionDetail(hospCode);
        logger.info("同步药房药库消耗报表数据，结束时间：" + DateUtils.getNow());
        // todo 2.同步药房药库实时进销存数据
        logger.info("同步药房药库实时进销存数据，开始时间：" + DateUtils.getNow());
        insertExtractStroInvoicingDetail(hospCode);
        logger.info("同步药房药库实时进销存数据，结束时间：" + DateUtils.getNow());
        // todo 3.同步药房药库业务报表数据
//        logger.info("同步药房药库业务报表数据，开始时间：" + DateUtils.getNow());
//        insertExtractBusiness(hospCode);
//        logger.info("同步药房药库业务报表数据，结束时间：" + DateUtils.getNow());
        return true;
    }
    /**
     * @Author gory
     * @Description同步药房药库业务报表
     * 1.同步库房入库业务统计
     * 2.同步出库到药房汇总统计
     * 3.同步库房报损汇总统计报表
     * 4.同步库房盘点汇总统计报表
     * 5.同步库房调价汇总统计表
     * @Date 2022/7/11 8:26
     * @Param [hospCode]
     **/
    public void insertExtractBusiness(String hospCode) {
        // todo 1.同步库房入库业务统计
        insertExtractBusinessByIn(hospCode);
    }
    /**
     * @Author gory
     * @Description
     * 1.按供应商
     * 2.按供应商/品种
     * 3.按供应商/品种批次
     * 4.按供应商/入库单据
     * 5.按供应商/入库单据明细
     * 6.按供应商/计费类别
     * 7.按供应商/材料分类
     * @Date 2022/7/11 9:14
     * @Param [hospCode]
     **/
    public void insertExtractBusinessByIn(String hospCode){
        //todo 1.按供应商
        insertExtractBusinessBySup(hospCode);

    }
    /**
     * @Author gory
     * @Description
     * 1.查询药房药库实时进销存数据最近同步时间
     * 2.查询需要同步的数据
     * 3.循环：开启事务，从最近的时间开始同步
     * 4.记录同步的条数，以及花费的时间
     * @Date 2022/7/11 9:19
     * @Param [hospCode]
     **/
    public void insertExtractBusinessBySup(String hospCode) {
        long begin = System.currentTimeMillis();// 同步开始时间
        // todo 1.查询最近的同步成功时间
        ExtractDataDTO extractDataDTO = new ExtractDataDTO();
        extractDataDTO.setHospCode(hospCode)
                .setExtractType(Constants.CQLX.AGYS)
                .setExtractStatus(Constants.SF.S);
        Date recentlyTime = extractStroInvoicingDAO.queryRecentlyExtractTime(extractDataDTO);
        if (null == recentlyTime) {
            recentlyTime = DateUtils.parse(DateUtil.formatDate(DateUtils.getNow()), DateUtils.Y_M_D);
        } else {
            recentlyTime = DateUtils.parse(DateUtil.formatDate(recentlyTime), DateUtils.Y_M_D);
        }
        while (recentlyTime.before(DateUtils.parse(
                DateUtil.formatDate(DateUtils.getNow()), DateUtils.Y_M_D))) {// 最近执行时间小于当前时间
            // 业务主表id
            String extractId = SnowflakeUtils.getId();
            //todo 2.查询需要同步的数据
            Map<String, Object> requestMapIncludeIncMap = new HashMap();
            requestMapIncludeIncMap.put("hospCode", hospCode);
            requestMapIncludeIncMap.put("recentlyTime", recentlyTime);
            requestMapIncludeIncMap.put("extractId", extractId);// 同步主表id
            // 查询实时进销存数据
            List<ExtractBusinessDO> extractBusinessList = extractStroInvoicingDAO.
                    queryBusinessBySup(requestMapIncludeIncMap);
            // 设置主键
            extractBusinessList.forEach(extractConsumptionDetailDO ->
                    extractConsumptionDetailDO.setId(SnowflakeUtils.getId()));
            TransactionStatus status = null;
            // todo 3.开启事务
            try {
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
                status = transactionManager.getTransaction(def);
                //插入同步主表
                long end = System.currentTimeMillis();// 同步结束时间
                ExtractDataDTO addExtractDataDTO = new ExtractDataDTO();
                addExtractDataDTO.setHospCode(hospCode)
                        .setExtractType(Constants.CQLX.AGYS)
                        .setExtractStatus(Constants.SF.S)
                        .setId(extractId)
                        .setConsumeTime((int) (end - begin))
                        .setExtractNum(extractBusinessList == null ? 0 : extractBusinessList.size())
                        .setExtractTime(DateUtils.getNow())
                        .setExtractDate(recentlyTime);
                extractStroInvoicingDAO.insertExtractData(addExtractDataDTO);
                // 插入明细
                if (extractBusinessList.size() > 0) {
                    extractStroInvoicingDAO.insertBatchBusiness(extractBusinessList);
                }
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

    /**
     * @Author gory
     * @Description 同步药房药库实时进销存数据
     * 1.查询药房药库实时进销存数据最近同步时间
     * 2.查询需要同步的数据
     * 3.循环：开启事务，从最近的时间开始同步
     * 4.记录同步的条数，以及花费的时间
     * @Date 2022/7/8 8:55
     * @Param [hospCode]
     **/
    public void insertExtractStroInvoicingDetail(String hospCode) {
        long begin = System.currentTimeMillis();// 同步开始时间
        // todo 1.查询最近的同步成功时间
        ExtractDataDTO extractDataDTO = new ExtractDataDTO();
        extractDataDTO.setHospCode(hospCode)
                .setExtractType(Constants.CQLX.YFYKSSJXC)
                .setExtractStatus(Constants.SF.S);
        Date recentlyTime = extractStroInvoicingDAO.queryRecentlyExtractTime(extractDataDTO);
        if (null == recentlyTime) {
            recentlyTime = DateUtils.parse(DateUtil.formatDate(DateUtils.getNow()), DateUtils.Y_M_D);
        } else {
            recentlyTime = DateUtils.parse(DateUtil.formatDate(recentlyTime), DateUtils.Y_M_D);
        }
        recentlyTime = DateUtils.dateAdd(recentlyTime, 1);
        while (recentlyTime.before(DateUtils.parse(
                DateUtil.formatDate(DateUtils.getNow()), DateUtils.Y_M_D))) {// 最近执行时间小于当前时间
            // 业务主表id
            String extractId = SnowflakeUtils.getId();
            //todo 2.查询需要同步的数据
            Map<String, Object> requestMapIncludeIncMap = new HashMap();
            requestMapIncludeIncMap.put("hospCode", hospCode);
            requestMapIncludeIncMap.put("recentlyTime", recentlyTime);
            requestMapIncludeIncMap.put("extractId", extractId);// 同步主表id
            // 查询实时进销存数据
            List<ExtractStroInvoicingDetailDO> stroInvoicingDetails = extractStroInvoicingDAO.
                    queryStroInvoicingByInvoic(requestMapIncludeIncMap);
            // 设置主键
            stroInvoicingDetails.forEach(extractConsumptionDetailDO ->
                    extractConsumptionDetailDO.setId(SnowflakeUtils.getId()));
            TransactionStatus status = null;
            // todo 3.开启事务
            try {
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
                status = transactionManager.getTransaction(def);
                //插入同步主表
                long end = System.currentTimeMillis();// 同步结束时间
                ExtractDataDTO addExtractDataDTO = new ExtractDataDTO();
                addExtractDataDTO.setHospCode(hospCode)
                        .setExtractType(Constants.CQLX.YFYKSSJXC)
                        .setExtractStatus(Constants.SF.S)
                        .setId(extractId)
                        .setConsumeTime((int) (end - begin))
                        .setExtractNum(stroInvoicingDetails == null ? 0 : stroInvoicingDetails.size())
                        .setExtractTime(DateUtils.getNow())
                        .setExtractDate(recentlyTime);
                extractStroInvoicingDAO.insertExtractData(addExtractDataDTO);
                // 插入明细
                if (stroInvoicingDetails.size() > 0) {
                    extractStroInvoicingDAO.insertBatchStroInvoic(stroInvoicingDetails);
                }
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

    /**
     * @Author gory
     * @Description 同步药房药库消耗报表数据
     * 1.查询药房药库消耗报表最近同步的时间
     * 2.查询需要同步的数据
     * 3.循环：开启事务，从最近的时间开始同步
     * 4.记录同步的条数，以及花费的时间
     * @Date 2022/7/6 13:51
     * @Param [hospCode]
     **/
    public void insertExtractConsumptionDetail(String hospCode) {
        long begin = System.currentTimeMillis();// 同步开始时间
        // todo 1.查询最近的同步成功时间
        ExtractDataDTO extractDataDTO = new ExtractDataDTO();
        extractDataDTO.setHospCode(hospCode)
                .setExtractType(Constants.CQLX.YFYKXH)
                .setExtractStatus(Constants.SF.S);
        Date recentlyTime = extractStroInvoicingDAO.queryRecentlyExtractTime(extractDataDTO);
        if (null == recentlyTime) {
            recentlyTime = DateUtils.parse(DateUtil.formatDate(DateUtils.getNow()), DateUtils.Y_M_D);
        } else {
            recentlyTime = DateUtils.parse(DateUtil.formatDate(recentlyTime), DateUtils.Y_M_D);
        }
        recentlyTime = DateUtils.dateAdd(recentlyTime, 1);
        while (recentlyTime.before(DateUtils.parse(
                DateUtil.formatDate(DateUtils.getNow()), DateUtils.Y_M_D))) {// 最近执行时间小于当前时间
            // 业务主表id
            String extractId = SnowflakeUtils.getId();
            //todo 2.查询需要同步的数据
            Map<String, Object> requestMapIncludeIncMap = new HashMap();
            requestMapIncludeIncMap.put("hospCode", hospCode);
            requestMapIncludeIncMap.put("recentlyTime", recentlyTime);
            requestMapIncludeIncMap.put("includeInc", Constants.SF.S);// 是否包含盘点
            requestMapIncludeIncMap.put("extractId", extractId);// 同步主表id
            Map<String, Object> requestMapIncludeNotIncMap = new HashMap();
            requestMapIncludeNotIncMap.put("hospCode", hospCode);
            requestMapIncludeNotIncMap.put("recentlyTime", recentlyTime);
            requestMapIncludeNotIncMap.put("includeInc", Constants.SF.F);// 是否不包含盘点
            requestMapIncludeNotIncMap.put("extractId", extractId);// 同步主表id
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
            try {
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
                        .setExtractTime(DateUtils.getNow())
                        .setExtractDate(recentlyTime);
                extractStroInvoicingDAO.insertExtractData(addExtractDataDTO);
                // 插入明细
                if (extractConsumptionDetails.size() > 0) {
                    // 按100 进行切割，防止一下子插入太多 导致数据库崩溃
                    List<List<ExtractConsumptionDetailDO>> lists = ListUtils.splitList(extractConsumptionDetails, 100);
                    for (List<ExtractConsumptionDetailDO> list:
                         lists) {
                        extractStroInvoicingDAO.insertBatchToConsumption(list);
                    }
                }
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

}
