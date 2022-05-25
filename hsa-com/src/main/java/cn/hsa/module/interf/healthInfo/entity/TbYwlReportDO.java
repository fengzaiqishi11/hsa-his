package cn.hsa.module.interf.healthInfo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbYwlReportDO implements Serializable {

    private String yljgdm;
    private String ksbm;
    private String ksmc;
    private String ywsj;
    private String mzrc;
    private String jzrc;
    private String ryrc;
    private String cyrc;
    private String zyrs;
    private String sycws;
    private String mjzylfy;
    private String zyylfy;
    private String mjzypfy;
    private String zyypfy;
    private String mjzybylfy;
    private String zyybylfy;
    private String mjzybypfy;
    private String zyybypfy;
    private String zysjfsys;
    private String tjyl1;
    private String tjyl2;
    private String tjyl3;
    private String tjyl4;
    private String tjyl5;
    private String tjyl6;
    private String tjyl7;
    private String tjyl8;
    private String xgbz;
}
