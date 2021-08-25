package cn.hsa.base.bmm.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bmm.bo.BaseMaterialBO;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.bmm.service.BaseMaterialService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.bmm.service.impl
 * @Class_name: BaseMaterialManagementServiceImpl
 * @Describe: 材料信息API接口实现层（提供给dubbo调用）
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/7 16:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/base/baseMateria")
@Slf4j
@Service("baseMaterialService_provider")
public class BaseMaterialServiceImpl  extends HsafService implements BaseMaterialService {
    @Resource
    BaseMaterialBO baseMaterialBO;

    /**
    * @Menthod getById
    * @Desrciption   根据主键id和医院编码查询材料信息
     * @param map 材料信息表主键
    * @Author xingyu.xie
    * @Date   2020/7/8 15:37
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
    **/
    @Override
    public WrapperResponse<BaseMaterialDTO> getById(Map map) {
        BaseMaterialDTO dto = baseMaterialBO.getById(MapUtils.get(map,"baseMaterialDTO"),MapUtils.get(map,"type",""));
        return WrapperResponse.success(dto);
    }

    /**
     * @Method: getByCode
     * @Description: 根据编码获取材料信息
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/9 16:17
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    @Override
    public WrapperResponse<BaseMaterialDTO> getByCode(Map map) {
        BaseMaterialDTO dto = baseMaterialBO.getByCode(MapUtils.get(map,"baseMaterialDTO"));
        return WrapperResponse.success(dto);
    }

    /**
     * @Menthod queryAll
     * @Desrciption   查询全部
     * @param map 医院编码
     * @Author xingyu.xie
     * @Date   2020/7/8 15:38
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     *
     * @return*/
    @Override
    public WrapperResponse<List<BaseMaterialDTO>> queryAll(Map map) {
        List<BaseMaterialDTO> dto = baseMaterialBO.queryAll(MapUtils.get(map,"baseMaterialDTO"));
        return WrapperResponse.success(dto);
    }

    /**
    * @Method queryBaseMaterialDTOs
    * @Desrciption 查询符合条件的材料信息
    * @param map
    * @Author liuqi1
    * @Date   2020/9/3 15:18
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>>
    **/
    @Override
    public WrapperResponse<List<BaseMaterialDTO>> queryBaseMaterialDTOs(Map<String, Object> map) {
        BaseMaterialDTO baseMaterialDTO = MapUtils.get(map,"baseMaterialDTO");
        List<BaseMaterialDTO> baseMaterialDTOS = baseMaterialBO.queryBaseMaterialDTOs(baseMaterialDTO);
        return WrapperResponse.success(baseMaterialDTOS);
    }

    /**
    * @Menthod queryPage
    * @Desrciption   根据数据对象来筛选查询
     * @param map 材料分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/8 15:38
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        PageDTO dto = baseMaterialBO.queryPage(MapUtils.get(map,"baseMaterialDTO"));
        return WrapperResponse.success(dto);
    }

    /**
     * @Menthod save
     * @Desrciption  新增或修改材料分类
     * @param map 材料分类数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:37
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> save(Map map) {
        return WrapperResponse.success(baseMaterialBO.save(MapUtils.get(map,"baseMaterialDTO")));
    }

    /**
    * @Menthod updateList
    * @Desrciption  修改多条材料信息
     * @param map 材料分类数据参数对象list和医院编码
    * @Author xingyu.xie
    * @Date   2020/8/24 15:38
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateList(Map map) {
        return WrapperResponse.success(baseMaterialBO.updateList(MapUtils.get(map,"baseMaterialDTOList")));
    }

    /**
    * @Menthod updateStatus
    * @Desrciption   删除一个或多个材料信息
     * @param map 材料信息表的主键列表
    * @Author xingyu.xie
    * @Date   2020/7/8 15:40
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateStatus(Map map) {

        return WrapperResponse.success(baseMaterialBO.updateStatus(MapUtils.get(map,"baseMaterialDTO")));
    }

    /**
     * @Menthod updateStatus
     * @Desrciption   删除一个或多个材料信息
     * @param map 材料信息表的主键列表
     * @Author xingyu.xie
     * @Date   2020/7/8 15:40
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> insertUpLoad(Map map) {
        return WrapperResponse.success(baseMaterialBO.insertUpLoad(map));
    }


    /**
     * @param map
     * @Method insertInsureDrugMatch
     * @Desrciption 医保统一支付平台： 同步材料数据到医保匹配表
     * @Param
     * @Author fuhui
     * @Date 2021/3/20 11:05
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> insertInsureMaterialMatch(Map<String, Object> map) {
        return WrapperResponse.success(baseMaterialBO.insertInsureMaterialMatch(map));
    }

    /**
     * @param map 材料信息数据传输对象List
     * @Menthod updateNationCodeById
     * @Desrciption 批量修改材料信息
     * @Author pengbo
     * @Date 2021/3/25 16:48
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateNationCodeById(Map map) {
        return WrapperResponse.success(baseMaterialBO.updateNationCodeById(map));
    }

    @Override
    public WrapperResponse<PageDTO> queryUnifiedPage(Map<String, Object> map) {
        PageDTO dto = baseMaterialBO.queryUnifiedPage(MapUtils.get(map,"baseMaterialDTO"));
        return WrapperResponse.success(dto);
    }
}
