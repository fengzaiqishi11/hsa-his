package cn.hsa.module.sys.system.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sys.system.dto.SysSystemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.module.sys.system.service
* @class_name: SysSystemService
* @Description: 子系统
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/30 9:08
* @Company: 创智和宇
**/
@FeignClient(value = "hsa-sys")
public interface SysSystemService {

    /**
     * @Method: queryAll
     * @Description: 获取所有的子系统列表
     * @Param: [sysSystemDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 9:21
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.system.entity.SysSystemDo>>
     **/
    @GetMapping("/service/sys/system/queryAll")
    WrapperResponse<List<SysSystemDTO>>  queryAll(Map map);

    /**
     * @Method: queryPage
     * @Description: 分页获取子系统列表
     * @Param: [sysSystemDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 9:43
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/sys/system/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method: save
     * @Description: 保存、修改、删除子系统
     * @Param: [SysSystemDTO]
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/6 9:05
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    WrapperResponse<Boolean> save(Map map);

    /**
     * @Method getById
     * @Desrciption 通过id获取值
     * @Param: [SysSystemDTO]
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/6 9:05
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.system.dto.SysSystemDTO>
     **/
    WrapperResponse<SysSystemDTO> getById(Map map);

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
    WrapperResponse<List<Map<String, Object>>> queryByUserCode(Map map);

    /**
     * @Method querySystemSeqNo
     * @Desrciption 查询子系统序号自动填充前端
     * @Param: [SysSystemDTO]
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/6 9:05
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<int>
     **/
    WrapperResponse<Integer> querySystemSeqNo(Map map);

    /**
     * @Method: delete
     * @Description: 删除子系统
     * @Param: List<SysSystemDTO>
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/6 9:05
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    WrapperResponse<Boolean> delete(Map map);
}
