package cn.hsa.module.interf.bill.dao;

import cn.hsa.module.interf.bill.dto.MzpjFyDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**门诊票据费用DAO
 *@description
 *
 *@author liuqi1
 *@date 2020年12月23日
 */
public interface MzpjFyDAO {
	
	/**
	 * 	查询门诊票据费用
	 *@description
	 *
	 *@param mzpjFyDTO
	 *@return
	 *
	 *@author liuqi1
	 *@date 2020年12月23日
	 */
	 List<MzpjFyDTO> queryMzpjFyDTO(@Param("pjhmList")List<String> pjhmList);
	
	/**
	 * 	批量新增门诊票据费用
	 *@description
	 *
	 *@param mzpjFyDTOs
	 *@return
	 *
	 *@author liuqi1
	 *@date 2020年12月23日
	 */
	 Integer insertMzpjFyDTOBatch(@Param("list")List<MzpjFyDTO> mzpjFyDTOs);
	
	 /**
	  *	批量更新门诊费用信息的上传id
	  *@description
	  *
	  *@param map
	  *@return
	  *
	  *@author liuqi1
	  *@date 2020年12月24日
	  */
	 Integer updateMzpjFyDTOBatch(Map<String,Object> map);
	 
	 /**
	 * 	根据业务流水号删除费用信息
	 *@description
	 *
	 *@param map
	 *@return
	 *
	 *@author liuqi1
	 *@date 2021年1月15日
	 */
	Integer deleteMzpjFyDTO(Map<String,Object> map);
	 
}

