package cn.hsa.oper.operpatientrecord.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.oper.operpatientrecord.bo.OperPatientRecordBO;
import cn.hsa.module.oper.operpatientrecord.dto.OperPatientRecordDTO;
import cn.hsa.module.oper.operpatientrecord.service.OperPatientRecordService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@HsafRestPath("/service/oper/operPatientRecord")
@Service("operPatientRecordService_provider")
public class OperPatientRecordServiceImpl implements OperPatientRecordService {

    @Resource
   private OperPatientRecordBO operPatientRecordBO;

    /**
       * @Method queryOperPatientPage
       * @Describe: 根据病人信息传输对象DTO来查询病人信息
       * @Author: luonianxin
       * @Date: 2021/4/23 14:19
    **/
    @Override
    public WrapperResponse<PageDTO> queryOperPatientPage(Map map) {
        OperPatientRecordDTO operPatientRecordDTO = MapUtils.get(map,"operPatientRecordDTO");
        return WrapperResponse.success(operPatientRecordBO.queryOperPatientRecords(operPatientRecordDTO));
    }

    /**
     * 获取非手术疾病信息
     *
     * @param map service层统一调用参数
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/24 15:13
     */
    @Override
    public WrapperResponse<PageDTO> getNonSurgicalDiseasesInfo(Map map) {
        return WrapperResponse.success(operPatientRecordBO.getNonSurgicalDiseasesInfo(map));
    }
}
