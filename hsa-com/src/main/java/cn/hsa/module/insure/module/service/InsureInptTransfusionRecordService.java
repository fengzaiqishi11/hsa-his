package cn.hsa.module.insure.module.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureInptTransfusionRecordDTO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;
import java.util.Map;

/**
 * 医保病人输血记录服务层接口
 * @author luonianxin
 */
@FeignClient(value = "hsa-insure")
public interface InsureInptTransfusionRecordService {

    /**
     *  新增医保病人输血信息
     * @param map 输血信息传输实体对象
     * @return  WrapperResponse<Boolean> 执行结果
     */
    WrapperResponse<Boolean> insertInsureInptTransfusionRecord(Map map);


    /**
     * 编辑医保输血信息记录
     *
     * @param map 输血信息传输实体对象
     * @return 是否成功
     */

    WrapperResponse<Boolean> updateInsureInptTransfusionRecord(Map map);


    /**
     * 更新记录传输状态
     *
     * @param map 输血信息传输实体对象
     * @return 是否成功
     */

    WrapperResponse<Boolean> updateInsureTransfusionRecordTransferred(Map map) ;


    /**
     *  查询病人输血信息列表
     * @param param 参数
     * @return java.util.List 病人输血信息列表
     */
    WrapperResponse<List<InsureInptTransfusionRecordDTO>> queryInsureInptTransfusionRecords(Map<String,Object> param);
    /** 传输信息到统一支付平台 **/
    WrapperResponse<Boolean> updateTransferInsureInptTranfusionRecords(Map<String,Object> param);

}
