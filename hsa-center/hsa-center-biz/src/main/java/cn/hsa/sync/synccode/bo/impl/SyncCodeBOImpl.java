package cn.hsa.sync.synccode.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.sync.synccode.bo.SyncCodeBO;
import cn.hsa.module.sync.synccode.dao.SyncCodeDao;
import cn.hsa.module.sync.synccode.dto.SyncCodeDTO;
import cn.hsa.module.sync.synccode.dto.SyncCodeDetailDTO;
import cn.hsa.module.sync.synccode.dto.SyncCodeSelectDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
* @Package_name: cn.hsa.sys.synccode.bo.impl
* @class_name: SysCodeBOImpl
* @Description: 值域代码BO实现层
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/15 11:38
* @Company: 创智和宇
**/
@Component
@Slf4j
public class SyncCodeBOImpl extends HsafBO implements SyncCodeBO {

    /**
     * 值域代码数据库访问接口
     */
    @Resource
    private SyncCodeDao syncCodeDao;

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
    public Map<String, List<SyncCodeSelectDTO>> getByCode(String code) {
        List<String> codeList = new ArrayList<>();
        if(!StringUtils.isEmpty(code)){
            //Long数组转List<Long>集合
            codeList = Arrays.asList(code.split(","));
        }
        return getDetailByGroup(syncCodeDao.getByCode(codeList));
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
    public PageDTO queryCodePage(SyncCodeDTO syncCodeDTO) {
        if (!StringUtils.isEmpty(syncCodeDTO.getKeyword())) {
            syncCodeDTO.setKeyword(syncCodeDTO.getKeyword().toUpperCase());
        }

        // 设置分页信息
        PageHelper.startPage(syncCodeDTO.getPageNo(), syncCodeDTO.getPageSize());

        // 查询所有
        List<SyncCodeDTO> list = syncCodeDao.queryCodePage(syncCodeDTO);

        // 返回分页结果
        return PageDTO.of(list);
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
    public PageDTO queryCodeDetailPage(SyncCodeDetailDTO syncCodeDetailDTO) {
        // 设置分页信息
        PageHelper.startPage(syncCodeDetailDTO.getPageNo(), syncCodeDetailDTO.getPageSize());

        // 查询所有
        List<SyncCodeDetailDTO> list = syncCodeDao.queryCodeDetailPage(syncCodeDetailDTO);

        // 返回分页结果
        return PageDTO.of(list);
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
    public SyncCodeDTO getCodeById(String id) {
        return syncCodeDao.getCodeById(id);
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
    public SyncCodeDetailDTO getCodeDetailById(String id) {
        return syncCodeDao.getCodeDetailById(id);
    }

    /**
    * @Menthod getMaxSeqno
    * @Desrciption 获取最大序号
    *
    * @Param
    * [synccode, hospCode]
    *
    * @Author jiahong.yang
    * @Date   2020/9/1 10:24
    * @Return int
    **/
    @Override
    public int getMaxSeqno(String code) {
      return syncCodeDao.getMaxSeqno(code);
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
    public Boolean saveCode(SyncCodeDTO syncCodeDTO) {
        if(StringUtils.isEmpty(syncCodeDTO.getId())){
            syncCodeDTO.setId("");
        }
        syncCodeDTO.setCode(syncCodeDTO.getCode().toUpperCase());
        //根据编码查询数据,校验编码不能重复
        if(syncCodeDao.getCodeCount(syncCodeDTO.getCode(),syncCodeDTO.getId()) > 0){
            throw new RuntimeException("编码已存在,code:"+syncCodeDTO.getCode());
        }

        //处理拼音码
        if(!StringUtils.isEmpty(syncCodeDTO.getName())){
            syncCodeDTO.setPym(PinYinUtils.toFirstPY(syncCodeDTO.getName()));
        }
        //处理五笔码
        if(!StringUtils.isEmpty(syncCodeDTO.getName())){
            syncCodeDTO.setWbm(WuBiUtils.getWBCode(syncCodeDTO.getName()));
        }

        if(StringUtils.isEmpty(syncCodeDTO.getId())){//新增
            syncCodeDTO.setId(SnowflakeUtils.getId());
            syncCodeDTO.setCrteTime(DateUtils.getNow());
            return syncCodeDao.insertCode(syncCodeDTO)>0;
        } else {//修改
            if(StringUtils.isEmpty(syncCodeDTO.getCode()) || StringUtils.isEmpty(syncCodeDTO.getOldCode())){
              throw new AppException("参数不能为空");
            }
            syncCodeDao.updateCodeDetailByCode(syncCodeDTO.getCode(),syncCodeDTO.getOldCode());
            return syncCodeDao.updateCode(syncCodeDTO)>0;
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
    public Boolean saveCodeDetail(SyncCodeDetailDTO syncCodeDetailDTO) {
        if(StringUtils.isEmpty(syncCodeDetailDTO.getId())){
            syncCodeDetailDTO.setId("");
        }
        //根据编码查询数据,校验值不能重复
        if(syncCodeDao.getCodeDetailCount(syncCodeDetailDTO.getCode(),syncCodeDetailDTO.getId(),syncCodeDetailDTO.getValue()) > 0){
            throw new RuntimeException("值域值已存在("+syncCodeDetailDTO.getValue()+")");
        }

        //处理拼音码
        if(!StringUtils.isEmpty(syncCodeDetailDTO.getName())){
            syncCodeDetailDTO.setPym(PinYinUtils.toFirstPY(syncCodeDetailDTO.getName()));
        }
        //处理五笔码
        if(!StringUtils.isEmpty(syncCodeDetailDTO.getName())){
            syncCodeDetailDTO.setWbm(WuBiUtils.getWBCode(syncCodeDetailDTO.getName()));
        }

        if(StringUtils.isEmpty(syncCodeDetailDTO.getId())){//新增
            syncCodeDetailDTO.setId(SnowflakeUtils.getId());
            syncCodeDetailDTO.setCrteTime(DateUtils.getNow());
            return syncCodeDao.insertCodeDetail(syncCodeDetailDTO)>0;
        } else {//修改
            return syncCodeDao.updateCodeDetail(syncCodeDetailDTO)>0;
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
    public Boolean deleteCodes(SyncCodeDTO syncCodeDTO) {
        return syncCodeDao.deleteCodes(syncCodeDTO)>0;
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
    public Boolean deleteCodeDetails(SyncCodeDetailDTO syncCodeDetailDTO) {
        return syncCodeDao.deleteCodeDetails(syncCodeDetailDTO)>0;
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
    public List<TreeMenuNode> getCodeTree(String code) {
        return syncCodeDao.getCodeTree(code);
    }

    /**
     * @Method: getDetailByGroup
     * @Description: 分类组装响应值
     * @Param: [codeList]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/25 11:12
     * @Return: java.util.Map<java.lang.String,java.util.List<cn.hsa.module.sys.synccode.dto.SyncCodeSelectDTO>>
     **/
    private Map<String, List<SyncCodeSelectDTO>> getDetailByGroup(List<SyncCodeSelectDTO> codeList) {
        Map<String, List<SyncCodeSelectDTO>> map = new HashMap<>();
        List<SyncCodeSelectDTO> list = null;
        if(codeList!=null && codeList.size()>0){
            for(SyncCodeSelectDTO dto:codeList){
                map.put(dto.getCCode(), null);
            }
            for(String key:map.keySet()){
                list = new ArrayList<>();
                for(SyncCodeSelectDTO dto:codeList){
                    if(key.equals(dto.getCCode()) && !StringUtils.isEmpty(dto.getValue())){
                        list.add(dto);
                    }
                }
                map.put(key, list);
            }
        }
        return map;
    }
}
