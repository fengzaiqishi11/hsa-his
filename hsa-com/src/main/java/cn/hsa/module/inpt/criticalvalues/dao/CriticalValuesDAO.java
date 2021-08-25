package cn.hsa.module.inpt.criticalvalues.dao;

import cn.hsa.base.PageDTO;
import cn.hsa.module.inpt.criticalvalues.dto.CriticalValueItemDTO;
import cn.hsa.module.inpt.criticalvalues.dto.CriticalValuesDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @PackageName: cn.hsa.module.inpt.criticalvalues.dao
 * @Class_name: CriticalValuesDAO
 * @Description: 危急值数据访问层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/1/7 14:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface CriticalValuesDAO {

    /**
     * @Method: queryPage()
     * @Description: 根据就诊id,医院编码查询危急值数据
     * @Param: baseDeptDTO
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2021/1/7 14:14
     * @Return:
     */
    List<CriticalValuesDTO> queryPage(CriticalValuesDTO criticalValuesDTO);

    /**
     * @Method: updateCriticalValue()
     * @Description: 修改危急值
     * @Param: criticalValuesDTO
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:14
     * @Return: boolean
     */
    boolean updateCriticalValue(@Param("criticalValuesDTOList") List<CriticalValuesDTO> criticalValuesDTOList);

    /**
     * @Method: updateCriticalValue()
     * @Description: 修改住院病人表的危急值状态
     * @Param: criticalValuesDTO
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:14
     * @Return: boolean
     */
    void updateInptVisitValueCode(CriticalValuesDTO criticalValuesDTO);

    /**
     * @Method: queryItemByVisitId()
     * @Description: 根据就诊查看检查项目类型，查询医嘱类型是lis医技的
     * @Param: criticalValuesDTO
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:14
     * @Return: boolean
     */
    List<CriticalValueItemDTO> queryItemByVisitId(CriticalValueItemDTO criticalValueItemDTO);

    /**
     * @Method: queryAll()
     * @Description: 通过就诊id,医院编码查询是否还包含有危机项目
     * @Param: criticalValuesDTO
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:14
     * @Return: boolean
     */
    List<CriticalValuesDTO> queryAll(CriticalValuesDTO criticalValuesDTO);
}
