package cn.hsa.module.center.nationstandardItem.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.nationstandardItem.entity.NationStandardItemDO;
import cn.hsa.module.center.nationstandardItem.entity.NationStandardItemDTO;
import cn.hsa.util.ExcelResolveService;

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
 * @author xingyu.xie
 * @since 2021-01-26 09:18:39
 */
public interface NationStandardItemService extends ExcelResolveService {

    /**
    * @Menthod getById
    * @Desrciption
     * @param nationStandardItemDTO 根据id查询国家标准材料
    * @Author xingyu.xie
    * @Date   2021/1/26 9:46
    * @Return cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO
    **/
    WrapperResponse<NationStandardItemDTO> getById(NationStandardItemDTO nationStandardItemDTO);


    /**
    * @Menthod queryAll
    * @Desrciption
     * @param nationStandardItemDTO 查询所有国家标准材料
    * @Author xingyu.xie
    * @Date   2021/1/26 9:46
    * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO>
    **/
    WrapperResponse<List<NationStandardItemDTO>> queryAll(NationStandardItemDTO nationStandardItemDTO);


    /**
    * @Menthod queryPage
    * @Desrciption
     * @param nationStandardItemDTO 分页查询国家标准材料
    * @Author xingyu.xie
    * @Date   2021/1/26 9:46
    * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO>
    **/
    WrapperResponse<PageDTO> queryPage(NationStandardItemDTO nationStandardItemDTO);


    /**
       * @Description: 保存或更新国家标准项目信息
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/7 9:31
    **/
    WrapperResponse<Boolean> saveNationStandardItem(Map nationStandardItemDOMap);
}