package cn.hsa.module.clinical.inptclinicalpathstate.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;

import java.util.List;

public interface InptClinicalPathStateBO {

    /**
    * @Menthod queryClinicalPathStageDetail
    * @Desrciption
    *
    * @Param
    * [inptClinicalPathStateDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/24 16:37
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryClinicalPathStageDetail(InptClinicalPathStateDTO inptClinicalPathStateDTO);

    /**
    * @Menthod queryInptClinicalPathState
    * @Desrciption
    *
    * @Param
    * [inptClinicalPathStateDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/24 16:37
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryInptClinicalPathState(InptClinicalPathStateDTO inptClinicalPathStateDTO);

    /**
    * @Menthod queryInptClinicalPathStateByVisitId
    * @Desrciption
    *
    * @Param
    * [inptClinicalPathStateDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/24 16:37
    * @Return cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO
    **/
    InptClinicalPathStateDTO queryInptClinicalPathStateByVisitId(InptClinicalPathStateDTO inptClinicalPathStateDTO);

    /**
    * @Menthod updateInptClinicalPathStateByVisitId
    * @Desrciption 修改病人入径信息
    *
    * @Param
    * [inptClinicalPathStateDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/24 16:37
    * @Return java.lang.Boolean
    **/
    Boolean updateInptClinicalPathStateByVisitId(InptClinicalPathStateDTO inptClinicalPathStateDTO);

    /**
    * @Menthod insertInptClinicalPathStateByVisitId
    * @Desrciption 新增病人入径
    *
    * @Param
    * [inptClinicalPathStateDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/24 16:37
    * @Return java.lang.Boolean
    **/
    Boolean insertInptClinicalPathStateByVisitId(InptClinicalPathStateDTO inptClinicalPathStateDTO);

    /**
    * @Menthod queryDeptByClinicalPathDeptId
    * @Desrciption 查询科室
    *
    * @Param
    * [inptClinicalPathStateDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/24 16:37
    * @Return java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>
    **/
    List<BaseDeptDTO> queryDeptByClinicalPathDeptId(InptClinicalPathStateDTO inptClinicalPathStateDTO);

    /**
    * @Menthod queryInptClinicalPathState
    * @Desrciption 查询患者信息
    *
    * @Param
    * [inptVisitDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/26 11:27
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryPatientPage(InptVisitDTO inptVisitDTO);
}
