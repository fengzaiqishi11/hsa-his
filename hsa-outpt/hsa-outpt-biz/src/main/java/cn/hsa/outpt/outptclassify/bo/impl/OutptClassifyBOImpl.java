package cn.hsa.outpt.outptclassify.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bspc.service.BaseSpecialCalcService;
import cn.hsa.module.outpt.outptclassify.bo.OutptClassifyBO;
import cn.hsa.module.outpt.outptclassify.dao.OutptClassifyDAO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyCostDTO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO;
import cn.hsa.module.outpt.outptclassifyclasses.bo.OutptClassifyClassesBO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Package_name: cn.hsa.outpt.outptclassify.bo.impl
 * @Class_name: OutptClassifyBOImpl
 * @Describe:  挂号类别业务接口实现层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/8/10 17:20
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class OutptClassifyBOImpl extends HsafBO implements OutptClassifyBO {

  @Resource
  private OutptClassifyDAO outptClassifyDAO;

  /**
   * 挂号排班接口
   */
  @Resource
  private OutptClassifyClassesBO outptClassifyClassesBO;
  /**
   * 特殊药品计费维护dubbo消费者接口
   */
  @Resource
  private BaseSpecialCalcService baseSpecialCalcService;


  /**
   * @Menthod getById()
   * @Desrciption 根据id获取挂号类别设置
   *
   * @Param
   * [1. outptClassifyDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/8/11 11:19
   * @Return cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO
   **/
  @Override
  public OutptClassifyDTO getById(OutptClassifyDTO outptClassifyDTO) {
    OutptClassifyDTO byId = outptClassifyDAO.getById(outptClassifyDTO);
    if(byId == null) {
      return null;
    }
    OutptClassifyCostDTO outptClassifyCostDTO = new OutptClassifyCostDTO();
    //给从表实体赋予挂号类别id
    outptClassifyCostDTO.setCyId(outptClassifyDTO.getId());
    //给从表实体赋予医院编码
    outptClassifyCostDTO.setHospCode(outptClassifyDTO.getHospCode());
    //根据医院编码和挂号类别id查出所有的挂号费用
    List<OutptClassifyCostDTO> outptClassifyCostDTOS = outptClassifyDAO.queryClassifyCostList(outptClassifyCostDTO);
    //算出总价格
    for (int i = 0; i < outptClassifyCostDTOS.size(); i++) {
      BigDecimal itemPrice = BigDecimalUtils.nullToZero(outptClassifyCostDTOS.get(i).getItemPrice());
      BigDecimal num = BigDecimalUtils.nullToZero(outptClassifyCostDTOS.get(i).getNum());
      BigDecimal multiply = BigDecimalUtils.multiply(itemPrice,num);
      outptClassifyCostDTOS.get(i).setItemPriceAll(multiply);
    }
    byId.setOutptClassifyCostDTOS(outptClassifyCostDTOS);
    return byId;
  }

  /**
   * @Menthod queryPage（）
   * @Desrciption 根据条件分页查询优惠配置信息
   *
   * @Param
   * [1. outptClassifyDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/8/11 11:52
   * @Return cn.hsa.base.PageDTO
   **/
  @Override
  public PageDTO queryPage(OutptClassifyDTO outptClassifyDTO) {
    PageHelper.startPage(outptClassifyDTO.getPageNo(),outptClassifyDTO.getPageSize());
    if(!StringUtils.isEmpty(outptClassifyDTO.getDeptCode())){
      Map map = new HashMap<>();
      map.put("deptCode",outptClassifyDTO.getDeptCode());
      map.put("hospCode",outptClassifyDTO.getHospCode());
      //获取该科室下面所有的子节点科室编码
      WrapperResponse<String> stringId = (WrapperResponse<String>) baseSpecialCalcService.getDeptTree(map);
      String data1 = stringId.getData();
      String[] split = data1.split(",");
      List<String> list = new ArrayList<>(Arrays.asList(split));
      outptClassifyDTO.setDeptCodes(list);
    }
    List<OutptClassifyDTO> outptClassifyDTOS= outptClassifyDAO.queryAllandPage(outptClassifyDTO);
    return PageDTO.of(outptClassifyDTOS);
  }

  /**
   * @Menthod queryAll（）
   * @Desrciption  查询全部挂号类别
   *
   * @Param
   * [1. outptClassifyDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/8/11 11:53
   * @Return java.util.List<cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO>
   **/
  @Override
  public List<OutptClassifyDTO> queryAll(OutptClassifyDTO outptClassifyDTO) {
    return outptClassifyDAO.queryAllandPage(outptClassifyDTO);
  }

  /**
   * @Menthod save()
   * @Desrciption 新增或者修改挂号列表的入口
   *
   * @Param
   * [1. outptClassifyDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/8/11 11:53
   * @Return java.lang.Boolean
   **/
  @Override
  public Boolean insert(OutptClassifyDTO outptClassifyDTO) {
    if (outptClassifyDAO.queryNameIsExist(outptClassifyDTO) > 0){
      throw new AppException("该挂号类别已存在");
    }
    //获取挂号费用列表
    List<OutptClassifyCostDTO> outptClassifyCostDTO =  outptClassifyDTO.getOutptClassifyCostDTOS();
    //挂号类别生成ID
    outptClassifyDTO.setId(SnowflakeUtils.getId());
    outptClassifyDTO.setPym(PinYinUtils.toFirstPY(outptClassifyDTO.getName()));
    outptClassifyDTO.setWbm(WuBiUtils.getWBCode(outptClassifyDTO.getName()));
    if(!ListUtils.isEmpty(outptClassifyCostDTO) && outptClassifyCostDTO.size() > 0){
      for (int i = 0; i < outptClassifyCostDTO.size(); i++) {
        //添加id
        outptClassifyCostDTO.get(i).setId(SnowflakeUtils.getId());
        //添加医院编码
        outptClassifyCostDTO.get(i).setHospCode(outptClassifyDTO.getHospCode());
        //添加挂号类别ID
        outptClassifyCostDTO.get(i).setCyId(outptClassifyDTO.getId());
      }
      //插入挂号类别费用
      outptClassifyDAO.insertOutptCost(outptClassifyCostDTO);
    }
    outptClassifyDTO.setCrteTime(DateUtils.getNow());
    //新增挂号类别
    outptClassifyDAO.insert(outptClassifyDTO);

    return true;
  }

  /**
   * @Menthod update()
   * @Desrciption 修改挂号类别
   *
   * @Param
   * [1. outptClassifyDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/8/11 14:57
   * @Return java.lang.Boolean
   **/
  public Boolean update(OutptClassifyDTO outptClassifyDTO) {
    if (outptClassifyDAO.queryNameIsExist(outptClassifyDTO) > 0){
      throw new AppException("该挂号类别已存在");
    }
    //获取挂号费用列表
    List<OutptClassifyCostDTO> outptClassifyCostDTO =  outptClassifyDTO.getOutptClassifyCostDTOS();

    //新建增加列表，用来存储编辑是新增的费用
    List<OutptClassifyCostDTO> outptCostAdd = new ArrayList<>();

    //新建修改列表，用来存储编辑是新增的费用
    List<OutptClassifyCostDTO> outptCostEdit = new ArrayList<>();

    outptClassifyDTO.setPym(PinYinUtils.toFirstPY(outptClassifyDTO.getName()));

    outptClassifyDTO.setWbm(WuBiUtils.getWBCode(outptClassifyDTO.getName()));

    //如果被移除的数据不为空，则执行删除
    if(!ListUtils.isEmpty(outptClassifyDTO.getIds()) && outptClassifyDTO.getIds().size() > 0){
      outptClassifyDAO.deleteOutptCost(outptClassifyDTO);
    }

    if(!ListUtils.isEmpty(outptClassifyCostDTO) && outptClassifyCostDTO.size() > 0){
      for (int i = 0; i < outptClassifyCostDTO.size(); i++) {
        if(StringUtils.isEmpty(outptClassifyCostDTO.get(i).getId())){
          //添加id
          outptClassifyCostDTO.get(i).setId(SnowflakeUtils.getId());
          //添加医院编码
          outptClassifyCostDTO.get(i).setHospCode(outptClassifyDTO.getHospCode());
          //添加挂号类别ID
          outptClassifyCostDTO.get(i).setCyId(outptClassifyDTO.getId());
          //加入新增列表
          outptCostAdd.add(outptClassifyCostDTO.get(i));
        }else{
          //加入修改列表
          outptCostEdit.add(outptClassifyCostDTO.get(i));
        }
      }
    }
    //修改挂号类别
    outptClassifyDAO.update(outptClassifyDTO);
    if(!ListUtils.isEmpty(outptCostAdd)){
      //新增挂号费用
      outptClassifyDAO.insertOutptCost(outptCostAdd);
    }
    if(!ListUtils.isEmpty(outptCostEdit)){
      //修改挂号费用
      outptClassifyDAO.updateOutptCost(outptCostEdit);
    }
    return true;
  }

  /**
   * @Menthod deleteById()
   * @Desrciption 根据id删除挂号别设置
   *
   * @Param
   * [1. outptClassifyDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/8/11 11:53
   * @Return java.lang.Boolean
   **/
  @Override
  public Boolean deleteById(OutptClassifyDTO outptClassifyDTO) {
    if(ListUtils.isEmpty(outptClassifyDTO.getIds())){
      throw new AppException("所选删除数据为空");
    }
    return outptClassifyDAO.deleteById(outptClassifyDTO) > 0;
  }

  @Override
  public List<OutptClassifyCostDTO> queryClassifyCostList(OutptClassifyCostDTO outptClassifyCostDTO) {
    return outptClassifyDAO.queryClassifyCostList(outptClassifyCostDTO);
  }
}
