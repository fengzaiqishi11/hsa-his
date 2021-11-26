package cn.hsa.module.interf.phys.dao;

import cn.hsa.module.interf.phys.dto.PhysRegisterDTO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * @Description: 同步体检收费组合到项目表
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangxuan
     * @Date: 2021-11-25
     */
    int insertPhysGroup(@Param("list") List<Map> list);

    /**
     * @Description: 插入退费申请
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangxuan
     * @Date: 2021-11-25
     */
    void insertReturn(@Param("list") List<Map> list);

    /** 
    * @Description: 删除费用表信息
    * @Param: 
    * @return: 
    * @Author: zhangxuan
    * @Date: 2021-11-25
    */ 
    int deleteBatchPhys(OutptCostDTO outptCostDTO);

    void updateRCodt(@Param("list") List<Map> list);
}
