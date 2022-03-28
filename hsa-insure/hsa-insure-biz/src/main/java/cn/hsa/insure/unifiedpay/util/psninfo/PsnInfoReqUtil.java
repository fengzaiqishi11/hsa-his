package cn.hsa.insure.unifiedpay.util.psninfo;

import cn.hsa.exception.BizRtException;
import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.dto.InsureIndividualBasicDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName PsnInfoReqUtil
 * @Deacription 人员信息获取-1101
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_1101)
public class PsnInfoReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        Map map = (Map) param;

        InsureIndividualBasicDTO insureIndividualBasicDTO = MapUtils.get(map, "insureIndividualBasicDTO");

        Map<String, Object> visitMap = new HashMap<>();
        String mdtrtCertType = insureIndividualBasicDTO.getBka895();
        //  电子凭证 - 需根据各市政策需要选择性传值 add 2021-06-16 by liaojiguang QrCodePolicy
        if (Constant.UnifiedPay.CKLX.DZPZ.equals(mdtrtCertType)) {
            // 就诊凭证类型  传值01
            visitMap.put("mdtrt_cert_type", Constant.UnifiedPay.CKLX.DZPZ);
            // 传值证件号码
            visitMap.put("mdtrt_cert_no", insureIndividualBasicDTO.getBka896());

            // 查询电子凭证系统参数读卡政策，湖南省需要证件号码/类型/姓名
            Map<String, Object> tempMap = new HashMap<>();
            tempMap.put("hospCode", map.get("hospCode"));
            tempMap.put("code", "QrCodePolicy");
            SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(tempMap).getData();
            String QrCodePolicy = sys.getValue();
            if (Constant.UnifiedPay.QrCodePolicy.HN.equals(QrCodePolicy)) {
                // 证件类型
                visitMap.put("psn_cert_type", insureIndividualBasicDTO.getPsnCertType());
                // 传值姓名
                visitMap.put("psn_name", insureIndividualBasicDTO.getAac003());
                // 传值证件号码
                visitMap.put("certno", insureIndividualBasicDTO.getAac002());
            }
        }
        // 身份证  本地社保卡 和省内异地社保卡
        else if (Constant.UnifiedPay.CKLX.SFZ.equals(mdtrtCertType) || Constant.UnifiedPay.CKLX.BDSBK.equals(mdtrtCertType)) {
            // 就诊凭证类型  传值02
            visitMap.put("mdtrt_cert_type", Constant.UnifiedPay.CKLX.SFZ);
            if ("null".equals(insureIndividualBasicDTO.getAac002()) || StringUtils.isEmpty(insureIndividualBasicDTO.getAac002())) {
                // 传值证件号码
                visitMap.put("mdtrt_cert_no", insureIndividualBasicDTO.getBka896());
            } else {
                // 传值证件号码
                visitMap.put("mdtrt_cert_no", insureIndividualBasicDTO.getAac002());
            }
            // 传值姓名
            visitMap.put("psn_name", "null".equals(insureIndividualBasicDTO.getAac003()) ? "" : insureIndividualBasicDTO.getAac003());
            // 传值证件号码
            visitMap.put("certno", "null".equals(insureIndividualBasicDTO.getAac002()) ? "" : insureIndividualBasicDTO.getAac002());
        }
        // 跨省异地读卡
        else if (Constant.UnifiedPay.CKLX.YDSBK.equals(mdtrtCertType)) {
            // 就诊凭证类型  传值03
            visitMap.put("mdtrt_cert_type", mdtrtCertType);
            // 传值证件号码
            visitMap.put("mdtrt_cert_no", insureIndividualBasicDTO.getBka896());
            // 传值社保卡识别码
            visitMap.put("card_sn", insureIndividualBasicDTO.getCardIden());
            visitMap.put("psn_cert_type", "01");
            // 传值姓名
            visitMap.put("psn_name", insureIndividualBasicDTO.getAac003());
            // 传值证件号码
            visitMap.put("certno", insureIndividualBasicDTO.getAac002());
        }
        // 澳门证件类型  香港
        else if (Constant.UnifiedPay.CKLX.AM.equals(mdtrtCertType) || Constant.UnifiedPay.CKLX.XG.equals(mdtrtCertType)) {
            // 就诊凭证类型
            visitMap.put("mdtrt_cert_type", mdtrtCertType);
            // 传值证件号码
            visitMap.put("mdtrt_cert_no", insureIndividualBasicDTO.getBka896());
            // 传值证件号码
            visitMap.put("card_sn", insureIndividualBasicDTO.getCardIden());
            // 传值05 或 04
            visitMap.put("psn_cert_type", insureIndividualBasicDTO.getBka895());
            // 传值证件号码
            visitMap.put("certno", insureIndividualBasicDTO.getAac002());
            // 传值姓名
            visitMap.put("psn_name", insureIndividualBasicDTO.getAac003());
        }
        // 港澳台
        else if (Constant.UnifiedPay.CKLX.GAT.equals(mdtrtCertType)) {
            // 就诊凭证类型
            visitMap.put("mdtrt_cert_type", mdtrtCertType);
            // 传值证件号码
            visitMap.put("mdtrt_cert_no", insureIndividualBasicDTO.getBka896());
            // 传值证件号码
            visitMap.put("card_sn", insureIndividualBasicDTO.getCardIden());
            // 传值05 或 04
            visitMap.put("psn_cert_type", insureIndividualBasicDTO.getBka895());
            // 传值证件号码
            visitMap.put("certno", insureIndividualBasicDTO.getAac002());
            // 传值姓名
            visitMap.put("psn_name", insureIndividualBasicDTO.getAac003());
        }
        // 其他证件
        else {
            // 就诊凭证类型  传值05
            visitMap.put("mdtrt_cert_type", mdtrtCertType);
            // 传值证件号码
            visitMap.put("mdtrt_cert_no", insureIndividualBasicDTO.getBka896());
            // 传值99
            visitMap.put("psn_cert_type", "99");
            // 传值证件号码
            visitMap.put("certno", insureIndividualBasicDTO.getBka896());
            // 传值姓名
            visitMap.put("psn_name", insureIndividualBasicDTO.getAac003());
        }
        checkRequest(visitMap);
        map.put("dataMap", visitMap);
        map.put("infno",Constant.UnifiedPay.REGISTER.UP_1101);
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkRequest(Map param) {
        String mdtrtCertType = MapUtils.get(param, "mdtrt_cert_type");
        if ("03".equals(mdtrtCertType)) {
            if (StringUtils.isEmpty(MapUtils.get(param, "card_sn"))) {
                throw new BizRtException(-1, "使用社保卡时，卡识别码不能为空");
            }
        }
        return true;
    }

}
