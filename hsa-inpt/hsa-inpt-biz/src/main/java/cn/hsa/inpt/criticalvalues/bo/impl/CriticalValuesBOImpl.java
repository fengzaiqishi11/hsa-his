package cn.hsa.inpt.criticalvalues.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.inpt.criticalvalues.bo.CriticalValuesBO;
import cn.hsa.module.inpt.criticalvalues.dao.CriticalValuesDAO;
import cn.hsa.module.inpt.criticalvalues.dto.CriticalValueItemDTO;
import cn.hsa.module.inpt.criticalvalues.dto.CriticalValuesDTO;
import cn.hsa.util.ListUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
public class CriticalValuesBOImpl extends HsafBO implements CriticalValuesBO {

    /**
     * 危急值数据访问层
     */
    @Resource
    private CriticalValuesDAO criticalValuesDAO;

    /**
     * @Method: updateCriticalValue()
     * @Description: 修改危急值
     * @Param: criticalValuesDTO
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:14
     * @Return: boolean
     */
    @Override
    public PageDTO queryPage(CriticalValuesDTO criticalValuesDTO) {
        PageHelper.startPage(criticalValuesDTO.getPageNo(), criticalValuesDTO.getPageSize());
        List<CriticalValuesDTO> criticalValuesDTOList = criticalValuesDAO.queryPage(criticalValuesDTO);
        return PageDTO.of(criticalValuesDTOList);
    }

    /**
     * @Method: updateCriticalValue()
     * @Description: 1.修改危急值表里面的处理危急值信息
     *               2.根据就诊id和医院编码查询危急值表里面，该病人是否还有危急项目，如果危急项目为空，更新住院病人表的危急值状态
     * @Param: criticalValuesDTOList:危急值的参数集合
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:14
     * @Return: boolean
     */
    @Override
    public boolean updateCriticalValue(List<CriticalValuesDTO> criticalValuesDTOList) {
        criticalValuesDAO.updateCriticalValue(criticalValuesDTOList);
        /**
         * 如果该病人的危急值已经全部处理，则修改住院病人的危急值状态
         */
        CriticalValuesDTO criticalValuesDTO = new CriticalValuesDTO();
        // 医院编码
        criticalValuesDTO.setHospCode(criticalValuesDTOList.get(0).getHospCode());
        // 就诊id
        criticalValuesDTO.setVisitId(criticalValuesDTOList.get(0).getVisitId());
        List<CriticalValuesDTO> valuesDTOList = criticalValuesDAO.queryAll(criticalValuesDTO);
        if(ListUtils.isEmpty(valuesDTOList)){
           criticalValuesDAO.updateInptVisitValueCode(criticalValuesDTO);
        }
        return true;
    }


    /**
     * @Method: queryItemByVisitId()
     * @Description: 根据就诊查看检查项目类型，查询医嘱类型是lis医技的
     * @Param: criticalValuesDTO
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:14
     * @Return: boolean
     */
    @Override
    public PageDTO queryItemByVisitId(CriticalValueItemDTO criticalValueItemDTO) {
        PageHelper.startPage(criticalValueItemDTO.getPageNo(), criticalValueItemDTO.getPageSize());
        List<CriticalValueItemDTO> criticalValueItemDTOList = criticalValuesDAO.queryItemByVisitId(criticalValueItemDTO);
        return PageDTO.of(criticalValueItemDTOList);
    }
}
