package cn.hsa.module.interf.phys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PhysDTO implements Serializable {
    //主键id
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;

    /**
     * 体检中的登记DTO 需要转成outpt_visit就诊dto
     */
    private PhysRegisterDTO physRegisterDTO;

    /**
     * 体检中的SettleDTO 需要转成outpt_cost 费用dto
     */
    private PhysSettleDTO physSettleDTO;

}
