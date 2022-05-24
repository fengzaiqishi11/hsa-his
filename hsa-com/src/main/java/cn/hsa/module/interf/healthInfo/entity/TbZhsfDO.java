package cn.hsa.module.interf.healthInfo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 4.5.1.1 诊后随访表（TB_ZHSF）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbZhsfDO implements Serializable {
    private String yljgdm;
    private String yljgmc;
    private String ssdh;
    private String sflx;
    private String sffs;
    private String jzksbm;
    private String jzksmc;
    private String jzsj;
    private String jzjllsh;
    private String xm;
    private String sfzhm;
    private String xbdm;
    private String nl;
    private String zdbm;
    private String zdmc;
    private String lxdh;
    private String sfsj;
    private String sfysbm;
    private String sfysxm;
    private String sfjg;
}
