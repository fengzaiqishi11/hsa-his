package cn.hsa.module.interf.bill.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.interf.bill.dto.*;

import java.util.List;
import java.util.Map;

/**
 * 电子票据BO
 * @author liudawen
 * @date 2021/11/9
 */
public interface BillBO {

    /**
     * 按条件分页查询门诊票据主体视图
     * @param map
     * @return
     */
    List<MzpjZtDTO> queryMzZtView(Map<String,Object> map);

    /**
     * 按条件分页查询门诊票据费用明细视图
     * @param map
     * @return
     */
    List<MzpjFyDTO> queryMzFyView(Map <String,Object> map);

    /**
     * 按条件分页查询门诊票据主体视图
     * @param map
     * @return
     */
    List<MzylMxDTO> queryMzYlView(Map <String,Object> map);

    /**
     * 按条件分页查询住院票据主体视图
     * @param map
     * @return
     */
    List<ZypjZtDTO> queryZyZtView(Map <String,Object> map);

    /**
     * 按条件分页查询住院票据费用明细视图
     * @param map
     * @return
     */
    List<ZypjFyDTO> queryZyFyView(Map <String,Object> map);

    /**
     * 按条件分页查询住院票据医疗明细视图
     * @param map
     * @return
     */
    List<ZyylMxDTO> queryZyYlView(Map <String,Object> map);

}
