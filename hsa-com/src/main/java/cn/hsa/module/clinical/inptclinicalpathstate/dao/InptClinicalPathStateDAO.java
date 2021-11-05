package cn.hsa.module.clinical.inptclinicalpathstate.dao;

import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO;
import cn.hsa.module.clinical.inptclinicalpathstage.dto.InptClinicalPathStageDTO;
import cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InptClinicalPathStateDAO {
    /**
     * @Meth: queryClinicalItem
     * @Description: 根据目录id 、阶段id查询阶段明细(供诊疗、其他、护理使用)
     * @Param: [inptClinicalPathStateDTO]
     * @return: java.util.List<cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    List<ClinicPathStageDetailDTO> queryClinicalItem(InptClinicalPathStateDTO inptClinicalPathStateDTO);
    /**
     * @Meth: queryInptClinicalPathStateByVisitId
     * @Description: 根据就诊id查询病人记录表
     * @Param: [inptClinicalPathStateDTO]
     * @return: cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    InptClinicalPathStateDTO queryInptClinicalPathStateByVisitId(InptClinicalPathStateDTO inptClinicalPathStateDTO);
    /**
     * @Meth: queryInptClinicalPathState
     * @Description: 查询入径病人信息
     * @Param: [inptClinicalPathStateDTO]
     * @return: java.util.List<cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    List<InptClinicalPathStateDTO> queryInptClinicalPathState(InptClinicalPathStateDTO inptClinicalPathStateDTO);


    /**
    * @Menthod updateInptClinicalPathStateByVisitId
    * @Desrciption 修改病人入径信息
    *
    * @Param
    * [inptClinicalPathStateDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/24 14:22
    * @Return int
    **/
    int updateInptClinicalPathStateByVisitId(InptClinicalPathStateDTO inptClinicalPathStateDTO);

    /**
    * @Menthod insertInptClinicalPathStateByVisitId
    * @Desrciption 新增病人入径
    *
    * @Param
    * [inptClinicalPathStateDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/24 14:23
    * @Return int
    **/
    int insertInptClinicalPathStateByVisitId(List<InptClinicalPathStateDTO> inptClinicalPathStateDTOS);

    /**
    * @Menthod queryNotExecItem
    * @Desrciption 查询阶段未执行项目
    *
    * @Param
    * [inptClinicalPathStateDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/10/26 10:33
    * @Return java.util.List<cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO>
    **/
    List<ClinicPathStageDetailDTO> queryNotExecItem(InptClinicalPathStageDTO inptClinicalPathStageDTO);

    /**
    * @Menthod queryDeptByClinicalPathDeptId
    * @Desrciption 查询科室
    *
    * @Param
    * [inptClinicalPathStateDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/24 16:43
    * @Return java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>
    **/
    List<BaseDeptDTO> queryDeptByClinicalPathDeptId(InptClinicalPathStateDTO inptClinicalPathStateDTO);

    /**
    * @Menthod queryPatientPage
    * @Desrciption 查询患者信息
    *
    * @Param
    * [inptVisitDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/26 11:46
    * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
    **/
    List<InptVisitDTO> queryPatientPage(InptVisitDTO inptVisitDTO);

    /**
    * @Menthod getPatientByVisitID
    * @Desrciption  用于出径病人信息展示
    *
    * @Param
    * [inptClinicalPathStateDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/27 15:03
    * @Return cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO
    **/
    InptClinicalPathStateDTO getPatientByVisitID(InptClinicalPathStateDTO inptClinicalPathStateDTO);

    /**
    * @Menthod quertClinicalVisitById
    * @Desrciption 根据id查询 在径病人信息
    *
    * @Param
    * [hospCode, list]
    *
    * @Author jiahong.yang
    * @Date   2021/10/25 10:18
    * @Return java.util.List<java.lang.String>
    **/
    List<InptClinicalPathStateDTO> quertClinicalVisitById(@Param("hospCode") String hospCode, @Param("list") List<String> list);

    /**
    * @Menthod updateTotalPriceById
    * @Desrciption 更新
    *
    * @Param
    * [inptClinicalPathStateDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/10/25 11:16
    * @Return java.lang.Boolean
    **/
    Boolean updateTotalPriceById(@Param("list") List<InptClinicalPathStateDTO> inptClinicalPathStateDTOS);
}
