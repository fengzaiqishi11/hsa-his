package cn.hsa.module.outpt.infectious.dao;

import cn.hsa.module.outpt.infectious.dto.InfectiousDiseaseDto;
import cn.hsa.module.outpt.infectious.entity.InfectiousDiseaseDO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.outpt.infectious.dao
 * @Class_name:: OutptInfectiousDiseaDao
 * @Description: 传染病执行Dao层
 * @Author: liuliyun
 * @Date: 2021/04/21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptInfectiousDiseaDao {
    /**
     * @Menthod insertMedicalRecord
     * @Desrciption  新增传染病记录
     * @param infectiousDiseaseDO 传染病信息
     * @Author liuliyun
     * @Date   2021/4/21 09:16
     * @Return int
     **/
    int insertInfectiousRecord(InfectiousDiseaseDO infectiousDiseaseDO);
    /**
     * @Menthod updateInfectiousRecord
     * @Desrciption  修改传染病记录
     * @param infectiousDiseaseDO 传染病信息
     * @Author liuliyun
     * @Date   2021/4/21 09:16
     * @Return int
     **/
    int updateInfectiousRecord(InfectiousDiseaseDO infectiousDiseaseDO);
    /**
     * @Menthod findInfectiousById
     * @Desrciption  根据条件查询传染病记录
     * @param infectiousDiseaseDO 传染病信息
     * @Author liuliyun
     * @Date   2021/4/21 10:16
     * @Return  List<InfectiousDiseaseDO>
     **/
    List<InfectiousDiseaseDO> findInfectiousById(InfectiousDiseaseDO infectiousDiseaseDO);
    /**
     * @Menthod findInfectiousPage
     * @Desrciption  根据条件查询传染病记录
     * @param infectiousDiseaseDO 传染病信息
     * @Author liuliyun
     * @Date   2021/4/22 10:05
     * @Return  List<InfectiousDiseaseDO>
     **/
    List<InfectiousDiseaseDO> findInfectiousPage(InfectiousDiseaseDto infectiousDiseaseDO);

    List<InfectiousDiseaseDto> selectByPrimaryKeys(InfectiousDiseaseDto infectiousDiseaseDO);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption  审核数据
     * @param record 审核信息
     * @Author liuliyun
     * @Date   2021/04/25 17:52
     * @Return 受影响的行数
     */
    int updateByPrimaryKey(InfectiousDiseaseDto record);


}
