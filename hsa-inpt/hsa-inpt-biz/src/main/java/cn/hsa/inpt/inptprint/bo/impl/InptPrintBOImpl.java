package cn.hsa.inpt.inptprint.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.inpt.drawMedicine.bo.impl.DrawMedicineBOImpl;
import cn.hsa.module.inpt.doctor.bo.DoctorAdviceBO;
import cn.hsa.module.inpt.doctor.dao.InptBabyDAO;
import cn.hsa.module.inpt.doctor.dao.InptVisitDAO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptBabyDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.inptprint.bo.InptPrintBO;
import cn.hsa.module.inpt.inptprint.dao.InptPrintDAO;
import cn.hsa.module.inpt.inptprint.dto.InptAdvicePrintDTO;
import cn.hsa.module.inpt.nurse.dao.InptAdviceExecDAO;
import cn.hsa.module.inpt.nurse.dto.InptAdviceExecDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO;
import cn.hsa.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.inpt.inptprint.bo.impl
 * @Class_name: inptPrintBoImpl
 * @Describe: 住院打印
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/10/27 19:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class InptPrintBOImpl extends HsafBO implements InptPrintBO {

  @Resource
  private InptPrintDAO inptPrintDAO;

  @Resource
  private DrawMedicineBOImpl drawMedicineBO;

  @Resource
  private InptVisitDAO inptVisitDAO;

  @Resource
  private InptBabyDAO inptBabyDAO;
  @Resource
  private DoctorAdviceBO doctorAdviceBO;


  /**
   * @Menthod queryInptCostList
   * @Desrciption 费用清单打印
   *
   * @Param
   * [inptCostDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/10/27 19:41
   * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>
   **/
  @Override
  public Map queryInptCostListPrint(InptCostDTO inptCostDTO) {
    List<InptCostDTO> inptCostDTOS = inptPrintDAO.queryInptCostListPrint(inptCostDTO);
    Map<String, List<InptCostDTO>> collect = new TreeMap<>();
    if(ListUtils.isEmpty(inptCostDTOS)){
      throw new AppException("未查询到数据，请更改查询条件后再试");
      // return null;
    }
    if("1".equals(inptCostDTO.getPrintFlag())){
      collect = inptCostDTOS.stream().collect(Collectors.groupingBy(InptCostDTO::getCostDate,TreeMap::new,Collectors.toList()));
      return queryDetailCostByDay(collect);
    } else if("2".equals(inptCostDTO.getPrintFlag())){
      collect = inptCostDTOS.stream().collect(Collectors.groupingBy(InptCostDTO::getBfcName));
      return queryDetailCostByDay(collect);
    } else if("3".equals(inptCostDTO.getPrintFlag())){
      collect = inptCostDTOS.stream().collect(Collectors.groupingBy(InptCostDTO::getCostDate,TreeMap::new,Collectors.toList()));
      return queryStructureByDate(inptCostDTOS,collect);
    } else if("4".equals(inptCostDTO.getPrintFlag())){
      Map<String, BigDecimal> collect1 = inptCostDTOS.stream().collect(Collectors.groupingBy(InptCostDTO::getBfcName,
        Collectors.mapping(InptCostDTO::getAmountMoney, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
      return queryStructureByAll(inptCostDTOS,collect1);
    } else if("5".equals(inptCostDTO.getPrintFlag())){
      List<InptCostDTO> inptCostDTOS1 = inptPrintDAO.queryInptItemCostListPrint(inptCostDTO);
      collect = inptCostDTOS1.stream().collect(Collectors.groupingBy(InptCostDTO::getBfcName));
      return queryDetailCostByDay(collect);
    }else if("6".equals(inptCostDTO.getPrintFlag())){
      List<InptCostDTO> inptCostDTOS1 = inptPrintDAO.queryInptItemCostListPrint(inptCostDTO);
      collect = inptCostDTOS1.stream().collect(Collectors.groupingBy(s->s.getCostDate()+'-'+s.getBfcName(),TreeMap::new,Collectors.toList()));
      return queryDetailCostByDay(collect);
    } else {
      return null;
    }
  }

  //费用清单批量打印 author：luoyong date：2021-03-05
  @Override
  public Map queryRegisteredPageBatch(InptCostDTO inptCostDTO) {
    List<InptCostDTO> inptCostDTOS = inptPrintDAO.queryInptCostListPrintBatch(inptCostDTO);
    if (ListUtils.isEmpty(inptCostDTOS)) {
      return null;
    }
    Map<String, List<InptCostDTO>> resultMap = inptCostDTOS.stream().collect(Collectors.groupingBy(dto -> dto.getVisitId()));

    if ("1".equals(inptCostDTO.getPrintFlag())) {
      for (String visit : resultMap.keySet()) {
        resultMap.get(visit).stream().collect(Collectors.groupingBy(InptCostDTO::getCostDate,TreeMap::new,Collectors.toList()));
      }
      return null;
    } else if ("2".equals(inptCostDTO.getPrintFlag())) {
      return null;
    } else if ("3".equals(inptCostDTO.getPrintFlag())) {
      return null;
    } else if ("4".equals(inptCostDTO.getPrintFlag())) {
      return null;
    } else if ("5".equals(inptCostDTO.getPrintFlag())) {
      return null;
    } else {
      return null;
    }
  }

  @Resource
  InptAdviceExecDAO inptAdviceExecDao;


  /*
   * @Menthod queryDetailCostByDay
   * @Desrciption 明细格式逐日/汇总打印
   *
   * @Param
   * [inptCostDTOS]
   *
   * @Author jiahong.yang
   * @Date   2020/10/29 15:19
   * @Return java.util.List<java.util.Map>
   **/
  public Map queryDetailCostByDay(Map<String, List<InptCostDTO>> collect){
    List<Map> list= new ArrayList<>();
    Map mapdata = new HashMap();
    BigDecimal total = BigDecimal.valueOf(0);
    if(collect.size() > 0){
      for (String key : collect.keySet()) {
        List<InptCostDTO> mid = Arrays.asList(new InptCostDTO[collect.get(key).size()]);
        Collections.copy(mid,collect.get(key));
        BigDecimal sum = BigDecimal.valueOf(0);
        for (int i = 0; i < mid.size(); i++) {
          sum = BigDecimalUtils.add(sum,mid.get(i).getAmountMoney());
          if(BigDecimalUtils.isZero(BigDecimalUtils.nullToZero(mid.get(i).getNum()))){
            collect.get(key).remove(mid.get(i));
          }
        }
        Map map = new HashMap();
        map.put("data",collect.get(key));
        map.put("key",key);
        total = BigDecimalUtils.add(total,sum);
        map.put("sum",sum);
        list.add(map);
        mapdata.put("startCostDate",mid.get(0).getStartCostDate());
        mapdata.put("endCostDate",mid.get(0).getEndCostDate());
      }
    }
    mapdata.put("listData",list);
    mapdata.put("total",total);
    return mapdata;
  }

  /**
   * @Menthod queryItemCostByDay
   * @Desrciption 项目汇总打印
   *
   * @Param
   * [collect]
   *
   * @Author jiahong.yang
   * @Date   2020/12/21 19:02
   * @Return java.util.Map
   **/
  public Map queryItemCostByDay(Map<String, List<InptCostDTO>> collect){
    List<Map> list= new ArrayList<>();
    Map mapdata = new HashMap();
    BigDecimal total = BigDecimal.valueOf(0);
    for (String key : collect.keySet()) {
      Map map = new HashMap();
      map.put("data",collect.get(key));
      map.put("key",key);
      BigDecimal sum = BigDecimal.valueOf(0);
      for(InptCostDTO s:collect.get(key)){
        sum = BigDecimalUtils.add(sum,s.getAmountMoney());
      }
      total = BigDecimalUtils.add(total,sum);
      map.put("sum",sum);
      list.add(map);
    }
    mapdata.put("listData",list);
    mapdata.put("total",total);
    return mapdata;
  }

  /**
   * @Menthod queryStructureByDate
   * @Desrciption  费用清单结构格式逐日打印
   *
   * @Param
   * [collect]
   *
   * @Author jiahong.yang
   * @Date   2020/10/29 19:21
   * @Return java.util.Map
   **/
  public Map queryStructureByDate(List<InptCostDTO> inptCostDTOS,Map<String, List<InptCostDTO>> collect){
    Map mapdata = new HashMap();
    List<Map> list= new ArrayList<>();
    BigDecimal total = BigDecimal.valueOf(0);
    mapdata.put("startCostDate",inptCostDTOS.get(0).getStartCostDate());
    mapdata.put("endCostDate",inptCostDTOS.get(0).getEndCostDate());
    for (String key : collect.keySet()) {
      List<Map> list1 = new ArrayList<>();
      Map map = new HashMap();
      Map<String, BigDecimal> collect1 = collect.get(key).stream().collect(Collectors.groupingBy(InptCostDTO::getBfcName,
        Collectors.mapping(InptCostDTO::getAmountMoney, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
      map.put("key",key);
      Map map1 = new HashMap();
      BigDecimal sum = BigDecimal.valueOf(0);
      int num = 0;
      int flag = 0;
      for (String key1 : collect1.keySet()) {
        flag = flag + 1;
        sum = BigDecimalUtils.add(sum,collect1.get(key1));
        map1.put("key" + num ,key1);
        map1.put("value" + num,collect1.get(key1));
        num = num + 1;

        if(num == 2){
          list1.add(map1);
          map1 = new HashMap();
          num = 0;
        }

        if(flag == collect1.size() && map1.size() > 0){
          list1.add(map1);
        }
      }
      map.put("data",list1);
      map.put("sum",sum);
      total = BigDecimalUtils.add(total,sum);
      list.add(map);
    }
    mapdata.put("listData",list);
    mapdata.put("total",total);
    return mapdata;
  }

  /**
   * @Menthod queryStructureByAll
   * @Desrciption 费用清单结构格式汇总打印
   *
   * @Param
   * [inptCostDTOS, collect]
   *
   * @Author jiahong.yang
   * @Date   2020/10/30 15:36
   * @Return java.util.Map
   **/
  public Map queryStructureByAll(List<InptCostDTO> inptCostDTOS,Map<String, BigDecimal> collect ) {
    Map mapdata = new HashMap();
    List<Map> list= new ArrayList<>();
    BigDecimal total = BigDecimal.valueOf(0);
    int num = 0;
    mapdata.put("startCostDate",inptCostDTOS.get(0).getStartCostDate());
    mapdata.put("endCostDate",inptCostDTOS.get(0).getEndCostDate());
    Map map = new HashMap();
    int flag = 0;
    for (String key : collect.keySet()) {
      flag = flag + 1;
      total = BigDecimalUtils.add(total,collect.get(key));
      map.put("key" + num ,key);
      map.put("value" + num,collect.get(key));
      num = num + 1;

      if(num == 2){
        list.add(map);
        map = new HashMap();
        num = 0;
      }

      if(flag == collect.size() && map.size() > 0){
        list.add(map);
      }
    }
    mapdata.put("listData",list);
    mapdata.put("total",total);
    return mapdata;
  }

  /**
   * @Menthod getApplyDetailsListPrint
   * @Desrciption 领药申请打印按病人
   *
   * @Param
   * [map]
   *
   * @Author jiahong.yang
   * @Date   2020/10/30 16:15
   * @Return java.util.Map
   **/
  @Override
  public Map getApplyDetailsListPrint(Map map) {
    String flag = MapUtils.get(map, "flag");
    if("1".equals(flag)){
      return getApplyListPrint(map);
    } else {
      return getApplySumPrint(map);
    }
  }

  /**
   * @Menthod getApplyListPrint
   * @Desrciption 按病人打印
   *
   * @Param
   * [map]
   *
   * @Author jiahong.yang
   * @Date   2020/10/30 16:50
   * @Return java.util.Map
   **/
  private Map getApplyListPrint(Map map){
    String hospCode = MapUtils.get(map, "hospCode");
    List<String> codeList = MapUtils.get(map, "codeList");
    List<Map> listMap = new ArrayList<>();
    List<PharInWaitReceiveDTO> list = drawMedicineBO.getPharInWaitReceiveDTOS(map, hospCode, codeList);
    Map<String, List<PharInWaitReceiveDTO>> collect = list.stream().collect(Collectors.groupingBy(PharInWaitReceiveDTO::getVisitId));
    for (String key : collect.keySet()) {
      InptVisitDTO inptVisitDTO = new InptVisitDTO();
      inptVisitDTO.setHospCode(hospCode);
      inptVisitDTO.setId(key);
      InptVisitDTO inptVisitById = inptVisitDAO.getInptVisitById(inptVisitDTO);
      Map dataMap = new HashMap();
      dataMap.put("title",inptVisitById);
      dataMap.put("data",collect.get(key));
      listMap.add(dataMap);
    }
    Map map1 = new HashMap();
    map1.put("data",listMap);
    return map1;
  }

  /**
   * @Menthod getApplySumPrint
   * @Desrciption 领药申请不按病人打印
   *
   * @Param
   * [map]
   *
   * @Author jiahong.yang
   * @Date   2020/10/31 11:27
   * @Return java.util.Map
   **/
  private Map getApplySumPrint(Map map) {
    String hospCode = MapUtils.get(map, "hospCode");
    List<String> codeList = MapUtils.get(map, "codeList");
    //领药申请详情明细
    List<PharInWaitReceiveDTO> inWaitReceiveList = drawMedicineBO.getPharInWaitReceiveDTOS(map, hospCode, codeList);
    List<PharInWaitReceiveDTO> sumList = new ArrayList<>();
    if(ListUtils.isEmpty(inWaitReceiveList)){
      return null;
    }
    //领药申请详情汇总
    sumList = drawMedicineBO.getSummary(inWaitReceiveList);
    // 过滤掉库存不足的
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
        //判断库存,如果库存为空就移除
        if (ListUtils.isEmpty(doctorAdviceBO.checkStock(inptAdviceDTO))) {
          it.remove();
        }
      }
    }
    // 根据预配药每个药品的汇总金额做排序，解决打印的预配药单据和配药单据数据顺序不一致的问题
    sumList.sort(Comparator.comparing(PharInWaitReceiveDTO::getAllTotalPrice));
    Map map1 = new HashMap();
    map1.put("data",sumList);
    return map1;
  }

  /**
   * @Menthod updateAdvicePrint
   * @Desrciption 查询医嘱打印表数据，并回写医嘱打印表
   *
   * @Param
   * [inptAdvicePrintDTO]
   *
   * @Author jiahong.yang
   * @Date   2021/1/15 10:50
   * @Return java.util.List<cn.hsa.module.inpt.inptprint.dto.InptAdvicePrintDTO>
   **/
  @Override
  public List<InptAdvicePrintDTO> updateAdvicePrint(InptAdvicePrintDTO inptAdvicePrintDTO) {
    // 根据就诊id查询医嘱主信息
    List<InptAdvicePrintDTO> inptAdviceDTOS = inptPrintDAO.queryAdviceByVistiId(inptAdvicePrintDTO);
    // 查询医嘱打印表信息
    List<InptAdvicePrintDTO> inptAdvicePrintDTOS = inptPrintDAO.queryAdvicePrintByVisitId(inptAdvicePrintDTO);
    // 如果医嘱信息表为空，直接返回空值
    if(ListUtils.isEmpty(inptAdviceDTOS)) {
      return null;
    }
    // 如果医嘱打印表信息为空，则直接插入医嘱信息
    if(ListUtils.isEmpty(inptAdvicePrintDTOS)) {
      for (int i = 0; i < inptAdviceDTOS.size(); i++) {
        inptAdviceDTOS.get(i).setId(SnowflakeUtils.getId());
        inptAdviceDTOS.get(i).setIsValid("1");
        inptAdviceDTOS.get(i).setSeqNo(i);
      }
      inptPrintDAO.insertAdvicePrint(inptAdviceDTOS);
      inptAdvicePrintDTO.setIsValid("1");
      return inptPrintDAO.queryAdvicePrintByVisitId(inptAdvicePrintDTO);
    }

    // 医嘱id为key值
    Map<String, List<InptAdvicePrintDTO>> collect = inptAdvicePrintDTOS.stream().collect(Collectors.groupingBy(InptAdvicePrintDTO::getIaId));
    List<InptAdvicePrintDTO> updateList = new ArrayList<>();

    // 修改医嘱打印表数据 根据医嘱信息表
    for(InptAdvicePrintDTO inptAdvicePrintDTO1 : inptAdviceDTOS){
      if(collect.containsKey(inptAdvicePrintDTO1.getIaId())){
        InptAdviceExecDTO inptAdviceExecDTO = new InptAdviceExecDTO();
        inptAdviceExecDTO.setHospCode(inptAdvicePrintDTO.getHospCode());
        inptAdviceExecDTO.setAdviceId(inptAdvicePrintDTO1.getIaId());
        // 查询最近执行时间
        InptAdviceExecDTO inptAdviceExecDTO1 = inptAdviceExecDao.queryAdviceExecLately(inptAdviceExecDTO);
        inptAdvicePrintDTO1.setLastExecTime(inptAdviceExecDTO1 == null ? null:inptAdviceExecDTO1.getExecTime());
        // 主键id
        inptAdvicePrintDTO1.setId(collect.get(inptAdvicePrintDTO1.getIaId()).get(0).getId());
        // 序号
        inptAdvicePrintDTO1.setSeqNo(collect.get(inptAdvicePrintDTO1.getIaId()).get(0).getSeqNo());
        // 有效值
        inptAdvicePrintDTO1.setIsValid(collect.get(inptAdvicePrintDTO1.getIaId()).get(0).getIsValid());
        updateList.add(inptAdvicePrintDTO1);
      }
    }
    // 不为空就执行修改操作
    if(!ListUtils.isEmpty(updateList)) {
      int update = inptPrintDAO.updateAdvicePrint(updateList);
    }
    inptAdvicePrintDTO.setIsValid("1");
    List<InptAdvicePrintDTO> inptAdvicePrintDTOS1 = inptPrintDAO.queryAdvicePrintByVisitId(inptAdvicePrintDTO);
    for (InptAdvicePrintDTO advicePrintDTO : inptAdvicePrintDTOS1) {
      if(!StringUtils.isEmpty(advicePrintDTO.getDosage())){
        String dosage = advicePrintDTO.getDosage();
        String [] low = dosage.split("\\.");
        dosage = Integer.parseInt(low[1])<=0?low[0]:dosage;
        advicePrintDTO.setDosage(dosage);
      }
    }
    return inptAdvicePrintDTOS1;
  }

  /**
   * @Menthod updateAdvicePrintStatus
   * @Desrciption 批量修改状态
   *
   * @Param
   * [inptAdvicePrintDTO]
   *
   * @Author jiahong.yang
   * @Date   2021/1/18 17:27
   * @Return java.lang.Boolean
   **/
  @Override
  public Boolean updateAdvicePrintStatus(InptAdvicePrintDTO inptAdvicePrintDTO) {
    inptAdvicePrintDTO.setIsValid("1");
    List<InptAdvicePrintDTO> inptAdvicePrintDTOS = inptPrintDAO.queryAdvicePrintByVisitId(inptAdvicePrintDTO);
    if(!ListUtils.isEmpty(inptAdvicePrintDTOS)){
      for (int i = 0; i < inptAdvicePrintDTOS.size(); i++) {
        inptAdvicePrintDTOS.get(i).setIsInPrint("1");
        // 如果存在停嘱医生，则已打印
        if(!StringUtils.isEmpty(inptAdvicePrintDTOS.get(i).getStopDoctorId())){
          inptAdvicePrintDTOS.get(i).setIsStopPrint("1");
        }
        // 如果存在执行人，则已打印
        if(!StringUtils.isEmpty(inptAdvicePrintDTOS.get(i).getExecId())){
          inptAdvicePrintDTOS.get(i).setIsExecPrint("1");
        }
        // 如果已经皮试，则已打印
        if("1".equals(inptAdvicePrintDTOS.get(i).getIsSkin()) && !StringUtils.isEmpty(inptAdvicePrintDTOS.get(i).getIsPositive())){
          inptAdvicePrintDTOS.get(i).setIsSkinPrint("1");
        }
        // 如果存在核收人，则已打印
        if(!StringUtils.isEmpty(inptAdvicePrintDTOS.get(i).getCheckId())){
          inptAdvicePrintDTOS.get(i).setIsInCheckPrint("1");
        }
        // 如果存在停嘱核收人，则已打印
        if(!StringUtils.isEmpty(inptAdvicePrintDTOS.get(i).getStopCheckId())){
          inptAdvicePrintDTOS.get(i).setIsStopCheckPrint("1");
        }
      }
      inptPrintDAO.updateAdvicePrintStatus(inptAdvicePrintDTOS);
    }
    return true;
  }

  /**
   * @Menthod updateAdvicePrintResetStatus
   * @Desrciption 打印状态重置为0
   *
   * @Param
   * [inptAdvicePrintDTO]
   *
   * @Author jiahong.yang
   * @Date   2021/4/21 15:01
   * @Return java.lang.Boolean
   **/
  @Override
  public Boolean updateAdvicePrintResetStatus(InptAdvicePrintDTO inptAdvicePrintDTO) {
    int i = inptPrintDAO.updateAdvicePrintResetStatus(inptAdvicePrintDTO);
    return i > 0;
  }

  /**
   * @Menthod updateResetPrint
   * @Desrciption
   *
   * @Param  重置医嘱打印格式
   * [inptAdvicePrintDTO]
   *
   * @Author jiahong.yang
   * @Date   2021/4/15 16:17
   * @Return java.lang.Boolean
   **/
  @Override
  public Boolean updateResetPrint(InptAdvicePrintDTO inptAdvicePrintDTO) {
    // 删除医嘱打印表中该患者的记录
    inptPrintDAO.deleteAdvicePrintByVisit(inptAdvicePrintDTO);
    // 根据id升序排序查出该患者的已提交医嘱
    List<InptAdvicePrintDTO> inptAdvicePrintDTOS = inptPrintDAO.queryAdviceByVistiId(inptAdvicePrintDTO);
    if(ListUtils.isEmpty(inptAdvicePrintDTOS)) {
      return true;
    }
    for (int i = 0; i < inptAdvicePrintDTOS.size(); i++) {
      InptAdviceExecDTO inptAdviceExecDTO = new InptAdviceExecDTO();
      inptAdviceExecDTO.setHospCode(inptAdvicePrintDTO.getHospCode());
      inptAdviceExecDTO.setAdviceId(inptAdvicePrintDTOS.get(i).getIaId());
      // 查询最近执行时间
      InptAdviceExecDTO inptAdviceExecDTO1 = inptAdviceExecDao.queryAdviceExecLately(inptAdviceExecDTO);
      // 设置默认有效
      inptAdvicePrintDTOS.get(i).setIsValid("1");
      // 设置主键
      inptAdvicePrintDTOS.get(i).setId(SnowflakeUtils.getId());
      // 设置序号
      inptAdvicePrintDTOS.get(i).setSeqNo(i);
      // 最近执行时间
      inptAdvicePrintDTOS.get(i).setLastExecTime(inptAdviceExecDTO1 == null ? null:inptAdviceExecDTO1.getExecTime());
    }
    // 插入医嘱打印表
    inptPrintDAO.insertAdvicePrint(inptAdvicePrintDTOS);
    return true;
  }

  /**
   * @Menthod saveAdvicePrint
   * @Desrciption 保存医嘱打印格式1
   *
   * @Param
   * [inptAdvicePrintDTO]
   *
   * @Author jiahong.yang
   * @Date   2021/4/15 16:17
   * @Return java.lang.Boolean
   **/
  @Override
  public Boolean saveAdvicePrint(InptAdvicePrintDTO inptAdvicePrintDTO) {
    if(StringUtils.isEmpty(inptAdvicePrintDTO.getIds1())) {
      throw new AppException("未传入医嘱信息");
    }
    if(StringUtils.isEmpty(inptAdvicePrintDTO.getIsLong())) {
      throw new AppException("未传入长期短期医嘱标识");
    }
    if(!StringUtils.isEmpty(inptAdvicePrintDTO.getDeleteIds())){
      InptAdvicePrintDTO delete = new InptAdvicePrintDTO();
      delete.setDeleteIds(inptAdvicePrintDTO.getDeleteIds());
      delete.setHospCode(inptAdvicePrintDTO.getHospCode());
      delete.setVisitId(inptAdvicePrintDTO.getVisitId());
      // 根据传过来的删除id串，修改状态
      inptPrintDAO.updateAdvicePrintIsVlidStatus(delete);
    }
    inptAdvicePrintDTO.setIsValid("1");
    // 根据前端传过来的id顺序进行查询，返回顺序为前端页面顺序
    List<InptAdvicePrintDTO> inptAdvicePrintDTOS = inptPrintDAO.queryAdvicePrintByIds(inptAdvicePrintDTO);
    if(ListUtils.isEmpty(inptAdvicePrintDTOS)) {
      throw new AppException("该患者没有有效可打印医嘱");
    }
    String[] split = inptAdvicePrintDTO.getIds1().split(",");
    List<String> ids = Arrays.asList(split);
    if(ids.size() != inptAdvicePrintDTOS.size()) {
      throw new AppException("数据错误");
    }
    // 根据传过来的id顺序，给查出来的医嘱信息进行序号设置
    for (int i = 0; i < ids.size(); i++) {
      for (InptAdvicePrintDTO item: inptAdvicePrintDTOS) {
        if(ids.get(i).equals(item.getId())) {
          item.setSeqNo(i);
        }
      }
    }
    inptAdvicePrintDTO.setDeleteIds(null);
    inptPrintDAO.updateAdvicePrintStatus(inptAdvicePrintDTOS);
    return true;
  }

  /**
   * @Menthod queryAdvicePrintDTOByVisit
   * @Desrciption 查询打印医嘱信息
   *
   * @Param
   * [inptAdvicePrintDTO]
   *
   * @Author jiahong.yang
   * @Date   2021/4/20 19:23
   * @Return java.util.List<cn.hsa.module.inpt.inptprint.dto.InptAdvicePrintDTO>
   **/
  @Override
  public List<InptAdvicePrintDTO> queryAdvicePrintDTOByVisit(InptAdvicePrintDTO inptAdvicePrintDTO) {
    if(StringUtils.isEmpty(inptAdvicePrintDTO.getVisitId())) {
      throw new AppException("就诊id不能为空");
    }
    List<InptAdvicePrintDTO> inptAdvicePrintDTOS = inptPrintDAO.queryAdvicePrintByVisitId(inptAdvicePrintDTO);
    return inptAdvicePrintDTOS;
  }



  /**
   * @Menthod queryBabyInptCostListPrint
   * @Desrciption 获取婴儿费用清单
   * @Param [inptCostDTO]
   * @Author liuliyun
   * @Date   2021/9/23 8:40
   * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>
   **/
  @Override
  public Map queryBabyInptCostListPrint(InptCostDTO inptCostDTO) {
    Map mergeList = new HashMap();
    List<Map> babyCostList = new ArrayList<>();
    // 获取大人费用
    Map patientCost=queryInptCostListPrint(inptCostDTO);
    mergeList.put("patientCost",patientCost);
    BigDecimal totalCost = BigDecimal.valueOf(0);
    totalCost = BigDecimalUtils.add(totalCost, (BigDecimal) patientCost.get("total"));
    InptBabyDTO inptBabyDTO=new InptBabyDTO();
    inptBabyDTO.setVisitId(inptCostDTO.getVisitId());
    inptBabyDTO.setHospCode(inptCostDTO.getHospCode());
    List<InptBabyDTO> inptBabyDTOS=inptBabyDAO.findByCondition(inptBabyDTO);
    if (inptBabyDTOS!=null && inptBabyDTOS.size()>0) {
      for (InptBabyDTO inptBabyDTO1:inptBabyDTOS) {
        inptCostDTO.setBabyId(inptBabyDTO1.getId());
        // 获取婴儿费用
        Map babyCost = queryBabyCost(inptCostDTO);
        if (babyCost == null || babyCost.isEmpty()){
          continue;
        }
        totalCost = BigDecimalUtils.add(totalCost, (BigDecimal) babyCost.get("total"));
        babyCostList.add(babyCost);
      }
    }
    mergeList.put("babyCost",babyCostList);
    mergeList.put("totalCost",totalCost);
    return mergeList;
  }


  public Map queryBabyCost(InptCostDTO inptCostDTO) {
    inptCostDTO.setQueryBaby("");
    List<InptCostDTO> inptCostDTOS = inptPrintDAO.queryInptCostListPrint(inptCostDTO);
    Map<String, List<InptCostDTO>> collect = new TreeMap<>();
    if(ListUtils.isEmpty(inptCostDTOS)){
      return null;
    }
    if("1".equals(inptCostDTO.getPrintFlag())){
      collect = inptCostDTOS.stream().collect(Collectors.groupingBy(InptCostDTO::getCostDate,TreeMap::new,Collectors.toList()));
      return queryDetailCostByDay(collect);
    } else if("2".equals(inptCostDTO.getPrintFlag())){
      collect = inptCostDTOS.stream().collect(Collectors.groupingBy(InptCostDTO::getBfcName));
      return queryDetailCostByDay(collect);
    } else if("3".equals(inptCostDTO.getPrintFlag())){
      collect = inptCostDTOS.stream().collect(Collectors.groupingBy(InptCostDTO::getCostDate,TreeMap::new,Collectors.toList()));
      return queryStructureByDate(inptCostDTOS,collect);
    } else if("4".equals(inptCostDTO.getPrintFlag())){
      Map<String, BigDecimal> collect1 = inptCostDTOS.stream().collect(Collectors.groupingBy(InptCostDTO::getBfcName,
        Collectors.mapping(InptCostDTO::getAmountMoney, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
      return queryStructureByAll(inptCostDTOS,collect1);
    } else if("5".equals(inptCostDTO.getPrintFlag())){
      List<InptCostDTO> inptCostDTOS1 = inptPrintDAO.queryInptItemCostListPrint(inptCostDTO);
      collect = inptCostDTOS1.stream().collect(Collectors.groupingBy(InptCostDTO::getBfcName));
      return queryDetailCostByDay(collect);
    }else if("6".equals(inptCostDTO.getPrintFlag())){
      List<InptCostDTO> inptCostDTOS1 = inptPrintDAO.queryInptItemCostListPrint(inptCostDTO);
      collect = inptCostDTOS1.stream().collect(Collectors.groupingBy(s->s.getCostDate()+'-'+s.getBfcName(),TreeMap::new,Collectors.toList()));
      return queryDetailCostByDay(collect);
    } else {
      return null;
    }
  }
}
