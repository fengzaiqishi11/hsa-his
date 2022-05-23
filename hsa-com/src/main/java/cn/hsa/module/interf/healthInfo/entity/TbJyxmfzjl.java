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
 * 实验室检验项目分组记录(TbJyxmfzjl)实体类
 *
 * @author liudawen
 * @date 2022-05-12 14:18:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbJyxmfzjl implements Serializable {
    private static final long serialVersionUID = -61135323273072841L;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 检验报告记录流水号
     */
    private String jybgjllsh;
    /**
     * 分组项目代码
     */
    private String fzxmdm;
    /**
     * 患者机构内唯一id
     */
    private String hzjgnwyid;
    /**
     * 分组项目名称
     */
    private String fzxmmc;
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

