package cn.hsa.module.center.nationstandarddrug.dao;

import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugZYDTO;
import cn.hsa.module.center.nationstandarddrug.entity.NationStandardDrugDO;
import cn.hsa.module.center.nationstandarddrug.entity.NationStandardDrugZYDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.nationstandarddrug.dao
 * @Class_name: NationStandardDrugDAO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/1/26 9:37
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface NationStandardDrugZYDAO {

  /**
     * @Package_name: cn.hsa.module.center.nationstandarddrug.dao
     * @Class_name: NationStandardDrugDAO
     * @Describe: 根据id查询国家标准药品信息
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/4/27 11:15
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
  **/
  NationStandardDrugZYDTO getZYById(NationStandardDrugZYDTO nationStandardDrugZYDTO);

  /**
  * @Menthod queryNationStandardDrugPage
  * @Desrciption 分页查询所有国家标准药品(西药中成药)
  *
  * @Param
  * [nationStandardDrugDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/1/26 9:38
  * @Return java.util.List<cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO>
  **/
  List<NationStandardDrugZYDTO> queryNationStandardDrugZYPage(NationStandardDrugZYDTO nationStandardDrugZYDTO);

  /**
   *  批量保存国家标准药品数据
   *
   * @param dataList 国家标准药品数据实体集合
   * @Author: luonianxin
   * @Date: 2021-05-08
   * @Return: Boolean 是否成功
   */
  Boolean saveNationStandardDrugZYBatch(@Param("list") List<NationStandardDrugZYDO> dataList);
}
