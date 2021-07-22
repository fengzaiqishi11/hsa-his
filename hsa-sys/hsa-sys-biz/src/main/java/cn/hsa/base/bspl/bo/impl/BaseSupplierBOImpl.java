package cn.hsa.base.bspl.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.bspl.bo.BaseSupplierBO;
import cn.hsa.module.base.bspl.dao.BaseSupplierDAO;
import cn.hsa.module.base.bspl.dto.BaseSupplierDTO;
import cn.hsa.module.sys.code.dto.SysCodeSelectDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.base.data.bo.impl
 * @Class_name: BaseSupplierBOImpl
 * @Describe: 供应商业务逻辑实现层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/7 16:56
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class BaseSupplierBOImpl extends HsafBO implements BaseSupplierBO {

  /**
   * 供应商数据库访问接口
   */
  @Resource
  private BaseSupplierDAO baseSupplierDao;

  /**
   * 注入码表service层
   */
  @Resource
  private SysCodeService sysCodeService;

  /**
   * 注入单据规则service层
   */
  @Resource
  private BaseOrderRuleService baseOrderRuleService;
  /**
   * @Menthod getById()
   * @Desrciption  通过主键id查询供应商信息
   *
   * @Param
   * 1. map
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 15:45
   * @Return cn.hsa.module.base.data.dao.BaseSupplierDao
   **/
  @Override
  public BaseSupplierDTO getById(Map<String, Object> map) {
    return this.baseSupplierDao.getById(map);
  }

  /**
   * @Menthod queryPage()
   * @Desrciption 根据条件查询供应商信息
   *
   * @Param
   * 1. baseSupplierDTO  供应商数据传输对象
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 17:02
   * @Return cn.hsa.base.PageDTO
   **/
  @Override
  public PageDTO queryPage(BaseSupplierDTO baseSupplierDTO) {
    // 设置分页信息
    PageHelper.startPage(baseSupplierDTO.getPageNo(), baseSupplierDTO.getPageSize());
    // 根据条件查询所
    List<BaseSupplierDTO> baseSupplierDTOS = baseSupplierDao.queryPage(baseSupplierDTO);

    return PageDTO.of(baseSupplierDTOS);
  }

  /**
  * @Menthod queryAll()
  * @Desrciption  查询所有供应商信息
  *
  * @Param
  * [baseSupplierDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/18 15:31
  * @Return java.util.List<cn.hsa.module.base.bspl.dto.BaseSupplierDTO>
  **/
  @Override
  public List<BaseSupplierDTO> queryAll(BaseSupplierDTO baseSupplierDTO) {
    List<BaseSupplierDTO> baseSupplierDTOS = baseSupplierDao.queryAll(baseSupplierDTO);
    return baseSupplierDTOS;
  }


  /**
   * @Menthod insert()
   * @Desrciption 新增供应商
   *
   * @Param
   * 1. baseSupplierDTO  供应商数据传输对象
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 15:53
   * @Return int
   **/
  @Override
  public boolean insert(BaseSupplierDTO baseSupplierDTO) {
    //判断编码是否重复
    BaseSupplierDTO baseSupplierDTO1 = new BaseSupplierDTO();
    baseSupplierDTO1.setCode(baseSupplierDTO.getCode());
    baseSupplierDTO1.setHospCode(baseSupplierDTO.getHospCode());

    if(StringUtils.isEmpty(baseSupplierDTO.getName())){
      throw new AppException("供应商名称为空");
    }else{
      String namePym = PinYinUtils.toFirstPY(baseSupplierDTO.getName());
      baseSupplierDTO.setNamePym(namePym);
      String nameWbm = WuBiUtils.getWBCode(baseSupplierDTO.getName());
      baseSupplierDTO.setNameWbm(nameWbm);
    }

    //获取编码
    HashMap map = new HashMap();
    map.put("hospCode",baseSupplierDTO.getHospCode());
    if("0".equals(baseSupplierDTO.getCompanyType())){
      map.put("typeCode","26");
    } else {
      map.put("typeCode","28");
    }
    WrapperResponse<String> orderNo = baseOrderRuleService.getOrderNo(map);
    baseSupplierDTO.setCode(orderNo.getData());

    if(!StringUtils.isEmpty(baseSupplierDTO.getAbbr())){
      String abbrPym = PinYinUtils.toFirstPY(baseSupplierDTO.getAbbr());
      String abbrWbm = WuBiUtils.getWBCode(baseSupplierDTO.getAbbr());
      baseSupplierDTO.setAbbrPym(abbrPym);
      baseSupplierDTO.setAbbrWbm(abbrWbm);
    }
    baseSupplierDTO.setCrteTime(DateUtils.getNow());
    return baseSupplierDao.insert(baseSupplierDTO) > 0;
  }


  /**
   * @Menthod updateBsplTypeStatusSuppelier()
   * @Desrciption 启用禁用供应商
   *
   * @Param
   * 1. map
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 15:57
   * @Return int
   **/
  @Override
  public boolean updateStatus(BaseSupplierDTO baseSupplierDTO) {
    if(ListUtils.isEmpty(baseSupplierDTO.getIds())){
      throw new AppException("所选删除数据为空");
    }
    return baseSupplierDao.updateStatus(baseSupplierDTO) > 0;
  }


  /**
   * @Menthod update()
   * @Desrciption 修改供应商信息
   *
   * @Param
   * 1. baseSupplierDTO  供应商数据传输对象
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 15:58
   * @Return int
   **/
  @Override
  public boolean update(BaseSupplierDTO baseSupplierDTO) {
    if(StringUtils.isEmpty(baseSupplierDTO.getName())){
      throw new AppException("供应商名称为空");
    }else{
      String namePym = PinYinUtils.toFirstPY(baseSupplierDTO.getName());
      baseSupplierDTO.setNamePym(namePym);
      String nameWbm = WuBiUtils.getWBCode(baseSupplierDTO.getName());
      baseSupplierDTO.setNameWbm(nameWbm);
    }

    if(!StringUtils.isEmpty(baseSupplierDTO.getAbbr())){
      String abbrPym = PinYinUtils.toFirstPY(baseSupplierDTO.getAbbr());
      String abbrWbm = WuBiUtils.getWBCode(baseSupplierDTO.getAbbr());
      baseSupplierDTO.setAbbrPym(abbrPym);
      baseSupplierDTO.setAbbrWbm(abbrWbm);
    }
    return baseSupplierDao.update(baseSupplierDTO) > 0;
  }

  /**
   * @Menthod insertUpLoad
   * @Desrciption  供应商导入功能
   * @param map
   * @Author xingyu.xie
   * @Date   2021/1/9 13:05
   * @Return boolean
   **/
  @Override
  public boolean insertUpLoad(Map map){

    String hospCode = (String) map.get("hospCode");
    String userName = (String) map.get("crteName");
    String userId = (String) map.get("crteId");
    Date nowDate = DateUtils.getNow();

    List<List<String>> resultList = (List<List<String>>) map.get("resultList");

    List<BaseSupplierDTO> insertList = new ArrayList<>();

    // 拿取系统码表列表
    HashMap sysCodeMap = new HashMap();
    sysCodeMap.put("hospCode",hospCode);
    sysCodeMap.put("code","SF");
    WrapperResponse<Map<String, List<SysCodeSelectDTO>>> byCode = sysCodeService.getByCode(sysCodeMap);

    Map<String, List<SysCodeSelectDTO>> data = byCode.getData();

    List<SysCodeSelectDTO> SF = data.get("SF");

    Map<String, String> sfMap = SF.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue,(v1,v2)->v2));

    try {
      for (List<String> item : resultList){

//                // 生成供应商编码
//                HashMap ruleMap = new HashMap();
//                ruleMap.put("hospCode",hospCode);
//                ruleMap.put("typeCode","22");
//                WrapperResponse<String> orderNo = baseOrderRuleService.getOrderNo(ruleMap);
//                String code = orderNo.getData();

        BaseSupplierDTO baseSupplierDTO = new BaseSupplierDTO();

        if (StringUtils.isEmpty(item.get(0)) || StringUtils.isEmpty(item.get(2)) || StringUtils.isEmpty(item.get(3)) ){
          throw new AppException("存在必填字段为空，请检查！");
        }

        baseSupplierDTO.setId(SnowflakeUtils.getId());
        // 医院编码
        baseSupplierDTO.setHospCode(hospCode);
        // 供应商编码
        baseSupplierDTO.setCode(item.get(0));
        // 供应商名简称
        baseSupplierDTO.setAbbr(item.get(1));
        // 供应商名
        baseSupplierDTO.setName(item.get(2));
        // 供应商是否有效
        baseSupplierDTO.setIsValid(sfMap.get(item.get(3)));
        // 供应商联系人
        baseSupplierDTO.setContact(item.get(4));
        // 供应商电话
        baseSupplierDTO.setPhone(item.get(5));
        // 供应商传真
        baseSupplierDTO.setFax(item.get(6));
        // 供应商邮编
        baseSupplierDTO.setPostCode(item.get(7));
        // 供应商电子邮箱
        baseSupplierDTO.setEmail(item.get(8));
        // 供应商开户银行
        baseSupplierDTO.setBank(item.get(9));
        // 供应商开户账号
        baseSupplierDTO.setAccount(item.get(10));
        // 供应商纳税号
        baseSupplierDTO.setTaxNum(item.get(11));
        // 供应商地址
        baseSupplierDTO.setAddress(item.get(12));
        // 供应商备注
        baseSupplierDTO.setRemark(item.get(13));

        // 有简称拼音码和五笔码就直接塞入，无就根据简称自动生成
        if (StringUtils.isNotEmpty(item.get(14))){
          baseSupplierDTO.setAbbrPym(item.get(14));
        }else if (StringUtils.isNotEmpty(item.get(1))){
          baseSupplierDTO.setAbbrPym(PinYinUtils.toFirstPY(item.get(1)));
        }

        if (StringUtils.isNotEmpty(item.get(15))){
          baseSupplierDTO.setAbbrWbm(item.get(15));
        }else if (StringUtils.isNotEmpty(item.get(1))){
          baseSupplierDTO.setAbbrWbm(WuBiUtils.getWBCode(item.get(1)));
        }

        // 有全称拼音码和五笔码就直接塞入，无就根据全称自动生成
        if (StringUtils.isNotEmpty(item.get(16))){
          baseSupplierDTO.setAbbrPym(item.get(16));
        }else if (StringUtils.isNotEmpty(item.get(2))){
          baseSupplierDTO.setAbbrPym(PinYinUtils.toFirstPY(item.get(2)));
        }

        if (StringUtils.isNotEmpty(item.get(17))){
          baseSupplierDTO.setAbbrWbm(item.get(17));
        }else if (StringUtils.isNotEmpty(item.get(2))){
          baseSupplierDTO.setAbbrWbm(WuBiUtils.getWBCode(item.get(2)));
        }
        // 创建信息
        baseSupplierDTO.setCrteTime(nowDate);
        baseSupplierDTO.setCrteName(userName);
        baseSupplierDTO.setCrteId(userId);
        baseSupplierDTO.setCompanyType("0");
        insertList.add(baseSupplierDTO);
      }
    }catch (IndexOutOfBoundsException | NullPointerException e ){
      throw new AppException("EXCEL数据格式错误，导入失败");
    }

    return this.baseSupplierDao.insertList(insertList)>0;

  }



}
