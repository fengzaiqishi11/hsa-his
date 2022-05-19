package cn.hsa.module.interf.healthInfo.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 电子病历索引表信息实体类(TbDzblsyxx)
 * @author liuliyun
 * @since 2022-05-16 11:43:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbDzblsyxx implements Serializable {
    private static final long serialVersionUID = 194403374321870347L;
    /**
     * 病历文档流水号
     */
    private String blwdlsh;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 患者类型代码
     */
    private String hzlxdm;
    /**
     * 住院流水号/门（急）诊流水号
     */
    private String jzlsh;
    /**
     * 文档类型编码
     */
    private String wdlxbm;
    /**
     * 患者机构内唯一id
     */
    private String hzjgnwyid;
    /**
     * 医疗机构名称
     */
    private String yljgmc;
    /**
     * 姓名
     */
    private String xm;
    /**
     * 身份证号码
     */
    private String sfzh;
    /**
     * 联系电话
     */
    private String lxdh;
    /**
     * 性别代码
     */
    private String xbdm;
    /**
     * 性别名称
     */
    private String xbmc;
    /**
     * 年龄
     */
    private String nl;
    /**
     * 证件类型
     */
    private String zjlx;
    /**
     * 证件号
     */
    private String zjh;
    /**
     * 出生日期
     */
    private Date csrq;
    /**
     * 病历文档类型编码
     */
    private String blwdlxbm;
    /**
     * 病历文档标题
     */
    private String blwdbt;
    /**
     * 书写时间
     */
    private Date sxsj;
    /**
     * 病历书写医生姓名
     */
    private String blsxysxm;
    /**
     * 病历书写医生代码
     */
    private String blsxysdm;
    /**
     * 就诊科室编码
     */
    private String jzksbm;
    /**
     * 就诊科室名称
     */
    private String jzksmc;
    /**
     * 主治医师编码
     */
    private String zzysbm;
    /**
     * 主治医师名称
     */
    private String zzysmc;
    /**
     * 经治医师编码
     */
    private String jzysbm;
    /**
     * 经治医师名称
     */
    private String jzysmc;
    /**
     * 书写状态
     */
    private String sxzt;
    /**
     * 有效标志
     */
    private String yxbz;
    /**
     * 入院日期/就诊日期
     */
    private Date jzrq;
    /**
     * 病历系统厂商标识
     */
    private String blxtcsbz;
    /**
     * 病历报告格式
     */
    private String blbggs;
    /**
     * 病历报告（内容）
     */
    private byte[] blbg;
    /**
     * 病历文件路径
     */
    private String blwjlj;
    /**
     * 病历文件组名
     */
    private String blwjzm;
    /**
     * 病历文件后缀名
     */
    private String blwjhzm;
    /**
     * 数据有效标志
     */
    private String validflag;
    /**
     * 数据产生时间
     */
    private Date appetime;
    /**
     * 最后修改时间
     */
    private Date modifytime;
    /**
     * 最后修改人编码
     */
    private String modifytcode;
    /**
     * 最后修改人名称
     */
    private String modifytname;


}
