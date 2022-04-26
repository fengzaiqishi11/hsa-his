package cn.hsa.module.center.tablecontrast.dao;

import cn.hsa.base.PageDTO;
import cn.hsa.module.center.hospital.dto.CenterSyncFlowDto;
import cn.hsa.module.center.hospital.entity.CenterRootDatabaseBO;
import cn.hsa.module.center.tablecontrast.dto.TableContrastDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.hospital.dao
 * @Class_name:: TableContrastDAO
 * @Description: TableContrast数据访问层接口
 * @Author: zhangxuan
 * @Date: 2020-07-30 13:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface TableContrastDAO {
    /**
     * @Menthod getByHospCode()
     * @Desrciption  通过code查询TableContrast信息
     * @Param
     * 1.TableContrast编码
     * @Author zhangxuan
     * @Date   2020/8/28 15:45
     * @Return cn.hsa.module.center.hospital.dao.TableContrastDAO
     **/
    TableContrastDTO getById(TableContrastDTO TableContrastDTO);
    /**
     * @Menthod queryPage
     * @Desrciption 根据条件查询TableContrast信息
     * @Param
     * 1. TableContrastDTO  TableContrast数据对象
     * @Author zhangxuan
     * @Date   2020/7/28 17:02
     * @Return cn.hsa.base.PageDTO
     **/
    List<TableContrastDTO> queryPage(TableContrastDTO TableContrastDTO);

    /**
     * @Menthod insert()
     * @Desrciption 新增TableContrast
     * @Param
     * 1. TableContrastDTO  TableContrast数据对象
     * @Author zhangxuan
     * @Date   2020/8/03 15:53
     * @Return int
     **/
    boolean insert(TableContrastDTO TableContrastDTO);

    /**
     * @Menthod delete()
     * @Desrciption 删除TableContrast
     * @Param
     *  1. map
     * @Author zhangxuan
     * @Date   2020/8/03 15:57
     * @Return int
     **/
    int delete(TableContrastDTO TableContrastDTO);
    /**
     * @Menthod update()
     * @Desrciption 修改TableContrast信息
     * @Param
     * 1. TableContrastDTO  TableContrast数据对象
     * @Author zhangxuan
     * @Date   2020/7/28 15:58
     * @Return int
     **/
    int update(TableContrastDTO TableContrastDTO);
}
