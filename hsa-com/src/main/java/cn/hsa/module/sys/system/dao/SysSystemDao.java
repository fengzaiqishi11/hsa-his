package cn.hsa.module.sys.system.dao;

import cn.hsa.module.sys.system.dto.SysSystemDTO;
import cn.hsa.module.sys.system.entity.SysSystemDo;
import cn.hsa.module.sys.user.dto.SysUserSystemDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.module.sys.system.dao
* @class_name: SysSystemDao
* @Description: 子系统
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/30 9:10
* @Company: 创智和宇
**/
public interface SysSystemDao {

    /**
     * @Method: queryAll
     * @Description: 获取所有的子系统列表
     * @Param: [sysSystemDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 9:21
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.system.entity.SysSystemDo>>
     **/
    List<SysSystemDTO> queryAll(SysSystemDo sysSystemDo);

    /**
     * @Method: queryPage
     * @Description: 分页查询
     * @Param: [sysSystemDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 9:53
     * @Return: cn.hsa.base.PageDTO
     **/
    List<SysSystemDTO> queryPage(SysSystemDTO sysSystemDTO);

    /**
     * @Method: getCount
     * @Description: 根据编码获取数量
     * @Param: [sysSystemDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 9:54
     * @Return: java.lang.Boolean
     **/
    int getCount(SysSystemDTO sysSystemDTO);

    /**
     * @Method: insert
     * @Description: 保存
     * @Param: [SysSystemDTO]
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/6 9:05
     * @Return: java.lang.int
     **/
    int insert(SysSystemDTO sysSystemDTO);

    /**
     * @Method: update
     * @Description: 修改
     * @Param: [SysSystemDTO]
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/6 9:05
     * @Return: java.lang.int
     **/
    int update(SysSystemDTO sysSystemDTO);

    /**
     * @Method: deleteSysSystem
     * @Description: 删除
     * @Param: [sysSystemDTOS]
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/6 9:05
     * @Return: java.lang.int
     **/
    int updateSystemByIsvalid(@Param("list") List<SysSystemDTO> sysSystemDTOS);

    /**
     * @Method getById
     * @Desrciption 通过id获取值
     * @Param: [SysSystemDTO]
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/6 9:05
     * @Return cn.hsa.module.sys.system.dto.SysSystemDTO
     **/
    SysSystemDTO getById(SysSystemDTO sysSystemDTO);

    /**
     * @Method queryByUserCode
     * @Description
     * 根据医院编码、用户编码（员工工号）查询可登陆子系统信息
     * @Param
     *
     * @Author zhongming
     * @Date 2020/8/10 10:28
     * @Return
     **/
    List<Map<String, Object>> queryByUserCode(Map map);

    /**
     * @Method querySystemSeqNo
     * @Desrciption 查询子系统序号自动填充前端
     * @Param: [SysSystemDTO]
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/6 9:05
     * @Return int
     **/
    Integer querySystemSeqNo(SysSystemDTO sysSystemDTO);

    int getCountByName(SysSystemDTO sysSystemDTO);

    List<SysUserSystemDTO> querySysUserSystem(SysSystemDTO sysSystemDTO);

    List<SysUserSystemDTO> querySysUserSystemByList(Map map);
}
