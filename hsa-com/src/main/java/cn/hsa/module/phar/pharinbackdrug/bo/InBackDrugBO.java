package cn.hsa.module.phar.pharinbackdrug.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDetailDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO;

import java.util.List;

/**
 *@Package_name: cn.hsa.module.phar.pharinbackdrug.bo
 *@Class_name: InBackDrugBO
 *@Describe: 住院退药
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/21 15:58
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InBackDrugBO {

    /**
    * @Method returnDrugBeHospitalized
    * @Desrciption 住院退药
    * @param pharInWaitReceiveDTO
    * @Author liuqi1
    * @Date   2020/8/25 10:39
    * @Return int
    **/
    Boolean updateInHospitalBackDrug(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
     * @Method queryWaitReceiveGroupByDeptId
     * @Desrciption 按申请科室分组查询出待退药的信息
     * @param pharInWaitReceiveDTO
     * @Author liuqi1
     * @Date   2020/8/28 15:24
     * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>
     **/
    List<PharInWaitReceiveDTO> queryWaitReceiveGroupByDeptId(PharInWaitReceiveDTO pharInWaitReceiveDTO);


    /**
     * @Method queryWaitReceiveByDeptId
     * @Desrciption 按项目id分组查询出科室待退药的信息
     * @param pharInWaitReceiveDTO
     * @Author liuqi1
     * @Date   2020/8/28 15:24
     * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>
     **/
    PageDTO queryWaitReceiveGroupByItemIdPage(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
     * @Method queryWaitReceiveByDeptId
     * @Desrciption 查询出申请科室的退药明细
     * @param pharInWaitReceiveDTO
     * @Author liuqi1
     * @Date   2020/8/28 15:24
     * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>
     **/
    PageDTO queryWaitReceiveByDeptIdPage(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
     * @Method: getWaitReceiveByCost
     * @Description: 根据相关参数查询领药申请记录
     * @Param: [pharInWaitReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/18 14:03
     * @Return: cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO
     **/
    PharInWaitReceiveDTO getWaitReceiveByCost(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
     * @Method getPharInReceiveList
     * @Desrciption 查询领药单列表
       @params [pharInReceiveDTO]
     * @Author chenjun
     * @Date   2020/9/24 10:42
     * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDTO>
    **/
    List<PharInReceiveDTO> getPharInReceiveList(PharInReceiveDTO pharInReceiveDTO);

    /**
     * @Method getPharInReceiveList
     * @Desrciption 查询领药单明细列表
     @params [pharInReceiveDTO]
      * @Author chenjun
     * @Date   2020/9/24 10:42
     * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDTO>
     **/
    List<PharInReceiveDetailDTO> getPharInReceiveDetailList(PharInReceiveDetailDTO pharInReceiveDetailDTO);

    /**
     * @Method queryAll
     * @Desrciption 查询列表
       @params [pharInReceiveDTO]
     * @Author chenjun
     * @Date   2020/10/10 11:14
     * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDTO>
    **/
    List<PharInReceiveDTO> queryAll(PharInReceiveDTO pharInReceiveDTO);

    /**未退药查询
    * @Method queryBackDrugPage
    * @Desrciption
    * @param pharInWaitReceiveDTO
    * @Author liuqi1
    * @Date   2021/4/23 11:01
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryBackDrugPage(PharInWaitReceiveDTO pharInWaitReceiveDTO);

}
