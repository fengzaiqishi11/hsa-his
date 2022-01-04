package cn.hsa.module.msg.dto;

import cn.hsa.module.inpt.doctor.entity.InptCostDO;
import cn.hsa.module.msg.entity.MsgTempRecordDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.msg.entity
 * @class_name: MsgTempRecordDO
 * @Description: 消息模板
 * @Author: youxianlin
 * @Email: 254580179@qq.com
 * @Date: 2021/1/21 10:59
 * @Company: 创智和宇
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class MsgTempRecordDTO extends MsgTempRecordDO implements Serializable {
    private static final long serialVersionUID = -6835255983464404633L;
    private String keyword;
    private String bedName;        //当前床位名称
    private String information;   //患者基本信息
    private String inDeptName;   //入院科室
    private List<String> ids;
    private String content; //医嘱内容
    private String inDeptId; // 科室id

}
