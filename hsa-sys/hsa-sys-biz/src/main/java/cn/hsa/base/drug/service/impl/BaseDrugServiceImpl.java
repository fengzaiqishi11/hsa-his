package cn.hsa.base.drug.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.drug.bo.BaseDrugBO;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.base.drug.service.BaseDrugService;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.drug.service.impl
 * @Class_name: BaseDrugServiceImpl
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/7/30 15:25
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/base/baseDrug")
@Slf4j
@Service("baseDrugService_provider")
public class BaseDrugServiceImpl extends HsafService implements BaseDrugService {

    /**
     * 药品管理业务逻辑接口
     */
    @Resource
    private BaseDrugBO baseDrugBO;

    /**
     * @Method getById
     * @Desrciption 通过id获取药品信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/16 9:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bi.dto.BaseDrugDTO>
     **/
    @Override
    public WrapperResponse<BaseDrugDTO> getById(Map map){
        return WrapperResponse.success(baseDrugBO.getById(MapUtils.get(map,"baseDrugDTO")));
    }

    /**
     * @Method: getByCode
     * @Description: 根据编码获取药品信息
     * @Param: [baseDrugDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/9 15:59
     * @Return: cn.hsa.module.base.drug.dto.BaseDrugDTO
     **/
    @Override
    public WrapperResponse<BaseDrugDTO> getByCode(Map map){
        return WrapperResponse.success(baseDrugBO.getByCode(MapUtils.get(map,"baseDrugDTO")));
    }

    /**
     * @Method queryPage()
     * @Description 分页查询所有药品信息(默认状态为有效)
     *
     * @Param
     * [map]
     *
     * @Author liaojunjie
     * @Date 2020/7/8 8:53
     * @Return WrapperResponse<PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        return WrapperResponse.success(baseDrugBO.queryPage(MapUtils.get(map,"baseDrugDTO")));
    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有药品信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/18 11:55
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.drug.dto.BaseDrugDTO>>
     **/
    @Override
    public WrapperResponse<List<BaseDrugDTO>> queryAll(Map map) {
        return WrapperResponse.success(baseDrugBO.queryAll(MapUtils.get(map,"baseDrugDTO")));
    }

    /**
     * @Method updateStatus()
     * @Description 修改有效标识状态
     *
     * @Param
     * [map]
     *
     * @Author liaojunjie
     * @Date 2020/7/16 20:55
     * @Return WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateStatus(Map map) {
        return WrapperResponse.success(baseDrugBO.updateStatus(MapUtils.get(map,"baseDrugDTO")));
    }

    /**
     * @Method save
     * @Desrciption 新增/修改单条药品信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/24 16:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> save(Map map) {
        return WrapperResponse.success(baseDrugBO.save(MapUtils.get(map,"baseDrugDTO")));
    }

    /**
     * @Method updateAllById
     * @Desrciption 通过ID数组修改所有药品的信息
     * @Param
     * [baseDrugDTO]
     * @Author liaojunjie
     * @Date   2020/8/14 10:20
     * @Return java.lang.Boolean
     **/
    @Override
    public WrapperResponse<Boolean> updateAllById(Map map) {
        return WrapperResponse.success(baseDrugBO.updateAllById(MapUtils.get(map,"baseDrugDTOs")));
    }

    /**
     * @Method queryStockDrugInfoOfDept
     * @Desrciption 查询某库位的项目(药品或材料)信息
     * @param map
     * @Author liuqi1
     * @Date   2020/8/12 11:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.drug.dto.BaseDrugDTO>>
     **/
    @Override
    public WrapperResponse<PageDTO> queryStockItemInfoPage(Map<String, Object> map) {
        BaseDeptDTO baseDeptDTO = MapUtils.get(map, "baseDeptDTO");
        PageDTO pageDTO = baseDrugBO.queryStockItemInfoPage(baseDeptDTO);
        return WrapperResponse.success(pageDTO);
    }

    @Override
    public WrapperResponse<Boolean> upLoad(Map map) {
        return WrapperResponse.success(baseDrugBO.insertUpload(map));
    }
    /**
     * @param map
     * @Method insertInsureDrugMatch
     * @Desrciption 医保统一支付平台： 同步药品数据到医保匹配表
     * @Param
     * @Author fuhui
     * @Date 2021/3/20 11:05
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> insertInsureDrugMatch(Map<String, Object> map) {
        return WrapperResponse.success(baseDrugBO.insertInsureDrugMatch(map));
    }


    @Override
    public WrapperResponse<Boolean> updateBaseDrugS(Map<String, Object> map) {
        BaseDrugDTO baseDrugDTO = (BaseDrugDTO) map.get("baseDrugDTO");
        if(baseDrugDTO == null || StringUtils.isEmpty(baseDrugDTO.getId())){
                throw  new RuntimeException("药品信息不能为空");
        }
        return WrapperResponse.success(baseDrugBO.updateBaseDrugS(baseDrugDTO));
    }

    /**
     * @param map
     * @Method queryUnifiedPage
     * @Desrciption 医保统一支付平台：药品匹配优化
     * @Param map
     * @Author fuhui
     * @Date 2021/4/16 16:21
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryUnifiedPage(Map<String, Object> map) {
        return WrapperResponse.success(baseDrugBO.queryUnifiedPage(MapUtils.get(map,"baseDrugDTO")));
    }

    /**
     * @Meth: queryEnableCancel
     * @Description: 查看能否作废药品
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangguorui
     * @Date: 2021/9/27
     */
    @Override
    public WrapperResponse<Boolean> queryEnableCancel(Map map) {
        return WrapperResponse.success(baseDrugBO.queryEnableCancel(MapUtils.get(map,"baseDrugDTO")));
    }
}
