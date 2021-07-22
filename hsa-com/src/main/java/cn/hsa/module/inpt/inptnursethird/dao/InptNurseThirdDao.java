package cn.hsa.module.inpt.inptnursethird.dao;

/**
 * @author ljh
 * @date 2020/09/16.
 */

import cn.hsa.module.inpt.inptnursethird.dto.InptNurseThirdDTO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.inptnursethird.dao
 * @Class_name:: InptNurseThirdDao
 * @Description:
 * @Author: ljh
 * @Date: 2020/9/16 10:41
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Mapper
public interface InptNurseThirdDao {
    /**
     * @Package_name: cn.hsa.module.inpt.inptnursethird.dao
     * @Class_name:: InptNurseThirdDao
     * @Description:
     * @Author: ljh
     * @Date: 2020/9/16 10:45
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    int insertList(@Param("list") List<InptNurseThirdDTO> list);

    /**
     * @Package_name: cn.hsa.module.inpt.inptnursethird.dao
     * @Class_name:: InptNurseThirdDao
     * @Description:
     * @Author: ljh
     * @Date: 2020/9/16 10:45
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    int deleteById(InptNurseThirdDTO inptNurseThirdDTO);

    /**
     * @Package_name: cn.hsa.module.inpt.inptnursethird.dao
     * @Class_name:: InptNurseThirdDao
     * @Description:
     * @Author: ljh
     * @Date: 2020/9/16 10:45
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    List<InptNurseThirdDTO> queryAll(InptNurseThirdDTO inptNurseThirdDTO);

    List<InptNurseThirdDTO> queryAllRecordTime(InptNurseThirdDTO inptNurseThirdDTO);


    /**
    * @Method queryAllByTimeSlot
    * @Param [inptNurseThirdDTO]
    * @description   三测单查询
    * @author marong
    * @date 2020/10/29 9:56
    * @return java.util.List<cn.hsa.module.inpt.inptnursethird.dto.InptNurseThirdDTO>
    * @throws
    */
    List<InptNurseThirdDTO> queryAllByTimeSlot(InptNurseThirdDTO inptNurseThirdDTO);
    
    /**
    * @Method getRecordTime
    * @Param [inptNurseThirdDTO] 
    * @description   
    * @author marong 
    * @date 2020/10/29 10:58
    * @return java.util.List<java.lang.String>  
    * @throws   
    */
    List<String> getRecordTime(InptNurseThirdDTO inptNurseThirdDTO);

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
     * @Menthod: updateList
     * @Desrciption: 批量修改三测单
     * @Param: editList
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-19 19:14
     * @Return: int
     **/
    int updateList(@Param("editList") List<InptNurseThirdDTO> editList);
}
