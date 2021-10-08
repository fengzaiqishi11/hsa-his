package cn.hsa.sys.code.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sys.code.bo.SysCodeBO;
import cn.hsa.module.sys.code.dto.SysCodeDTO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.code.dto.SysCodeSelectDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
* @Package_name: cn.hsa.sys.code.bo.impl
* @class_name: SysCodeBOImpl
* @Description: 值域代码BO实现层
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/15 11:38
* @Company: 创智和宇
**/
@HsafRestPath("/service/sys/code")
@Slf4j
@Service("sysCodeService_provider")
public class SysCodeServiceImpl extends HsafBO implements SysCodeService {

    /**
     * 值域代码数据库访问接口
     */
    @Resource
    private SysCodeBO sysCodeBO;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
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
    public WrapperResponse<Map<String, List<SysCodeSelectDTO>>> getByCode(Map map) {
        try {
            String code = MapUtils.get(map, "code");
            String hospCode = MapUtils.get(map, "hospCode");
            return WrapperResponse.success(sysCodeBO.getByCode(code,hospCode));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * 根据编码获取值域代码值优先从缓存获取
     *
     * @param map
     * @Method: getCodeDetailsByCodeCache
     * @Description:
     * @Param: [code]
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2020/7/15 11:25
     * @Return: java.util.List
     */
    @Override
    @SuppressWarnings("unchecked")
    public WrapperResponse<Map<String, List<SysCodeSelectDTO>>> getCodeDetailsByCodeCache(Map map) {

        String code = MapUtils.get(map, "code");
        String hospCode = MapUtils.get(map, "hospCode");
        String codeCacheRedisKey = hospCode+"_"+Constants.REDISKEY.CODEDETAIL;
        List<String> codeList = null;
        if(!StringUtils.isEmpty(code)){
            codeList = Arrays.asList(code.split(","));
        }
        if(redisUtils.hasKey(codeCacheRedisKey)){
            Map allCodeDetails = redisUtils.hmget(codeCacheRedisKey);
            if(!ListUtils.isEmpty(codeList)){
                List<String> finalCodeList = codeList;
                // 移除查询的所有缓存中未查询
                allCodeDetails.keySet().removeIf(key -> !(finalCodeList.contains(key)));
            }
            return WrapperResponse.success(allCodeDetails);
        }else{
            Map codeDetailMap = sysCodeBO.getByCode("",hospCode);
            redisUtils.hmset(codeCacheRedisKey,codeDetailMap);
            if(!ListUtils.isEmpty(codeList)){
                List<String> finalCodeList = codeList;
                // 移除查询的所有缓存中未查询的数据
                codeDetailMap.keySet().removeIf(key -> !(finalCodeList.contains(key)));
            }
            return WrapperResponse.success(codeDetailMap);
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
            SysCodeDTO sysCodeDTO = MapUtils.get(map, "sysCodeDTO");
            return WrapperResponse.success(sysCodeBO.queryCodePage(sysCodeDTO));
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
            SysCodeDetailDTO sysCodeDetailDTO = MapUtils.get(map, "sysCodeDetailDTO");
            return WrapperResponse.success(sysCodeBO.queryCodeDetailPage(sysCodeDetailDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
    * @Menthod queryCodeDetailAll
    * @Desrciption 获取值域代码值全部列表
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/10/15 14:22
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<List<SysCodeDetailDTO>> queryCodeDetailAll(Map map) {
      try {
        SysCodeDetailDTO sysCodeDetailDTO = MapUtils.get(map, "sysCodeDetailDTO");
        return WrapperResponse.success(sysCodeBO.queryCodeDetailAll(sysCodeDetailDTO));
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
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.code.dto.SysCodeDTO>
     **/
    @Override
    public WrapperResponse<SysCodeDTO> getCodeById(Map map) {
        try {
            String id = MapUtils.get(map, "id");
            String hospCode = MapUtils.get(map, "hospCode");
            // 参数校验
            if (StringUtils.isEmpty(id)) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            return WrapperResponse.success(sysCodeBO.getCodeById(id,hospCode));
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
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.code.dto.SysCodeDTO>
     **/
    @Override
    public WrapperResponse<SysCodeDetailDTO> getCodeDetailById(Map map) {
        try {
            String id = MapUtils.get(map, "id");
            String hospCode = MapUtils.get(map, "hospCode");
            // 参数校验
            if (StringUtils.isEmpty(id)) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            return WrapperResponse.success(sysCodeBO.getCodeDetailById(id,hospCode));
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
        String hospCode = MapUtils.get(map, "hospCode");
        String code = MapUtils.get(map, "code");
        return WrapperResponse.success(sysCodeBO.getMaxSeqno(code,hospCode));
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
            SysCodeDTO sysCodeDTO = MapUtils.get(map, "sysCodeDTO");
            // 参数校验
            if (sysCodeDTO == null) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            if (StringUtils.isEmpty(sysCodeDTO.getCode())) {
                return WrapperResponse.error(400,"编码不能为空",null);
            }
            // add by zhangguorui 新增完成之后，调用getCodeDetailsByCodeCache先清除redis，再添加
            Boolean aBoolean = sysCodeBO.saveCode(sysCodeDTO);
            if (aBoolean){
                Map codeMap = new HashMap();
                codeMap.put("hospCode",sysCodeDTO.getHospCode());
                getCodeDetailsByCodeCache(codeMap);
                return WrapperResponse.success(true);
            }else{
                return WrapperResponse.success(false);
            }

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
            SysCodeDetailDTO sysCodeDetailDTO = MapUtils.get(map, "sysCodeDetailDTO");
            // 参数校验
            if (sysCodeDetailDTO == null) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            // add by zhangguorui 新增完成之后，调用getCodeDetailsByCodeCache先清除redis，再添加
            Boolean aBoolean = sysCodeBO.saveCodeDetail(sysCodeDetailDTO);
            if (aBoolean){
                Map codeMap = new HashMap();
                codeMap.put("hospCode",sysCodeDetailDTO.getHospCode());
                getCodeDetailsByCodeCache(codeMap);
                return WrapperResponse.success(true);
            } else {
                return WrapperResponse.success(false);
            }

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
            SysCodeDTO sysCodeDTO = MapUtils.get(map, "sysCodeDTO");
            // 参数校验
            if (sysCodeDTO.getIds()==null && sysCodeDTO.getIds().size()<=0) {
                return WrapperResponse.error(400,"ids参数不能为空",null);
            }
            // add by zhangguorui 删除完成之后，调用getCodeDetailsByCodeCache清除redis
            Boolean aBoolean = sysCodeBO.deleteCodes(sysCodeDTO);
            if (aBoolean){
                Map codeMap = new HashMap();
                codeMap.put("hospCode",sysCodeDTO.getHospCode());
                getCodeDetailsByCodeCache(codeMap);
                return WrapperResponse.success(true);
            } else {
                return WrapperResponse.success(false);
            }
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
            SysCodeDetailDTO sysCodeDetailDTO = MapUtils.get(map, "sysCodeDetailDTO");
            // 参数校验
            if (sysCodeDetailDTO.getIds()==null && sysCodeDetailDTO.getIds().size()<=0) {
                return WrapperResponse.error(400,"ids参数不能为空",null);
            }
            // add by zhangguorui 删除完成之后，调用getCodeDetailsByCodeCache清除redis
            Boolean aBoolean = sysCodeBO.deleteCodeDetails(sysCodeDetailDTO);
            if (aBoolean){
                Map codeMap = new HashMap();
                codeMap.put("hospCode",sysCodeDetailDTO.getHospCode());
                getCodeDetailsByCodeCache(codeMap);
                return WrapperResponse.success(true);
            } else {
                return WrapperResponse.success(false);
            }
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
            String hospCode = MapUtils.get(map, "hospCode");
            return WrapperResponse.success(TreeUtils.buildByRecursive(sysCodeBO.getCodeTree(code,hospCode),"-1"));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }
    /**
     * @Method queryNationCode()
     * @Desrciption  维护科室信息的国家编码
     * @Param hospCode医院编码 nationCode 国家编码值
     *
     * @Author fuhui
     * @Date   2020/10/30 0:32
     * @Return TreeMenuNode树集合
     **/
    @Override
    public List<TreeMenuNode> queryNationCode(Map map) {
        SysCodeDetailDTO sysCodeDetailDTO = MapUtils.get(map, "sysCodeDetailDTO");
        return sysCodeBO.queryNationCode(sysCodeDetailDTO);
    }

    /**
     * @Menthod getInsureDict
     * @Desrciption 获取医保字典信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/18 11:39
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse getInsureDict(Map<String, String> param) {
        //TODO 后续做医保码表翻译
        return null;
    }

    /**
     * @Menthod: getCodeDetailByValue
     * @Desrciption: 根据值域代码值查询出单个具体的值域代码值对象
     * @Param: sysCodeDetailDTO[c_code, value] c_code值域代码 value代码值
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-17 16:10
     * @Return: SysCodeDetailDTO
     **/
    @Override
    public WrapperResponse<SysCodeDetailDTO> getCodeDetailByValue(Map map) {
        return WrapperResponse.success(sysCodeBO.getCodeDetailByValue(MapUtils.get(map, "sysCodeDetailDTO")));
    }


    /**
     * @Method getCodeData
     * @Desrciption 获取节点LIST
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/10/29 20:05
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
     **/
    @Override
    public WrapperResponse<List<TreeMenuNode>> getCodeData(Map map) {
        try {
            String code = MapUtils.get(map, "code");
            String hospCode = MapUtils.get(map, "hospCode");
            return WrapperResponse.success(sysCodeBO.getCodeTree(code,hospCode));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method queryFathers
     * @Desrciption 根据子代查询祖宗的编码(返回值包括子节点的值)
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/11/25 10:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.lang.String>>
     **/
    @Override
    public WrapperResponse<List<String>> queryFathers(Map map) {
        return WrapperResponse.success(sysCodeBO.queryFathers(map));
    }
}
