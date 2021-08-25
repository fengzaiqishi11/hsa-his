package cn.hsa.module.stro.backstroconfirm.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.stro.stroout.dto.StroOutDTO;
import cn.hsa.module.stro.stroout.dto.StroOutDetailDTO;

import java.util.List;


/**
 * @Package_name: cn.hsa.module.store.retunstro.bo
 * @Class_name: ReturnStroBo
 * @Describe:
 * @Author: xingyu.xie
 * @Email: xingyu.xie@powersi.com
 * @Date: 2020/7/22 8:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BackStroConfirmBO {


  /**
   * @Menthod queryBackOutinPage
   * @Desrciption  查询药房退库的单据信息
   * @param stroOutDTO 出入库表数据传输对象
   * @Author xingyu.xie
   * @Date   2020/7/27 14:26
   * @Return java.util.List<cn.hsa.module.stro.outinstro.dto.StroOutDTO>
   **/
  PageDTO queryBackOutPage(StroOutDTO stroOutDTO);

  /**
  * @Menthod queryBackOutinPageyf
  * @Desrciption  药房退库管理查询
   * @param stroOutDTO 出入库表数据传输对象
  * @Author xingyu.xie
  * @Date   2020/8/19 20:05
  * @Return cn.hsa.base.PageDTO
  **/
  PageDTO queryBackOutinPageyf(StroOutDTO stroOutDTO);
  /**
   * @Menthod queryOutinDetailByOutinId
   * @Desrciption  根据医院编码和出入库表orderNo去查询出入库明细的数据
   * @param stroOutDTO 出入库表数据传输对象
   * @Author xingyu.xie
   * @Date   2020/7/27 14:26
   * @Return java.util.List<cn.hsa.module.stro.outinstro.dto.StroOutinDetailDTO>
   **/
  List<StroOutDetailDTO> queryOutDetailByOutId(StroOutDTO stroOutDTO);

  /**
   * @Menthod updateAuditCode
   * @Desrciption  批量进行退库确认的审核
   * @param stroOutDTO 出入库表数据传输对象
   * @Author xingyu.xie
   * @Date   2020/7/27 14:29
   * @Return boolean
   **/
  Boolean updateOkAuditCode(StroOutDTO stroOutDTO);
  /**
   * @Menthod insert
   * @Desrciption  新增
   * @param stroOutDTO 出入库表数据传输对象
   * @Author xingyu.xie
   * @Date   2020/7/27 14:29
   * @Return boolean
   **/
  int insert(StroOutDTO stroOutDTO);
  /**
   * @Menthod update
   * @Desrciption  gx
   * @param stroOutDTO 出入库表数据传输对象
   * @Author xingyu.xie
   * @Date   2020/7/27 14:29
   * @Return boolean
   **/
  int update(StroOutDTO stroOutDTO);


  /**
   * @Menthod getById
   * @Desrciption   通过ID查询单条数据
   * @param id 主键
   * @Author xingyu.xie
   * @Date   2020/7/27 15:24
   * @Return java.util.List<cn.hsa.module.stro.outinstro.dto.StroOutinDetailDTO>
   **/
  StroOutDTO getById(String id);

/**
 * @Package_name: cn.hsa.module.stro.backstroconfirm.bo
 * @Class_name:: BackStroConfirmBO
 * @Description:
 * @Author: ljh
 * @Date: 2020/8/11 8:50
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
int updateAuditCode(StroOutDTO stroOutDTO);
}
