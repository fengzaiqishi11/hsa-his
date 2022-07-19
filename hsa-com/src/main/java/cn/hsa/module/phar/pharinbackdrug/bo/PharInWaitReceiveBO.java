package cn.hsa.module.phar.pharinbackdrug.bo;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDetailDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO;

import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.phar.pharinbackdrug.bo
 *@Class_name: PharInWaitReceiveBO
 *@Describe: 住院待领
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/3 17:24
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface PharInWaitReceiveBO {

    /**
    * @Method insertPharInWaitBatch
    * @Desrciption 住院待领批量新增
    * @param pharInWaitReceiveDTOs
    * @Author liuqi1
    * @Date   2020/9/3 17:27
    * @Return int
    **/
    Boolean insertPharInWaitBatch(List<PharInWaitReceiveDTO> pharInWaitReceiveDTOs);

    /**
    * @Method queryPharInWaitReceive
    * @Desrciption 根据条件查询出待领信息集合
    * @param pharInWaitReceiveDTO
    * @Author liuqi1
    * @Date   2020/9/7 13:45
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>
    **/
    List<PharInWaitReceiveDTO> queryPharInWaitReceive(PharInWaitReceiveDTO pharInWaitReceiveDTO);
    List<PharInWaitReceiveDTO> queryPharInWaitReceives(PharInWaitReceiveDTO pharInWaitReceiveDTO);
    List<PharInWaitReceiveDTO> queryPharInWaitReceiveToIsBack(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
    * @Menthod queryPharInWaitReceivePage
    * @Desrciption 根据条件分页查询出待领信息集合
    *
    * @Param
    * [pharInWaitReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/9/8 16:54
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryPharInWaitReceivePage(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
     * @Method updateInWaitStatus
     * @Desrciption 封信待领表状态
       @params [pharInWaitReceiveDTO]
     * @Author chenjun
     * @Date   2020/10/16 15:14
     * @Return java.lang.Boolean
    **/
    Boolean updateInWaitStatus(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
    * @Menthod updateInWaitStatusByWrIds
    * @Desrciption  修改待领表状态
    *
    * @Param
    * [pharInWaitReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/6 16:22
    * @Return java.lang.Boolean
    **/
    Boolean updateInWaitStatusByWrIds(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
    * @Menthod updateInWaitStatus
    * @Desrciption 批量修改为紧急领药
    *
    * @Param
    * [pharInWaitReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/1/22 11:05
    * @Return java.lang.Boolean
    **/
    Boolean updateUrgentMedicine(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
     * @Method queryPharInWaitReceiveApply
     * @Desrciption 查询待领药品
       @params [pharInWaitReceiveDTO]
     * @Author chenjun
     * @Date   2020/10/16 15:14
     * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>
    **/
    List<PharInWaitReceiveDTO> queryPharInWaitReceiveApply(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
     * @Method deletePharInReceive
     * @Desrciption 删除领药单表
       @params [pharInReceiveDTO]
     * @Author chenjun
     * @Date   2020/10/16 15:13
     * @Return java.lang.Boolean
    **/
    Boolean deletePharInReceive(PharInReceiveDTO pharInReceiveDTO);

    /**
     * @Method deletePharInReceiveDetail
     * @Desrciption 删除领药单明细表
       @params [pharInReceiveDetailDTO]
     * @Author chenjun
     * @Date   2020/10/16 15:14
     * @Return java.lang.Boolean
    **/
    Boolean deletePharInReceiveDetail(PharInReceiveDetailDTO pharInReceiveDetailDTO);

    /**
    * @Method updateCostIdBatch
    * @Desrciption 批量更新待领表的费用明细id
    * @param inptCostDTOs
    * @Author liuqi1
    * @Date   2020/11/9 20:27
    * @Return java.lang.Boolean
    **/
    Boolean updateCostIdBatch(List<InptCostDTO> inptCostDTOs);


    List<Map<String, Object>> queryAllVisit(PharInWaitReceiveDTO pharInWaitReceiveDTO);
}
