package cn.hsa.module.base.drug.dao;

import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.drug.dao
 * @Class_name:: BaseDrugDAO
 * @Description: 药品管理访问层接口
 * @Author: liaojunjie
 * @Date: 2020/7/16 17:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BaseDrugDAO {
    /**
     * @Method getById
     * @Desrciption 通过id获取项目对象
     * @Param
     * [baseDrugDTO]
     * @Author liaojunjie
     * @Date   2020/7/16 14:13
     * @Return cn.hsa.module.base.drug.dto.BaseDrugDTO
     **/
    BaseDrugDTO getById(BaseDrugDTO baseDrugDTO);

    /**
     * @Method: getByCode
     * @Description: 根据编码获取药品信息
     * @Param: [baseDrugDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/9 15:59
     * @Return: cn.hsa.module.base.drug.dto.BaseDrugDTO
     **/
    BaseDrugDTO getByCode(BaseDrugDTO baseDrugDTO);

    /**
     * @Method queryPage
     * @Desrciption 分页查询
     * @Param
     * [baseDrugDTO]
     * @Author liaojunjie
     * @Date   2020/7/16 11:26
     * @Return java.util.List<cn.hsa.module.base.drug.dto.BaseDrugDTO>
     **/
    List<BaseDrugDTO> queryPage(BaseDrugDTO baseDrugDTO);

    /**
     * @Method queryAll
     * @Desrciption 查询某医院下的所有药品
     * @Param
     * [baseDrugDTO]
     * @Author liaojunjie
     * @Date   2020/7/16 14:11
     * @Return java.util.List<cn.hsa.module.base.drug.dto.BaseDrugDTO>
     **/
    List<BaseDrugDTO> queryAll(BaseDrugDTO baseDrugDTO);

    /**
     * @Method insert
     * @Desrciption  插入
     * @Param
     * [baseDrugDTO]
     * @Author liaojunjie
     * @Date   2020/7/16 11:27
     * @Return java.lang.Integer
     **/
    Integer insert(BaseDrugDTO baseDrugDTO);

    /**
     * @Method update
     * @Desrciption 通过主键修改（无判空条件）
     * @Param
     * [baseDrugDTO]
     * @Author liaojunjie
     * @Date   2020/7/16 11:27
     * @Return java.lang.Integer
     **/
    Integer updateBaseDrug(BaseDrugDTO baseDrugDTO);


    /**
     * @Method update
     * @Desrciption 通过主键修改（有判空条件）、回写可用
     * @Param
     * [baseDrugDTO]
     * @Author liaojunjie
     * @Date   2020/7/16 11:27
     * @Return java.lang.Integer
     **/
    Integer updateBaseDrugS(BaseDrugDTO baseDrugDTO);

    /**
     * @Method updateStatus
     * @Desrciption  修改审核状态
     * @Param
     * [baseDrugDTO]
     * @Author liaojunjie
     * @Date   2020/7/16 11:27
     * @Return java.lang.Integer
     **/
    Integer updateStatus(BaseDrugDTO baseDrugDTO);

    /**
     * @Method updateAllById
     * @Desrciption  通过ID数组修改所有药品的信息
     * @Param
     * [baseDrugDTO]
     * @Author liaojunjie
     * @Date   2020/8/14 10:15
     * @Return java.lang.Integer
     **/
    Integer updateAllById(@Param("list") List<BaseDrugDTO> baseDrugDTOS);

    /**
    * @Method queryStockDrugInfoOfDept
    * @Desrciption 查询某库位的药品信息
    * @param baseDrugDTO
    * @Author liuqi1
    * @Date   2020/8/12 11:01
    * @Return java.util.List<cn.hsa.module.base.drug.dto.BaseDrugDTO>
    **/
    List<BaseDrugDTO> queryStockDrugInfoPage(BaseDrugDTO baseDrugDTO);

    /**
    * @Method queryNewStockDrugInfoPage
    * @Desrciption 查询某库位的药品信息,用于调价
    * @param baseDrugDTO
    * @Author liuqi1
    * @Date   2020/12/9 17:33
    * @Return java.util.List<cn.hsa.module.base.drug.dto.BaseDrugDTO>
    **/
    List<BaseDrugDTO> queryNewStockDrugInfoPage(BaseDrugDTO baseDrugDTO);

    /**
     * @Method isCodeExist
     * @Desrciption
     * @Param
     * [baseDrugDTO]
     * @Author liaojunjie
     * @Date   2020/8/29 15:03
     * @Return java.lang.Integer
     **/
    Integer isCodeExist(BaseDrugDTO baseDrugDTO);

    /**
     * @Method queryIds
     * @Desrciption 通过id数组查询多个药品信息
     * @Param
     * [baseDrugDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 10:26
     * @Return java.util.List<cn.hsa.module.base.drug.dto.BaseDrugDTO>
     **/
    List<BaseDrugDTO> queryIds(BaseDrugDTO baseDrugDTO);

    Boolean insertBatch (List<BaseDrugDTO>baseDrugDTOS);

    void insertInsureMatch(@Param("baseDrugDTOList")List<BaseDrugDTO> baseDrugDTOList);

    List<BaseDrugDTO> queryUnifiedPage(BaseDrugDTO baseDrugDTO);
}
