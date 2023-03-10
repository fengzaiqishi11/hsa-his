package cn.hsa.module.inpt.inptnursethird.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.inptnursethird.dto.InptNurseThirdDTO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.sys.redis.bo.RedisBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.inptnursethird.service
 * @Class_name:: InptNurseThirdService
 * @Description:
 * @Author: ljh
 * @Date: 2020/9/16 10:50
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-inpt")
public interface InptNurseThirdService {


    /**
     * @Package_name: cn.hsa.module.inpt.inptnursethird.dao
     * @Class_name:: InptNurseThirdDao
     * @Description:
     * @Author: ljh
     * @Date: 2020/9/16 10:45
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    @PostMapping("/service/inpt/inptnursethird/queryAllRecordTime")
    WrapperResponse<Boolean> insertList(Map map);


    /**
     * @Package_name: cn.hsa.module.inpt.inptnursethird.dao
     * @Class_name:: InptNurseThirdDao
     * @Description:
     * @Author: ljh
     * @Date: 2020/9/16 10:45
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    @GetMapping("/service/inpt/inptnursethird/queryAll")
    WrapperResponse<List<InptNurseThirdDTO>> queryAll(Map map) throws Exception;


    /**
     * @Package_name: cn.hsa.module.inpt.inptnursethird.dao
     * @Class_name:: InptNurseThirdDao
     * @Description:
     * @Author: ljh
     * @Date: 2020/9/16 10:45
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    @GetMapping("/service/inpt/inptnursethird/queryAllRecordTime")
    WrapperResponse<List<InptNurseThirdDTO>> queryAllRecordTime(Map map) throws Exception;

    /**
    * @Method getPageList
    * @Param [map]
    * @description   ??????????????????
    * @author marong
    * @date 2020/10/29 10:21
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
    * @throws
    */
    @GetMapping("/service/inpt/inptnursethird/getPageList")
    WrapperResponse<List<Map<String, Object>>> getPageList(Map map) ;


    /**
    * @Method queryAllByTimeSlot
    * @Param [map]  ?????????????????????
    * @description
    * @author marong
    * @date 2020/10/29 9:53
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.inptnursethird.dto.InptNurseThirdDTO>>
    * @throws
    */
    @GetMapping("/service/inpt/inptnursethird/queryAllByTimeSlot")
    WrapperResponse<Map<String, Object>> queryAllByTimeSlot(Map map);

    /**
    * @Method saveInptNurseThird
    * @Param [map]
    * @description
    * @author marong
    * @date 2020/10/29 14:07
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    * @throws
    */
    @GetMapping("/service/inpt/inptnursethird/saveInptNurseThird")
    WrapperResponse<Boolean> saveInptNurseThird(Map map);

    /**
     * @Method queryOperByVisitId
     * @Param [OperInfoRecordDTO]
     * @description   ????????????visitId???????????????????????????
     * @author luoyong
     * @date 2020/10/29 9:53
     * @return List<OperInfoRecordDTO>
     * @throws
     */
    @GetMapping("/service/inpt/inptnursethird/queryOperByVisitId")
    WrapperResponse<List<OperInfoRecordDTO>> queryOperByVisitId(Map map);

    /**
     * @Menthod: queryInptThirdRecordByBatch
     * @Desrciption: ???????????????????????????????????????????????????????????????
     * @Param: inptNurseThirdDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-17 16:56
     * @Return: List<InptNurseThirdDTO>
     **/
    @GetMapping("/service/inpt/inptNursethird/queryInptThirdRecordByBatch")
    WrapperResponse<List<InptNurseThirdDTO>> queryInptThirdRecordByBatch(Map map);

    /**
     * @Menthod: saveBatch
     * @Desrciption: ?????????????????????
     * @Param: inptNurseThirdDTOS
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-19 15:59
     * @Return: boolean
     **/
    @PostMapping("/service/inpt/inptNursethird/saveBatch")
    WrapperResponse<Boolean> saveBatch(Map map);

    /**
     * @Menthod: queryAllByVisitId
     * @Desrciption: ????????????id??????????????????????????????????????????????????????
     * @Param: visitId
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-22 10:02
     * @Return:
     **/
    WrapperResponse<List<InptNurseThirdDTO>> queryAllByVisitId(Map<String, Object> map);
}
