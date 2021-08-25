package cn.hsa.module.inpt.criticalvalues.dto;

import cn.hsa.base.PageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CriticalValueItemDTO extends PageDO implements  Serializable {
    private String id;
    private String visitId;
    private String hospCode ;
    private String doctorName;
    private String checkItem;
    private String baseAdviceId;
    private String itemId;
}
