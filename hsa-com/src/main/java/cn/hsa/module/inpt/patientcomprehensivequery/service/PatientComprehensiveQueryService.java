package cn.hsa.module.inpt.patientcomprehensivequery.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.patientcomprehensivequery
 * @Class_name: PatientComprehensiveQueryService
 * @Describe:  病人综合查询业务传输接口
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/10/12 16:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-inpt")
public interface PatientComprehensiveQueryService {

    /**
     * @Method queryPatientDiagnosePage()
     * @Desrciption  病人诊断信息查询
     * @Param InptVisitDTO 住院就诊病人DTO数据传输对象
     *
     * @Author fuhui
     * @Date   2020/10/12 15:55
     * @Return 病人诊断分页信息
     **/
    @GetMapping("/service/inpt/patientComprehensiveQueryService/queryPatientDiagnosePage")
    WrapperResponse<PageDTO> queryPatientDiagnosePage(Map map);

    /**
     * @Method queryAdvice()
     * @Desrciption  查询临时医嘱和长期医嘱的信息
     * @Param inptVisitDTO：visitId：就诊id isLong：是否长期医嘱
     *
     * @Author fuhui
     * @Date   2020/10/12 17:20
     * @Return 医嘱信息
     **/
    @GetMapping("/service/inpt/patientComprehensiveQueryService/queryAdvice")
    WrapperResponse<List<InptAdviceDTO>> queryAdvice(Map map);

  /**
  * @Menthod queryAdviceSummary
  * @Desrciption 病人综合查询---查询医嘱汇总信息
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/12 10:53
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.inpt.doctor.dto.InptAdviceDTO>>
  **/
  @GetMapping("/service/inpt/patientcomprehensivequery/queryAdviceSummary")
  WrapperResponse<PageDTO> queryAdviceSummary(Map<String, Object> map);

  /**
  * @Menthod queryAdviceSummaryDetail
  * @Desrciption 病人综合查询---查询医嘱汇总信息明细
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/13 13:57
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDetailDTO>>
  **/
  @GetMapping("/service/inpt/patientcomprehensivequery/queryAdviceSummaryDetail")
  WrapperResponse<PageDTO> queryAdviceSummaryDetail(Map<String, Object> map);

  /**
  * @Menthod queryAdviceLongOrShort
  * @Desrciption 病人综合查询---查询长期医嘱或者短期医嘱
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/12 10:53
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List< cn.hsa.module.inpt.doctor.dto.InptAdviceDTO>>
  **/
  @GetMapping("/service/inpt/patientcomprehensivequery/queryAdviceLongOrShort")
  WrapperResponse<List<InptAdviceDTO>> queryAdviceLongOrShort(Map<String, Object> map);
  /**
   * @Method querySettleInfo
   * @Desrciption  查询结算详细信息
   * @Param
   * map [ 就诊号，当前科室，医院编码 ]
   * @Author zhangxuan
   * @Date   2020-10-14 14:56
   * @Return map
  **/
  @GetMapping("/service/inpt/patientcomprehensivequery/querySettleInfo")
  WrapperResponse<PageDTO> querySettleInfo(Map map);

  /**
   * @Description: 查询所有住院病人
   * @Param:
   * @Author: guanhongqiang
   * @Email: hongqiang.guan@powersi.com.cn
   * @Date 2021/4/23 16:13
   * @Return
   */
  @GetMapping("/service/inpt/patientcomprehensivequery/queryAllInptVisitInfo")
  WrapperResponse<PageDTO> queryAllInptVisitInfo(Map map);

  /**
   * @Method queryBedChangeInfo
   * @Desrciption  查询床位异动信息
   * @Param
   * map [就诊号]
   * @Author zhangxuan
   * @Date   2020-10-15 14:03
   * @Return map
   **/
  @GetMapping("/service/inpt/patientcomprehensivequery/queryBedChangeInfo")
  WrapperResponse<PageDTO> queryBedChangeInfo(Map map);
  /**
   * @Method querySettleInvoice
   * @Desrciption  结算票据信息
   * @Param
   * map [就诊号]
   * @Author zhangxuan
   * @Date   2020-10-15 14:25
   * @Return
   **/
  @GetMapping("/service/inpt/patientcomprehensivequery/querySettleInvoice")
  WrapperResponse<PageDTO> querySettleInvoice(Map map);

  /**
   * @Menthod queryItemAndDrugAndMaterialAndAdvice
   * @Desrciption  项目汇总查询
   * @param map 必填:就诊id(visitId), 医院编码（hospCode） 选填:仅当前科室（pharId）,医嘱项目（isAdviceItem=Y）,非医嘱项目（isAdviceItem=N）
   * @Author xingyu.xie
   * @Date   2020/10/13 13:49
   * @Return java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>
   **/
  @GetMapping("/service/inpt/patientcomprehensivequery/queryItemAndDrugAndMaterialAndAdvice")
  WrapperResponse<PageDTO> queryItemAndDrugAndMaterialAndAdvice(Map map);

  /**
   * @Menthod queryItemAndDrugAndMaterialDetail
   * @Desrciption  根据项目汇总查询其中明细
   * @param map 必填:就诊id(visitId), 医院编码（hospCode） 选填:仅当前科室（pharId）,医嘱项目（isAdviceItem=Y）,非医嘱项目（isAdviceItem=N）
   *                                     项目id(item_id),项目类型(item_code)
   * @Author xingyu.xie
   * @Date   2020/12/22 9:22
   * @Return java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>
   **/
  @GetMapping("/service/inpt/patientcomprehensivequery/queryItemAndDrugAndMaterialDetail")
  WrapperResponse<List<PatientCompreHensiveQueryDTO>> queryItemAndDrugAndMaterialDetail(Map map);

  /**
   * @Menthod queryCostClassify
   * @Desrciption  计费类别查询
   * @param map 必填:就诊id(visitId), 医院编码（hospCode）
   * @Author xingyu.xie
   * @Date   2020/10/13 13:49
   * @Return java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>
   **/
  @GetMapping("/service/inpt/patientcomprehensivequery/queryCostClassify")
  WrapperResponse<PageDTO> queryCostClassify(Map map);

  /**
   * @Menthod queryCostClassifyDetail
   * @Desrciption  计费类别明细查询
   * @param map 必填:就诊id(visitId), 医院编码（hospCode）,财务类别id（原费用类别）（bfcId） 选填:仅当前科室（pharId）,项目名称keyword(keyword)
   * @Author xingyu.xie
   * @Date   2020/10/13 13:49
   * @Return java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>
   **/
  @GetMapping("/service/inpt/patientcomprehensivequery/queryCostClassifyDetail")
  WrapperResponse<PageDTO> queryCostClassifyDetail(Map map);

  /**
   * @Menthod queryCostAll
   * @Desrciption  费用汇总查询
   * @param map 必填：就诊id（VisitId）
   * @Author xingyu.xie
   * @Date   2020/10/13 13:50
   * @Return java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>
   **/
  @GetMapping("/service/inpt/patientcomprehensivequery/queryCostAll")
  WrapperResponse<PageDTO> queryCostAll(Map map);

  /**
   * @Menthod queryCostAllDetail
   * @Desrciption  费用汇总明细查询
   * @param map  必填：就诊id（VisitId）,财务分类id（原费用类别）（bfcId）,计费时间（costTime）
   * @Author xingyu.xie
   * @Date   2020/10/13 13:50
   * @Return java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>
   **/
  @GetMapping("/service/inpt/patientcomprehensivequery/queryCostAllDetail")
  WrapperResponse<List<PatientCompreHensiveQueryDTO>> queryCostAllDetail(Map map);

  /**
   * @Method queryCostDetail
   * @Desrciption 费用详情查询
   * @Param
   * [map]
   * @Author liaojunjie
   * @Date   2020/10/22 21:39
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>>
   **/
  @GetMapping("/service/inpt/patientcomprehensivequery/queryCostDetail")
  WrapperResponse<PageDTO> queryCostDetail(Map map);
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
  WrapperResponse<List<Map<String, Object>>> queryYWLX(Map map);
}

