package cn.hsa.clinical.clinicalpathstage.bo.impl;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.clinical.clinicalpathstage.dao.ClinicalPathStageDAO;
import cn.hsa.module.clinical.clinicalpathstage.dto.ClinicalPathStageDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import cn.hsa.module.clinical.clinicalpathstage.bo.ClinicalPathStageBO;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class ClinicalPathStageBOImpl implements ClinicalPathStageBO {

    @Resource
    private ClinicalPathStageDAO clinicalPathStageDAO;
    @Resource
    private BaseOrderRuleService baseOrderRuleService;
    /**
     * @Meth:queryClinicalPathStage
     * @Description: 根据条件查询临床路径阶段
     * @Param: [clinicalPathStageDTO]
     * @return: cn.hsa.base.PageDTO
     * @Author: zhangguorui
     * @Date: 2021/9/10
     */
    @Override
    public PageDTO queryClinicalPathStage(ClinicalPathStageDTO clinicalPathStageDTO) {
        Optional.ofNullable(clinicalPathStageDTO.getHospCode()).orElseThrow(() ->new AppException("医院编码不能为空"));
        PageHelper.startPage(clinicalPathStageDTO.getPageNo(),clinicalPathStageDTO.getPageSize());
        List<ClinicalPathStageDTO> clinicalPathStageDTOS = clinicalPathStageDAO.queryClinicalPathStage(clinicalPathStageDTO);
        return PageDTO.of(clinicalPathStageDTOS);
    }
    /**
     * @Meth: addOrupdateClinicalPathStage
     * @Description: 根据目录id 添加 路径阶段
     * @Param: [clinicalPathStageDTO]
     * @return: java.lang.Boolean
     * @Author: zhangguorui
     * @Date: 2021/9/11
     */
    @Override
    public Boolean addOrUpdateClinicalPathStage(ClinicalPathStageDTO clinicalPathStageDTO) {
        Optional.ofNullable(clinicalPathStageDTO.getHospCode()).orElseThrow(() -> new AppException("医院编码不能为空"));
        Optional.ofNullable(clinicalPathStageDTO.getListId()).orElseThrow(() -> new AppException("目录id不能为空"));
        if (StringUtils.isEmpty(clinicalPathStageDTO.getId())){ // 添加
            // 生成主键
            String id = SnowflakeUtils.getId();
            // 创建时间
            Date crteTime = DateUtils.getNow();
            // 生成阶段编号
            HashMap map = new HashMap();
            map.put("hospCode",clinicalPathStageDTO.getHospCode());
            map.put("typeCode", Constants.ORDERRULE.LCLJJDMS);
            WrapperResponse<String> orderNo = baseOrderRuleService.getOrderNo(map);
            if(StringUtils.isEmpty(orderNo.getData())) {
                throw new AppException("系统没有临床路径阶段描述单号规则");
            }
            // 新增赋值编码
            clinicalPathStageDTO.setCode(orderNo.getData());
            // 封装参数
            clinicalPathStageDTO.setCrteTime(crteTime);
            clinicalPathStageDTO.setId(id);
            return clinicalPathStageDAO.insert(clinicalPathStageDTO) > 0;
        } else{ // 编辑
            Optional.ofNullable(clinicalPathStageDTO.getId()).orElseThrow(() ->new AppException("主键id不能为空"));
            return clinicalPathStageDAO.updateById(clinicalPathStageDTO) > 0;
        }

    }
    /**
     * @Meth: deleteClinicalPathStage
     * @Description: 删除阶段描述
     * @Param: [clinicalPathStageDTO]
     * @return: java.lang.Boolean
     * @Author: zhangguorui
     * @Date: 2021/9/11
     */
    @Override
    public Boolean deleteClinicalPathStage(ClinicalPathStageDTO clinicalPathStageDTO) {
        Optional.ofNullable(clinicalPathStageDTO.getHospCode()).orElseThrow(() -> new AppException("医院编码不能为空"));
        Optional.ofNullable(clinicalPathStageDTO.getIds()).orElseThrow(() -> new AppException("无删除数据"));
        Optional.ofNullable(clinicalPathStageDTO.getListId()).orElseThrow(() -> new AppException("目录id为空"));
        return clinicalPathStageDAO.deleteClinicalPathStage(clinicalPathStageDTO) > 0;
    }
    /**
     * @Meth: queryClinicalPathStageById
     * @Description: 通过id查询阶段路径描述
     * @Param: [clinicalPathStageDTO]
     * @return: cn.hsa.module.clinical.clinicalpathstage.dto.ClinicalPathStageDTO
     * @Author: zhangguorui
     * @Date: 2021/9/13
     */
    @Override
    public ClinicalPathStageDTO queryClinicalPathStageById(ClinicalPathStageDTO clinicalPathStageDTO) {
        if ("1".equals(clinicalPathStageDTO.getIsFlag())){ // 查询最大的序列号
            if(StringUtils.isEmpty(clinicalPathStageDTO.getListId())) {
              throw new AppException("请先选择路径目录");
            }
            return clinicalPathStageDAO.getMaxSortNo(clinicalPathStageDTO);
        } else{
            Optional.ofNullable(clinicalPathStageDTO.getId()).orElseThrow(()->new AppException("临床路径阶段描述主键不能为空"));
            return clinicalPathStageDAO.queryClinicalPathStageById(clinicalPathStageDTO);
        }
    }
}
