package cn.hsa.module.center.nationstandardItem.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.center.nationstandardItem.entity.NationStandardItemDO;
import cn.hsa.module.center.nationstandardItem.entity.NationStandardItemDTO;

import java.util.List;
import java.util.Map;

/**
 * 表名含义：
 * base：基础模块
 * material：国家标准项目
 * <p>
 * 表说明：
 * (NationStandardMaterialDTO)表服务接口
 *
 * @author makejava
 * @since 2021-01-26 09:18:39
 */
public interface NationStandardItemBO {

    /**
     * @Menthod getById
     * @Desrciption
     * @param nationStandardItemDTO 根据id查询国家标准材料
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO
     **/
    NationStandardItemDTO getById(NationStandardItemDTO nationStandardItemDTO);


    /**
     * @Menthod queryAll
     * @Desrciption
     * @param nationStandardItemDTO 查询所有国家标准材料
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO>
     **/
    List<NationStandardItemDTO> queryAll(NationStandardItemDTO nationStandardItemDTO);


    /**
     * @Menthod queryPage
     * @Desrciption
     * @param nationStandardItemDTO 分页查询国家标准材料
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO>
     **/
    PageDTO queryPage(NationStandardItemDTO nationStandardItemDTO);

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