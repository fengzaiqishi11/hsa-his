package cn.hsa.insure.unifiedpay.util.outptsettle;

import cn.hsa.exception.BizRtException;
import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.dept.service.BaseDeptService;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName OutptRegReqUtil
 * @Deacription 门诊挂号-2201
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.OUTPT.UP_2201)
public class OutptRegReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Resource
    private BaseDeptService baseDeptService_consumer;

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        Map map = (Map) param;
        Map<String, Object> dataMap = new HashMap<>(16);
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisit");

        // 人员编号
        dataMap.put("psn_no", insureIndividualVisitDTO.getAac001());
        // 险种类型
        dataMap.put("insutype", insureIndividualVisitDTO.getAae140());
        // 开始时间
        dataMap.put("begntime", DateUtils.format(insureIndividualVisitDTO.getVisitTime(), DateUtils.Y_M_DH_M_S));
        // 就诊凭证类型
        dataMap.put("mdtrt_cert_type", insureIndividualVisitDTO.getMdtrtCertType());
        // 就诊凭证编号
        dataMap.put("mdtrt_cert_no", insureIndividualVisitDTO.getMdtrtCertNo());
        // 传值社保卡识别码
        dataMap.put("card_sn", insureIndividualVisitDTO.getCardIden());
        dataMap.put("psn_cert_type", "01");
        // 传值证件号码
        dataMap.put("certno", insureIndividualVisitDTO.getAac002());
        // 人员类别
        dataMap.put("psn_type", insureIndividualVisitDTO.getBka035());
        // 姓名
        dataMap.put("psn_name", insureIndividualVisitDTO.getAac003());
        // 住院/门诊号
        dataMap.put("ipt_otp_no", insureIndividualVisitDTO.getVisitNo());
        // 医师编码
        dataMap.put("atddr_no", insureIndividualVisitDTO.getPracCertiNo());
        // 医师姓名
        dataMap.put("dr_name", insureIndividualVisitDTO.getDoctorName());
        // 科室编码
        dataMap.put("dept_code", insureIndividualVisitDTO.getVisitDrptId());
        // 科室名称
        dataMap.put("dept_name", insureIndividualVisitDTO.getVisitDrptName());
        String hospCode = MapUtils.get(map, "hospCode");
        Map deptMap = new HashMap(2);
        deptMap.put("hospCode", hospCode);
        BaseDeptDTO baseDeptDTO = new BaseDeptDTO();
        baseDeptDTO.setHospCode(hospCode);
        baseDeptDTO.setId(insureIndividualVisitDTO.getVisitDrptId());
        deptMap.put("baseDeptDTO", baseDeptDTO);
        baseDeptDTO = baseDeptService_consumer.getById(deptMap).getData();
        // 科别
        dataMap.put("caty", baseDeptDTO.getTypeCode());

        HashMap commParam = new HashMap();
        // 校验参数
        checkRequest(dataMap);
        // 组装参数
        commParam.put("dataMap", dataMap);
        commParam.put("infno",Constant.UnifiedPay.OUTPT.UP_2201);
        commParam.put("msgId",MapUtils.get(map,"msgId"));
        commParam.put("opter",MapUtils.get(map,"opter"));
        commParam.put("opter_name",MapUtils.get(map,"opter_name"));
        commParam.put("insuplcAdmdvs",MapUtils.get(map,"insuplcAdmdvs"));
        commParam.put("hospCode",MapUtils.get(map,"hospCode"));
        commParam.put("orgCode",MapUtils.get(map,"orgCode"));
        commParam.put("configCode",MapUtils.get(map,"configCode"));
        commParam.put("configRegCode",MapUtils.get(map,"configRegCode"));

        return getInsurCommonParam(commParam);
    }

    @Override
    public boolean checkRequest(Map param) {
        String mdtrtCertType = MapUtils.get(param, "atddr_no");
        if (StringUtils.isEmpty(mdtrtCertType)) {
            throw new BizRtException(-1, "该" + MapUtils.get(param, "dr_name") + "医生的医师编码没有维护,请先去用户管理里面维护");
        }
        return true;
    }

}
