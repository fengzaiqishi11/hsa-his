package cn.hsa.module.base.bac.dto;

import cn.hsa.module.base.bac.entity.BaseAssistCalcDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
/**
 * @author ljh
 * @date 2020/07/08.
 */
@Data
@AllArgsConstructor
@ToString(callSuper = true)
public class BaseAssistCalcDTO extends BaseAssistCalcDO implements Serializable {
    private static final long serialVersionUID = 500823660970529987L;

    private String deptCodeName;
    private String deptId;
    List<BaseAssistCalcDetailDTO> baseAssistCalcDetailDO;
    List<String> ids;
    public BaseAssistCalcDTO() {

    }
    private String zdksbz;
    private String zdxmbz;
    private String zdxmid;
    private String ksid;
    /***
     * 1:指定科室、指定项目
     * 2:不指定科室、指定项目
     * 3:指定科室、不指定项目
     * 4:不指定科室、不指定项目
     */
    private String type;
}
