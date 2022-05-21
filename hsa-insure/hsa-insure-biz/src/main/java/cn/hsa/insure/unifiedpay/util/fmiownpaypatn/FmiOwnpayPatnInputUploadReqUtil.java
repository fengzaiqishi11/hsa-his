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
import cn.hsa.module.insure.module.dao.InsureGetInfoDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.module.insure.module.dto.InsureSettleInfoDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName PsnInfoReqUtil
 * @Deacription 人员信息获取-1101
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_4201A)
public class FmiOwnpayPatnInputUploadReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        Map map = (Map) param;
        InsureConfigurationDTO insureConfigurationDTO =  (InsureConfigurationDTO) map.get("insureConfigurationDTO");

        InsureSettleInfoDTO insureSettleInfoDTO = (InsureSettleInfoDTO) map.get("insureSettleInfoDTO");

        String visitId = (String) map.get("visitId");

        List<Map<String, Object>> feeList = MapUtils.get(map, "mapList");

        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        isInsureUnifiedMap.put("code", "JG_NAME");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        if (sysParameterDTO == null) {
            throw new AppException("请先配置默认的医疗机构编码参数信息:编码为:HOSP_INSURE_CODE,值为对应的医疗机构编码值");
        }

        Map<String, Object> dataMap = new HashMap<>(3);
        dataMap.put("fsiOwnpayPatnFeeListDDTO",HumpUnderlineUtils.humpToUnderlineArray((List<FmiOwnpayPatnFeeListDDTO>)initFeeListDDTO(insureSettleInfoDTO, feeList,insureConfigurationDTO,sysParameterDTO.getValue(),visitId)));

        HashMap commParam = new HashMap();
        checkRequest(dataMap);
        commParam.put("input", dataMap);
        commParam.put("infno",Constant.UnifiedPay.REGISTER.UP_4201A);

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

    private Object initFeeListDDTO(InsureSettleInfoDTO insureSettleInfoDTO,List<Map<String, Object>> feeList, InsureConfigurationDTO insureConfigurationDTO
                                        , String fixmedinsName,String visitId) {
        List<FmiOwnpayPatnFeeListDDTO> listMap = new ArrayList<>();
        if (!ListUtils.isEmpty(feeList)) {
            for (Map<String, Object> item : feeList) {
                // 医保上传
                FmiOwnpayPatnFeeListDDTO feedetail = new FmiOwnpayPatnFeeListDDTO();
                String doctorId = MapUtils.get(item, "doctorId");
                String doctorName = MapUtils.get(item, "doctorName");

                feedetail.setBkkpSn(MapUtils.get(item, "id"));
                feedetail.setFixmedinsMdtrtId(visitId);

                if (insureSettleInfoDTO.getLx().equals("1")) {

                    feedetail.setFeeOcurTime( DateUtils.format((Date) item.get("costTime"), DateUtils.Y_M_DH_M_S)); // 费用发生时间

                } else if (insureSettleInfoDTO.getLx().equals("0")) {
                    feedetail.setFeeOcurTime( DateUtils.format((Date) item.get("crteTime"), DateUtils.Y_M_DH_M_S)); // 费用发生时间

                }
                feedetail.setFixmedinsCode(insureConfigurationDTO.getOrgCode());
                feedetail.setFixmedinsName(fixmedinsName);
                BigDecimal cnt = BigDecimalUtils.scale((BigDecimal) item.get("totalNum"), 4);
                BigDecimal price = BigDecimalUtils.scale((BigDecimal) item.get("price"), 4);
                feedetail.setCnt(cnt); // 数量
                feedetail.setPric(price); // 单价

                DecimalFormat df1 = new DecimalFormat("0.00");
                String realityPrice = df1.format(BigDecimalUtils.convert(item.get("realityPrice").toString()));
                BigDecimal convertPrice = BigDecimalUtils.convert(realityPrice);
                feedetail.setDetItemFeeSumamt(convertPrice); // 明细项目费用总额

                feedetail.setMedListCodg(item.get("insureItemCode") == null ? "" : item.get("insureItemCode").toString()); // 医疗目录编码
                feedetail.setMedinsListCodg(item.get("hospItemCode") == null ? "" : item.get("hospItemCode").toString()); // 医药机构目录编码
                feedetail.setMedinsListName(MapUtils.get(item, "insureItemName")); // 医药机构目录名称
                feedetail.setMedChrgitmType(MapUtils.get(item, "insureItemType")); // 医疗收费项目类别
                feedetail.setProdname(MapUtils.get(item, "insureItemName")); // 商品名

                feedetail.setBilgDeptCodg(MapUtils.get(item, "deptId")); // 开单科室编码
                feedetail.setBilgDeptName(MapUtils.get(item, "deptName")); // 开单科室名称

                if (StringUtils.isEmpty(MapUtils.get(item, "deptId"))) {
                    feedetail.setBilgDrCodg( doctorId); // 开单医生编码
                } else {
                    feedetail.setBilgDrCodg( MapUtils.get(item, "deptId")); // 开单医生编码
                }
                if (StringUtils.isEmpty(MapUtils.get(item, "doctorName"))) {
                    feedetail.setBilgDrName( doctorName); // 开单医生编码
                } else {
                    feedetail.setBilgDrName( MapUtils.get(item, "doctorName")); // 开单医生姓名
                }

                feedetail.setOrdersDeptCode(null);
                feedetail.setOrdersDeptName(null);
                feedetail.setOrdersDrCode(null);
                feedetail.setOrdersDrName(null);

                feedetail.setTcmdrugUsedWay(null);
                feedetail.setExtinsFlag(null);
                feedetail.setExtinsHospCode(null);

                feedetail.setDscgTkdrugFlag(null);
                feedetail.setSinDosDscr(null);
                feedetail.setUsedFrquDscr(null);
                feedetail.setPrdDays(null);
                feedetail.setMedcWayDscr(null);
                feedetail.setMemo(null);
                feedetail.setMemo(null);

                listMap.add(feedetail);
            }

        }

        return listMap;
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}
