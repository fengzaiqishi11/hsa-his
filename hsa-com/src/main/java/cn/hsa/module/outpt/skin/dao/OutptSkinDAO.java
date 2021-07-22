package cn.hsa.module.outpt.skin.dao;

import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.skin.dto.OutptSkinDTO;

import java.util.List;
/**
 * @Package_name: cn.hsa.module.outpt.skin.dao
 * @Class_name:: OutptSkinDAO
 * @Description: 皮试处理结果DAO层
 * @Author: zhangxuan
 * @Date: 2020-08-14 14:08
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptSkinDAO {
    /**
     * @Method
     * @Desrciption  根据条件分页查询皮试处理结果
     * @Param
     * outptSkinDTO
     * @Author zhangxuan
     * @Date   2020-08-14 14:09
     * @Return
    **/
    List<OutptSkinDTO> queryPage(OutptSkinDTO outptSkinDTO);
    /**
     * @Method
     * @Desrciption  根据条件查询皮试处理结果
     * @Param
     * outptSkinDTO
     * @Author zhangxuan
     * @Date   2020-08-14 15:24
     * @Return
    **/
    List<OutptSkinDTO> queryAll(OutptSkinDTO outptSkinDTO);
    /**
     * @Method getById
     * @Desrciption  根据主键查询皮试处理结果
     * @Param
     * outptSkinDTO
     * @Author zhangxuan
     * @Date   2020-08-14 17:15
     * @Return
    **/
    OutptSkinDTO getById(OutptSkinDTO outptSkinDTO);
    /**
     * @Method insert
     * @Desrciption  新增皮试处理结果
     * @Param
     * outptSkinDTO
     * @Author zhangxuan
     * @Date   2020-08-14 17:15
     * @Return
     **/
    int insert(OutptSkinDTO outptSkinDTO);
    /**
     * @Method update
     * @Desrciption  编辑皮试处理结果
     * @Param
     * outptSkinDTO
     * @Author zhangxuan
     * @Date   2020-08-14 17:15
     * @Return
     **/
    int update(OutptSkinDTO outptSkinDTO);
    int updateS(OutptSkinDTO outptSkinDTO);
    /**
     * @Method delete
     * @Desrciption  删除皮试处理结果
     * @Param
     * outptSkinDTO
     * @Author zhangxuan
     * @Date   2020-08-14 17:15
     * @Return
     **/
    int delete(OutptSkinDTO outptSkinDTO);

    OutptPrescribeDetailsDTO getPreDetailById(OutptSkinDTO outptSkinDTO);

    int updatePreDetail(OutptPrescribeDetailsDTO prescribeDetailsDTO);
}
