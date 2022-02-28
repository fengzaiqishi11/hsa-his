package cn.hsa.module.outpt.medictocare.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.medictocare.dto.MedicToCareDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @author powersi
 * @create 2022-02-28 15:24
 * @desc
 **/
@FeignClient(value = "hsa-outpt")
public interface CareToMedicApplyService {
    /**
     * @Menthod: queryPage()
     * @Desrciption: 根据条件分页查询养转医患者列表
     * @Param: medicToCareDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @date 2022/2/28 9:38
     * @Return: cn.hsa.base.PageDTO
     **/
    @PostMapping("/service/outpt/caretomedicapply/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);


    /**
     * @Menthod getMedicToCareInfoById()
     * @Desrciption   根据主键id查询申请明细详细信息
     * @Param medicToCareDTO
     * @Author yuelong.chen
     * @Date   2022/2/28 9:38
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<OutinInvoiceDO>
     **/
    @PostMapping("/service/outpt/caretomedicapply/getMedicToCareInfoById")
    WrapperResponse<MedicToCareDTO> getMedicToCareInfoById(Map map);

    /**
     * @Menthod: insertMedicToCare()
     * @Desrciption: 养转医申请完后更新
     * @Param: medicToCareDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022/2/28 9:38
     * @Return: Boolean
     **/
    @PostMapping("/service/outpt/caretomedicapply/updateMedicToCare")
    WrapperResponse<Boolean> updateMedicToCare(Map map);
}
