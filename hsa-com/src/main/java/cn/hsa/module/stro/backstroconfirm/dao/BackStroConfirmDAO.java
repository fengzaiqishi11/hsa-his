package cn.hsa.module.stro.backstroconfirm.dao;

import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.module.stro.stroout.dto.StroOutDTO;
import cn.hsa.module.stro.stroout.dto.StroOutDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.store.instore.bo
 * @Class_name: StroOutBo
 * @Describe:
 * @Author: xingyu.xie
 * @Email: xingyu.xie@powersi.com
 * @Date: 2020/7/22 8:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BackStroConfirmDAO {


  /**
  * @Menthod queryBackOutPage
  * @Desrciption  分页查询入库表的退库记录
   * @param stroOutDTO 入库表数据传输对象
  * @Author xingyu.xie
  * @Date   2020/8/20 8:56
  * @Return java.util.List<cn.hsa.module.stro.stroout.dto.StroOutDTO>
  **/
  List<StroOutDTO> queryBackOutPage(StroOutDTO stroOutDTO);

  /**
  * @Menthod queryOutDetailByOutId
  * @Desrciption  根据主表的单据号查询详细表的数据
   * @param stroOutDTO 入库表数据传输对象
  * @Author xingyu.xie
  * @Date   2020/8/20 8:56
  * @Return java.util.List<cn.hsa.module.stro.stroout.dto.StroOutDetailDTO>
  **/
  List<StroOutDetailDTO> queryOutDetailByOutId(StroOutDTO stroOutDTO);

  /**
  * @Menthod getById
  * @Desrciption  通过id查询出库的主表记录
   * @param stroOutDTO 入库表数据传输对象
  * @Author xingyu.xie
  * @Date   2020/8/20 8:57
  * @Return cn.hsa.module.stro.stroout.dto.StroOutDTO
  **/
  StroOutDTO getById(StroOutDTO stroOutDTO);

  /**
  * @Menthod queryOutDetailByOutIds
  * @Desrciption  通过多个主表的单据号查询相应的所有出库明细表数据
   * @param stroOutDTO 入库表数据传输对象
  * @Author xingyu.xie
  * @Date   2020/8/20 8:57
   *
  * @Return java.util.List<cn.hsa.module.stro.stock.dto.StroStockDetailDTO>
  **/
  List<StroStockDetailDTO> queryOutDetailByOutIds(StroOutDTO stroOutDTO);

  /**
  * @Menthod updateAuditCode
  * @Desrciption  该变退库审核的的审核状态
   * @param stroOutDTO
  * @Author xingyu.xie
  * @Date   2020/8/20 8:58
  * @Return int
  **/
  int updateOkAuditCode(StroOutDTO stroOutDTO);


  /**
   * @Menthod
   * @Desrciption
   * @param id
   * @Author ljh
   * @Date   2020/8/7 15:53
   * @Return int
   **/
  int deleteById(String id, String hospCode);

  /**
   * @Menthod
   * @Desrciption
   * @param  list
   * @Author ljh
   * @Date   2020/8/7 15:53
   * @Return int
   **/
  int insertList(List<StroOutDetailDTO> list);

  /**
   * @Package_name: cn.hsa.module.stro.backstroconfirm.dao
   * @Class_name:: StroOutDAO
   * @Description:
   * @Author: ljh
   * @Date: 2020/8/19 18:17
   * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
   */
  List<StroOutDTO> queryBackOutinPageyf(StroOutDTO stroOutDTO);


  /**
   * @Package_name: cn.hsa.module.stro.backstroconfirm.dao
   * @Class_name:: StroOutDAO
   * @Description:
   * @Author: ljh
   * @Date: 2020/8/19 16:21
   * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
   */
  int insert(StroOutDTO stroOutDTO);

  /**
   * @Package_name: cn.hsa.module.stro.backstroconfirm.dao
   * @Class_name:: StroOutDAO
   * @Description:
   * @Author: ljh
   * @Date: 2020/8/19 16:21
   * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
   */
  int update(StroOutDTO stroOutinDTO);

  /**
   * @Package_name: cn.hsa.module.stro.backstroconfirm.dao
   * @Class_name:: StroOutDAO
   * @Description:
   * @Author: ljh
   * @Date: 2020/8/19 16:20
   * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
   */
  StroOutDTO getById(String id);
  /**
   * @Package_name: cn.hsa.module.stro.backstroconfirm.dao
   * @Class_name:: BackStroConfirmDAO
   * @Description: shenghe
   * @Author: ljh
   * @Date: 2020/8/10 17:22
   * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
   */
  int updateAuditCode(List<StroOutDTO> list);

  /**
   * @Package_name: cn.hsa.module.stro.backstroconfirm.dao
   * @Class_name:: StroOutDAO
   * @Description:
   * @Author: ljh
   * @Date: 2020/8/19 16:21
   * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
   */

  List<StroOutDTO> queryByids(List<String> list);
  /**
   * @Meth: queryDeptById
   * @Description: 获得类别标识
   * @Param: [inStockId, hospCode]
   * @return: java.lang.String
   * @Author: zhangguorui
   * @Date: 2022/2/17
   */
  String queryDeptById(@Param("inStockId") String inStockId,@Param("hospCode") String hospCode);

  List<String> queryDrug(@Param("types") List<String> types, @Param("value") List<StroOutDetailDTO> value);
}
