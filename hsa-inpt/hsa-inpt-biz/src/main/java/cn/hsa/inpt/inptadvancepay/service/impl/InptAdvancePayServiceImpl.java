package cn.hsa.inpt.inptadvancepay.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.advancepay.bo.InptAdvancePayBO;
import cn.hsa.module.inpt.advancepay.dto.InptAdvancePayDTO;
import cn.hsa.module.inpt.advancepay.service.InptAdvancePayService;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.inptadvancepay.service.impl
 * @Class_name: InptAdvancePayBOServiceImpl
 * @Describe: 预交金管理service层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/9/8 16:18
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/inpt/inptAdvancePay")
@Service("inptAdvancePayService_provider")
public class InptAdvancePayServiceImpl extends HsafService implements InptAdvancePayService {

    @Resource
    private InptAdvancePayBO inptAdvancePayBO;

    /**
    * @Menthod getById
    * @Desrciption  根据id查询单条预交金信息
     * @param map
    * @Author xingyu.xie
    * @Date   2020/9/14 8:48
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.inpt.advancepay.dto.InptAdvancePayDTO>
    **/
    @Override
    public WrapperResponse<InptAdvancePayDTO> getById(Map map) {
        return WrapperResponse.success(inptAdvancePayBO.getById(MapUtils.get(map, "inptAdvancePayDTO")));
    }

    /**
     * @Method queryPatientInfoPage
     * @Desrciption 分页查询患者信息
     * @param map
     * @Author xingyu.xie
     * @Date   2020/9/4 10:37
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPatientInfoPage(Map<String, Object> map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
        //此方法与催款查询公用，为了不冲突  加 statusCodes字段，默认过滤 作废（7） ， 出院（8）的病人
        String [] statusCodes = {"7","8"};
        inptVisitDTO.setStatusCodes(statusCodes);
        PageDTO pageDTO = inptAdvancePayBO.queryPatientInfoPage(inptVisitDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
    * @Menthod queryAll
    * @Desrciption  根据实体筛选所有预交金信息
     * @param map
    * @Author xingyu.xie
    * @Date   2020/9/14 8:49
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.advancepay.dto.InptAdvancePayDTO>>
    **/
    @Override
    public WrapperResponse<List<InptAdvancePayDTO>> queryAll(Map map) {
        return WrapperResponse.success(inptAdvancePayBO.queryAll(MapUtils.get(map, "inptAdvancePayDTO")));
    }

    /**
    * @Menthod queryPage
    * @Desrciption  分页并且根据实体筛选所有预交金信息
     * @param map
    * @Author xingyu.xie
    * @Date   2020/9/14 8:49
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        return WrapperResponse.success(inptAdvancePayBO.queryPage(MapUtils.get(map, "inptAdvancePayDTO")));
    }

    /**
    * @Menthod insert
    * @Desrciption  新增单条预交金信息
     * @param map
    * @Author xingyu.xie
    * @Date   2020/9/14 8:49
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> insert(Map map) {
        return WrapperResponse.success(inptAdvancePayBO.insert(MapUtils.get(map, "inptAdvancePayDTO")));
    }


    /**
    * @Menthod flushingRed
    * @Desrciption  多条预交金冲红
     * @param map
    * @Author xingyu.xie
    * @Date   2020/9/14 8:50
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> flushingRed(Map map) {
        return WrapperResponse.success(inptAdvancePayBO.updateFlushingRed(MapUtils.get(map, "inptAdvancePayDTOList")));
    }

    /**
    * @Menthod updateInptAdvancePay
    * @Desrciption  修改单条预交金信息（有非空判断）
     * @param map
    * @Author xingyu.xie
    * @Date   2020/9/14 8:50
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateInptAdvancePay(Map map) {
        return WrapperResponse.success(inptAdvancePayBO.updateInptAdvancePay(MapUtils.get(map, "inptAdvancePayDTO")));
    }

    /**
    * @Menthod updateInptAdvancePayS
    * @Desrciption  修改单条预交金信息（无非空判断）
     * @param map
    * @Author xingyu.xie
    * @Date   2020/9/14 8:50
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateInptAdvancePayS(Map map) {
        return WrapperResponse.success(inptAdvancePayBO.updateInptAdvancePayS(MapUtils.get(map, "inptAdvancePayDTO")));
    }

    /**
    * @Menthod deleteById
    * @Desrciption  根据id删除单条预交金信息
     * @param map
    * @Author xingyu.xie
    * @Date   2020/9/14 8:51
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> deleteById(Map map) {
        return WrapperResponse.success(inptAdvancePayBO.deleteById(MapUtils.get(map, "inptAdvancePayDTO")));
    }

    /**
     * @Menthod queryAdvancePay
     * @Desrciption 预交金查询接口
     * @param map
     * @Author luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date 2020/9/9 11:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryAdvancePay(Map map) {
        return WrapperResponse.success(inptAdvancePayBO.queryAdvancePay(MapUtils.get(map, "inptVisitDTO")));
    }
}
