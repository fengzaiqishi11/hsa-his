package cn.hsa.module.center.tablecontrast.dto;

import cn.hsa.module.center.tablecontrast.entity.TableContrastDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.hospital.dto
 * @Class_name: TableContrastDTO
 * @Describe: TableContrast信息DTO
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com.cn
 * @Date: 2020/8/4 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class TableContrastDTO extends TableContrastDO implements Serializable {
    String keyWord;

    String ids;

    List<String> listId;
}
