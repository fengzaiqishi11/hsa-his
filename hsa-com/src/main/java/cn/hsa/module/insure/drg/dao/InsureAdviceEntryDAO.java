package cn.hsa.module.insure.drg.dao;

import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualCostDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.entity.InsureEntryLogDO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.drg.dao
 * @class_name: InsureAdviceEntryDAO
 * @project_name: hsa-his
 * @Description:
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date:2020/12/10 15:06
 * @Company: 创智和宇
 **/
public interface InsureAdviceEntryDAO {

    /**
     * @Method: queryInsurePatientInfo()
     * @Descrition: 根据就诊id查询医保病人信息
     * @Pramas: insureIndividualVisitDTO：vsisitId:就诊Id
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/12
     * @Retrun:
     */
    InsureIndividualVisitDTO queryInsurePatientInfo(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Method: queryPage()
     * @Descrition: 分页根据查询医保病人信息
     * @Pramas: insureIndividualVisitDTO：vsisitId:就诊Id
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/12
     * @Retrun:
     */
    List<InptVisitDTO> queryPage(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Method: queryPrescribe()
     * @Descrition: 查询处方信息
     * @Pramas: insureIndividualVisitDTO：vsisitId:就诊Id
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/12
     * @Retrun: 疾病诊断信息数据传输对象
     */
    List<OutptPrescribeDetailsDTO> queryPrescribe(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Method: queryDoctorAdvice()
     * @Descrition: 查询医嘱信息
     * @Pramas: insureIndividualVisitDTO：vsisitId:就诊Id
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/12
     * @Retrun: 医嘱信息数据传输对象
     */
    List<InptAdviceDTO> queryDoctorAdvice(@Param("map") Map<String, Object> map);

    /**
     * @Method: queryInptDisease()
     * @Descrition: 根据就诊id查询诊断信息
     * @Pramas: insureIndividualVisitDTO：vsisitId:就诊Id
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/12
     * @Retrun: 疾病诊断信息数据传输对象
     */
    List<InptVisitDTO> queryInptVisitDisease(InsureIndividualVisitDTO insureIndividualVisitDTO);


    /**
     * @Method: queryPatientIsSettle()
     * @Descrition: 根据就诊id  医院编码 查询改病人是否已经做了结算操作
     * @Pramas: visitId:就诊id hospCode；医院编码
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/16
     * @Retrun:
     */
    Integer queryPatientIsSettle(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Method: insertInsureAdviceLog()
     * @Descrition: 医嘱录入上传到医保的患者的信息 保存到日志表中
     * @Pramas: a
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/17
     * @Retrun: a
     */
    boolean insertInsureAdviceLog(InsureIndividualVisitDTO visitDTO);

    /**
     * @Method: queryInsurePatientLog()
     * @Descrition: 医嘱录入上传之前 查询病人是否已经上传
     * @Pramas: visitId:就诊id hospCode；医院编码
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/17
     * @Retrun: InsureEntryLogDO数据映射对象
     */
    InsureEntryLogDO queryInsurePatientLog(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Method: queryCostId()
     * @Descrition: 根据就诊id查询已经费用传输成功的费用信息
     * @Pramas: visitId:就诊id hospCode；医院编码
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/17
     * @Retrun: 医保就诊表费用传输对象
     */
    List<Map<String, Object>> queryCostId(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Method: queryAdviceId()
     * @Descrition: 根据费用id查询出对应的医嘱id信息
     * @Pramas: costId:费用id visitId:就诊id hospCode:医院编码
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/17
     * @Retrun: 医嘱信息数据传输对象
     */
    List<String> queryAdviceId(@Param("costListMap") List<Map<String, Object>> costListMap);

    Boolean deleteLog(InsureIndividualVisitDTO insureIndividualVisitDTO);

    int updateInsureUploadById(List<InptAdviceDTO> adviceDTOList);

    //根据visit_id修改上传标志位未上传
    int updateInsureUploadByVisitId(String visitId);
}
