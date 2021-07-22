package cn.hsa.module.inpt.inptnursethird.bo;

import cn.hsa.module.inpt.inptnursethird.dto.InptNurseThirdDTO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.inptnursethird.bo
 * @Class_name:: InptNurseThirdBO
 * @Description:
 * @Author: ljh
 * @Date: 2020/9/16 10:55
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InptNurseThirdBO {

    /**
     * @Package_name: cn.hsa.module.inpt.inptnursethird.dao
     * @Class_name:: InptNurseThirdDao
     * @Description:
     * @Author: ljh
     * @Date: 2020/9/16 10:45
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    int insertList(InptNurseThirdDTO inptNurseThirdDTO);


    /**
     * @Package_name: cn.hsa.module.inpt.inptnursethird.dao
     * @Class_name:: InptNurseThirdDao
     * @Description:
     * @Author: ljh
     * @Date: 2020/9/16 10:45
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    List<InptNurseThirdDTO> queryAll(InptNurseThirdDTO inptNurseThirdDTO) throws Exception;


    /**
     * @Package_name: cn.hsa.module.inpt.inptnursethird.dao
     * @Class_name:: InptNurseThirdDao
     * @Description:
     * @Author: ljh
     * @Date: 2020/9/16 10:45
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    List<InptNurseThirdDTO> queryAllRecordTime(InptNurseThirdDTO inptNurseThirdDTO) throws Exception;


    /**
    * @Method getPageList
    * @Param [inptNurseThirdDTO]
    * @description   获取分页信息
    * @author marong
    * @date 2020/10/29 10:21
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    * @throws
    */
    List<Map<String, Object>> getPageList(InptNurseThirdDTO inptNurseThirdDTO);

    /**
    * @Method queryAllByTimeSlot
    * @Param [inptNurseThirdDTO]
    * @description   三测单查询
    * @author marong
    * @date 2020/10/29 9:54
    * @return java.util.List<cn.hsa.module.inpt.inptnursethird.dto.InptNurseThirdDTO>
    * @throws
    */
    Map<String, Object> queryAllByTimeSlot(InptNurseThirdDTO inptNurseThirdDTO);

    /**
    * @Method saveInptNurseThird
    * @Param [inptNurseThirdDTO] 
    * @description   
    * @author marong 
    * @date 2020/10/29 14:09
    * @return java.lang.Boolean  
    * @throws   
    */
    Boolean saveInptNurseThird(InptNurseThirdDTO inptNurseThirdDTO);

    /**
     * @Method queryOperByVisitId
     * @Param [OperInfoRecordDTO]
     * @description   根据就诊visitId查询已完成手术列表
     * @author luoyong
     * @date 2020/10/29 9:53
     * @return List<OperInfoRecordDTO>
     * @throws
     */
    List<OperInfoRecordDTO> queryOperByVisitId(OperInfoRecordDTO operInfoRecordDTO);

    /**
     * @Menthod: queryInptThirdRecordByBatch
     * @Desrciption: 根据三测单节点时间点查询科室下在院病人列表
     * @Param: inptNurseThirdDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-17 16:56
     * @Return: List<InptNurseThirdDTO>
     **/
    List<InptNurseThirdDTO> queryInptThirdRecordByBatch(InptNurseThirdDTO inptNurseThirdDTO);

    /**
     * @Menthod: saveBatch
     * @Desrciption: 批量保存三测单
     * @Param: inptNurseThirdDTOS
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-19 15:59
     * @Return: boolean
     **/
    Boolean saveBatch(List<InptNurseThirdDTO> inptNurseThirdDTOS);
}
