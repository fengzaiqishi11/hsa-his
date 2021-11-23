package cn.hsa.module.interf.bill.dao;

import cn.hsa.module.interf.bill.dto.ZypjZtDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ZypjZtDAO {

	/**
	 *	查询住院票据主体信息
	 *@description
	 *
	 *@param zypjZtDTO
	 *@return
	 *
	 *@author liuqi1
	 *@date 2020年12月24日
	 */
	List<ZypjZtDTO> queryZypjZtDTO(ZypjZtDTO zypjZtDTO);
	
	/**
	 * 	查询住院票据主体信息(手工上传查询)
	 *@description
	 *
	 *@param mzpjZtDTO
	 *@return
	 *
	 *@author liuqi1
	 *@date 2020年12月24日
	 */
	List<ZypjZtDTO> queryZypjZtDTOWithHand(ZypjZtDTO zypjZtDTO);
	
	/**
	 * 	批量新增住院主体信息
	 *@description
	 *
	 *@param zypjZtDTOs
	 *@return
	 *
	 *@author liuqi1
	 *@date 2020年12月24日
	 */
	Integer insertZypjZtDTOBatch(@Param("list")List<ZypjZtDTO> zypjZtDTOs);
	
	/**
	 * 	批量更新住院主体信息的上传id
	 *@description
	 *
	 *@param map
	 *@return
	 *
	 *@author liuqi1
	 *@date 2020年12月24日
	 */
	Integer updateZypjZtDTOBatch(Map<String,Object> map);
	
	/**
	 * 获得票据主体表当前年度所有的票据校验码
	 *@description
	 *
	 *@param map
	 *@return
	 *
	 *@author liuqi1
	 *@date 2020年12月29日
	 */
	List<String> queryPjjym(Map<String,Object> map);
	
	/**
	 * 	根据业务流水号删除主体信息
	 *@description
	 *
	 *@param map
	 *@return
	 *
	 *@author liuqi1
	 *@date 2021年1月15日
	 */
	Integer deleteZypjZtDTO(Map<String,Object> map);
	/**
	 * 	查询主体表需要上传的数据
	 *@description
	 *
	 *@param map
	 *@return
	 *
	 *@author pengbo
	 *@date 2021年1月15日
	 */
	List<ZypjZtDTO> listZypjZtInTable();
	/**
	 * 	查询失败主体表需要上传的数据
	 *@description
	 *
	 *@param map
	 *@return
	 *
	 *@author pengbo
	 *@date 2021年1月15日
	 */
	List<ZypjZtDTO> listFailZypjZtInTable(Map<String,Object> map);
	
}

