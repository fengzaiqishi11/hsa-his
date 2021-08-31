package cn.hsa.module.insure.module.dao;

import cn.hsa.module.insure.module.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.module.dao
 * @class_name: InsureDirectoryDownLoadDAO
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/21 14:50
 * @Company: 创智和宇
 **/
public interface InsureDirectoryDownLoadDAO {

    /**
     * @Method insertInsureDirectory
     * @Desrciption  保存医保目录信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/21 16:03
     * @Return
    **/
    void insertInsureDirectory(@Param("directoryInfoDOList") List<InsureDirectoryInfoDO> directoryInfoDOList);

    /**
     * @Method queryAllInsureDirectory
     * @Desrciption  查询所有保存的医保目录信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/21 16:03
     * @Return
     **/
    List<InsureDirectoryInfoDO> queryAllInsureDirectory(Map<String, Object> map);

    /**
     * @Method queyrAllInsureUnifiedMatch
     * @Desrciption  查询所有的医疗目录与医保目录匹配信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/21 17:17
     * @Return
    **/
    List<InsureUnifiedMatchDO> queyrAllInsureUnifiedMatch(Map<String, Object> map);


    /**
     * @Method insertInsureUnfiedMatch
     * @Desrciption  新增医疗目录与医保目录匹配信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/21 19:54
     * @Return
    **/
    void insertInsureUnfiedMatch(@Param("insertList") List<InsureUnifiedMatchDO> insertList);


    /**
     * @Method queyrAllInsureUnifiedLimitPrice
     * @Desrciption  查询所有的医保目录限价信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/21 18:50
     * @Return
    **/
    List<InsureUnifiedLimitPriceDO> queyrAllInsureUnifiedLimitPrice(Map<String, Object> map);

    /**
     * @Method insertInsureUnfiedLimitPrice
     * @Desrciption  批量新增医保目录限价信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/21 20:05
     * @Return
    **/
    void insertInsureUnfiedLimitPrice(@Param("insureUnifiedLimitPriceDOList") List<InsureUnifiedLimitPriceDO>  insureUnifiedLimitPriceDOList);

    /**
     * @Method queryAllInsureUnifiedRatio
     * @Desrciption  查询所有的医保目录先自付比例信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/21 19:05
     * @Return
    **/
    List<InsureUnifidRatioDO> queryAllInsureUnifiedRatio(Map<String, Object> map);

    /**
     * @Method insertInsureUnifiedRation
     * @Desrciption  新增医保目录先自付比例信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/21 19:10
     * @Return
    **/
    void insertInsureUnifiedRation(@Param("list") List<InsureUnifidRatioDO> list);

    /**
     * @param insureUnifidRatioDO
     * @Method queryPageInsureUnifiedLimitPrice
     * @Desrciption 分页查询所有的目录限价信息
     * @Param
     * @Author fuhui
     * @Date 2021/4/21 19:16
     * @Return
     */
    List<InsureDirectoryInfoDO> queryPageInsureUnifiedRatio(InsureUnifidRatioDO insureUnifidRatioDO);

    /**
     * @param insureDirectoryInfoDO
     * @Method queryPageInsureUnifiedMatch
     * @Desrciption 分页查询所有医保目录先自付比例信息
     * @Param
     * @Author fuhui
     * @Date 2021/4/21 19:16
     * @Return
     */
    List<InsureUnifidRatioDO> queryPageInsureUnifiedDirectory(InsureDirectoryInfoDO insureDirectoryInfoDO);

    /**
     * @param insureUnifiedMatchDO
     * @Method queryPageInsureUnifiedMatch
     * @Desrciption 分页查询所有医疗目录与医保目录匹配信息
     * @Param
     * @Author fuhui
     * @Date 2021/4/21 19:16
     * @Return
     */
    List<InsureUnifiedMatchDO> queryPageInsureUnifiedMatch(InsureUnifiedMatchDO insureUnifiedMatchDO);

    /**
     * @param insureUnifiedLimitPriceDO
     * @Method queryPageInsureUnifiedLimitPrice
     * @Desrciption 分页查询所有的目录限价信息
     * @Param
     * @Author fuhui
     * @Date 2021/4/21 19:16
     * @Return
     */
    List<InsureUnifiedLimitPriceDO> queryPageInsureUnifiedLimitPrice(InsureUnifiedLimitPriceDO insureUnifiedLimitPriceDO);

    List<InsureUnifiedMatchMedicinsDO> queyrAllInsureMedicinesMatch(Map<String, Object> map);

    List<InsureUnifiedMatchMedicinsDO> queyrPageInsureMedicinesMatch(InsureUnifiedMatchMedicinsDO insureUnifiedMatchMedicinsDO);

    void insertInsureMedicinesMatch(@Param("unifiedMatchDOList") List<InsureUnifiedMatchMedicinsDO> unifiedMatchDOList);
    
    /**
     * @Method 查询所有的名族药品数据
     * @Desrciption  
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/4/28 16:29
     * @Return 
    **/
    List<InsureUnifiedNationDrugDO> queyrAllInsureNationDrug(Map<String, Object> map);
}
