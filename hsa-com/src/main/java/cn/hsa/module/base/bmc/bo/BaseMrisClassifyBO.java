package cn.hsa.module.base.bmc.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.bmc.dto.BaseMrisClassifyDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.bfc.bo
 * @Class_name: BaseMaterialManagementBO
 * @Describe: 病案费用归类业务逻辑实现层接口
 * @Author: xingyu.xie
 * @Date: 2020/09/17 16:00
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BaseMrisClassifyBO {

    /**
    * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询病案费用归类
     * @param baseMrisClassifyDTO  主键ID 和医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/09/17 16:05 
    * @Return cn.hsa.module.base.bfc.dto.BaseMaterialDTO
    **/
    BaseMrisClassifyDTO getById(BaseMrisClassifyDTO baseMrisClassifyDTO);

    /**
     * @Menthod queryAll
     * @Desrciption   查询所有病案费用归类
     * @param baseMrisClassifyDTO  医院编码
     * @Author xingyu.xie
     * @Date   2020/7/14 16:04
     * @Return java.util.List<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    List<BaseMrisClassifyDTO> queryAll(BaseMrisClassifyDTO baseMrisClassifyDTO);

    /**
    * @Menthod queryAll
    * @Desrciption   分页查询病案费用归类
     * @param baseMrisClassifyDTO  生产企业分类数据对象
    * @Author xingyu.xie
    * @Date   2020/09/17 16:04 
    * @Return java.util.List<cn.hsa.module.base.bfc.dto.BaseMaterialDTO>
    **/
    PageDTO queryPage(BaseMrisClassifyDTO baseMrisClassifyDTO);

    /**
     * @Menthod save
     * @Desrciption  修改或新增病案费用归类
     * @param baseMrisClassifyDTO 生产企业数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:32
     * @Return boolean
     **/
    boolean save(BaseMrisClassifyDTO baseMrisClassifyDTO);

    /**
     * @Menthod updateBaseMrisClassifyS
     * @Desrciption  修改病案费用归类
     * @param baseMrisClassifyDTO 生产企业数据参数对象
     * @Author xingyu.xie
     * @Date   2020/09/17 11:32
     * @Return boolean
     **/
    boolean updateBaseMrisClassifyS(BaseMrisClassifyDTO baseMrisClassifyDTO);

    /**
    * @Menthod updateStatus
     * @Desrciption   根据主键ID和医院编码等参数删除病案费用归类
     * @param baseMrisClassifyDTO  病案费用归类表主键id列表，医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/09/17 16:05 
    * @Return int
    **/
    boolean updateStatus(BaseMrisClassifyDTO baseMrisClassifyDTO);
}
