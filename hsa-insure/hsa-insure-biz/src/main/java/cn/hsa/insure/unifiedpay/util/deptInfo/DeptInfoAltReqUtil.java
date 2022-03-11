package cn.hsa.insure.unifiedpay.util.deptInfo;

import cn.hsa.exception.BizRtException;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DeptInfoAltReqUtil
 * @Deacription 科室信息变更-3402
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_3402)
public class DeptInfoAltReqUtil<T> implements BaseReqUtil<T> {

    @Override
    public String initRequest(T param) {
        BaseDeptDTO deptDTO = (BaseDeptDTO) param;

        Map<String, Object> httpParam = new HashMap<>(18);
        // 医院科室编码
        httpParam.put("hosp_dept_codg", deptDTO.getCode());
        // 科别
        httpParam.put("caty", deptDTO.getNationCode());
        // 医院科室名称
        httpParam.put("hosp_dept_name", deptDTO.getName());
        // 开始时间
        httpParam.put("begntime", deptDTO.getCrteTime());
        // 结束时间
        //httpParam.put("endtime", null);
        // 简介
        httpParam.put("itro", deptDTO.getIntro());
        // 科室负责人姓名
        httpParam.put("dept_resper_name", deptDTO.getPersonName());
        // 科室负责人电话
        httpParam.put("dept_resper_tel", deptDTO.getPhone());
        // 科室医疗服务范围
        //httpParam.put("dept_med_serv_scp", null);
        // 科室成立日期
        httpParam.put("dept_estbdat", deptDTO.getCrteTime());
        // 批准床位数量
        httpParam.put("aprv_bed_cnt", Integer.parseInt(deptDTO.getBedNum()));
        // 医保认可床位数
        //httpParam.put("hi_crtf_bed_cnt", null);
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

        checkRequest(httpParam);
        return JSON.toJSONString(httpParam);
    }

    @Override
    public boolean checkRequest(Map param) {
        if(StringUtils.isEmpty(MapUtils.get(param,"hosp_dept_codg"))){
            throw new BizRtException(-1,"hosp_dept_codg不能为空");
        }
        return true;
    }

}
