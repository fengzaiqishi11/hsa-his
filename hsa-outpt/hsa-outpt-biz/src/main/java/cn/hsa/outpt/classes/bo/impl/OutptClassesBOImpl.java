package cn.hsa.outpt.classes.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.outpt.classes.bo.OutptClassesBO;
import cn.hsa.module.outpt.classes.dao.OutptClassesDAO;
import cn.hsa.module.outpt.classes.dto.OutptClassesDTO;
import cn.hsa.util.PinYinUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
/**
 * @Package_name: cn.hsa.outpt.classes.bo.impl
 * @Class_name:: OutptClassesBOImpl
 * @Description: 班次信息维护BO层实现类
 * @Author: zhangxuan
 * @Date: 2020-08-14 10:31
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class OutptClassesBOImpl extends HsafBO implements OutptClassesBO {
    /**
     * 班次数据库访问接口
     */
    @Resource
    private OutptClassesDAO outptClassesDao;
    /**
     * @Menthod queryPage()
     * @Desrciption 根据条件查询班次信息
     * @Param
     * 1. outptClassesDTO  班次数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/28 17:02
     * @Return cn.hsa.sys.PageDTO
     **/
    @Override
    public PageDTO queryPage(OutptClassesDTO outptClassesDTO) {
        // 设置分页信息
        PageHelper.startPage(outptClassesDTO.getPageNo(), outptClassesDTO.getPageSize());
        // 根据条件查询所
        List<OutptClassesDTO> outptClassesDTOS = outptClassesDao.queryPage(outptClassesDTO);
        return PageDTO.of(outptClassesDTOS);
    }

    /**
     * @Menthod queryById()
     * @Desrciption  查询所有班次信息
     *
     * @Param
     * [outptClassesDTO]
     * @Author zhangxuan
     * @Date   2020/7/28 15:31
     * @Return java.util.List<cn.hsa.module.sys.parameter.dto.OutptClassesDTO>
     **/
    @Override
    public OutptClassesDTO getById(OutptClassesDTO outptClassesDTO) {
        OutptClassesDTO outptClassesDTOS = outptClassesDao.getById(outptClassesDTO);
        return outptClassesDTOS;
    }

    /**
     * @Menthod queryAll()
     * @Desrciption  查询所有班次信息
     *
     * @Param
     * [outptClassesDTO]
     * @Author zhangxuan
     * @Date   2020/7/28 15:31
     * @Return java.util.List<cn.hsa.module.sys.parameter.dto.outptClassesDTO>
     **/
    @Override
    public List<OutptClassesDTO> queryAll(OutptClassesDTO outptClassesDTO) {
        List<OutptClassesDTO> outptClassesDTOS = outptClassesDao.queryAll(outptClassesDTO);
        return outptClassesDTOS;
    }


    /**
     * @Menthod insert()
     * @Desrciption 新增班次
     * @Param
     * 1. outptClassesDTO  班次数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/28 15:53
     * @Return int
     **/
    @Override
    public Boolean insert(OutptClassesDTO outptClassesDTO) {
            return save(outptClassesDTO);
    }

    /**
     * @Menthod deleteSuppelier()
     * @Desrciption 删除班次
     * @Param
     * 1. map
     * @Author zhangxuan
     * @Date   2020/7/28 15:57
     * @Return int
     **/
    @Override
    public Boolean delete(OutptClassesDTO outptClassesDTO) {
        if(StringUtils.isEmpty(outptClassesDTO.getHospCode())){
            return false;
        }else{
            outptClassesDTO.setHospCode(outptClassesDTO.getHospCode());
            if (outptClassesDao.queryIdsIsExist(outptClassesDTO)> 0) {
                throw new AppException("该班次已被使用");
            }else{
                return outptClassesDao.delete(outptClassesDTO) > 0;
            }
        }
    }

    /**
     * @Menthod update()
     * @Desrciption 修改班次信息
     * @Param
     * 1. outptClassesDTO  班次数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/28 15:58
     * @Return int
     **/
    @Override
    public Boolean update(OutptClassesDTO outptClassesDTO) {
        return save(outptClassesDTO);
    }

    /**
     * @Method: save()
     * @Description: 该方法主要用来保存班次信息维护修改和新增的信息
     * @Param: outptClassesDTO数据传输对象
     * @Author: zhangxuan
     * @Date: 2020/8/13 14:17
     * @Return: int
     */
    private Boolean save(OutptClassesDTO outptClassesDTO) {
        //生成拼音码
        if (StringUtils.isEmpty(outptClassesDTO.getPym())) {
            outptClassesDTO.setPym(PinYinUtils.toFirstPY(outptClassesDTO.getName()));
        }
        //生成五笔码
        if (StringUtils.isEmpty(outptClassesDTO.getWbm())) {
            outptClassesDTO.setPym(PinYinUtils.toFirstPY(outptClassesDTO.getName()));
        }
        //判断名称是否已存在
        if (outptClassesDao.queryNameIsExist(outptClassesDTO)> 0) {
            throw new AppException("该班次名称已经存在");
        } else{
            if(StringUtils.isEmpty(outptClassesDTO.getId())){
                outptClassesDTO.setId(SnowflakeUtils.getId());  // 设置主键id
                return outptClassesDao.insert(outptClassesDTO) > 0;
            } else {
                if (outptClassesDao.queryIdIsExist(outptClassesDTO)> 0) {
                    throw new AppException("该班次已被使用");
                }else {
                    return outptClassesDao.update(outptClassesDTO) > 0;
                }
            }
        }
    }
}
