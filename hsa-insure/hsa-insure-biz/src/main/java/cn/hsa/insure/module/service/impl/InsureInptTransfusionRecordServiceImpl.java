package cn.hsa.insure.module.service.impl;

import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.insure.module.bo.InsureInptTransfusionRecordBO;
import cn.hsa.module.insure.module.dto.InsureInptTransfusionRecordDTO;
import cn.hsa.module.insure.module.service.InsureInptTransfusionRecordService;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 医保病人输血信息传输服务层
 * @Author: luonianxin
 * @Eamil: nianxin.luo@powersi.com.cn
 * @Date: 2020/11/18 20:19
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/insure/insureInptTranfusionRecord")
@Service("insureInptTransfusionRecordService_provider")
public class InsureInptTransfusionRecordServiceImpl implements InsureInptTransfusionRecordService {

   @Resource
   private InsureInptTransfusionRecordBO insureInptTransfusionRecordBO;

    /**
     * 新增医保病人输血信息
     *
     * @param param 输血信息传输实体对象
     * @return WrapperResponse<Boolean> 执行结果
     */
    @Override
    public WrapperResponse<Boolean> insertInsureInptTransfusionRecord(Map param) {
        InsureInptTransfusionRecordDTO insureInptTransfusionRecordDTO = MapUtils.get(param,"insureInptTransfusionRecordDTO");
        if(null == insureInptTransfusionRecordDTO) {
            throw new AppException("必传参数不可为空！");
        }
        return WrapperResponse.success(insureInptTransfusionRecordBO.insertInsureInptTransfusionRecord(insureInptTransfusionRecordDTO));
    }

    /**
     * 编辑医保输血信息记录
     *
     * @param param 输血信息传输实体对象
     * @return 是否成功
     */
    @Override
    public WrapperResponse<Boolean> updateInsureInptTransfusionRecord(Map param) {
        InsureInptTransfusionRecordDTO insureInptTransfusionRecordDTO = MapUtils.get(param,"insureInptTransfusionRecordDTO");
        if(null == insureInptTransfusionRecordDTO) {
            throw new AppException("必传参数不可为空！");
        }
        return WrapperResponse.success(insureInptTransfusionRecordBO.updateInsureInptTransfusionRecord(insureInptTransfusionRecordDTO));
    }

    /**
     * 更新记录传输状态
     *
     * @param param 输血信息传输实体对象
     * @return 是否成功
     */
    @Override
    public WrapperResponse<Boolean> updateInsureTransfusionRecordTransferred(Map param) {
        InsureInptTransfusionRecordDTO insureInptTransfusionRecordDTO = MapUtils.get(param,"insureInptTransfusionRecordDTO");
        if(null == insureInptTransfusionRecordDTO) {
            throw new AppException("必传参数不可为空！");
        }
        return WrapperResponse.success(insureInptTransfusionRecordBO.updateInsureInptTransfusionRecord(insureInptTransfusionRecordDTO));
    }

    /**
     * 查询病人输血信息列表
     *
     * @param param 参数
     * @return java.util.List 病人输血信息列表
     */
    @Override
    public WrapperResponse<List<InsureInptTransfusionRecordDTO>> queryInsureInptTransfusionRecords(Map<String, Object> param) {
        return WrapperResponse.success(insureInptTransfusionRecordBO.queryInsureInptTransfusionRecords(param));
    }

    /**
     * 传输信息到统一支付平台
     * @param param ids 主键id，可以传多个以逗号隔开
     **/
    @Override
    public WrapperResponse<Boolean> transferInsureInptTranfusionRecords(Map<String, Object> param) {
        return WrapperResponse.success(insureInptTransfusionRecordBO.transferInsureInptTranfusionRecords(param));
    }

}
