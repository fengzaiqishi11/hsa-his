package cn.hsa.report.business.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName DiseInfoReqDTO
 * @Deacription 人员累计信息查询请求dto层
 * @Author liuzhuoting
 * @Date 2021-08-16
 * @Version 1.0
 **/
@Data
public class PsnCumulationReqDTO implements Serializable {

    /**
     * 人员编号
     */
    @JSONField(name = "psn_no")
    private String psnNo;

    /**
     * 累计年月
     */
    @JSONField(name = "cum_ym")
    private String cumYm;

    /**
     * 就诊ID
     */
    @JSONField(name = "mdtrtId")
    private String mdtrtId;

}
