package cn.hsa.insure.unifiedpay.util.fmiownpaypatn;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.fmiownpaypatn.dto.FmiOwnpayPatnDiseListDDTO;
import cn.hsa.module.insure.fmiownpaypatn.dto.FmiOwnpayPatnFeeListDDTO;
import cn.hsa.module.insure.fmiownpaypatn.dto.FmiOwnpayPatnMdtrtDDTO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.module.insure.module.dto.InsureSettleInfoDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.BigDecimalUtils;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName PsnInfoReqUtil
 * @Deacription 人员信息获取-1101
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_4263)
public class FmiOwnpayPatnLedgerResultReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        Map map = (Map) param;

        Map<String, Object> dataMap = new HashMap<>(3);

        dataMap.put("mdtrtId", MapUtils.get(map,"mdtrtId"));
        dataMap.put("fixmedinsCode", MapUtils.get(map,"fixmedinsCode"));
        dataMap.put("certno",MapUtils.get(map,"certno"));

        dataMap.put("pageNum",MapUtils.get(map,"pageNum"));
        dataMap.put("pageSize",MapUtils.get(map,"pageSize"));

        HashMap commParam = new HashMap();
        checkRequest(dataMap);
        commParam.put("input", dataMap);
        commParam.put("infno",Constant.UnifiedPay.REGISTER.UP_4263);

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
        return true;
    }

}
