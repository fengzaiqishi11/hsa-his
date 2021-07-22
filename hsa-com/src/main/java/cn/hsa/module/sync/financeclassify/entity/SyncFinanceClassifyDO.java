package cn.hsa.module.sync.financeclassify.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_ame: cn.hsa.module.base.bfc.entity
 * @Class_name: BaseFinanceClassifyDO
 * @Description: 财务分类数据库映射对象
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 21:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SyncFinanceClassifyDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 768046296707845560L;
    private String id;

    private String code;

    private String name;

    private String inCode;
    
    private String outCode;
    
    private String upCode;
    
    private String isEnd;
    
    private String pym;
    
    private String wbm;
    
    private String isValid;
    
    private String crteId;
    
    private String crteName;

    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
}