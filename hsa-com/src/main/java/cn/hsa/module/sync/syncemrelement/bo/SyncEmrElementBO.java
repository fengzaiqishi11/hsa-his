package cn.hsa.module.sync.syncemrelement.bo;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.sync.syncemrelement.dto.SyncEmrElementDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrclassifyelement.bo
 * @Class_name: EmrElementBO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/17 16:44
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface SyncEmrElementBO {

  /**
  * @Menthod getByIdorCode
  * @Desrciption 根据主键id或者编码code查询单个电子病历元素
  *
  * @Param
  * [SyncEmrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 16:45
  * @Return cn.hsa.module.emr.emrclassifyelement.dto.EmrElementDTO
  **/
  SyncEmrElementDTO getByIdorCode(SyncEmrElementDTO emrElementDTO);

  /**
  * @Menthod queryAll
  * @Desrciption 查询所有电子病历元素
  *
  * @Param
  * [SyncEmrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 16:46
  * @Return java.util.List<cn.hsa.module.emr.emrclassifyelement.dto.EmrElementDTO>
  **/
  List<SyncEmrElementDTO> queryAll(SyncEmrElementDTO emrElementDTO);

  /**
  * @Menthod queryElementCodes
  * @Desrciption 根据条件查询元素编码列表
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/9 10:59
  * @Return java.util.List<java.lang.String>
  **/
  List<String> queryElementCodes(Map map);

  /**
  * @Menthod save
  * @Desrciption 保存电子病历元素
  *
  * @Param
  * [SyncEmrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 16:46
  * @Return boolean
  **/
  boolean save(SyncEmrElementDTO emrElementDTO);

  /**
  * @Menthod getEmrElementTree
  * @Desrciption 获取电子病历元素树
  *
  * @Param
  * [SyncEmrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 16:36
  * @Return java.util.List<cn.hsa.base.TreeMenuNode>
  **/
  List<TreeMenuNode> getEmrElementTree(SyncEmrElementDTO emrElementDTO);
}
