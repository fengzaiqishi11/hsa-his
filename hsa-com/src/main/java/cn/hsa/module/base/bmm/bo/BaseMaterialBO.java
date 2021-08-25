package cn.hsa.module.base.bmm.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.bfc.bo
 * @Class_name: BaseMaterialManagementBO
 * @Describe: 材料信息业务逻辑实现层接口
 * @Author: powersi
 * @Date: 2020/7/7 16:00
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BaseMaterialBO {

    /**
    * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询材料信息
     * @param baseMaterialDTO  主键ID 和医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/7/7 16:05
    * @Return cn.hsa.module.base.bfc.dto.BaseMaterialDTO
    **/
    BaseMaterialDTO getById(BaseMaterialDTO baseMaterialDTO,String type);

    /**
     * @Method: getByCode
     * @Description:根据编码获取材料信息
     * @Param: [baseMaterialDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/9 16:18
     * @Return: cn.hsa.module.base.bmm.dto.BaseMaterialDTO
     **/
    BaseMaterialDTO getByCode(BaseMaterialDTO baseMaterialDTO);

    /**
     * @Menthod queryAll
     * @Desrciption   查询所有材料信息
     * @param baseMaterialDTO  医院编码
     * @Author xingyu.xie
     * @Date   2020/7/14 16:04
     * @Return java.util.List<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    List<BaseMaterialDTO> queryAll(BaseMaterialDTO baseMaterialDTO);

    /**
    * @Method queryBaseMaterialDTOs
    * @Desrciption 查询出符合条件的材料信息
    * @param baseMaterialDTO
    * @Author liuqi1
    * @Date   2020/9/3 15:11
    * @Return java.util.List<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
    **/
    List<BaseMaterialDTO> queryBaseMaterialDTOs(BaseMaterialDTO baseMaterialDTO);

    /**
    * @Menthod queryAll
    * @Desrciption   分页查询材料信息
     * @param baseMaterialDTO  材料分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/7 16:04
    * @Return java.util.List<cn.hsa.module.base.bfc.dto.BaseMaterialDTO>
    **/
    PageDTO queryPage(BaseMaterialDTO baseMaterialDTO);

    /**
     * @Menthod save
     * @Desrciption  修改或新增材料分类信息
     * @param baseMaterialDTO 材料分类数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:32
     * @Return boolean
     **/
    boolean save(BaseMaterialDTO baseMaterialDTO);

    /**
    * @Menthod updateList
    * @Desrciption 修改多条材料
     * @param baseMaterialDTOList 材料分类数据参数对象
    * @Author xingyu.xie
    * @Date   2020/8/24 15:32
    * @Return boolean
    **/
    boolean updateList(List<BaseMaterialDTO> baseMaterialDTOList);

    /**
    * @Menthod updateStatus
     * @Desrciption   根据主键ID和医院编码等参数删除材料信息
     * @param baseMaterialDTO  材料信息表主键id列表，医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/7/7 16:05
    * @Return int
    **/
    boolean updateStatus(BaseMaterialDTO baseMaterialDTO);


    /**
     * @Menthod insertUpLoad
     * @Desrciption   数据导入功能
     * @param map  材料信息表主键id列表，医院编码等参数
     * @Author xingyu.xie
     * @Date   2020/7/7 16:05
     * @Return int
     **/
    boolean insertUpLoad(Map map);

    /**
     * @param map 材料信息数据传输对象List
     * @Menthod updateNationCodeById
     * @Desrciption 批量修改材料信息
     * @Author pengbo
     * @Date 2021/3/25 16:48
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    boolean updateNationCodeById(Map map);
    Boolean insertInsureMaterialMatch(Map<String, Object> map);


    PageDTO queryUnifiedPage(BaseMaterialDTO baseMaterialDTO);
}
