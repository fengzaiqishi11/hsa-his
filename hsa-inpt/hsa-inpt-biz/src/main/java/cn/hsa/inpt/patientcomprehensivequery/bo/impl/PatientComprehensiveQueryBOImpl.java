package cn.hsa.inpt.patientcomprehensivequery.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.base.bpft.dto.BasePreferentialDTO;
import cn.hsa.module.base.bpft.dto.BasePreferentialTypeDTO;
import cn.hsa.module.base.bpft.service.BasePreferentialService;
import cn.hsa.module.inpt.doctor.dao.InptCostDAO;
import cn.hsa.module.inpt.doctor.dao.InptVisitDAO;
import cn.hsa.module.inpt.doctor.dto.*;
import cn.hsa.module.inpt.doctor.entity.InptCostDO;
import cn.hsa.module.inpt.fees.dto.InptSettleDTO;
import cn.hsa.module.inpt.patientcomprehensivequery.bo.PatientComprehensiveQueryBO;
import cn.hsa.module.inpt.patientcomprehensivequery.dao.PatientComprehensiveQueryDAO;
import cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO;
import cn.hsa.module.mris.mrisHome.dto.InptBedChangeInfoDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.module.inpt.patientcomprehensivequery
 * @Class_name: PatientComprehensiveQueryService
 * @Describe:  病人综合查询业务逻辑接口实现层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/10/12 10:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class PatientComprehensiveQueryBOImpl extends HsafBO implements PatientComprehensiveQueryBO {

    @Resource
    private PatientComprehensiveQueryDAO patientComprehensiveQueryDAO;

    /**
     * @Method queryPatientDiagnosePage()
     * @Desrciption  病人诊断信息查询
     * @Param InptVisitDTO 住院就诊病人DTO数据传输对象
     *
     * @Author fuhui
     * @Date   2020/10/12 15:55
     * @Return 病人诊断分页信息
     **/
    @Override
    public PageDTO queryPatientDiagnosePage(InptDiagnoseDTO inptDiagnoseDTO) {
        PageHelper.startPage(inptDiagnoseDTO.getPageNo(),inptDiagnoseDTO.getPageSize());
        List<InptDiagnoseDTO> inptDiagnoseDTOList = patientComprehensiveQueryDAO.queryPatientDiagnose(inptDiagnoseDTO);
        return PageDTO.of(inptDiagnoseDTOList);
    }
  /**
   * @Method queryCostDetail
   * @Desrciption 费用详情查询
   * @Param
   * [patientCompreHensiveQueryDTO]
   * @Author liaojunjie
   * @Date   2020/10/22 21:42
   * @Return cn.hsa.base.PageDTO
   **/
  @Override
  public PageDTO queryCostDetail(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO) {
    PageHelper.startPage(patientCompreHensiveQueryDTO.getPageNo(),patientCompreHensiveQueryDTO.getPageSize());
    List<InptCostDTO> patientCompreHensiveQueryDTOS = patientComprehensiveQueryDAO.queryCostDetail(patientCompreHensiveQueryDTO);
    return PageDTO.of(patientCompreHensiveQueryDTOS);
  }

  /**
     * @Method queryAdvice()
     * @Desrciption  查询临时医嘱和长期医嘱的信息
     * @Param inptVisitDTO：visitId：就诊id isLong：是否长期医嘱
     *
     * @Author fuhui
     * @Date   2020/10/12 17:20
     * @Return 医嘱信息
     **/
    @Override
    public List<InptAdviceDTO> queryAdvice(InptAdviceDTO inptAdviceDTO) {
        List<InptAdviceDTO> inptAdviceDTOList = patientComprehensiveQueryDAO.queryAdvice(inptAdviceDTO);
        for(int i=0;i<inptAdviceDTOList.size();i++){
          inptAdviceDTOList.get(i).setExecOrder(i);
        }
        return inptAdviceDTOList;
    }
  /**
  * @Menthod queryAdviceSummary
  * @Desrciption 病人综合查询--医嘱汇总查询
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/13 9:15
  * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDTO>
  **/
  @Override
  public PageDTO queryAdviceSummary(InptAdviceDTO inptAdviceDTO) {
    PageHelper.startPage(inptAdviceDTO.getPageNo(), inptAdviceDTO.getPageSize());
    List<InptAdviceDTO> inptVisitDTOList =  patientComprehensiveQueryDAO.queryAdviceSummary(inptAdviceDTO);
    return PageDTO.of(inptVisitDTOList);
  }

  /**
  * @Menthod queryAdviceSummaryDetail
  * @Desrciption 病人综合查询---查询医嘱汇总信息明细
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/13 14:00
  * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDetailDTO>
  **/
  @Override
  public PageDTO queryAdviceSummaryDetail(InptAdviceDetailDTO inptAdviceDetailDTO) {
    PageHelper.startPage(inptAdviceDetailDTO.getPageNo(), inptAdviceDetailDTO.getPageSize());
    List<InptAdviceDetailDTO> inptAdviceDetailDTOS =  patientComprehensiveQueryDAO.queryAdviceSummaryDetail(inptAdviceDetailDTO);
    return PageDTO.of(inptAdviceDetailDTOS);
  }

  /**
  * @Menthod queryAdviceLongOrShort
  * @Desrciption 病人综合查询--长期/短期医嘱查询
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/13 9:16
  * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDTO>
  **/
  @Override
  public List<InptAdviceDTO> queryAdviceLongOrShort(InptAdviceDTO InptAdviceDTO) {
    return patientComprehensiveQueryDAO.queryAdviceLongOrShort(InptAdviceDTO);
  }

  /**
   * @Method querySettleInfo
   * @Desrciption  查询结算详细信息
   * @Param
   * map [ 就诊号，当前科室，医院编码 ]
   * @Author zhangxuan
   * @Date   2020-10-14 14:56
   * @Return map
   **/

  @Override
  public PageDTO querySettleInfo(InptSettleDTO inptSettleDTO) {
    PageHelper.startPage(inptSettleDTO.getPageNo(), inptSettleDTO.getPageSize());
    List<InptSettleDTO> inptSettleDTOS = patientComprehensiveQueryDAO.querySettleInfo(inptSettleDTO);
    return PageDTO.of(inptSettleDTOS);
  }

  /**
   * @Description: 查询所有住院病人
   * @Param:
   * @Author: guanhongqiang
   * @Email: hongqiang.guan@powersi.com.cn
   * @Date 2021/4/23 16:15
   * @Return
   */
  @Override
  public PageDTO queryAllInptVisitInfo(InptSettleDTO inptSettleDTO) {
    PageHelper.startPage(inptSettleDTO.getPageNo(), inptSettleDTO.getPageSize());
   // List<InptSettleDTO> inptSettleDTOS = patientComprehensiveQueryDAO.queryAllInptVisitInfo(inptSettleDTO);
    //不查询所有，只查询在院病人
    List<InptSettleDTO> inptSettleDTOS = patientComprehensiveQueryDAO.queryInptVisitInfo(inptSettleDTO);
    return PageDTO.of(inptSettleDTOS);
  }

  /**
   * @Method queryBedChangeInfo
   * @Desrciption  查询床位异动信息
   * @Param
   * map [就诊号]
   * @Author zhangxuan
   * @Date   2020-10-15 14:03
   * @Return map
   **/
  @Override
  public PageDTO queryBedChangeInfo(InptBedChangeInfoDTO inptBedChangeInfoDTO) {
    PageHelper.startPage(inptBedChangeInfoDTO.getPageNo(),inptBedChangeInfoDTO.getPageSize());
    List<InptBedChangeInfoDTO> inptBedChangeInfoDTOS = patientComprehensiveQueryDAO.queryBedChangeInfo(inptBedChangeInfoDTO);
    return PageDTO.of(inptBedChangeInfoDTOS);
  }
  /**
   * @Method querySettleInvoice
   * @Desrciption  结算票据信息
   * @Param
   * map [就诊号]
   * @Author zhangxuan
   * @Date   2020-10-15 14:25
   * @Return
   **/
  @Override
  public PageDTO querySettleInvoice(InptSettleDTO inptSettleDTO) {
    PageHelper.startPage(inptSettleDTO.getPageNo(),inptSettleDTO.getPageSize());
    List<InptSettleDTO> inptSettleDTOS = patientComprehensiveQueryDAO.querySettleInvoice(inptSettleDTO);
    return PageDTO.of(inptSettleDTOS);
  }


  /**
  * @Menthod queryItemAndDrugAndMaterialAndAdvice
  * @Desrciption
   * @param patientCompreHensiveQueryDTO
  * @Author xingyu.xie
  * @Date   2020/10/14 11:17
  * @Return cn.hsa.base.PageDTO
  **/
  @Override
  public PageDTO queryItemAndDrugAndMaterialAndAdvice(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO) {
    PageHelper.startPage(patientCompreHensiveQueryDTO.getPageNo(),patientCompreHensiveQueryDTO.getPageSize());
    List<PatientCompreHensiveQueryDTO> patientCompreHensiveQueryDTOS = patientComprehensiveQueryDAO.queryItemAndDrugAndMaterialAndAdvice(patientCompreHensiveQueryDTO);
    return PageDTO.of(patientCompreHensiveQueryDTOS);
  }


  /**
   * @Menthod queryItemAndDrugAndMaterialDetail
   * @Desrciption  根据项目汇总查询其中明细
   * @param patientCompreHensiveQueryDTO 必填:就诊id(visitId), 医院编码（hospCode） 选填:仅当前科室（pharId）,医嘱项目（isAdviceItem=Y）,非医嘱项目（isAdviceItem=N）
   *                                     项目id(item_id),项目类型(item_code)
   * @Author xingyu.xie
   * @Date   2020/12/22 9:22
   * @Return java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>
   **/
  @Override
  public List<PatientCompreHensiveQueryDTO> queryItemAndDrugAndMaterialDetail(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO) {
    return patientComprehensiveQueryDAO.queryItemAndDrugAndMaterialDetail(patientCompreHensiveQueryDTO);

  }

  /**
  * @Menthod queryCostClassify
  * @Desrciption  分页查询计费类别（财务分类）
   * @param patientCompreHensiveQueryDTO
  * @Author xingyu.xie
  * @Date   2020/10/14 13:42
  * @Return cn.hsa.base.PageDTO
  **/
  @Override
  public PageDTO queryCostClassify(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO) {
    PageHelper.startPage(patientCompreHensiveQueryDTO.getPageNo(),patientCompreHensiveQueryDTO.getPageSize());
    List<PatientCompreHensiveQueryDTO> patientCompreHensiveQueryDTOS = patientComprehensiveQueryDAO.queryCostClassify(patientCompreHensiveQueryDTO);
    return PageDTO.of(patientCompreHensiveQueryDTOS);
  }

  /**
  * @Menthod queryCostClassifyDetail
  * @Desrciption  分页查询计费类别明细
   * @param patientCompreHensiveQueryDTO
  * @Author xingyu.xie
  * @Date   2020/10/14 13:42
  * @Return cn.hsa.base.PageDTO
  **/
  @Override
  public PageDTO queryCostClassifyDetail(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO) {
    PageHelper.startPage(patientCompreHensiveQueryDTO.getPageNo(),patientCompreHensiveQueryDTO.getPageSize());
    List<PatientCompreHensiveQueryDTO> patientCompreHensiveQueryDTOS = patientComprehensiveQueryDAO.queryCostClassifyDetail(patientCompreHensiveQueryDTO);
    return PageDTO.of(patientCompreHensiveQueryDTOS);
  }

  /**
  * @Menthod queryCostAll
  * @Desrciption  分页查询费用汇总
   * @param patientCompreHensiveQueryDTO
  * @Author xingyu.xie
  * @Date   2020/10/14 13:42
  * @Return cn.hsa.base.PageDTO
  **/
  @Override
  public PageDTO queryCostAll(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO) {
    PageHelper.startPage(patientCompreHensiveQueryDTO.getPageNo(),patientCompreHensiveQueryDTO.getPageSize());
    List<PatientCompreHensiveQueryDTO> patientCompreHensiveQueryDTOS = patientComprehensiveQueryDAO.queryCostAll(patientCompreHensiveQueryDTO);
    return PageDTO.of(patientCompreHensiveQueryDTOS);
  }

  /**
  * @Menthod queryCostAllDetail
  * @Desrciption  查询费用汇总明细
   * @param patientCompreHensiveQueryDTO
  * @Author xingyu.xie
  * @Date   2020/10/14 13:43
  * @Return java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>
  **/
  @Override
  public List<PatientCompreHensiveQueryDTO> queryCostAllDetail(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO) {
    return patientComprehensiveQueryDAO.queryCostAllDetail(patientCompreHensiveQueryDTO);
  }
  /**
   * @Method queryJS
   * @Desrciption 查询结算信息业务类型
   * @Param
   * [sysUserDTO]
   * @Author yuelong.chen
   * @Date   2021/11/4 11:18
   * @Return
   *
   * @return*/
  @Override
  public List<Map<String, Object>> queryYWLX(Map map) {
    String hospCode = MapUtils.get(map, "hospCode");
    //分割字符串
    String[] code = MapUtils.get(map, "codes").toString().split(",");
    //用来存code值的list
    List<String> codesList = new ArrayList<>();
    if (code.length > 0){
      for (int i = 0; i < code.length; i++) {
        codesList.add(code[i]);
      }
    }
    List<Map<String, Object>> ywlx = patientComprehensiveQueryDAO.queryYWLX(hospCode, codesList);
    return ywlx;
   }


   @Resource
   private BasePreferentialService basePreferentialService_consumer;

   @Resource
   private SysParameterService sysParameterService_consumer;

   @Resource
   private InptVisitDAO inptVisitDAO;

   @Resource
   private InptCostDAO inptCostDAO;

  /**
   * @Menthod: updateVisitPreferential
   * @Desrciption: 修改病人优惠类别
   * @Param: preferentialTypeId：优惠类别id，visitId：就诊id
   * @Author: luoyong
   * @Email: luoyong@powersi.com.cn
   * @Date: 2022-03-21 11:31
   * @Return: boolean
   **/
  @Override
  public Boolean updateVisitPreferential(Map<String, Object> map) {
    String preferentialTypeId = MapUtils.get(map, "preferentialTypeId");
    if (StringUtils.isEmpty(preferentialTypeId)) throw new RuntimeException("未选择优惠类别");
    String visitId = MapUtils.get(map, "visitId");
    if (StringUtils.isEmpty(visitId)) throw new RuntimeException("未选择就诊人");
    String hospCode = MapUtils.get(map, "hospCode");

    InptVisitDTO paramDto = new InptVisitDTO();
    paramDto.setHospCode(hospCode);
    paramDto.setId(visitId);
    InptVisitDTO inptVisitDTO = inptVisitDAO.getInptVisitById(paramDto);
    if (inptVisitDTO == null) throw new RuntimeException("未找到相关就诊人信息");
    if (preferentialTypeId.equals(inptVisitDTO.getPreferentialTypeId())) throw new RuntimeException("该患者已是【" + inptVisitDTO.getPreferentialTypeName() + "】优惠类型了，请选择其他！");

    List<InptCostDO> inptCostDOS = inptCostDAO.queryInptCostList(map);

    map.put("code", "JG_SF_SRFS");
    SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(map).getData();
    if (sysParameterDTO == null || StringUtils.isEmpty(sysParameterDTO.getValue())) {
        throw new RuntimeException("系统参数错误，请联系管理员维护好金额舍入方式！");
    }

    // 获取优惠类别下的所有优惠项目
    BasePreferentialTypeDTO basePreferentialTypeDTO = new BasePreferentialTypeDTO();
    basePreferentialTypeDTO.setId(preferentialTypeId);
    basePreferentialTypeDTO.setHospCode(hospCode);
    map.put("basePreferentialTypeDTO", basePreferentialTypeDTO);
    List<BasePreferentialDTO> basePreferentialDTOS = basePreferentialService_consumer.queryPreferentials(map).getData();

    BigDecimal totalAdvance = inptVisitDTO.getTotalAdvance() == null ? new BigDecimal(0): inptVisitDTO.getTotalAdvance(); // 总预交
    BigDecimal totalcost = inptVisitDTO.getTotalCost(); // 总费用
    BigDecimal totalBalance = inptVisitDTO.getTotalBalance(); // 剩余总金额
    BigDecimal totalPreferentialPrice = new BigDecimal(0); //累计优惠总金额

    if (!ListUtils.isEmpty(basePreferentialDTOS)) {
      // 不为空：根据优惠类型下的优惠项，重新计算金额及总金额
      if (!ListUtils.isEmpty(inptCostDOS)) {
        Map<String, List<BasePreferentialDTO>> listMap = basePreferentialDTOS.stream().collect(Collectors.groupingBy(BasePreferentialDTO::getTypeCode));
        // 财务分类
        List<BasePreferentialDTO> cwflList = listMap.get(Constants.YHLB.CWFL);
        // 药品和项目
        List<BasePreferentialDTO> ypAndXmList = new ArrayList<>();
        List<BasePreferentialDTO> ypList = listMap.get(Constants.YHLB.YP);
        if (!ListUtils.isEmpty(ypList)) ypAndXmList.addAll(ypList);
        List<BasePreferentialDTO> xmList = listMap.get(Constants.YHLB.XM);
        if (!ListUtils.isEmpty(xmList)) ypAndXmList.addAll(xmList);

        // 计算舍入金额，将舍入金额放入最后一个费用中
        BigDecimal roundPrice = new BigDecimal(0);

        if (!ListUtils.isEmpty(cwflList)) {
          for (int i = 0; i < cwflList.size(); i++) {
            BasePreferentialDTO basePreferentialDTO = cwflList.get(i);
            for (int j = 0; j < inptCostDOS.size(); j++) {
              InptCostDO inptCostDO = inptCostDOS.get(j);
              if (basePreferentialDTO.getItemId().equals(inptCostDO.getBfcId())) {
                // 优惠方式
                String inCode = basePreferentialDTO.getInCode();
                // 优惠比列或金额
                BigDecimal inScale = basePreferentialDTO.getInScale();
                if (Constants.YHFS.ABL.equals(inCode)) {
                  // 优惠金额
                  BigDecimal preferentialPrice = inptCostDO.getTotalPrice().multiply(new BigDecimal(1).subtract(inScale.abs()));
                  // 舍入金额
                  roundPrice = BigDecimalUtils.add(roundPrice, BigDecimalUtils.rounding(sysParameterDTO.getValue(), preferentialPrice));
                  // 去掉舍入金额后的优惠金额
                  preferentialPrice = preferentialPrice.subtract(roundPrice);
                  inptCostDO.setPreferentialPrice(preferentialPrice);
                  // 计算累计优惠金额
                  totalPreferentialPrice = totalPreferentialPrice.add(preferentialPrice);
                  // 优惠后金额
                  BigDecimal realityPrice = inptCostDO.getTotalPrice().subtract(preferentialPrice);
                  inptCostDO.setRealityPrice(realityPrice);
                  // 舍入金额存入最后一个修改的费用当中，在进行舍入
                  if (j == (inptCostDOS.size() -1)) {
                    realityPrice = handleRealityPrice(sysParameterDTO.getValue(), inptCostDO.getRealityPrice(), roundPrice);
                    inptCostDO.setRealityPrice(realityPrice);
                  }
                } else if (Constants.YHFS.AJE.equals(inCode)){
                  // 优惠金额
                  inptCostDO.setPreferentialPrice(inScale.abs());
                  // 计算累计的优惠金额
                  totalPreferentialPrice = totalPreferentialPrice.add(inScale.abs());
                  // 优惠后金额
                  inptCostDO.setRealityPrice(inptCostDO.getTotalPrice().subtract(inScale.abs()));
                }
              }
            }
          }
        }
        if (!ListUtils.isEmpty(ypAndXmList)) {
          for (int i = 0; i < ypAndXmList.size(); i++) {
            BasePreferentialDTO basePreferentialDTO = ypAndXmList.get(i);
            for (int j = 0; j < inptCostDOS.size(); j++) {
              InptCostDO inptCostDO = inptCostDOS.get(j);
              if (basePreferentialDTO.getItemId().equals(inptCostDO.getItemId())) {
                // 优惠方式
                String inCode = basePreferentialDTO.getInCode();
                // 优惠比列或金额
                BigDecimal inScale = basePreferentialDTO.getInScale();
                if (Constants.YHFS.ABL.equals(inCode)) {
                  // 优惠金额
                  BigDecimal preferentialPrice = inptCostDO.getTotalPrice().multiply(new BigDecimal(1).subtract(inScale.abs()));
                  // 舍入金额
                  roundPrice = BigDecimalUtils.add(roundPrice, BigDecimalUtils.rounding(sysParameterDTO.getValue(), preferentialPrice));
                  // 去掉舍入金额后的优惠金额
                  preferentialPrice = preferentialPrice.subtract(roundPrice);
                  inptCostDO.setPreferentialPrice(preferentialPrice);
                  // 计算累计优惠金额
                  totalPreferentialPrice = totalPreferentialPrice.add(preferentialPrice);
                  // 优惠后金额
                  BigDecimal realityPrice = inptCostDO.getTotalPrice().subtract(preferentialPrice);
                  inptCostDO.setRealityPrice(realityPrice);
                  // 舍入金额存入最后一个修改的费用当中，在进行舍入
                  if (j == (inptCostDOS.size() -1)) {
                    realityPrice = handleRealityPrice(sysParameterDTO.getValue(), inptCostDO.getRealityPrice(), roundPrice);
                    inptCostDO.setRealityPrice(realityPrice);
                  }
                } else if (Constants.YHFS.AJE.equals(inCode)){
                  // 优惠金额
                  inptCostDO.setPreferentialPrice(inScale.abs());
                  // 计算累计的优惠金额
                  totalPreferentialPrice = totalPreferentialPrice.add(inScale.abs());
                  // 优惠后金额
                  inptCostDO.setRealityPrice(inptCostDO.getTotalPrice().subtract(inScale.abs()));
                }

              }
            }
          }
        }
      }
      // 总费用 = 原总费用 - 总优惠金额
      totalcost = totalcost.subtract(totalPreferentialPrice);
      // 总余额 = 总预交 - 总费用
      totalBalance = totalAdvance.subtract(totalcost);
    }

    // 1.修改费用表中费用
    if (!ListUtils.isEmpty(basePreferentialDTOS)) {
      patientComprehensiveQueryDAO.updateInptCost(inptCostDOS);
    }

    // 2.修改就诊表中的优惠类别id、累计费用、累计余额
    map.put("totalcost", totalcost);
    map.put("totalBalance", totalBalance);
    patientComprehensiveQueryDAO.updateInptVisitPreferential(map);

    return true;
  }

  /**
   * 根据'JG_SF_SRFS'的舍入方式，计算金额
   * @param value 舍入方式
   * @param realityPrice 优惠有金额
   * @param roundPrice 舍入金额
   * @return
   */
  private BigDecimal handleRealityPrice(String value, BigDecimal realityPrice, BigDecimal roundPrice) {
    switch (value) {
        case "01":
          //按角4舍5入：1.15块=1.2块
          realityPrice = realityPrice.add(roundPrice).setScale(1,BigDecimal.ROUND_HALF_UP);//四舍五入，保留一位小数；获取到舍入后的总费用
          break;
        case "02":
          //按角舍 ：1.15块=1.1块
          realityPrice = realityPrice.add(roundPrice).setScale(1,BigDecimal.ROUND_DOWN);//保留一位小数，舍去分及后几位数
          break;
        case "03":
          //按角入：1.15块=1.2块
          realityPrice = realityPrice.add(roundPrice).setScale(1,BigDecimal.ROUND_UP);//保留一位小数，入角
          break;
        case "11":
          //按元4舍5入:1.15块=1块
          realityPrice = realityPrice.add(roundPrice).setScale(0,BigDecimal.ROUND_HALF_UP);
          break;
        case "12":
          //按元舍：1.5块=1块
          realityPrice = realityPrice.add(roundPrice).setScale(0,BigDecimal.ROUND_DOWN);
          break;
        case "13":
          //按元入：1.5块=2块
          realityPrice = realityPrice.add(roundPrice).setScale(0,BigDecimal.ROUND_UP);
    }
    return realityPrice;
  }
}
