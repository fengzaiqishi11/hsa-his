package cn.hsa.inpt.patientcomprehensivequery.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDetailDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.patientcomprehensivequery.bo.PatientComprehensiveQueryBO;
import cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO;
import cn.hsa.module.inpt.patientcomprehensivequery.service.PatientComprehensiveQueryService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.patientcomprehensivequery.service.impl
 * @Class_name: PatientComprehensiveQueryService
 * @Describe:  病人综合查询业务传输实现层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/10/12 16:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/inpt/patientComprehensiveQuery")
@Slf4j
@Service("patientComprehensiveQuery_provider")
public class PatientComprehensiveQueryServiceImpl extends HsafService implements PatientComprehensiveQueryService {

  @Resource
  private PatientComprehensiveQueryBO patientComprehensiveQueryBO;

  /**
  * @Menthod queryAdviceSummary
  * @Desrciption 病人综合查询---医嘱汇总查询
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/12 11:16
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDTO>>
  **/
  @Override
  public WrapperResponse<PageDTO> queryAdviceSummary(Map<String, Object> map) {
    InptAdviceDTO inptAdviceDTO = MapUtils.get(map,"inptAdviceDTO");
    if("Y".equals(inptAdviceDTO.getPharId())){
      inptAdviceDTO.setDeptId(MapUtils.get(map,"loginDeptId"));
    }
    return WrapperResponse.success(patientComprehensiveQueryBO.queryAdviceSummary(inptAdviceDTO));
  }
    /**
     * @Method queryPatientDiagnosePage()
     * @Desrciption 病人诊断信息查询
     * @Param InptVisitDTO 住院就诊病人DTO数据传输对象
     * @Author fuhui
     * @Date 2020/10/12 15:55
     * @Return 病人诊断分页信息
     **/
    @Override
    @HsafRestPath(value = "/queryPatientDiagnosePage", method = RequestMethod.GET)
    public WrapperResponse<PageDTO> queryPatientDiagnosePage(Map map) {
        InptDiagnoseDTO inptDiagnoseDTO = MapUtils.get(map, "inptDiagnoseDTO");
        return WrapperResponse.success(patientComprehensiveQueryBO.queryPatientDiagnosePage(inptDiagnoseDTO));
    }

    /**
     * @Method queryAdvice()
     * @Desrciption 查询临时医嘱和长期医嘱的信息
     * @Param inptVisitDTO：visitId：就诊id isLong：是否长期医嘱
     * @Author fuhui
     * @Date 2020/10/12 17:20
     * @Return 医嘱信息
     **/
    @Override
    public WrapperResponse<List<InptAdviceDTO>> queryAdvice(Map map) {
        InptAdviceDTO inptAdviceDTO = MapUtils.get(map, "inptAdviceDTO");
        return WrapperResponse.success(patientComprehensiveQueryBO.queryAdvice(inptAdviceDTO));
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
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDetailDTO>>
  **/
  @Override
  public WrapperResponse<PageDTO> queryAdviceSummaryDetail(Map<String, Object> map) {
    InptAdviceDetailDTO inptAdviceDetailDTO = MapUtils.get(map,"inptAdviceDetailDTO");
    return WrapperResponse.success(patientComprehensiveQueryBO.queryAdviceSummaryDetail(inptAdviceDetailDTO));
  }

  /**
  * @Menthod queryAdviceLongOrShort
  * @Desrciption 病人长期/短期医嘱查询
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/12 11:16
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDTO>>
  **/
  @Override
  public WrapperResponse<List<InptAdviceDTO>> queryAdviceLongOrShort(Map<String, Object> map) {
    InptAdviceDTO inptAdviceDTO = MapUtils.get(map,"inptAdviceDTO");
    return WrapperResponse.success(patientComprehensiveQueryBO.queryAdviceLongOrShort(inptAdviceDTO));
  }

  /**
   * @Method querySettleInfo
   * @Desrciption  查询结算详细信息
   * @Param
   * map [ 就诊号，当前科室，医院编码 ]
   * @Author zhangxuan
   * @Date   2020-10-14 14:56
   * @Return pageDTO
   **/
  @Override
  public WrapperResponse<PageDTO> querySettleInfo(Map map) {
    PageDTO pageDTO = patientComprehensiveQueryBO.querySettleInfo(MapUtils.get(map,"inptSettleDTO"));
    return WrapperResponse.success(pageDTO);
  }

  /**
   * @Description: 查询所有住院病人
   * @Param:
   * @Author: guanhongqiang
   * @Email: hongqiang.guan@powersi.com.cn
   * @Date 2021/4/23 16:14
   * @Return
   */
  @Override
  public WrapperResponse<PageDTO> queryAllInptVisitInfo(Map map) {
    PageDTO pageDTO = patientComprehensiveQueryBO.queryAllInptVisitInfo(MapUtils.get(map,"inptSettleDTO"));
    return WrapperResponse.success(pageDTO);
  }

  /**
   * @Method queryBedChangeInfo
   * @Desrciption  查询床位异动信息
   * @Param
   * map [就诊号]
   * @Author zhangxuan
   * @Date   2020-10-15 14:03
   * @Return pageDTO
   **/
  @Override
  public WrapperResponse<PageDTO> queryBedChangeInfo(Map map) {
    PageDTO pageDTO = patientComprehensiveQueryBO.queryBedChangeInfo(MapUtils.get(map,"inptBedChangeInfoDTO"));
    return WrapperResponse.success(pageDTO);
  }
  /**
   * @Method querySettleInvoice
   * @Desrciption  结算票据信息
   * @Param
   * map [就诊号]
   * @Author zhangxuan
   * @Date   2020-10-15 14:25
   * @Return pageDTO
   **/
  @Override
  public WrapperResponse<PageDTO> querySettleInvoice(Map map) {
    PageDTO pageDTO = patientComprehensiveQueryBO.querySettleInvoice(MapUtils.get(map,"inptSettleDTO"));
    return WrapperResponse.success(pageDTO);
  }

  /**
  * @Menthod queryItemAndDrugAndMaterialAndAdvice
  * @Desrciption  分页查询项目汇总
   * @param map
  * @Author xingyu.xie
  * @Date   2020/10/14 13:43
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @Override
  public WrapperResponse<PageDTO> queryItemAndDrugAndMaterialAndAdvice(Map map) {
    return WrapperResponse.success(patientComprehensiveQueryBO.queryItemAndDrugAndMaterialAndAdvice(MapUtils.get(map,"patientCompreHensiveQueryDTO")));
  }

  /**
   * @Menthod queryItemAndDrugAndMaterialDetail
   * @Desrciption  根据项目汇总查询其中明细
   * @param map 必填:就诊id(visitId), 医院编码（hospCode） 选填:仅当前科室（pharId）,医嘱项目（isAdviceItem=Y）,非医嘱项目（isAdviceItem=N）
   *                                     项目id(item_id),项目类型(item_code)
   * @Author xingyu.xie
   * @Date   2020/12/22 9:22
   * @Return java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>
   **/
  @Override
  public WrapperResponse<List<PatientCompreHensiveQueryDTO>> queryItemAndDrugAndMaterialDetail(Map map) {
    return WrapperResponse.success(patientComprehensiveQueryBO.queryItemAndDrugAndMaterialDetail(MapUtils.get(map,"patientCompreHensiveQueryDTO")));
  }

  /**
  * @Menthod queryCostClassify
  * @Desrciption  分页查询计费类别
   * @param map
  * @Author xingyu.xie
  * @Date   2020/10/14 13:43
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @Override
  public WrapperResponse<PageDTO> queryCostClassify(Map map) {
    return WrapperResponse.success(patientComprehensiveQueryBO.queryCostClassify(MapUtils.get(map,"patientCompreHensiveQueryDTO")));
  }

  /**
  * @Menthod queryCostClassifyDetail
  * @Desrciption  分页查询计费类别明细
   * @param map
  * @Author xingyu.xie
  * @Date   2020/10/14 13:44
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @Override
  public WrapperResponse<PageDTO> queryCostClassifyDetail(Map map) {
    return WrapperResponse.success(patientComprehensiveQueryBO.queryCostClassifyDetail(MapUtils.get(map,"patientCompreHensiveQueryDTO")));
  }

  /**
  * @Menthod queryCostAll
  * @Desrciption 分页查询费用汇总
   * @param map
  * @Author xingyu.xie
  * @Date   2020/10/14 13:44
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @Override
  public WrapperResponse<PageDTO> queryCostAll(Map map) {
    return WrapperResponse.success(patientComprehensiveQueryBO.queryCostAll(MapUtils.get(map,"patientCompreHensiveQueryDTO")));
  }

  /**
  * @Menthod queryCostAllDetail
  * @Desrciption  查询费用汇总明细
   * @param map
  * @Author xingyu.xie
  * @Date   2020/10/14 13:44
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>>
  **/
  @Override
  public WrapperResponse<List<PatientCompreHensiveQueryDTO>> queryCostAllDetail(Map map) {
    return WrapperResponse.success(patientComprehensiveQueryBO.queryCostAllDetail(MapUtils.get(map,"patientCompreHensiveQueryDTO")));
  }

    /**
     * @Method queryCostDetail
     * @Desrciption 费用详情查询
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/10/23 8:46
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
  @Override
  public WrapperResponse<PageDTO> queryCostDetail(Map map) {
    return WrapperResponse.success(patientComprehensiveQueryBO.queryCostDetail(MapUtils.get(map,"patientCompreHensiveQueryDTO")));
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
  public WrapperResponse<List<Map<String, Object>>> queryYWLX(Map map) {
    return WrapperResponse.success(patientComprehensiveQueryBO.queryYWLX(map));
  }

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
  public WrapperResponse<Boolean> updateVisitPreferential(Map<String, Object> map) {
    return WrapperResponse.success(patientComprehensiveQueryBO.updateVisitPreferential(map));
  }
}
