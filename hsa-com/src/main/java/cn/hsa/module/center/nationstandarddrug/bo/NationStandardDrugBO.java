package cn.hsa.module.center.nationstandarddrug.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;

import java.util.Map;


/**
 * @Package_name: cn.hsa.module.center.nationstandarddrug.bo
 * @Class_name: NationStandardDrugBO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/1/26 9:41
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface NationStandardDrugBO {
  /**
  * @Menthod queryNationStandardDrugPage
  * @Desrciption 分页查询所有国家标准药品
  *
  * @Param
  * [nationStandardDrugDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/1/26 9:41
  * @Return java.util.List<cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO>
  **/
  PageDTO queryNationStandardDrugPage(NationStandardDrugDTO nationStandardDrugDTO);


  /**
     * @Package_name: cn.hsa.module.center.nationstandarddrug.bo
     * @Class_name: NationStandardDrugBO
     * @Describe: 根据id查询国家标准药品信息
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/4/27 11:14
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
  **/
  NationStandardDrugDTO getById(NationStandardDrugDTO nationStandardDrugDTO);


  /**
   * @Description: 更新国家标准材料信息
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/5/7 9:23
   * @Param dataMap 上传数据包容器主要包含一下三个基础项：
   *                 key为crteName,value为用户名，
   *                 key为crteId，value为用户id，
   *                 key为resultList,value为List<List<String>> 的数据集合
   **/
  Boolean saveProcessedUploadDataBatch(Map<String,Object> dataMap);
}
