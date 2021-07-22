package cn.hsa.module.oper.operInforecord.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.oper.operInforecord.dto.OperAccountTempDTO;
import cn.hsa.module.oper.operInforecord.dto.OperAccountTempDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.oper.operInforecord.service
 *@Class_name: OperAccountTempService
 *@Describe: 手术补记账模板service
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/12/5 10:30
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-oper")
public interface OperAccountTempService {

    /**
     * @Method queryOperAccountTempDTOPage
     * @Desrciption 手术模板分页查询
     * @param map
     * @Author liuqi1
     * @Date   2020/12/5 10:35
     * @Return cn.hsa.base.PageDTO
     **/
    @PostMapping("/service/oper/operAccountTemp/queryOperAccountTempDTOPage")
    WrapperResponse<PageDTO> queryOperAccountTempDTOPage(Map<String,Object> map);

    /**
     * @Method queryOperAccountTempDetailDTOPage
     * @Desrciption 手术模板明细分页查询
     * @param map
     * @Author liuqi1
     * @Date   2020/12/5 10:37
     * @Return cn.hsa.base.PageDTO
     **/
    @PostMapping("/service/oper/operAccountTemp/queryOperAccountTempDetailDTOPage")
    WrapperResponse<PageDTO> queryOperAccountTempDetailDTOPage(Map<String,Object> map);


    /**
     * @Method queryOperAccountTempDetailDTOPage
     * @Desrciption 手术模板明细分页查询
     * @param map
     * @Author liuqi1
     * @Date   2020/12/5 10:37
     * @Return cn.hsa.base.PageDTO
     **/
    @PostMapping("/service/oper/operAccountTemp/queryOperAccountTempDetailDTO")
    WrapperResponse<List<OperAccountTempDetailDTO>> queryOperAccountTempDetailDTO(Map<String,Object> map);

    /**
     * @Method insertOperAccountTempDTO
     * @Desrciption 手术模板、明细新增
     * @param map
     * @Author liuqi1
     * @Date   2020/12/5 10:45
     * @Return java.lang.Boolean
     **/
    @PostMapping("/service/oper/operAccountTemp/insertOperAccountTempDTO")
    WrapperResponse<Boolean> insertOperAccountTempDTO(Map<String,Object> map);

    /**
    * @Menthod deleteOperAccountTempDTOById
    * @Desrciption  根据模版id删除模版和明细
     * @param map
    * @Author xingyu.xie
    * @Date   2021/1/22 11:22 
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/service/oper/operAccountTemp/deleteOperAccountTempDTOById")
    WrapperResponse<Boolean> deleteOperAccountTempDTOById(Map<String,Object> map);

    /**
    * @Menthod deleteOperAccountTempDetailById
    * @Desrciption  保存模版内容和模版明细内容
     * @param map
    * @Author xingyu.xie
    * @Date   2021/1/22 11:22 
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/service/oper/operAccountTemp/saveOperAccountTemp")
    WrapperResponse<Boolean> saveOperAccountTemp(Map<String,Object> map);
}
