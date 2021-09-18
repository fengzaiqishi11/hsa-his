package cn.hsa.module.stro.stroin.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.stro.stroin.dto.StroInDTO;
import cn.hsa.module.stro.stroin.dto.StroInDetailDTO;
import cn.hsa.module.stro.stroout.dto.StroOutDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.store.instore.bo
 * @Class_name: StroStroInBo
 * @Describe:  药库入口业务逻辑实现层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/22 8:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface StroInBO {

  /**
  * @Menthod getById()
  * @Desrciption 根据主键id获取出入库信息
  *
  * @Param
  * [1.id 主键id
   * 2.hosCode 医院编码
   * ]
  *
  * @Author jiahong.yang
  * @Date   2020/7/23 15:22
  * @Return cn.hsa.module.stro.comm.dto.StroInDTO
  **/
  StroInDTO getById(StroInDTO stroInDTO);

  /**
  * @Menthod queryPage()
  * @Desrciption  按条件查询药品入库信息
  *
  * @Param
  * [1. stroInDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/23 15:23
  * @Return java.util.List<cn.hsa.module.stro.comm.dto.StroInDTO>
  **/
  PageDTO queryStroInPage(StroInDTO stroInDTO);

  /**
  * @Menthod queryAll()
  * @Desrciption 查询全部出入库信息不分页
  *
  * @Param
  * [1. stroInDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/23 15:23
  * @Return java.util.List<cn.hsa.module.stro.comm.dto.StroInDTO>
  **/
  List<StroInDTO> queryAll(StroInDTO stroInDTO);

  /**
  * @Menthod save()
  * @Desrciption 保存增加和编辑入库的数据
  *
  * @Param
  * [stroInDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/25 13:51
  * @Return java.lang.Boolean
  **/
  Boolean save(StroInDTO stroStroInDTO);

  /**
  * @Menthod updateAuditCode()
  * @Desrciption 修改审核状态(审核和作废)
  *
  * @Param
  * [stroStroInDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/27 14:31
  * @Return java.lang.Boolean
  **/
  Boolean updateAuditCode(StroInDTO stroStroInDTO);

  /**
  * @Menthod queryStroInDetailAll()
  * @Desrciption 查询所有入库明细
  *
  * @Param
  * [stroStroInDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/25 16:43
  * @Return java.util.List<cn.hsa.module.stro.stroin.dto.StroInDetailDTO>
  **/
  List<StroInDetailDTO> queryStroInDetailAll(StroInDetailDTO stroStroInDetailDTO);

  /**
  * @Menthod queryDrugPage()
  * @Desrciption 获取全部药品或者材料填充下拉表单
  *
  * @Param
  * [1. baseDrugDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/29 17:35
  * @Return cn.hsa.base.PageDTO
  **/
  PageDTO queryDrugorMaterialPage(Map map);

  /**
  * @Menthod queryMaterialPage()
  * @Desrciption  获取全部材料填充下拉表单
  *
  * @Param
  * [1. materialDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/29 17:38
  * @Return cn.hsa.base.PageDTO
  **/
  PageDTO queryMaterialPage(BaseMaterialDTO baseMaterialDTO);

  /**
   * @Method insertBySameLevel
   * @Desrciption 出库同级调拨的时候往入库插入记录
   * @Param
   * [stroOutDTO]
   * @Author liaojunjie
   * @Date   2020/11/2 14:32
   * @Return java.lang.Boolean
   **/
  Boolean insertBySameLevel(List<StroOutDTO>stroOutDTOS);

  /**
  * @Menthod queryWholeOut
  * @Desrciption 整单出库查询
  *
  * @Param
  * [stroInDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/3/8 11:15
  * @Return cn.hsa.module.stro.stroin.dto.StroInDTO
  **/
  StroInDTO queryWholeSuppOut(StroInDTO stroInDTO);

  /**
  * @Menthod insertWholeOut
  * @Desrciption 整单出库供应商新增
  *
  * @Param
  * [stroInDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/3/8 14:20
  * @Return cn.hsa.module.stro.stroin.dto.StroInDTO
  **/
  StroInDTO insertWholeSuppOut(StroInDTO stroInDTO);

  /**
  * @Menthod queryStroinDetail
  * @Desrciption 查询明细数据
  *
  * @Param
  * [stroInDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/3/29 10:02
  * @Return java.util.List<cn.hsa.module.stro.stroin.dto.StroInDetailDTO>
  **/
  List<StroInDetailDTO> queryStroinDetail(StroInDetailDTO stroInDetailDTO);
  /**
   * @Meth: queryStroinDetailForExprot
   * @Description: 查询明细 为了批量导出
   * @Param: [stroInDetailDTO]
   * @return: java.util.List<cn.hsa.module.stro.stroin.dto.StroInDetailDTO>
   * @Author: zhangguorui
   * @Date: 2021/9/17
   */
  List<StroInDetailDTO> queryStroinDetailForExprot(StroInDetailDTO stroInDetailDTO);
}
