package cn.hsa.module.center.profilefile.dto;

import cn.hsa.module.center.profilefile.entity.CenterProfileFileDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.profilefile.dto
 * @Class_name: CenterProfileFileDTO
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/8/10 16:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class CenterProfileFileDTO extends CenterProfileFileDO implements Serializable {
    private String keyword;                   //搜索关键字
    private List<String> ids;                 //多个档案id存储集合
    private String sex;
}
