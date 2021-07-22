package cn.hsa.module.insure.module.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* @Package_name: 
* @Class_name: DO
* @Describe: 表含义： insure：医保 dict：字典  表说明： 用
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureDictDO extends PageDO implements Serializable {
        //序列号
        private static final long serialVersionUID = -8059311669143338381L;
        //主键
        private String id;
        //医院编码
        private String hospCode;
        //医保注册编码
        private String insureRegCode;
        //类型编码
        private String code;
        //字典名
        private String name;
        //字典值
        private String value;
        //备注
        private String remark;
        //创建人ID
        private String crteId;
        //创建人姓名
        private String crteName;
        //创建时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date crteTime;
        //扩展字段1
        private String ext01;
        //扩展字段2
        private String ext02;
        //扩展字段3
        private String ext03;
        private String label;
        private String strCodes;
        private List<String> codeList;
}