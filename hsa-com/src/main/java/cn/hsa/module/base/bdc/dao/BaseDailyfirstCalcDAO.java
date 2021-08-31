package cn.hsa.module.base.bdc.dao;

import cn.hsa.module.base.bdc.dto.BaseDailyfirstCalcDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.syncdailyfirst.dao
 * @Class_name:: BaseDailyfirstCalcDAO
 * @Description:
 * @Author: ljh
 * @Date: 2020/8/27 13:23
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BaseDailyfirstCalcDAO {

    /**
     * @Package_name: cn.hsa.module.base.syncdailyfirst.dao
     * @Class_name:: BaseDailyfirstCalcDAO
     * @Description: 查询
     * @Author: ljh
     * @Date: 2020/8/27 13:23
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    List<BaseDailyfirstCalcDTO> queryAll(BaseDailyfirstCalcDTO baseDailyfirstCalcDTO);


    /**
     * @Package_name: cn.hsa.module.base.syncdailyfirst.dao
     * @Class_name:: BaseDailyfirstCalcDAO
     * @Description: 删除
     * @Author: ljh
     * @Date: 2020/8/27 13:23
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
    int deleteById(Long id);

    /**
     * @Package_name: cn.hsa.module.base.syncdailyfirst.dao
     * @Class_name:: BaseDailyfirstCalcDAO
     * @Description: 修改状态
     * @Author: ljh
     * @Date: 2020/8/27 13:23
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    int  deleteBylist(List<BaseDailyfirstCalcDTO> list);

    /**
     * @Package_name: cn.hsa.module.base.syncdailyfirst.dao
     * @Class_name:: BaseDailyfirstCalcDAO
     * @Description: 批量修改
     * @Author: ljh
     * @Date: 2020/8/27 13:23
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
    int updateBatch(@Param("list")List<BaseDailyfirstCalcDTO> list);

    /**
     * @Package_name: cn.hsa.module.base.syncdailyfirst.dao
     * @Class_name:: BaseDailyfirstCalcDAO
     * @Description: 批量新增
     * @Author: ljh
     * @Date: 2020/8/27 13:23
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
    int insertList(List<BaseDailyfirstCalcDTO> list);

    /**
     * @Method: queryDaily
     * @Description: 获取每日首次计费信息
     * @Param: [dailyfirstCalcDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/24 11:44
     * @Return: cn.hsa.module.base.bdc.dto.BaseDailyfirstCalcDTO
     **/
    List<BaseDailyfirstCalcDTO> queryDaily(BaseDailyfirstCalcDTO dailyfirstCalcDTO);
}
