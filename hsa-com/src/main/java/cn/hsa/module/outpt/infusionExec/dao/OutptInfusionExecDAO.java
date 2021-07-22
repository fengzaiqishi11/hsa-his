package cn.hsa.module.outpt.infusionExec.dao;

import cn.hsa.module.outpt.prescribeExec.dto.OutptPrescribeExecDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.infusionExec.dao
 * @Class_name:: OutptInfusionExecDAO
 * @Description: 门诊输液执行Dao层
 * @Author: luoyong
 * @Email: luoyong@powersi.com.cn
 * @Date: 2020/8/13 15:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptInfusionExecDAO {

    /**
     * @Menthod: queryPage()
     * @Desrciption: 根据条件分页查询输液列表
     * @Param: map (outptPrescribeDetailsDTO-门诊处方明细DTO，usageCodeList-用药方式List)
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: List<Map < String, Object>>
     **/
    List<OutptPrescribeExecDTO> queryPage(Map map);

    List<OutptPrescribeExecDTO> queryExec(Map map);

    /**
     * @Menthod: updatePreExec()
     * @Desrciption: 批量输液执行---更新《处方执行表》执行信息
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: int
     **/
    int updatePreExec(Map map);

    /**
     * @Menthod: updatePreDet()
     * @Desrciption: 批量输液执行---更新《处方明细表》执行信息
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: int
     **/
    int updatePreDet(Map map);

    /**
     * @Menthod: updateInfuReg()
     * @Desrciption: 批量输液执行---更新《输液登记表》执行信息
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: int
     **/
    int updateInfuReg(Map map);

    /**
     * @Menthod: updateByExec()
     * @Desrciption: 批量取消输液执行
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: int
     **/
    int removeBatchExec(Map map);

    void updatePreDetBatch(@Param("list1") List<OutptPrescribeExecDTO> execDTOS);

    void updatePreExecBatch(@Param("list2") List<OutptPrescribeExecDTO> execDTOS);

    void updateInfuRegBatch(@Param("list3") List<OutptPrescribeExecDTO> execDTOS);

    void deletePreDetBatch(@Param("list4") List<OutptPrescribeExecDTO> cancelList);

    void deletePreExecBatch(@Param("list5") List<OutptPrescribeExecDTO> cancelList);

    void deleteInfuRegBatch(@Param("list6") List<OutptPrescribeExecDTO> cancelList);
}
