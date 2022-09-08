package cn.hsa.stro.stock.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.stro.stock.bo.StroStockBO;
import cn.hsa.module.stro.stock.dto.StroStockDTO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.module.stro.stock.service.StroStockService;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author ljh
 * @date 2020/07/27.
 */
@Slf4j
@HsafRestPath("/web/stro/cxstrostock")
@Service("stroStockService_provider")
public class StroStockServiceImpl extends HsafBO implements StroStockService {

    @Resource
    private StroStockBO stroStockBO;


    @HsafRestPath(value = "/queryById", method = RequestMethod.GET)
    @Override
    public WrapperResponse<StroStockDTO> queryById(Map map) {

        return WrapperResponse.success(stroStockBO.queryById((MapUtils.get(map, "id")), (MapUtils.get(map, "hospCode"))));
    }


    @HsafRestPath(value = "/queryAll", method = RequestMethod.GET)
    @Override
    public WrapperResponse<List<StroStockDTO>> queryAll(Map map) {
        return WrapperResponse.success(stroStockBO.queryAll(MapUtils.get(map,"bean")));
    }

    @HsafRestPath(value = "/queryPage", method = RequestMethod.GET)
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        return WrapperResponse.success(stroStockBO.queryPage(MapUtils.get(map,"bean")));
    }

    @HsafRestPath(value = "/queryPageDetail", method = RequestMethod.GET)
    @Override
    public WrapperResponse<PageDTO> queryPageDetail(Map map) {
        return WrapperResponse.success(stroStockBO.queryPageDetail(MapUtils.get(map,"bean")));
    }

    @HsafRestPath(value = "/queryPageDetailCancleNo", method = RequestMethod.GET)
    @Override
    public WrapperResponse<PageDTO> queryPageDetailCancleNo(Map map) {
        return WrapperResponse.success(stroStockBO.queryPageDetailCancleNo(MapUtils.get(map,"bean")));
    }

    /**
     * @Method: queryStockDetails
     * @Description:分页获取库存明细
     * @Param: [stroStockDetail]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/5 14:30
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @HsafRestPath(value = "/queryStockDetails", method = RequestMethod.GET)
    @Override
    public WrapperResponse<PageDTO> queryStockDetails(Map map) {
        return WrapperResponse.success(stroStockBO.queryStockDetails(MapUtils.get(map,"bean")));
    }


    @HsafRestPath(value = "/queryAllDetail", method = RequestMethod.GET)
    @Override
    public WrapperResponse<List<StroStockDetailDTO>> queryAllDetail(Map map) {
        return WrapperResponse.success(stroStockBO.queryAllDetail(MapUtils.get(map,"bean")));
    }
    @HsafRestPath(value = "/update", method = RequestMethod.POST)
    @Override
    public WrapperResponse<Boolean> update(Map map) {
        return WrapperResponse.success(stroStockBO.update(MapUtils.get(map,"bean"))>0);
    }

    /**
     * 保存库存信息
     * @param map map {hospCode：医院编码 type: 1:采购入库 2：直接入库 3：退供应商 4：出库到科室 5：出库到药房 6：药房退库 7：盘盈盘亏 8：报损报溢 9：同级调拨（库房） 10：同级调拨（药房）
     *      sfBatchNo ：是否按批次盘点
     *      stroStockDetailDTOList: 库存明细 + 拆零单位、规格、剂型、库位码、订单号、拆零购进价、创建人、创建人ID
     *   }
     * @return
     */
    @HsafRestPath(value = "/saveStock", method = RequestMethod.POST)
    @Override
    public WrapperResponse<Boolean> saveStock(Map map) {
        return WrapperResponse.success(!ListUtils.isEmpty(stroStockBO.saveStock(map)));
    }

    /**
     * @Method: updateStockOccupy
     * @Description: 占用库存或者解除占用的库存数据(stockNum为负数)
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/23 8:58
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @HsafRestPath(value = "/updateStockOccupy", method = RequestMethod.POST)
    @Override
    public WrapperResponse<Boolean> updateStockOccupy(Map map) {
        return WrapperResponse.success(stroStockBO.updateStockOccupy(MapUtils.get(map,"pharOutReceiveDetailDOList")));
    }

    /**
     * @param map
     * @Method: updateOccupy
     * @Descrition: 库存过期更新对应的药品、材料的占用库存数据
     * @Pramas: param
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/11/29
     * @Retrun: null
     */
    @Override
    public WrapperResponse<Boolean> updateOccupy(Map map) {
        String hospCode = MapUtils.get(map, "hospCode");
        return WrapperResponse.success(stroStockBO.updateOccupy(hospCode));
    }

    /**
    * @Menthod inserStockByTime
    * @Desrciption 月底库存存储记录
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/12/13 16:16
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> insertStockByTime(Map map) {
      StroStockDTO stroStockDTO = MapUtils.get(map, "stroStockDTO");
      return WrapperResponse.success(stroStockBO.insertStockByTime(stroStockDTO));
    }

  /**库存效期查询
     * @Method queryValidityWarningPage
     * @Desrciption
     * @param map
     * @Author liuqi1
     * @Date   2021/4/22 14:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryValidityWarningPage(Map map) {
        return WrapperResponse.success(stroStockBO.queryValidityWarningPage(MapUtils.get(map,"bean")));
    }

    /**
    * @Menthod queryStockByBatchAll
    * @Desrciption 查询库存明细中批号汇总
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/6/2 14:50
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryStockByBatchAll(Map map) {
      return WrapperResponse.success(stroStockBO.queryStockByBatchAll(MapUtils.get(map,"stroStockDetailDTO")));
    }
    /**
     * @Method queryPageByOutptOrInpt
     * @Desrciption 提供给门诊医生站、住院医生站的药品查询接口
     * @Param [stroStockDTO]
     * @Author zhangguorui
     * @Date   2021/7/16 14:31
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     */
    @Override
    public WrapperResponse<PageDTO> queryPageByOutptOrInpt(Map map) {
        return WrapperResponse.success(stroStockBO.queryPageByOutptOrInpt(MapUtils.get(map,"stroStockDTO")));
    }

    /**
     * @Meth: queryAllStockPage
     * @Description: 查询全院库存
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/26
     */
    @Override
    public WrapperResponse<PageDTO> queryAllStockPage(Map map) {
        return WrapperResponse.success(stroStockBO.queryAllStockPage(MapUtils.get(map,"stroStockDTO")));
    }
    /**
     * @Meth: queryAllStockDetails
     * @Description: 查询全部库存明细
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/26
     */
    @Override
    public WrapperResponse<PageDTO> queryAllStockDetails(Map map) {
        return WrapperResponse.success(stroStockBO.queryAllStockDetails(MapUtils.get(map,"stroStockDetail")));
    }

    /**
     * 查询药品和材料的利润统计信息
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<PageDTO> queryDrugAndMaterialProfit(Map map) {
        return WrapperResponse.success(stroStockBO.queryDrugAndMaterialProfit(MapUtils.get(map,"itemProfitStatisticsDTO")));

    }

    /**
     * @Author gory
     * @Description 同步占用库存
     * @Date 2022/9/6 17:29
     * @Param [map]
     **/
    @Override
    public WrapperResponse<Boolean> updateOccupyByExpire(Map map) {
        return WrapperResponse.success(stroStockBO.updateOccupyByExpire(MapUtils.get(map,"hospCode")));
    }


}
