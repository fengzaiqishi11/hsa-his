package cn.hsa.module.insure.module.bo;

import cn.hsa.module.insure.module.dto.InsureInptTransfusionRecordDTO;

import java.util.List;
import java.util.Map;

/**
 *  医保输血信息业务层接口
 * @author luonianxin
 */
public interface InsureInptTransfusionRecordBO {
    /**
     *   新增医保输血信息记录
     * @param insureInptTransfusionRecordDTO 输血信息传输实体对象
     * @return 是否成功
     */
    Boolean insertInsureInptTransfusionRecord(InsureInptTransfusionRecordDTO insureInptTransfusionRecordDTO);
    /**
     *   编辑医保输血信息记录
     * @param insureInptTransfusionRecordDTO 输血信息传输实体对象
     * @return 是否成功
     */
    Boolean updateInsureInptTransfusionRecord(InsureInptTransfusionRecordDTO insureInptTransfusionRecordDTO);
    /**
     *   更新记录传输状态
     * @param insureInptTransfusionRecordDTO 输血信息传输实体对象
     * @return 是否成功
     */
    Boolean updateInsureTransfusionRecordTransferred(InsureInptTransfusionRecordDTO insureInptTransfusionRecordDTO);

    Boolean updateTransferInsureInptTranfusionRecords(Map params);

    /**
     *  查询病人输血信息列表
     * @param param 参数
     * @return java.util.List 病人输血信息列表
     */
    List<InsureInptTransfusionRecordDTO> queryInsureInptTransfusionRecords(Map<String,Object>param);
}
