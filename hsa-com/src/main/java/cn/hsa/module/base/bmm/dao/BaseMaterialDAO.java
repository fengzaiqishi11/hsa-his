package cn.hsa.module.base.bmm.dao;

import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.bfc.dao
 * @Class_name: BaseMaterialManagementDAO
 * @Describe: 材料信息数据访问层接口
 * @Author: powersi
 * @Date: 2020/7/7 15:58
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

public interface BaseMaterialDAO {

    /**
     * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询材料信息
     * @param baseMaterialDTO  主键ID和医院编码等参数
     * @Author xingyu.xie
     * @Date   2020/7/7 15:31
     * @Return BaseMaterialDTO
     **/
    BaseMaterialDTO getById(BaseMaterialDTO baseMaterialDTO);

    /**
    * @Menthod queryByIds
    * @Desrciption  根据主键ids查询多个材料的库存信息
     * @param baseMaterialDTO
    * @Author xingyu.xie
    * @Date   2020/12/9 11:34
    * @Return java.util.List<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
    **/
    List<BaseMaterialDTO> queryByIds(BaseMaterialDTO baseMaterialDTO);

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
     * @Desrciption 查询全部材料信息
     * @param baseMaterialDTO
     * @Author xingyu.xie
     * @Date   2020/7/7 16:05
     * @Return java.util.List<cn.hsa.module.base.bfc.dto.BaseMaterialDTO>
     **/
    List<BaseMaterialDTO> queryAll(BaseMaterialDTO baseMaterialDTO);

    /**
    * @Method queryBaseMaterialDTOs
    * @Desrciption 查询出符合条件的材料信息
    * @param baseMaterialDTO
    * @Author liuqi1
    * @Date   2020/9/3 15:03
    * @Return java.util.List<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
    **/
    List<BaseMaterialDTO> queryBaseMaterialDTOs(BaseMaterialDTO baseMaterialDTO);

    /**
    * @Menthod queryPage
    * @Desrciption 分页查询材料信息
     * @param baseMaterialDTO  材料分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/7 16:05
    * @Return java.util.List<cn.hsa.module.base.bfc.dto.BaseMaterialDTO>
    **/
    List<BaseMaterialDTO> queryPage(BaseMaterialDTO baseMaterialDTO);
    /**
    * @Menthod insert
    * @Desrciption 新增单条材料信息
     * @param baseMaterialDTO  材料分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/7 16:05
    * @Return int
    **/
    int insert(BaseMaterialDTO baseMaterialDTO);

    /**
    * @Menthod update
    * @Desrciption 修改单条材料信息
     * @param baseMaterialDTO  材料分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/7 16:05
    * @Return int
    **/
    int update(BaseMaterialDTO baseMaterialDTO);

    /**
     * @Menthod updateList
     * @Desrciption 修改多条条材料信息
     * @param baseMaterialDTO  材料分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/7 16:05
     * @Return int
     **/
    int updateList(@Param("list") List<BaseMaterialDTO> baseMaterialDTO);

    /**
    * @Menthod updateStatus
    * @Desrciption   根据主键ID和医院编码等参数删除材料信息
     * @param baseMaterialDTO  材料信息表主键id列表，医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/7/7 16:05
    * @Return int
    **/
    int updateStatus(BaseMaterialDTO baseMaterialDTO);

    /**
     * @Method isCodeExist
     * @Desrciption 判断材料编码是否已经存在
     * @Param
     * [baseMaterialDTO]
     * @Author xingyu.xie
     * @Date   2020/7/24 16:45
     * @Return java.lang.Integer
     **/
    Integer isCodeExist(BaseMaterialDTO baseMaterialDTO);

    /**
    * @Method queryStockMaterialInfoPage
    * @Desrciption 查询某库位的材料的信息
    * @param baseMaterialDTO
    * @Author liuqi1
    * @Date   2020/9/9 11:03
    * @Return java.util.List<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
    **/
    List<BaseMaterialDTO> queryStockMaterialInfoPage(BaseMaterialDTO baseMaterialDTO);

    /**
    * @Method queryNewStockMaterialInfoPage
    * @Desrciption 查询某库位的材料的信息,用于调价
    * @param baseMaterialDTO
    * @Author liuqi1
    * @Date   2020/12/9 17:27
    * @Return java.util.List<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
    **/
    List<BaseMaterialDTO> queryNewStockMaterialInfoPage(BaseMaterialDTO baseMaterialDTO);

    /**
    * @Menthod insertList
    * @Desrciption
     * @param baseMaterialDTO
    * @Author xingyu.xie
    * @Date   2021/1/8 16:42
    * @Return int
    **/
    int insertList(@Param("list") List<BaseMaterialDTO> baseMaterialDTO);

    void insertInsureMatch(@Param("baseMaterialDTOList") List<BaseMaterialDTO> baseMaterialDTOList);
    /**
     * @param baseMaterialDTO 材料信息数据传输对象List
     * @Menthod updateNationCodeById
     * @Desrciption 批量修改材料信息
     * @Author pengbo
     * @Date 2021/3/25 16:48
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    int updateNationCodeById(BaseMaterialDTO baseMaterialDTO);

    List<BaseMaterialDTO> queryUnifiedPage(BaseMaterialDTO baseMaterialDTO);
}
