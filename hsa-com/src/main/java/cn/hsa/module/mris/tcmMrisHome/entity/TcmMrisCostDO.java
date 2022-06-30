package cn.hsa.module.mris.tcmMrisHome.entity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.module.mris.tcmMrisHome.entity
 * @Class_name: TcmMrisCostDO
 * @Describe: 中医病案费用信息
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2022/01/17 09:34
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TcmMrisCostDO implements java.io.Serializable {

    private static final long serialVersionUID = 4898966933663813584L;
    /** 主键 */
    private String id;

    /** 医院编码 */
    private String hospCode;

    /** 病案ID（mris_base_info.id） */
    private String mbiId;

    /** 就诊ID */
    private String visitId;

    /** 总费用 */
    private BigDecimal fy01;

    /** 西药费 */
    private BigDecimal fy02;

    /** 中药费 */
    private BigDecimal fy03;

    /** 中成药费 */
    private BigDecimal fy04;

    /** 中草药费 */
    private BigDecimal fy05;

    /** 其他费用 */
    private BigDecimal fy06;

    /** 自费金额 */
    private BigDecimal fy07;

    /** 综合医疗服务类：（1）一般医疗服务费 */
    private BigDecimal zhylfwl01;

    /** 综合医疗服务类：（2）一般治疗操作费 */
    private BigDecimal zhylfwl02;

    /** 综合医疗服务类：（3）护理费 */
    private BigDecimal zhylfwl03;

    /** 综合医疗服务类：（4）其他费用 */
    private BigDecimal zhylfwl04;

    /** 综合医疗服务类：（1）一般医疗服务费（中医辨证论治费） */
    private BigDecimal zhylfwl05;

    /** 综合医疗服务类：（1）一般医疗服务费（中医辨证论治会诊费） */
    private BigDecimal zhylfwl06;

    /** 诊断类：(5) 病理诊断费 */
    private BigDecimal zdl01;

    /** 诊断类：(6) 实验室诊断费 */
    private BigDecimal zdl02;

    /** 诊断类：(7) 影像学诊断费 */
    private BigDecimal zdl03;

    /** 诊断类：(8) 临床诊断项目费 */
    private BigDecimal zdl04;

    /** 治疗类：(9) 非手术治疗项目费 */
    private BigDecimal zll01;

    /** 治疗类：非手术治疗项目费 其中临床物理治疗费 */
    private BigDecimal zll02;

    /** 治疗类：(10) 手术治疗费 */
    private BigDecimal zll03;

    /** 治疗类：手术治疗费 其中麻醉费 */
    private BigDecimal zll04;

    /** 治疗类：手术治疗费 其中手术费 */
    private BigDecimal zll05;

    /** 康复类：(11) 康复费 */
    private BigDecimal kfl01;

    /** 西药类： 西药费 其中抗菌药物费用 */
    private BigDecimal xyl01;

    /** 中药费:医疗机构中药制剂费 */
    private BigDecimal zyf01;

    /** 血液和血液制品类： 血费 */
    private BigDecimal xyzpl01;

    /** 血液和血液制品类： 白蛋白类制品费 */
    private BigDecimal xyzpl02;

    /** 血液和血液制品类： 球蛋白制品费 */
    private BigDecimal xyzpl03;

    /** 血液和血液制品类：凝血因子类制品费 */
    private BigDecimal xyzpl04;

    /** 血液和血液制品类： 细胞因子类费 */
    private BigDecimal xyzpl05;

    private BigDecimal xyzpl06; // 细胞因子类制品费

    /** 耗材类：检查用一次性医用材料费 */
    private BigDecimal hcl01;

    /** 耗材类：治疗用一次性医用材料费 */
    private BigDecimal hcl02;

    /** 耗材类：手术用一次性医用材料费 */
    private BigDecimal hcl03;

    /** 中医类：中医治疗类，中医类总费用 */
    private BigDecimal zyl01;

    /** 中医类：中医诊断费 */
    private BigDecimal zyl02;

    /** 中医类：中医外治费 */
    private BigDecimal zyl03;

    /** 中医类：中医骨伤 */
    private BigDecimal zyl04;

    /** 中医类：针刺与灸法 */
    private BigDecimal zyl05;

    /** 中医类：中医推拿治疗 */
    private BigDecimal zyl06;

    /** 中医类：中医肛肠治疗 */
    private BigDecimal zyl07;

    /** 中医类：中医特殊治疗 */
    private BigDecimal zyl08;

    /** 中医类：中医其他 */
    private BigDecimal zyl09;

    /** 中医类：中药特殊调配加工 */
    private BigDecimal zyl10;

    /** 中医类：辩证施膳 */
    private BigDecimal zyl11;
    /** 中医类：中医和民族医医疗服务费 */
    private BigDecimal zyl12;

}