package cn.hsa.module.interf.phys.dao;

import cn.hsa.module.interf.phys.dto.PhysRegisterDTO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PhysRegDAO {
    /**
     * @Method addVisitByPhys
     * @Desrciption 同步就诊信息
     * @Param [OutptVisitDTO]
     * @Author zhangguorui
     * @Date   2021/7/14 15:01
     * @Return int
     */
    int addVisitByPhys(OutptVisitDTO outptVisitDTO);
    /**
     * @Method addBatchPhys
     * @Desrciption 批量插入费用表
     * @Param [outptCostDTOS]
     * @Author zhangguorui
     * @Date   2021/7/14 17:19
     * @Return int
     */
    int addBatchPhys(@Param("outptCostDTOS") List<OutptCostDTO> outptCostDTOS);
}
