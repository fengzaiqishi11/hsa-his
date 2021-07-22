package cn.hsa.module.base.ba.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.util.MapUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.baseadvice.service
 * @Class_name: BaseAdviceService
 * @Describe: 医嘱信息Service接口层（提供给dubbo调用）
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/13 16:29
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-base")
public interface BaseAdviceService {
    /**
     * @Menthod getById
     * @Desrciption   根据主键ID查询医嘱信息
     * @param map id 医嘱信息表主键ID
     * @Author xingyu.xie
     * @Date   2020/7/14 16:17
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseAdviceDTO>
     **/
    @PostMapping("/service/base/baseAdvice/getById")
    WrapperResponse<BaseAdviceDTO> getById(Map map);

    /**
     * @Menthod queryAllAdviceDetail
     * @Desrciption   根据主键ID查询医嘱信息
     * @param map id 医嘱信息表主键ID
     * @Author xingyu.xie
     * @Date   2020/7/14 16:17
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseAdviceDTO>
     **/
    @PostMapping("/service/base/baseAdvice/queryAllAdviceDetail")
    WrapperResponse<List<BaseAdviceDetailDTO>> queryAllAdviceDetail(Map map);

    /**
     * @Menthod queryItemsByAdviceCode
     * @Desrciption   根据医嘱编码查询医嘱详细表，再根据项目编码查询所有项目
     * @param map   医嘱编码,医院编码等信息
     * @Author xingyu.xie
     * @Date   2020/7/14 16:04
     * @Return java.util.List<cn.hsa.module.base.bad.dto.BaseAdviceDTO>
     **/
    WrapperResponse<PageDTO> queryItemsByAdviceCode(Map map);

    /**
     * @Menthod queryPage
     * @Desrciption   查询全部医嘱信息
     * @param map 医院编码
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/service/base/baseAdvice/queryAll")
    WrapperResponse<List<BaseAdviceDTO>> queryAll(Map map);

    /**
     * @Menthod queryPage
     * @Desrciption   分页查询医嘱信息
     * @param map 医嘱信息数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/service/base/baseAdvice/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Menthod insert
     * @Desrciption 新增单条医嘱信息
     * @param map  医嘱分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseAdviceDTO>
     **/
    @PostMapping("/service/base/baseAdvice/insert")
    WrapperResponse<Boolean> insert(Map map);

    /**
     * @Menthod update
     * @Desrciption 修改单条医嘱信息
     * @param map  医嘱分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseAdviceDTO>
     **/
    @PostMapping("/service/base/baseAdvice/update")
    WrapperResponse<Boolean> update(Map map);

    /**
     * @Menthod updateStatus
     * @Desrciption   根据主键ID删除医嘱信息
     * @param map  医嘱信息表主键
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/base/baseAdvice/updateStatus")
    WrapperResponse<Boolean> updateStatus(Map map);

    /**
     * @Menthod queryItemAndMaterialPage
     * @Desrciption 查询项目和材料表二合一数据
     * @param map 医嘱详细数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/4 16:21
     * @Return java.util.List<cn.hsa.module.base.ba.dto.BaseItemAndMaterialDTO>
     **/
    @GetMapping("/service/base/baseAdvice/queryItemAndMaterialPage")
    WrapperResponse<PageDTO> queryItemAndMaterialPage(Map map);

    /**
     * @Menthod updateBaseAdviceAndBaseAdviceDetailsByItemCode
     * @Desrciption 更新材料或项目的单价或者
     * @param map 医院编码hospCode baseAdviceDetailDTO 项目或材料的  1.单价 2.名称 3.单位代码 4.规格
     * @Author xingyu.xie
     * @Date   2020/9/4 14:41
     * @Return boolean
     **/
    @PostMapping("/service/base/baseAdvice/updateBaseAdviceAndBaseAdviceDetailsByItemCode")
    WrapperResponse<Boolean> updateBaseAdviceAndBaseAdviceDetailsByItemCode(Map map);

    /**
     * @Method: getBaseAdvices
     * @Description: 根据医嘱ID,就诊ID获取医嘱目录列表
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/18 16:41
     * @Return: java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDTO>
     **/
    @GetMapping("/service/base/baseAdvice/getBaseAdvices")
    WrapperResponse<List<BaseAdviceDTO>> getBaseAdvices(Map map);

    /**
    * @Method queryItemAndMaterialAndDrugPage
    * @Param [map]
    * @description    查询和项目和材料和药品三合一表
    * @author marong
    * @date 2020/9/29 15:43
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    * @throws
    */
    @GetMapping("/service/base/baseAdvice/queryItemAndMaterialAndDrugPage")
    WrapperResponse<PageDTO> queryItemAndMaterialAndDrugPage(Map map);

    /**
     * @Method: getBaseAdviceByCode
     * @Description: 根据编码获取医嘱目录信息
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/3 10:25
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDTO>>
     **/
    @GetMapping("/service/base/baseAdvice/getBaseAdviceByCode")
    WrapperResponse<BaseAdviceDTO> getBaseAdviceByCode(Map map);

    /**
    * @Method queryOperationNameList
    * @Param [map]
    * @description   查询手术医嘱
    * @author marong
    * @date 2020/12/1 9:03
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO>>
    * @throws
    */
    WrapperResponse<List<BaseAdviceDTO>> queryOperationNameList(Map<String, Object> map);

    /**
    * @Menthod generateAdviceByItem
    * @Desrciption  根据项目生成医嘱
     * @param map
    * @Author xingyu.xie
    * @Date   2020/12/8 9:50
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.String>
    **/
     WrapperResponse<List<BaseItemDTO>> generateAdviceByItem(Map<String, Object> map);

    /**
     * @Method getOperationNamePage
     * @Param [baseAdviceDTO]
     * @description   查询手术医嘱分页数据展示
     * @author Mr.Liao
     * @date 2020/12/18 14:38
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @throws
     */
    WrapperResponse<PageDTO> getOperationNamePage(Map<String, Object> map);

    /**
     * @Method queryDetailByAdviceCode
     * @Param [baseAdviceDTO]
     * @description   根据CODE获取医嘱目录明细
     * @author pengbo
     * @date 2021/04/01 14:38
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @throws
     */
    WrapperResponse<PageDTO> queryDetailByAdviceCode(Map<String, Object> map);

    /**合管条码打印查询
    * @Method queryPipePrintPage
    * @Desrciption
    * @param map
    * @Author liuqi1
    * @Date   2021/4/25 11:32
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    WrapperResponse<PageDTO> queryPipePrintPage(Map map);

    /**合管条码打印更新
    * @Method updateWithPipePrint
    * @Desrciption
    * @param map
    * @Author liuqi1
    * @Date   2021/4/26 11:53
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    WrapperResponse<Boolean> updateWithPipePrint(Map map);


}
