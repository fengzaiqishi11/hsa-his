package cn.hsa.center.code.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.code.bo.CenterCodeBO;
import cn.hsa.module.center.code.dao.CenterCodeDao;
import cn.hsa.module.center.code.dto.CenterCodeDTO;
import cn.hsa.module.center.code.dto.CenterCodeDetailDTO;
import cn.hsa.module.center.code.dto.CenterCodeSelectDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
* @Package_name: cn.hsa.sys.code.bo.impl
* @class_name: SysCodeBOImpl
* @Description: 值域代码BO实现层
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/15 11:38
* @Company: 创智和宇
**/
@Component
@Slf4j
public class CenterCodeBOImpl extends HsafBO implements CenterCodeBO {

    /**
     * 值域代码数据库访问接口
     */
    @Resource
    private CenterCodeDao centerCodeDao;

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
    public Map<String, List<CenterCodeSelectDTO>> getByCode(String code) {
        List<String> codeList = new ArrayList<>();
        if(!StringUtils.isEmpty(code)){
            //Long数组转List<Long>集合
            codeList = Arrays.asList(code.split(","));
        }
        return getDetailByGroup(centerCodeDao.getByCode(codeList));
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
    public PageDTO queryCodePage(CenterCodeDTO centerCodeDTO) {
        if (!StringUtils.isEmpty(centerCodeDTO.getKeyword())) {
            centerCodeDTO.setKeyword(centerCodeDTO.getKeyword().toUpperCase());
        }
        // 设置分页信息
        PageHelper.startPage(centerCodeDTO.getPageNo(), centerCodeDTO.getPageSize());

        // 查询所有
        List<CenterCodeDTO> list = centerCodeDao.queryCodePage(centerCodeDTO);

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
    public PageDTO queryCodeDetailPage(CenterCodeDetailDTO centerCodeDetailDTO) {
        // 设置分页信息
        PageHelper.startPage(centerCodeDetailDTO.getPageNo(), centerCodeDetailDTO.getPageSize());

        // 查询所有
        List<CenterCodeDetailDTO> list = centerCodeDao.queryCodeDetailPage(centerCodeDetailDTO);

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
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.code.dto.CenterCodeDTO>
     **/
    @Override
    public CenterCodeDTO getCodeById(String id) {
        return centerCodeDao.getCodeById(id);
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
    public CenterCodeDetailDTO getCodeDetailById(String id) {
        return centerCodeDao.getCodeDetailById(id);
    }

    /**
    * @Menthod getMaxSeqno
    * @Desrciption 获取最大序号
    *
    * @Param
    * [code, hospCode]
    *
    * @Author jiahong.yang
    * @Date   2020/9/1 10:24
    * @Return int
    **/
    @Override
    public int getMaxSeqno(String code) {
      return centerCodeDao.getMaxSeqno(code);
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
    public Boolean saveCode(CenterCodeDTO centerCodeDTO) {
        if(StringUtils.isEmpty(centerCodeDTO.getId())){
            centerCodeDTO.setId("");
        }
        centerCodeDTO.setCode(centerCodeDTO.getCode().toUpperCase());
        //根据编码查询数据,校验编码不能重复
        if(centerCodeDao.getCodeCount(centerCodeDTO.getCode(),centerCodeDTO.getId()) > 0){
            throw new RuntimeException("编码已存在,code:"+centerCodeDTO.getCode());
        }

        //处理拼音码
        if(!StringUtils.isEmpty(centerCodeDTO.getName())){
            centerCodeDTO.setPym(PinYinUtils.toFirstPY(centerCodeDTO.getName()));
        }
        //处理五笔码
        if(!StringUtils.isEmpty(centerCodeDTO.getName())){
            centerCodeDTO.setWbm(WuBiUtils.getWBCode(centerCodeDTO.getName()));
        }

        if(StringUtils.isEmpty(centerCodeDTO.getId())){//新增
            centerCodeDTO.setId(SnowflakeUtils.getId());
            centerCodeDTO.setCrteTime(DateUtils.getNow());
            return centerCodeDao.insertCode(centerCodeDTO)>0;
        } else {//修改
            if(StringUtils.isEmpty(centerCodeDTO.getCode()) || StringUtils.isEmpty(centerCodeDTO.getOldCode())){
              throw new AppException("参数不能为空");
            }
            centerCodeDao.updateCodeDetailByCode(centerCodeDTO.getCode(),centerCodeDTO.getOldCode());
            return centerCodeDao.updateCode(centerCodeDTO)>0;
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
    public Boolean saveCodeDetail(CenterCodeDetailDTO centerCodeDetailDTO) {
        if(StringUtils.isEmpty(centerCodeDetailDTO.getId())){
            centerCodeDetailDTO.setId("");
        }
        //根据编码查询数据,校验值不能重复
        if(centerCodeDao.getCodeDetailCount(centerCodeDetailDTO.getCode(),centerCodeDetailDTO.getId(),centerCodeDetailDTO.getValue()) > 0){
            throw new RuntimeException("值域值已存在("+centerCodeDetailDTO.getValue()+")");
        }

        //处理拼音码
        if(!StringUtils.isEmpty(centerCodeDetailDTO.getName())){
            centerCodeDetailDTO.setPym(PinYinUtils.toFirstPY(centerCodeDetailDTO.getName()));
        }
        //处理五笔码
        if(!StringUtils.isEmpty(centerCodeDetailDTO.getName())){
            centerCodeDetailDTO.setWbm(WuBiUtils.getWBCode(centerCodeDetailDTO.getName()));
        }

        if(StringUtils.isEmpty(centerCodeDetailDTO.getId())){//新增
            centerCodeDetailDTO.setId(SnowflakeUtils.getId());
            centerCodeDetailDTO.setCrteTime(DateUtils.getNow());
            return centerCodeDao.insertCodeDetail(centerCodeDetailDTO)>0;
        } else {//修改
            return centerCodeDao.updateCodeDetail(centerCodeDetailDTO)>0;
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
    public Boolean deleteCodes(CenterCodeDTO centerCodeDTO) {
        return centerCodeDao.deleteCodes(centerCodeDTO)>0;
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
    public Boolean deleteCodeDetails(CenterCodeDetailDTO centerCodeDetailDTO) {
        return centerCodeDao.deleteCodeDetails(centerCodeDetailDTO)>0;
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
    public List<TreeMenuNode> getCodeTree(String code) {
        return centerCodeDao.getCodeTree(code);
    }

    /**
     * @Method: getDetailByGroup
     * @Description: 分类组装响应值
     * @Param: [codeList]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/25 11:12
     * @Return: java.util.Map<java.lang.String,java.util.List<cn.hsa.module.sys.code.dto.CenterCodeSelectDTO>>
     **/
    private Map<String, List<CenterCodeSelectDTO>> getDetailByGroup(List<CenterCodeSelectDTO> codeList) {
        Map<String, List<CenterCodeSelectDTO>> map = new HashMap<>();
        if(codeList!=null && codeList.size()>0){
            codeList.stream().forEach(dto->{
                if (!StringUtils.isEmpty(dto.getCCode()) && map.get(dto.getCCode())==null) {
                    map.put(dto.getCCode(), codeList.stream().filter(key -> key.getCCode().equals(dto.getCCode())).collect(Collectors.toList()));
                }
            });
        }
        return map;
    }
}
