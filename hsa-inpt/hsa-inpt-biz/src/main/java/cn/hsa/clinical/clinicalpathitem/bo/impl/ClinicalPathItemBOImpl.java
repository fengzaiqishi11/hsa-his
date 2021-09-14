package cn.hsa.clinical.clinicalpathitem.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.clinical.clinicalpathitem.bo.ClinicalPathItemBO;
import cn.hsa.module.clinical.clinicalpathitem.dao.ClinicalPathItemDAO;
import cn.hsa.module.clinical.clinicalpathitem.dto.ClinicalPathItemDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.Op;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class ClinicalPathItemBOImpl implements ClinicalPathItemBO {
    @Resource
    private ClinicalPathItemDAO clinicalPathItemDAO;
    @Resource
    private BaseOrderRuleService baseOrderRuleService;
    /**
     * @Description: 查询满足条件的临床路径
     * @Param: [queryDTO]
     * @return: java.util.List<cn.hsa.module.clinical.clinicalpathitem.dto.ClinicalPathItemDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/9
     */
    @Override
    public PageDTO queryAll(ClinicalPathItemDTO queryDTO) {
        Optional.ofNullable(queryDTO.getHospCode()).orElseThrow(() -> new AppException("医院编码不能为空"));
        PageHelper.startPage(queryDTO.getPageNo(), queryDTO.getPageSize());
        return PageDTO.of(clinicalPathItemDAO.queryAll(queryDTO));
    }

    /**
     * @Meth:updateOrAddPathItem
     * @Description: 更新或者添加临床项目
     * @Param: [clinicalPathItemDTO]
     * @return: java.lang.Boolean
     * @Author: zhangguorui
     * @Date: 2021/9/9
     */
    @Override
    public Boolean updateOrAddPathItem(ClinicalPathItemDTO clinicalPathItemDTO) {
        Optional.ofNullable(clinicalPathItemDTO.getHospCode()).orElseThrow(() -> new AppException("医院编码不能为空"));
        // 校验项目编码
        int i = clinicalPathItemDAO.queryByCode(clinicalPathItemDTO);
        if (i > 0) {
            throw new AppException("项目编码不能重复");
        }
        if (StringUtils.isEmpty(clinicalPathItemDTO.getId())) { // id为空 说明为添加
            // 生成主键
            String id = SnowflakeUtils.getId();
            clinicalPathItemDTO.setId(id);
            // 生成拼音码
            if (StringUtils.isEmpty(clinicalPathItemDTO.getPym())) {
                clinicalPathItemDTO.setPym(PinYinUtils.toFirstPY(clinicalPathItemDTO.getName()));
            }
            // 生成五笔码
            if (StringUtils.isEmpty(clinicalPathItemDTO.getWbm())) {
                clinicalPathItemDTO.setWbm(WuBiUtils.getWBCode(clinicalPathItemDTO.getName()));
            }
            HashMap map = new HashMap();
            map.put("hospCode",clinicalPathItemDTO.getHospCode());
            map.put("typeCode", Constants.ORDERRULE.LCLJXM);
            WrapperResponse<String> orderNo = baseOrderRuleService.getOrderNo(map);
            if(StringUtils.isEmpty(orderNo.getData())) {
                throw new AppException("系统没有临床路径项目描述单号规则");
            }
            // 新增赋值编码
            clinicalPathItemDTO.setCode(orderNo.getData());
            // 创建时间
            clinicalPathItemDTO.setCrteTime(DateUtils.getNow());
            // 插入数据库
            return clinicalPathItemDAO.insert(clinicalPathItemDTO) > 0;
        } else {
            Optional.ofNullable(clinicalPathItemDTO.getId()).orElseThrow(() -> new AppException("无法保存,主键为空"));
            // 编辑
            return clinicalPathItemDAO.updateById(clinicalPathItemDTO) > 0;
        }
    }

    /**
     * @Meth:deletePathItemBatch
     * @Description: 直接删除临床路径项目
     * @Param: [clinicalPathItemDTO]
     * @return: java.lang.Boolean
     * @Author: zhangguorui
     * @Date: 2021/9/9
     */
    @Override
    public Boolean deletePathItemBatch(ClinicalPathItemDTO clinicalPathItemDTO) {
        Optional.ofNullable(clinicalPathItemDTO.getHospCode()).orElseThrow(() -> new AppException("医院编码不能为空"));
        if (ListUtils.isEmpty(clinicalPathItemDTO.getIds())) {
            throw new AppException("请选中要删除的数据");
        }
        return clinicalPathItemDAO.deletePathItemBatch(clinicalPathItemDTO) > 0;
    }
    /**
     * @Description: 根据id临床路径项目
     * @Param: [map]
     * @Author: zhangguorui
     * @Date: 2021/9/9
     */
    @Override
    public ClinicalPathItemDTO queryPathItemById(ClinicalPathItemDTO clinicalPathItemDTO) {
        Optional.ofNullable(clinicalPathItemDTO.getHospCode()).orElseThrow(() -> new AppException("医院编码不能为空"));
        Optional.ofNullable(clinicalPathItemDTO.getId()).orElseThrow(() -> new AppException("项目id不能为空"));
        ClinicalPathItemDTO resultClinicalPathItemDTO = clinicalPathItemDAO.queryPathItemById(clinicalPathItemDTO);
        Optional.ofNullable(resultClinicalPathItemDTO).orElseThrow(() -> new AppException("查询不到数据"));
        return resultClinicalPathItemDTO;
    }
}
