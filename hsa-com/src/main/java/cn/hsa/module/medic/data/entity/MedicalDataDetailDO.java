package cn.hsa.module.medic.data.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
* @Package_name: cn.hsa.module.medic.data.entity
* @class_name: MedicalDataDetailDO
* @Description: lis/pacs配置明细表
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2021/1/28 15:05
* @Company: 创智和宇
**/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MedicalDataDetailDO extends PageDO implements Serializable {

    private static final long serialVersionUID = 217448257180401094L;

    private String id;

    private String hospCode;

    /***
     * 类型
     * 1.lis科室同步,2:lis医生同步,3:lis收费项目同步,4:lis申请单,5:lis主动获取,6:lis推送,7:lis PDF报告查询,8:lis危急值主动获取,9:lis危急值推送,
     * 41.pacs科室同步,42:pacs医生同步,43:pacs收费项目同步,44:pacs申请单,45:pacs
     */
    private String type;

    private String resultEName;

    private String resultCName;

    private String resultType;

    private String resultRemark;

    private String resourceEName;

    private String resourceCName;

    private String resourceType;

    private String resourceRemark;

    private String crteId;

    private String crteName;

    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
}
