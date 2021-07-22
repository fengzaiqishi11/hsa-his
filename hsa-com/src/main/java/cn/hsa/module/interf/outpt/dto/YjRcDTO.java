package cn.hsa.module.interf.outpt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class YjRcDTO extends YjRcDTODetail implements Serializable {

    private static final long serialVersionUID = -2122671344739328200L;
    /**
     * 医院编码
     */
    private String hospCode;
    //业务ID
    private String ywid;
    //用户ID
    private String yhid;
    //婚姻
    private String hyid;
    //签名
    private String sign;
    //业务ID
    private String brid;
    //姓名
    private String xm;
    //身份证
    private String sfzh;
    //性别
    private String xbid;
    //血型
    private String xxid;
    //民族
    private String mzid;
    //联系电话
    private String lxdh;
    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date csrq;
    //过敏史
    private String gms;
    //工作单位
    private String gzdw;
    //现住地址
    private String zxdz;
    //医保卡号
    private String ybkh;
    //病历号
    private String blh;
    //病历号
    private String jzksid;
    //病历号
    private String mzysid;
    //病历号
    private String mzicdmc;
    //病历号
    private String tz;
    //病历号
    private String sg;
    //病历号
    private String cfzbz;
    //病历号
    private String jzdjryid;

    //就诊科室ID
    private String jzid;
    //就诊医生ID
    private String jzysid;
    //医嘱录入人员ID，如果为就诊医生，填写就诊医生ID
    private String lrryid;
    //处方模板明细
    List<YjRcDTODetail> yjRcDTODetail;

    private String visitId; // 就诊id

    private String cfxxList; // 处方明细list

    private String itemId; // 项目id

    private String deptId; // 科室id

    private String brxm; // 病人姓名

    private String brxb; // 病人性别

    private String cfdh; // 处方单号

    private String opId; // 处方id

    private String tx_date;//就诊时间

    private String outProfile;//档案号

    private String brnl;//病人年龄
}
