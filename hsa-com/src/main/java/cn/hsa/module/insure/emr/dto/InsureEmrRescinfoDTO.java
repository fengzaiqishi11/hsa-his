package cn.hsa.module.insure.emr.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

import cn.hsa.module.insure.emr.entity.InsureEmrRescinfoDO;
import lombok.Data;

/**
* @ClassName InsureEmrRescinfoDTO
* @Deacription 医保电子病历上传-病情抢救记录dto层
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
@Data
public class InsureEmrRescinfoDTO extends InsureEmrRescinfoDO implements Serializable {

    /**
     * 主键ID
     */
    private Long uuid;
    /**
     * 源主键ID
     */
    private Long orgUuid;
    /**
     * 就医流水号,院内唯一号
     */
    private String mdtrtSn;
    /**
     * 医保病人必填,医保就诊ID
     */
    private String mdtrtId;
    /**
     * 医保病人必填,医保人员编号
     */
    private String psnNo;
    /**
     * 科室代码
     */
    private String dept;
    /**
     * 科室名称
     */
    private String deptName;
    /**
     * 病区名称
     */
    private String wardareaName;
    /**
     * 病床号
     */
    private String bedno;
    /**
     * 诊断名称
     */
    private String diagName;
    /**
     * 诊断代码
     */
    private String diagCode;
    /**
     * 病情变化情况
     */
    private String condChg;
    /**
     * 抢救措施
     */
    private String rescMes;
    /**
     * 手术操作代码
     */
    private String oprnOprtCode;
    /**
     * 手术操作名称
     */
    private String oprnOprtName;
    /**
     * 手术及操作目标部位名称
     */
    private String oprnOperPart;
    /**
     * 介入物名称
     */
    private String itvtName;
    /**
     * 操作方法
     */
    private String oprtMtd;
    /**
     * 操作次数
     */
    private Integer oprtCnt;
    /**
     * 抢救开始日期时间
     */
    private String rescBegntime;
    /**
     * 抢救结束日期时间
     */
    private String rescEndtime;
    /**
     * 检查/检验项目名称
     */
    private String diseItemName;
    /**
     * 检查/检验结果
     */
    private String diseCcls;
    /**
     * 检查/检验定量结果
     */
    private BigDecimal diseCclsQunt;
    /**
     * 检查/检验结果代码
     */
    private String diseCclsCode;
    /**
     * 注意事项
     */
    private String mnan;
    /**
     * 参加抢救人员名单
     */
    private String rescPsnList;
    /**
     * 专业技术职务类别代码
     */
    private String proftechttlCode;
    /**
     * 医师编号
     */
    private String docCode;
    /**
     * 医师姓名
     */
    private String drName;
    /**
     * 有效标志
     */
    private String valiFlag;
    /**
     * 数据来源：1.HIS，2.手动添加
     */
    private String source;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 修改时间
     */
    private String updateTime;
    /**
     * 医院编码
     */
    private String hospCode;

}
