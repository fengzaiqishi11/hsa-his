package cn.hsa.module.insure.module.dto;

import cn.hsa.module.insure.module.entity.InsureItemMatchDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
* @Package_name: 
* @Class_name: DTO
* @Describe: 表含义： insure：医保 item：项目 matching：匹配  
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureItemMatchDTO extends InsureItemMatchDO implements Serializable {
        private static final long serialVersionUID = -4796957501740003787L;
        // 医保机构编码
        private String insureRegCode;
        // 输入框内容
        private String keyword;
        // 医保区域
        private String area;
        // 下载类型
        private String downloadType;
        // 操作员编码
        private String code ;
        // 医疗机构编码
        private String regCode ;

        private String firstrow;
        private Boolean isItemCancel ; // 项目对照撤销
        private String lastrow;
        private Boolean isDiseaseHandler; // 是否疾病手动匹配
        private String matchCode; // 匹配类型
        private String matchType ;  // 匹配方式
        private String typeCode ; //项目类别
        private String lmtUserFlag ; //限制使用标志
        private String limUserExplain; // 限制使用说明
        private String visitId; //就诊id
        /**
         * 是否报销
         */
        private String isReimburse;
        private String isUpay; // 是否为新医保
        private String hospId; // HIS端ID
        private String insureId; // 医保端ID
        private List<String> itemIdCollectList;
        private String remark;
        private String hilistLv; // 医保目录等级
        private String chrgType; // 收费类别
        private BigDecimal hilistPrice; // 医保价格
}