package cn.hsa.sync.syncparameter.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.sync.syncparameter.bo.SyncParameterBO;
import cn.hsa.module.sync.syncparameter.dao.SyncParameterDAO;
import cn.hsa.module.sync.syncparameter.dto.SyncParameterDTO;
import cn.hsa.util.PinYinUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.sync.data.bo.impl
 * @Class_name: syncSupplierBOImpl
 * @Describe: 参数业务逻辑实现层
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/9/2 16:56
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class SyncParameterBOImpl extends HsafBO implements SyncParameterBO {

    /**
     * 参数数据库访问接口
     */
    @Resource
    private SyncParameterDAO syncParameterDao;
    /**
     * @Menthod queryPage()
     * @Desrciption 根据条件查询参数信息
     * @Param
     * 1. syncParameterDTO  参数数据传输对象
     * @Author zhangxuan
     * @Date   2020/9/2 17:02
     * @Return cn.hsa.sync.PageDTO
     **/
    @Override
    public PageDTO queryPage(SyncParameterDTO syncParameterDTO) {
        // 设置分页信息
        PageHelper.startPage(syncParameterDTO.getPageNo(), syncParameterDTO.getPageSize());
        // 根据条件查询所
        List<SyncParameterDTO> syncParameterDTOS = syncParameterDao.queryPage(syncParameterDTO);
        return PageDTO.of(syncParameterDTOS);
    }

    /**
     * @Menthod queryAll()
     * @Desrciption  查询所有参数信息
     *
     * @Param
     * [syncParameterDTO]
     * @Author zhangxuan
     * @Date   2020/9/2 15:31
     * @Return java.util.List<cn.hsa.module.sync.parameter.dto.syncParameterDTO>
     **/
    @Override
    public List<SyncParameterDTO> queryAll(SyncParameterDTO syncParameterDTO) {
        List<SyncParameterDTO> syncParameterDTOS = syncParameterDao.queryAll(syncParameterDTO);
        return syncParameterDTOS;
    }


    /**
     * @Menthod insert()
     * @Desrciption 新增参数
     * @Param
     * 1. syncParameterDTO  参数数据传输对象
     * @Author zhangxuan
     * @Date   2020/9/2 15:53
     * @Return int
     **/
    @Override
    public boolean insert(SyncParameterDTO syncParameterDTO) {
            return save(syncParameterDTO);
    }

    /**
     * @Menthod deleteSuppelier()
     * @Desrciption 删除参数
     * @Param
     * 1. map
     * @Author zhangxuan
     * @Date   2020/9/2 15:57
     * @Return int
     **/
    @Override
    public boolean delete(SyncParameterDTO syncParameterDTO) {
            return syncParameterDao.delete(syncParameterDTO) > 0;
    }

    /**
     * @Menthod update()
     * @Desrciption 修改参数信息
     * @Param
     * 1. syncParameterDTO  参数数据传输对象
     * @Author zhangxuan
     * @Date   2020/9/2 15:58
     * @Return int
     **/
    @Override
    public boolean update(SyncParameterDTO syncParameterDTO) {
        return save(syncParameterDTO);
    }

    /**
     * @Method: save()
     * @Description: 该方法主要用来保存参数信息维护修改和新增的信息
     * @Param: syncParameterDTO数据传输对象
     * @Author: zhangxuan
     * @Date: 2020/8/13 14:17
     * @Return: int
     */
    private boolean save(SyncParameterDTO syncParameterDTO) {
        if (syncParameterDao.queryCodeIsExist(syncParameterDTO)> 0) {
            throw new AppException("该参数编码已经存在" + syncParameterDTO.getCode());
        } else if (syncParameterDao.queryNameIsExist(syncParameterDTO)> 0) {
            throw new AppException("该参数名称已经存在" + syncParameterDTO.getName());
        }
        //生成拼音码
        if (StringUtils.isEmpty(syncParameterDTO.getPym())) {
            syncParameterDTO.setPym(PinYinUtils.toFirstPY(syncParameterDTO.getName()));
        }
        //生成五笔码
        if (StringUtils.isEmpty(syncParameterDTO.getWbm())) {
            syncParameterDTO.setWbm(PinYinUtils.toFirstPY(syncParameterDTO.getName()));
        }
        //判断主键id是否存在，如果存在则是修改，否则就是新增
        if (StringUtils.isEmpty(syncParameterDTO.getId())) {
            syncParameterDTO.setId(SnowflakeUtils.getId());
            return syncParameterDao.insert(syncParameterDTO) > 0;
        } else {
            return syncParameterDao.update(syncParameterDTO) > 0;
        }
    }
}
