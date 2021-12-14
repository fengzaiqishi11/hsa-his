package cn.hsa.inpt.patientcomprehensivequery.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDetailDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.fees.dto.InptSettleDTO;
import cn.hsa.module.inpt.patientcomprehensivequery.bo.PatientComprehensiveQueryBO;
import cn.hsa.module.inpt.patientcomprehensivequery.dao.PatientComprehensiveQueryDAO;
import cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO;
import cn.hsa.module.mris.mrisHome.dto.InptBedChangeInfoDTO;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
}
