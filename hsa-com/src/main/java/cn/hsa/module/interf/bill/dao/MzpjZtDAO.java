package cn.hsa.module.interf.bill.dao;

import cn.hsa.module.interf.bill.dto.MzpjZtDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**门诊票据主体信息查询
 *@description
 *
 *@author pengbo
 *@date 2020年12月24日
 */
public interface MzpjZtDAO {
	
	/**
	 * 	批量新增门诊主体信息
	 *@description
	 *
	 *@param mzpjZtDTOs
	 *@return
	 *
	 *@author pengbo
	 *@date 2020年12月24日
	 */
	Integer insertMzpjZtDTOBatch(@Param("list")List<MzpjZtDTO> mzpjZtDTOs);
	
	/**
	 * 	批量更新门诊主体信息的上传id
	 *@description
	 *
	 *@param map
	 *@return
	 *
	 *@author pengbo
	 *@date 2020年12月24日
	 */
	Integer updateMzpjZtDTOBatch(Map<String,Object> map);
	
	/**
	 * 获得票据主体表当前年度所有的票据校验码
	 *@description
	 *
	 *@param map
	 *@return
	 *
	 *@author pengbo
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
	 *@author pengbo
	 *@date 2021年1月15日
	 */
	Integer deleteMzpjZtDTO(Map<String,Object> map);
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
	List<MzpjZtDTO> listMzpjZtInTable();
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
	List<MzpjZtDTO> listFailMzpjZtInTable(Map<String,Object> map);
}

