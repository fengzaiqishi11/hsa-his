package cn.hsa.module.phar.pharoutdistribute.dto;

import cn.hsa.module.phar.pharoutdistribute.entity.PharOutDistributeAllDetailDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.phar.pharoutdistribute.dto
 * @Class_name: PharOutDistributeBatchDetailDTO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/5/20 15:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class PharOutDistributeAllDetailDTO extends PharOutDistributeAllDetailDO implements Serializable {
    private static final long serialVersionUID = -3067127187289573963L;
    private List<String> ids;
    // 可退药数量
    private BigDecimal backNumOk;
    private String keyword;
    // 开方科室
    private String deptId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endTime;
    // 开方科室名称
    private String deptName;
    // 患者信息
    private String patientInfo;
    /**
     * 发药药房id
     */
    private String pharId;

}
