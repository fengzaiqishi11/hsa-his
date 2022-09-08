package cn.hsa.module.stro.stock.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.stro.stock.dto.StroStockDTO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_ame: cn.hsa.module.stro.stock.service
 * @Class_name: StroStockService
 * @Description:  药库查询
 * @Author: ljh
 * @Date: 2020/7/30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-stro")
public interface StroStockService {

    /**
     * @Method getById()
     * @Description 通过ID查询单条数据
     *
     * @Param
     * 1、id：主键ID
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<StroStockDTO>
     **/
    @GetMapping("/service/web/stro/cxstrostock/queryById")
    WrapperResponse<StroStockDTO> queryById(Map map);


    /**
     * @Method queryAll()
     * @Description 通过实体作为筛选条件查询
     *
     * @Param
     * cxStroStockDTO 实例对象
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<List<StroStockDTO>>
     **/
    @GetMapping("/service/web/stro/cxstrostock/queryAll")
    WrapperResponse<List<StroStockDTO>> queryAll(Map map);

    /**
     * @Method queryPage()
     * @Description 通过实体作为筛选条件分页查询
     *
     * @Param
     * cxStroStockDTO 实例对象
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/service/web/stro/cxstrostock/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);
    /**
     * @Method queryPage()
     * @Description 通过实体作为筛选条件分页查询
     *
     * @Param
     * StroStockDetailDTO 实例对象
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/service/web/stro/cxstrostock/queryPageDetail")
    WrapperResponse<PageDTO> queryPageDetail(Map map);

    @GetMapping("/service/web/stro/cxstrostock/queryPageDetailCancleNo")
    WrapperResponse<PageDTO> queryPageDetailCancleNo(Map map);

    /**
     * @Method: queryStockDetails
     * @Description:分页获取库存明细
     * @Param: [stroStockDetail]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/5 14:30
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/web/stro/cxstrostock/queryStockDetails")
    WrapperResponse<PageDTO> queryStockDetails(Map map);

    /**
     * @Method queryAllDetail()
     * @Description 通过实体作为筛选条件查询
     *
     * @Param
     * StroStockDetailDTO 实例对象
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<List<StroStockDetailDTO>>
     **/
    @GetMapping("/service/web/stro/cxstrostock/queryAllDetail")
    WrapperResponse<List<StroStockDetailDTO>> queryAllDetail(Map map);

    /**
     * @Method update()
     * @Description 更新
     *
     * @Param
     * StroStockDetailDTO 实例对象
     *
     * @Author lj
     *
     * @Date 2020/7/30 20:53
     * @Return  WrapperResponse<Boolean>
     **/
    @PostMapping("/service/web/stro/cxstrostock/update")
    WrapperResponse<Boolean> update(Map map);

    /**
     * 保存库存信息
     * @param map map {hospCode：医院编码 type: 1:采购入库 2：直接入库 3：退供应商 4：出库到科室 5：出库到药房 6：药房退库 7：盘盈盘亏 8：报损报溢 9：同级调拨（库房） 10：同级调拨（药房）
     *      sfBatchNo ：是否按批次盘点
     *      stroStockDetailDTOList: 库存明细 + 拆零单位、规格、剂型、库位码、订单号、拆零购进价、创建人、创建人ID
     *   }
     * @return
     */
    @PostMapping("/service/web/stro/stroStock/saveStock")
    WrapperResponse<Boolean> saveStock(Map map);

    /**
    * @Method: updateStockOccupy
    * @Description: 占用库存或者解除占用的库存数据(stockNum为负数)
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/9/23 8:57
    * @Return:
    **/
    @PostMapping("/service/web/stro/stroStock/updateStockOccupy")
    WrapperResponse<Boolean> updateStockOccupy(Map map);

    /**
     * @Method: updateOccupy
     * @Descrition: 库存过期更新对应的药品、材料的占用库存数据
     * @Pramas: param
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/11/29
     * @Retrun: null
     */
    @PostMapping("/service/web/stro/stroStock/updateOccupy")
    WrapperResponse<Boolean> updateOccupy(Map map);

    /**
    * @Menthod inserStockByTime
    * @Desrciption 月底库存存储记录
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/12/13 16:12
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/service/web/stro/stroStock/inserStockByTime")
    WrapperResponse<Boolean> insertStockByTime(Map map);

    /**库存效期查询
    * @Method queryValidityWarningPage
    * @Desrciption
    * @param map
    * @Author liuqi1
    * @Date   2021/4/22 14:45
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @PostMapping("/service/web/stro/stroStock/queryValidityWarningPage")
    WrapperResponse<PageDTO> queryValidityWarningPage(Map map);

    /**
    * @Menthod queryStockByBatchAll
    * @Desrciption 查询库存明细
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/6/2 14:49
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @PostMapping("/service/web/stro/stroStock/queryStockByBatchAll")
    WrapperResponse<PageDTO> queryStockByBatchAll(Map map);
    /**
     * @Method queryPageByOutptOrInpt
     * @Desrciption 提供给门诊医生站、住院医生站的药品查询接口
     * @Param [stroStockDTO]
     * @Author zhangguorui
     * @Date   2021/7/16 14:31
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     */
    @PostMapping("/service/web/stro/stroStock/queryPageByOutptOrInpt")
    WrapperResponse<PageDTO> queryPageByOutptOrInpt(Map map);
    /**
     * @Meth: queryAllStockPage
     * @Description: 查询全院库存
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/26
     */
    @GetMapping("/service/web/stro/cxstrostock/queryAllStockPage")
    WrapperResponse<PageDTO> queryAllStockPage(Map map);
    /**
     * @Meth: queryAllStockDetails
     * @Description: 查询全院库存明细
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/26
     */
    @GetMapping("/service/web/stro/cxstrostock/queryAllStockDetails")
    WrapperResponse<PageDTO> queryAllStockDetails(Map map);
    /**
     * 查询药品和材料的利润统计信息
     * @param map
     * @return
     */
    @GetMapping("/service/web/stro/cxstrostock/queryDrugAndMaterialProfit")
    WrapperResponse<PageDTO> queryDrugAndMaterialProfit(Map map);

    @PostMapping("/service/web/stro/stroStock/updateOccupyByExpire")
    WrapperResponse<Boolean>  updateOccupyByExpire(Map<String,Object> map);
}
