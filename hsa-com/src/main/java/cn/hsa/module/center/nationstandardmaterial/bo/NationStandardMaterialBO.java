package cn.hsa.module.center.nationstandardmaterial.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDO;
import cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO;

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
 * @author makejava
 * @since 2021-01-26 09:18:39
 */
public interface NationStandardMaterialBO {

    /**
     * @Menthod getById
     * @Desrciption
     * @param nationStandardMaterialDTO 根据id查询国家标准材料
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO
     **/
    NationStandardMaterialDTO getById(NationStandardMaterialDTO nationStandardMaterialDTO);


    /**
     * @Menthod queryAll
     * @Desrciption
     * @param nationStandardMaterialDTO 查询所有国家标准材料
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO>
     **/
    List<NationStandardMaterialDTO> queryAll(NationStandardMaterialDTO nationStandardMaterialDTO);


    /**
     * @Menthod queryPage
     * @Desrciption
     * @param nationStandardMaterialDTO 分页查询国家标准材料
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO>
     **/
    PageDTO queryPage(NationStandardMaterialDTO nationStandardMaterialDTO);


    /**
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/6 16:57
       * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
    **/
    boolean insertUpLoad(Map map);

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