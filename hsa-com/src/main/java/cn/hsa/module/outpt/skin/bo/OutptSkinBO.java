package cn.hsa.module.outpt.skin.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.outpt.skin.dto.OutptSkinDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.outpt.skin.bo
 * @Class_name:: OutptSkinBO
 * @Description: 皮试处理业务逻辑实现层
 * @Author: zhangxuan
 * @Date: 2020-08-14 10:26
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptSkinBO {

   /**
    * @Method
    * @Desrciption 根据条件分页查询皮试处理结果
    * @Param
    * outptSkinDTO
    * @Author zhangxuan
    * @Date   2020-08-14 14:03
    * @Return
    **/
    PageDTO queryPage(OutptSkinDTO outptSkinDTO);
    /**
     * @Method
     * @Desrciption  根据条件查询皮试处理结果
     * @Param
     * outptSkinDTO
     * @Author zhangxuan
     * @Date   2020-08-14 15:18
     * @Return
    **/
    List<OutptSkinDTO> queryAll(OutptSkinDTO outptSkinDTO);
    /**
     * @Method getById
     * @Desrciption  根据主键查询
     * @Param 
     * outptSkinDTO
     * @Author zhangxuan
     * @Date   2020-08-14 16:57
     * @Return 
    **/
    OutptSkinDTO getById(OutptSkinDTO outptSkinDTO);
    /**
     * @Method insert
     * @Desrciption  新增皮试处理结果
     * @Param
     * outptSkinDTO
     * @Author zhangxuan
     * @Date   2020-08-14 16:57
     * @Return
    **/
    Boolean insert(OutptSkinDTO outptSkinDTO);
    /**
     * @Method update
     * @Desrciption  编辑皮试处理结果
     * @Param
     * outptSkinDTO
     * @Author zhangxuan
     * @Date   2020-08-14 16:58
     * @Return
    **/
    Boolean update(OutptSkinDTO outptSkinDTO);
    /**
     * @Method delete
     * @Desrciption  删除皮试处理结果
     * @Param
     * outptSkinDTO
     * @Author zhangxuan
     * @Date   2020-08-14 16:59
     * @Return
    **/
    Boolean delete(OutptSkinDTO outptSkinDTO);
}
