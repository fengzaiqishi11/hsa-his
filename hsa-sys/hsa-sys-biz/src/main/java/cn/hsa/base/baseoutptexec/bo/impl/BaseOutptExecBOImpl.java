package cn.hsa.base.baseoutptexec.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.baseoutptexec.bo.BaseOutptExecBO;
import cn.hsa.module.base.baseoutptexec.dao.BaseOutptExecDAO;
import cn.hsa.module.base.baseoutptexec.dto.BaseOutptExecDTO;
import cn.hsa.module.base.dept.dao.BaseDeptDAO;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.base.baseoutptexec.bo.impl
 * @Class_name: BaseOutptExecBOImpl
 * @Describe: 门诊执行科室配置信息维护业务逻辑实现层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/10/14 14:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class BaseOutptExecBOImpl extends HsafBO implements BaseOutptExecBO {

  /**
   * 门诊执行科室配置信息数据库访问接口
   */
  @Resource
  private BaseOutptExecDAO baseOutptExecDAO;

  @Resource
  private BaseDeptDAO baseDeptDAO;

  @Resource
  private SysCodeService sysCodeService_consumer;

  /**
  * @Menthod getById
  * @Desrciption  根据id查询门诊执行科室配置信息
  *
  * @Param
  * [baseOutptExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/10/14 14:49
  * @Return cn.hsa.module.base.baseoutptexec.dto.BaseOutptExecDTO
  **/
  @Override
  public BaseOutptExecDTO getById(BaseOutptExecDTO baseOutptExecDTO) {
    return baseOutptExecDAO.getById(baseOutptExecDTO);
  }

  /**
  * @Menthod queryPage
  * @Desrciption 根据条件分页查询门诊执行科室配置信息
  *
  * @Param
  * [baseOutptExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/10/14 14:49
  * @Return cn.hsa.base.PageDTO
  **/
  @Override
  public PageDTO queryPage(BaseOutptExecDTO baseOutptExecDTO) {
    // 设置分页信息
    PageHelper.startPage(baseOutptExecDTO.getPageNo(), baseOutptExecDTO.getPageSize());
    // 根据条件查询所
    List<BaseOutptExecDTO> baseOutptExecDTOS = baseOutptExecDAO.queryPageorAll(baseOutptExecDTO);
    // 根据科室列表id串转化为科室名称串
    for (int i = 0; i < baseOutptExecDTOS.size(); i++) {
      //把科室id列表转化为对应得名称列表
      baseOutptExecDTOS.get(i).setDeptNames(changeDeptNameById(baseOutptExecDTOS.get(i)));
      //把用法编码列表转化为对应得名称列表
      baseOutptExecDTOS.get(i).setUsageCodeNames(changeUsageCodeNameByCode(baseOutptExecDTOS.get(i)));
    }
    return PageDTO.of(baseOutptExecDTOS);
  }

  /**
  * @Menthod changeDeptNameById
  * @Desrciption 根据科室列表id串转化为科室名称串
  *
  * @Param
  * [baseOutptExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/10/15 14:50
  * @Return java.lang.String
  **/
  private String changeDeptNameById(BaseOutptExecDTO baseOutptExecDTO){
    String deptIds = baseOutptExecDTO.getDeptIds();
    String[] split = deptIds.split(",");
    List<String> deptIdList = Arrays.asList(split);
    if(!ListUtils.isEmpty(deptIdList)){
      BaseDeptDTO baseDeptDTO = new BaseDeptDTO();
      baseDeptDTO.setHospCode(baseOutptExecDTO.getHospCode());
      baseDeptDTO.setDeptIdList(deptIdList);
      List<BaseDeptDTO> baseDeptDTOS = baseDeptDAO.queryAll(baseDeptDTO);
      List<String> collect = baseDeptDTOS.stream().map(BaseDeptDTO::getName).collect(Collectors.toList());
      return Joiner.on(",").join(collect);
    } else {
      return "";
    }
  }

  /**
  * @Menthod changeUsageCodeNameByCode
  * @Desrciption 把用法编码列表转化为对应得名称列表
  *
  * @Param
  * [baseOutptExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/10/15 14:50
  * @Return java.lang.String
  **/
  private String changeUsageCodeNameByCode(BaseOutptExecDTO baseOutptExecDTO){
    String usageCodes = baseOutptExecDTO.getUsageCodes();
    String[] usageCode = usageCodes.split(",");
    List<String> usageCodeList = Arrays.asList(usageCode);
    SysCodeDetailDTO sysCodeDetailDTO = new SysCodeDetailDTO();
    sysCodeDetailDTO.setHospCode(baseOutptExecDTO.getHospCode());
    sysCodeDetailDTO.setCode("YF");
    sysCodeDetailDTO.setValueList(usageCodeList);
    Map map = new HashMap();
    map.put("hospCode",baseOutptExecDTO.getHospCode());
    map.put("sysCodeDetailDTO",sysCodeDetailDTO);
    WrapperResponse<List<SysCodeDetailDTO>> listWrapperResponse = sysCodeService_consumer.queryCodeDetailAll(map);
    List<String> collect = listWrapperResponse.getData().stream().map(SysCodeDetailDTO::getName).collect(Collectors.toList());
    return Joiner.on(",").join(collect);
  }
  /**
  * @Menthod queryAll
  * @Desrciption 根据条件查询门诊执行科室配置信息
  *
  * @Param
  * [baseOutptExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/10/14 14:49
  * @Return java.util.List<cn.hsa.module.base.baseoutptexec.dto.BaseOutptExecDTO>
  **/
  @Override
  public List<BaseOutptExecDTO> queryAll(BaseOutptExecDTO baseOutptExecDTO) {
    return baseOutptExecDAO.queryPageorAll(baseOutptExecDTO);
  }

  /**
  * @Menthod save
  * @Desrciption 新增/编辑门诊执行科室配置信息
  *
  * @Param
  * [baseOutptExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/10/14 14:49
  * @Return boolean
  **/
  @Override
  public boolean save(BaseOutptExecDTO baseOutptExecDTO) {
    isDeptRepeat(baseOutptExecDTO);
    if (StringUtils.isEmpty(baseOutptExecDTO.getId())) {
      baseOutptExecDTO.setId(SnowflakeUtils.getId());
      baseOutptExecDTO.setCrteTime(DateUtils.getNow());
      baseOutptExecDTO.setIsValid("1");
      return baseOutptExecDAO.insert(baseOutptExecDTO) > 0;
    } else {
      return baseOutptExecDAO.update(baseOutptExecDTO) > 0;
    }
  }

  /**
  * @Menthod updateStatus
  * @Desrciption 作废/启用门诊执行科室配置信息
  *
  * @Param
  * [baseOutptExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/10/14 14:50
  * @Return boolean
  **/
  @Override
  public boolean updateStatus(BaseOutptExecDTO baseOutptExecDTO) {
    if(Constants.SF.S.equals(baseOutptExecDTO.getIsValid())){
      baseOutptExecDTO.setId(baseOutptExecDTO.getIds().get(0));
      BaseOutptExecDTO byId = baseOutptExecDAO.getById(baseOutptExecDTO);
      isDeptRepeat(byId);
    }
    return baseOutptExecDAO.updateStatus(baseOutptExecDTO)>0;
  }

  /**
   * @Menthod getExecDept
   * @Desrciption 根据用法、开方科室获取执行科室
   *
   * @Param
   * [baseOutptExecDTODO]
   *
   * @Author zengfeng
   * @Date   2020/10/15 14:08
   * @Return cn.hsa.module.base.baseoutptexec.entity.BaseOutptExecDTO
   **/
  @Override
  public BaseOutptExecDTO getExecDept(BaseOutptExecDTO baseOutptExecDTO) {
    BaseOutptExecDTO baseOutptExec = baseOutptExecDAO.getExecDept(baseOutptExecDTO);
    if(baseOutptExec == null){
      baseOutptExec = new BaseOutptExecDTO();
      baseOutptExec.setExecDeptId(baseOutptExecDTO.getDeptIds());
    }
    return baseOutptExec;
  }

  /**
   * @Method isDeptRepeat
   * @Desrciption 是否存在同一个科室同一个用法重复的数据
   * @Param
   * [baseOutptExecDTO]
   * @Author liaojunjie
   * @Date   2021/1/19 15:17
   * @Return void
   **/
  public void isDeptRepeat(BaseOutptExecDTO baseOutptExecDTO){
    if(baseOutptExecDTO != null){
      BaseOutptExecDTO newBaseOutptExec = new BaseOutptExecDTO();
      newBaseOutptExec.setHospCode(baseOutptExecDTO.getHospCode());
      newBaseOutptExec.setId(baseOutptExecDTO.getId());
      List<String> depts = new ArrayList<>();
      List<String> usageCodes = new ArrayList<>();
      String deptIds = baseOutptExecDTO.getDeptIds();
      String usages = baseOutptExecDTO.getUsageCodes();
      if(deptIds.contains(",")){
        depts = Arrays.asList(deptIds.split(","));
      } else {
        depts.add(deptIds);
      }
      if(usages.contains(",")){
        usageCodes = Arrays.asList(usages.split(","));
      } else {
        usageCodes.add(usages);
      }
      for(String dept:depts){
        newBaseOutptExec.setDeptIds(dept);
        for (String usageCode : usageCodes){
          newBaseOutptExec.setUsageCodes(usageCode);
          List<BaseOutptExecDTO> execDeptS = baseOutptExecDAO.getExecDeptS(newBaseOutptExec);
          if(ListUtils.isEmpty(execDeptS)){
            continue;
          } else {
            throw new AppException("同一个科室，同一个用法不能重复");
          }
        }
      }
    }
  }
}
