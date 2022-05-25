package cn.hsa.module.interf.healthInfo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 4.2.1.3 药品销售信息（TB_YPXSXX）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbYpxxssDO implements Serializable {

    private String yljgdm;
    private String ypdm;
    private String jzh;
    private String ywlx;
    private String yfdm;
    private String szbwlsh;
    private String ypmc;
    private String spm;
    private String scqy;
    private String ypjxms;
    private String ypgg;
    private String bzl;
    private String dw;
    private String cgj;
    private String xsjg;
    private String xssl;
    private String xsje;
    private String ypsx;
    private String xplx;
    private String kjybz;
    private String kjyjb;
    private String xssj;
    private String xsksdm;
    private String xsksmc;
    private String xsysdm;
    private String xsysmc;
    private String yljgmc;
}
