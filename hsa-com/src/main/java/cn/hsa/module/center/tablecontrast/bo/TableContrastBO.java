package cn.hsa.module.center.tablecontrast.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.center.tablecontrast.dto.TableContrastDTO;

/**
 * @Package_name: cn.hsa.module.center.tablecontrast.bo
 * @Class_name: TableContrastBO
 * @Describe: TableContrast信息Bo层
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/8/3 15:05
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface TableContrastBO {
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
    PageDTO queryPage(TableContrastDTO TableContrastDTO);

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
    boolean delete(TableContrastDTO TableContrastDTO);
    /**
     * @Menthod update()
     * @Desrciption 修改TableContrast信息
     * @Param
     * 1. TableContrastDTO  TableContrast数据对象
     * @Author zhangxuan
     * @Date   2020/7/28 15:58
     * @Return int
     **/
    boolean update(TableContrastDTO TableContrastDTO);
}
