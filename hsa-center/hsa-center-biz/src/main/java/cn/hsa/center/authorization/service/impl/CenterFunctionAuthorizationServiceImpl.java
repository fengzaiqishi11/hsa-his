package cn.hsa.center.authorization.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.authorization.bo.CenterFunctionAuthorizationBO;
import cn.hsa.module.center.authorization.dao.CenterFunctionAuthorizationDAO;
import cn.hsa.module.center.authorization.dto.CenterFunctionAuthorizationDto;
import cn.hsa.module.center.authorization.dto.CenterFunctionDto;
import cn.hsa.module.center.authorization.entity.CenterFunctionAuthorizationDO;
import cn.hsa.module.center.authorization.service.CenterFunctionAuthorizationService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author luonianxin
 * @version 1.0
 * @date 2022/6/7 16:30
 */
@Slf4j
@Service("centerFunctionAuthorizationService_provider")
public class CenterFunctionAuthorizationServiceImpl implements CenterFunctionAuthorizationService {

    private CenterFunctionAuthorizationBO centerFunctionAuthorizationBO;

    @Autowired
    public void setCenterFunctionAuthorizationDAO(CenterFunctionAuthorizationBO centerFunctionAuthorizationBO) {
        this.centerFunctionAuthorizationBO = centerFunctionAuthorizationBO;
    }


    @Override
    public WrapperResponse<CenterFunctionAuthorizationDO> queryBizAuthorizationByOrderTypeCode(Map<String,Object> params) {
        String hospCode = MapUtils.getEmptyErr(params,"hospCode","医院编码【hospCode】不能为空!");
        String orderTypeCode = MapUtils.getEmptyErr(params,"orderTypeCode","业务编码【orderTypeCode】不能为空!");

        return centerFunctionAuthorizationBO.queryBizAuthorizationByOrderTypeCode(hospCode,orderTypeCode);
    }

    /**
     * 新增功能授权数据
     *
     * @param functionAuthorizationDO
     * @return
     */

    public WrapperResponse<CenterFunctionAuthorizationDO> insertBizAuthorization(CenterFunctionAuthorizationDO functionAuthorizationDO) {
        return centerFunctionAuthorizationBO.insertBizAuthorization(functionAuthorizationDO);
    }



    /**
     * @param centerFunctionAuthorizationDto
     * @Menthod queryHospZzywPage()
     * @Desrciption 根据条件分页查询医院增值业务
     * @Param 1. [CenterHospitalDTO] 参数数据传输DTO对象
     * @Author pengbo
     * @Date 2022/06/28 16:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     */
    @Override
    public WrapperResponse<Map<String,Object>> queryHospZzywPage(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto) {
        return WrapperResponse.success(centerFunctionAuthorizationBO.queryHospZzywPage(centerFunctionAuthorizationDto));
    }

    /**
     * @param centerFunctionAuthorizationDto
     * @Menthod queryPage()
     * @Desrciption 根据条件分页查询参数信息
     * @Param 1. [centerFunctionAuthorizationDto] 参数数据传输DTO对象
     * @Author pengbo
     * @Date 2022/06/28 16:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     */
    @Override
    public WrapperResponse<List<CenterFunctionAuthorizationDto>> queryPage(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto) {
        return WrapperResponse.success(centerFunctionAuthorizationBO.queryPage(centerFunctionAuthorizationDto));
    }

    @Override
    public WrapperResponse<Boolean> updateAuthorization(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto) {
        return WrapperResponse.success(centerFunctionAuthorizationBO.updateAuthorization(centerFunctionAuthorizationDto));
    }

    @Override
    public WrapperResponse<CenterFunctionAuthorizationDto> updateAuthorizationAudit(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto) {
        return WrapperResponse.success(centerFunctionAuthorizationBO.updateAuthorizationAudit(centerFunctionAuthorizationDto));
    }

    @Override
    public WrapperResponse<CenterFunctionAuthorizationDto> saveBizAuthorization(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto) {
        return WrapperResponse.success(centerFunctionAuthorizationBO.saveBizAuthorization(centerFunctionAuthorizationDto));
    }

    @Override
    public WrapperResponse<List<CenterFunctionDto>> queryCenterFunction(CenterFunctionDto centerFunctionDto) {
        return WrapperResponse.success(centerFunctionAuthorizationBO.queryCenterFunction(centerFunctionDto));
    }
    /**
     * @Menthod deleteAuthorizationByCode()
     * @Desrciption   关闭（删除）增值服务
     * @Param
     * 1. [centerFunctionAuthorizationDto] 参数数据传输DTO对象
     * @Author pengbo
     * @Date   2022/06/28 16:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     **/
    @Override
    public WrapperResponse<CenterFunctionAuthorizationDto> deleteAuthorizationByCode(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto) {
        return WrapperResponse.success(centerFunctionAuthorizationBO.deleteAuthorizationByCode(centerFunctionAuthorizationDto));
    }
}
