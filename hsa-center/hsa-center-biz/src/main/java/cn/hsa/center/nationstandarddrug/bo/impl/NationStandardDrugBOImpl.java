package cn.hsa.center.nationstandarddrug.bo.impl;

import cn.hsa.base.PageDO;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.nationstandardItem.entity.NationStandardItemDO;
import cn.hsa.module.center.nationstandarddrug.bo.NationStandardDrugBO;
import cn.hsa.module.center.nationstandarddrug.dao.NationStandardDrugDAO;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import cn.hsa.module.center.nationstandarddrug.entity.NationStandardDrugDO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.center.nationstandarddrug.bo.impl
 * @Class_name: NationStandardDrugBOImpl
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/1/26 9:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class NationStandardDrugBOImpl extends HsafBO implements NationStandardDrugBO {

  @Resource
  private NationStandardDrugDAO nationStandardDrugDAO;

  /**
   * @Menthod queryNationStandardDrugPage
   * @Desrciption 分页查询所有国家标准药品
   *
   * @Param
   * [nationStandardDrugDTO]
   *
   * @Author jiahong.yang
   * @Date   2021/1/26 9:47
   * @Return java.util.List<cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO>
   **/
  @Override
  public PageDTO queryNationStandardDrugPage(NationStandardDrugDTO nationStandardDrugDTO) {
    List<NationStandardDrugDTO> nationStandardDrugDTOS = new ArrayList<>();
    PageHelper.startPage(nationStandardDrugDTO.getPageNo(), nationStandardDrugDTO.getPageSize());
    if(StringUtils.isEmpty(nationStandardDrugDTO.getFlag())){
      return null;
    } else if("1".equals(nationStandardDrugDTO.getFlag()) || "0".equals(nationStandardDrugDTO.getFlag())) {
      nationStandardDrugDTOS = nationStandardDrugDAO.queryNationStandardDrugPage(nationStandardDrugDTO);
    } else {
      nationStandardDrugDTOS = nationStandardDrugDAO.queryNationStandardZYPage(nationStandardDrugDTO);
    }
    return PageDTO.of(nationStandardDrugDTOS);
  }

  /**

   * @Describe: 根据id查询国家标准药品信息
   * @Author: luonianxin
   **/
  @Override
  public NationStandardDrugDTO getById(NationStandardDrugDTO nationStandardDrugDTO) {
    return nationStandardDrugDAO.getById(nationStandardDrugDTO);
  }

  @Override
  public Boolean saveProcessedUploadDataBatch(Map<String, Object> dataMap) {
    if(null == dataMap.get("crteName")
            ||null == dataMap.get("crteId")
            ||null == dataMap.get("resultList")){
      throw new AppException("存在必传参数为空,请检查");
    }
    String userName = (String) dataMap.get("crteName");
    String userId = (String) dataMap.get("crteId");
    List<List<String>> resultList = (List<List<String>>) dataMap.get("resultList");

    List<NationStandardDrugDO> insertList = new ArrayList<>();

    try {
      Date now = DateUtils.getNow();
      for (List<String> item : resultList){

        NationStandardDrugDO nationStandardDrugDO = new NationStandardDrugDO();
        if (StringUtils.isEmpty(item.get(0)) || StringUtils.isEmpty(item.get(1)) || StringUtils.isEmpty(item.get(2))
                || StringUtils.isEmpty(item.get(3)) || StringUtils.isEmpty(item.get(4)) || StringUtils.isEmpty(item.get(5))
                || StringUtils.isEmpty(item.get(8))|| StringUtils.isEmpty(item.get(9))
                || StringUtils.isEmpty(item.get(10))|| StringUtils.isEmpty(item.get(11))|| StringUtils.isEmpty(item.get(12))
                || StringUtils.isEmpty(item.get(13))|| StringUtils.isEmpty(item.get(15))|| StringUtils.isEmpty(item.get(16))){
          throw new AppException("存在必填字段为空，请检查！出错记录是："+item.toString());
        }
        // id
        nationStandardDrugDO.setId(SnowflakeUtils.getId());
        // 数据来源
        nationStandardDrugDO.setDataSources(item.get(0));
        // 注册名称
        nationStandardDrugDO.setRegisterName(item.get(1));
        // 药品编码
        nationStandardDrugDO.setCode(item.get(2));
        // 商品名
        nationStandardDrugDO.setGoodName(item.get(3));
        // 注册剂型
        nationStandardDrugDO.setRegisterPrep(item.get(4));
        // 实际剂型
        nationStandardDrugDO.setActualPrep(item.get(5));
        // 注册规格
        nationStandardDrugDO.setRegisterSpec(item.get(6));
        // 实际规格
        nationStandardDrugDO.setActualSpec(item.get(7));
        // 包装材质
        nationStandardDrugDO.setPackaging(item.get(8));
        // 拆分比
        nationStandardDrugDO.setSplitRatio(item.get(9));
        // 包装单位
        nationStandardDrugDO.setUnit(item.get(10));
        // 拆零单位
        nationStandardDrugDO.setSplitUnit(item.get(11));
        // 生产厂家
        nationStandardDrugDO.setProd(item.get(12));
        // 批准文号
        nationStandardDrugDO.setDan(item.get(13));
        // 分散包装企业
        nationStandardDrugDO.setPackagProd(item.get(14));
        // 市场状态
        nationStandardDrugDO.setMarketStatus(item.get(15));
        // 药品本位码
        nationStandardDrugDO.setInsure(item.get(16));
        // 甲乙类
        nationStandardDrugDO.setRatioAtOne(item.get(17));
        // 编号
        nationStandardDrugDO.setCodeNo(item.get(18));
        // 药品本位码
        nationStandardDrugDO.setInsuranceName(item.get(19));
        // 注册剂型
        nationStandardDrugDO.setInsurancePrep(item.get(20));
        // 备注
        nationStandardDrugDO.setRemark(item.get(21));
        // 是否有效
        nationStandardDrugDO.setIsValid(Constants.SF.S);
        // 创建者名字
        nationStandardDrugDO.setCrteName(userName);
        // 创建者id
        nationStandardDrugDO.setCrteId(userId);

        insertList.add(nationStandardDrugDO);
      }
      nationStandardDrugDAO.saveNationStandardDrugBatch(insertList);
      insertList.clear();
    }catch (IndexOutOfBoundsException | NullPointerException  e){
      throw new AppException("EXCEL数据格式错误，导入失败");
    }
    return true;
  }

  /**
   * 新增国家标准药品信息
   *
   * @param nationStandardDrugDO
   * @return 受影响的行数
   */
  @Override
  public Boolean saveNationStandardDrug(NationStandardDrugDO nationStandardDrugDO) {
    nationStandardDrugDO.setPym(PinYinUtils.toFullPY(nationStandardDrugDO.getGoodName()));
    nationStandardDrugDO.setWbm(WuBiUtils.getWBCode(nationStandardDrugDO.getGoodName()));

    return nationStandardDrugDAO.saveNationStandardDrug(nationStandardDrugDO) > 0;
  }

  /**
   * 更新国家标准药品信息
   *
   * @param nationStandardDrugDO
   * @return 受影响的行数
   */
  @Override
  public Boolean updateNationStandardDrug(NationStandardDrugDO nationStandardDrugDO) {
    nationStandardDrugDO.setPym(PinYinUtils.toFullPY(nationStandardDrugDO.getGoodName()));
    nationStandardDrugDO.setWbm(WuBiUtils.getWBCode(nationStandardDrugDO.getGoodName()));
    return nationStandardDrugDAO.updateNationStandardDrug(nationStandardDrugDO) > 0;
  }
}
