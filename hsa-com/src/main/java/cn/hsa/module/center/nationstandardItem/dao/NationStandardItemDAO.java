package cn.hsa.module.center.nationstandardItem.dao;

import cn.hsa.module.center.nationstandardItem.entity.NationStandardItemDO;
import cn.hsa.module.center.nationstandardItem.entity.NationStandardItemDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 表名含义：
 * base：基础模块
 * material：国家标准项目
 * <p>
 * 表说明：
 * (NationStandardItemDTO)表数据库访问层
 *
 * @author xingyu.xie
 * @since 2021-01-26 09:18:34
 */
public interface NationStandardItemDAO {


    /**
    * @Menthod getById
    * @Desrciption  
     * @param nationStandardItemDTO
    * @Author xingyu.xie
    * @Date   2021/1/26 9:45 
    * @Return cn.hsa.module.center.nationstandardmaterial.entity.NationStandardItemDTO
    **/
    NationStandardItemDTO getById(NationStandardItemDTO nationStandardItemDTO);

    /**
    * @Menthod queryAll
    * @Desrciption  
     * @param nationStandardItemDTO
    * @Author xingyu.xie
    * @Date   2021/1/26 9:46
    * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardItemDTO>
    **/
    List<NationStandardItemDTO> queryAll(NationStandardItemDTO nationStandardItemDTO);

    /**
       * @Description: 保存国家标准材料信息
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/7 9:23
    **/
    Boolean saveNationStandardItem(NationStandardItemDO nationStandardItemDO);

    /**
       * @Description: 更新国家标准材料信息
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/7 9:23
    **/
    Boolean updateNationStandardItem(NationStandardItemDO nationStandardItemDO);

    /**
     *  批量保存国家标准项目数据
     *
     * @param dataList 国家标准项目的实体集合
     * @Author: luonianxin
     * @Date: 2021-05-08
     * @Return: Boolean 是否成功
     */
    Boolean saveNationStandardItemBatch(@Param("list") List<NationStandardItemDO> dataList);
}