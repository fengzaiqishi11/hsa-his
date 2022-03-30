package cn.hsa.insure.unifiedpay.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.emr.bo.InsureUnifiedEmrBO;
import cn.hsa.module.insure.emr.service.InsureUnifiedEmrService;
import lombok.extern.slf4j.Slf4j;

import cn.hsa.module.insure.emr.dto.*;
import cn.hsa.module.insure.emr.service.InsureUnifiedEmrService;
import cn.hsa.util.MapUtils;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName InsureUnifiedEmrServiceImpl
 * @Description TODO
 * @Author qiang.fan
 * @Date 2022/3/25 13:37
 * @Version 1.0
 **/


@Slf4j
@HsafRestPath("/service/insure/insureUnifiedEmr")
@Service("insureUnifiedEmrService_provider")
public class InsureUnifiedEmrServiceImpl implements InsureUnifiedEmrService {

    @Resource
    private InsureUnifiedEmrBO insureUnifiedEmrBO;


    @Override
    public WrapperResponse<PageDTO> queryInsureUnifiedEmrInfo(Map<String, Object> map) {
        // 查询 联合 结算表 主，关联 医保就诊表，电子病历入院记录表
        return WrapperResponse.success(insureUnifiedEmrBO.queryInsureUnifiedEmrInfo(MapUtils.get(map,"insureEmrUnifiedDTO")));
    }

    @Override
    public WrapperResponse<InsureEmrDetailDTO> queryInsureUnifiedEmrDetail(Map<String, Object> map) {
        // 根据 his就诊id，医保登记id，人员编号
        // 查询 患者基本展示信息
        // 查询 入院记录，诊断记录，病程记录，手术记录，抢救记录，死亡记录，出院小结
        return WrapperResponse.success(insureUnifiedEmrBO.queryInsureUnifiedEmrDetail(MapUtils.get(map,"insureEmrUnifiedDTO")));
    }

    @Override
    public WrapperResponse<InsureEmrAdminfoDTO> updateInsureUnifiedEmrAdminfo(Map<String, Object> map) {
        // 根据uuid 判断记录是否存在，不存在则新增，存在则修改
        return WrapperResponse.success(insureUnifiedEmrBO.updateInsureUnifiedEmrAdminfo(MapUtils.get(map,"insureEmrAdminfoDTO")));
    }

    @Override
    public WrapperResponse<InsureEmrDiseinfoDTO> updateInsureUnifiedEmrDiseinfo(Map<String, Object> map) {
        // 根据uuid 判断记录是否存在，不存在则新增，存在则修改
        return WrapperResponse.success(insureUnifiedEmrBO.updateInsureUnifiedEmrDiseinfo(MapUtils.get(map,"insureEmrDiseinfoDTO")));
    }

    @Override
    public WrapperResponse<InsureEmrCoursrinfoDTO> updateInsureUnifiedEmrCoursrinfo(Map<String, Object> map) {
        // 根据uuid 判断记录是否存在，不存在则新增，存在则修改
        return WrapperResponse.success(insureUnifiedEmrBO.updateInsureUnifiedEmrCoursrinfo(MapUtils.get(map,"insureEmrCoursrinfoDTO")));
    }

    @Override
    public WrapperResponse updateInsureUnifiedEmrOprninfo(Map<String, Object> map) {
        // 根据uuid 判断记录是否存在，不存在则新增，存在则修改
        return WrapperResponse.success(insureUnifiedEmrBO.updateInsureUnifiedEmrOprninfo(MapUtils.get(map,"insureEmrOprninfoDTO")));
    }

    @Override
    public WrapperResponse<InsureEmrRescinfoDTO> updateInsureUnifiedEmrRescinfo(Map<String, Object> map) {
        // 根据uuid 判断记录是否存在，不存在则新增，存在则修改
        return WrapperResponse.success(insureUnifiedEmrBO.updateInsureUnifiedEmrRescinfo(MapUtils.get(map,"insureEmrRescinfoDTO")));
    }

    @Override
    public WrapperResponse<InsureEmrDieinfoDTO> updateInsureUnifiedEmrDieinfo(Map<String, Object> map) {
        // 根据uuid 判断记录是否存在，不存在则新增，存在则修改
        return WrapperResponse.success(insureUnifiedEmrBO.updateInsureUnifiedEmrDieinfo(MapUtils.get(map,"insureEmrDieinfoDTO")));
    }

    @Override
    public WrapperResponse<InsureEmrDscginfoDTO> updateInsureUnifiedEmrDscginfo(Map<String, Object> map) {
        // 根据uuid 判断记录是否存在，不存在则新增，存在则修改
        return WrapperResponse.success(insureUnifiedEmrBO.updateInsureUnifiedEmrDscginfo(MapUtils.get(map,"insureEmrDscginfoDTO")));
    }

    @Override
    public void updateInsureUnifiedEmrUpload(Map<String, Object> map) {
        // 根据 his就诊id，医保登记id，人员编号
        // 查询 入院记录，诊断记录，病程记录，手术记录，抢救记录，死亡记录，出院小结
        // 组装 报文 调用医保接口
        insureUnifiedEmrBO.updateInsureUnifiedEmrUpload(MapUtils.get(map,"insureEmrUnifiedDTO"));
    }

    @Override
    public void updateInsureUnifiedEmrSync(Map<String, Object> map) {
        // 提供给电子病历系统，做数据初始化
        // 初始化 入院记录，诊断记录，病程记录，手术记录，抢救记录，死亡记录，出院小结
        insureUnifiedEmrBO.updateInsureUnifiedEmrSync(MapUtils.get(map,"insureEmrUnifiedDTO"));
    }

    @Override
    public void export(HttpServletRequest req, Map<String, Object> map) {
        List<InsureEmrUnifiedDTO> list = insureUnifiedEmrBO.export(MapUtils.get(map,"insureEmrUnifiedDTO"));

    }
}
