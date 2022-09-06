package cn.hsa.module.stro.stock.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.stro.stock.dto.ItemProfitStatisticsDTO;
import cn.hsa.module.stro.stock.dto.StroStockDTO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_ame: cn.hsa.module.stro.stock.bo
 * @Class_name: StroStockBO
 * @Description:  药库查询
 * @Author: ljh
 * @Date: 2020/7/30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface StroStockBO {

    /**
     * @Method getById()
     * @Description 通过ID查询单条数据
     *
     * @Param
     * 1、id：主键ID
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return StroStockDTO>
     **/
    StroStockDTO queryById(String id, String hospCode);

    /**
    * @Menthod getStroStockDetailById
    * @Desrciption  通过id查询单个库存详细
     * @param id
     * @param hospCode
    * @Author xingyu.xie
    * @Date   2020/10/13 18:29
    * @Return cn.hsa.module.stro.stock.dto.StroStockDetailDTO
    **/
    public StroStockDetailDTO getStroStockDetailById(String id, String hospCode);

    /**
     * @Method queryAll()
     * @Description 通过实体作为筛选条件查询
     *
     * @Param
     * cxStroStockDTO 实例对象
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return List<StroStockDTO>>
     **/
    List<StroStockDTO> queryAll(StroStockDTO cxStroStockDTO);

    /**
     * @Method queryPage()
     * @Description 通过实体作为筛选条件分页查询
     *
     * @Param
     * cxStroStockDTO 实例对象
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return PageDTO>
     **/
    PageDTO queryPage(StroStockDTO cxStroStockDTO);

    /**
     * @Method queryPage()
     * @Description 通过实体作为筛选条件分页查询
     *
     * @Param
     * StroStockDetailDTO 实例对象
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return PageDTO>
     **/
    PageDTO queryPageDetail(StroStockDetailDTO stroStockDetail);

    PageDTO queryPageDetailCancleNo(StroStockDetailDTO stroStockDetail);

    /**
     * @Method: queryStockDetails
     * @Description: 分页获取库存明细
     * @Param: [stroStockDetail]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/5 14:26
     * @Return: java.util.List<cn.hsa.module.stro.stock.dto.StroStockDetailDTO>
     **/
    PageDTO queryStockDetails(StroStockDetailDTO stroStockDetail);

    /**
     * @Method queryAllDetail()
     * @Description 通过实体作为筛选条件查询
     *
     * @Param
     * StroStockDetailDTO 实例对象
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return List<StroStockDetailDTO>
     **/
    public List<StroStockDetailDTO> queryAllDetail(StroStockDetailDTO stroStockDetail);

    /**
     * @Method update()
     * @Description 更新
     *
     * @Param
     * StroStockDetailDTO 实例对象
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return  int
     **/
    int update(StroStockDTO stroStockDTO);


    /**
     * 保存库存信息
     * @param map map {hospCode：医院编码 type: 1:药库入库 2：出库到药房 3：出库到科室 4：药房入库 5：药房退库 6：药库退供应商 7：盘盈盘亏 8：报损报溢 9：调拨出库 10：药房出库
     *      sfBatchNo ：是否按批次盘点
     *      stroStockDetailDTOList: 库存明细 + 拆零单位、规格、剂型、库位码、订单号、拆零购进价、创建人、创建人ID
     *   }
     * @return
     */
    List<StroInvoicingDTO> saveStock(Map map);

    /**
     * @Method: getStroStock
     * @Description: 校验库存
     * @Param: 参数：itemId:项目ID,itemCode:项目编码,stockNum:需要占用库存数量,hospCode:医院编码,bizId:库位ID
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/12 17:14
     * @Return: WrapperResponse<Boolean>
     **/
    Boolean getStroStock(Map map);

    /**
     * @Method: updateStock
     * @Description: 占用库存或者解除占用的库存数据(stockNum为负数)
     * @Param: itemId:项目ID,itemCode:项目编码,stockNum:需要占用库存数量,hospCode:医院编码,bizId:库位ID
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/12 17:43
     * @Return: int
     **/
    Boolean updateStockOccupy(List<Map<String,Object>> pharOutReceiveDetailDOList);

    /**
     * @Menthod checkPar
     * @Desrciption 检查参数是否为空
     * @param map {hospCode：医院编码  stroStockDetailDTOList: 库存明细}
     * @Author zengfeng
     * @Date   2020/8/5 9:12
     * @Return 是否成功
     **/
    boolean checkPar(Map map);

    /**
     * @param
     * @Method: updateOccupy
     * @Descrition: 库存过期更新对应的药品、材料的占用库存数据
     * @Pramas: param
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/11/29
     * @Retrun: null
     */
    boolean updateOccupy(String hospCode);

    /**
    * @Menthod inserStockByTime
    * @Desrciption 月底库存存储记录
    *
    * @Param
    * [hospCode]
    *
    * @Author jiahong.yang
    * @Date   2021/12/13 16:15
    * @Return boolean
    **/
    boolean insertStockByTime(StroStockDTO stroStockDTO);

    /**库存效期查询
    * @Method queryValidityWarningPage
    * @Desrciption
    * @param stroStockDTO
    * @Author liuqi1
    * @Date   2021/4/22 14:51
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryValidityWarningPage(StroStockDTO stroStockDTO);

    /**
    * @Menthod queryStockByBatchAll
    * @Desrciption 查询库存明细中批号汇总
    *
    * @Param
    * [stroStockDetailDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/6/2 14:50
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryStockByBatchAll(StroStockDetailDTO stroStockDetailDTO);
    /**
     * @Method queryPageByOutptOrInpt
     * @Desrciption 提供给门诊医生站、住院医生站的药品查询接口
     * @Param [stroStockDTO]
     * @Author zhangguorui
     * @Date   2021/7/16 14:43
     * @Return cn.hsa.base.PageDTO
     */
    PageDTO queryPageByOutptOrInpt(StroStockDTO stroStockDTO);
    /**
     * @Meth: queryAllStockPage
     * @Description: 查询全院库存
     * @Param: [stroStockDTO]
     * @return: cn.hsa.base.PageDTO
     * @Author: zhangguorui
     * @Date: 2021/9/26
     */
    PageDTO queryAllStockPage(StroStockDTO stroStockDTO);
    /**
     * @Meth: queryAllStockDetails
     * @Description: 查询全部库存明细
     * @Param: [stroStockDTO]
     * @return: cn.hsa.base.PageDTO
     * @Author: zhangguorui
     * @Date: 2021/9/26
     */
    PageDTO queryAllStockDetails(StroStockDetailDTO stroStockDetail);

    /**
     * 统计药品和材料的利润信息
     * @param itemProfitStatisticsDTO
     * @return
     */
    PageDTO queryDrugAndMaterialProfit(ItemProfitStatisticsDTO itemProfitStatisticsDTO);
    /**
     * @Author gory
     * @Description 查询出库存不足 或者 过期的药品
     * @Date 2022/8/31 16:36
     * @Param [stringObjectMap]
     **/
    List<StroStockDetailDTO> getStroStockDetailIfNumShortage(Map map);
}
