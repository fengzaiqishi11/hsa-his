package cn.hsa.module.clinical.clinicalpathstagedetail.service;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.clinical.clinicalpathstagedetail.service
 * @Class_name: ClinicPathStageDetailService1
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/9 19:49
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface ClinicPathStageDetailService {

  /**
  * @Menthod getClinicPathStageDetailById
  * @Desrciption 查询单个路径阶段明细
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 15:44
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO>
  **/
  WrapperResponse<ClinicPathStageDetailDTO> getClinicPathStageDetailById(Map map);

  /**
  * @Menthod queryAllClinicPathStageDetail
  * @Desrciption 查询所有路径阶段明细
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 15:44
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO>>
  **/
  WrapperResponse<List<ClinicPathStageDetailDTO>> queryAllClinicPathStageDetail(Map map);

  /**
  * @Menthod queryClinicPathStageDetailPage
  * @Desrciption  分页查询路径阶段明细
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 15:44
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  WrapperResponse<PageDTO> queryClinicPathStageDetailPage(Map map);

  /**
  * @Menthod insertClinicPathStageDetail
  * @Desrciption 新增路径阶段明细
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 15:44
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  WrapperResponse<Boolean> insertClinicPathStageDetail(Map map);

  /**
  * @Menthod updateClinicPathStageDetail
  * @Desrciption 修改路径阶段明细
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 15:44
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  WrapperResponse<Boolean>  updateClinicPathStageDetail(Map map);

  /**
  * @Menthod deleteClinicPathStageDetailById
  * @Desrciption 删除路径明细
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 15:44
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  WrapperResponse<Boolean> deleteClinicPathStageDetailById(Map map);

  /**
  * @Menthod queryClinicalPathTree
  * @Desrciption 查询路径阶段树
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 15:45
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
  **/
  WrapperResponse<List<TreeMenuNode>> queryClinicalPathTree(Map map);

  /**
  * @Menthod queryClinicalPrint
  * @Desrciption 临床路径打印
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/10/28 9:20
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
  **/
  WrapperResponse<Map> queryClinicalPrint(Map map);

}
