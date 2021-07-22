package cn.hsa.module.sys.system.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.sys.system.dto.SysSystemDTO;
import cn.hsa.module.sys.system.entity.SysSystemDo;

import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.module.sys.system.bo
* @class_name: SysSystemBO
* @Description: 子系统
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/30 9:09
* @Company: 创智和宇
**/
public interface SysSystemBO {

    /**
     * @Method: queryAll
     * @Description: 获取所有的子系统列表
     * @Param: [sysSystemDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 9:24
     * @Return: java.util.List<cn.hsa.module.sys.system.entity.SysSystemDo>
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
    PageDTO queryPage(SysSystemDTO sysSystemDTO);

    /**
     * @Method: save
     * @Description: 保存、修改、删除子系统
     * @Param: [SysSystemDTO]
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/6 9:05
     * @Return: java.lang.Boolean
     **/
    Boolean save(SysSystemDTO sysSystemDTO);

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

    /**
     * @Method: delete
     * @Description: 删除子系统
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/6 9:05
     * @Return: java.lang.Boolean
     **/
    Boolean delete(Map map);
}
