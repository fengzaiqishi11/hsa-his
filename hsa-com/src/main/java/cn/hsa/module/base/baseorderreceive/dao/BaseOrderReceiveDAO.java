package cn.hsa.module.base.baseorderreceive.dao;


import cn.hsa.module.base.baseorderreceive.dto.BaseOrderReceiveDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.bfc.dao
 * @Class_name: BaseMaterialManagementDAO
 * @Describe: 领药单据类型数据访问层接口
 * @Author: powersi
 * @Date: 2020/09/17 15:58
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

public interface BaseOrderReceiveDAO {

    /**
     * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询领药单据类型
     * @param baseOrderReceiveDTO  主键ID和医院编码等参数
     * @Author xingyu.xie
     * @Date   2020/09/17 15:31
     * @Return BaseMaterialDTO
     **/
    BaseOrderReceiveDTO getById(BaseOrderReceiveDTO baseOrderReceiveDTO);

    /**
     * @Menthod queryAll
     * @Desrciption 查询全部领药单据类型
     * @param baseOrderReceiveDTO  医院编码
     * @Author xingyu.xie
     * @Date   2020/09/17 16:05
     * @Return java.util.List<cn.hsa.module.base.bfc.dto.BaseMaterialDTO>
     **/
    List<BaseOrderReceiveDTO> queryAll(BaseOrderReceiveDTO baseOrderReceiveDTO);

    /**
    * @Menthod insert
    * @Desrciption 新增单条领药单据类型
     * @param baseOrderReceiveDTO  材料分类数据对象
    * @Author xingyu.xie
    * @Date   2020/09/17 16:05
    * @Return int
    **/
    int insert(BaseOrderReceiveDTO baseOrderReceiveDTO);

    /**
    * @Menthod update
    * @Desrciption 修改单条领药单据类型(有非空判断)
     * @param baseOrderReceiveDTO  材料分类数据对象
    * @Author xingyu.xie
    * @Date   2020/09/17 16:05
    * @Return int
    **/
    int updateBaseOrderReceiveS(BaseOrderReceiveDTO baseOrderReceiveDTO);

    /**
     * @Menthod update
     * @Desrciption 修改单条领药单据类型(无非空判断)
     * @param baseOrderReceiveDTO  材料分类数据对象
     * @Author xingyu.xie
     * @Date   2020/09/17 16:05
     * @Return int
     **/
    int updateBaseOrderReceive(BaseOrderReceiveDTO baseOrderReceiveDTO);

    /**
    * @Menthod updateStatus
    * @Desrciption   根据主键ID和医院编码等参数删除领药单据类型
     * @param baseOrderReceiveDTO  领药单据类型表主键id列表，医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/09/17 16:05
    * @Return int
    **/
    int updateStatus(BaseOrderReceiveDTO baseOrderReceiveDTO);

    /**
     * @Method isCodeExist
     * @Desrciption 判断领药单据类型名字是否已经存在
     * @Param
     * [baseOrderReceiveDTO]
     * @Author xingyu.xie
     * @Date   2020/7/24 16:45
     * @Return java.lang.Integer
     **/
    Integer isCodeExist(BaseOrderReceiveDTO baseOrderReceiveDTO);

    /**
     * @Method isCodeExist
     * @Desrciption 判断领药单据类型编码是否已经存在
     * @Param
     * [baseOrderReceiveDTO]
     * @Author xingyu.xie
     * @Date   2020/7/24 16:45
     * @Return java.lang.Integer
     **/
    Integer codeExist(BaseOrderReceiveDTO baseOrderReceiveDTO);
}
