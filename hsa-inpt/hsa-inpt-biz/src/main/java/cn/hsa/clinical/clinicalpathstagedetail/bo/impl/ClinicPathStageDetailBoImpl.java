package cn.hsa.clinical.clinicalpathstagedetail.bo.impl;

import cn.hsa.base.PageDO;
import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.clinical.clinicalpathstagedetail.bo.ClinicPathStageDetailBO;
import cn.hsa.module.clinical.clinicalpathstagedetail.dao.ClinicPathStageDetailDAO;
import cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Package_name: cn.hsa.clinical.clinicalpathstagedetail.bo.impl
 * @Class_name: ClinicPathStageDetailBoImpl
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/10 10:00
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class ClinicPathStageDetailBoImpl extends HsafBO implements ClinicPathStageDetailBO {

  @Resource
  private ClinicPathStageDetailDAO clinicPathStageDetailDAOl;

  @Resource
  private BaseOrderRuleService baseOrderRuleService;

  /**
  * @Menthod getClinicPathStageDetailById
  * @Desrciption 查询单个路径明细
  *
  * @Param
  * [clinicPathStageDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 9:40
  * @Return cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO
  **/
  @Override
  public ClinicPathStageDetailDTO getClinicPathStageDetailById(ClinicPathStageDetailDTO clinicPathStageDetailDTO) {
    if("1".equals(clinicPathStageDetailDTO.getFlag())) {
      // 查询最大序号
      return clinicPathStageDetailDAOl.getMaxSortNo(clinicPathStageDetailDTO);
    }
    return clinicPathStageDetailDAOl.getClinicPathStageDetailById(clinicPathStageDetailDTO);
  }

  /**
  * @Menthod queryAllClinicPathStageDetail
  * @Desrciption 查询所有路径明细
  *
  * @Param
  * [clinicPathStageDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 9:41
  * @Return java.util.List<cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO>
  **/
  @Override
  public List<ClinicPathStageDetailDTO> queryAllClinicPathStageDetail(ClinicPathStageDetailDTO clinicPathStageDetailDTO) {
    return clinicPathStageDetailDAOl.queryAllClinicPathStageDetail(clinicPathStageDetailDTO);
  }

  /**
  * @Menthod queryClinicPathStageDetailPage
  * @Desrciption 分页查询路径阶段明细
  *
  * @Param
  * [clinicPathStageDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 9:41
  * @Return cn.hsa.base.PageDTO
  **/
  @Override
  public PageDTO queryClinicPathStageDetailPage(ClinicPathStageDetailDTO clinicPathStageDetailDTO) {
    PageHelper.startPage(clinicPathStageDetailDTO.getPageNo(),clinicPathStageDetailDTO.getPageSize());
    List<ClinicPathStageDetailDTO> clinicPathStageDetailDTOS = clinicPathStageDetailDAOl.queryAllClinicPathStageDetail(clinicPathStageDetailDTO);
    return PageDTO.of(clinicPathStageDetailDTOS);
  }

  /**
  * @Menthod insertClinicPathStageDetail
  * @Desrciption 新增路径阶段明细
  *
  * @Param
  * [clinicPathStageDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 9:42
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean insertClinicPathStageDetail(ClinicPathStageDetailDTO clinicPathStageDetailDTO) {
    if(StringUtils.isEmpty(clinicPathStageDetailDTO.getListId())) {
      throw  new AppException("临床路径目录未绑定");
    }
    if(StringUtils.isEmpty(clinicPathStageDetailDTO.getStageId())) {
      throw  new AppException("临床路径阶段未绑定");
    }
    clinicPathStageDetailDTO.setId(SnowflakeUtils.getId());
    HashMap map = new HashMap();
    map.put("hospCode",clinicPathStageDetailDTO.getHospCode());
    map.put("typeCode", Constants.ORDERRULE.LCJDMX);
    WrapperResponse<String> orderNo = baseOrderRuleService.getOrderNo(map);
    if(StringUtils.isEmpty(orderNo.getData())) {
      throw new AppException("系统没有临床路径明细单号规则");
    }
    // 新增赋值编码
    clinicPathStageDetailDTO.setCode(orderNo.getData());
    return clinicPathStageDetailDAOl.insertClinicPathStageDetail(clinicPathStageDetailDTO) > 0;

  }

  /**
  * @Menthod updateClinicPathStageDetail
  * @Desrciption 修改路径阶段明细
  *
  * @Param
  * [clinicPathStageDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 9:42
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean updateClinicPathStageDetail(ClinicPathStageDetailDTO clinicPathStageDetailDTO) {
    return clinicPathStageDetailDAOl.updateClinicPathStageDetail(clinicPathStageDetailDTO) > 0;

  }

  /**
  * @Menthod deleteClinicPathStageDetailById
  * @Desrciption  删除路径阶段明细
  *
  * @Param
  * [clinicPathStageDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 9:42
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean deleteClinicPathStageDetailById(ClinicPathStageDetailDTO clinicPathStageDetailDTO) {
    return clinicPathStageDetailDAOl.deleteClinicPathStageDetailById(clinicPathStageDetailDTO) > 0;

  }

  /**
  * @Menthod queryClinicalPathTree
  * @Desrciption 查询路径阶段树
  *
  * @Param
  * [clinicPathStageDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 15:49
  * @Return java.util.List<cn.hsa.base.TreeMenuNode>
  **/
  @Override
  public List<TreeMenuNode> queryClinicalPathTree(ClinicPathStageDetailDTO clinicPathStageDetailDTO) {
    TreeMenuNode parent = new TreeMenuNode();
    parent.setLabel("临床路径目录");
    parent.setId("-1");
    parent.setParentId("-2");
    List<TreeMenuNode> treeMenuNodeList = clinicPathStageDetailDAOl.queryClinicalPathTree(clinicPathStageDetailDTO);
    treeMenuNodeList.add(parent);
    return treeMenuNodeList;
  }
}
