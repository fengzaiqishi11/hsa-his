package cn.hsa.module.insure.module.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.insure.module.entity
 * @class_name: InsurePathologicalReport
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/9/4 16:37
 * @Company: 创智和宇
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsurePathologicalReportDO extends PageDO implements Serializable {


    private String id; //主键id
    private String hospCode; // 医院编码
    private String visitId; // 就诊id
    private String mdtrtId; // 就医登记号
    private String psnNo; // 人员编号
    private String palgNo; //病理号
    private String frozenNo; // 冰冻号
    private Date cmaDate; // 送检日期
    private Date rpotDate; // 报告日期
    private String cmaMatl; // 送检材料
    private String clncDise; // 临床诊断
    private String examFnd; //检查所见
    private String sabc; //免疫组化
    private String palgDiag; //病理诊断
    private String rpotDoc; // 报告医师

    private String valiFlag; // 是否有效
    private String isTrans; // 是否上传

}
