package cn.hsa.module.oper.operInforecord.dao;

import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;

import java.util.List;

public interface OperInfoRecordDAO {

    int insertSurgery(OperInfoRecordDTO operInfoRecordDTO);

    int updateSurgeryStatus(OperInfoRecordDTO operInfoRecordDTO);

    int updateSurgeryInfo(OperInfoRecordDTO operInfoRecordDTO);
    /** 根据医嘱ID更新手术信息 **/
    int updateSurgeryByAdviceId(OperInfoRecordDTO operInfoRecordDTO);

    List<OperInfoRecordDTO> queryOperInfoRecordList(OperInfoRecordDTO operInfoRecordDTO);

    int queryOperInfoRecordIsRepeated(OperInfoRecordDTO operInfoRecordDTO);

    List<OperInfoRecordDTO> queryOperInfoRecordBasic(OperInfoRecordDTO operInfoRecordDTO);

    OperInfoRecordDTO getOperInfoById(OperInfoRecordDTO operInfoRecordDTO);
    OperInfoRecordDTO getOperAccountingByOperId(OperInfoRecordDTO operInfoRecordDTO);

    int updateSurgeryCompleteToCancel(OperInfoRecordDTO operInfoRecordDTO);
}
