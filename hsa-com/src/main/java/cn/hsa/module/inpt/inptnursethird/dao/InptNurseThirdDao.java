package cn.hsa.module.inpt.inptnursethird.dao;

/**
 * @author ljh
 * @date 2020/09/16.
 */

import cn.hsa.module.inpt.inptnursethird.dto.InptNurseThirdDTO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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

    // 根据三测单节点时间点查询科室下在院病人的【婴儿】列表
    List<InptNurseThirdDTO> queryInptThirdRecordByBabyBatch(InptNurseThirdDTO inptNurseThirdDTO);

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

    /**
     * @Menthod: queryAllByVisitId
     * @Desrciption: 根据就诊id查询出患者在院期间所有护理三测单记录
     * @Param: visitId
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-22 10:02
     * @Return:
     **/
    List<InptNurseThirdDTO> queryAllByVisitId(Map<String, Object> map);

    /**
     * 批量录入三测单，修改每个就诊者当天全部时间点共有的参数值
     * 比如 上午血压和下午血压这些共有的值，修改一个时间则全部时间都修改
     * @param editList
     * @return
     */
    int updatePublicParameterList(@Param("editList") List<InptNurseThirdDTO> editList);

    List<InptNurseThirdDTO> queryTimeSlotList(@Param("hospCode") String hospCode,@Param("startDate") String startDate);
}
