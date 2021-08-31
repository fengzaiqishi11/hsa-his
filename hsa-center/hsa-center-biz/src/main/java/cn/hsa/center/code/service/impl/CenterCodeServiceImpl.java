package cn.hsa.center.code.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.code.bo.CenterCodeBO;
import cn.hsa.module.center.code.dto.CenterCodeDTO;
import cn.hsa.module.center.code.dto.CenterCodeDetailDTO;
import cn.hsa.module.center.code.dto.CenterCodeSelectDTO;
import cn.hsa.module.center.code.service.CenterCodeService;
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
@Service("centerCodeService_provider")
public class CenterCodeServiceImpl extends HsafBO implements CenterCodeService {

    /**
     * 值域代码数据库访问接口
     */
    @Resource
    private CenterCodeBO centerCodeBO;

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
    public WrapperResponse<Map<String, List<CenterCodeSelectDTO>>> getByCode(Map map) {
        try {
            String code = MapUtils.get(map, "code");
            return WrapperResponse.success(centerCodeBO.getByCode(code));
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
            CenterCodeDTO centerCodeDTO = MapUtils.get(map, "centerCodeDTO");
            return WrapperResponse.success(centerCodeBO.queryCodePage(centerCodeDTO));
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
            CenterCodeDetailDTO centerCodeDetailDTO = MapUtils.get(map, "centerCodeDetailDTO");
            return WrapperResponse.success(centerCodeBO.queryCodeDetailPage(centerCodeDetailDTO));
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
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.code.dto.CenterCodeDTO>
     **/
    @Override
    public WrapperResponse<CenterCodeDTO> getCodeById(Map map) {
        try {
            String id = MapUtils.get(map, "id");
            // 参数校验
            if (StringUtils.isEmpty(id)) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            return WrapperResponse.success(centerCodeBO.getCodeById(id));
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
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.code.dto.CenterCodeDTO>
     **/
    @Override
    public WrapperResponse<CenterCodeDetailDTO> getCodeDetailById(Map map) {
        try {
            String id = MapUtils.get(map, "id");
            // 参数校验
            if (StringUtils.isEmpty(id)) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            return WrapperResponse.success(centerCodeBO.getCodeDetailById(id));
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
        return WrapperResponse.success(centerCodeBO.getMaxSeqno(code));
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
            CenterCodeDTO centerCodeDTO = MapUtils.get(map, "centerCodeDTO");
            // 参数校验
            if (centerCodeDTO == null) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            if (StringUtils.isEmpty(centerCodeDTO.getCode())) {
                return WrapperResponse.error(400,"编码不能为空",null);
            }
            return WrapperResponse.success(centerCodeBO.saveCode(centerCodeDTO));
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
            CenterCodeDetailDTO centerCodeDetailDTO = MapUtils.get(map, "centerCodeDetailDTO");
            // 参数校验
            if (centerCodeDetailDTO == null) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            return WrapperResponse.success(centerCodeBO.saveCodeDetail(centerCodeDetailDTO));
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
            CenterCodeDTO centerCodeDTO = MapUtils.get(map, "centerCodeDTO");
            // 参数校验
            if (centerCodeDTO.getIds()==null && centerCodeDTO.getIds().size()<=0) {
                return WrapperResponse.error(400,"ids参数不能为空",null);
            }
            return WrapperResponse.success(centerCodeBO.deleteCodes(centerCodeDTO));
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
            CenterCodeDetailDTO centerCodeDetailDTO = MapUtils.get(map, "centerCodeDetailDTO");
            // 参数校验
            if (centerCodeDetailDTO.getIds()==null && centerCodeDetailDTO.getIds().size()<=0) {
                return WrapperResponse.error(400,"ids参数不能为空",null);
            }
            return WrapperResponse.success(centerCodeBO.deleteCodeDetails(centerCodeDetailDTO));
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
            return WrapperResponse.success(TreeUtils.buildByRecursive(centerCodeBO.getCodeTree(code),"-1"));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }
}
