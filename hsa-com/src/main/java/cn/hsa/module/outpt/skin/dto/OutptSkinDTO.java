package cn.hsa.module.outpt.skin.dto;

import cn.hsa.module.outpt.skin.entity.OutptSkinDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.outpt.skin.dto
 * @Class_name:: OutptSkinDTO
 * @Description: 皮试处理数据DTO传输对象
 * @Author: zhangxuan
 * @Date: 2020-08-14 11:26
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptSkinDTO extends OutptSkinDO implements Serializable {
    private List<String> ids;
    /*
    * 姓名或者就诊号
    * */
    private String keyword;
    /*
    * 皮试标志
    * */
    private String skinSign;
    /*
    * 开始时间
    * */
    private String startTime;
    /*
     * 结束时间
     * */
    private String endTime;
    /*
     * 姓名
     * */
    private String name;
    /*
     * 开方医生姓名
     * */
    private String doctorName;
    /*
     * 开方科室名称
     * */
    private String deptName;
    /*
     * 年龄
     * */
    private String age;
    /*
     * 性别代码
     * */
    private String genderCode;
    /*
     * 年龄单位代码
     * */
    private String ageUnitCode;
    //是否皮试
    private String isSkin;
    //是否结算
    private String isSettle;
    //处方id
    private String opId;
    //证件号
    private String certNo;
    //就诊号
    private String visitNo;
    //药品名称
    private String itemName;
    //药品ID
    private String itemId;
    //登记时间
    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date crteTime;
    //执行科室名称
    private String execDeptName;

}
