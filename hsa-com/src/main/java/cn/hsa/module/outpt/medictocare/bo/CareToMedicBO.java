package cn.hsa.module.outpt.medictocare.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.outpt.medictocare.dto.MedicToCareDTO;

/**
 * @author powersi
 * @create 2022-02-28 10:29
 * @desc
 **/
public interface CareToMedicBO {
    /**
     * @Menthod: queryPage()
     * @Desrciption: 根据条件分页查询养转医患者列表
     * @Param: medicToCareDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @date 2022/2/28 9:38
     * @Return: cn.hsa.base.PageDTO
     **/
    PageDTO queryPage(MedicToCareDTO medicToCareDTO);


    /**
     * @Menthod getMedicToCareInfoById()
     * @Desrciption   根据主键id查询申请明细详细信息
     * @Param medicToCareDTO
     * @Author yuelong.chen
     * @Date   2022/2/28 9:38
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<OutinInvoiceDO>
     **/
    MedicToCareDTO getCareToMedicInfoById(MedicToCareDTO medicToCareDTO);

    /**
     * @Menthod: insertMedicToCare()
     * @Desrciption: 养转医申请完后更新
     * @Param: medicToCareDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022/2/28 9:38
     * @Return: Boolean
     **/
    Boolean updateCareToMedic(MedicToCareDTO medicToCareDTO);
}
