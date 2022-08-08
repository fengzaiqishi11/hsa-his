package cn.hsa.module.phar.pharindistributedrug.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.phar.pharindistributedrug.dto.PharInReceiveDTO;
import cn.hsa.module.phar.pharindistributedrug.dto.PharInReceiveDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.module.phar.pharoutdistributedrug.service
* @class_name: OutDistributeDrugService
* @Description: 住院发药service
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/9/2 15:48
* @Company: 创智和宇
**/
@FeignClient(value = "hsa-stro")
public interface InDistributeDrugService {

    /**
     * @Method: getInRecivePage
     * @Description: 住院发药--申请记录
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/10 14:24
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/stro/inDistributeDrug/getInRecivePage")
    WrapperResponse<PageDTO> getInRecivePage(Map map);

    /**
     * @Method: getSendInRecivePage
     * @Description: 住院发药记录
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/26 15:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/stro/inDistributeDrug/getSendInRecivePage")
    WrapperResponse<PageDTO> getSendInRecivePage(Map map);

    /**
     * @Method: getInReviceDetailPage
     * @Description: 住院发药--取药明细单
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/10 14:53
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/stro/inDistributeDrug/getInReviceDetailPage")
    WrapperResponse<PageDTO> getInReviceDetailPage(Map map);

    /**
    * @Menthod getInReviceDetail
    * @Desrciption
     * @param map
    * @Author xingyu.xie
    * @Date   2020/12/25 11:43
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.util.List<cn.hsa.module.phar.pharindistributedrug.dto.PharInReceiveDetailDTO>>>
    **/
    @GetMapping("/service/stro/inDistributeDrug/getInReviceDetail")
    WrapperResponse<Map<String, List<PharInReceiveDetailDTO>>> getInReviceDetail(Map map);

    /**
     * @Method: getInSumReviceDetailPage
     * @Description: 住院发药--取药合计单
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/10 14:53
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/stro/inDistributeDrug/getInSumReviceDetailPage")
    WrapperResponse<PageDTO> getInSumReviceDetailPage(Map map);

    /**
     * @Method: inDispense
     * @Description: 住院配药
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/10 15:51
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/stro/inDistributeDrug/inDispense")
    WrapperResponse<Boolean> inDispense(Map map);

    /**
     * @Method: cancelInDispense
     * @Description: 取消配药
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/26 15:03
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/stro/inDistributeDrug/cancelInDispense")
    WrapperResponse<Boolean> cancelInDispense(Map map);

    /**
     * @Method: inDistribute
     * @Description: 住院发药
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/10 15:52
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/stro/inDistributeDrug/inDistribute")
    WrapperResponse<Boolean> inDistribute(Map map);

    /**
    * @Menthod queryPatientB
    * @Desrciption 根据配药单号查询患者
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/4/22 19:12
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
    **/
    WrapperResponse<List<InptVisitDTO>> queryPatientByOrder(Map map);

    /**
    * @Menthod queryDrugByOrderAndVisitId
    * @Desrciption 根据配药单号,就诊id查询病人配药
    *
    * @Param
    * [pharInReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/4/22 19:12
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.phar.pharindistributedrug.dto.PharInReceiveDTO>>
    **/
    WrapperResponse<List<InptAdviceDTO>> queryDrugByOrderAndVisitId(Map map);

    /**
    * @Menthod updatePremedication
    * @Desrciption 选择性取消getPatientByVisitID
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/7/13 10:16
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/service/stro/inDistributeDrug/updatePremedication")
    WrapperResponse<Boolean> updatePremedication(Map map);
    /**
     * @Menthod: queryDMDrugByOrderAndVisitId
     * @Desrciption: 根据就诊id查询精麻处方
     * @Param: inptVisitDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022-08-03 19:31
     * @Return:
     **/
    WrapperResponse<List<InptAdviceDTO>> queryDMDrugByOrderAndVisitId(Map map);
}
