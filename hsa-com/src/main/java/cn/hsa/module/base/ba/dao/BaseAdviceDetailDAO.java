package cn.hsa.module.base.ba.dao;

import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.baseadvice.dao
 * @Class_name: BaseAdviceDAO
 * @Describe: 医嘱详细表数据访问层接口
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/13 16:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BaseAdviceDetailDAO {

    /**
     * @Menthod getById
     * @Desrciption 根据主键Id查询医嘱详细信息
     * @param baseAdviceDetailDTO  主键ID等参数
     * @Author xingyu.xie
     * @Date   2020/7/7 15:31
     * @Return baseAdviceDetailDTO
     **/
    BaseAdviceDetailDTO getById(BaseAdviceDetailDTO baseAdviceDetailDTO);

    /**
     * @Menthod queryAll
     * @Desrciption 查询全部医嘱详细信息
     * @param baseAdviceDetailDTO  医院编码等参数
     * @Author xingyu.xie
     * @Date   2020/7/7 16:05
     * @Return java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO>
     **/
    List<BaseAdviceDetailDTO> queryAll(BaseAdviceDetailDTO baseAdviceDetailDTO);

    /**
     * @Menthod queryDetailByAdviceCodes
     * @Desrciption   根据医嘱编码查询医嘱详细信息
     * @param baseAdviceDTO  医嘱编码等信息
     * @Author xingyu.xie
     * @Date   2020/7/14 16:04
     * @Return java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO>
     **/
    List<BaseAdviceDetailDTO> queryItemByAdviceCode(BaseAdviceDTO baseAdviceDTO);

    /**
     * @Menthod queryAll
     * @Desrciption 根据项目编码和医院编码查询项目详细信息
     * @param baseAdviceDTO  项目编码和医院编码
     * @Author xingyu.xie
     * @Date   2020/7/7 16:05
     * @Return java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO>
     **/
    List<BaseAdviceDetailDTO> queryDetailByAdviceCode(BaseAdviceDTO baseAdviceDTO);

    /**
     * @Menthod queryPage
     * @Desrciption 分页查询医嘱详细信息
     * @param baseAdviceDetailDTO  医嘱详细分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/7 16:05
     * @Return java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO>
     **/
    List<BaseAdviceDetailDTO> queryPage(BaseAdviceDetailDTO baseAdviceDetailDTO);

    /**
     *  根据项目编码表查询医嘱详细信息
     * @param baseAdviceDetailDTO
     * @Date 2021/8/11
     * @Author: luonianxin
     * @return java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO>
     */
    List<BaseAdviceDetailDTO> queryBaseAdviceByItemCode(BaseAdviceDetailDTO baseAdviceDetailDTO);
    /**
     * @Menthod insert
     * @Desrciption 新增单条医嘱详细信息
     * @param baseAdviceDetailDTO  医嘱详细分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/7 16:05
     * @Return int
     **/
    int insert(@Param("list") List<BaseAdviceDetailDTO> baseAdviceDetailDTO);

    /**
     * @Menthod update
     * @Desrciption 修改单条医嘱详细信息
     * @param baseAdviceDetailDTO  医嘱详细分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/7 16:05
     * @Return int
     **/
    int update(@Param("list") List<BaseAdviceDetailDTO> baseAdviceDetailDTO);

    /**
     * @Menthod delete
     * @Desrciption   根据主键ID等参数删除医嘱详细信息
     * @param baseAdviceDTO  医嘱详细信息表主键id列表等参数
     * @Author xingyu.xie
     * @Date   2020/7/7 16:05
     * @Return int
     **/
    int delete(BaseAdviceDTO baseAdviceDTO);


    /**
     * @Menthod queryItemAndMaterialPage
     * @Desrciption 查询项目和材料表二合一数据
     * @param baseAdviceDetailDTO 医嘱详细数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/4 16:21
     * @Return java.util.List<cn.hsa.module.base.ba.dto.BaseItemAndMaterialDTO>
     **/
    List<BaseAdviceDetailDTO> queryItemAndMaterialPage(BaseAdviceDetailDTO baseAdviceDetailDTO);

    /**
    * @Method queryItemAndMaterialStockPage
    * @Desrciption 查询出医院的项目和有库存的材料信息（typeCode是区分材料和项目 itemTypeCode 是项目分类或材料分类代码）
    * @param baseAdviceDetailDTO
    * @Author liuqi1
    * @Date   2020/12/7 9:52
    * @Return java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO>
    **/
    List<BaseAdviceDetailDTO> queryItemAndMaterialStockPage(BaseAdviceDetailDTO baseAdviceDetailDTO);


    /**
    * @Method queryItemAndMaterialAndDrugPage
    * @Param [baseAdviceDetailDTO]
    * @description   查询和项目和材料和药品三合一表
    * @author marong
    * @date 2020/9/29 15:48
    * @return java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO>
    * @throws
    */
    List<BaseAdviceDetailDTO> queryItemAndMaterialAndDrugPage(BaseAdviceDetailDTO baseAdviceDetailDTO);

    void updateGenerateStutas(@Param("code") List<String> code,@Param("hospCode") String hospCode, @Param("key")String key);

    List<String> queryBaseAdviceDetailByItemCode(@Param("code")List<String> code, @Param("hospCode")String hospCode);
}
