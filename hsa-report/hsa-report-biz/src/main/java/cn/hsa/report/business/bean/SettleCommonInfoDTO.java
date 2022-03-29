package cn.hsa.report.business.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName SettleCommonInfoDTO
 * @Deacription 结算通用信息dto层
 * @Author liuzhuoting
 * @Date 2021-12-01
 * @Version 1.0
 **/
@Data
public class SettleCommonInfoDTO implements Serializable {

    private String msgId;

    private SettleInfoResDTO settleInfo;

    private List<SettleInfoDetailResDTO> settleInfoDetail;

}
