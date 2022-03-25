package cn.hsa.insure.unifiedpay.emr.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.emr.service.InsureUnifiedEmrService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName InsureUnifiedEmrServiceImpl
 * @Description TODO
 * @Author qiang.fan
 * @Date 2022/3/25 13:37
 * @Version 1.0
 **/

public class InsureUnifiedEmrServiceImpl implements InsureUnifiedEmrService {

    @Override
    public WrapperResponse<PageDTO> queryInsureUnifiedEmrInfo(Map<String, Object> map) {
        //TODO 查询 联合 结算表 主，关联 医保就诊表，电子病历入院记录表
        return null;
    }

    @Override
    public WrapperResponse queryInsureUnifiedEmrDetail(Map<String, Object> map) {
        //TODO 根据 his就诊id，医保登记id，人员编号
        // 查询 患者基本展示信息
        // 查询 入院记录，诊断记录，病程记录，手术记录，抢救记录，死亡记录，出院小结
        return null;
    }

    @Override
    public WrapperResponse updateInsureUnifiedEmrAdminfo(Map<String, Object> map) {
        //TODO 根据uuid 判断记录是否存在，不存在则新增，存在则修改
        return null;
    }

    @Override
    public WrapperResponse updateInsureUnifiedEmrDiseinfo(Map<String, Object> map) {
        //TODO 根据uuid 判断记录是否存在，不存在则新增，存在则修改
        return null;
    }

    @Override
    public WrapperResponse updateInsureUnifiedEmrCoursrinfo(Map<String, Object> map) {
        //TODO 根据uuid 判断记录是否存在，不存在则新增，存在则修改
        return null;
    }

    @Override
    public WrapperResponse updateInsureUnifiedEmrOprninfo(Map<String, Object> map) {
        //TODO 根据uuid 判断记录是否存在，不存在则新增，存在则修改
        return null;
    }

    @Override
    public WrapperResponse updateInsureUnifiedEmrRescinfo(Map<String, Object> map) {
        //TODO 根据uuid 判断记录是否存在，不存在则新增，存在则修改
        return null;
    }

    @Override
    public WrapperResponse updateInsureUnifiedEmrDieinfo(Map<String, Object> map) {
        //TODO 根据uuid 判断记录是否存在，不存在则新增，存在则修改
        return null;
    }

    @Override
    public WrapperResponse updateInsureUnifiedEmrDscginfo(Map<String, Object> map) {
        //TODO 根据uuid 判断记录是否存在，不存在则新增，存在则修改
        return null;
    }

    @Override
    public WrapperResponse updateInsureUnifiedEmrUpload(Map<String, Object> map) {
        //TODO 根据 his就诊id，医保登记id，人员编号
        // 查询 入院记录，诊断记录，病程记录，手术记录，抢救记录，死亡记录，出院小结
        // 组装 报文 调用医保接口
        return null;
    }

    @Override
    public WrapperResponse updateInsureUnifiedEmrSync(Map<String, Object> map) {
        //TODO 提供给电子病历系统，做数据初始化
        // 初始化 入院记录，诊断记录，病程记录，手术记录，抢救记录，死亡记录，出院小结
        return null;
    }

    @Override
    public void export(HttpServletRequest req, Map<String, Object> map) {

    }
}
