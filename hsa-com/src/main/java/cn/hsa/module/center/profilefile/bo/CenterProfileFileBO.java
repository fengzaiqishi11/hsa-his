package cn.hsa.module.center.profilefile.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.center.profilefile.dto.CenterProfileFileDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.profilefile.bo
 * @Class_name: CenterProfileFileBO
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/8/11 8:56
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface CenterProfileFileBO {
     /**
     * @Method getById
     * @Desrciption
     * @Param
     * [centerProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:50
     * @Return cn.hsa.module.center.profilefile.dto.CenterProfileFileDTO
     **/
     CenterProfileFileDTO getById(CenterProfileFileDTO centerProfileFileDTO);

     /**
      * @Method queryAll
      * @Desrciption
      * @Param
      * [centerProfileFileDTO]
      * @Author liaojunjie
      * @Date   2020/8/27 12:51
      * @Return java.util.List<cn.hsa.module.center.profilefile.dto.CenterProfileFileDTO>
      **/
     List<CenterProfileFileDTO> queryAll(CenterProfileFileDTO centerProfileFileDTO);

     /**
      * @Method queryPage
      * @Desrciption
      * @Param
      * [centerProfileFileDTO]
      * @Author liaojunjie
      * @Date   2020/8/27 12:51
      * @Return cn.hsa.base.PageDTO
      **/
     PageDTO queryPage(CenterProfileFileDTO centerProfileFileDTO);
}
