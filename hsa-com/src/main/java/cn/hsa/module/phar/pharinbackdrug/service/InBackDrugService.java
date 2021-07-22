package cn.hsa.module.phar.pharinbackdrug.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDetailDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.phar.pharinbackdrug.service
 *@Class_name: InBackDrugService
 *@Describe: 住院退药
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/21 15:58
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-stro")
public interface InBackDrugService {

    /**
    * @Method returnDrugBeHospitalized
    * @Desrciption 住院退药
    * @param map
    * @Author liuqi1
    * @Date   2020/8/25 10:29
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @GetMapping("/service/phar/pharinbackdrug/updaeInHospitalBackDrug")
    WrapperResponse<Boolean> updateInHospitalBackDrug(Map<String,Object> map);


    /**
     * @Method queryWaitReceiveGroupByDeptId
     * @Desrciption 按申请科室分组查询出待退药的信息
     * @param map
     * @Author liuqi1
     * @Date   2020/8/28 15:24
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>>
     **/
    @GetMapping("/service/phar/pharinbackdrug/queryWaitReceiveGroupByDeptId")
    WrapperResponse<List<PharInWaitReceiveDTO>> queryWaitReceiveGroupByDeptId(Map<String,Object> map);


    /**
     * @Method queryWaitReceiveByDeptId
     * @Desrciption 按项目id分组查询出科室待退药的信息
     * @param map
     * @Author liuqi1
     * @Date   2020/8/28 15:24
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>>
     **/
    @GetMapping("/service/phar/pharinbackdrug/queryWaitReceiveGroupByItemIdPage")
    WrapperResponse<PageDTO> queryWaitReceiveGroupByItemIdPage(Map<String,Object> map);

    /**
     * @Method queryWaitReceiveByDeptId
     * @Desrciption 查询出申请科室的退药明细
     * @param map
     * @Author liuqi1
     * @Date   2020/8/28 15:24
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>>
     **/
    @GetMapping("/service/phar/pharinbackdrug/queryWaitReceiveByDeptIdPage")
    WrapperResponse<PageDTO> queryWaitReceiveByDeptIdPage(Map<String,Object> map);

    /**
     * @Method: getWaitReceiveByCost
     * @Description: 根据相关参数查询领药申请记录
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/18 14:00
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>
     **/
    @GetMapping("/service/phar/pharinbackdrug/getWaitReceiveByCost")
    WrapperResponse<PharInWaitReceiveDTO> getWaitReceiveByCost(Map<String, Object> map);

    @GetMapping("/service/phar/pharinbackdrug/getPharInReceiveList")
    WrapperResponse<List<PharInReceiveDTO>> getPharInReceiveList(Map map);

    WrapperResponse<PageDTO> getPharInReceiveList1(Map map);

    @GetMapping("/service/phar/pharinbackdrug/getPharInReceiveListDetail")
    WrapperResponse<List<PharInReceiveDetailDTO>> getPharInReceiveDetailList(Map map);

    /**
    * @Menthod getPharInReceiveDetailList1
    * @Desrciption 返回分页
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/1/6 19:44
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/service/phar/pharinbackdrug/getPharInReceiveDetailList1")
    WrapperResponse<PageDTO> getPharInReceiveDetailList1(Map map);

    @GetMapping("/service/phar/pharinbackdrug/queryAll")
    WrapperResponse<List<PharInReceiveDTO>> queryAll(Map map);

    /**未退药查询
    * @Method queryBackDrugPage
    * @Desrciption
    * @param map
    * @Author liuqi1
    * @Date   2021/4/23 11:00
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/service/phar/pharinbackdrug/queryBackDrugPage")
    WrapperResponse<PageDTO> queryBackDrugPage(Map<String,Object> map);
}
