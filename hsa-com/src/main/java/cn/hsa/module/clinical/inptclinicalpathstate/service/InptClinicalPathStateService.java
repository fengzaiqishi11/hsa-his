package cn.hsa.module.clinical.inptclinicalpathstate.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface InptClinicalPathStateService {

    /**
     * @Meth: queryClinicalPathStageDetail
     * @Description: 通过目录id、阶段id查询临床路径阶段明细
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    WrapperResponse<PageDTO> queryClinicalPathStageDetail(Map map);
    /**
     * @Meth: queryInptClinicalPathState
     * @Description: 根据条件查询病人病情记录表
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    WrapperResponse<PageDTO> queryInptClinicalPathState(Map map);
    /**
     * @Meth: queryInptClinicalPathStateByVisitId
     * @Description: 通过病人信息查询单个临床病人记录
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    WrapperResponse<InptClinicalPathStateDTO> queryInptClinicalPathStateByVisitId(Map map);


    /**
    * @Menthod updateInptClinicalPathStateByVisitId
    * @Desrciption 修改病人入径信息
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/9/24 14:20
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    WrapperResponse<Boolean> updateInptClinicalPathStateByVisitId(Map map);

    /**
    * @Menthod insertInptClinicalPathStateByVisitId
    * @Desrciption 新增病人入径
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/9/24 14:21
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    WrapperResponse<Boolean> insertInptClinicalPathStateByVisitId(Map map);

    /**
    * @Menthod queryDeptByClinicalPathDeptId
    * @Desrciption 查询科室
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/9/24 16:34
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>>
    **/
    WrapperResponse<List<BaseDeptDTO>> queryDeptByClinicalPathDeptId(Map map);

    /**
    * @Menthod queryPatientPage
    * @Desrciption 查询患者信息
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/9/26 11:26
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    WrapperResponse<PageDTO> queryPatientPage(Map map);

    /**
    * @Menthod getPatientByVisitID
    * @Desrciption 用于出径病人信息展示
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/9/27 14:54
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO>
    **/
    WrapperResponse<InptClinicalPathStateDTO> getPatientByVisitID(Map map);


}
