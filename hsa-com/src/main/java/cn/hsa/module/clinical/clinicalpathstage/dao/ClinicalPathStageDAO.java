package cn.hsa.module.clinical.clinicalpathstage.dao;


import cn.hsa.module.clinical.clinicalpathstage.dto.ClinicalPathStageDTO;

import java.util.List;

/**
 * 表名含义：临床路径阶段描述(ClinicalPathStage)表数据库访问层
 *
 * @author makejava
 * @since 2021-09-10 16:35:00
 */
public interface ClinicalPathStageDAO {

    /**
     * @Meth:queryClinicalPathStage
     * @Description: 根据条件查询 临床路径阶段
     * @Param: [clinicalPathStageDTO]
     * @return: List<ClinicalPathStageDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/10
     */
    List<ClinicalPathStageDTO> queryClinicalPathStage(ClinicalPathStageDTO clinicalPathStageDTO);

    /**
     * @Meth: insert
     * @Description: 添加操作
     * @Param: [clinicalPathStageDTO]
     * @return: int
     * @Author: zhangguorui
     * @Date: 2021/9/11
     */
    int insert(ClinicalPathStageDTO clinicalPathStageDTO);
    /**
     * @Meth:updateById
     * @Description: 通过Id编辑
     * @Param: [clinicalPathStageDTO]
     * @return: int
     * @Author: zhangguorui
     * @Date: 2021/9/11
     */
    int updateById(ClinicalPathStageDTO clinicalPathStageDTO);
    /**
     * @Meth: deleteClinicalPathStage
     * @Description: 通过id 目录id删除
     * @Param: [clinicalPathStageDTO]
     * @return: java.lang.Boolean
     * @Author: zhangguorui
     * @Date: 2021/9/11
     */
    int deleteClinicalPathStage(ClinicalPathStageDTO clinicalPathStageDTO);
    /**
     * @Meth: getMaxSortNo
     * @Description: 获得最大的序列号
     * @Param: [clinicalPathStageDTO]
     * @return: cn.hsa.module.clinical.clinicalpathstage.dto.ClinicalPathStageDTO
     * @Author: zhangguorui
     * @Date: 2021/9/13
     */
    ClinicalPathStageDTO getMaxSortNo(ClinicalPathStageDTO clinicalPathStageDTO);
    /**
     * @Meth: queryClinicalPathStageById
     * @Description: 根据Id查询
     * @Param: [clinicalPathStageDTO]
     * @return: cn.hsa.module.clinical.clinicalpathstage.dto.ClinicalPathStageDTO
     * @Author: queryClinicalPathStageById
     * @Date: 2021/9/13
     */
    ClinicalPathStageDTO queryClinicalPathStageById(ClinicalPathStageDTO clinicalPathStageDTO);

    /**
    * @Menthod getNextClinicalPathStage
    * @Desrciption 查询下一阶段阶段
    *
    * @Param
    * [clinicalPathStageDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/10/8 16:52
    * @Return cn.hsa.module.clinical.clinicalpathstage.dto.ClinicalPathStageDTO
    **/
    ClinicalPathStageDTO getNextClinicalPathStage(ClinicalPathStageDTO clinicalPathStageDTO);
}

