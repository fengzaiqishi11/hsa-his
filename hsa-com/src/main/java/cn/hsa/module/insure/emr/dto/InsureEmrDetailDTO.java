package cn.hsa.module.insure.emr.dto;


import java.io.Serializable;

import cn.hsa.base.PageDO;
import lombok.Data;

/**
 * @ClassName InsureEmrUnifiedDTO
 * @Deacription 医保电子病历上传dto层
 * @Author liuhuiming
 * @Date 2022-03-25
 * @Version 1.0
 **/
@Data
public class InsureEmrDetailDTO implements Serializable {

    /**
     * 入院记录
     */
    private InsureEmrAdminfoDTO insureEmrAdminfoDTO;

    /**
     * 病程记录
     */
    private InsureEmrCoursrinfoDTO insureEmrCoursrinfoDTO;

    /**
     * 死亡记录
     */
    private InsureEmrDieinfoDTO insureEmrDieinfoDTO;

    /**
     * 诊断信息
     */
    private InsureEmrDiseinfoDTO insureEmrDiseinfoDTO;

    /**
     * 出院记录
     */
    private InsureEmrDscginfoDTO insureEmrDscginfoDTO;

    /**
     * 手术记录
     */
    private InsureEmrOprninfoDTO insureEmrOprninfoDTO;

    /**
     * 病情抢救记录
     */
    private InsureEmrRescinfoDTO insureEmrRescinfoDTO;
}
