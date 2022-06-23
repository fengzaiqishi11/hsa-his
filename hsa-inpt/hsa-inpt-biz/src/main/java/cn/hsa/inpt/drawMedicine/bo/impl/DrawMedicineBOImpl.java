package cn.hsa.inpt.drawMedicine.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.baseorderreceive.dto.BaseOrderReceiveDTO;
import cn.hsa.module.base.baseorderreceive.service.BaseOrderReceiveService;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.inpt.doctor.bo.DoctorAdviceBO;
import cn.hsa.module.inpt.doctor.dao.InptAdviceDAO;
import cn.hsa.module.inpt.doctor.dao.InptCostDAO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.drawMedicine.bo.DrawMedicineBO;
import cn.hsa.module.inpt.medical.bo.MedicalAdviceBO;
import cn.hsa.module.inpt.medical.dto.MedicalAdviceDTO;
import cn.hsa.module.inpt.medical.service.MedicalAdviceService;
import cn.hsa.module.phar.pharapply.service.PharApplyService;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDetailDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO;
import cn.hsa.module.phar.pharinbackdrug.entity.PharInWaitReceiveDO;
import cn.hsa.module.phar.pharinbackdrug.service.InBackDrugService;
import cn.hsa.module.phar.pharinbackdrug.service.PharInWaitReceiveService;
import cn.hsa.util.*;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.ResourceTransactionManager;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.inpt.drawMedicine.bo.impl
 * @Class_name: DrawMedicineBOImpl
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/9/11 16:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class DrawMedicineBOImpl implements DrawMedicineBO {

  @Resource
  PharInWaitReceiveService pharInWaitReceiveService_consumer;
  @Resource
  PharApplyService pharApplyService;
  @Resource
  BaseOrderRuleService baseOrderRuleService;
  @Resource
  BaseOrderReceiveService baseOrderReceiveService;
  @Resource
  InBackDrugService inBackDrugService;
  @Resource
  InptCostDAO inptCostDAO;
  @Resource
  InptAdviceDAO inptAdviceDAO;
  //医生站
  @Resource
  private DoctorAdviceBO doctorAdviceBO;
  @Resource
  private MedicalAdviceService medicalAdviceService_consumer;

  @Resource
  private RedisUtils redisUtils;

  /**
   * 开启事务
   */
  @Resource
  private ResourceTransactionManager transactionManager;

  /**
   * @Method beforehandDrawMedicine
   * @Desrciption 预配药
   * @params [billList]
   * @Author chenjun
   * @Date 2020/9/11 16:47
   * @Return java.lang.Boolean
   **/
  @Override
  public Boolean saveBeforehandDrawMedicine(Map map) {
    String hospCode = MapUtils.get(map, "hospCode");
    String id = MapUtils.get(map, "id");
    String deptId = MapUtils.get(map, "deptId");

    Map adviceMap = new HashMap();
    adviceMap.put("deptId",deptId);
    adviceMap.put("hospCode", hospCode);
    adviceMap.put("typeCode", id);
    String key = hospCode + deptId + "_MEDICALADVICE";
    try {
      if(!redisUtils.setIfAbsent(key,deptId,600)) {
        throw new AppException("有人正在进行预配药,请稍后再试!");
      }
      List<InptAdviceDTO> inptAdviceDTOList = inptAdviceDAO.queryInptAdviceAdvanceTake(adviceMap);
      if(!ListUtils.isEmpty(inptAdviceDTOList)){
        List<String> adviceIds = inptAdviceDTOList.stream().map(InptAdviceDTO::getId).distinct().collect(Collectors.toList());
        TransactionStatus status = null;
        try{
          // 开启独立新事务
          DefaultTransactionDefinition def = new DefaultTransactionDefinition();
          def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
          status = transactionManager.getTransaction(def);
          //根据提前领药天数,跑一遍长期费用接口
          MedicalAdviceDTO medicalAdviceDTO = new MedicalAdviceDTO();
          medicalAdviceDTO.setHospCode(hospCode);
          medicalAdviceDTO.setDeptId(deptId);
          medicalAdviceDTO.setCheckTime(DateUtils.getNow());
          medicalAdviceDTO.setCheckName(MapUtils.getVS(map,"crteName",""));
          medicalAdviceDTO.setCheckId(MapUtils.getVS(map,"crteId",""));
          medicalAdviceDTO.setSfTqly("1");
          medicalAdviceDTO.setIds(adviceIds);
          adviceMap.put("medicalAdviceDTO", medicalAdviceDTO);
          MedicalAdviceBO bean = SpringUtils.getBean(MedicalAdviceBO.class);
          bean.modifyLongCost(medicalAdviceDTO);
          medicalAdviceService_consumer.longCost(adviceMap);
          //回写提前领药天数为0
          adviceMap.put("advanceDays", "0");
          saveAdvanceTakeMedicine(adviceMap,"ypy");
          // 提交独立事务
          transactionManager.commit(status);
        }catch (RuntimeException e){
          if (status != null) {
            transactionManager.rollback(status);
          }
          throw new RuntimeException("提前领药异常，原因："+e.getMessage());
        }
      }
      Map baseOrderReceiveMap = new HashMap();
      BaseOrderReceiveDTO baseOrderReceiveDTO = new BaseOrderReceiveDTO();
      baseOrderReceiveDTO.setHospCode(hospCode);
      baseOrderReceiveDTO.setId(id);
      baseOrderReceiveMap.put("hospCode", hospCode);
      baseOrderReceiveMap.put("baseOrderReceiveDTO", baseOrderReceiveDTO);
      WrapperResponse<BaseOrderReceiveDTO> response = baseOrderReceiveService.getById(baseOrderReceiveMap);
      BaseOrderReceiveDTO orderReceiveDTO = response.getData();
      //获取所有待领药品 按科室  请领标志0  有效标志
      Map queryWaitReceiveMap = new HashMap();
      PharInWaitReceiveDTO pharInWaitReceiveDTO = new PharInWaitReceiveDTO();
      pharInWaitReceiveDTO.setHospCode(hospCode);
      pharInWaitReceiveDTO.setStatusCode("0");
      pharInWaitReceiveDTO.setDeptId(MapUtils.get(map, "deptId"));
      pharInWaitReceiveDTO.setIsBack("0");
      queryWaitReceiveMap.put("hospCode", hospCode);
      queryWaitReceiveMap.put("pharInWaitReceiveDTO", pharInWaitReceiveDTO);
      //查询所有待领药品
      WrapperResponse<List<PharInWaitReceiveDTO>> waitResponse = pharInWaitReceiveService_consumer.queryPharInWaitReceiveApply(queryWaitReceiveMap);
      List<PharInWaitReceiveDTO> waitReceiveListAll = waitResponse.getData();
      if (ListUtils.isEmpty(waitReceiveListAll)) {
        throw new AppException("没有可领药品");
      }

      //获取所有退费药品 按科室  请领标志0  有效标志
      Map queryWaitReceiveMapIsBack = new HashMap();
      PharInWaitReceiveDTO pharInWaitReceiveDTOisBack = new PharInWaitReceiveDTO();
      pharInWaitReceiveDTOisBack.setHospCode(hospCode);
      pharInWaitReceiveDTOisBack.setDeptId(deptId);
      pharInWaitReceiveDTOisBack.setIsBack("1");
      queryWaitReceiveMapIsBack.put("hospCode", hospCode);
      queryWaitReceiveMapIsBack.put("pharInWaitReceiveDTO", pharInWaitReceiveDTOisBack);
      //查询所有待领药品
      WrapperResponse<List<PharInWaitReceiveDTO>> waitResponse1 = pharInWaitReceiveService_consumer.queryPharInWaitReceiveApply(queryWaitReceiveMapIsBack);
      List<PharInWaitReceiveDTO> isBackWaitReceiveList = waitResponse1.getData();
      if (!ListUtils.isEmpty(isBackWaitReceiveList)) {
        for (PharInWaitReceiveDTO isBackDto : isBackWaitReceiveList) {
          for (Iterator<PharInWaitReceiveDTO> it = waitReceiveListAll.iterator();it.hasNext();) {
            PharInWaitReceiveDTO waitDto = it.next();
            if (isBackDto.getOldWrId().equals(waitDto.getId())) {
              waitDto.setNum(BigDecimalUtils.add(waitDto.getNum(), isBackDto.getNum()));
              waitDto.setSplitNum(BigDecimalUtils.add(waitDto.getSplitNum(), isBackDto.getSplitNum()));
              waitDto.setTotalPrice(BigDecimalUtils.add(waitDto.getTotalPrice(), isBackDto.getTotalPrice()));
              if (BigDecimalUtils.isZero(waitDto.getNum()) || BigDecimalUtils.lessZero(waitDto.getNum())) {
                it.remove();
              }
              break;
            }
          }
        }
      }
      //
      //所有单据筛选出来的药品的集合
      List<PharInWaitReceiveDTO> waitReceiveListListSx = new ArrayList<>();
      //定义配药01表数据集合
      List<PharInReceiveDTO> inReceiveList = new ArrayList<>();
      //定义配药02表数据集合
      List<PharInReceiveDetailDTO> inReceiveDetailList = new ArrayList<>();

      //筛选出来的待领药品集合
      List<PharInWaitReceiveDTO> lyList = new ArrayList<>();

      //筛选一个单据符合条件的待领药品到lyList
      filterWaitReceive(orderReceiveDTO, waitReceiveListAll, lyList);

      //把一个单据筛选出来的药品放进筛选总集合，后续统一链接数据库更新qlbz
      waitReceiveListListSx.addAll(lyList);

      if (ListUtils.isEmpty(waitReceiveListListSx)) {
        throw new AppException("没有符合条件的药品");
      }
      //按药房把lyList分组
      Map<String, List<PharInWaitReceiveDTO>> yfidGroupMap = lyList.stream().collect(Collectors.groupingBy(PharInWaitReceiveDTO::getPharId));
      List<PharInWaitReceiveDTO> noInventoryList = new ArrayList<>();
      //遍历根据药房分组后的药品集合yfidGroupMap，然后组装配药01和02，存进ypzypl01List，ypzypl02List中，最后进行统一插入操作
      yfidGroupMap.forEach((k, v) -> {
        //组装数据插入配药表
        buildPharReceive(map, v, inReceiveList, inReceiveDetailList, k, orderReceiveDTO,noInventoryList);
      });
      // 判断库存不足的药是否有同组药
      if(!ListUtils.isEmpty(noInventoryList) && !ListUtils.isEmpty(inReceiveDetailList)) {
        for (PharInWaitReceiveDTO noitem :noInventoryList) {
          Iterator<PharInReceiveDetailDTO> it = inReceiveDetailList.iterator();
          while(it.hasNext()){
            PharInReceiveDetailDTO dto = it.next();
            if(StringUtils.isNotEmpty(noitem.getGroupNo()) && StringUtils.isNotEmpty(dto.getGroupNo()) &&
              !"0".equals(dto.getGroupNo()) && !"0".equals(noitem.getGroupNo()) && dto.getVisitId().equals(noitem.getVisitId()) && noitem.getGroupNo().equals(dto.getGroupNo())){
              it.remove();
            }
          }
        }
      }
      if(ListUtils.isEmpty(inReceiveDetailList)) {
        throw new AppException("预配药失败,配药数据为空或药品(同组)库存不足");
      }
      // 根据配药表主键分组
      Map<String, List<PharInReceiveDetailDTO>> stringListMap = inReceiveDetailList.stream().collect(Collectors.groupingBy(PharInReceiveDetailDTO::getReceiveId));
      // 如果明细为空则删除主表信息
      Iterator<PharInReceiveDTO> it = inReceiveList.iterator();
      while(it.hasNext()){
        PharInReceiveDTO dto = it.next();
        if(!stringListMap.containsKey(dto.getId())) {
          it.remove();
        }
      }
      //统一更新ypzyly表请领标志为请领（1）
      List<String> ids = inReceiveDetailList.stream().map(PharInReceiveDetailDTO::getWrId).collect(Collectors.toList());
      Map inwaitMap = new HashMap();
      PharInWaitReceiveDTO updateInwait = new PharInWaitReceiveDTO();
      updateInwait.setIds(ids);
      updateInwait.setHospCode(hospCode);
      updateInwait.setStatusCode("1");
      //todo 修改 ，由于事务不一致导致的重复预配药问题以及预配药操作，配药单没有生成数据的问题
      inptCostDAO.updateInWaitStatus(updateInwait);
      //插入配药主表
      inptCostDAO.insertPharInReceives(inReceiveList);
      // 插入配药明细表
      inptCostDAO.insertPharInReceiveDetails(inReceiveDetailList);
    }catch (Exception e) {
      log.error("配药出现问题，出错方法为：{}",e.getSuppressed());
      throw new RuntimeException("预配药失败，原因："+e.getMessage());
    } finally {
      redisUtils.del(key);
    }
    // TODO: 2020/4/16 记录操作日志表
    return true;
  }

  /**
   * @Menthod checkDrawMedicineStock
   * @Desrciption 预配药的时候校验库存
   *
   * @Param
   * [map]
   *
   * @Author jiahong.yang
   * @Date   2021/9/7 10:37
   * @Return java.lang.Boolean
   **/
  @Override
  public PharInWaitReceiveDTO checkDrawMedicineStock(Map map) {
    String hospCode = MapUtils.get(map, "hospCode");
    String id = MapUtils.get(map, "id");
    String deptId = MapUtils.get(map, "deptId");
    PharInWaitReceiveDTO check = new PharInWaitReceiveDTO();
    StringBuilder message = new StringBuilder();
    Map baseOrderReceiveMap = new HashMap();
    BaseOrderReceiveDTO baseOrderReceiveDTO = new BaseOrderReceiveDTO();
    baseOrderReceiveDTO.setHospCode(hospCode);
    baseOrderReceiveDTO.setId(id);
    baseOrderReceiveMap.put("hospCode", hospCode);
    baseOrderReceiveMap.put("baseOrderReceiveDTO", baseOrderReceiveDTO);
    WrapperResponse<BaseOrderReceiveDTO> response = baseOrderReceiveService.getById(baseOrderReceiveMap);
    BaseOrderReceiveDTO orderReceiveDTO = response.getData();
    //获取所有待领药品 按科室  请领标志0  有效标志
    Map queryWaitReceiveMap = new HashMap();
    PharInWaitReceiveDTO pharInWaitReceiveDTO = new PharInWaitReceiveDTO();
    pharInWaitReceiveDTO.setHospCode(hospCode);
    pharInWaitReceiveDTO.setStatusCode("0");
    pharInWaitReceiveDTO.setDeptId(MapUtils.get(map, "deptId"));
    pharInWaitReceiveDTO.setIsBack("0");
    queryWaitReceiveMap.put("hospCode", hospCode);
    queryWaitReceiveMap.put("pharInWaitReceiveDTO", pharInWaitReceiveDTO);
    //查询所有待领药品
    WrapperResponse<List<PharInWaitReceiveDTO>> waitResponse = pharInWaitReceiveService_consumer.queryPharInWaitReceiveApply(queryWaitReceiveMap);
    List<PharInWaitReceiveDTO> waitReceiveListAll = waitResponse.getData();
    if (ListUtils.isEmpty(waitReceiveListAll)) {
      return null;
    }

    //获取所有退费药品 按科室  请领标志0  有效标志
    Map queryWaitReceiveMapIsBack = new HashMap();
    PharInWaitReceiveDTO pharInWaitReceiveDTOisBack = new PharInWaitReceiveDTO();
    pharInWaitReceiveDTOisBack.setHospCode(hospCode);
    pharInWaitReceiveDTOisBack.setDeptId(deptId);
    pharInWaitReceiveDTOisBack.setIsBack("1");
    queryWaitReceiveMapIsBack.put("hospCode", hospCode);
    queryWaitReceiveMapIsBack.put("pharInWaitReceiveDTO", pharInWaitReceiveDTOisBack);
    //查询所有待领药品
    WrapperResponse<List<PharInWaitReceiveDTO>> waitResponse1 = pharInWaitReceiveService_consumer.queryPharInWaitReceiveApply(queryWaitReceiveMapIsBack);
    List<PharInWaitReceiveDTO> isBackWaitReceiveList = waitResponse1.getData();
    // 过滤掉已退费数量
    if (!ListUtils.isEmpty(isBackWaitReceiveList)) {
      for (PharInWaitReceiveDTO isBackDto : isBackWaitReceiveList) {
        for (Iterator<PharInWaitReceiveDTO> it = waitReceiveListAll.iterator();it.hasNext();) {
          PharInWaitReceiveDTO waitDto = it.next();
          if (isBackDto.getOldWrId().equals(waitDto.getId())) {
            waitDto.setNum(BigDecimalUtils.add(waitDto.getNum(), isBackDto.getNum()));
            waitDto.setSplitNum(BigDecimalUtils.add(waitDto.getSplitNum(), isBackDto.getSplitNum()));
            waitDto.setTotalPrice(BigDecimalUtils.add(waitDto.getTotalPrice(), isBackDto.getTotalPrice()));
            if (BigDecimalUtils.isZero(waitDto.getNum()) || BigDecimalUtils.lessZero(waitDto.getNum())) {
              it.remove();
            }
            break;
          }
        }
      }
    }
    //筛选出来的待领药品集合
    List<PharInWaitReceiveDTO> lyList = new ArrayList<>();
    //筛选一个单据符合条件的待领药品到lyList
    filterWaitReceive(orderReceiveDTO, waitReceiveListAll, lyList);
    AtomicInteger itemNum = new AtomicInteger();
    HashMap itemMap = new HashMap();
    List<PharInWaitReceiveDTO> sumList = new ArrayList<>();
    //领药申请详情汇总
    sumList = getSummaryStock(lyList);
    sumList.forEach(dto -> {
      //校验库存
      if (Constants.XMLB.YP.equals(dto.getItemCode()) || Constants.XMLB.CL.equals(dto.getItemCode())) {
        InptAdviceDTO inptAdviceDTO = new InptAdviceDTO();
        inptAdviceDTO.setHospCode(dto.getHospCode());
        inptAdviceDTO.setItemId(dto.getItemId());
        inptAdviceDTO.setPharId(dto.getPharId());
        inptAdviceDTO.setTotalNum(dto.getAllNum());
        inptAdviceDTO.setUnitCode(dto.getUnitCode());
        //判断库存
        if (ListUtils.isEmpty(doctorAdviceBO.checkStock(inptAdviceDTO))) {
          itemNum.getAndIncrement();
          check.setCheckFlag(true);
          if(itemNum.get() <= 8 && !itemMap.containsKey(dto.getId())) {
            itemMap.put(dto.getId(),dto.getItemName());
            message.append("【");
            message.append(dto.getProductName());
            message.append(dto.getItemName());
            message.append(dto.getSpec());
            message.append("】,");
          }
        }
      }
    });

    check.setItemName(String.valueOf(message));
    return check;
  }


  /**
  * @Menthod getSummaryStock
  * @Desrciption 领药申请汇总库存校验
  *
  * @Param
  * [inWaitReceiveList]
  *
  * @Author jiahong.yang
  * @Date   2021/11/23 17:20
  * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>
  **/
  public List<PharInWaitReceiveDTO> getSummaryStock(List<PharInWaitReceiveDTO> inWaitReceiveList) {
    if (ListUtils.isEmpty(inWaitReceiveList)) {
      return inWaitReceiveList;
    }
    List<PharInWaitReceiveDTO> summaryList = new ArrayList<>();
    Map<String, List<PharInWaitReceiveDTO>> itemMap = inWaitReceiveList.stream().collect(Collectors.groupingBy(e -> e.getItemId() + "#" + e.getPharId()));
    itemMap.forEach((k, v) -> {
      BigDecimal num = v.stream().map(PharInWaitReceiveDTO::getNum).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal totalPrice = v.stream().map(PharInWaitReceiveDTO::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
//            BigDecimal kszbsl = v.stream().map(PharInWaitReceiveDTO::getKszbsl).reduce(BigDecimal.ZERO, BigDecimal::add);
      v.get(0).setAllNum(num);
      v.get(0).setAllTotalPrice(totalPrice);
//            v.get(0).setKszbsl(kszbsl);
      summaryList.add(v.get(0));
    });
    return summaryList;
  }

  /**
   * @Method getApplyDetailsList
   * @Desrciption 查询领药明细数据
   * @params [map]
   * @Author chenjun
   * @Date 2020-12-17 15:23
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
   **/
  @Override
  public PageDTO getApplyDetailsList(Map map) {
    String hospCode = MapUtils.get(map, "hospCode");
    List<String> codeList = MapUtils.get(map, "codeList");
    List<PharInWaitReceiveDTO> list = getPharInWaitReceiveDTOS(map, hospCode, codeList);
    for (PharInWaitReceiveDTO waitReceiveDTO : list) {
      if (waitReceiveDTO.getSplitUnitCode().equals(waitReceiveDTO.getCurrUnitCode())) {
        waitReceiveDTO.setNum(waitReceiveDTO.getSplitNum());
        waitReceiveDTO.setPrice(waitReceiveDTO.getSplitPrice());
      }
    }
    return PageDTO.of(list);
  }

  /**
   * @Method getApplySummaryList
   * @Desrciption 查询领药汇总信息
   * @params [map]
   * @Author chenjun
   * @Date 2020-12-17 15:24
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
   **/
  @Override
  public PageDTO getApplySummaryList(Map map) {
    String hospCode = MapUtils.get(map, "hospCode");
    List<String> codeList = MapUtils.get(map, "codeList");
    //领药申请详情明细
    List<PharInWaitReceiveDTO> inWaitReceiveList = getPharInWaitReceiveDTOS(map, hospCode, codeList);
    List<PharInWaitReceiveDTO> sumList = new ArrayList<>();
    if (ListUtils.isEmpty(inWaitReceiveList)) {
      return PageDTO.of(sumList);
    }
    //领药申请详情汇总
    sumList = getSummary(inWaitReceiveList);
    for (Iterator<PharInWaitReceiveDTO> it = sumList.iterator();it.hasNext();) {
      PharInWaitReceiveDTO dto = it.next();
      //校验库存
      if (Constants.XMLB.YP.equals(dto.getItemCode()) || Constants.XMLB.CL.equals(dto.getItemCode())) {
        InptAdviceDTO inptAdviceDTO = new InptAdviceDTO();
        inptAdviceDTO.setHospCode(dto.getHospCode());
        inptAdviceDTO.setItemId(dto.getItemId());
        inptAdviceDTO.setPharId(dto.getPharId());
        inptAdviceDTO.setTotalNum(dto.getAllNum());
        inptAdviceDTO.setUnitCode(dto.getCurrUnitCode());
        //判断库存,如果库存为空就弄成红色
        if (ListUtils.isEmpty(doctorAdviceBO.checkStock(inptAdviceDTO))) {
          dto.setColor(Constants.COLOR.RED);
        }
      }
    }

    return PageDTO.of(sumList);
  }

  /**
   * @Method getPharInReceiveList
   * @Desrciption 查询领药单集合
   * @params [map]
   * @Author chenjun
   * @Date 2020-12-17 15:24
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
   **/
  @Override
  public PageDTO getPharInReceiveList(Map map) {
    String pageNo = MapUtils.get(map, "pageNo");
    String pageSize = MapUtils.get(map, "pageSize");
    PharInReceiveDTO pharInReceiveDTO = new PharInReceiveDTO();
    if(!StringUtils.isEmpty(pageNo) && !StringUtils.isEmpty(pageSize)){
      // 设置分页信息
      pharInReceiveDTO.setPageNo(Integer.parseInt(pageNo));
      pharInReceiveDTO.setPageSize(Integer.parseInt(pageSize));
    }
    Map mapQuery = new HashMap();
    pharInReceiveDTO.setHospCode(MapUtils.get(map, "hospCode"));
    pharInReceiveDTO.setStatusCode(Constants.LYZT.QL);
    pharInReceiveDTO.setDeptId(MapUtils.get(map, "deptId"));
    mapQuery.put("hospCode", MapUtils.get(map, "hospCode"));
    mapQuery.put("pharInReceiveDTO", pharInReceiveDTO);
    return inBackDrugService.getPharInReceiveList1(mapQuery).getData();
  }

  /**
   * @Method getPharInReceiveListDetail
   * @Desrciption 查询领药单明细集合
   * @params [map]
   * @Author chenjun
   * @Date 2020-12-17 15:24
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
   **/
  @Override
  public PageDTO getPharInReceiveListDetail(Map map) {
    String pageNo = MapUtils.get(map, "pageNo");
    String pageSize = MapUtils.get(map, "pageSize");
    PharInReceiveDetailDTO pharInReceiveDetailDTO = new PharInReceiveDetailDTO();
    if(!StringUtils.isEmpty(pageNo) && !StringUtils.isEmpty(pageSize)){
      pharInReceiveDetailDTO.setPageNo(Integer.parseInt(pageNo));
      pharInReceiveDetailDTO.setPageSize(Integer.parseInt(pageSize));
    }
    Map mapQuery = new HashMap();
    pharInReceiveDetailDTO.setHospCode(MapUtils.get(map, "hospCode"));
    pharInReceiveDetailDTO.setReceiveId(MapUtils.get(map, "receiveId"));
    mapQuery.put("hospCode", MapUtils.get(map, "hospCode"));
    mapQuery.put("pharInReceiveDetailDTO", pharInReceiveDetailDTO);
    return inBackDrugService.getPharInReceiveDetailList1(mapQuery).getData();
  }

  /**
   * @Method cancelDrawMedicine
   * @Desrciption 取消配药
   * @params [map]
   * @Author chenjun
   * @Date 2020-12-17 15:25
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   **/
  @Override
  public Boolean deleteCancelDrawMedicine(PharInReceiveDTO pharInReceiveDTO) {
    String hospCode = pharInReceiveDTO.getHospCode();
    //查出待领取id并更新待领表状态
    Map mapQuery = new HashMap();
    PharInReceiveDetailDTO pharInReceiveDetailDTO = new PharInReceiveDetailDTO();
    pharInReceiveDetailDTO.setHospCode(pharInReceiveDTO.getHospCode());
    pharInReceiveDetailDTO.setReceiveId(pharInReceiveDTO.getId());
    if(StringUtils.isEmpty(pharInReceiveDTO.getId())) {
      throw new AppException("没有需要取消配药的数据");
    }
    pharInReceiveDetailDTO.setFlag("1");
    mapQuery.put("hospCode", hospCode);
    mapQuery.put("pharInReceiveDetailDTO", pharInReceiveDetailDTO);
    WrapperResponse<List<PharInReceiveDetailDTO>> response = inBackDrugService.getPharInReceiveDetailList(mapQuery);
    List<PharInReceiveDetailDTO> pharInReceiveDetailList = response.getData();
    List<String> wrIds = pharInReceiveDetailList.stream().map(PharInReceiveDetailDTO::getWrId).collect(Collectors.toList());
    Map mapUpdate = new HashMap();
    PharInWaitReceiveDTO pharInWaitReceiveDTO = new PharInWaitReceiveDTO();
    pharInWaitReceiveDTO.setIds(wrIds);
    pharInWaitReceiveDTO.setHospCode(hospCode);
    pharInWaitReceiveDTO.setStatusCode(Constants.LYZT.DL);
    mapUpdate.put("hospCode", hospCode);
    mapUpdate.put("pharInWaitReceiveDTO", pharInWaitReceiveDTO);
    if(ListUtils.isEmpty(wrIds)) {
      throw new AppException("没有需要取消配药的数据");
    }
    // 修改待领表状态
    pharInWaitReceiveService_consumer.updateInWaitStatus(mapUpdate);
    // 修改待领表冲红记录状态
    pharInWaitReceiveService_consumer.updateInWaitStatusByWrIds(mapUpdate);
    // 根据带领id集合查询带领集合
    WrapperResponse<List<PharInWaitReceiveDTO>> wrapperResponse = pharInWaitReceiveService_consumer.queryPharInWaitReceive(mapUpdate);
    List<PharInWaitReceiveDTO> pharInWaitReceiveDTOS = wrapperResponse.getData();
    // 获取费用id集合
    List<String> costIds = pharInWaitReceiveDTOS.stream().map(PharInWaitReceiveDTO::getCostId).collect(Collectors.toList());
    InptCostDTO inptCostDTO = new InptCostDTO();
    inptCostDTO.setHospCode(hospCode);
    inptCostDTO.setIds(costIds);
    // 已退费不退药状态
    inptCostDTO.setBackCode(Constants.COST_TYZT.TFBTY);
    // 更新费用退药状态
    inptCostDAO.updateCostBackCodeStatus(inptCostDTO);

    //删除领药单表和明细表数据
    List<String> ids = new ArrayList<>();
    ids.add(pharInReceiveDTO.getId());

    Map deleteMap = new HashMap();
    PharInReceiveDTO pharInReceiveDTO1 = new PharInReceiveDTO();
    pharInReceiveDTO1.setHospCode(hospCode);
    pharInReceiveDTO1.setIds(ids);
    deleteMap.put("hospCode", hospCode);
    deleteMap.put("pharInReceiveDTO", pharInReceiveDTO1);
    pharInWaitReceiveService_consumer.deletePharInReceive(deleteMap);

    Map deleteDetailMap = new HashMap();
    PharInReceiveDetailDTO pharInReceiveDetailDTO1 = new PharInReceiveDetailDTO();
    pharInReceiveDetailDTO1.setHospCode(hospCode);
    pharInReceiveDetailDTO1.setReceiveIds(ids);
    deleteDetailMap.put("hospCode", hospCode);
    deleteDetailMap.put("pharInReceiveDetailDTO", pharInReceiveDetailDTO1);
    pharInWaitReceiveService_consumer.deletePharInReceiveDetail(deleteDetailMap);
    return true;
  }

  /**
   * @Menthod updateUrgentMedicine
   * @Desrciption 修改状态为紧急领药
   *
   * @Param
   * [pharInWaitReceiveDTO]
   *
   * @Author jiahong.yang
   * @Date   2021/1/22 11:00
   * @Return java.lang.Boolean
   **/
  @Override
  public Boolean updateUrgentMedicine(Map map) {
    WrapperResponse<Boolean> booleanWrapperResponse = pharInWaitReceiveService_consumer.updateUrgentMedicine(map);
    return true;
  }

  /**
   * @param map
   * @Method saveAdvanceTakeMedicine
   * @Desrciption 提前领药
   * @params map
   * @Author pengbo
   * @Date 2021-05-12 15:20
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   */
  @Override
  public void saveAdvanceTakeMedicine(Map<String, Object> map,String type) {
    String hospCode = MapUtils.get(map,"hospCode","");
    //新增提前领药
    if ("tqly".equals(type)){
      //领药天数
      String days = String.valueOf(MapUtils.get(map,"advanceDays",0));
      //领药单据类型
      String advanceType = MapUtils.get(map,"advanceType","0");
      if(StringUtils.isEmpty(advanceType)){
        throw new RuntimeException("请选择领药单据类型!");
      }

      Map<String,Object> advanceMap = inptAdviceDAO.getMedicineAdvance(map) ;
      if(advanceMap != null){
        throw new RuntimeException("不允许重复提前申请相同类型的药品!");
      }

      Map parmMap = new HashMap();
      parmMap.put("hospCode", hospCode);
      parmMap.put("id", advanceType);
      BaseOrderReceiveDTO baseOrderReceiveDTO = new BaseOrderReceiveDTO();
      baseOrderReceiveDTO.setHospCode(hospCode);
      baseOrderReceiveDTO.setId(advanceType);
      parmMap.put("baseOrderReceiveDTO",baseOrderReceiveDTO);
      baseOrderReceiveDTO = baseOrderReceiveService.getById(parmMap).getData();
      if(baseOrderReceiveDTO == null ){
        throw new RuntimeException("未获取到领药单据类型!");
      }
      String deptId = MapUtils.get(map,"deptId","");
      String endDate = MapUtils.get(map,"endDate","");
      parmMap.clear();
      parmMap.put("deptId",deptId);
      parmMap.put("hospCode",hospCode);
      parmMap.put("endDate",endDate);

      //8.是否紧急领药
      if("1".equals(baseOrderReceiveDTO.getIsEmergency())){
        throw new RuntimeException("紧急领药不允许提前领药!");
      }
      //1.判断科室是否可用
      if(StringUtils.isNotEmpty(baseOrderReceiveDTO.getDeptIds())){
        if (!Arrays.asList(baseOrderReceiveDTO.getDeptIds().split(",")).contains(deptId)){
          throw new RuntimeException(baseOrderReceiveDTO.getName()+"在当前科室不可用!");
        }
      }
      //2.用法
      if(StringUtils.isNotEmpty(baseOrderReceiveDTO.getUsageCodes())){
        List<String > usageCodes = Arrays.asList(baseOrderReceiveDTO.getUsageCodes().split(","));
        parmMap.put("usageCodes",usageCodes);
      }else{
        parmMap.put("usageCodes",Arrays.asList(new String []{""}));
      }
      //3.是否材料
      parmMap.put("isMaterial",baseOrderReceiveDTO.getIsMaterial());
      //4.是否大输液
      parmMap.put("isIvdrip",baseOrderReceiveDTO.getIsLvp());
      //5.是否是特殊药品
      parmMap.put("isPh",baseOrderReceiveDTO.getIsPh());
      //6.是否中草药
      parmMap.put("isHerb",baseOrderReceiveDTO.getIsHerb());
      //7.是否出院带药
      parmMap.put("isGive",baseOrderReceiveDTO.getIsGive());
      //8.是否全部
      parmMap.put("isAll",baseOrderReceiveDTO.getIsAll());
      //9.是否按病人
      parmMap.put("isPatient",baseOrderReceiveDTO.getIsPatient());

      List <String> adviceIds = inptAdviceDAO.selectAdviceByDeptAndType(parmMap);
      if(ListUtils.isEmpty(adviceIds)){
        throw new RuntimeException("没有需要提前领药的数据!");
      }
      //新增提前领药记录
      map.put("id",SnowflakeUtils.getId());
      map.put("typeCode",advanceType);
      map.put("sfpy","0");
      inptAdviceDAO.insertMedicineAdvance(map);
      Map<String, Object> paramMap = null ;
      if(!ListUtils.isEmpty(adviceIds)){
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (String adviceId: adviceIds){
          paramMap = new HashMap<>();
          paramMap.put("id",SnowflakeUtils.getId());
          paramMap.put("advance_id",MapUtils.get(map,"id",""));
          paramMap.put("advice_id",adviceId);
          paramMap.put("hosp_code",hospCode);
          mapList.add(paramMap);
        }
        //新增关联表
        inptAdviceDAO.insertMedicineAdvanceAdvice(mapList);
        //修改医嘱提前领药天数
        inptAdviceDAO.updateAdvanceDays(adviceIds,days);
      }
    }
    //预配药修改状态
    else{

      //修改医嘱提前领药天数
      MedicalAdviceDTO medicalAdviceDTO = MapUtils.get(map,"medicalAdviceDTO");
      List<String> adviceIds = medicalAdviceDTO.getIds();
      List<List<String>>  groupedList = splitList(adviceIds,50);
      for(List<String> subList : groupedList){
        inptAdviceDAO.updateAdvanceDaysLastExcTime(subList,"0");
      }
      //修改提前领药记录
      map.put("sfpy", Constants.SF.S);
      inptAdviceDAO.updateMedicineAdvance(map);
    }


  }

  /**
   *  将一个数据按照指定大小分割成若干大小
   * @param sourceList 需要被分割的List
   * @param groupSize  分割后每个子集合的大小(最后一个可能会小于该数值)
   * @return 分割后的list
   */
  private  List<List<String>> splitList(List<String> sourceList,int groupSize){
    return Lists.partition(sourceList,groupSize);
  }

  @Override
  public List<Map<String, Object>> getTableTqlyData(Map<String, Object> map) {
    return inptAdviceDAO.selectMedicineAdvance(map);
  }

  /**
   * @param map
   * @Method updateAdvance
   * @Desrciption 取消提前领药
   * @params map
   * @Author pengbo
   * @Date 2021-05-12 15:20
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   */
  @Override
  public Boolean updateAdvance(Map<String, Object> map) {
    map.put("sfpy","2");
    //取消提前领药记录
    inptAdviceDAO.updateMedicineAdvance(map);
    //修改提前领药的医嘱天数
    inptAdviceDAO.updateInptAdviceAdvanceDays(map);
    //删除提前领药的医嘱明细表数据
    inptAdviceDAO.deleteMedicineAdvanceAdvice(map);
    return true;
  }

  //领药申请详情汇总
  public List<PharInWaitReceiveDTO> getSummary(List<PharInWaitReceiveDTO> inWaitReceiveList) {
    if (ListUtils.isEmpty(inWaitReceiveList)) {
      return inWaitReceiveList;
    }
    List<PharInWaitReceiveDTO> summaryList = new ArrayList<>();
    Map<String, List<PharInWaitReceiveDTO>> itemMap = inWaitReceiveList.stream().collect(Collectors.groupingBy(e -> e.getItemId() + "#" + e.getUnitCode()));
    itemMap.forEach((k, v) -> {
      for (PharInWaitReceiveDTO waitReceiveDTO : v) {
        if (waitReceiveDTO.getSplitUnitCode().equals(waitReceiveDTO.getCurrUnitCode())) {
          waitReceiveDTO.setNum(waitReceiveDTO.getSplitNum());
          waitReceiveDTO.setPrice(waitReceiveDTO.getSplitPrice());
          waitReceiveDTO.setTotalPrice(BigDecimalUtils.multiply(waitReceiveDTO.getNum(), waitReceiveDTO.getPrice()));
        }
      }
      BigDecimal num = v.stream().map(PharInWaitReceiveDTO::getNum).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal totalPrice = v.stream().map(PharInWaitReceiveDTO::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
//            BigDecimal kszbsl = v.stream().map(PharInWaitReceiveDTO::getKszbsl).reduce(BigDecimal.ZERO, BigDecimal::add);
      v.get(0).setAllNum(num);
      v.get(0).setAllTotalPrice(totalPrice);
//            v.get(0).setKszbsl(kszbsl);
      summaryList.add(v.get(0));
    });
    return summaryList;
  }

  public List<PharInWaitReceiveDTO> getPharInWaitReceiveDTOS(Map map, String hospCode, List<String> codeList) {
    String id = MapUtils.get(map, "id");
    Map baseOrderReceiveMap = new HashMap();
    BaseOrderReceiveDTO baseOrderReceiveDTO = new BaseOrderReceiveDTO();
    baseOrderReceiveDTO.setHospCode(hospCode);
    baseOrderReceiveDTO.setId(id);
    baseOrderReceiveMap.put("hospCode", hospCode);
    baseOrderReceiveMap.put("baseOrderReceiveDTO", baseOrderReceiveDTO);
    WrapperResponse<BaseOrderReceiveDTO> response = baseOrderReceiveService.getById(baseOrderReceiveMap);
    BaseOrderReceiveDTO orderReceiveDTO = response.getData();


    //获取所有待领药品 按科室  请领标志0  有效标志
    Map queryWaitReceiveMap = new HashMap();
    PharInWaitReceiveDTO pharInWaitReceiveDTO = new PharInWaitReceiveDTO();
    pharInWaitReceiveDTO.setHospCode(hospCode);
    pharInWaitReceiveDTO.setStatusCode("0");
    pharInWaitReceiveDTO.setDeptId(MapUtils.get(map, "deptId"));
    pharInWaitReceiveDTO.setIsBack("0");
    PharInWaitReceiveDTO pharInWaitReceiveDTO1 = MapUtils.get(map, "pharInWaitReceiveDTO");
    if(pharInWaitReceiveDTO1 != null && !StringUtils.isEmpty(pharInWaitReceiveDTO1.getIsEmergency())){
      // 查询非紧急数据
      pharInWaitReceiveDTO.setIsEmergency(pharInWaitReceiveDTO1.getIsEmergency());
    }
    queryWaitReceiveMap.put("hospCode", hospCode);
    queryWaitReceiveMap.put("pharInWaitReceiveDTO", pharInWaitReceiveDTO);
    //查询所有待领药品
    WrapperResponse<List<PharInWaitReceiveDTO>> waitResponse = pharInWaitReceiveService_consumer.queryPharInWaitReceiveApply(queryWaitReceiveMap);
    List<PharInWaitReceiveDTO> waitReceiveListAll = waitResponse.getData();
    if (ListUtils.isEmpty(waitReceiveListAll)) {
      return new ArrayList<>();
    }

    //获取所有退费药品 按科室  请领标志0  有效标志
    Map queryWaitReceiveMapIsBack = new HashMap();
    PharInWaitReceiveDTO pharInWaitReceiveDTOisBack = new PharInWaitReceiveDTO();
    pharInWaitReceiveDTOisBack.setHospCode(hospCode);
    pharInWaitReceiveDTOisBack.setDeptId(MapUtils.get(map, "deptId"));
    pharInWaitReceiveDTOisBack.setIsBack("1");
    queryWaitReceiveMapIsBack.put("hospCode", hospCode);
    queryWaitReceiveMapIsBack.put("pharInWaitReceiveDTO", pharInWaitReceiveDTOisBack);
    //查询所有待领药品
    WrapperResponse<List<PharInWaitReceiveDTO>> waitResponse1 = pharInWaitReceiveService_consumer.queryPharInWaitReceiveApply(queryWaitReceiveMapIsBack);
    List<PharInWaitReceiveDTO> isBackWaitReceiveList = waitResponse1.getData();

//        if (!ListUtils.isEmpty(isBackWaitReceiveList)) {
//            isBackWaitReceiveList.forEach(isBackDto -> {
//                for (PharInWaitReceiveDTO waitDto : waitReceiveListAll) {
//                    if (isBackDto.getOldWrId().equals(waitDto.getId())) {
//                        waitDto.setNum(BigDecimalUtils.add(waitDto.getNum(), isBackDto.getNum()));
//                        waitDto.setSplitNum(BigDecimalUtils.add(waitDto.getSplitNum(), isBackDto.getSplitNum()));
//                        waitDto.setTotalPrice(BigDecimalUtils.add(waitDto.getTotalPrice(), isBackDto.getTotalPrice()));
//                        if (BigDecimalUtils.isZero(waitDto.getNum()) || BigDecimalUtils.lessZero(waitDto.getNum())) {
//                            waitReceiveListAll.remove(waitDto);
//                        }
//                        break;
//                    }
//                }
//            });
//        }
    // update by 2021/9/29 修改领药申请出现0的问题
    for (PharInWaitReceiveDTO isBackDto : isBackWaitReceiveList) {
      for (Iterator<PharInWaitReceiveDTO> it = waitReceiveListAll.iterator();it.hasNext();) {
        PharInWaitReceiveDTO waitDto = it.next();
        if (isBackDto.getOldWrId().equals(waitDto.getId())) {
          waitDto.setNum(BigDecimalUtils.add(waitDto.getNum(), isBackDto.getNum()));
          waitDto.setSplitNum(BigDecimalUtils.add(waitDto.getSplitNum(), isBackDto.getSplitNum()));
          waitDto.setTotalPrice(BigDecimalUtils.add(waitDto.getTotalPrice(), isBackDto.getTotalPrice()));
          if (BigDecimalUtils.isZero(waitDto.getNum()) || BigDecimalUtils.lessZero(waitDto.getNum())) {
            it.remove();
          }
          break;
        }
      }
    }


    //所有单据筛选出来的药品的集合
    List<PharInWaitReceiveDTO> waitReceiveListListSx = new ArrayList<>();

    //筛选出来的待领药品集合
    List<PharInWaitReceiveDTO> lyList = new ArrayList<>();

    //筛选一个单据符合条件的待领药品到lyList
    filterWaitReceive(orderReceiveDTO, waitReceiveListAll, lyList);

    //把一个单据筛选出来的药品放进筛选总集合，后续统一链接数据库更新qlbz
    waitReceiveListListSx.addAll(lyList);

    //排序按患者，按时间
    waitReceiveListListSx.sort(Comparator.comparing(PharInWaitReceiveDO::getVisitId).reversed().thenComparing(PharInWaitReceiveDO::getCrteTime));
    return waitReceiveListListSx;
  }


  // 组装数据插入配药表
  private void buildPharReceive(Map map, List<PharInWaitReceiveDTO> inReceiveList, List<PharInReceiveDTO> pharInReceiveList, List<PharInReceiveDetailDTO> pharInReceiveDetailList,
                                String yfid, BaseOrderReceiveDTO orderReceiveDTO,List<PharInWaitReceiveDTO> noInventoryList) {
    if (ListUtils.isEmpty(inReceiveList)) {
      return;
    }
    String hospCode = MapUtils.get(map, "hospCode");
    String deptId = MapUtils.get(map, "deptId");
    String crteId = MapUtils.get(map, "crteId");
    String crteName = MapUtils.get(map, "crteName");
    PharInReceiveDTO inReceiveDTO = new PharInReceiveDTO();
    inReceiveDTO.setId(SnowflakeUtils.getId());
    inReceiveDTO.setHospCode(hospCode);
    inReceiveDTO.setPharId(yfid);
    Map orderMap = new HashMap();
    orderMap.put("hospCode", hospCode);
    orderMap.put("typeCode", "14");
    WrapperResponse<String> orderRespose = baseOrderRuleService.getOrderNo(orderMap);
    inReceiveDTO.setOrderNo(orderRespose.getData());
    inReceiveDTO.setOrderReceiveId(orderReceiveDTO.getId());
    //inReceiveDTO.setOrderTypeCode(orderCode);
    BigDecimal totalPrice;
    try {
      totalPrice = inReceiveList.stream().map(PharInWaitReceiveDTO::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    } catch (Exception e) {
      throw new AppException("有药品金额为空！！！" + e);
    }
    inReceiveDTO.setTotalPrice(totalPrice);
    inReceiveDTO.setStatusCode(Constants.LYZT.QL); //单据状态,
    inReceiveDTO.setDeptId(deptId);
    inReceiveDTO.setCrteId(crteId);
    inReceiveDTO.setCrteName(crteName);
    inReceiveDTO.setCrteTime(new Date());
    //计算发药窗口
    Map<String, String> bwParam = new HashMap<String, String>();
    bwParam.put("hospCode", hospCode);//医院编码
    bwParam.put("isValid", Constants.SF.S);//是否有效
    bwParam.put("pharId", yfid);//药房id
    List<Map<String, Object>> windosList = inptCostDAO.queryShortcutWindows(bwParam);
    if (!ListUtils.isEmpty(windosList) && windosList.get(0)!=null && windosList.get(0).get("id")!=null) {
      inReceiveDTO.setWindowId((String) windosList.get(0).get("id"));
    }
    List<PharInWaitReceiveDTO> newInReceiveList = new ArrayList<>();
    for(PharInWaitReceiveDTO pharInWaitReceiveDTO:inReceiveList) {
      PharInWaitReceiveDTO newItem = DeepCopy.deepCopy(pharInWaitReceiveDTO);
      newInReceiveList.add(newItem);
    }
    List<PharInWaitReceiveDTO> summaryStock = getSummaryStock(newInReceiveList);
    Map<String, String> stockMap = new HashMap<>();
    // 检验哪些药品 药房的库存不足
    for(PharInWaitReceiveDTO dto:summaryStock) {
      //校验库存
      if (Constants.XMLB.YP.equals(dto.getItemCode()) || Constants.XMLB.CL.equals(dto.getItemCode())) {
        InptAdviceDTO inptAdviceDTO = new InptAdviceDTO();
        inptAdviceDTO.setHospCode(dto.getHospCode());
        inptAdviceDTO.setItemId(dto.getItemId());
        inptAdviceDTO.setPharId(dto.getPharId());
        inptAdviceDTO.setTotalNum(dto.getAllNum());
        inptAdviceDTO.setUnitCode(dto.getUnitCode());
        //判断库存
        if (ListUtils.isEmpty(doctorAdviceBO.checkStock(inptAdviceDTO))) {
          stockMap.put(inptAdviceDTO.getItemId(),inptAdviceDTO.getPharId());
        }
      }
    }
    for(PharInWaitReceiveDTO dto:inReceiveList){
      // 库存不足的药品 并且是同一个药房的过滤掉
      if(stockMap.containsKey(dto.getItemId()) && stockMap.get(dto.getItemId()).equals(dto.getPharId())) {
        noInventoryList.add(dto);
        continue;
      }
      PharInReceiveDetailDTO pharInReceiveDetailDTO = new PharInReceiveDetailDTO();
      pharInReceiveDetailDTO.setId(SnowflakeUtils.getId());
      pharInReceiveDetailDTO.setHospCode(hospCode);
      pharInReceiveDetailDTO.setWrId(dto.getId());
      pharInReceiveDetailDTO.setReceiveId(inReceiveDTO.getId());
      pharInReceiveDetailDTO.setAdviceId(dto.getAdviceId());
      pharInReceiveDetailDTO.setGroupNo(dto.getGroupNo());
      pharInReceiveDetailDTO.setVisitId(dto.getVisitId());
      pharInReceiveDetailDTO.setBabyId(dto.getBabyId());
      pharInReceiveDetailDTO.setItemCode(dto.getItemCode());
      pharInReceiveDetailDTO.setItemId(dto.getItemId());
      pharInReceiveDetailDTO.setItemName(dto.getItemName());
      pharInReceiveDetailDTO.setPrice(dto.getPrice());
      pharInReceiveDetailDTO.setNum(dto.getNum());
      pharInReceiveDetailDTO.setSpec(dto.getSpec());
      pharInReceiveDetailDTO.setUnitCode(dto.getUnitCode());
      pharInReceiveDetailDTO.setTotalPrice(dto.getTotalPrice());
      //ph  批号
      pharInReceiveDetailDTO.setDosage(dto.getDosage()); //发药单价
      pharInReceiveDetailDTO.setDosageUnitCode(dto.getDosageUnitCode()); //？
      pharInReceiveDetailDTO.setSplitRatio(dto.getSplitRatio());
      pharInReceiveDetailDTO.setSplitUnitCode(dto.getSplitUnitCode());
      pharInReceiveDetailDTO.setSplitNum(dto.getSplitNum());
      pharInReceiveDetailDTO.setSplitPrice(dto.getSplitPrice());
      pharInReceiveDetailDTO.setChineseDrugNum(dto.getChineseDrugNum());
      pharInReceiveDetailDTO.setUsageCode(dto.getUsageCode());
      pharInReceiveDetailDTO.setCurrUnitCode(dto.getCurrUnitCode());
      pharInReceiveDetailList.add(pharInReceiveDetailDTO);
    }
    // 没有明细不插入主表
    if(!ListUtils.isEmpty(pharInReceiveDetailList)) {
      pharInReceiveList.add(inReceiveDTO);
    }
  }


  private void filterWaitReceive(BaseOrderReceiveDTO baseOrderReceiveDto, List<PharInWaitReceiveDTO> inWaitReceiveList, List<PharInWaitReceiveDTO> lyList) {

    for (PharInWaitReceiveDTO dto : inWaitReceiveList) {
      //是否为全部药品领药单
      if ("1".equals(baseOrderReceiveDto.getIsAll())) {
        //药品筛选出来，改为请领状态
        dto.setStatusCode("1");
        //一个单据领取的药品集合
        lyList.add(dto);
      } else if ("1".equals(baseOrderReceiveDto.getIsEmergency())) {
        if("1".equals(dto.getIsEmergency())){
          //药品筛选出来，改为请领状态
          dto.setStatusCode("1");
          //一个单据领取的药品集合
          lyList.add(dto);
        }
      } else{
        if (
          (("0".equals(baseOrderReceiveDto.getIsLvp())) ? (StringUtils.isEmpty(dto.getIsLvp()) || "0".equals(dto.getIsLvp())) : "1".equals(dto.getIsLvp())) &&
            (("0".equals(baseOrderReceiveDto.getIsPh())) ? (StringUtils.isEmpty(dto.getPhCode()) || "5".equals(dto.getPhCode())) : !"5".equals(baseOrderReceiveDto)) &&
            (("0".equals(baseOrderReceiveDto.getIsHerb())) ? (StringUtils.isEmpty(dto.getBigTypeCode()) || !"2".equals(dto.getBigTypeCode())) : "2".equals(dto.getBigTypeCode())) &&
            (("0".equals(baseOrderReceiveDto.getIsGive())) ? (StringUtils.isEmpty(dto.getUseCode()) || !"4".equals(dto.getUseCode())) : "4".equals(dto.getUseCode())) &&
            (("0".equals(baseOrderReceiveDto.getIsEmergency())) ? (StringUtils.isEmpty(dto.getIsEmergency()) || "0".equals(dto.getIsEmergency())) : "1".equals(dto.getIsEmergency())) &&
            (("0".equals(baseOrderReceiveDto.getIsMaterial())) ? (StringUtils.isEmpty(dto.getItemCode()) || (!"2".equals(dto.getItemCode())) || "1".equals(baseOrderReceiveDto.getIsGive())) : "2".equals(dto.getItemCode()))) {
          String[] usageCodeSz = baseOrderReceiveDto.getUsageCodes().split(",");
          List<String> usageCodeList = Arrays.asList(usageCodeSz);
          if ((StringUtils.isEmpty(baseOrderReceiveDto.getUsageCodes()) ||StringUtils.isEmpty(dto.getUsageCode()) || usageCodeList.contains(dto.getUsageCode()))) {
            //药品筛选出来，改为请领状态
            dto.setStatusCode("1");
            //一个单据领取的药品集合
            lyList.add(dto);
          }

        }
      }
    }
  }
}
