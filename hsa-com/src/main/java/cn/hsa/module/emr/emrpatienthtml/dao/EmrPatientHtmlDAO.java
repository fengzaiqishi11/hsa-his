package cn.hsa.module.emr.emrpatienthtml.dao;

import cn.hsa.module.emr.emrpatient.dto.EmrPatientDTO;
import cn.hsa.module.emr.emrpatienthtml.dto.EmrPatientHtmlDTO;

/**
 * @Package_name: cn.hsa.module.emr.emrpatienthtml.dao
 * @Class_name: EmrPatientHtmlDAO
 * @Describe: 电子病历存储病人已经编写好的病历，以html格式存储，此表数据不做删除处理，仅能修改（送审状态），其他值为新增，不能修改；当病历修改后再次送审时新增一条数据
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/9/23 20:18
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface EmrPatientHtmlDAO {

	/**
	 * @Description: 新增病人病历内容（html）
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/23 20:24
	 * @Return
	 */
	int insertEmrPatientHtml(EmrPatientHtmlDTO emrPatientHtmlDTO);

	/**
	 * @Description: 修改病人病历记录（html），
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/23 20:26
	 * @Return
	 */
	int updateEmrPatientHtml(EmrPatientHtmlDTO emrPatientHtmlDTO);

	/**
	 * @Description: 查询病人单个电子病历内容
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/23 20:33
	 * @Return
	 */
	EmrPatientHtmlDTO getEmrPatientHtmlDO(EmrPatientDTO emrPatientDTO);

}
