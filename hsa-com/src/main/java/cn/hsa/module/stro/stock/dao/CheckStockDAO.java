package cn.hsa.module.stro.stock.dao;

import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.stro.stock.dto.CheckStockDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.stro.stock.dao
 * @Class_name: CheckStockDAO
 * @Describe: 校验库存dao
 * @Author: zhangguorui
 * @Eamil: guorui.zhang@powersi.com
 * @Date: 2021/8/9 19:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface CheckStockDAO {
    /**
     * @Method getStrockNumber
     * @Desrciption 获得库存、占存数量
     * @Param [CheckStockDTO checkStockDTO]
     * @Author zhangguorui
     * @Date   2021/8/10 9:59
     * @Return java.math.BigDecimal
     */
    Map<String,BigDecimal> getStrockNumber(CheckStockDTO checkStockDTO);
    /**
     * @Method getParameterValue
     * @Desrciption 获得系统参数
     * @Param [hospCode, code]
     * @Author zhangguorui
     * @Date   2021/8/10 13:48
     * @Return java.util.List<cn.hsa.module.sys.parameter.dto.SysParameterDTO>
     */
    List<SysParameterDTO> getParameterValue(@Param("hospCode") String hospCode, @Param("code") String[] code);
    /**
     * @Method getOuptCostTotalNumber
     * @Desrciption 获得未结算 药品数量
     * @Param [CheckStockDTO checkStockDTO]
     * @Author zhangguorui
     * @Date   2021/8/10 15:36
     * @Return java.math.BigDecimal
     */
    BigDecimal getOuptCostTotalNumber(CheckStockDTO checkStockDTO);
    /**
     * @Method getOuptCostNoPrescribeNumber
     * @Desrciption 获得已结算但未配药的数量
     * @Param [CheckStockDTO checkStockDTO]
     * @Author zhangguorui
     * @Date   2021/8/10 16:44
     * @Return java.math.BigDecimal
     */
    BigDecimal getOuptCostNoPrescribeNumber(CheckStockDTO checkStockDTO);
    /**
     * @Method getInptCostTotalNumber
     * @Desrciption 住院未核收数量
     * @Param [CheckStockDTO checkStockDTO]
     * @Author zhangguorui
     * @Date   2021/8/10 20:12
     * @Return java.math.BigDecimal
     */
    BigDecimal getInptCostTotalNumber(CheckStockDTO checkStockDTO);
    /**
     * @Method getInptCostNoPrescribeNumber
     * @Desrciption 住院已经核算，但没有核收数据
     * @Param [CheckStockDTO checkStockDTO]
     * @Author zhangguorui
     * @Date   2021/8/10 20:12
     * @Return java.math.BigDecimal
     */
    BigDecimal getInptCostNoPrescribeNumber(CheckStockDTO checkStockDTO);
}
