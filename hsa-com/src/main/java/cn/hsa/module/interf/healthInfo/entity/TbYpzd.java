package cn.hsa.module.interf.healthInfo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 药品字典(TbYpzd)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:02
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbYpzd implements Serializable {
    private static final long serialVersionUID = -26497988157887626L;
    /**
     * 机构编码
     */
    private String yljgdm;
    /**
     * 药品编码
     */
    private String ypbm;
    /**
     * 药品名称
     */
    private String ypmc;
    /**
     * 药品通用名称
     */
    private String ypsymc;
    /**
     * 药品规格
     */
    private String ypgg;
    /**
     * 药品剂型代码
     */
    private String ypjxdm;
    /**
     * 药品剂型
     */
    private String ypjxms;
    /**
     * 药品剂量单位
     */
    private String ypjldw;
    /**
     * 药品用法代码
     */
    private String ypyfdm;
    /**
     * 药品用法
     */
    private String ypyfms;
    /**
     * 发药单位
     */
    private String fydw;
    /**
     * 最小单位
     */
    private String zxdw;
    /**
     * 包装单位
     */
    private String bzdw;
    /**
     * 拆分数量
     */
    private BigDecimal cfsl;
    /**
     * 包装批发价
     */
    private BigDecimal bzpfj;
    /**
     * 包装零售价
     */
    private BigDecimal bzlsj;
    /**
     * 限定日剂量（DDD）
     */
    private BigDecimal ddd;
    /**
     * 生产厂家
     */
    private String sccj;
    /**
     * 处方药标志
     */
    private String cfybz;
    /**
     * 处方药分类
     */
    private String cfyfl;
    /**
     * 处方药分类描述
     */
    private String cfyflms;
    /**
     * 基本药物标志
     */
    private String jbybz;
    /**
     * 基本药物类型
     */
    private String jbylx;
    /**
     * 基本药物描述
     */
    private String jbywms;
    /**
     * 药品分类编码
     */
    private String ypflbm;
    /**
     * 药品分类名称
     */
    private String ypflmc;
    /**
     * 抗菌类药品分级
     */
    private String kjlypfj;
    /**
     * 抗菌类分级描述
     */
    private String kjfjms;
    /**
     * 是否有毒标识
     */
    private String sfydbz;
    /**
     * 麻醉精神类药品分类代码
     */
    private String mzjslfldm;
    /**
     * 麻醉精神类药品标识描述
     */
    private String mzjslms;
    /**
     * 抗生素标识
     */
    private String kssbz;
    /**
     * 药理分类
     */
    private String ylfl;
    /**
     * 卫生局标准编码
     */
    private String wsjbm;
    /**
     * 医保局标准编码
     */
    private String ybjbm;
    /**
     * 新农合标准编码
     */
    private String xnhbjbm;
    /**
     * 省药品采购标准编码
     */
    private String sypcgbm;
    /**
     * 是否有效
     */
    private String sfyx;
    /**
     * 是否自制药
     */
    private String sfzzy;
    /**
     * 是否中药饮片
     */
    private String sfzyyp;
    /**
     * 是否中成药注射剂
     */
    private String sfzcyzsj;
    /**
     * 是否质子泵抑制剂
     */
    private String sfzzbyzj;
    /**
     * 数据有效标志
     */
    private String validflag;
    /**
     * 数据产生时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appetime;
    /**
     * 最后修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

