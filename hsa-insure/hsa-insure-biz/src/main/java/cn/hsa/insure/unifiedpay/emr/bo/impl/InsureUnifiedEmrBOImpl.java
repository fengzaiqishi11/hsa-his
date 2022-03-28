package cn.hsa.insure.unifiedpay.emr.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.enums.FunctionEnum;
import cn.hsa.insure.unifiedpay.bo.impl.InsureItfBOImpl;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.BaseReqUtilFactory;
import cn.hsa.module.insure.emr.bo.InsureUnifiedEmrBO;
import cn.hsa.module.insure.emr.dao.InsureEmrAdminfoDAO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName InsureUnifiedEmrBOImpl
 * @Description TODO
 * @Author qiang.fan
 * @Date 2022/3/25 13:38
 * @Version 1.0
 **/

@Component
public class InsureUnifiedEmrBOImpl implements InsureUnifiedEmrBO {

    @Autowired
    private InsureEmrAdminfoDAO insureEmrAdminfoDAO;

    @Resource
    private InsureItfBOImpl insureItfBO;

    @Resource
    private BaseReqUtilFactory baseReqUtilFactory;

    @Resource
    private InsureIndividualVisitDAO insureIndividualVisitDAO;

    @Override
    public PageDTO queryInsureUnifiedEmrInfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO) {
        // 设置分页信息
        PageHelper.startPage(insureEmrUnifiedDTO.getPageNo(), insureEmrUnifiedDTO.getPageSize());
        List<InsureEmrUnifiedDTO> resultList = insureEmrAdminfoDAO.queryInsureUnifiedEmrInfo(insureEmrUnifiedDTO);
        return PageDTO.of(resultList);
    }

    @Override
    public WrapperResponse updateInsureUnifiedEmrUpload(Map<String, Object> map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);

        //1.组装参数上传，2.修改状态
        Map<String, Object> paramMap = new HashMap<>();
        // 参保地医保区划
        paramMap.put("insuplcAdmdvs", insureIndividualVisitDTO.getInsuplcAdmdvs());
        paramMap.put("orgCode", insureIndividualVisitDTO.getMedicineOrgCode());
        paramMap.put("configCode", insureIndividualVisitDTO.getInsureRegCode());
        paramMap.put("hospCode", MapUtils.get(map, "hospCode"));
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.OUTPATIENT_VISIT.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(MapUtils.get(map, "hospCode"));
        // 调用统一支付平台接口
        insureItfBO.executeInsur(FunctionEnum.INSUR_EMR_UPLOAD, interfaceParamDTO);

        //TODO 2.修改状态
        return null;
    }

    /**
     * @Method commonGetVisitInfo
     * @Desrciption  医保统一支付：住院结算，统一患者就诊信息查询接口
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/2/14 10:57
     * @Return InsureIndividualVisitDTO
     **/
    public  InsureIndividualVisitDTO commonGetVisitInfo(Map<String, Object> map){
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        String hospCode = (String) map.get("hospCode");//医院编码
        String visitId = (String) map.get("visitId");//就诊id
        Map<String, Object> insureVisitParam = new HashMap<String, Object>();
        insureVisitParam.put("id", visitId);
        insureVisitParam.put("medicalRegNo",MapUtils.get(map,"medicalRegNo"));
        insureVisitParam.put("hospCode", hospCode);
        insureIndividualVisitDTO = insureIndividualVisitDAO.getInsureIndividualVisitById(insureVisitParam);
        if (insureIndividualVisitDTO == null || StringUtils.isEmpty(insureIndividualVisitDTO.getId())) {
            throw new AppException("未查找到医保就诊信息，请做医保登记。");
        }
        return insureIndividualVisitDTO;
    }
}
