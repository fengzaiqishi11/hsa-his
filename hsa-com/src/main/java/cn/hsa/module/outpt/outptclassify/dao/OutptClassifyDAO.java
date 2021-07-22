package cn.hsa.module.outpt.outptclassify.dao;

import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyCostDTO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.outpt.outptclassify.dao
 * @Class_name: OutptClassifyDAO
 * @Describe:  挂号类别设置数据接口
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/8/10 15:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface OutptClassifyDAO {

  /**
  * @Menthod getByID()
  * @Desrciption 根据id获取挂号类别设置
  *
  * @Param
  * [id]
  *
  * @Author jiahong.yang
  * @Date   2020/8/10 16:36
  * @Return cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO
  **/
  OutptClassifyDTO getById(OutptClassifyDTO outptClassifyDTO);

  /**
  * @Menthod queryAllandPage
  * @Desrciption 查询挂号类别
  *
  * @Param
  * [1. outptClassify]
  *
  * @Author jiahong.yang
  * @Date   2020/8/10 16:36
  * @Return java.util.List<cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO>
  **/
  List<OutptClassifyDTO> queryAllandPage(OutptClassifyDTO outptClassifyDTO);

  /**
  * @Menthod queryNameIsExist
  * @Desrciption 查询该挂号类别是否已存在
  *
  * @Param
  * [outptClassifyDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/24 16:42
  * @Return int
  **/
  int queryNameIsExist(OutptClassifyDTO outptClassifyDTO);

  /**
  * @Menthod insert()
  * @Desrciption 新增挂号类别
  *
  * @Param
  * [1. outptClassifyDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/10 16:37
  * @Return int
  **/
  int insert(OutptClassifyDTO outptClassifyDTO);

  /**
  * @Menthod update()
  * @Desrciption 更新挂号类别
  *
  * @Param
  * [1. outptClassifyDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/10 16:38
  * @Return int
  **/
  int update(OutptClassifyDTO outptClassifyDTO);

  /**
  * @Menthod deleteById
  * @Desrciption  根据id删除挂号别设置
  *
  * @Param
  * [1. id]
  *
  * @Author jiahong.yang
  * @Date   2020/8/10 16:38
  * @Return int
  **/
  int deleteById(OutptClassifyDTO outptClassifyDTO);

  /**
  * @Menthod queryAll
  * @Desrciption 查询挂号类别费用
  *
  * @Param
  * [1. outptClassifyCostDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/11 13:56
  * @Return java.util.List<OutptClassifyCost>
  **/
  List<OutptClassifyCostDTO> queryOutptCostAll(OutptClassifyCostDTO outptClassifyCostDTO);

  /**
  * @Menthod insertOutptCost()
  * @Desrciption 增加挂号类别费用
  *
  * @Param
  * [outptClassifyCostDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/11 13:57
  * @Return int
  **/
  int insertOutptCost(@Param("list") List<OutptClassifyCostDTO> outptClassifyCostDTO);

  /**
  * @Menthod updateOutptCost()
  * @Desrciption 修改挂号类别费用
  *
  * @Param
  * [1. outptClassifyCostDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/11 13:58
  * @Return int
  **/
  int updateOutptCost(@Param("list") List<OutptClassifyCostDTO> outptClassifyCostDTO);

  /**
  * @Menthod deleteOutptCost（）
  * @Desrciption 删除挂号类别费用
  *
  * @Param
  * [outptClassifyCostDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/11 14:00
  * @Return int
  **/
  int deleteOutptCost(OutptClassifyDTO outptClassifyDTO);

  List<OutptClassifyCostDTO> queryClassifyCostList(OutptClassifyCostDTO outptClassifyCostDTO);
}
