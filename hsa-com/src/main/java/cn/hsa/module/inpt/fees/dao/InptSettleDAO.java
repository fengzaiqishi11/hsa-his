package cn.hsa.module.inpt.fees.dao;

import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.fees.dto.InptSettleDTO;
import cn.hsa.module.inpt.fees.entity.InptSettleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.fees.dao
 * @Class_name: InptSettleDAO
 * @Describe(描述): 住院结算DAO
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/09/24 19:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InptSettleDAO {

    /**
     * @Menthod selectByPrimaryKey
     * @Desrciption 根据主键查询住院结算费用明细
     * @param id 主键
     * @Author Ou·Mr
     * @Date 2020/9/24 20:27
     * @Return cn.hsa.module.inpt.doctor.entity.InptSettleDO
     */
    InptSettleDO selectByPrimaryKey(@Param("id") String id);

    /**
     * @Menthod insert
     * @Desrciption 保存住院结算费用明细
     * @param inptSettleDO 保存参数
     * @Author Ou·Mr
     * @Date 2020/9/24 20:32
     * @Return int 受影响行数
     */
    int insert(InptSettleDO inptSettleDO);

    /**
     * @Menthod insertSelective
     * @Desrciption  保存住院结算费用明细 字段为null不做保存
     * @param inptSettleDO 保存参数
     * @Author Ou·Mr
     * @Date 2020/9/24 20:34
     * @Return int 受影响的行数
     */
    int insertSelective(InptSettleDO inptSettleDO);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption  修改住院结算费用明细
     * @param inptSettleDO 修改参数
     * @Author Ou·Mr
     * @Date 2020/9/24 20:36
     * @Return int 受影响行数
     */
    int updateByPrimaryKey(InptSettleDO inptSettleDO);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption 修改住院结算费用明细 字段为null不做保存
     * @param inptSettleDO 修改参数
     * @Author Ou·Mr
     * @Date 2020/9/24 20:37
     * @Return int 受影响行数
     */
    int updateByPrimaryKeySelective(InptSettleDO inptSettleDO);

    /**
     * @Menthod deleteById
     * @Desrciption 根据id删除住院结算费用明细
     * @param id 主键id
     * @Author Ou·Mr
     * @Date 2020/9/24 20:38
     * @Return int 受影响行数
     */
    int deleteById(@Param("id") String id);

    /**
     * @Menthod deleteByIds
     * @Desrciption 根据ids批量删除住院结算费用明细
     * @param ids 主键ids
     * @Author Ou·Mr
     * @Date 2020/9/24 20:53
     * @Return int 受影响的行数
     */
    int deleteByIds(@Param("ids") String [] ids);

    /**
     * @Menthod findByCondition
     * @Desrciption  查询院结算费用明细
     * @param inptSettleDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/9/24 20:54
     * @Return java.util.List<cn.hsa.module.inpt.fees.dto.InptCostSettleDTO>
     */
    List<InptSettleDTO> findByCondition(InptSettleDTO inptSettleDTO);

    /**
     * @Menthod deleteByVisitId
     * @Desrciption 根据就诊id、是否结算、状态来删除试算的脏数据
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/28 11:14 
     * @Return int 受影响的行数
     */
    int deleteByVisitId(Map param);
    
    /**
     * @Menthod queryInptCostList
     * @Desrciption 查询住院费用信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/9/28 15:03 
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>
     */
    List<InptCostDTO> queryInptCostList(Map param);

    /**
     * @Menthod queryInptSettleIsVisit
     * @Desrciption 查询结算信息
     * @param inptSettleDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/10/12 15:25
     * @Return java.util.List<cn.hsa.module.inpt.fees.dto.InptSettleDTO>
     */
    List<InptSettleDTO> queryInptSettleIsVisit(InptSettleDTO inptSettleDTO);

    /**
    * @Method: querySettle
    * @Description: 获取住院发票数据
    * @Param: settleId
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/12/8 15:30
    * @Return: List<Map<String, Object>>
    **/
    List<Map<String, Object>> querySettle(String settleId,String hospCode);

    /**
     * @Menthod: queryDiagnose
     * @Desrciption: 根据就诊id查询对应的诊断列表
     * @Param: inptVisitDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-03-04 19:02
     * @Return: PageDTO
     **/
    List<InptDiagnoseDTO> queryDiagnose(InptVisitDTO inptVisitDTO);
}
