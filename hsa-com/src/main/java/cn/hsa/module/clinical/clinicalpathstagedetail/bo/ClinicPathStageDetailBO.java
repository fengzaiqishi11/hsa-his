package cn.hsa.module.clinical.clinicalpathstagedetail.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.clinical.clinicalpathstagedetail.dao
 * @Class_name: ClinicPathStageDetailDAO1
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/9 19:53
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface ClinicPathStageDetailBO {

  /**
  * @Menthod getClinicPathStageDetailById
  * @Desrciption  查询单个路径阶段明细
  *
  * @Param
  * [clinicPathStageDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 15:48
  * @Return cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO
  **/
  ClinicPathStageDetailDTO getClinicPathStageDetailById(ClinicPathStageDetailDTO clinicPathStageDetailDTO);

  /**
  * @Menthod queryAllClinicPathStageDetail
  * @Desrciption  查询所有路径阶段明细
  *
  * @Param
  * [clinicPathStageDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 15:48
  * @Return java.util.List<cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO>
  **/
  List<ClinicPathStageDetailDTO> queryAllClinicPathStageDetail(ClinicPathStageDetailDTO clinicPathStageDetailDTO);

  /**
  * @Menthod queryClinicPathStageDetailPage
  * @Desrciption  分页查询路径阶段明细
  *
  * @Param
  * [clinicPathStageDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 15:48
  * @Return cn.hsa.base.PageDTO
  **/
  PageDTO queryClinicPathStageDetailPage(ClinicPathStageDetailDTO clinicPathStageDetailDTO);

  /**
  * @Menthod insertClinicPathStageDetail
  * @Desrciption  新增路径阶段明细
  *
  * @Param
  * [clinicPathStageDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 15:48
  * @Return java.lang.Boolean
  **/
  Boolean insertClinicPathStageDetail(ClinicPathStageDetailDTO clinicPathStageDetailDTO);

  /**
  * @Menthod updateClinicPathStageDetail
  * @Desrciption 修改路径阶段明细
  *
  * @Param
  * [clinicPathStageDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 15:48
  * @Return java.lang.Boolean
  **/
  Boolean updateClinicPathStageDetail(ClinicPathStageDetailDTO clinicPathStageDetailDTO);

  /**
  * @Menthod deleteClinicPathStageDetailById
  * @Desrciption 删除路径明细
  *
  * @Param
  * [clinicPathStageDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 15:48
  * @Return java.lang.Boolean
  **/
  Boolean deleteClinicPathStageDetailById(ClinicPathStageDetailDTO clinicPathStageDetailDTO);

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
  List<TreeMenuNode> queryClinicalPathTree(ClinicPathStageDetailDTO clinicPathStageDetailDTO);
}
