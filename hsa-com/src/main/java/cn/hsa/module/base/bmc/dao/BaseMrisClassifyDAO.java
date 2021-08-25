package cn.hsa.module.base.bmc.dao;


import cn.hsa.module.base.bmc.dto.BaseMrisClassifyDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.bfc.dao
 * @Class_name: BaseMaterialManagementDAO
 * @Describe: 病案费用归类数据访问层接口
 * @Author: powersi
 * @Date: 2020/09/17 15:58
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

public interface BaseMrisClassifyDAO {

    /**
     * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询病案费用归类
     * @param baseMrisClassifyDTO  主键ID和医院编码等参数
     * @Author xingyu.xie
     * @Date   2020/09/17 15:31
     * @Return BaseMaterialDTO
     **/
    BaseMrisClassifyDTO getById(BaseMrisClassifyDTO baseMrisClassifyDTO);

    /**
     * @Menthod queryAll
     * @Desrciption 查询全部病案费用归类
     * @param baseMrisClassifyDTO  医院编码
     * @Author xingyu.xie
     * @Date   2020/09/17 16:05
     * @Return java.util.List<cn.hsa.module.base.bfc.dto.BaseMaterialDTO>
     **/
    List<BaseMrisClassifyDTO> queryAll(BaseMrisClassifyDTO baseMrisClassifyDTO);

    /**
    * @Menthod insert
    * @Desrciption 新增单条病案费用归类
     * @param baseMrisClassifyDTO  材料分类数据对象
    * @Author xingyu.xie
    * @Date   2020/09/17 16:05
    * @Return int
    **/
    int insert(BaseMrisClassifyDTO baseMrisClassifyDTO);

    /**
    * @Menthod update
    * @Desrciption 修改单条病案费用归类(有非空判断)
     * @param baseMrisClassifyDTO  材料分类数据对象
    * @Author xingyu.xie
    * @Date   2020/09/17 16:05
    * @Return int
    **/
    int updateBaseMrisClassifyS(BaseMrisClassifyDTO baseMrisClassifyDTO);

    /**
     * @Menthod update
     * @Desrciption 修改单条病案费用归类(无非空判断)
     * @param baseMrisClassifyDTO  材料分类数据对象
     * @Author xingyu.xie
     * @Date   2020/09/17 16:05
     * @Return int
     **/
    int updateBaseMrisClassify(BaseMrisClassifyDTO baseMrisClassifyDTO);

    /**
    * @Menthod updateStatus
    * @Desrciption   根据主键ID和医院编码等参数删除病案费用归类
     * @param baseMrisClassifyDTO  病案费用归类表主键id列表，医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/09/17 16:05
    * @Return int
    **/
    int updateStatus(BaseMrisClassifyDTO baseMrisClassifyDTO);

    /**
     * @Method isCodeExist
     * @Desrciption 判断病案费用归类编码是否已经存在
     * @Param
     * [baseMrisClassifyDTO]
     * @Author xingyu.xie
     * @Date   2020/7/24 16:45
     * @Return java.lang.Integer
     **/
    Integer isCodeExist(BaseMrisClassifyDTO baseMrisClassifyDTO);
}
