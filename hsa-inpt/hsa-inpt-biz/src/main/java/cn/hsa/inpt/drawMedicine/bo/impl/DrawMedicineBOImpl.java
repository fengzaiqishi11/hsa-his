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
import cn.hsa.module.inpt.medical.dto.MedicalAdviceDTO;
import cn.hsa.module.inpt.medical.service.MedicalAdviceService;
import cn.hsa.module.phar.pharapply.service.PharApplyService;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDetailDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO;
import cn.hsa.module.phar.pharinbackdrug.entity.PharInReceiveDO;
import cn.hsa.module.phar.pharinbackdrug.entity.PharInWaitReceiveDO;
import cn.hsa.module.phar.pharinbackdrug.service.InBackDrugService;
import cn.hsa.module.phar.pharinbackdrug.service.PharInWaitReceiveService;
import cn.hsa.util.*;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.ResourceTransactionManager;
import org.springframework.util.CollectionUtils;

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
  //?????????
  @Resource
  private DoctorAdviceBO doctorAdviceBO;
  @Resource
  private MedicalAdviceService medicalAdviceService_consumer;

  @Resource
  private RedisUtils redisUtils;

  /**
   * ????????????
   */
  @Resource
  private ResourceTransactionManager transactionManager;

  /**
   * @Method beforehandDrawMedicine
   * @Desrciption ?????????
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

    String key = hospCode + deptId + "_MEDICALADVICE";
    try {
      if(!redisUtils.setIfAbsent(key,deptId,600)) {
        throw new AppException("???????????????????????????,???????????????!");
      }
      Map baseOrderReceiveMap = new HashMap();
      BaseOrderReceiveDTO baseOrderReceiveDTO = new BaseOrderReceiveDTO();
      baseOrderReceiveDTO.setHospCode(hospCode);
      baseOrderReceiveDTO.setId(id);
      baseOrderReceiveMap.put("hospCode", hospCode);
      baseOrderReceiveMap.put("baseOrderReceiveDTO", baseOrderReceiveDTO);
      WrapperResponse<BaseOrderReceiveDTO> response = baseOrderReceiveService.getById(baseOrderReceiveMap);
      BaseOrderReceiveDTO orderReceiveDTO = response.getData();
      //???????????????????????? ?????????  ????????????0  ????????????
      Map queryWaitReceiveMap = new HashMap();
      PharInWaitReceiveDTO pharInWaitReceiveDTO = new PharInWaitReceiveDTO();
      pharInWaitReceiveDTO.setHospCode(hospCode);
      pharInWaitReceiveDTO.setStatusCode("0");
      pharInWaitReceiveDTO.setDeptId(MapUtils.get(map, "deptId"));
      pharInWaitReceiveDTO.setIsBack("0");
      queryWaitReceiveMap.put("hospCode", hospCode);
      queryWaitReceiveMap.put("pharInWaitReceiveDTO", pharInWaitReceiveDTO);
      //????????????????????????
      WrapperResponse<List<PharInWaitReceiveDTO>> waitResponse = pharInWaitReceiveService_consumer.queryPharInWaitReceiveApply(queryWaitReceiveMap);
      List<PharInWaitReceiveDTO> waitReceiveListAll = waitResponse.getData();
      if (ListUtils.isEmpty(waitReceiveListAll)) {
        throw new AppException("??????????????????");
      }

      //???????????????????????? ?????????  ????????????0  ????????????
      Map queryWaitReceiveMapIsBack = new HashMap();
      PharInWaitReceiveDTO pharInWaitReceiveDTOisBack = new PharInWaitReceiveDTO();
      pharInWaitReceiveDTOisBack.setHospCode(hospCode);
      pharInWaitReceiveDTOisBack.setDeptId(deptId);
      pharInWaitReceiveDTOisBack.setIsBack("1");
      queryWaitReceiveMapIsBack.put("hospCode", hospCode);
      queryWaitReceiveMapIsBack.put("pharInWaitReceiveDTO", pharInWaitReceiveDTOisBack);
      //????????????????????????
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
      //??????????????????????????????????????????
      List<PharInWaitReceiveDTO> waitReceiveListListSx = new ArrayList<>();
      //????????????01???????????????
      List<PharInReceiveDTO> inReceiveList = new ArrayList<>();
      //????????????02???????????????
      List<PharInReceiveDetailDTO> inReceiveDetailList = new ArrayList<>();

      //?????????????????????????????????
      List<PharInWaitReceiveDTO> lyList = new ArrayList<>();

      //????????????????????????????????????????????????lyList
      filterWaitReceive(orderReceiveDTO, waitReceiveListAll, lyList);

      //?????????????????????????????????????????????????????????????????????????????????????????????qlbz
      waitReceiveListListSx.addAll(lyList);

      if (ListUtils.isEmpty(waitReceiveListListSx)) {
        throw new AppException("???????????????????????????");
      }
      //????????????lyList??????
      Map<String, List<PharInWaitReceiveDTO>> yfidGroupMap = lyList.stream().collect(Collectors.groupingBy(PharInWaitReceiveDTO::getPharId));
      List<PharInWaitReceiveDTO> noInventoryList = new ArrayList<>();
      //??????????????????????????????????????????yfidGroupMap?????????????????????01???02?????????ypzypl01List???ypzypl02List????????????????????????????????????
      yfidGroupMap.forEach((k, v) -> {
        //???????????????????????????
        buildPharReceive(map, v, inReceiveList, inReceiveDetailList, k, orderReceiveDTO,noInventoryList);
      });
      // ??????????????????????????????????????????
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
        throw new AppException("???????????????,???????????????????????????(??????)????????????");
      }
      // ???????????????????????????
      Map<String, List<PharInReceiveDetailDTO>> stringListMap = inReceiveDetailList.stream().collect(Collectors.groupingBy(PharInReceiveDetailDTO::getReceiveId));
      // ???????????????????????????????????????
      Iterator<PharInReceiveDTO> it = inReceiveList.iterator();
      while(it.hasNext()){
        PharInReceiveDTO dto = it.next();
        if(!stringListMap.containsKey(dto.getId())) {
          it.remove();
        }
      }
      //????????????ypzyly???????????????????????????1???
      List<String> ids = inReceiveDetailList.stream().map(PharInReceiveDetailDTO::getWrId).collect(Collectors.toList());
      Map inwaitMap = new HashMap();
      PharInWaitReceiveDTO updateInwait = new PharInWaitReceiveDTO();
      updateInwait.setIds(ids);
      updateInwait.setHospCode(hospCode);
      updateInwait.setStatusCode("1");
      //todo ?????? ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
      inptCostDAO.updateInWaitStatus(updateInwait);
      //??????????????????
      inptCostDAO.insertPharInReceives(inReceiveList);
      // ?????????????????????
      inptCostDAO.insertPharInReceiveDetails(inReceiveDetailList);
      //?????????????????????????????????
      map.put("typeCode",id);
      map.put("sfpy", Constants.SF.S);
      map.put("isAll",orderReceiveDTO.getIsAll());
      inptAdviceDAO.updateMedicineAdvance(map);
    }catch (Exception e) {
      log.error("???????????????????????????????????????{}",e.getSuppressed());
      throw new RuntimeException("???????????????????????????"+e.getMessage());
    } finally {
      redisUtils.del(key);
    }
    // TODO: 2020/4/16 ?????????????????????
    return true;
  }

  /**
   * @Menthod checkDrawMedicineStock
   * @Desrciption ??????????????????????????????
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
    //???????????????????????? ?????????  ????????????0  ????????????
    Map queryWaitReceiveMap = new HashMap();
    PharInWaitReceiveDTO pharInWaitReceiveDTO = new PharInWaitReceiveDTO();
    pharInWaitReceiveDTO.setHospCode(hospCode);
    pharInWaitReceiveDTO.setStatusCode("0");
    pharInWaitReceiveDTO.setDeptId(MapUtils.get(map, "deptId"));
    pharInWaitReceiveDTO.setIsBack("0");
    queryWaitReceiveMap.put("hospCode", hospCode);
    queryWaitReceiveMap.put("pharInWaitReceiveDTO", pharInWaitReceiveDTO);
    //????????????????????????
    WrapperResponse<List<PharInWaitReceiveDTO>> waitResponse = pharInWaitReceiveService_consumer.queryPharInWaitReceiveApply(queryWaitReceiveMap);
    List<PharInWaitReceiveDTO> waitReceiveListAll = waitResponse.getData();
    if (ListUtils.isEmpty(waitReceiveListAll)) {
      return null;
    }

    //???????????????????????? ?????????  ????????????0  ????????????
    Map queryWaitReceiveMapIsBack = new HashMap();
    PharInWaitReceiveDTO pharInWaitReceiveDTOisBack = new PharInWaitReceiveDTO();
    pharInWaitReceiveDTOisBack.setHospCode(hospCode);
    pharInWaitReceiveDTOisBack.setDeptId(deptId);
    pharInWaitReceiveDTOisBack.setIsBack("1");
    queryWaitReceiveMapIsBack.put("hospCode", hospCode);
    queryWaitReceiveMapIsBack.put("pharInWaitReceiveDTO", pharInWaitReceiveDTOisBack);
    //????????????????????????
    WrapperResponse<List<PharInWaitReceiveDTO>> waitResponse1 = pharInWaitReceiveService_consumer.queryPharInWaitReceiveApply(queryWaitReceiveMapIsBack);
    List<PharInWaitReceiveDTO> isBackWaitReceiveList = waitResponse1.getData();
    // ????????????????????????
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
    //?????????????????????????????????
    List<PharInWaitReceiveDTO> lyList = new ArrayList<>();
    //????????????????????????????????????????????????lyList
    filterWaitReceive(orderReceiveDTO, waitReceiveListAll, lyList);
    AtomicInteger itemNum = new AtomicInteger();
    HashMap itemMap = new HashMap();
    List<PharInWaitReceiveDTO> sumList = new ArrayList<>();
    //????????????????????????
    sumList = getSummaryStock(lyList);
    sumList.forEach(dto -> {
      //????????????
      if (Constants.XMLB.YP.equals(dto.getItemCode()) || Constants.XMLB.CL.equals(dto.getItemCode())) {
        InptAdviceDTO inptAdviceDTO = new InptAdviceDTO();
        inptAdviceDTO.setHospCode(dto.getHospCode());
        inptAdviceDTO.setItemId(dto.getItemId());
        inptAdviceDTO.setPharId(dto.getPharId());
        inptAdviceDTO.setTotalNum(dto.getAllNum());
        inptAdviceDTO.setUnitCode(dto.getUnitCode());
        //????????????
        if (ListUtils.isEmpty(doctorAdviceBO.checkStock(inptAdviceDTO))) {
          itemNum.getAndIncrement();
          check.setCheckFlag(true);
          if(itemNum.get() <= 8 && !itemMap.containsKey(dto.getId())) {
            itemMap.put(dto.getId(),dto.getItemName());
            message.append("???");
            message.append(dto.getProductName());
            message.append(dto.getItemName());
            message.append(dto.getSpec());
            message.append("???,");
          }
        }
      }
    });

    check.setItemName(String.valueOf(message));
    return check;
  }


  /**
  * @Menthod getSummaryStock
  * @Desrciption ??????????????????????????????
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
   * @Desrciption ????????????????????????
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
   * @Desrciption ????????????????????????
   * @params [map]
   * @Author chenjun
   * @Date 2020-12-17 15:24
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
   **/
  @Override
  public PageDTO getApplySummaryList(Map map) {
    String hospCode = MapUtils.get(map, "hospCode");
    List<String> codeList = MapUtils.get(map, "codeList");
    //????????????????????????
    List<PharInWaitReceiveDTO> inWaitReceiveList = getPharInWaitReceiveDTOS(map, hospCode, codeList);
    List<PharInWaitReceiveDTO> sumList = new ArrayList<>();
    if (ListUtils.isEmpty(inWaitReceiveList)) {
      return PageDTO.of(sumList);
    }
    //????????????????????????
    sumList = getSummary(inWaitReceiveList);
    for (Iterator<PharInWaitReceiveDTO> it = sumList.iterator();it.hasNext();) {
      PharInWaitReceiveDTO dto = it.next();
      //????????????
      if (Constants.XMLB.YP.equals(dto.getItemCode()) || Constants.XMLB.CL.equals(dto.getItemCode())) {
        InptAdviceDTO inptAdviceDTO = new InptAdviceDTO();
        inptAdviceDTO.setHospCode(dto.getHospCode());
        inptAdviceDTO.setItemId(dto.getItemId());
        inptAdviceDTO.setPharId(dto.getPharId());
        inptAdviceDTO.setTotalNum(dto.getAllNum());
        inptAdviceDTO.setUnitCode(dto.getCurrUnitCode());
        //????????????,?????????????????????????????????
        if (ListUtils.isEmpty(doctorAdviceBO.checkStock(inptAdviceDTO))) {
          dto.setColor(Constants.COLOR.RED);
        }
      }
    }

    return PageDTO.of(sumList);
  }

  /**
   * @Method getPharInReceiveList
   * @Desrciption ?????????????????????
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
      // ??????????????????
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
   * @Desrciption ???????????????????????????
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
   * @Desrciption ????????????
   * @params [map]
   * @Author chenjun
   * @Date 2020-12-17 15:25
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   **/
  @Override
  public Boolean deleteCancelDrawMedicine(PharInReceiveDTO pharInReceiveDTO) {
    String hospCode = pharInReceiveDTO.getHospCode();
    //???????????????id????????????????????????
    Map mapQuery = new HashMap();
    PharInReceiveDetailDTO pharInReceiveDetailDTO = new PharInReceiveDetailDTO();
    pharInReceiveDetailDTO.setHospCode(pharInReceiveDTO.getHospCode());
    pharInReceiveDetailDTO.setReceiveId(pharInReceiveDTO.getId());
    if(StringUtils.isEmpty(pharInReceiveDTO.getId())) {
      throw new AppException("?????????????????????????????????");
    }
    pharInReceiveDetailDTO.setFlag("1");
    mapQuery.put("hospCode", hospCode);
    mapQuery.put("pharInReceiveDetailDTO", pharInReceiveDetailDTO);
    PharInReceiveDO inReceiveById = inptCostDAO.getInReceiveById(pharInReceiveDTO);
    if(!Constants.FYZT.QL.equals(inReceiveById.getStatusCode())) {
      throw new AppException("???????????????????????????????????????????????????");
    }
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
      throw new AppException("?????????????????????????????????");
    }
    // ?????????????????????
    pharInWaitReceiveService_consumer.updateInWaitStatus(mapUpdate);
    // ?????????????????????????????????
    pharInWaitReceiveService_consumer.updateInWaitStatusByWrIds(mapUpdate);
    // ????????????id????????????????????????
    WrapperResponse<List<PharInWaitReceiveDTO>> wrapperResponse = pharInWaitReceiveService_consumer.queryPharInWaitReceive(mapUpdate);
    List<PharInWaitReceiveDTO> pharInWaitReceiveDTOS = wrapperResponse.getData();
    // ????????????id??????
    List<String> costIds = pharInWaitReceiveDTOS.stream().map(PharInWaitReceiveDTO::getCostId).collect(Collectors.toList());
    InptCostDTO inptCostDTO = new InptCostDTO();
    inptCostDTO.setHospCode(hospCode);
    inptCostDTO.setIds(costIds);
    // ????????????????????????
    inptCostDTO.setBackCode(Constants.COST_TYZT.TFBTY);
    // ????????????????????????
    inptCostDAO.updateCostBackCodeStatus(inptCostDTO);

    //????????????????????????????????????
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
   * @Desrciption ???????????????????????????
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
   * @Desrciption ????????????
   * @params map
   * @Author pengbo
   * @Date 2021-05-12 15:20
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   */
  @Override
  public void saveAdvanceTakeMedicine(Map<String, Object> map) {

    String hospCode = MapUtils.get(map,"hospCode","");
    //????????????
    String days = String.valueOf(MapUtils.get(map,"advanceDays",0));
    //??????????????????
    String advanceType = MapUtils.get(map,"advanceType","0");

    if(StringUtils.isEmpty(advanceType)){
      throw new RuntimeException("???????????????????????????!");
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
      throw new RuntimeException("??????????????????????????????!");
    }

    parmMap.clear();
    String deptId = MapUtils.get(map,"deptId","");
    String endDate = MapUtils.get(map,"endDate","");
    parmMap.put("deptId",deptId);
    parmMap.put("hospCode",hospCode);
    parmMap.put("endDate",endDate);

    //8.??????????????????
    if("1".equals(baseOrderReceiveDTO.getIsEmergency())){
      throw new RuntimeException("?????????????????????????????????!");
    }
    //1.????????????????????????
    if(StringUtils.isNotEmpty(baseOrderReceiveDTO.getDeptIds())){
      if (!Arrays.asList(baseOrderReceiveDTO.getDeptIds().split(",")).contains(deptId)){
        throw new RuntimeException(baseOrderReceiveDTO.getName()+"????????????????????????!");
      }
    }
    //2.??????
    if(StringUtils.isNotEmpty(baseOrderReceiveDTO.getUsageCodes())){
      List<String > usageCodes = Arrays.asList(baseOrderReceiveDTO.getUsageCodes().split(","));
      parmMap.put("usageCodes",usageCodes);
    }else{
      parmMap.put("usageCodes",Arrays.asList(new String []{""}));
    }
    //3.????????????
    parmMap.put("isMaterial",baseOrderReceiveDTO.getIsMaterial());
    //4.???????????????
    parmMap.put("isIvdrip",baseOrderReceiveDTO.getIsLvp());
    //5.?????????????????????
    parmMap.put("isPh",baseOrderReceiveDTO.getIsPh());
    //6.???????????????
    parmMap.put("isHerb",baseOrderReceiveDTO.getIsHerb());
    //7.??????????????????
    parmMap.put("isGive",baseOrderReceiveDTO.getIsGive());
    //8.????????????
    parmMap.put("isAll",baseOrderReceiveDTO.getIsAll());
    //9.???????????????
    parmMap.put("isPatient",baseOrderReceiveDTO.getIsPatient());


    List<InptAdviceDTO> inptAdviceDTOS = inptAdviceDAO.selectAdviceByDeptAndType(parmMap);
    if(CollectionUtils.isEmpty(inptAdviceDTOS)){
      throw new RuntimeException("?????????????????????????????????!");
    }
    //????????????????????????
    map.put("id",SnowflakeUtils.getId());
    map.put("typeCode",advanceType);
    map.put("sfpy","0");
    inptAdviceDAO.insertMedicineAdvance(map);
    Map<String, Object> paramMap = null ;
    List<Map<String,Object>> mapList = new ArrayList<>();
    for (InptAdviceDTO adviceDTO: inptAdviceDTOS){
      paramMap = new HashMap<>();
      paramMap.put("id",SnowflakeUtils.getId());
      paramMap.put("advance_id",MapUtils.get(map,"id",""));
      paramMap.put("advice_id",adviceDTO.getId());
      paramMap.put("hosp_code",hospCode);
      paramMap.put("last_exec_time",adviceDTO.getLastExecTime());
      paramMap.put("this_exec_time",map.get("endDate"));
      mapList.add(paramMap);
    }
    //???????????????
    inptAdviceDAO.insertMedicineAdvanceAdvice(mapList);
    //??????????????????????????????,???????????????????????????????????????????????????????????????????????????
    inptAdviceDAO.updateAdvanceDays(inptAdviceDTOS,days);
    //???????????????????????????
    List<String> adviceIds = inptAdviceDTOS.stream().map(InptAdviceDTO::getId).collect(Collectors.toList());
    try{
      Map adviceMap = new HashMap();
      adviceMap.put("deptId",deptId);
      adviceMap.put("hospCode", hospCode);
      adviceMap.put("typeCode", advanceType);
      //????????????????????????,???????????????????????????
      MedicalAdviceDTO medicalAdviceDTO = new MedicalAdviceDTO();
      medicalAdviceDTO.setHospCode(hospCode);
      medicalAdviceDTO.setDeptId(deptId);
      medicalAdviceDTO.setCheckTime(DateUtils.getNow());
      medicalAdviceDTO.setCheckName(MapUtils.getVS(map,"crteName",""));
      medicalAdviceDTO.setCheckId(MapUtils.getVS(map,"crteId",""));
      //???????????????????????????????????????????????????ID
      medicalAdviceDTO.setSfTqly((String) map.get("id"));
      medicalAdviceDTO.setIds(adviceIds);
      adviceMap.put("medicalAdviceDTO", medicalAdviceDTO);
      medicalAdviceService_consumer.longCost(adviceMap);
    }catch (RuntimeException e){
      throw new RuntimeException("??????????????????????????????"+e.getMessage());
    }
    //?????????????????????
    List<List<String>>  groupedList = splitList(adviceIds,50);
    for(List<String> subList : groupedList){
      inptAdviceDAO.updateAdvanceDaysLastExcTime(subList,"0");
    }
  }

  /**
   *  ??????????????????????????????????????????????????????
   * @param sourceList ??????????????????List
   * @param groupSize  ?????????????????????????????????(????????????????????????????????????)
   * @return ????????????list
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
   * @Desrciption ??????????????????
   * @params map
   * @Author pengbo
   * @Date 2021-05-12 15:20
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   */
  @Override
  public Boolean updateAdvance(Map<String, Object> map) {
    map.put("sfpy","2");
    //????????????????????????
    inptAdviceDAO.updateMedicineAdvance(map);
    //?????????????????????????????????
    inptAdviceDAO.updateInptAdviceAdvanceDays(map);
    //??????????????????????????????????????????
    inptAdviceDAO.deleteMedicineAdvanceAdvice(map);
    return true;
  }

  @Override
  public List<Map<String, Object>> queryAllVisit(Map<String, Object> map) {
    return  pharInWaitReceiveService_consumer.queryAllVisit(map).getData();
  }

  //????????????????????????
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
    String ids = MapUtils.get(map, "ids");

    Map baseOrderReceiveMap = new HashMap();
    BaseOrderReceiveDTO baseOrderReceiveDTO = new BaseOrderReceiveDTO();
    baseOrderReceiveDTO.setHospCode(hospCode);
    baseOrderReceiveDTO.setId(id);
    baseOrderReceiveMap.put("hospCode", hospCode);
    baseOrderReceiveMap.put("baseOrderReceiveDTO", baseOrderReceiveDTO);
    WrapperResponse<BaseOrderReceiveDTO> response = baseOrderReceiveService.getById(baseOrderReceiveMap);
    BaseOrderReceiveDTO orderReceiveDTO = response.getData();


    //???????????????????????? ?????????  ????????????0  ????????????
    Map queryWaitReceiveMap = new HashMap();
    PharInWaitReceiveDTO pharInWaitReceiveDTO = new PharInWaitReceiveDTO();
    pharInWaitReceiveDTO.setHospCode(hospCode);
    pharInWaitReceiveDTO.setStatusCode("0");
    pharInWaitReceiveDTO.setDeptId(MapUtils.get(map, "deptId"));
    pharInWaitReceiveDTO.setIsBack("0");
    PharInWaitReceiveDTO pharInWaitReceiveDTO1 = MapUtils.get(map, "pharInWaitReceiveDTO");
    if (pharInWaitReceiveDTO1 != null){
      if (!StringUtils.isEmpty(pharInWaitReceiveDTO1.getIsEmergency())){
        // ?????????????????????
        pharInWaitReceiveDTO.setIsEmergency(pharInWaitReceiveDTO1.getIsEmergency());
        pharInWaitReceiveDTO.setIsLong(pharInWaitReceiveDTO1.getIsLong());
      }
      pharInWaitReceiveDTO.setIsAdvance(pharInWaitReceiveDTO1.getIsAdvance());
      pharInWaitReceiveDTO.setIds(pharInWaitReceiveDTO1.getIds());
    }

    if(!StringUtils.isEmpty(ids)){
      pharInWaitReceiveDTO.setIds(Arrays.asList(ids.split(",")));
    }

    queryWaitReceiveMap.put("hospCode", hospCode);
    queryWaitReceiveMap.put("pharInWaitReceiveDTO", pharInWaitReceiveDTO);
    //????????????????????????
    WrapperResponse<List<PharInWaitReceiveDTO>> waitResponse = pharInWaitReceiveService_consumer.queryPharInWaitReceiveApply(queryWaitReceiveMap);
    List<PharInWaitReceiveDTO> waitReceiveListAll = waitResponse.getData();
    if (ListUtils.isEmpty(waitReceiveListAll)) {
      return new ArrayList<>();
    }

    //???????????????????????? ?????????  ????????????0  ????????????
    Map queryWaitReceiveMapIsBack = new HashMap();
    PharInWaitReceiveDTO pharInWaitReceiveDTOisBack = new PharInWaitReceiveDTO();
    pharInWaitReceiveDTOisBack.setHospCode(hospCode);
    pharInWaitReceiveDTOisBack.setDeptId(MapUtils.get(map, "deptId"));
    pharInWaitReceiveDTOisBack.setIsBack("1");
    if (pharInWaitReceiveDTO1 != null){
      pharInWaitReceiveDTOisBack.setIds(pharInWaitReceiveDTO1.getIds());
    }
    if(!StringUtils.isEmpty(ids)){
      pharInWaitReceiveDTOisBack.setIds(Arrays.asList(ids.split(",")));
    }
    queryWaitReceiveMapIsBack.put("hospCode", hospCode);
    queryWaitReceiveMapIsBack.put("pharInWaitReceiveDTO", pharInWaitReceiveDTOisBack);
    //????????????????????????
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
    // update by 2021/9/29 ????????????????????????0?????????
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


    //??????????????????????????????????????????
    List<PharInWaitReceiveDTO> waitReceiveListListSx = new ArrayList<>();

    //?????????????????????????????????
    List<PharInWaitReceiveDTO> lyList = new ArrayList<>();

    //????????????????????????????????????????????????lyList
    filterWaitReceive(orderReceiveDTO, waitReceiveListAll, lyList);

    //?????????????????????????????????????????????????????????????????????????????????????????????qlbz
    waitReceiveListListSx.addAll(lyList);

    //???????????????????????????
    waitReceiveListListSx.sort(Comparator.comparing(PharInWaitReceiveDO::getVisitId).reversed().thenComparing(PharInWaitReceiveDO::getCrteTime));
    return waitReceiveListListSx;
  }


  // ???????????????????????????
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
      throw new AppException("??????????????????????????????" + e);
    }
    inReceiveDTO.setTotalPrice(totalPrice);
    inReceiveDTO.setStatusCode(Constants.LYZT.QL); //????????????,
    inReceiveDTO.setDeptId(deptId);
    inReceiveDTO.setCrteId(crteId);
    inReceiveDTO.setCrteName(crteName);
    inReceiveDTO.setCrteTime(new Date());
    //??????????????????
    Map<String, String> bwParam = new HashMap<String, String>();
    bwParam.put("hospCode", hospCode);//????????????
    bwParam.put("isValid", Constants.SF.S);//????????????
    bwParam.put("pharId", yfid);//??????id
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
    // ?????????????????? ?????????????????????
    for(PharInWaitReceiveDTO dto:summaryStock) {
      //????????????
      if (Constants.XMLB.YP.equals(dto.getItemCode()) || Constants.XMLB.CL.equals(dto.getItemCode())) {
        InptAdviceDTO inptAdviceDTO = new InptAdviceDTO();
        inptAdviceDTO.setHospCode(dto.getHospCode());
        inptAdviceDTO.setItemId(dto.getItemId());
        inptAdviceDTO.setPharId(dto.getPharId());
        inptAdviceDTO.setTotalNum(dto.getAllNum());
        inptAdviceDTO.setUnitCode(dto.getUnitCode());
        //????????????
        if (ListUtils.isEmpty(doctorAdviceBO.checkStock(inptAdviceDTO))) {
          stockMap.put(inptAdviceDTO.getItemId(),inptAdviceDTO.getPharId());
        }
      }
    }
    for(PharInWaitReceiveDTO dto:inReceiveList){
      // ????????????????????? ????????????????????????????????????
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
      //ph  ??????
      pharInReceiveDetailDTO.setDosage(dto.getDosage()); //????????????
      pharInReceiveDetailDTO.setDosageUnitCode(dto.getDosageUnitCode()); //???
      pharInReceiveDetailDTO.setSplitRatio(dto.getSplitRatio());
      pharInReceiveDetailDTO.setSplitUnitCode(dto.getSplitUnitCode());
      pharInReceiveDetailDTO.setSplitNum(dto.getSplitNum());
      pharInReceiveDetailDTO.setSplitPrice(dto.getSplitPrice());
      pharInReceiveDetailDTO.setChineseDrugNum(dto.getChineseDrugNum());
      pharInReceiveDetailDTO.setUsageCode(dto.getUsageCode());
      pharInReceiveDetailDTO.setCurrUnitCode(dto.getCurrUnitCode());
      pharInReceiveDetailList.add(pharInReceiveDetailDTO);
    }
    // ???????????????????????????
    if(!ListUtils.isEmpty(pharInReceiveDetailList)) {
      pharInReceiveList.add(inReceiveDTO);
    }
  }


  private void filterWaitReceive(BaseOrderReceiveDTO baseOrderReceiveDto, List<PharInWaitReceiveDTO> inWaitReceiveList, List<PharInWaitReceiveDTO> lyList) {

    for (PharInWaitReceiveDTO dto : inWaitReceiveList) {
      //??????????????????????????????
      if ("1".equals(baseOrderReceiveDto.getIsAll())) {
        //???????????????????????????????????????
        dto.setStatusCode("1");
        //?????????????????????????????????
        lyList.add(dto);
      } else if ("1".equals(baseOrderReceiveDto.getIsEmergency())) {
        if("1".equals(dto.getIsEmergency())){
          //???????????????????????????????????????
          dto.setStatusCode("1");
          //?????????????????????????????????
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
            //???????????????????????????????????????
            dto.setStatusCode("1");
            //?????????????????????????????????
            lyList.add(dto);
          }

        }
      }
    }
  }
}
