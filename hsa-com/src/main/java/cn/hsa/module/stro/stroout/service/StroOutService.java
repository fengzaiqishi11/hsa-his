package cn.hsa.module.stro.stroout.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.stro.stroout.dto.StroOutDTO;
import cn.hsa.module.stro.stroout.dto.StroOutDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.stro.stroout.service
 * @Class_name: StrooutService
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/7/30 17:23
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-stro")
public interface StroOutService {
    /**
     * @Method getById
     * @Desrciption 通过id获取到某个出库单信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/30 19:15
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.outinstro.dto.StroOutinDTO>
     **/
    @GetMapping("/service/stro/stroOut/getById")
    WrapperResponse<StroOutDTO> getById(Map map);

    /**
     * @Method queryPage
     * @Desrciption 分页查询出库单信息（包含出库明细）
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/30 19:15
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/

    @GetMapping("/service/stro/stroOut/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method queryAll
     * @Desrciption 查询所有出库单信息（包含出库明细）
     * @Param
     * [stroOutinDTO]
     * @Author liaojunjie
     * @Date   2020/7/30 19:15
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.stro.outinstro.dto.StroOutDTO>>
     **/
    @GetMapping("/service/stro/stroOut/queryAll")
    WrapperResponse<List<StroOutDTO>> queryAll(Map map);

    /**
     * @Method save
     * @Desrciption 新增和保存出库单信息(包括出库单明细)
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/30 19:16
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @GetMapping("/service/stro/stroOut/save")
    WrapperResponse<Boolean> save(Map map);

    /**
     * @Method updateAuditCode
     * @Desrciption 审核和作废
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/30 19:16
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @GetMapping("/service/stro/stroOut/updateAuditCode")
    WrapperResponse<Boolean> updateAuditCode(Map map);

    /**
     * @Method queryStock
     * @Desrciption 查询处理后的库存明细
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/8/11 19:32
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.stock.dto.StroStockDTO>
     **/
    @GetMapping("/service/stro/stroOut/queryStock")
    WrapperResponse<PageDTO> queryStock(Map map);

    /**
     * @Method insertWholeOut
     * @Desrciption 整单出库
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/16 10:42
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.stroout.dto.StroOutDTO>
     **/
    @PostMapping("/service/stro/stroOut/insertWholeOut")
    WrapperResponse<StroOutDTO> insertWholeOut(Map map);

    /**
     * @Method queryWholeOut
     * @Desrciption 整单出库前进行库存数量查询
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/16 10:42
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.stroout.dto.StroOutDTO>
     **/
    @PostMapping("/service/stro/stroOut/queryWholeOut")
    WrapperResponse<StroOutDTO> queryWholeOut(Map map);
    /**
     * @Meth: queryStroOutDetail
     * @Description: 根据出库单id 查询 出库单明细
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.stro.stroout.dto.StroOutDetailDTO>>
     * @Author: zhangguorui
     * @Date: 2021/12/14
     */
    @GetMapping("/service/stro/stroOut/queryStroOutDetail")
    WrapperResponse<List<StroOutDetailDTO>> queryStroOutDetail(Map map);
}
