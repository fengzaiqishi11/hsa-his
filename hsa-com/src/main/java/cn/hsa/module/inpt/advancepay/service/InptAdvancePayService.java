package cn.hsa.module.inpt.advancepay.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.advancepay.dto.InptAdvancePayDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.inptadvice.service
 * @Class_name: BaseAdvancePayService
 * @Describe: 预交金信息Service接口层（提供给dubbo调用）
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/13 16:29
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-inpt")
public interface InptAdvancePayService {
    /**
     * @Menthod getById
     * @Desrciption   根据主键ID查询预交金信息
     * @param map id 预交金信息表主键ID
     * @Author xingyu.xie
     * @Date   2020/7/14 16:17
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.inpt.bmm.dto.InptAdvancePayDTO>
     **/
    @GetMapping("/service/inpt/inptAdvancePay/getById")
    WrapperResponse<InptAdvancePayDTO> getById(Map map);

    /**
     * @Method queryPatientInfoPage
     * @Desrciption 分页查询患者信息
     * @param map
     * @Author xingyu.xie
     * @Date   2020/9/4 10:37
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
     **/
    @GetMapping("/service/inpt/inptAdvancePay/queryPatientInfoPage")
    WrapperResponse<PageDTO> queryPatientInfoPage(Map<String,Object> map);

    /**
     * @Menthod queryPage
     * @Desrciption   查询全部预交金信息
     * @param map 医院编码
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.inpt.PageDTO>
     **/
    @GetMapping("/service/inpt/inptAdvancePay/queryAll")
    WrapperResponse<List<InptAdvancePayDTO>> queryAll(Map map);

    /**
     * @Menthod queryPage
     * @Desrciption   分页查询预交金信息
     * @param map 预交金信息数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.inpt.PageDTO>
     **/
    @GetMapping("/service/inpt/inptAdvancePay/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Menthod insert
     * @Desrciption 新增单条预交金信息
     * @param map  预交金分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.inpt.bmm.dto.InptAdvancePayDTO>
     **/
    @PostMapping("/service/inpt/inptAdvancePay/insert")
    WrapperResponse<Boolean> insert(Map map);


    /**
     * @Menthod flushingRed
     * @Desrciption 预交金冲红
     * @param map  预交金分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.inpt.bmm.dto.InptAdvancePayDTO>
     **/
    @PostMapping("/service/inpt/inptAdvancePay/flushingRed")
    WrapperResponse<Boolean> flushingRed(Map map);

    /**
     * @Menthod update
     * @Desrciption 修改单条预交金信息(有非空判断)
     * @param map  预交金分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.inpt.bmm.dto.InptAdvancePayDTO>
     **/
    @PostMapping("/service/inpt/inptAdvancePay/updateInptAdvancePay")
    WrapperResponse<Boolean> updateInptAdvancePay(Map map);

    /**
     * @Menthod update
     * @Desrciption 修改单条预交金信息(无非空判断)
     * @param map  预交金分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.inpt.bmm.dto.InptAdvancePayDTO>
     **/
    @PostMapping("/service/inpt/inptAdvancePay/updateInptAdvancePayS")
    WrapperResponse<Boolean> updateInptAdvancePayS(Map map);

    /**
     * @Menthod updateStatus
     * @Desrciption   根据主键ID删除预交金信息
     * @param map  预交金信息表主键
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/inpt/inptAdvancePay/deleteById")
    WrapperResponse<Boolean> deleteById(Map map);

    /**
     * @Menthod queryAdvancePay
     * @Desrciption  预交金查询接口
     * @param map
     * @Author luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date   2020/9/9 11:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/inpt/inptAdvancePay/queryAdvancePay")
    WrapperResponse<PageDTO> queryAdvancePay(Map map);
}
