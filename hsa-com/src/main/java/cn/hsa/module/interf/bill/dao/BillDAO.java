package cn.hsa.module.interf.bill.dao;

import cn.hsa.module.interf.bill.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/** 票据DAO
 *@description
 *
 *@author liuqi1
 *@date 2020年12月23日
 */
public interface BillDAO {
	/**
	 * 从视图里面查询门诊主体数据信息,过滤时间段,已抽取的主体数据
	 * 
	 * @param map
	 * @return
	 */
	List<MzpjZtDTO> listMzpjZtDTOsInView(Map <String,Object> map);

	/**
	 * 从视图里面查询门诊费用数据,根据PJHM
	 * 
	 * @param pjhmList
	 * @return
	 */
	List<MzpjFyDTO> listMzpjFyByPjhm(@Param("pjhmList")List<String> pjhmList);

	/**
	 * 从视图里面查询门诊明细数据,根据PJHM
	 * 
	 * @param pjhmList
	 * @return
	 */
	List<MzylMxDTO> listMzpjMxByPjhm(@Param("pjhmList")List<String> pjhmList);
	
	/**
	 * 从视图里面查询住院主体数据信息,过滤时间段,已抽取的主体数据
	 * 
	 * @param map
	 * @return
	 */
	List<ZypjZtDTO> listZypjZtDTOsInView(Map <String,Object> map);
	/**
	 * 从视图里面查询住院费用数据,根据PJHM
	 * 
	 * @param pjhmList
	 * @return
	 */
	List<ZypjFyDTO> listZypjFyByPjhm(@Param("pjhmList")List<String> pjhmList);
	/**
	 * 从视图里面查询住院明细数据,根据PJHM
	 * 
	 * @param pjhmList
	 * @return
	 */
	List<ZyylMxDTO> listZypjMxByPjhm(@Param("pjhmList")List<String> pjhmList);

}
