package cn.hsa.module.inpt.longcost.dao;

import cn.hsa.module.inpt.bedlist.dto.InptLongCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.longcost.dao
 * @Class_name: bedLongCostDAO
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/10/20 10:23
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BedLongCostDAO {

    List<InptLongCostDTO> queryBedLongCost(Map map);

    int update( @Param("list") List<String> idList, @Param("lastExecTime") Date lastExecTime);

    boolean updateVisitTotalMoney(@Param("list") List<InptVisitDTO> list);

    @MapKey("visit_id")
    Map<String, Map<String, Object>> queryTotalInptAdvancePay(@Param("hospCode") String hospCode, @Param("list") List<String> list);

    @MapKey("visit_id")
    Map<String, Map<String, Object>> queryTotalInptCost(@Param("hospCode") String hospCode, @Param("list") List<String> list);

    /**
     * @param inptCostDTO
     * @Method queryBackCostWithAddAccountPage
     * @Desrciption 根据visitId and soureTypeCode 查询这个病人录入的长期费用补记账
     * @Author pengbo
     * @Date 2021/5/24 14:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    List<InptLongCostDTO> queryLongCostByVistIdAndSoureTypeCode(InptCostDTO inptCostDTO);


    /**
     * @Method insertLongCost
     * @Desrciption 批量插入长期费用表
     * @params [list]
     * @Author chenjun
     * @Date 2020/9/10 9:20
     * @Return void
     **/
    boolean insertLongCost(List<InptLongCostDTO> list);
    /**
     * @Method insertLongCost
     * @Desrciption 批量插入长期费用表
     * @params [list]
     * @Author chenjun
     * @Date 2020/9/10 9:20
     * @Return void
     **/
    int updateAccountRepairLong(InptLongCostDTO updateLongCost);
}
