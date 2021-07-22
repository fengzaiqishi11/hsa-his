package cn.hsa.module.inpt.doctor.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptBabyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.doctor.service
 * @Class_name: InptBabyService
 * @Describe(描述): 住院婴儿service
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/12/01 9:50
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-inpt")
public interface InptBabyService {

    /**
     * @Menthod findByCondition
     * @Desrciption 根据查询条件查询住院婴儿信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/12/1 9:31
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptBabyDTO>
     */
    @GetMapping("/service/inpt/inptBaby/findByCondition")
    List<InptBabyDTO> findByCondition(Map<String,Object> param);

    /**
     * @Menthod: queryByVisitId
     * @Desrciption: 根据就诊id查询出对应的新生婴儿列表
     * @Param: inptVisitDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-19 15:25
     * @Return: PageDTO
     **/
    @GetMapping("/service/inpt/baby/queryByVisitId")
    WrapperResponse<PageDTO> queryByVisitId(Map map);

    /**
     * @Menthod: saveBaby
     * @Desrciption: 保存/编辑新生儿
     * @Param: inptBabyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-19 15:25
     * @Return: Boolean
     **/
    @PostMapping("/service/inpt/baby/saveBaby")
    WrapperResponse<Boolean> saveBaby(Map map);

    /**
     * @Menthod: getById
     * @Desrciption: 根据婴儿id查询婴儿信息
     * @Param: inptBabyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-19 15:25
     * @Return: InptBabyDTO
     **/
    @GetMapping("/service/inpt/baby/getById")
    WrapperResponse<InptBabyDTO> getById(Map map);
}
