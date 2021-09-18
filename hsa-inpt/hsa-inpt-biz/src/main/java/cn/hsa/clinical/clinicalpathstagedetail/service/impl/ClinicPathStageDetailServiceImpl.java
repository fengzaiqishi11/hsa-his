package cn.hsa.clinical.clinicalpathstagedetail.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicalpathstagedetail.bo.ClinicPathStageDetailBO;
import cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO;
import cn.hsa.module.clinical.clinicalpathstagedetail.service.ClinicPathStageDetailService;
import cn.hsa.util.MapUtils;
import cn.hsa.util.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.clinical.clinicalpathstagedetail.service.impl
 * @Class_name: ClinicPathStageDetailServiceImpl
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/10 9:29
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/inpt/clinicPathStageDetailService")
@Slf4j
@Service("clinicPathStageDetailService_provider")
public class ClinicPathStageDetailServiceImpl extends HsafService implements ClinicPathStageDetailService {

  @Resource
  private ClinicPathStageDetailBO clinicPathStageDetailBO;
  /**
  * @Menthod getClinicPathStageDetailById
  * @Desrciption  查询单个路径阶段明细
  *
  * @Param
  * [clinicPathStageDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/10 9:37
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO>
  **/
  @Override
  public WrapperResponse<ClinicPathStageDetailDTO> getClinicPathStageDetailById(Map map) {
    ClinicPathStageDetailDTO clinicPathStageDetailDTO = MapUtils.get(map,"clinicPathStageDetailDTO");
    return WrapperResponse.success(clinicPathStageDetailBO.getClinicPathStageDetailById(clinicPathStageDetailDTO));
  }

  /**
  * @Menthod queryClinicPathStageDetailAll
  * @Desrciption 查询所有路径阶段明细
  *
  * @Param
  * [clinicPathStageDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/10 9:39
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO>>
  **/
  @Override
  public WrapperResponse<List<ClinicPathStageDetailDTO>> queryAllClinicPathStageDetail(Map map) {
    ClinicPathStageDetailDTO clinicPathStageDetailDTO = MapUtils.get(map,"clinicPathStageDetailDTO");
    return WrapperResponse.success(clinicPathStageDetailBO.queryAllClinicPathStageDetail(clinicPathStageDetailDTO));
  }

  /**
  * @Menthod queryClinicPathStageDetailPage
  * @Desrciption 分页查询路径阶段明细
  *
  * @Param
  * [clinicPathStageDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/10 9:39
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @Override
  public WrapperResponse<PageDTO> queryClinicPathStageDetailPage(Map map) {
    ClinicPathStageDetailDTO clinicPathStageDetailDTO = MapUtils.get(map,"clinicPathStageDetailDTO");
    return WrapperResponse.success(clinicPathStageDetailBO.queryClinicPathStageDetailPage(clinicPathStageDetailDTO));
  }

  /**
  * @Menthod insertClinicPathStageDetail
  * @Desrciption 新增路径阶段明细
  *
  * @Param
  * [clinicPathStageDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/10 9:39
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> insertClinicPathStageDetail(Map map) {
    ClinicPathStageDetailDTO clinicPathStageDetailDTO = MapUtils.get(map,"clinicPathStageDetailDTO");
    return WrapperResponse.success(clinicPathStageDetailBO.insertClinicPathStageDetail(clinicPathStageDetailDTO));
  }

  /**
  * @Menthod updateClinicPathStageDetail
  * @Desrciption 修改路径阶段明细
  *
  * @Param
  * [clinicPathStageDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/10 9:39
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> updateClinicPathStageDetail(Map map) {
    ClinicPathStageDetailDTO clinicPathStageDetailDTO = MapUtils.get(map,"clinicPathStageDetailDTO");
    return WrapperResponse.success(clinicPathStageDetailBO.updateClinicPathStageDetail(clinicPathStageDetailDTO));
  }

  /**
  * @Menthod deleteClinicPathStageDetailById
  * @Desrciption 删除路径明细
  *
  * @Param
  * [clinicPathStageDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/10 9:39
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> deleteClinicPathStageDetailById(Map map) {
    ClinicPathStageDetailDTO clinicPathStageDetailDTO = MapUtils.get(map,"clinicPathStageDetailDTO");
    return WrapperResponse.success(clinicPathStageDetailBO.deleteClinicPathStageDetailById(clinicPathStageDetailDTO));
  }

  /**
  * @Menthod queryClinicalPathTree
  * @Desrciption  查询路径阶段树
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 15:47
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
  **/
  @Override
  public WrapperResponse<List<TreeMenuNode>> queryClinicalPathTree(Map map) {
    ClinicPathStageDetailDTO clinicPathStageDetailDTO = MapUtils.get(map,"clinicPathStageDetailDTO");
    List<TreeMenuNode> treeMenuNodeList = clinicPathStageDetailBO.queryClinicalPathTree(clinicPathStageDetailDTO);
    List<TreeMenuNode> treeMenuNodes = TreeUtils.buildByRecursive(treeMenuNodeList, "-2");
    return WrapperResponse.success(treeMenuNodes);
  }
}
