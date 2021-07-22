package cn.hsa.module.emr.emrpatientrecord.dao;

import cn.hsa.module.emr.emrpatient.dto.EmrPatientDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrpatientrecord.dao
 * @Class_name: EmrPatientRecoderDAO
 * @Describe: 病人病历数据结构化存储，此记录不删除，不修改，修改时新增一条数据
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/9/22 13:58
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface EmrPatientRecordDAO {

	/**
	 * @Description: 当病人病历记录保存时，需要新增一条结构化数据
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/23 15:00
	 * @Return
	 */
	int insertEmrPatientRecord(@Param("nrMap") Map<String, Object> nrMap);

	/**
	 * @Description: 病历结构化存储垂直拆表后存储01表
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/11/7 12:18
	 * @Return
	 */
	int insertEmrPatientRecord1(@Param("nrMap") Map<String, Object> nrMap);

	/**
	 * @Description: 病历结构化存储垂直拆表后存储02表
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/11/7 12:19
	 * @Return
	 */
	int insertEmrPatientRecord2(@Param("nrMap") Map<String, Object> nrMap);

	/**
	 * @Description: 病历结构化存储垂直拆表后存储03表
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/11/7 12:19
	 * @Return
	 */
	int insertEmrPatientRecord3(@Param("nrMap") Map<String, Object> nrMap);

	/**
	 * @Description: 查询当前病人同一份病历，同一创建次数的最新两条记录（有修改才有两条以上的记录）
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/23 15:11
	 * @Return
	 */
	List<Map<String, Object>> getEmrPatientRecords(EmrPatientDTO emrPatientDTO);

}
