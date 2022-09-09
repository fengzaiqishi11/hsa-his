package cn.hsa.module.phar.pharoutdistributedrug.dto;

import cn.hsa.module.phar.pharoutdistributedrug.entity.PharOutReceiveDO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* @Package_name: cn.hsa.module.phar.pharoutdistributedrug.entity
* @class_name: PharOutReceiveDO
* @Description: 门诊领药申请表DTO
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/9/2 15:58
* @Company: 创智和宇
**/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PharOutReceiveDTO extends PharOutReceiveDO implements Serializable {

    private static final long serialVersionUID = 402755114841777914L;

    // 用于搜索身份证，姓名，手机号的关键字
    private String keyword;
    // 姓名
    private String name;
    // 性别代码
    private String genderCode;
    // 年龄
    private String age;
    // 年龄单位
    private String ageUnitCode;
    // 身法证号
    private String certNo;
    //日期
    private String queryDate;
    //状态 2:已配未发,3:已发
    private String status;
    //诊断
    private String diseaseName;
    //发药窗口
    private String windowName;
    //结算单号
    private String settleNo;
    //创建时间
    private String createTime;
    //患者信息
    private String patientInfo;
    //就诊号
    private String visitNo;
    //配药时间
    private String assignTimeStr;
    //发药时间
    private String distTimeStr;

    //病人类型
    private String patientCode;
    //参保号
    private String insureNo;
    //门诊档案号
    private String outProfile;
    //电话号码
    private String phone;
    //就诊医生
    private String doctorName;
    //就诊科室名称
    private String deptName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;        //开始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;         //结束日期
    // 返回数据
    List<StroStockDetailDTO> resultList;
    // 返回标识：警告0,或者错误1
    String flag;
}
