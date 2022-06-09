package cn.hsa.module.base.bd.dto;

import cn.hsa.module.base.bd.entity.BaseDiseaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_ame: cn.hsa.module.base.bd.dto
 * @Class_name: BaseDiseaseDTO
 * @Description: 疾病管理数据传输DTO对象
 * @Author: liaojunjie
 * @Date: 2020/7/9 21:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BaseDiseaseDTO extends BaseDiseaseDO implements Serializable {
    //输入框内容
    String keyword;
    // 要删除的id列表
    private List<String> ids;
    // 医保匹配的时候做医保机构编码存储
    private String insureRegCode;
    // 医保匹配的时候做id存储
    private String insureId;
    /**
     * 疾病ids，字符串类型
     */
    private String diseaseIds;

    private Integer limit;

    private List<String> codes;  //编码集
    /**
     * 查询类型
     */
    private String queryType;
    private Boolean isNationCode ;
    //是否需要上报
    private Boolean isNeedReport;
    // 基础信息疾病查询
    private String baseQuery;



}
