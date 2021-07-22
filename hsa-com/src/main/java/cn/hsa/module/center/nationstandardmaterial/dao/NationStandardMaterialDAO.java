package cn.hsa.module.center.nationstandardmaterial.dao;

import cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDO;
import cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 表名含义：
 * base：基础模块
 * material：国家标准材料
 * <p>
 * 表说明：
 * (NationStandardMaterialDTO)表数据库访问层
 *
 * @author xingyu.xie
 * @since 2021-01-26 09:18:34
 */
public interface NationStandardMaterialDAO {


    /**
    * @Menthod getById
    * @Desrciption  
     * @param nationStandardMaterial
    * @Author xingyu.xie
    * @Date   2021/1/26 9:45 
    * @Return cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO
    **/
    NationStandardMaterialDTO getById(NationStandardMaterialDTO nationStandardMaterial);

    /**
    * @Menthod queryAll
    * @Desrciption  
     * @param nationStandardMaterial
    * @Author xingyu.xie
    * @Date   2021/1/26 9:46
    * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO>
    **/
    List<NationStandardMaterialDTO> queryAll(NationStandardMaterialDTO nationStandardMaterial);


    /**
       * @Package_name: cn.hsa.module.center.nationstandardmaterial.dao
       * @Class_name: NationStandardMaterialDAO
       * @Describe: 批量插入国脚标准材料表数据
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/4/26 15:32
       * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
    **/
   int  saveBatch(@Param("list") List<NationStandardMaterialDO> data2InsertList);


    /**
     * @Describe: 保存国家标准材料信息
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/6 16:54
     **/
    Boolean saveNationStandardMaterial(NationStandardMaterialDO nationStandardMaterialDO);

    /**
     * @Describe: 更新国家标准材料信息
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/6 16:54
     **/
    Boolean updateNationStandardMaterial(NationStandardMaterialDO nationStandardMaterialDO);
}