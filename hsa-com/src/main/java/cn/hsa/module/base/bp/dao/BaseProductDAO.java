package cn.hsa.module.base.bp.dao;

import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.bp.dto.BaseProductDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.bfc.dao
 * @Class_name: BaseMaterialManagementDAO
 * @Describe: 生产企业数据访问层接口
 * @Author: powersi
 * @Date: 2020/7/7 15:58
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

public interface BaseProductDAO {

    /**
     * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询生产企业
     * @param baseProductDTO  主键ID和医院编码等参数
     * @Author xingyu.xie
     * @Date   2020/7/7 15:31
     * @Return BaseMaterialDTO
     **/
    BaseProductDTO getById(BaseProductDTO baseProductDTO);

    /**
     * @Menthod queryAll
     * @Desrciption 查询全部生产企业
     * @param baseProductDTO  医院编码
     * @Author xingyu.xie
     * @Date   2020/7/7 16:05
     * @Return java.util.List<cn.hsa.module.base.bfc.dto.BaseMaterialDTO>
     **/
    List<BaseProductDTO> queryAll(BaseProductDTO baseProductDTO);

    /**
    * @Menthod queryPage
    * @Desrciption 分页查询生产企业
     * @param baseProductDTO  材料分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/7 16:05
    * @Return java.util.List<cn.hsa.module.base.bfc.dto.BaseMaterialDTO>
    **/
    List<BaseProductDTO> queryPage(BaseProductDTO baseProductDTO);
    /**
    * @Menthod insert
    * @Desrciption 新增单条生产企业
     * @param baseProductDTO  材料分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/7 16:05
    * @Return int
    **/
    int insert(BaseProductDTO baseProductDTO);

    /**
    * @Menthod update
    * @Desrciption 修改单条生产企业
     * @param baseProductDTO  材料分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/7 16:05
    * @Return int
    **/
    int update(BaseProductDTO baseProductDTO);

    /**
    * @Menthod updateStatus
    * @Desrciption   根据主键ID和医院编码等参数删除生产企业
     * @param baseProductDTO  生产企业表主键id列表，医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/7/7 16:05
    * @Return int
    **/
    int updateStatus(BaseProductDTO baseProductDTO);

    /**
     * @Method isCodeExist
     * @Desrciption 判断生产企业编码是否已经存在
     * @Param
     * [baseProductDTO]
     * @Author xingyu.xie
     * @Date   2020/7/24 16:45
     * @Return java.lang.Integer
     **/
    Integer isCodeExist(BaseProductDTO baseProductDTO);

    /**
     * @Method: getByName
     * @Description: 根据厂家名称查询
     * @Param: code、name
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date 2021-03-26 11:53
     * @Return List<BaseProductDTO>
     */
    List<BaseProductDTO> getByName(BaseMaterialDTO baseMaterialDTO);
}
