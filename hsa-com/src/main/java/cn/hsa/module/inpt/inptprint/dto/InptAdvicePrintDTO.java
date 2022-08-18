package cn.hsa.module.inpt.inptprint.dto;

import cn.hsa.module.inpt.inptprint.entity.InptAdvicePrintDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.inpt.inptprint.dto
 * @Class_name: InptAdvicePrintDTO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/1/15 10:08
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InptAdvicePrintDTO extends InptAdvicePrintDO implements Serializable {
    private static final long serialVersionUID = -58618438095047232L;
    private List<String> ids;
    private String startOnlyDate;
    private String startOnlyTime;
    private String stopOnlyDate;
    private String stopOnlyTime;
    private String printFlag;
    private String ids1;
    private String flag;
    private String deleteIds;
    private String execTimeNoM;
    private String itemCode;
    private String itemId;
    private String yylx;
    private String typeCode;
    private String totalNum;
    private String totalNumUnitCode;
    private String useCode;
    private String rateName;
    private String dosage;
    private String dosageUnitCode;
    private BigDecimal herbNum;
    /**
     * 婴儿Id
     */
    private String babyId;
    /**
     * 是否查询婴儿
     */
    private String queryBaby;

    /**
     * 药品大类
     */
    private String bigTypeCode;

    // 医嘱是否停自身
    private String isStopMyself;

    private String  teachDoctorId ;

    private String  teachDoctorName ;

    private String itemName;

    private String remark;

    private String useDays;
    /**
     * 停嘱带教医生id
     */
    private String stopTeachDoctorId;
    /**
     * 停嘱带教医生名称
     */
    private String stopTeachDoctorName;
}
