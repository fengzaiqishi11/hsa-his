package cn.hsa.module.sync.syncemrelement.dao;


import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.sync.syncemrelement.dto.SyncEmrElementDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrclassifyelement.dao
 * @Class_name: EmrElementDO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/17 16:50
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface SyncEmrElementDAO {

  /**
  * @Menthod getById
  * @Desrciption 根据主键id或者编码code查询单个电子病历元素
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 16:51
  * @Return cn.hsa.module.emr.emrclassifyelement.dto.EmrElementDTO
  **/
  SyncEmrElementDTO getByIdorCode(SyncEmrElementDTO emrElementDTO);

  /**
  * @Menthod queryPageorAll
  * @Desrciption 查询电子病历元素
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 16:52
  * @Return java.util.List<cn.hsa.module.emr.emrclassifyelement.dto.EmrElementDTO>
  **/
  List<SyncEmrElementDTO> queryPageorAll(SyncEmrElementDTO emrElementDTO);

  /**
  * @Menthod queryElementCodes
  * @Desrciption 根据条件查询元素编码列表
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/9 11:01
  * @Return java.util.List<java.lang.String>
  **/
  List<String> queryElementCodes(Map map);

  /**
  * @Menthod insert
  * @Desrciption 新增电子病历元素
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 16:52
  * @Return int
  **/
  int insert(SyncEmrElementDTO emrElementDTO);

  /**
  * @Menthod getMaxCode
  * @Desrciption 查找出同一父节点下最大的code
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/23 14:05
  * @Return cn.hsa.module.emr.emrelement.dto.EmrElementDTO
  **/
  SyncEmrElementDTO getMaxCode(Map map);

  /**
  * @Menthod addColumn
  * @Desrciption 将新增的元素作为字段扩充到emr_patient表中
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/24 9:40
  * @Return void
  **/
  void addColumn(Map map);

  /**
  * @Menthod update
  * @Desrciption 修改电子病历元素
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 16:52
  * @Return int
  **/
  int update(SyncEmrElementDTO emrElementDTO);

  /**
  * @Menthod updateList
  * @Desrciption 批量修改元素状态
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/21 15:15
  * @Return int
  **/
  int updateList(SyncEmrElementDTO emrElementDTO);

  /**
  * @Menthod getEmrElementTree
  * @Desrciption 获取电子病历元素树
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/19 14:18
  * @Return java.util.List<cn.hsa.base.TreeMenuNode>
  **/
  List<TreeMenuNode> getEmrElementTree(SyncEmrElementDTO emrElementDTO);


  /**
  * @Menthod getEmrElementTreeByCodes
  * @Desrciption  获取电子病历元素树,并通过code筛选
   * @param emrElementDTO
  * @Author xingyu.xie
  * @Date   2020/9/27 16:29
  * @Return java.util.List<cn.hsa.base.TreeMenuNode>
  **/
  List<TreeMenuNode> getEmrElementTreeByCodes(SyncEmrElementDTO emrElementDTO);

  /**
  * @Menthod queryCodeIsExist
  * @Desrciption 查询电子病历元素编码是否已存在
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 16:53
  * @Return int
  **/
  int queryCodeIsExist(SyncEmrElementDTO emrElementDTO);

  /**
   * @Description: 查询当前数据库指定表格的列数
   * @Param:
   * @Author: guanhongqiang
   * @Email: hongqiang.guan@powersi.com.cn
   * @Date 2020/11/7 10:13
   * @Return
   */
  int getEmrPatientRecordColumns(@Param("tableName") String tableName);
}
