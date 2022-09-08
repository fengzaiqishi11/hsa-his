package cn.hsa.module.center.hospital.dto;

import cn.hsa.module.center.hospital.entity.CenterHospitalDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
/**
 * @Package_name: cn.hsa.module.center.hospital.dto
 * @Class_name: CenterHospitalDTO
 * @Describe: 医院信息DTO
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com.cn
 * @Date: 2020/8/4 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class CenterHospitalDTO extends CenterHospitalDO implements Serializable {
    private List<String> ids;
    private String keyword;
    private String start_time;
    private String end_time ;

    private String dataName ;
    private Long millsOfDays;
    private String workTypeCode;

    private String registNum ;
    private String visitNum;
    private String deptNams;
    // 长期医嘱费用查询专用，直接加迁移标志怕影响其他地方查询，所以再加一个是否未长期费用的标志
    private String isLong;
}
