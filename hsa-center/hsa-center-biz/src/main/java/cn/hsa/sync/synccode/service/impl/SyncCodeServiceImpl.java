package cn.hsa.sync.synccode.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.synccode.bo.SyncCodeBO;
import cn.hsa.module.sync.synccode.dto.SyncCodeDTO;
import cn.hsa.module.sync.synccode.dto.SyncCodeDetailDTO;
import cn.hsa.module.sync.synccode.dto.SyncCodeSelectDTO;
import cn.hsa.module.sync.synccode.service.SyncCodeService;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import cn.hsa.util.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.sys.synccode.bo.impl
* @class_name: SysCodeBOImpl
* @Description: 值域代码BO实现层
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/15 11:38
* @Company: 创智和宇
**/
@HsafRestPath("/service/sync/code")
@Slf4j
@Service("syncCodeService_provider")
public class SyncCodeServiceImpl extends HsafBO implements SyncCodeService {

    /**
     * 值域代码数据库访问接口
     */
    @Resource
    private SyncCodeBO syncCodeBO;

    /**
     * @Method: getCodes
     * @Description: 根据编码获取值域代码值
     * @Param: [synccode]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 11:25
     * @Return: java.util.List
     **/
    @Override
    public WrapperResponse<Map<String, List<SyncCodeSelectDTO>>> getByCode(Map map) {
        try {
            String code = MapUtils.get(map, "code");
            return WrapperResponse.success(syncCodeBO.getByCode(code));
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
            SyncCodeDTO syncCodeDTO = MapUtils.get(map, "syncCodeDTO");
            return WrapperResponse.success(syncCodeBO.queryCodePage(syncCodeDTO));
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
            SyncCodeDetailDTO syncCodeDetailDTO = MapUtils.get(map, "syncCodeDetailDTO");
            return WrapperResponse.success(syncCodeBO.queryCodeDetailPage(syncCodeDetailDTO));
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
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.synccode.dto.SyncCodeDTO>
     **/
    @Override
    public WrapperResponse<SyncCodeDTO> getCodeById(Map map) {
        try {
            String id = MapUtils.get(map, "id");
            // 参数校验
            if (StringUtils.isEmpty(id)) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            return WrapperResponse.success(syncCodeBO.getCodeById(id));
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
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.synccode.dto.SyncCodeDTO>
     **/
    @Override
    public WrapperResponse<SyncCodeDetailDTO> getCodeDetailById(Map map) {
        try {
            String id = MapUtils.get(map, "id");
            // 参数校验
            if (StringUtils.isEmpty(id)) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            return WrapperResponse.success(syncCodeBO.getCodeDetailById(id));
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
        return WrapperResponse.success(syncCodeBO.getMaxSeqno(code));
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
            SyncCodeDTO syncCodeDTO = MapUtils.get(map, "syncCodeDTO");
            // 参数校验
            if (syncCodeDTO == null) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            if (StringUtils.isEmpty(syncCodeDTO.getCode())) {
                return WrapperResponse.error(400,"编码不能为空",null);
            }
            return WrapperResponse.success(syncCodeBO.saveCode(syncCodeDTO));
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
            SyncCodeDetailDTO syncCodeDetailDTO = MapUtils.get(map, "syncCodeDetailDTO");
            // 参数校验
            if (syncCodeDetailDTO == null) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            return WrapperResponse.success(syncCodeBO.saveCodeDetail(syncCodeDetailDTO));
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
            SyncCodeDTO syncCodeDTO = MapUtils.get(map, "syncCodeDTO");
            // 参数校验
            if (syncCodeDTO.getIds()==null && syncCodeDTO.getIds().size()<=0) {
                return WrapperResponse.error(400,"ids参数不能为空",null);
            }
            return WrapperResponse.success(syncCodeBO.deleteCodes(syncCodeDTO));
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
            SyncCodeDetailDTO syncCodeDetailDTO = MapUtils.get(map, "syncCodeDetailDTO");
            // 参数校验
            if (syncCodeDetailDTO.getIds()==null && syncCodeDetailDTO.getIds().size()<=0) {
                return WrapperResponse.error(400,"ids参数不能为空",null);
            }
            return WrapperResponse.success(syncCodeBO.deleteCodeDetails(syncCodeDetailDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method: getCodeTree
     * @Description: 根据编码获取值域代码值树形结构
     * @Param: [synccode]
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
            return WrapperResponse.success(TreeUtils.buildByRecursive(syncCodeBO.getCodeTree(code),"-1"));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method getCodeData
     * @Desrciption 获取节点LIST
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/10/31 14:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
     **/
    @Override
    public WrapperResponse<List<TreeMenuNode>> getCodeData(Map map) {
        try {
            String code = MapUtils.get(map, "code");
            String hospCode = MapUtils.get(map, "hospCode");
            return WrapperResponse.success(syncCodeBO.getCodeTree(code));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }
}
