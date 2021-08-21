package cn.hsa.module.insure.module.dao;

import cn.hsa.module.insure.module.dto.InsureInptTransfusionRecordDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *  医保输血信息数据库访问实体
 * @author luonianxin
 */
public interface InsureInptTransfusionRecordDAO {

    /**
     *  新增一条输血记录
     * @param insureInptTransfusionRecordDTO
     * @return 受影响的行数
     */
    Integer insertInsureInptTransfusionRecord(InsureInptTransfusionRecordDTO insureInptTransfusionRecordDTO);

    /**
     *  查询病人输血记录列表
     * @param insureInptTransfusionRecordDTO
     * @return java.util.List 输血记录对象列表
     */
    List<InsureInptTransfusionRecordDTO> queryInsureInptTransfusionRecords(Map<String,Object> insureInptTransfusionRecordDTO);

    List<Map> queryInsureInptTransfusionRecordsMap(@Param("hospCode") String hospCode, @Param("idList") List<String> idList);
    /**
     *  更新输血记录
     * @param insureInptTransfusionRecordDTO
     * @return 受影响的行数
     */
    Integer updateInsureInptTransfusionRecord(InsureInptTransfusionRecordDTO insureInptTransfusionRecordDTO);
    /**
     *  更新传输状态
     * @param insureInptTransfusionRecordDTO s
     * @return 受影响的行数
     */
    Integer updateInsureTransfusionRecordTransferred(InsureInptTransfusionRecordDTO insureInptTransfusionRecordDTO);
}
