package cn.hsa.stro.inventory.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.stro.incdec.bo.StroIncdecBO;
import cn.hsa.module.stro.incdec.dto.StroIncdecDTO;
import cn.hsa.module.stro.incdec.dto.StroIncdecDetailDTO;
import cn.hsa.module.stro.inventory.bo.InventoryBO;
import cn.hsa.module.stro.inventory.dao.InventoryDAO;
import cn.hsa.module.stro.inventory.dao.StroInventoryDetailDao;
import cn.hsa.module.stro.inventory.dto.InventoryDTO;
import cn.hsa.module.stro.inventory.dto.StroInventoryDetailDTO;
import cn.hsa.module.stro.stock.bo.StroStockBO;
import cn.hsa.module.stro.stock.dao.StroStockDetailDao;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.module.stro.stock.service.StroStockService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Package_name: cn.hsa.stro.inventory.bo.impl
 * @Class_name:: InventoryDOImpl
 * @Description:
 * @Author: ljh
 * @Date: 2020/8/7 11:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class InventoryBOImpl extends HsafBO implements InventoryBO {
    /**
     * 库存变动接口
     */
    @Resource
    StroStockService stroStockService;
    /**
     * 盘点表
     */
    @Resource
    private InventoryDAO inventoryDAO;
    /**
     * 盘点表明细
     */
    @Resource
    private StroInventoryDetailDao stroInventoryDetailDao;

    @Resource
    private StroIncdecBO stroIncdecBO;

    @Resource
    private BaseOrderRuleService baseOrderRuleService_consumer;

    @Resource
    private StroStockDetailDao stroStockDetailDao;

    @Resource
    private StroStockBO stroStockBO;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public InventoryDTO queryById(String id, String hospCode) {
        return inventoryDAO.queryById(id, hospCode);
    }

    /**
     * @param inventoryDTO
     * @Menthod queryAll
     * @Desrciption 通过实体作为筛选条件查询
     * @Author ljh
     * @Date 2020/8/7 11:00
     * @Return java.util.List<cn.hsa.module.stro.inventory.dto.InventoryDTO>
     **/
    @Override
    public List<InventoryDTO> queryAll(InventoryDTO inventoryDTO) {
        return inventoryDAO.queryAll(inventoryDTO);
    }

    /**
     * @Method: insert
     * @Description: 盘点单入库
     * 1.获取单据号
     * 2.入库盘点单
     * 3.入库盘点单明显
     * @Param: [inventoryDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/15 15:18
     * @Return: boolean
     **/
    @Override
    public InventoryDTO insert(InventoryDTO inventoryDTO) {
        if (inventoryDTO == null) {
            throw new AppException("参数为空,盘点失败");
        }
        List<StroInventoryDetailDTO> baseAssistCalcDetaillist = inventoryDTO.getStroInventoryDetailDTOList();
        if (ListUtils.isEmpty(baseAssistCalcDetaillist)) {
            throw new AppException("没有盘点明细记录,盘点失败");
        }

        //获取盘点单据号
        Map map = new HashMap();
        map.put("hospCode", inventoryDTO.getHospCode());
        map.put("typeCode", Constants.ORDERRULE.PD);
        String orderNo = baseOrderRuleService_consumer.getOrderNo(map).getData();
        if (StringUtils.isEmpty(orderNo)) {
            throw new AppException("获取盘点单号失败");
        }

        //盘点单组装参数并入库
        inventoryDTO.setId(SnowflakeUtils.getId());
        inventoryDTO.setAuditCode("0");
        inventoryDTO.setCrteTime(DateUtils.getNow());
        inventoryDTO.setOrderNo(orderNo);

        // 盘点前总金额
        BigDecimal beforePriceAll = BigDecimal.valueOf(0);
        // 盘点后总金额
        BigDecimal afterPriceAll = BigDecimal.valueOf(0);
        //盘点单明细补全参数并入库
        for (StroInventoryDetailDTO stroInventoryDetailDTO:baseAssistCalcDetaillist) {
            if (StringUtils.isEmpty(stroInventoryDetailDTO.getResultCode())) {
                throw new AppException("盘点结论为空");
            }
            if (stroInventoryDetailDTO.getFinalNum() == null) {
                throw new AppException("实盘数量为空");
            }
            if (stroInventoryDetailDTO.getFinalSplitNum() == null) {
              throw new AppException("实盘拆零数量为空");
            }
            if (stroInventoryDetailDTO.getBeforeNum() == null) {
                throw new AppException("盘点前总数量为空");
            }
            stroInventoryDetailDTO.setId(SnowflakeUtils.getId());
            stroInventoryDetailDTO.setInventoryId(inventoryDTO.getId());
            stroInventoryDetailDTO.setHospCode(inventoryDTO.getHospCode());
            // 根据实盘拆零数量算出实盘数量，保留四位小数
            stroInventoryDetailDTO.setFinalNum(BigDecimalUtils.divide(stroInventoryDetailDTO.getFinalSplitNum(),stroInventoryDetailDTO.getSplitRatio()));
            // 根据库存拆零数量算出库存数量，保留四位小数
            stroInventoryDetailDTO.setBeforeNum(BigDecimalUtils.divide(stroInventoryDetailDTO.getBeforeSplitNum(),stroInventoryDetailDTO.getSplitRatio()));
            // 损益数量
            stroInventoryDetailDTO.setIncdecNum(BigDecimalUtils.subtract(stroInventoryDetailDTO.getFinalNum(),stroInventoryDetailDTO.getBeforeNum()));
            // 盘点前总金额
            beforePriceAll = BigDecimalUtils.add(beforePriceAll,BigDecimalUtils.multiply(stroInventoryDetailDTO.getBeforeNum(),stroInventoryDetailDTO.getBuyPrice()));
            // 盘点后总金额
            afterPriceAll = BigDecimalUtils.add(afterPriceAll,BigDecimalUtils.multiply(stroInventoryDetailDTO.getFinalNum(),stroInventoryDetailDTO.getBuyPrice()));
        }
        // 盘点前总金额
        inventoryDTO.setBeforePrice(beforePriceAll);
        // 盘点后总金额
        inventoryDTO.setAfterPrice(afterPriceAll);
        // 损益总金额
        inventoryDTO.setIncdecPrice(BigDecimalUtils.subtract(afterPriceAll,beforePriceAll));
        inventoryDAO.insert(inventoryDTO);
        insertListDetail(baseAssistCalcDetaillist);
        return inventoryDTO;
    }

    /**
     * @param inventoryDTO
     * @Menthod update
     * @Desrciption 修改数据
     * @Author ljh
     * @Date 2020/8/7 11:00
     * @Return int
     **/
    @Override
    public int update(InventoryDTO inventoryDTO) {
        String id = inventoryDTO.getId();
        inventoryDTO.setCrteTime(new Date());
        stroInventoryDetailDao.deleteById(id, inventoryDTO.getHospCode());
        List<StroInventoryDetailDTO> baseAssistCalcDetaillist = inventoryDTO.getStroInventoryDetailDTOList();
        // 盘点前总金额
        BigDecimal beforePriceAll = BigDecimal.valueOf(0);
        // 盘点后总金额
        BigDecimal afterPriceAll = BigDecimal.valueOf(0);
        if(ListUtils.isEmpty(baseAssistCalcDetaillist)) {
          throw new AppException("盘点明细不能为空");
        }
        for (int i = 0; i < baseAssistCalcDetaillist.size(); i++) {
            baseAssistCalcDetaillist.get(i).setInventoryId(id);
            baseAssistCalcDetaillist.get(i).setHospCode(inventoryDTO.getHospCode());
            baseAssistCalcDetaillist.get(i).setId(SnowflakeUtils.getId());
            // 根据实盘拆零数量算出实盘数量，保留四位小数
            baseAssistCalcDetaillist.get(i).setFinalNum(BigDecimalUtils.divide(baseAssistCalcDetaillist.get(i).getFinalSplitNum(),baseAssistCalcDetaillist.get(i).getSplitRatio()));
            // 根据库存拆零数量算出库存数量，保留四位小数
            baseAssistCalcDetaillist.get(i).setBeforeNum(BigDecimalUtils.divide(baseAssistCalcDetaillist.get(i).getBeforeSplitNum(),baseAssistCalcDetaillist.get(i).getSplitRatio()));
            // 损益数量
            baseAssistCalcDetaillist.get(i).setIncdecNum(BigDecimalUtils.subtract(baseAssistCalcDetaillist.get(i).getFinalNum(),baseAssistCalcDetaillist.get(i).getBeforeNum()));
            // 盘点前总金额
            beforePriceAll = BigDecimalUtils.add(beforePriceAll,BigDecimalUtils.multiply(baseAssistCalcDetaillist.get(i).getBeforeNum(),baseAssistCalcDetaillist.get(i).getBuyPrice()));
            // 盘点后总金额
            afterPriceAll = BigDecimalUtils.add(afterPriceAll,BigDecimalUtils.multiply(baseAssistCalcDetaillist.get(i).getFinalNum(),baseAssistCalcDetaillist.get(i).getBuyPrice()));
        }
        stroInventoryDetailDao.insertList(baseAssistCalcDetaillist);
        // 盘点前总金额
        inventoryDTO.setBeforePrice(beforePriceAll);
        // 盘点后总金额
        inventoryDTO.setAfterPrice(afterPriceAll);
        // 损益总金额
        inventoryDTO.setIncdecPrice(BigDecimalUtils.subtract(afterPriceAll,beforePriceAll));
        return inventoryDAO.update(inventoryDTO);
    }


    /**
     * @param inventoryDTO
     * @Menthod updateAuditCode
     * @Desrciption 改变审核状态
     * 1.修改审核信息
     * 2.入库报损表
     * 3.更新库存
     * @Author ljh
     * @Date 2020/8/7 10:57
     * @Return int
     **/
    @Override
    public boolean updateAuditCode(InventoryDTO inventoryDTO) {
      if (ListUtils.isEmpty(inventoryDTO.getIds())) {
          throw new AppException("参数为空");
      }
      String redisKey = new StringBuilder(inventoryDTO.getHospCode()).append(inventoryDTO.getBizId()).
        append(Constants.STRO_INVENTORY_TF_REDIS_KEY).toString();
      if (!redisUtils.setIfAbsent(redisKey,inventoryDTO.getId(),600)){
        throw new AppException("盘点正在进行,请稍后!");
      }
      try {
        //审核流程
        if ("1".equals(inventoryDTO.getAuditCode())) {
          //常用数据
          String hospCode = inventoryDTO.getHospCode();
          String bizId = inventoryDTO.getBizId();
          String auditName = inventoryDTO.getAuditName();
          String auditId = inventoryDTO.getAuditId();
          String auditcode = inventoryDTO.getAuditCode();
          //待更新列表
          List<InventoryDTO> inventorys = new ArrayList<>();
          //库存明细
          List<StroStockDetailDTO> stroStockDetailDTOList = new ArrayList<>();
          //报损明细
          List<StroIncdecDetailDTO> stroIncdecDetailDTOList = new ArrayList<>();
          //根据ID查询盘点单记录
          List<InventoryDTO> inventoryDTOList = inventoryDAO.queryByids(inventoryDTO.getIds(),hospCode);
          if (ListUtils.isEmpty(inventoryDTOList)) {
            throw new AppException("盘点单为空,审核失败");
          }
          // 分批处理数量
          int count = 100;
          //校验是否审核
          for (InventoryDTO outDTO : inventoryDTOList) {
            if (!"0".equals(outDTO.getAuditCode())) {
              throw new AppException("请审核待审核状态的记录,审核失败");
            }
          }
          for (InventoryDTO dto:inventoryDTOList) {
            InventoryDTO inventory = new InventoryDTO();
            inventory.setId(dto.getId());
            inventory.setAuditCode(auditcode);
            inventory.setAuditName(auditName);
            inventory.setAuditId(auditId);
            inventory.setAuditTime(DateUtils.getNow());
            inventorys.add(inventory);
            //根据盘点单ID获取盘点单明细
            List<String> inIds = new ArrayList<>();
            inIds.add(dto.getId());
            List<StroInventoryDetailDTO> stroInventoryDetailDTOList = stroInventoryDetailDao.queryAllid(inIds,hospCode);
            if (ListUtils.isEmpty(stroInventoryDetailDTOList)) {
              throw new AppException("盘点单明细为空,审核失败");
            }
            for (StroInventoryDetailDTO stroInventoryDetailDTO:stroInventoryDetailDTOList) {
              //正常
              if ("1".equals(stroInventoryDetailDTO.getResultCode())) {
                // 库存明细 + 拆零单位、规格、剂型、库位码、订单号、拆零购进价、创建人、创建人ID
                StroStockDetailDTO stroStockDetailDTO = new StroStockDetailDTO();
                stroStockDetailDTO.setHospCode(hospCode);
                stroStockDetailDTO.setBizId(bizId);
                stroStockDetailDTO.setCrteId(auditId);
                stroStockDetailDTO.setCrteName(auditName);
                stroStockDetailDTO.setItemCode(stroInventoryDetailDTO.getItemCode());
                stroStockDetailDTO.setItemId(stroInventoryDetailDTO.getItemId());
                stroStockDetailDTO.setItemName(stroInventoryDetailDTO.getItemName());
                stroStockDetailDTO.setSplitNum(BigDecimalUtils.subtract(stroInventoryDetailDTO.getFinalSplitNum(),stroInventoryDetailDTO.getBeforeSplitNum()));
                stroStockDetailDTO.setNum(stroInventoryDetailDTO.getIncdecNum());
                stroStockDetailDTO.setBuyPrice(stroInventoryDetailDTO.getBuyPrice());
                stroStockDetailDTO.setSellPrice(stroInventoryDetailDTO.getSellPrice());
                stroStockDetailDTO.setBatchNo(stroInventoryDetailDTO.getBatchNo());
                stroStockDetailDTO.setOrderNo(stroInventoryDetailDTO.getOrderno());
                stroStockDetailDTO.setUnitCode(stroInventoryDetailDTO.getUnitCode());
                stroStockDetailDTO.setSplitUnitCode(stroInventoryDetailDTO.getSplitUnitCode());
                stroStockDetailDTO.setSplitRatio(stroInventoryDetailDTO.getSplitRatio());
                stroStockDetailDTO.setHospCode(stroInventoryDetailDTO.getHospCode());
                stroStockDetailDTO.setSplitPrice(stroInventoryDetailDTO.getSplitSellPrice());
                stroStockDetailDTO.setInvoicingTargetName(stroInventoryDetailDTO.getInvoicingTargetName());
                stroStockDetailDTO.setInvoicingTargetId(stroInventoryDetailDTO.getInvoicingTargetId());
                //查询库存明细数据
                StroStockDetailDTO stockDetailDTO =stroStockDetailDao.queryByItemIdAndNo(stroInventoryDetailDTO.getItemCode(),stroInventoryDetailDTO.getItemId(),
                  stroInventoryDetailDTO.getBatchNo(),inventoryDTO.getBizId(),inventoryDTO.getHospCode());
                if (stockDetailDTO != null) {
                  stroStockDetailDTO.setExpiryDate(stockDetailDTO.getExpiryDate());
                  // 库存拆零单价
                  stroStockDetailDTO.setNewSplitPrice(stockDetailDTO.getSplitPrice());
                  // 库存单价
                  stroStockDetailDTO.setNewPrice(stockDetailDTO.getSellPrice());
                }
                stroStockDetailDTOList.add(stroStockDetailDTO);
              } else { //需要报损
                StroIncdecDetailDTO stroIncdecDetailDTO = new StroIncdecDetailDTO();
                stroIncdecDetailDTO.setHospCode(stroInventoryDetailDTO.getHospCode());
                stroIncdecDetailDTO.setItemCode(stroInventoryDetailDTO.getItemCode());
                stroIncdecDetailDTO.setItemId(stroInventoryDetailDTO.getItemId());
                stroIncdecDetailDTO.setItemName(stroInventoryDetailDTO.getItemName());
                stroIncdecDetailDTO.setSellPrice(stroInventoryDetailDTO.getSellPrice());
                stroIncdecDetailDTO.setBuyPrice(stroInventoryDetailDTO.getBuyPrice());
                stroIncdecDetailDTO.setSplitPrice(stroInventoryDetailDTO.getSplitSellPrice());
                stroIncdecDetailDTO.setBatchNo(stroInventoryDetailDTO.getBatchNo());
                stroIncdecDetailDTO.setExpiryDate(stroInventoryDetailDTO.getExpiryDate());
                stroIncdecDetailDTO.setBeforeNum(stroInventoryDetailDTO.getBeforeNum());
                stroIncdecDetailDTO.setSplitRatio(stroInventoryDetailDTO.getSplitRatio());
                stroIncdecDetailDTO.setSplitNum(BigDecimalUtils.multiply(stroInventoryDetailDTO.getIncdecNum(),stroInventoryDetailDTO.getSplitRatio()));
                if(BigDecimalUtils.compareTo(stroInventoryDetailDTO.getIncdecNum(), BigDecimal.valueOf(0)) > 0) {
                  // 如果数量大于0,报溢
                  stroIncdecDetailDTO.setProfitLossType("1");
                  stroIncdecDetailDTO.setNum(stroInventoryDetailDTO.getIncdecNum());
                } else {
                  // 如果数量小于0,报损
                  stroIncdecDetailDTO.setProfitLossType("0");
                  // 转化为正数
                  stroIncdecDetailDTO.setNum(BigDecimalUtils.multiply(stroInventoryDetailDTO.getIncdecNum(), BigDecimal.valueOf(-1)));
                  stroIncdecDetailDTO.setSplitNum(BigDecimalUtils.multiply(stroIncdecDetailDTO.getNum(),stroIncdecDetailDTO.getSplitRatio()));
                }
                stroIncdecDetailDTO.setUnitCode(stroInventoryDetailDTO.getUnitCode());
                stroIncdecDetailDTO.setResultCode(stroInventoryDetailDTO.getResultCode());

                stroIncdecDetailDTO.setSplitUnitCode(stroInventoryDetailDTO.getSplitUnitCode());
                stroIncdecDetailDTO.setSellBeforePrice(BigDecimalUtils.multiply(stroIncdecDetailDTO.getBeforeNum(),stroIncdecDetailDTO.getSellPrice()));
                stroIncdecDetailDTO.setSellAfterPrice(BigDecimalUtils.multiply(stroInventoryDetailDTO.getFinalNum(),stroIncdecDetailDTO.getSellPrice()));
                stroIncdecDetailDTO.setBuyBeforePrice(BigDecimalUtils.multiply(stroIncdecDetailDTO.getBeforeNum(),stroIncdecDetailDTO.getBuyPrice()));
                stroIncdecDetailDTO.setBuyAfterPrice(BigDecimalUtils.multiply(stroInventoryDetailDTO.getFinalNum(),stroIncdecDetailDTO.getBuyPrice()));
                stroIncdecDetailDTOList.add(stroIncdecDetailDTO);
              }
            }
            //进入报损流程
            if (!(ListUtils.isEmpty(stroIncdecDetailDTOList))) {
              StroIncdecDTO stroIncdecDTO = new StroIncdecDTO();
              stroIncdecDTO.setBizId(dto.getBizId());
              stroIncdecDTO.setHospCode(dto.getHospCode());
              stroIncdecDTO.setBeforePrice(dto.getBeforePrice());
              stroIncdecDTO.setAfterPrice(dto.getAfterPrice());
              stroIncdecDTO.setPrice(dto.getIncdecPrice());
              stroIncdecDTO.setRemark(dto.getRemark());
              stroIncdecDTO.setStroIncdecDetailDTOs(stroIncdecDetailDTOList);
              stroIncdecBO.insertOrUpdateStroIncdecDTO(stroIncdecDTO);
            }
          }
          //更新库存
          if (!(ListUtils.isEmpty(stroStockDetailDTOList))) {
            List<StroStockDetailDTO> newList = new ArrayList(); //定义新的list 用于接收每次的值
            for (int i = 0; i < stroStockDetailDTOList.size(); i++) {
              newList.add(stroStockDetailDTOList.get(i));
              if ((count == newList.size()) || (i == (stroStockDetailDTOList.size() - 1))) {
                //如果list的size=cont 或者 刚好是全部数量(说明list数据循环完毕),因为上边从0开始,所以size-1,不然数组越界
                Map map = new HashMap();
                map.put("hospCode", hospCode);
                map.put("type", EnumUtil.CRFS7.getKey());
                map.put("sfBatchNo", "true");
                map.put("stroStockDetailDTOList", newList);
                stroStockBO.saveStock(map);
                newList.clear();
              }
            }
          }
          return inventoryDAO.updateAuditCode(inventorys,hospCode)>0;
        } else if ("2".equals(inventoryDTO.getAuditCode())) { //作废流程
          //根据ID查询盘点单记录
          List<InventoryDTO> inventoryDTOList = inventoryDAO.queryByids(inventoryDTO.getIds(),inventoryDTO.getHospCode());
          if (ListUtils.isEmpty(inventoryDTOList)) {
            throw new AppException("盘点单为空,审核失败");
          }
          //待更新列表
          List<InventoryDTO> inventorys = new ArrayList<>();
          //拼接需要更新审核信息的数据
          for (String id:inventoryDTO.getIds()) {
            InventoryDTO inventory = new InventoryDTO();
            inventory.setId(id);
            inventory.setAuditCode(inventoryDTO.getAuditCode());
            inventory.setAuditName(inventoryDTO.getAuditName());
            inventory.setAuditId(inventoryDTO.getAuditId());
            inventory.setAuditTime(DateUtils.getNow());
            inventorys.add(inventory);
          }
          return inventoryDAO.updateAuditCode(inventorys,inventoryDTO.getHospCode())>0;
        }
      }catch (AppException e) {
        log.error("盘点失败",e.getMessage());
        throw new AppException(e.getMessage());
      }finally {
        redisUtils.del(redisKey);
      }
      return true;
    }

    /**
     * @param inventoryDTO
     * @Menthod queryPage
     * @Desrciption 删除
     * @Author ljh
     * @Date 2020/8/7 10:54
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryPage(InventoryDTO inventoryDTO) {
        PageHelper.startPage(inventoryDTO.getPageNo(), inventoryDTO.getPageSize());

        // 查询所有
        List<InventoryDTO> baseBedDTOList = inventoryDAO.queryAll(inventoryDTO);

        // 返回分页结果
        return PageDTO.of(baseBedDTOList);
    }

    /**
     * @param id
     * @Menthod queryByIdDetail
     * @Desrciption 通过ID查询单条数据
     * @Author ljh
     * @Date 2020/8/7 10:54
     * @Return cn.hsa.module.stro.inventory.dto.StroInventoryDetailDTO
     **/
    @Override
    public StroInventoryDetailDTO queryByIdDetail(String id, String hospCode) {
        return stroInventoryDetailDao.queryById(id, hospCode);
    }

    /**
     * @param stroInventoryDetailDTO
     * @Menthod queryAllDetail
     * @Desrciption 通过实体作为筛选条件查询
     * @Author ljh
     * @Date 2020/8/7 10:54
     * @Return java.util.List<cn.hsa.module.stro.inventory.dto.StroInventoryDetailDTO>
     **/
    @Override
    public List<StroInventoryDetailDTO> queryAllDetail(StroInventoryDetailDTO stroInventoryDetailDTO) {
        return stroInventoryDetailDao.queryAll(stroInventoryDetailDTO);
    }

    /**
     * @param list
     * @Menthod insertListDetail
     * @Desrciption 批量新增所有列
     * @Author ljh
     * @Date 2020/8/7 10:52
     * @Return int
     **/
    @Override
    public int insertListDetail(List<StroInventoryDetailDTO> list) {
        return stroInventoryDetailDao.insertList(list);
    }

    /**
     * @param stroInventoryDetailDTO
     * @Menthod queryPageDetail
     * @Desrciption
     * @Author ljh
     * @Date 2020/8/7 10:51
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryPageDetail(StroInventoryDetailDTO stroInventoryDetailDTO) {
        PageHelper.startPage(stroInventoryDetailDTO.getPageNo(), 1000000);

        // 查询所有
        List<StroInventoryDetailDTO> list = stroInventoryDetailDao.queryAll(stroInventoryDetailDTO);

        // 返回分页结果
        return PageDTO.of(list);
    }

    /**
     * @Method: printInventory
     * @Description: 盘点单打印接口
     * @Param: [inventoryDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/19 9:21
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.inventory.dto.InventoryDTO>
     **/
    @Override
    public List<InventoryDTO> printInventory(InventoryDTO inventoryDTO) {
        if (inventoryDTO == null) {
            throw new AppException("参数为空");
        }
        if (ListUtils.isEmpty(inventoryDTO.getIds())) {
            throw new AppException("参数为空");
        }
        return stroInventoryDetailDao.printInventory(inventoryDTO);
    }
}
