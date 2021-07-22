package cn.hsa.module.oper.operInforecord.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;

import java.util.List;
import java.util.Map;

public interface OperInfoRecordBO {




    Boolean saveSurgery(OperInfoRecordDTO operInfoRecordDTO);
    Boolean updateSurgeryByAdviceId(Map map);

    Boolean updateSurgeryStatus(OperInfoRecordDTO operInfoRecordDTO);

    Boolean updateSurgeryInfo(OperInfoRecordDTO operInfoRecordDTO);

    PageDTO queryOperInfoRecordList(OperInfoRecordDTO operInfoRecordDTO);

    Boolean queryOperInfoRecordIsRepeated(OperInfoRecordDTO operInfoRecordDTO);
    OperInfoRecordDTO getOperInfoById(OperInfoRecordDTO operInfoRecordDTO);

    Boolean updateSurgeryCompleteToCancel(OperInfoRecordDTO operInfoRecordDTO);
}
