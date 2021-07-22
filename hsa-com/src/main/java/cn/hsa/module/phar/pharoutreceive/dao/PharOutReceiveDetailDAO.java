package cn.hsa.module.phar.pharoutreceive.dao;

import cn.hsa.module.phar.pharoutreceive.dto.PharOutReceiveDetailDTO;
import cn.hsa.module.phar.pharoutreceive.entity.PharOutReceiveDetailDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.phar.pharoutreceive.dao
 * @Class_name: PharOutReceiveDetailDAO
 * @Describe(描述):门诊领药申请明细信息DAO层
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/09/16 15:18
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface PharOutReceiveDetailDAO {

    /**
     * @Menthod selectByPrimaryKey
     * @Desrciption  根据主键查询信息
     * @param id
     * @Author Ou·Mr
     * @Date 2020/9/16 15:13
     * @Return cn.hsa.module.phar.pharoutreceive.dto.PharOutReceiveDTO
     */
    PharOutReceiveDetailDTO selectByPrimaryKey(@Param("id") String id);

    /**
     * @Menthod insert
     * @Desrciption  新增门诊领药申请信息
     * @param pharOutReceiveDetailDO 参数
     * @Author Ou·Mr
     * @Date 2020/9/16 15:13
     * @Return int 受影响的行数
     */
    int insert(PharOutReceiveDetailDO pharOutReceiveDetailDO);

    /**
     * @Menthod insertSelective
     * @Desrciption   新增门诊领药申请信息
     * @param pharOutReceiveDetailDO 参数
     * @Author Ou·Mr
     * @Date 2020/9/16 15:14
     * @Return int 受影响的行数
     */
    int insertSelective(PharOutReceiveDetailDO pharOutReceiveDetailDO);


    /**
     * @Menthod batchInsert
     * @Desrciption  批量新增门诊领药申请信息
     * @param pharOutReceiveDetailDOList 参数
     * @Author Ou·Mr
     * @Date 2020/9/16 15:13
     * @Return int 受影响的行数
     */
    int batchInsert(List<PharOutReceiveDetailDO> pharOutReceiveDetailDOList);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption   编辑门诊领药申请信息
     * @param pharOutReceiveDetailDO 参数
     * @Author Ou·Mr
     * @Date 2020/9/16 15:14
     * @Return int 受影响的行数
     */
    int updateByPrimaryKey(PharOutReceiveDetailDO pharOutReceiveDetailDO);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption   编辑门诊领药申请信息
     * @param pharOutReceiveDetailDO 参数
     * @Author Ou·Mr
     * @Date 2020/9/16 15:15
     * @Return int 受影响的行数
     */
    int updateByPrimaryKeySelective(PharOutReceiveDetailDO pharOutReceiveDetailDO);

    /**
     * @Menthod deleteById
     * @Desrciption  删除门诊领药申请信息
     * @param id 主键
     * @Author Ou·Mr
     * @Date 2020/9/16 15:15
     * @Return int 受影响的行数
     */
    int deleteById(@Param("id") String id);

    /**
     * @Menthod deleteByIds
     * @Desrciption  批量删除门诊领药申请信息
     * @param ids 主键集合
     * @Author Ou·Mr
     * @Date 2020/9/16 15:16
     * @Return int 受影响的行数
     */
    int deleteByIds(@Param("ids") String [] ids);

    /**
     * @Menthod findByCondition
     * @Desrciption  查询门诊领药申请信息
     * @param pharOutReceiveDetailDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/9/16 15:17
     * @Return java.util.List<cn.hsa.module.phar.pharoutreceive.dto.PharOutReceiveDetailDTO>
     */
    List<PharOutReceiveDetailDTO> findByCondition(PharOutReceiveDetailDTO pharOutReceiveDetailDTO);
}
