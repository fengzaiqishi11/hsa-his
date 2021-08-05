package cn.hsa.module.inpt.fees.dao;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.fees.dto.InptCostSettleDTO;
import cn.hsa.module.inpt.fees.entity.InptCostSettleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.fees.dao
 * @Class_name: InptCostSettleDAO
 * @Describe(描述): 住院结算费用明细DAO
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/09/24 19:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InptCostSettleDAO {

    /**
     * @Menthod selectByPrimaryKey
     * @Desrciption 根据主键查询住院结算费用明细
     * @param id 主键
     * @Author Ou·Mr
     * @Date 2020/9/24 20:27 
     * @Return cn.hsa.module.inpt.doctor.entity.InptCostDO
     */
    InptCostSettleDO selectByPrimaryKey(@Param("id") String id);
    
    /**
     * @Menthod insert
     * @Desrciption 保存住院结算费用明细
     * @param inptCostSettleDO 保存参数
     * @Author Ou·Mr
     * @Date 2020/9/24 20:32 
     * @Return int 受影响行数
     */
    int insert(InptCostSettleDO inptCostSettleDO);

    /**
     * @Menthod insertSelective
     * @Desrciption  保存住院结算费用明细 字段为null不做保存
     * @param inptCostSettleDO 保存参数
     * @Author Ou·Mr
     * @Date 2020/9/24 20:34 
     * @Return int 受影响的行数
     */
    int insertSelective(InptCostSettleDO inptCostSettleDO);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption  修改住院结算费用明细
     * @param inptCostSettleDO 修改参数
     * @Author Ou·Mr
     * @Date 2020/9/24 20:36 
     * @Return int 受影响行数
     */
    int updateByPrimaryKey(InptCostSettleDO inptCostSettleDO);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption 修改住院结算费用明细 字段为null不做保存
     * @param inptCostSettleDO 修改参数
     * @Author Ou·Mr
     * @Date 2020/9/24 20:37
     * @Return int 受影响行数
     */
    int updateByPrimaryKeySelective(InptCostSettleDO inptCostSettleDO);

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
     * @param inptCostSettleDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/9/24 20:54 
     * @Return java.util.List<cn.hsa.module.inpt.fees.dto.InptCostSettleDTO>
     */
    List<InptCostSettleDTO> findByCondition(InptCostSettleDTO inptCostSettleDTO);

    /**
     * @Menthod batchInsert
     * @Desrciption 批量增加
     * @param inptCostSettleDOList 参数
     * @Author Ou·Mr
     * @Date 2020/10/23 11:48 
     * @Return int 受影响行数
     */
    int batchInsert(List<InptCostSettleDO> inptCostSettleDOList);

    Boolean updateInsureSettleCost(Map<String, Object> settleMap);

    /**
     * @Description: 查询医保中途结算最大次数
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/7/29 16:05
     * @Return
     */
    Map<String, Object> selectMidWaySettleMaxTimes(Map<String, Object> selectMap);
}
