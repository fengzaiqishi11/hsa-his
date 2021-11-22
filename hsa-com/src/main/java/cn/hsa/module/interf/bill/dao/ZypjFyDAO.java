package cn.hsa.module.interf.bill.dao;

import cn.hsa.module.interf.bill.dto.ZypjFyDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**住院票据费用DAO
 *@description
 *
 *@author liuqi1
 *@date 2020年12月23日
 */
public interface ZypjFyDAO {
	
	/**
	 * 	查询住院票据费用
	 *@description
	 *
	 *@param zypjFyDTO
	 *@return
	 *
	 *@author liuqi1
	 *@date 2020年12月23日
	 */
	public List<ZypjFyDTO> queryZypjFyDTO(@Param("pjhmList")List<String> pjhmList);
	
	/**
	 * 	批量新增住院票据费用
	 *@description
	 *
	 *@param zypjFyDTOs
	 *@return
	 *
	 *@author liuqi1
	 *@date 2020年12月23日
	 */
	public Integer insertZypjFyDTOBatch(@Param("list")List<ZypjFyDTO> zypjFyDTOs);
	
	 /**
	  *	批量更新住院费用信息的上传id
	  *@description
	  *
	  *@param map
	  *@return
	  *
	  *@author liuqi1
	  *@date 2020年12月24日
	  */
	 Integer updateZypjFyDTOBatch(Map<String,Object> map);
	
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
	Integer deleteZypjFyDTO(Map<String,Object> map);
	 
}

