package cn.hsa.module.inpt.inspectionreport.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Class_name: ExaminationItem
 * @Describe: 检验检测具体项目传输对象
 * @author: luonianxin
 * @Email: nianxin.luo@powersi.com
 * @Date: 2021/5/18 10:09
 **/

@Data
@NoArgsConstructor
@ToString
public class ExaminationItem implements Serializable {

    private static final long serialVersionUID = 3054665903035376177L;

    /** 对应医嘱/处方明细ID **/
    private String opdId;
    /** 医嘱id/处方id **/
    private String adviceId;
    /** 申请单号 **/
    private String applyNo;
    /** 就诊ID **/
    private String visitId;
    /** 住院号/就诊号 **/
    private String inNo;
    /** 项目id **/
    private String itemId;
    /** 项目名称 **/
    private String itemName;
    /** 结果 **/
    private String result;
    /** 结果描述（说明） **/
    private String resultDetail;
    /** 参考值范围 **/
    private String referenceValue;
    /** 结果单位 **/
    private String resultUnit;
    /** 创建时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crteTime;
    /** 是否偏高偏低 **/
    private String isPositive;
    /**
     * 检验项目名称
     */
    private String adviceName;
    /**
     * 送检科室名称
     * */
    private String deptName;

    private String zhId;

}
