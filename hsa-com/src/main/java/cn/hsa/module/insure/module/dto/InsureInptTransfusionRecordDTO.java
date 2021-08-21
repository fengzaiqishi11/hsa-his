package cn.hsa.module.insure.module.dto;

import cn.hsa.module.insure.module.entity.InsureInptTransfusionRecordDO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 *  医保输血信息传输实体对象
 * @author luonianxin
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class InsureInptTransfusionRecordDTO extends InsureInptTransfusionRecordDO implements Serializable {

    private static final long serialVersionUID = -1837084634392858935L;
    /** 查询关键字 **/
    private String keyword;
    /** 查询开始时间 **/
    private String startTime;
    /** 查询结束时间 **/
    private String endTime;
}
