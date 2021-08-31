package cn.hsa.center.nationstandardmaterial.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.nationstandardmaterial.bo.NationStandardMaterialBO;
import cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDO;
import cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO;
import cn.hsa.module.center.nationstandardmaterial.service.NationStandardMaterialService;
import cn.hsa.util.MapUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
@HsafRestPath("/service/center/nationStandardMaterial")
@Service("nationStandardMaterialService_provider")
@Slf4j
public class NationStandardMaterialServiceImpl extends HsafService implements NationStandardMaterialService {


    @Resource
    private NationStandardMaterialBO nationStandardMaterialBO;

    /**
     * @Menthod getById
     * @Desrciption
     * @param nationStandardMaterialDTO 根据id查询国家标准材料
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO
     **/
    @Override
    public WrapperResponse<NationStandardMaterialDTO> getById(NationStandardMaterialDTO nationStandardMaterialDTO){
        return WrapperResponse.success(nationStandardMaterialBO.getById(nationStandardMaterialDTO));
    }


    /**
     * @Menthod queryAll
     * @Desrciption
     * @param nationStandardMaterialDTO 查询所有国家标准材料
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO>
     **/
    @Override
    public WrapperResponse<List<NationStandardMaterialDTO>> queryAll(NationStandardMaterialDTO nationStandardMaterialDTO){
        return WrapperResponse.success(nationStandardMaterialBO.queryAll(nationStandardMaterialDTO));
    }


    /**
     * @Menthod queryPage
     * @Desrciption
     * @param nationStandardMaterialDTO 分页查询国家标准材料
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(NationStandardMaterialDTO nationStandardMaterialDTO){
        return WrapperResponse.success(nationStandardMaterialBO.queryPage(nationStandardMaterialDTO));
    }

    /**
       * @Description: 保存国家标准材料信息
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/6 17:29
       * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
    **/
    @Override
    public WrapperResponse<Boolean> saveNationStandardMaterial(Map nationStandardMaterialDOParam) {
        NationStandardMaterialDO nationStandardMaterialDO = null;
        try {
             nationStandardMaterialDO = MapUtils.get(nationStandardMaterialDOParam, "nationStandardMaterialDO");
            if(nationStandardMaterialDO == null){
                throw new AppException("参数不能为空");
            }
            nationStandardMaterialDO.setCode(SnowflakeUtils.getId());
            nationStandardMaterialDO.setRegCertNo(SnowflakeUtils.getId());
            if(StringUtils.isEmpty(nationStandardMaterialDO.getCode())){
                throw new AppException("材料编码不可为空");
            }
        }catch (AppException ae){
            WrapperResponse<Boolean> rt = WrapperResponse.fail(false);
            rt.setMessage(ae.getMessage());
            return rt;
        }
        if(StringUtils.isEmpty(nationStandardMaterialDO.getId())){
            nationStandardMaterialDO.setId(SnowflakeUtils.getId());
            return WrapperResponse.success(nationStandardMaterialBO.saveNationStandardMaterial(nationStandardMaterialDO));
        }
        return WrapperResponse.success(nationStandardMaterialBO.updateNationStandardMaterial(nationStandardMaterialDO));
    }

    @Override
    public WrapperResponse<Boolean> insertUpLoad(Map map) {
        return WrapperResponse.success(nationStandardMaterialBO.insertUpLoad(map));
    }


    @Override
    public void insertProcessedUploadData(Map<String, Object> dataMap) {
         this.insertUpLoad(dataMap);
    }
}