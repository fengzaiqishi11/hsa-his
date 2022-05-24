package cn.hsa.module.interf.healthInfo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 4.4.1.1 双向转诊表（TB_SXZZ）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbSxzzDO implements Serializable {

    private String yljgdm;
    private String zzdh;
    private String yljgmc;
    private String ksbm;
    private String ksmc;
    private String sqysxm;
    private String xm;
    private String xbdm;
    private String sfzhm;
    private String lxdh;
    private String zdbm;
    private String zdmc;
    private String zzlx;
    private String sqzzsj;
    private String zzyysm;
    private String zzzt;
    private String jsjgbm;
    private String jsjgmc;
    private String jsksbm;
    private String jsksmc;
    private String jsysxm;
    private String jssj;
    private String sfyx ;
}
