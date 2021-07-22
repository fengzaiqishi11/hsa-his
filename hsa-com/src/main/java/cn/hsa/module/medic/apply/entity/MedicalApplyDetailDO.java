package cn.hsa.module.medic.apply.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name
 * @class_nameMedicalApplyDO
 * @Description 申请明细
 * @Author youxianlin
 * @Email: 254580179@qq.com
 * @Date2020/12/11 10:21
 * @Company 创智和宇
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MedicalApplyDetailDO extends PageDO implements Serializable {

    private static final long serialVersionUID = 217448257180401094L;

    private String id;

    private String hospCode;

    private String applyId;

    private String visitId;

    private String opdId;

    private String adviceCode;

    private String deptId;
    private String orderNo;
    private String content;
    private String remark;
    private String blobId;

}
