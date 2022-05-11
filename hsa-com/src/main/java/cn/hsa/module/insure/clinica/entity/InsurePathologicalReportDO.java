package cn.hsa.module.insure.clinica.entity;

import java.util.Date;
import java.io.Serializable;

import cn.hsa.base.PageDO;
import lombok.Data;

/**
* @ClassName InsurePathologicalReportDO
* @Deacription 病理检查报告记录信息表
* @Author liuhuiming
* @Date 2022-05-10
* @Version 1.0
**/
@Data
public class InsurePathologicalReportDO extends PageDO implements Serializable {

    /**
     * 主键
     */
    private Long uuid;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 就医流水号
     */
    private String mdtrtSn;
    /**
     * 就诊id
     */
    private String mdtrtId;
    /**
     * 人员编号
     */
    private String psnNo;
    /**
     * 住院号/就诊号
     */
    private String visitNo;
    /**
     * 病理号
     */
    private String palgNo;
    /**
     * 冰冻号
     */
    private String frozenNo;
    /**
     * 送检日期
     */
    private Date cmaDate;
    /**
     * 报告日期
     */
    private Date rpotDate;
    /**
     * 送检材料
     */
    private String cmaMatl;
    /**
     * 临床诊断
     */
    private String clncDise;
    /**
     * 检查所见
     */
    private String examFnd;
    /**
     * 免疫组化
     */
    private String sabc;
    /**
     * 病理诊断
     */
    private String palgDiag;
    /**
     * 报告医师
     */
    private String rpotDoc;
    /**
     * 有效标志
     */
    private String valiFlag;
    /**
     * 是否上传
     */
    private String isUplod;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 修改时间
     */
    private String updateTime;

}
