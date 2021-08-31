package cn.hsa.module.center.nationstandardmaterial.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO;
import cn.hsa.util.ExcelResolveService;

import java.util.List;
import java.util.Map;

/**
 * 表名含义：
 * base：基础模块
 * material：国家标准材料
 * <p>
 * 表说明：
 * (NationStandardMaterialDTO)表服务接口
 *
 * @author xingyu.xie
 * @since 2021-01-26 09:18:39
 */
public interface NationStandardMaterialService extends ExcelResolveService {

    /**
    * @Menthod getById
    * @Desrciption
     * @param nationStandardMaterialDTO 根据id查询国家标准材料
    * @Author xingyu.xie
    * @Date   2021/1/26 9:46
    * @Return cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO
    **/
    WrapperResponse<NationStandardMaterialDTO> getById(NationStandardMaterialDTO nationStandardMaterialDTO);


    /**
    * @Menthod queryAll
    * @Desrciption
     * @param nationStandardMaterialDTO 查询所有国家标准材料
    * @Author xingyu.xie
    * @Date   2021/1/26 9:46
    * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO>
    **/
    WrapperResponse<List<NationStandardMaterialDTO>> queryAll(NationStandardMaterialDTO nationStandardMaterialDTO);


    /**
    * @Menthod queryPage
    * @Desrciption
     * @param nationStandardMaterialDTO 分页查询国家标准材料
    * @Author xingyu.xie
    * @Date   2021/1/26 9:46
    * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO>
    **/
    WrapperResponse<PageDTO> queryPage(NationStandardMaterialDTO nationStandardMaterialDTO);


    /**
       * @Package_name: cn.hsa.module.center.nationstandardmaterial.service
       * @Class_name: NationStandardMaterialService
       * @Describe: 文件导入国家标准物料数据
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/4/26 15:22
       * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
    **/

     WrapperResponse<Boolean> insertUpLoad(Map map);

    /**
     * @Description: 保存国家标准材料信息
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/6 16:54
     * @Return: 成功的标志
     **/
    WrapperResponse<Boolean> saveNationStandardMaterial(Map nationStandardMaterialDOMap);
}