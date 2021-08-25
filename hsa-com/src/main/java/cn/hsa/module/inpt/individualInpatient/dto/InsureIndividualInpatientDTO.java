package cn.hsa.module.inpt.individualInpatient.dto;

import cn.hsa.module.inpt.individualInpatient.entity.InsureIndividualInpatientDO;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

@Data
@ToString(callSuper = true)
public class InsureIndividualInpatientDTO extends InsureIndividualInpatientDO implements Serializable {

    private static final long serialVersionUID = 740334062988036582L;


    private Map<String,Object> resultMap;

    private String patientType;   //病人类型

    private String regionType;    //异地还是本地

    private String functionId;  //业务id

    private String bka895;   //证件类型

    private String bka896;  //入参值
    private String aaz507;   //密码
    private String aka130;		//医疗业务类型
    private String akb020;		//医院编码
    private String bka891;		//结算标识	0:未结算
    private String bka977;	    //入参通用接口标识 非必填	通用接口必填

    private String regCode; //机构编码

    private String aae011;  //就诊登记工号


}
