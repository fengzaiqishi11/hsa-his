package cn.hsa.module.sync.material.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.base.data.entity
 * @Class_name: BaseMaterialDO
 * @Describe:  材料管理数据库映射对象
 * @Author: xingyu.xie
 * @Email: xingyu.xie@powersi.com
 * @Date: 2020/7/7 15:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SyncMaterialDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 950447210438467941L;

    private String id;

    private String typeCode;
    
    private String bfcCode;
    
    private String code;
    
    private String name;
    
    private String spec;
    
    private BigDecimal price;
    
    private String unitCode;
    
    private BigDecimal splitRatio;
    
    private String splitUnitCode;
    
    private BigDecimal splitPrice;
    
    private String isSuppCurtain;
    
    private String useCode;
    
    private String remark;
    
    private String prodCode;
    
    private String regCertNo;
    
    private String pym;
    
    private String wbm;
    
    private String isValid;
    
    private String crteId;
    
    private String crteName;

    private Date crteTime;

}