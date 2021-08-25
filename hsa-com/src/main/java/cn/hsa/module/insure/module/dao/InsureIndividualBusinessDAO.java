package cn.hsa.module.insure.module.dao;

import cn.hsa.module.insure.module.dto.InsureIndividualBusinessDTO;
import cn.hsa.module.insure.module.entity.InsureIndividualBusinessDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.insure.module.dao
 * @Class_name: InsureIndividualBusinessDAO
 * @Describe(描述): 医保个人业务申请信息表 DAO层
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/19 20:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InsureIndividualBusinessDAO {

    /**
     * @Menthod selectByPrimaryKey
     * @Desrciption 根据主键查询医保个人业务申请信息
     * @param id 主键id
     * @Author Ou·Mr
     * @Date 2020/11/19 20:23 
     * @Return cn.hsa.module.insure.module.dto.InsureIndividualBusinessDTO
     */
    InsureIndividualBusinessDTO selectByPrimaryKey(@Param("id") String id);

    /**
     * @Menthod insert
     * @Desrciption 保存医保个人业务申请信息
     * @param insureIndividualBusinessDO 参数
     * @Author Ou·Mr
     * @Date 2020/11/19 20:23 
     * @Return int 受影响行数
     */
    int insert(InsureIndividualBusinessDO insureIndividualBusinessDO);

    /**
     * @Menthod insertSelective
     * @Desrciption 保存医保个人业务申请信息
     * @param insureIndividualBusinessDO 参数
     * @Author Ou·Mr
     * @Date 2020/11/19 20:25 
     * @Return int 受影响行数
     */
    int insertSelective(InsureIndividualBusinessDO insureIndividualBusinessDO);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption 修改医保个人业务申请信息
     * @param insureIndividualBusinessDO 参数
     * @Author Ou·Mr
     * @Date 2020/11/19 20:26 
     * @Return int 受影响的行数
     */
    int updateByPrimaryKey(InsureIndividualBusinessDO insureIndividualBusinessDO);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption 修改医保个人业务申请信息
     * @param insureIndividualBusinessDO 参数
     * @Author Ou·Mr
     * @Date 2020/11/19 20:29 
     * @Return int 受影响行数
     */
    int updateByPrimaryKeySelective(InsureIndividualBusinessDO insureIndividualBusinessDO);

    /**
     * @Menthod deleteById
     * @Desrciption 根据主键删除医保个人业务申请信息
     * @param id 主键id
     * @Author Ou·Mr
     * @Date 2020/11/19 20:30 
     * @Return int 受影响行数
     */
    int deleteById(@Param("id") String id);

    /**
     * @Menthod deleteByIds
     * @Desrciption 根据ids批量删除医保个人业务申请信息
     * @param ids 参数
     * @Author Ou·Mr
     * @Date 2020/11/19 20:31 
     * @Return int 受影响行数
     */
    int deleteByIds(@Param("ids") String[] ids);

    /**
     * @Menthod findByCondition
     * @Desrciption 查询医保个人业务申请信息
     * @param insureIndividualBusinessDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/11/19 20:33 
     * @Return java.util.List<cn.hsa.module.insure.module.dto.InsureIndividualBusinessDTO>
     */
    List<InsureIndividualBusinessDTO> findByCondition(InsureIndividualBusinessDTO insureIndividualBusinessDTO);

    /**
     * @Menthod deleteByVisitId
     * @Desrciption 根据就诊id删除医保个人业务申请信息
     * @param visitId 就诊id
     * @Author Ou·Mr
     * @Date 2020/11/19 20:38 
     * @Return int 受影响行数
     */
    int deleteByVisitId(@Param("visitId") String visitId);
}
