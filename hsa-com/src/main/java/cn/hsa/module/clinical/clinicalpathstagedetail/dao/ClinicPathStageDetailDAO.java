package cn.hsa.module.clinical.clinicalpathstagedetail.dao;

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
public interface ClinicPathStageDetailDAO {

  /**
  * @Menthod getClinicPathStageDetailById
  * @Desrciption 查询单个路径明细
  *
  * @Param
  * [clinicPathStageDetail]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 9:44
  * @Return cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO
  **/
  ClinicPathStageDetailDTO getClinicPathStageDetailById(ClinicPathStageDetailDTO clinicPathStageDetail);

  /**
  * @Menthod getMaxSortNo
  * @Desrciption 查询最大序号
  *
  * @Param
  * [clinicPathStageDetail]
  *
  * @Author jiahong.yang
  * @Date   2021/9/16 16:17
  * @Return cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO
  **/
  ClinicPathStageDetailDTO getMaxSortNo(ClinicPathStageDetailDTO clinicPathStageDetail);


  /**
  * @Menthod queryAllClinicPathStageDetail
  * @Desrciption 查询所有路径明细
  *
  * @Param
  * [clinicPathStageDetail]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 9:44
  * @Return java.util.List<cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO>
  **/
  List<ClinicPathStageDetailDTO> queryAllClinicPathStageDetail(ClinicPathStageDetailDTO clinicPathStageDetail);


  /**
  * @Menthod insertClinicPathStageDetail
  * @Desrciption 新增路径阶段明细
  *
  * @Param
  * [clinicPathStageDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 9:44
  * @Return int
  **/
  int insertClinicPathStageDetail(ClinicPathStageDetailDTO clinicPathStageDetailDTO);


  /**
  * @Menthod updateClinicPathStageDetail
  * @Desrciption 修改路径阶段明细
  *
  * @Param
  * [clinicPathStageDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 9:44
  * @Return int
  **/
  int updateClinicPathStageDetail(ClinicPathStageDetailDTO clinicPathStageDetailDTO);


  /**
  * @Menthod deleteClinicPathStageDetailById
  * @Desrciption 删除路径阶段明细
  *
  * @Param
  * [clinicPathStageDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 9:44
  * @Return int
  **/
  int deleteClinicPathStageDetailById(ClinicPathStageDetailDTO clinicPathStageDetailDTO);

  /**
  * @Menthod queryClinicalPathTree
  * @Desrciption 查询路径阶段树
  *
  * @Param
  * [clinicPathStageDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 15:50
  * @Return java.util.List<cn.hsa.base.TreeMenuNode>
  **/
  List<TreeMenuNode> queryClinicalPathTree(ClinicPathStageDetailDTO clinicPathStageDetailDTO);
}
