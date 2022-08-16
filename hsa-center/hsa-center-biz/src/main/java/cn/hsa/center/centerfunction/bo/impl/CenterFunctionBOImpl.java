package cn.hsa.center.centerfunction.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.centerfunction.bo.CenterFunctionBO;
import cn.hsa.module.center.centerfunction.dao.CenterFunctionDao;
import cn.hsa.module.center.centerfunction.dto.CenterFunctionDTO;
import cn.hsa.module.center.centerfunction.dto.CenterFunctionDetailDTO;
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
public class CenterFunctionBOImpl extends HsafBO implements CenterFunctionBO {

    /**
     * 值域代码数据库访问接口
     */
    @Resource
    private CenterFunctionDao centerFunctionDao;

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
    public Map<String, List<CenterFunctionDetailDTO>> getByCode(String code) {
        List<String> codeList = new ArrayList<>();
        if(!StringUtils.isEmpty(code)){
            //Long数组转List<Long>集合
            codeList = Arrays.asList(code.split(","));
        }
        return getDetailByGroup(centerFunctionDao.getByCode(codeList));
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
    public PageDTO queryCodePage(CenterFunctionDTO centerFunctionDTO) {
        if (!StringUtils.isEmpty(centerFunctionDTO.getKeyword())) {
            centerFunctionDTO.setKeyword(centerFunctionDTO.getKeyword().toUpperCase());
        }
        // 设置分页信息
        PageHelper.startPage(centerFunctionDTO.getPageNo(), centerFunctionDTO.getPageSize());

        // 查询所有
        List<CenterFunctionDTO> list = centerFunctionDao.queryCodePage(centerFunctionDTO);

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
    public PageDTO queryCodeDetailPage(CenterFunctionDetailDTO centerFunctionDetailDTO) {


        if(StringUtils.isEmpty(centerFunctionDetailDTO.getFunctionCode())){
            return null;
        }

        // 设置分页信息
        PageHelper.startPage(centerFunctionDetailDTO.getPageNo(), centerFunctionDetailDTO.getPageSize());

        // 查询所有
        List<CenterFunctionDetailDTO> list = centerFunctionDao.queryCodeDetailPage(centerFunctionDetailDTO);

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
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.code.dto.CenterFunctionDTO>
     **/
    @Override
    public CenterFunctionDTO getCodeById(String id) {
        return centerFunctionDao.getCodeById(id);
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
    public CenterFunctionDetailDTO getCodeDetailById(String id) {
        return centerFunctionDao.getCodeDetailById(id);
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
      return centerFunctionDao.getMaxSeqno(code);
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
    public Boolean saveCode(CenterFunctionDTO centerFunctionDTO) {
        if(StringUtils.isEmpty(centerFunctionDTO.getId())){
            centerFunctionDTO.setId("");
        }
        centerFunctionDTO.setCode(centerFunctionDTO.getCode().toUpperCase());
        //根据编码查询数据,校验编码不能重复
        if(centerFunctionDao.getCodeCount(centerFunctionDTO.getCode(),centerFunctionDTO.getId()) > 0){
            throw new RuntimeException("编码已存在,code:"+centerFunctionDTO.getCode());
        }

        //处理拼音码
        if(!StringUtils.isEmpty(centerFunctionDTO.getName())){
            centerFunctionDTO.setPym(PinYinUtils.toFirstPY(centerFunctionDTO.getName()));
        }
        //处理五笔码
        if(!StringUtils.isEmpty(centerFunctionDTO.getName())){
            centerFunctionDTO.setWbm(WuBiUtils.getWBCode(centerFunctionDTO.getName()));
        }

        if(StringUtils.isEmpty(centerFunctionDTO.getId())){//新增
            centerFunctionDTO.setId(SnowflakeUtils.getId());
            centerFunctionDTO.setCrteTime(DateUtils.getNow());
            return centerFunctionDao.insertCode(centerFunctionDTO)>0;
        } else {//修改
            if(StringUtils.isEmpty(centerFunctionDTO.getCode()) || StringUtils.isEmpty(centerFunctionDTO.getOldCode())){
              throw new AppException("参数不能为空");
            }
            centerFunctionDao.updateCodeDetailByCode(centerFunctionDTO.getCode(),centerFunctionDTO.getOldCode());
            return centerFunctionDao.updateCode(centerFunctionDTO)>0;
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
    public Boolean saveCodeDetail(CenterFunctionDetailDTO centerFunctionDetailDTO) {
        if(StringUtils.isEmpty(centerFunctionDetailDTO.getId())){
            centerFunctionDetailDTO.setId("");
        }
        //根据编码查询数据,校验值不能重复
        if(centerFunctionDao.getCodeDetailCount(centerFunctionDetailDTO.getFunctionCode(),centerFunctionDetailDTO.getId(),centerFunctionDetailDTO.getValue()) > 0){
            throw new RuntimeException("值域值已存在("+centerFunctionDetailDTO.getValue()+")");
        }

        //处理拼音码
        if(!StringUtils.isEmpty(centerFunctionDetailDTO.getName())){
            centerFunctionDetailDTO.setPym(PinYinUtils.toFirstPY(centerFunctionDetailDTO.getName()));
        }
        //处理五笔码
        if(!StringUtils.isEmpty(centerFunctionDetailDTO.getName())){
            centerFunctionDetailDTO.setWbm(WuBiUtils.getWBCode(centerFunctionDetailDTO.getName()));
        }

        if(StringUtils.isEmpty(centerFunctionDetailDTO.getId())){
            //新增
            centerFunctionDetailDTO.setId(SnowflakeUtils.getId());
            centerFunctionDetailDTO.setCrteTime(DateUtils.getNow());
            return centerFunctionDao.insertCodeDetail(centerFunctionDetailDTO)>0;
        } else {//修改
            return centerFunctionDao.updateCodeDetail(centerFunctionDetailDTO)>0;
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
    public Boolean deleteCodes(CenterFunctionDTO centerFunctionDTO) {
        return centerFunctionDao.deleteCodes(centerFunctionDTO)>0;
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
    public Boolean deleteCodeDetails(CenterFunctionDetailDTO centerFunctionDetailDTO) {
        return centerFunctionDao.deleteCodeDetails(centerFunctionDetailDTO)>0;
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
        return centerFunctionDao.getCodeTree(code);
    }

    /**
     * @Method: getDetailByGroup
     * @Description: 分类组装响应值
     * @Param: [codeList]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/25 11:12
     * @Return: java.util.Map<java.lang.String,java.util.List<cn.hsa.module.sys.code.dto.CenterFunctionSelectDTO>>
     **/
    private Map<String, List<CenterFunctionDetailDTO>> getDetailByGroup(List<CenterFunctionDetailDTO> codeList) {
        Map<String, List<CenterFunctionDetailDTO>> map = new HashMap<>();
        if(codeList!=null && codeList.size()>0){
            codeList.stream().forEach(dto->{
                if (!StringUtils.isEmpty(dto.getFunctionCode()) && map.get(dto.getFunctionCode())==null) {
                    map.put(dto.getFunctionCode(), codeList.stream().filter(key -> key.getFunctionCode().equals(dto.getFunctionCode())).collect(Collectors.toList()));
                }
            });
        }
        return map;
    }
}
