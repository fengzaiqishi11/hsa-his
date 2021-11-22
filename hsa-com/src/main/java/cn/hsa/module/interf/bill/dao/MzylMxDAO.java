package cn.hsa.module.interf.bill.dao;

import cn.hsa.module.interf.bill.dto.MzylMxDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 门诊医疗明细信息
 *
 * @author liuqi1
 * @description
 * @date 2020年12月24日
 */
public interface MzylMxDAO {

    /**
     * 查询门诊医疗明细信息
     *
     * @param mzylMxDTO
     * @return
     * @description
     * @author liuqi1
     * @date 2020年12月24日
     */
    List<MzylMxDTO> queryMzylMxDTO(@Param("pjhmList") List<String> pjhmListt);

    /**
     * 批量新增门诊医疗明细信息
     *
     * @param mzylMxDTOs
     * @return
     * @description
     * @author liuqi1
     * @date 2020年12月24日
     */
    Integer insertMzylMxDTOBatch(@Param("list") List<MzylMxDTO> mzylMxDTOs);

    /**
     * 批量更新门诊医疗明细信息的上传id
     *
     * @param map
     * @return
     * @description
     * @author liuqi1
     * @date 2020年12月24日
     */
    Integer updateMzylMxDTOBatch(Map<String, Object> map);

    /**
     * 根据业务流水号删除医疗明细信息
     *
     * @param map
     * @return
     * @description
     * @author liuqi1
     * @date 2021年1月15日
     */
    Integer deleteMzylMxDTO(Map<String, Object> map);
}

