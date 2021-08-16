package cn.hsa.module.inpt.inptguarantee.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.inpt.inptguarantee.dto.InptGuaranteeDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.inpt.inptguarantee.bo
 * @Class_name: InptGuaranteeBO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/8/10 15:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface InptGuaranteeBO {

  /**
  * @Menthod queryById
  * @Desrciption 查询单个保证金信息
  *
  * @Param
  * [inptGuaranteeDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/8/10 15:58
  * @Return cn.hsa.module.inpt.inptguarantee.dto.InptGuaranteeDTO
  **/
  InptGuaranteeDTO queryById(InptGuaranteeDTO inptGuaranteeDTO);

  /**
  * @Menthod queryAllInptGuarantee
  * @Desrciption 查询所有保证金
  *
  * @Param
  * [inptGuaranteeDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/8/10 15:58
  * @Return java.util.List<cn.hsa.module.inpt.inptguarantee.dto.InptGuaranteeDTO>
  **/
  List<InptGuaranteeDTO> queryAllInptGuarantee(InptGuaranteeDTO inptGuaranteeDTO);

  /**
  * @Menthod queryPageInptGuarantee
  * @Desrciption 分页查询所有保证金
  *
  * @Param
  * [inptGuaranteeDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/8/10 15:58
  * @Return cn.hsa.base.PageDTO
  **/
  PageDTO queryPageInptGuarantee(InptGuaranteeDTO inptGuaranteeDTO);

  /**
  * @Menthod save
  * @Desrciption 保存
  *
  * @Param
  * [inptGuaranteeDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/8/10 15:58
  * @Return java.lang.Boolean
  **/
  Boolean save(InptGuaranteeDTO inptGuaranteeDTO);

  /**
  * @Menthod updateAuditCode
  * @Desrciption 修改状态
  *
  * @Param
  * [inptGuaranteeDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/8/10 15:58
  * @Return java.lang.Boolean
  **/
  Boolean updateAuditCode(InptGuaranteeDTO inptGuaranteeDTO);

}
