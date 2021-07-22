package cn.hsa.module.emr.emrpatientrecord.dto;

import cn.hsa.module.emr.emrpatientrecord.entity.EmrPatientRecordDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * TODO
 *
 * @author guanhongqiang
 * @date 2020/9/21 19:56
 */
@Data
@AllArgsConstructor
@ToString(callSuper = true)
public class EmrPatientRecordDTO extends EmrPatientRecordDO implements Serializable {
	private static final long serialVersionUID = -1991180396605059268L;




}
