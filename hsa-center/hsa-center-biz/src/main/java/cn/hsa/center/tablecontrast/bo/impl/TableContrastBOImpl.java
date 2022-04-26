package cn.hsa.center.tablecontrast.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.tablecontrast.bo.TableContrastBO;
import cn.hsa.module.center.tablecontrast.dao.TableContrastDAO;
import cn.hsa.module.center.tablecontrast.dto.TableContrastDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Package_name: cn.hsa.sys.hospital.bo.impl
 * @Class_name:: TableContrastBOImpl
 * @Description: 医院信息管理业务逻辑实现层
 * @Author: zhangxuan
 * @Date: 2020-07-30 13:48
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class TableContrastBOImpl extends HsafBO implements TableContrastBO {
    /**
     * 医院数据库访问接口
     */
    @Resource
    private TableContrastDAO tableContrastDao;
    /**
     * @Menthod getById()
     * @Desrciption  通过id查询医院信息
     * @Param
     * 1. id
     * @Author zhangxuan
     * @Date   2020/7/30 15:45
     * @Return cn.hsa.module.sys.hospital.dao.TableContrastDTO
     **/
    @Override
    public TableContrastDTO getById(TableContrastDTO TableContrastDTO) {
        return this.tableContrastDao.getById(TableContrastDTO);
    }
    /**
     * @Menthod queryPage()
     * @Desrciption 根据条件查询医院信息
     * @Param
     * 1. TableContrastDTO  医院数据传输对象
     * @Author zhangxuan
     * @Date   2020/8/03 17:02
     * @Return cn.hsa.center.PageDTO
     **/
    @Override
    public PageDTO queryPage(TableContrastDTO TableContrastDTO) {
        // 设置分页信息
        PageHelper.startPage(TableContrastDTO.getPageNo(), TableContrastDTO.getPageSize());
        // 根据条件查询所
        List<TableContrastDTO> TableContrastDTOS = tableContrastDao.queryPage(TableContrastDTO);
        return PageDTO.of(TableContrastDTOS);
    }


    /**
     * @Menthod insert()
     * @Desrciption 新增医院
     * @Param
     * 1. TableContrastDTO  医院数据传输对象
     * @Author zhangxuan
     * @Date   2020/8/03 15:53
     * @Return int
     **/
    @Override
    public boolean insert(TableContrastDTO TableContrastDTO) {
            return save(TableContrastDTO);
    }

    /**
     * @Menthod delete()
     * @Desrciption 删除医院
     * @Param
     * 1. map
     * @Author zhangxuan
     * @Date   2020/8/03 15:57
     * @Return int
     **/
    @Override
    public boolean delete(TableContrastDTO TableContrastDTO) {
            return tableContrastDao.delete(TableContrastDTO) > 0;
    }
    /**
     * @Menthod update()
     * @Desrciption 修改医院信息
     * @Param
     * 1. TableContrastDTO  医院数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/30 15:58
     * @Return int
     **/
    @Override
    public boolean update(TableContrastDTO TableContrastDTO) {
        return save(TableContrastDTO);
    }
    /**
     * @Method: save()
     * @Description: 该方法主要用来保存医院信息维护修改和新增的信息
     * @Param: TableContrastDTO数据传输对象
     * @Author: zhangxuan
     * @Date: 2020/8/4 14:17
     * @Return: TableContrastDTO数据传输对象集合
     */
    public boolean save(TableContrastDTO tableContrastDTO) {
        if(StringUtils.isEmpty(tableContrastDTO.getId())){
            tableContrastDTO.setId(SnowflakeUtils.getId());
            return tableContrastDao.insert(tableContrastDTO);
        }
        else{
            return tableContrastDao.update(tableContrastDTO) > 0;
        }
    }
}
