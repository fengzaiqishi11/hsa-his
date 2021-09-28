package cn.hsa.clinical.inptclinicalpathstate.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.clinical.inptclinicalpathstate.bo.InptClinicalPathStateBO;
import cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO;
import cn.hsa.module.clinical.inptclinicalpathstate.service.InptClinicalPathStateService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@HsafRestPath("/service/inpt/inptClinicalPathStateService")
@Slf4j
@Service("inptClinicalPathStateService_provider")
public class InptClinicalPathStateServiceImpl implements InptClinicalPathStateService {

    @Resource
    private InptClinicalPathStateBO inptClinicalPathStateBO;
    /**
     * @Meth: queryClinicalPathStageDetail
     * @Description: 根据目录id、阶段id查询临床路径明细
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    @Override
    public WrapperResponse<PageDTO> queryClinicalPathStageDetail(Map map) {
        return WrapperResponse.success(inptClinicalPathStateBO.queryClinicalPathStageDetail(MapUtils.get(map,"inptClinicalPathStateDTO")));
    }
    /**
     * @Meth: queryInptClinicalPathState
     * @Description: 根据条件查询病人病情记录表
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    @Override
    public WrapperResponse<PageDTO> queryInptClinicalPathState(Map map) {
        return  WrapperResponse.success(inptClinicalPathStateBO.queryInptClinicalPathState(MapUtils.get(map,"inptClinicalPathStateDTO")));
    }

    /**
     * @Meth: queryInptClinicalPathStateByVisitId
     * @Description: 通过病人信息查询单个病人记录
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    @Override
    public WrapperResponse<InptClinicalPathStateDTO> queryInptClinicalPathStateByVisitId(Map map) {

        return WrapperResponse.success(inptClinicalPathStateBO.queryInptClinicalPathStateByVisitId(MapUtils.get(map,"inptClinicalPathStateDTO")));
    }

    /**
    * @Menthod updateInptClinicalPathStateByVisitId
    * @Desrciption 修改病人入径信息
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/9/24 14:05
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO>
    **/
    @Override
    public WrapperResponse<Boolean> updateInptClinicalPathStateByVisitId(Map map) {
      return WrapperResponse.success(inptClinicalPathStateBO.updateInptClinicalPathStateByVisitId(MapUtils.get(map,"inptClinicalPathStateDTO")));
    }

    /**
    * @Menthod insertInptClinicalPathStateByVisitId
    * @Desrciption 新增病人入径
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/9/24 14:19
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> insertInptClinicalPathStateByVisitId(Map map) {
      return WrapperResponse.success(inptClinicalPathStateBO.insertInptClinicalPathStateByVisitId(MapUtils.get(map,"inptClinicalPathStateDTO")));
    }

    /**
    * @Menthod queryDeptByClinicalPathDeptId
    * @Desrciption 查询科室
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/9/24 16:36
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>>
    **/
    @Override
    public WrapperResponse<List<BaseDeptDTO>> queryDeptByClinicalPathDeptId(Map map) {
      return WrapperResponse.success(inptClinicalPathStateBO.queryDeptByClinicalPathDeptId(MapUtils.get(map,"inptClinicalPathStateDTO")));
    }

    /**
    * @Menthod queryPatientPage
    * @Desrciption 查询患者信息
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/9/26 11:27
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryPatientPage(Map map) {
      return WrapperResponse.success(inptClinicalPathStateBO.queryPatientPage(MapUtils.get(map,"inptVisitDTO")));
    }

    /**
    * @Menthod getPatientByVisitID
    * @Desrciption 用于出径病人信息展示
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/9/27 14:57
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO>
    **/
    @Override
    public WrapperResponse<InptClinicalPathStateDTO> getPatientByVisitID(Map map) {
      return WrapperResponse.success(inptClinicalPathStateBO.getPatientByVisitID(MapUtils.get(map,"inptClinicalPathStateDTO")));
    }
}
