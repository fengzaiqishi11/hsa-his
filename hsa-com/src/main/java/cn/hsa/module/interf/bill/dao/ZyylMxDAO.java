package cn.hsa.module.interf.bill.dao;

import cn.hsa.module.interf.bill.dto.ZyylMxDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**住院医疗明细信息
 *@description
 *
 *@author liuqi1
 *@date 2020年12月24日
 */
public interface ZyylMxDAO {

	/**
	 *	查询住院医疗明细信息
	 *@description
	 *
	 *@param zyylMxDTO
	 *@return
	 *
	 *@author liuqi1
	 *@date 2020年12月24日
	 */
	List<ZyylMxDTO> queryZyylMxDTO(@Param("pjhmList")List<String> pjhmList);
	
	/**
	 * 	批量新增住院医疗明细信息
	 *@description
	 *
	 *@param zyylMxDTOs
	 *@return
	 *
	 *@author liuqi1
	 *@date 2020年12月24日
	 */
	Integer insertZyylMxDTOBatch(@Param("list")List<ZyylMxDTO> zyylMxDTOs);
	
	/**
	 * 	批量更新医住院疗明细信息的上传id
	 *@description
	 *
	 *@param map
	 *@return
	 *
	 *@author liuqi1
	 *@date 2020年12月24日
	 */
	Integer updateZyylMxDTOBatch(Map<String,Object> map);
	
	
	/**
	 * 	根据业务流水号删除医疗明细信息
	 *@description
	 *
	 *@param map
	 *@return
	 *
	 *@author liuqi1
	 *@date 2021年1月15日
	 */
	Integer deleteZyylMxDTO(Map<String,Object> map);
	
}

