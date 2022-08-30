package cn.hsa.module.inpt.medical.dto;

import cn.hsa.module.inpt.doctor.entity.InptAdviceDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Package_name
 * @class_nameMedicalAdviceDTO
 * @Description
 * @Author youxianlin
 * @Email: 254580179@qq.com
 * @Date2020/9/9 10:48
 * @Company 创智和宇
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class MedicalAdviceDTO extends InptAdviceDO implements Serializable {
    private static final long serialVersionUID = 457969190712196833L;

    //患者姓名
    private String visitName;
    //患者年龄
    private String visitAge;
    //患者性别
    private String visitSex;
    //患者证件号码
    private String visitCardNo;
    //频率名称
    private String rateName;
    //医嘱核收记录主键集合
    private List<String> ids;
    //核收时间
    private Date acceptTime;
    //患者信息
    private String visitInfo;
    //执行科室
    private String execDeptName;
    //药品类别
    private String type;
    //床位
    private String bedName;
    //搜索条件
    private String keyword;

    private String sfTqly ;
    /**
     * 国家卫健委编码
     */
    private String nationCode;
    /**
     * 国家卫健委编码名称
     */
    private String nationName;
    /**
     * 提前领药记录主键
     */
    private String advanceId;

    /**
     *核对签名ID
     */
    private String checkedId;
    /**
     * 核对签名名称
     */
    private String checkedName;

    /**
     * 核对签名名称
     */
    private String isMyself;
    /**
     * 停嘱
     */
    private String [] stopIds;
    /**
     * 开嘱
     */
    private String [] kzIds;

}
