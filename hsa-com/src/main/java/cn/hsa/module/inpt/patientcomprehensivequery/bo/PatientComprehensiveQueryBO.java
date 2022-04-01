package cn.hsa.module.inpt.patientcomprehensivequery.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDetailDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.fees.dto.InptSettleDTO;
import cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO;
import cn.hsa.module.mris.mrisHome.dto.InptBedChangeInfoDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.patientcomprehensivequery
 * @Class_name: PatientComprehensiveQueryService
 * @Describe:  病人综合查询业务逻辑接口
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/10/12 10:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface PatientComprehensiveQueryBO {
  /**
   * @Menthod queryAdviceSummary
   * @Desrciption 病人综合查询---医嘱汇总查询
   *
   * @Param
   * [map]
   *
   * @Author jiahong.yang
   * @Date   2020/10/12 11:08
   * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDTO
   **/
  PageDTO queryAdviceSummary(InptAdviceDTO inptAdviceDTO);

  /**
  * @Menthod queryAdviceSummaryDetail
  * @Desrciption 病人综合查询---查询医嘱汇总信息明细
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/13 13:58
  * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDTO>
  **/
  PageDTO queryAdviceSummaryDetail(InptAdviceDetailDTO inptAdviceDetailDTO);

  /**
   * @Menthod queryAdviceLongOrShort
   * @Desrciption 查询短期医嘱或者长期医嘱
   *
   * @Param
   * [map]
   *
   * @Author jiahong.yang
   * @Date   2020/10/12 11:11
   * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDTO>
   **/
  List<InptAdviceDTO> queryAdviceLongOrShort(InptAdviceDTO inptAdviceDTO);


  /**
   * @Menthod queryItemAndDrugAndMaterialAndAdvice
   * @Desrciption  项目汇总查询
   * @param patientCompreHensiveQueryDTO 必填:就诊id(visitId), 医院编码（hospCode） 选填:仅当前科室（pharId）,医嘱项目（isAdviceItem=Y）,非医嘱项目（isAdviceItem=N）
   * @Author xingyu.xie
   * @Date   2020/10/13 13:49
   * @Return java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>
   **/
  PageDTO queryItemAndDrugAndMaterialAndAdvice(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO);

  /**
   * @Menthod queryItemAndDrugAndMaterialAndAdvice
   * @Desrciption  项目汇总查询
   * @param patientCompreHensiveQueryDTO 必填:就诊id(visitId), 医院编码（hospCode） 选填:仅当前科室（pharId）,医嘱项目（isAdviceItem=Y）,非医嘱项目（isAdviceItem=N）
   * @Author xingyu.xie
   * @Date   2020/10/13 13:49
   * @Return java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>
   **/
  List<PatientCompreHensiveQueryDTO> queryItemAndDrugAndMaterialDetail(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO);

  /**
   * @Menthod queryCostClassify
   * @Desrciption  计费类别查询
   * @param patientCompreHensiveQueryDTO 必填:就诊id(visitId), 医院编码（hospCode）
   * @Author xingyu.xie
   * @Date   2020/10/13 13:49
   * @Return java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>
   **/
  PageDTO queryCostClassify(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO);

  /**
   * @Menthod queryCostClassifyDetail
   * @Desrciption  计费类别明细查询
   * @param patientCompreHensiveQueryDTO 必填:就诊id(visitId), 医院编码（hospCode）,财务类别id（原费用类别）（bfcId） 选填:仅当前科室（pharId）,项目名称keyword(keyword)
   * @Author xingyu.xie
   * @Date   2020/10/13 13:49
   * @Return java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>
   **/
  PageDTO queryCostClassifyDetail(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO);

  /**
   * @Menthod queryCostAll
   * @Desrciption  费用汇总查询
   * @param patientCompreHensiveQueryDTO 必填：就诊id（VisitId）
   * @Author xingyu.xie
   * @Date   2020/10/13 13:50
   * @Return java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>
   **/
  PageDTO queryCostAll(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO);

  /**
   * @Menthod queryCostAllDetail
   * @Desrciption  费用汇总明细查询
   * @param patientCompreHensiveQueryDTO  必填：就诊id（VisitId）,财务分类id（原费用类别）（bfcId）,计费时间（costTime）
   * @Author xingyu.xie
   * @Date   2020/10/13 13:50
   * @Return java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>
   **/
  List<PatientCompreHensiveQueryDTO> queryCostAllDetail(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO);
  /**
   * @Method querySettleInfo
   * @Desrciption  查询结算详细信息
   * @Param
   * map [ 就诊号，当前科室，医院编码 ]
   * @Author zhangxuan
   * @Date   2020-10-14 14:56
   * @Return map
   **/
  PageDTO querySettleInfo(InptSettleDTO inptSettleDTO);

  /**
   * @Description: 查询所有住院病人
   * @Param:
   * @Author: guanhongqiang
   * @Email: hongqiang.guan@powersi.com.cn
   * @Date 2021/4/23 16:14
   * @Return
   */
  PageDTO queryAllInptVisitInfo(InptSettleDTO inptSettleDTO);

  /**
   * @Method queryBedChangeInfo
   * @Desrciption  查询床位异动信息
   * @Param
   * map [就诊号]
   * @Author zhangxuan
   * @Date   2020-10-15 14:03
   * @Return map
   **/
  PageDTO queryBedChangeInfo(InptBedChangeInfoDTO inptBedChangeInfoDTO);
  /**
   * @Method querySettleInvoice
   * @Desrciption  结算票据信息
   * @Param
   * map [就诊号]
   * @Author zhangxuan
   * @Date   2020-10-15 14:25
   * @Return
   **/
  PageDTO querySettleInvoice(InptSettleDTO inptSettleDTO);
  /**
   * @Method queryAdvice()
   * @Desrciption 查询临时医嘱和长期医嘱的信息
   * @Param inptVisitDTO：visitId：就诊id isLong：是否长期医嘱
   * @Author fuhui
   * @Date 2020/10/12 17:20
   * @Return 医嘱信息
   **/
  List<InptAdviceDTO> queryAdvice(InptAdviceDTO inptAdviceDTO);
  /**
   * @Method queryPatientDiagnosePage()
   * @Desrciption 病人诊断信息查询
   * @Param InptVisitDTO 住院就诊病人DTO数据传输对象
   * @Author fuhui
   * @Date 2020/10/12 15:55
   * @Return 病人诊断分页信息
   **/
  PageDTO queryPatientDiagnosePage(InptDiagnoseDTO inptDiagnoseDTO);

  /**
   * @Method queryCostDetail
   * @Desrciption 费用详情查询
   * @Param [patientCompreHensiveQueryDTO]
   * @Author liaojunjie
   * @Date 2020/10/23 8:46
   * @Return cn.hsa.base.PageDTO
   **/
    PageDTO queryCostDetail(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO);
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
  List<Map<String, Object>> queryYWLX(Map map);

  /**
   * @Menthod: updateVisitPreferential
   * @Desrciption: 修改病人优惠类别
   * @Param: preferentialTypeId：优惠类别id，visitId：就诊id
   * @Author: luoyong
   * @Email: luoyong@powersi.com.cn
   * @Date: 2022-03-21 11:31
   * @Return: boolean
   **/
    Boolean updateVisitPreferential(Map<String, Object> map);
}
