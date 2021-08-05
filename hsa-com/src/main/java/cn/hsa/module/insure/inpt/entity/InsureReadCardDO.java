package cn.hsa.module.insure.inpt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.insure.inpt.entity
 * @class_name: InsureReadCardlDO
 * @Description: 读卡实体类
 * @Author: liaojiguang
 * @Email: jiguang.liao@powersi.com
 * @Date: 2021/8/2 10:07
 * @Company: 创智和宇
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InsureReadCardDO implements Serializable {
    // 医院编码
    private String hospCode;

    // 医疗机构编码
    private String fixmedinsCode;

    // 身份证号码
    private String idcard;

    // 行政区划
    private String insuAdmdvs;

    // 密码
    private String inputPassword;

    // 人员姓名
    private String psnName;

    // 新密码
    private String password;

    // 旧密码
    private String oldPassword;

    // 医保注册编码
    private String insureRegCode;

}
