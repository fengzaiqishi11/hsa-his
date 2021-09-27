package cn.hsa.module.clinical.clinicalpathitem.dto;

import cn.hsa.module.clinical.clinicalpathitem.entity.ClinicalPathItemDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class ClinicalPathItemDTO extends ClinicalPathItemDO implements Serializable {
    /**
     * 关键字 ：项目名/拼音码/五笔码
     *
     */
    private String keyword;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 项目ids 用于批量删除
     */
    private List<String> ids;

    /**
     * 导入的文件
     * */
    private Map<String, Object> resultMap;

}
