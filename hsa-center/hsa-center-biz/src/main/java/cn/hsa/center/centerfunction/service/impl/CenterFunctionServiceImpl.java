package cn.hsa.center.centerfunction.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.centerfunction.bo.CenterFunctionBO;
import cn.hsa.module.center.centerfunction.dto.CenterFunctionDTO;
import cn.hsa.module.center.centerfunction.dto.CenterFunctionDetailDTO;
import cn.hsa.module.center.centerfunction.service.CenterFunctionService;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import cn.hsa.util.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.sys.code.bo.impl
* @class_name: SysCodeBOImpl
* @Description: 值域代码BO实现层
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/15 11:38
* @Company: 创智和宇
**/
@HsafRestPath("/service/center/code")
@Slf4j
@Service("centerFunctionService_provider")
public class CenterFunctionServiceImpl extends HsafBO implements CenterFunctionService {

    /**
     * 值域代码数据库访问接口
     */
    @Resource
    private CenterFunctionBO centerFunctionBO;


    /**
     * @Method: getCodes
     * @Description: 根据编码获取值域代码值
     * @Param: [code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 11:25
     * @Return: java.util.List
     **/
    @Override
    public WrapperResponse<Map<String, List<CenterFunctionDetailDTO>>> getByCode(Map map) {
        try {
            String code = MapUtils.get(map, "code");
            return WrapperResponse.success(centerFunctionBO.getByCode(code));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method: queryCodePage
     * @Description: 分页获取值域代码列表
     * @Param: [sysCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 15:25
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryCodePage(Map map) {
        try {
            CenterFunctionDTO centerFunctionDTO = MapUtils.get(map, "centerFunctionDTO");
            return WrapperResponse.success(centerFunctionBO.queryCodePage(centerFunctionDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method: queryCodeDetailPage
     * @Description: 分页获取值域代码值列表
     * @Param: [sysCodeDetailDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 15:28
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryCodeDetailPage(Map map) {
        try {
            CenterFunctionDetailDTO centerFunctionDetailDTO = MapUtils.get(map, "centerFunctionDetailDTO");
            return WrapperResponse.success(centerFunctionBO.queryCodeDetailPage(centerFunctionDetailDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method: getCodeById
     * @Description: 根据主键获取值域代码对象
     * @Param: [id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.code.dto.CenterFunctionDTO>
     **/
    @Override
    public WrapperResponse<CenterFunctionDTO> getCodeById(Map map) {
        try {
            String id = MapUtils.get(map, "id");
            // 参数校验
            if (StringUtils.isEmpty(id)) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            return WrapperResponse.success(centerFunctionBO.getCodeById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method: getCodeById
     * @Description: 根据主键获取值域代码值对象
     * @Param: [id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.code.dto.CenterFunctionDTO>
     **/
    @Override
    public WrapperResponse<CenterFunctionDetailDTO> getCodeDetailById(Map map) {
        try {
            String id = MapUtils.get(map, "id");
            // 参数校验
            if (StringUtils.isEmpty(id)) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            return WrapperResponse.success(centerFunctionBO.getCodeDetailById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
    * @Menthod getMaxSeqno
    * @Desrciption 获取最大序号
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/9/1 10:23
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Integer>
    **/
    @Override
    public WrapperResponse<Integer> getMaxSeqno(Map map) {
        String code = MapUtils.get(map, "code");
        return WrapperResponse.success(centerFunctionBO.getMaxSeqno(code));
    }

  /**
     * @Method: insertCode
     * @Description: 新增值域代码
     * @Param: [sysCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:07
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> saveCode(Map map) {
        try {
            CenterFunctionDTO centerFunctionDTO = MapUtils.get(map, "centerFunctionDTO");
            // 参数校验
            if (centerFunctionDTO == null) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            if (StringUtils.isEmpty(centerFunctionDTO.getCode())) {
                return WrapperResponse.error(400,"编码不能为空",null);
            }
            return WrapperResponse.success(centerFunctionBO.saveCode(centerFunctionDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method: insertCode
     * @Description: 新增值域代码值
     * @Param: [sysCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:07
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> saveCodeDetail(Map map) {
        try {
            CenterFunctionDetailDTO centerFunctionDetailDTO = MapUtils.get(map, "centerFunctionDetailDTO");
            // 参数校验
            if (centerFunctionDetailDTO == null) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            return WrapperResponse.success(centerFunctionBO.saveCodeDetail(centerFunctionDetailDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method: deleteCodes
     * @Description: 批量删除值域代码
     * @Param: [idStr]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:11
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> deleteCodes(Map map) {
        try {
            CenterFunctionDTO centerFunctionDTO = MapUtils.get(map, "centerFunctionDTO");
            // 参数校验
            if (centerFunctionDTO.getIds()==null && centerFunctionDTO.getIds().size()<=0) {
                return WrapperResponse.error(400,"ids参数不能为空",null);
            }
            return WrapperResponse.success(centerFunctionBO.deleteCodes(centerFunctionDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method: deleteCodes
     * @Description: 批量删除值域代码值
     * @Param: [idStr]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:11
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> deleteCodeDetails(Map map) {
        try {
            CenterFunctionDetailDTO centerFunctionDetailDTO = MapUtils.get(map, "centerFunctionDetailDTO");
            // 参数校验
            if (centerFunctionDetailDTO.getIds()==null && centerFunctionDetailDTO.getIds().size()<=0) {
                return WrapperResponse.error(400,"ids参数不能为空",null);
            }
            return WrapperResponse.success(centerFunctionBO.deleteCodeDetails(centerFunctionDetailDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method: getCodeTree
     * @Description: 根据编码获取值域代码值树形结构
     * @Param: [code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 11:25
     * @Return: java.util.List
     *
     * @return*/
    @Override
    public WrapperResponse<List<TreeMenuNode>> getCodeTree(Map map) {
        try {
            String code = MapUtils.get(map, "code");
            return WrapperResponse.success(TreeUtils.buildByRecursive(centerFunctionBO.getCodeTree(code),"-1"));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }
}
