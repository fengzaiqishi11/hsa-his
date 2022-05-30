package cn.hsa.module.inpt.medicaltechnology.dao;

import cn.hsa.base.PageDO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.medicaltechnology.dto.MedicalTechnologyDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.medicaltechnology.dao
 * @Class_name: MedicalTechnologyDAO
 * @Describe: 住院医技确费
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/3/12 11:41
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface MedicalTechnologyDAO {

	List<MedicalTechnologyDTO> getLISorPASSNeedConfirmCost(MedicalTechnologyDTO medicalTechnologyDTO);

	List<MedicalTechnologyDTO> getOutPutLISorPASSNeedConfirmCost(MedicalTechnologyDTO medicalTechnologyDTO);

	List<MedicalTechnologyDTO> getRecoveryConfirmCost(MedicalTechnologyDTO medicalTechnologyDTO);

	int saveMwdicalTechnologyConfirmCost(Map<String, Object> map);
	int saveOutPtMwdicalTechnologyConfirmCost(Map<String, Object> map);
	/**
	 * @Description: 取消医技确费
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/15 10:00
	 * @Return
	 */
	int updateMedicalTechnologyConfirmCost(Map<String, Object> map);
	int updateOutPtMedicalTechnologyConfirmCost(Map<String, Object> map);
	/**
	 * @Description: 查询当前患者就诊id未确费的费用数量
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/15 15:47
	 * @Return
	 */
	int getConfirmCost(InptVisitDTO inptVisitDTO);

    List<SysParameterDTO> getParameterValue(@Param("hospCode") String hospCode, @Param("code") String[] code);

    InptVisitDTO getVisitDTOById(InptVisitDTO inptVisitDTO);
	/**
	 * @Author gory
	 * @Description 获取不是未确费的费用
	 * @Date 2022/5/25 15:19
	 * @Param [list]
	 **/
	int getAlreadyCostByCostIds(@Param("hospCode")String hospCode,@Param("list") List<String> list);
}
