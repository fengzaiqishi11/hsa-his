package cn.hsa.sys.system.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sys.system.bo.SysSystemBO;
import cn.hsa.module.sys.system.dto.SysSystemDTO;
import cn.hsa.module.sys.system.entity.SysSystemDo;
import cn.hsa.module.sys.system.service.SysSystemService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.sys.system.service.impl
* @class_name: SysSystemServiceImpl
* @Description: 子系统
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/30 9:19
* @Company: 创智和宇
**/
@HsafRestPath("/service/sys/system")
@Slf4j
@Service("sysSystemService_provider")
public class SysSystemServiceImpl extends HsafBO implements SysSystemService {

    @Resource
    private SysSystemBO sysSystemBO;

    /**
     * @Method: queryAll
     * @Description: 获取所有的子系统列表
     * @Param: [sysSystemDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 9:21
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.system.entity.SysSystemDo>>
     **/
    @HsafRestPath(value = "/queryAll", method = RequestMethod.GET)
    @Override
    public WrapperResponse<List<SysSystemDTO>> queryAll(Map map) {
        try {
            SysSystemDo sysSystemDo = MapUtils.get(map, "sysSystemDo");
            return WrapperResponse.success(sysSystemBO.queryAll(sysSystemDo));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method: queryPage
     * @Description: 分页获取子系统列表
     * @Param: [sysSystemDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 9:43
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @HsafRestPath(value = "/queryPage", method = RequestMethod.GET)
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        try {
            SysSystemDTO sysSystemDTO = MapUtils.get(map, "sysSystemDTO");
            return WrapperResponse.success(sysSystemBO.queryPage(sysSystemDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method: save
     * @Description: 保存、修改、删除子系统
     * @Param: [SysSystemDTO]
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/6 9:05
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    @PostMapping("/service/sys/system/save")
    public WrapperResponse<Boolean> save(Map map) {
        return WrapperResponse.success(sysSystemBO.save(MapUtils.get(map,"sysSystemDTO")));
    }

    /**
     * @Method getById
     * @Desrciption 通过id获取值
     * @Param: [SysSystemDTO]
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/6 9:05
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.system.dto.SysSystemDTO>
     **/
    @Override
    @GetMapping("/service/sys/system/getById")
    public WrapperResponse<SysSystemDTO> getById(Map map) {
        return WrapperResponse.success(sysSystemBO.getById(MapUtils.get(map,"sysSystemDTO")));
    }

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
    @Override
    public WrapperResponse<List<Map<String, Object>>> queryByUserCode(Map map) {
        return WrapperResponse.success(sysSystemBO.queryByUserCode(map));
    }

    /**
     * @Method querySystemSeqNo
     * @Desrciption 查询子系统序号自动填充前端
     * @Param: [SysSystemDTO]
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/6 9:05
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<int>
     **/
    @Override
    @GetMapping("/service/sys/system/querySystemSeqNo")
    public WrapperResponse<Integer> querySystemSeqNo(Map map) {
        return WrapperResponse.success(sysSystemBO.querySystemSeqNo(MapUtils.get(map,"sysSystemDTO")));
    }

    /**
     * @Method: delete
     * @Description: 删除子系统
     * @Param: List<SysSystemDTO>
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/6 9:05
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    @PostMapping("/service/sys/system/delete")
    public WrapperResponse<Boolean> delete(Map map) {
        return WrapperResponse.success(sysSystemBO.delete(map));
    }
}
