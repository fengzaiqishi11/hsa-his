package cn.hsa.sys.system.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.sys.system.bo.SysSystemBO;
import cn.hsa.module.sys.system.dao.SysSystemDao;
import cn.hsa.module.sys.system.dto.SysSystemDTO;
import cn.hsa.module.sys.system.entity.SysSystemDo;
import cn.hsa.module.sys.user.dto.SysUserSystemDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.sys.system.bo.impl
 * @class_name: SysSystemBOImpl
 * @Description: 子系统
 * @Author: youxianlin
 * @Email: 254580179@qq.com
 * @Date: 2020/7/30 9:22
 * @Company: 创智和宇
 **/
@Component
@Slf4j
public class SysSystemBOImpl extends HsafBO implements SysSystemBO {

    @Resource
    private SysSystemDao sysSystemDao;

    /**
     * @Method: queryAll
     * @Description: 获取所有的子系统列表
     * @Param: [sysSystemDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 9:24
     * @Return: java.util.List<cn.hsa.module.sys.system.entity.SysSystemDo>
     **/
    @Override
    public List<SysSystemDTO> queryAll(SysSystemDo sysSystemDo) {
        return sysSystemDao.queryAll(sysSystemDo);
    }

    /**
     * @Method: queryPage
     * @Description: 分页查询
     * @Param: [sysSystemDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 9:53
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryPage(SysSystemDTO sysSystemDTO) {
        // 设置分页信息
        PageHelper.startPage(sysSystemDTO.getPageNo(), sysSystemDTO.getPageSize());

        // 查询所有
        List<SysSystemDTO> list = sysSystemDao.queryPage(sysSystemDTO);

        // 返回分页结果
        return PageDTO.of(list);
    }

    /**
     * @Method: save
     * @Description: 保存、修改、删除子系统
     * @Param: [SysSystemDTO]
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/6 9:05
     * @Return: java.lang.Boolean
     **/
    @Override
    public Boolean save(SysSystemDTO sysSystemDTO) {
        //根据编码查询数据,校验编码不能重复
        if (sysSystemDao.getCount(sysSystemDTO) > 0) {
            throw new AppException("【" + sysSystemDTO.getCode() + "】系统编码已存在！");
        }
        if (sysSystemDao.getCountByName(sysSystemDTO) > 0) {
            throw new AppException("【" + sysSystemDTO.getName() + "】系统名称已存在！");
        }

        //处理拼音码
        if (!StringUtils.isEmpty(sysSystemDTO.getName())) {
            sysSystemDTO.setPym(PinYinUtils.toFirstPY(sysSystemDTO.getName()));
        }
        //处理五笔码
        if (!StringUtils.isEmpty(sysSystemDTO.getName())) {
            sysSystemDTO.setWbm(WuBiUtils.getWBCode(sysSystemDTO.getName()));
        }

        //根据系统编码查询是否关联用户
        List<SysUserSystemDTO> list = sysSystemDao.querySysUserSystem(sysSystemDTO);

        if (StringUtils.isEmpty(sysSystemDTO.getId())) {//新增
            sysSystemDTO.setId(SnowflakeUtils.getId());
            sysSystemDTO.setIsValid("1");
            sysSystemDao.insert(sysSystemDTO);
            return true;
        } else {//修改
            if (!ListUtils.isEmpty(list)) {
                throw new RuntimeException("系统已关联用户，不可以修改");
            }
            sysSystemDao.update(sysSystemDTO);
            return true;
        }
    }

    /**
     * @Method getById
     * @Desrciption 通过id获取值
     * @Param: [SysSystemDTO]
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/6 9:05
     * @Return cn.hsa.module.sys.system.dto.SysSystemDTO
     **/
    @Override
    public SysSystemDTO getById(SysSystemDTO sysSystemDTO) {
        return sysSystemDao.getById(sysSystemDTO);
    }

    /**
     * @Method queryByUserCode
     * @Description 根据医院编码、用户编码（员工工号）查询可登陆子系统信息
     * @Param
     * @Author zhongming
     * @Date 2020/8/10 10:28
     * @Return
     **/
    @Override
    public List<Map<String, Object>> queryByUserCode(Map map) {
        return sysSystemDao.queryByUserCode(map);
    }

    /**
     * @Method querySystemSeqNo
     * @Desrciption 查询子系统序号自动填充前端
     * @Param: [SysSystemDTO]
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/6 9:05
     * @Return int
     **/
    @Override
    public Integer querySystemSeqNo(SysSystemDTO sysSystemDTO) {
        return sysSystemDao.querySystemSeqNo(sysSystemDTO);
    }

    /**
     * @Method: delete
     * @Description: 删除子系统
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/6 9:05
     * @Return: java.lang.Boolean
     **/
    @Override
    public Boolean delete(Map map) {
        List<SysSystemDTO> sysSystemDTOS = MapUtils.get(map, "sysSystemDTOS");

        if (ListUtils.isEmpty(sysSystemDTOS)) {
            throw new RuntimeException("未选择需要删除的子系统");
        }
        //判断要删除的子系统是否关联用户
        List<SysUserSystemDTO> list = sysSystemDao.querySysUserSystemByList(map);
        if (!ListUtils.isEmpty(list)) {
            throw new RuntimeException("系统已关联用户，不可以删除");
        }
        //删除处理
        return sysSystemDao.updateSystemByIsvalid(sysSystemDTOS) > 0;
    }
}
