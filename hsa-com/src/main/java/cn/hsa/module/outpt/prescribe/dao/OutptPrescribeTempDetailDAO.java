package cn.hsa.module.outpt.prescribe.dao;

import cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDetailDTO;

import java.util.List;

/**
 * 处方模板明细表(lc_mb02)(OutptPrescribeTempDetail)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-19 15:52:18
 */
public interface OutptPrescribeTempDetailDAO {

    /**
    * @Method queryOutptPrescribeTempDetailById
    * @Desrciption 单个查询
    * @param outptPrescribeTempDetailDTO
    * @Author liuqi1
    * @Date   2020/8/19 16:40
    * @Return cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDetailDTO
    **/
    OutptPrescribeTempDetailDTO getOutptPrescribeTempDetailById(OutptPrescribeTempDetailDTO outptPrescribeTempDetailDTO);

    /**
    * @Method queryOutptPrescribeTempDetails
    * @Desrciption 根据处方模板id查询出处方模板明细
    * @param outptPrescribeTempDTO
    * @Author liuqi1
    * @Date   2020/8/19 16:41
    * @Return java.util.List<cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDetailDTO>
    **/
    List<OutptPrescribeTempDetailDTO> queryOutptPrescribeTempDetails(OutptPrescribeTempDTO outptPrescribeTempDTO);

    /**
    * @Method queryOutptPrescribeTempDetailPage
    * @Desrciption 分页查询
    * @param outptPrescribeTempDetailDTO
    * @Author liuqi1
    * @Date   2020/8/19 16:42
    * @Return java.util.List<cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDetailDTO>
    **/
    List<OutptPrescribeTempDetailDTO> queryOutptPrescribeTempDetailPage(OutptPrescribeTempDetailDTO outptPrescribeTempDetailDTO);

    /**
    * @Method insertOutptPrescribeTempDetail
    * @Desrciption 单个新增
    * @param outptPrescribeTempDetailDTO
    * @Author liuqi1
    * @Date   2020/8/19 16:42
    * @Return int
    **/
    int insertOutptPrescribeTempDetail(OutptPrescribeTempDetailDTO outptPrescribeTempDetailDTO);

    /**
    * @Method updateOutptPrescribeTempDetailDTO
    * @Desrciption 单个修改
    * @param outptPrescribeTempDetailDTO
    * @Author liuqi1
    * @Date   2020/8/19 16:42
    * @Return int
    **/
    int updateOutptPrescribeTempDetail(OutptPrescribeTempDetailDTO outptPrescribeTempDetailDTO);

    /**
    * @Method deleteOutptPrescribeTempDetailDTO
    * @Desrciption 根据处方模板id删除处方模板明细
    * @param outptPrescribeTempDTO
    * @Author liuqi1
    * @Date   2020/8/19 16:43
    * @Return int
    **/
    int deleteOutptPrescribeTempDetail(OutptPrescribeTempDTO outptPrescribeTempDTO);

}