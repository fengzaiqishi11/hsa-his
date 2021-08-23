package cn.hsa.inpt.inptnursethird.service.impl;

import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.inptnursethird.bo.InptNurseThirdBO;
import cn.hsa.module.inpt.inptnursethird.dto.InptNurseThirdDTO;
import cn.hsa.module.inpt.inptnursethird.service.InptNurseThirdService;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import sun.awt.windows.WWindowPeer;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.inptnursethird.service.impl
 * @Class_name:: InptNurseThirdServiceImpl
 * @Description:
 * @Author: ljh
 * @Date: 2020/9/16 13:43
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/inpt/inptNurseThird")
@Service("inptNurseThirdService_provider")
public class InptNurseThirdServiceImpl implements InptNurseThirdService {

    @Resource
    private InptNurseThirdBO inptNurseThirdBO;

    /**
     * @param map
     * @Package_name: cn.hsa.module.inpt.inptnursethird.dao
     * @Class_name:: InptNurseThirdDao
     * @Description:
     * @Author: ljh
     * @Date: 2020/9/16 10:45
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    @Override
    public WrapperResponse<Boolean> insertList(Map map) {
        return WrapperResponse.success(inptNurseThirdBO.insertList(MapUtils.get(map, "bean")) > 0);
    }

    /**
     * @param map
     * @Package_name: cn.hsa.module.inpt.inptnursethird.dao
     * @Class_name:: InptNurseThirdDao
     * @Description:
     * @Author: ljh
     * @Date: 2020/9/16 10:45
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    @Override
    public WrapperResponse<List<InptNurseThirdDTO>> queryAll(Map map) throws Exception {
        return WrapperResponse.success(inptNurseThirdBO.queryAll(MapUtils.get(map, "bean")));
    }

    /**
     * @param map
     * @Package_name: cn.hsa.module.inpt.inptnursethird.dao
     * @Class_name:: InptNurseThirdDao
     * @Description:
     * @Author: ljh
     * @Date: 2020/9/16 10:45
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    @Override
    public WrapperResponse<List<InptNurseThirdDTO>> queryAllRecordTime(Map map) throws Exception {
        return WrapperResponse.success(inptNurseThirdBO.queryAllRecordTime(MapUtils.get(map, "bean")));
    }

    /**
    * @Method getPageList
    * @Param [map]
    * @description   获取分页信息
    * @author marong
    * @date 2020/10/29 10:21
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
    * @throws
    */
    @Override
    public WrapperResponse<List<Map<String, Object>>> getPageList(Map map)  {
        return WrapperResponse.success(inptNurseThirdBO.getPageList(MapUtils.get(map, "bean")));
    }

    /**
    * @Method queryAllByTimeSlot
    * @Param [map]
    * @description   三测单查询
    * @author marong
    * @date 2020/10/29 9:53
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.inptnursethird.dto.InptNurseThirdDTO>>
    * @throws
    */
    @Override
    public WrapperResponse<Map<String, Object>> queryAllByTimeSlot(Map map) {
        return WrapperResponse.success(inptNurseThirdBO.queryAllByTimeSlot(MapUtils.get(map, "inptNurseThirdDTO")));
    }

    /**
    * @Method saveInptNurseThird
    * @Param [map] 
    * @description   
    * @author marong 
    * @date 2020/10/29 14:09
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>  
    * @throws   
    */
    @Override
    public WrapperResponse<Boolean> saveInptNurseThird(Map map) {
        return WrapperResponse.success(inptNurseThirdBO.saveInptNurseThird(MapUtils.get(map, "inptNurseThirdDTO")));
    }

    /**
     * @Method queryOperByVisitId
     * @Param [OperInfoRecordDTO]
     * @description   根据就诊visitId查询已完成手术列表
     * @author luoyong
     * @date 2020/10/29 9:53
     * @return List<OperInfoRecordDTO>
     * @throws
     */
    @Override
    public WrapperResponse<List<OperInfoRecordDTO>> queryOperByVisitId(Map map) {
        return WrapperResponse.success(inptNurseThirdBO.queryOperByVisitId(MapUtils.get(map, "operInfoRecordDTO")));
    }

    /**
     * @Menthod: queryInptThirdRecordByBatch
     * @Desrciption: 根据三测单节点时间点查询科室下在院病人列表
     * @Param: inptNurseThirdDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-17 16:56
     * @Return: List<InptNurseThirdDTO>
     **/
    @Override
    public WrapperResponse<List<InptNurseThirdDTO>> queryInptThirdRecordByBatch(Map map) {
        return WrapperResponse.success(inptNurseThirdBO.queryInptThirdRecordByBatch(MapUtils.get(map, "inptNurseThirdDTO")));
    }

    /**
     * @Menthod: saveBatch
     * @Desrciption: 批量保存三测单
     * @Param: inptNurseThirdDTOS
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-19 15:59
     * @Return: boolean
     **/
    @Override
    public WrapperResponse<Boolean> saveBatch(Map map) {
        return WrapperResponse.success(inptNurseThirdBO.saveBatch(MapUtils.get(map, "inptNurseThirdDTOS")));
    }

    /**
     * @Menthod: queryAllByVisitId
     * @Desrciption: 根据就诊id查询出患者在院期间所有护理三测单记录
     * @Param: visitId
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-22 10:02
     * @Return:
     **/
    @Override
    public WrapperResponse<List<InptNurseThirdDTO>> queryAllByVisitId(Map<String, Object> map) {
        return WrapperResponse.success(inptNurseThirdBO.queryAllByVisitId(map));
    }
}
