package cn.hsa.module.insure.module.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.insure.module.entity
 * @class_name: InsureNoStructReport
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/9/4 16:31
 * @Company: 创智和宇
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureNoStructReportDO extends PageDO implements Serializable {
    private String id; //主键id
    private String hospCode; // 医院编码
    private String visitId; // 就诊id
    private String mdtrtId; // 就医登记号
    private String psnNo; // 人员编号
    private String otpIptFlag; // 门诊/住院标志
    private String examTestFlag; // 检查/检验标志
    private String appyNo; // 申请号
    private String fileName; // 文件名
    private String fileFormate; // 文件格式类型
    private String examTestRpot; // 检查/检验报告(base64编码格式)
    private String valiFlag; // 是否上传
    private String isTrans; // 是否上传
}
