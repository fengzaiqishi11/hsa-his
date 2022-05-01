package cn.hsa.insure.unifiedpay.util.deptInfo;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName DeptInfoBatchImpReqUtil
 * @Deacription 科室信息上传-3401A
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_3401A)
public class DeptInfoBatchImpReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        List<BaseDeptDTO> deptDTOList = (List<BaseDeptDTO>) param;


        List<Map<String, Object>> mapList = new ArrayList<>();
        deptDTOList.stream().forEach(deptDTO -> {
            // 设置上传状态未已上传
            deptDTO.setIsUpload(Constants.SF.S); // 已上传

            Map<String, Object> httpParam = new HashMap<>(18);
            // 医院科室编码
            httpParam.put("hosp_dept_codg", deptDTO.getCode());
            // 科别
            httpParam.put("caty", deptDTO.getNationCode());
            // 医院科室名称
            httpParam.put("hosp_dept_name", deptDTO.getName());
            // 开始时间
            httpParam.put("begntime", DateUtils.parse(DateUtils.format(deptDTO.getCrteTime(), DateUtils.Y_M_DH_M_S), DateUtils.Y_M_DH_M_S));
            // 结束时间
            httpParam.put("endtime", null);
            // 简介
            httpParam.put("itro", deptDTO.getIntro());
            // 科室负责人姓名
            httpParam.put("dept_resper_name", deptDTO.getPersonName());
            // 科室负责人电话
            httpParam.put("dept_resper_tel", deptDTO.getPhone());
            // 科室医疗服务范围
            httpParam.put("dept_med_serv_scp", null);
            // 科室成立日期
            httpParam.put("dept_estbdat", DateUtils.parse(DateUtils.format(deptDTO.getCrteTime(), DateUtils.Y_M_DH_M_S), DateUtils.Y_M_DH_M_S));
            // 批准床位数量
            httpParam.put("aprv_bed_cnt", Integer.parseInt(deptDTO.getBedNum()));
            // 医保认可床位数
            httpParam.put("hi_crtf_bed_cnt", null);
            // 统筹区编号
            //TODO insureConfigurationDTO.getAttrCode()
            httpParam.put("poolarea_no", null);
            // 医生人数
            httpParam.put("dr_psncnt", Integer.parseInt(deptDTO.getDoctorNum()));
            // 药师人数
            httpParam.put("phar_psncnt", Integer.parseInt(deptDTO.getDrugNum()));
            // 护士人数
            httpParam.put("nurs_psncnt", Integer.parseInt(deptDTO.getNurseNum()));
            // 技师人数
            httpParam.put("tecn_psncnt", Integer.parseInt(deptDTO.getMedicNum()));
            // 备注
            httpParam.put("memo", deptDTO.getRemark());

            mapList.add(httpParam);

            checkRequest(httpParam);


        });


        Map httpParam = new HashMap();
        httpParam.put("infno",Constant.UnifiedPay.REGISTER.UP_3401A);
        return getInsurCommonParam(httpParam);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}
