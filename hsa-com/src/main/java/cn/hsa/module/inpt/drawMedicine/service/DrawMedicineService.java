package cn.hsa.module.inpt.drawMedicine.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.drawMedicine.server
 * @Class_name: DrawMedicineServer
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/9/11 16:42
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-inpt")
public interface DrawMedicineService {

    /**
     * @Method beforehandDrawMedicine
     * @Desrciption 预配药
       @params [map]
     * @Author chenjun
     * @Date   2020-12-17 15:23
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @GetMapping("/service/inpt/drawMedicine/saveBeforehandDrawMedicine")
    WrapperResponse<Boolean> saveBeforehandDrawMedicine(Map map);

    /**
    * @Menthod checkDrawMedicineStock
    * @Desrciption 预配药的时候校验库存
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/9/7 10:36
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<PharInWaitReceiveDTO>
    **/
    @GetMapping("/service/inpt/drawMedicine/checkDrawMedicineStock")
    WrapperResponse<PharInWaitReceiveDTO> checkDrawMedicineStock(Map map);

    /**
     * @Method getApplyDetailsList
     * @Desrciption 查询领药明细数据
       @params [map]
     * @Author chenjun
     * @Date   2020-12-17 15:23
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/service/inpt/drawMedicine/getApplyDetailsList")
    WrapperResponse<PageDTO> getApplyDetailsList(Map map);

    /**
     * @Method getApplySummaryList
     * @Desrciption 查询领药汇总信息
       @params [map]
     * @Author chenjun
     * @Date   2020-12-17 15:24
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/service/inpt/drawMedicine/getApplySummaryList")
    WrapperResponse<PageDTO> getApplySummaryList(Map map);

    /**
     * @Method getPharInReceiveList
     * @Desrciption 查询领药单集合
       @params [map]
     * @Author chenjun
     * @Date   2020-12-17 15:24
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/service/inpt/drawMedicine/getPharInReceiveList")
    WrapperResponse<PageDTO> getPharInReceiveList(Map map);

    /**
     * @Method getPharInReceiveListDetail
     * @Desrciption 查询领药单明细集合
       @params [map]
     * @Author chenjun
     * @Date   2020-12-17 15:24
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/service/inpt/drawMedicine/getPharInReceiveListDetail")
    WrapperResponse<PageDTO> getPharInReceiveListDetail(Map map);

    /**
     * @Method cancelDrawMedicine
     * @Desrciption 取消配药
       @params [map]
     * @Author chenjun
     * @Date   2020-12-17 15:25
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @GetMapping("/service/inpt/drawMedicine/cancelDrawMedicine")
    WrapperResponse<Boolean> cancelDrawMedicine(Map map);

    /**
    * @Menthod updateUrgentMedicine
    * @Desrciption 修改状态为紧急领药
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/1/22 10:57
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/service/inpt/drawMedicine/updateUrgentMedicine")
    WrapperResponse<Boolean> updateUrgentMedicine(Map map);

    /**
     * @Method saveAdvanceTakeMedicine
     * @Desrciption 提前领药
     * @params map
     * @Author pengbo
     * @Date   2021-05-12 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/inpt/drawMedicine/saveAdvanceTakeMedicine")
    WrapperResponse<Boolean> saveAdvanceTakeMedicine(Map<String, Object> map);
    /**
     * @Method getTableTqlyData
     * @Desrciption 查询提前领药
     * @params map
     * @Author pengbo
     * @Date   2021-05-12 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    WrapperResponse<List<Map<String, Object>>> getTableTqlyData(Map<String, Object> map);

    /**
     * @Method updateAdvance
     * @Desrciption 取消提前领药
     * @params map
     * @Author pengbo
     * @Date   2021-05-12 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    WrapperResponse<Boolean> updateAdvance(Map<String, Object> map);

    WrapperResponse<List<Map<String, Object>>> queryAllVisit(Map<String, Object> map);
}
