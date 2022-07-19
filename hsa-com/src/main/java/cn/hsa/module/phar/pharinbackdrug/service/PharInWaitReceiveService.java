package cn.hsa.module.phar.pharinbackdrug.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.phar.pharinbackdrug.service
 *@Class_name: PharInWaitReceiveService
 *@Describe: 住院待领
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/3 17:28
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-stro")
public interface PharInWaitReceiveService {

    /**
    * @Method insertPharInWaitBatch
    * @Desrciption 住院待领批量新增
    * @param map
    * @Author liuqi1
    * @Date   2020/9/3 17:30
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @GetMapping("/service/phar/pharInWait/insertPharInWaitBatch")
    WrapperResponse<Boolean> insertPharInWaitBatch(Map<String,Object> map);

    /**
     * @Method queryPharInWaitReceive
     * @Desrciption 根据条件查询出待领信息集合
     * @param map
     * @Author liuqi1
     * @Date   2020/9/7 13:45
     * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>
     **/
    WrapperResponse<List<PharInWaitReceiveDTO>> queryPharInWaitReceive(Map<String,Object> map);
    WrapperResponse<List<PharInWaitReceiveDTO>> queryPharInWaitReceives(Map<String,Object> map);

    @GetMapping("/service/phar/pharInWait/queryPharInWaitReceiveToIsBack")
    WrapperResponse<List<PharInWaitReceiveDTO>> queryPharInWaitReceiveToIsBack(Map<String,Object> map);

    /**
    * @Menthod queryPharInWaitReceivePage
    * @Desrciption 根据条件分页查询出待领信息集合
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/9/8 16:49
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    WrapperResponse<PageDTO> queryPharInWaitReceivePage(Map<String,Object> map);

    /**
     * @Method updateInWaitStatus
     * @Desrciption 批量修改请领状态
       @params [map]
     * @Author chenjun
     * @Date   2020/9/23 16:07
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    WrapperResponse<Boolean> updateInWaitStatus(Map map);

    /**
    * @Menthod updateInWaitStatusByWrIds
    * @Desrciption 批量修改请领状态
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/5/6 16:22
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    WrapperResponse<Boolean> updateInWaitStatusByWrIds(Map map);


  /**
    * @Menthod updateUrgentMedicine
    * @Desrciption 批量修改为紧急领药
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/1/22 11:04
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    WrapperResponse<Boolean> updateUrgentMedicine(Map map);

    /**
     * @Method queryPharInWaitReceiveApply
     * @Desrciption 科室领药查询待领药品
       @params [map]
     * @Author chenjun
     * @Date   2020/9/24 18:54
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>>
    **/
    @RequestMapping("/service/phar/pharInWait/queryPharInWaitReceiveApply")
    WrapperResponse<List<PharInWaitReceiveDTO>> queryPharInWaitReceiveApply(Map map);

    /**
     * @Method deletePharInReceive
     * @Desrciption 删除领药单表数据
       @params [map]
     * @Author chenjun
     * @Date   2020/10/16 15:08
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>>
    **/
    @RequestMapping("/service/phar/pharInWait/deletePharInReceive")
    WrapperResponse<Boolean> deletePharInReceive(Map map);

    /**
     * @Method deletePharInReceiveDetail
     * @Desrciption 删除领药单明细表数据
       @params [map]
     * @Author chenjun
     * @Date   2020/10/16 15:08
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>>
    **/
    @RequestMapping("/service/phar/pharInWait/deletePharInReceiveDetail")
    WrapperResponse<Boolean> deletePharInReceiveDetail(Map map);

    /**
    * @Method updateCostIdBatch
    * @Desrciption 批量更新待领表的费用明细id
    * @param map
    * @Author liuqi1
    * @Date   2020/11/9 20:30
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @RequestMapping("/service/phar/pharInWait/updateCostIdBatch")
    WrapperResponse<Boolean> updateCostIdBatch(Map map);

    WrapperResponse<List<Map<String, Object>>> queryAllVisit(Map<String, Object> map);
}
