package cn.hsa.report.business.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName SettleSheetPartTwoLxDTO
 * @Deacription 离休结算单累计信息
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 3.0
 */
@Data
public class SettleSheetPartTwoLxDTO extends SettleSheetPartTwoDTO {

    @ApiModelProperty(name = "totalCount", value = "住院总次数")
    private Integer totalCount;

    @Override
    public void setScale() {
        super.setScale();
        this.totalCount = this.totalCount == null ? 0 : this.totalCount;
    }

}
