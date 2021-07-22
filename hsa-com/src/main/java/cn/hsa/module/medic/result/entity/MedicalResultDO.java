package cn.hsa.module.medic.result.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
* @Package_name: cn.hsa.module.medic.result.entity
* @class_name: MedicalResultDTO
* @Description: lis/pacs结果表
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2021/1/28 15:06
* @Company: 创智和宇
**/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MedicalResultDO extends PageDO implements Serializable {

    private static final long serialVersionUID = 217448257180401094L;

    private String id;

    private String hospCode;

    private String visitId;
    private String inNo;
    private String adviceId;
    private String barCode;
    private String typeCode;
    private String itemCode;
    private String itemName;
    private String resultType;
    private String result;
    private String resultDetail;
    private String reporteText;
    private String picUrl;
    private String isPositive;
    private String referenceValue;
    private String resultUnit;
    private String rangeState;
    private String rangeStateColor;
    private String checkPart;
    private String checkWays;
    private String checkType;
    private String diagnosisId;
    private String diagnosisName;
    private String applyId;
    private String patInfoId;
    private String resultId;



    private String crteId;

    private String crteName;

    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
}
