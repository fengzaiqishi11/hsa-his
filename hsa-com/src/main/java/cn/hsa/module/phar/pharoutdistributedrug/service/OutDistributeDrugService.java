package cn.hsa.module.phar.pharoutdistributedrug.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.phar.pharoutdistributedrug.dto.PharOutReceiveDTO;
import cn.hsa.module.phar.pharoutdistributedrug.dto.PharOutReceiveDetailDTO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.module.phar.pharoutdistributedrug.service
* @class_name: OutDistributeDrugService
* @Description: 门诊发药service
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/9/2 15:48
* @Company: 创智和宇
**/
@FeignClient(value = "hsa-stro")
public interface OutDistributeDrugService {

    /**
     * @Method: getOutRecivePage
     * @Description: 获取待领列表
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/2 17:33
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/stro/outDistributeDrug/getOutRecivePage")
    WrapperResponse<PageDTO> getOutRecivePage(Map map);

    /**
    * @Menthod queryOutDistributedByIds
    * @Desrciption   根据ids查询所有的配药单数据
     * @param map
    * @Author xingyu.xie
    * @Date   2020/10/28 21:48
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @PostMapping("/service/stro/outDistributeDrug/queryOutDistributedByIds")
    WrapperResponse<List<PharOutReceiveDetailDTO>> queryOutDistributedByIds(Map map);

    /**
     * @Method: getOutDistributePage
     * @Description: 获取发药列表
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/12 16:28
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/stro/outDistributeDrug/getOutDistributePage")
    WrapperResponse<PageDTO> getOutDistributePage(Map map);

    /**
     * @Method: getOutReciveDetailPage
     * @Description: 获取待领明细列表
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/2 17:33
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/stro/outDistribute/getOutReciveDetailPage")
    WrapperResponse<PageDTO> getOutReciveDetailPage(Map map);

    /**
     * @Method: getOutDistributeDetailPage
     * @Description: 获取发药明细列表
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/12 16:42
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/stro/outDistribute/getOutDistributeDetailPage")
    WrapperResponse<PageDTO> getOutDistributeDetailPage(Map map);

    /**
     * @Method: dispense
     * @Description: 门诊配药
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/3 10:58
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/stro/outDistribute/dispense")
    WrapperResponse<PharOutReceiveDTO> outDispense(Map map);

    /**
     * @Method: outEnableDispense
     * @Description: 取消配药
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/12 14:53
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/stro/outDistribute/outEnableDispense")
    WrapperResponse<Boolean> outEnableDispense(Map map);

    /**
     * @Method: outDistribute
     * @Description: 门诊发药
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/3 11:15
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/stro/outDistribute/outDistribute")
    WrapperResponse<Boolean> outDistribute(Map map);

    /**
     * @Method: getOrderList
     * @Description: 获取所有处方列表
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/7 14:15
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
     **/
    @GetMapping("/service/stro/outDistribute/getOrderList")
    WrapperResponse<List<Map>> getOrderList(Map map);

    /**
     * @Method: getUserList
     * @Description: 获取配药人列表
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/12 15:48
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
     **/
    @GetMapping("/service/stro/outDistribute/getUserList")
    WrapperResponse<List<Map>> getUserList(Map map);

    /**
    * @Menthod queryPharOutDistributeAllDetailDTO
    * @Desrciption 查询门诊发药信息
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/5/27 11:43
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/service/stro/outDistribute/queryPharOutDistributeAllDetailDTO")
    WrapperResponse<PageDTO> queryPharOutDistributeAllDetailDTO(Map map);

    /**
     * @Menthod queryPharOutDistributeAllDetailDTO
     * @Desrciption 查询住院发药信息
     *
     * @Param
     * [map]
     *
     * @Author jiahong.yang
     * @Date   2021/5/27 11:43
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/stro/outDistribute/queryPharInDistributeAllDetailDTO")
    WrapperResponse<PageDTO> queryPharInDistributeAllDetailDTO(Map map);


    /**
    * @Menthod getPrescribePrint
    * @Desrciption 获取处方单打印
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/6/15 15:50
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
    **/
    @GetMapping("/service/stro/outDistribute/getPrescribePrint")
    WrapperResponse<List<OutptPrescribeDTO>> getPrescribePrint(Map map);
}
