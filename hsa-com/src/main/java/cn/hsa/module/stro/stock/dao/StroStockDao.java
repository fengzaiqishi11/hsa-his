package cn.hsa.module.stro.stock.dao;


import cn.hsa.module.stro.stock.dto.StroStockDTO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.module.stro.stroin.dto.StroInRecordDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * @Package_name: cn.hsa.module.stro.stock.dao
 * @Class_name:: StroStockDao
 * @Description:
 * @Author: ljh
 * @Date: 2020/8/12 11:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface StroStockDao {

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
    StroStockDTO queryById(String id, String hospCode);



    /**
     * @Method queryAll()
     * @Description 联药品表查询
     *
     * @Param
     * cxStroStockDTO 实例对象
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<List<StroStockDTO>>
     **/
    List<StroStockDTO> queryAlldrug(StroStockDTO cxStroStockDTO);

    /**
     * @Package_name: cn.hsa.module.stro.stock.dao
     * @Class_name:: StroStockDao
     * @Description: 联材料表查询
     * @Author: ljh
     * @Date: 2020/9/10 10:45
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    List<StroStockDTO> queryAllmaterial(StroStockDTO cxStroStockDTO);

    List<StroStockDTO> queryAll(StroStockDTO cxStroStockDTO);

    /**
    * @Method: updateMaxMin
    * @Description: 修改库存上限下限
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/10/13 9:56
    * @Return:
    **/
    int updateMaxMin(StroStockDTO stroStockDTO);

    /**
     * @Menthod getStock
     * @Desrciption 获取项目库存信息
     * @param stroStockDetailDTO  库存明细表
     * @Author zengfeng
     * @Date   2020/8/4 16:05
     * @Return
     **/
    StroStockDTO getStock(StroStockDetailDTO stroStockDetailDTO);

    /**
     * @Menthod getStockDetail
     * @Desrciption 获取项目库存明细数据
     * @param stroStockDetailDTO  库存明细表
     * @Author zengfeng
     * @Date   2020/8/4 16:05
     * @Return
     **/
    List<StroStockDetailDTO>  getStockDetail(StroStockDetailDTO stroStockDetailDTO);

    /**
    * @Menthod getStockDetail1
    * @Desrciption 用于盘点
    *
    * @Param
    * [stroStockDetailDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/4/29 20:41
    * @Return java.util.List<cn.hsa.module.stro.stock.dto.StroStockDetailDTO>
    **/
    List<StroStockDetailDTO>  getStockDetail1(StroStockDetailDTO stroStockDetailDTO);

    /**
    * @Method: getStockDetail
    * @Description: 获取项目库存明细数据
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/12/3 20:30
    * @Return:
    **/
    StroStockDetailDTO  getStockDetails(StroStockDetailDTO stroStockDetailDTO);

    /**
     * @Menthod getStockDetailBatchNoNum
     * @Desrciption 获取项目或者材料库存明细批次数量
     * @param stroStockDetailDTO  库存明细表
     * @Author zengfeng
     * @Date   2020/8/4 16:05
     * @Return
     **/
    StroStockDetailDTO  getStockDetailBatchNoNum(StroStockDetailDTO stroStockDetailDTO);

    /**
     * @Menthod insertStock
     * @Desrciption 插入库存主表
     * @param stroStockDTOList  库存主表
     * @Author zengfeng
     * @Date   2020/8/4 16:05
     * @Return
     **/
    int insertStock(List<StroStockDTO> stroStockDTOList);

    /**
     * @Menthod updateStock
     * @Desrciption 更新库存信息
     * @param stroStockDTOList  库存主表
     * @Author zengfeng
     * @Date   2020/8/4 16:05
     * @Return
     **/
    int updateStock(List<StroStockDTO> stroStockDTOList);

    /**
     * @Menthod insertStockDetail
     * @Desrciption 保存库存明细
     * @param stroStockDetailDTOList  库存明细表
     * @Author zengfeng
     * @Date   2020/8/4 16:05
     * @Return
     **/
    int insertStockDetail(List<StroStockDetailDTO> stroStockDetailDTOList);

    /**
     * @Menthod updateStockDetail
     * @Desrciption 更新库存明细数据
     * @param stroStockDetailDTOList  库存明细表
     * @Author zengfeng
     * @Date   2020/8/4 16:05
     * @Return
     **/
    int updateStockDetail(List<StroStockDetailDTO> stroStockDetailDTOList);

    /**
     * @Method: getStroStock
     * @Description: 校验库存
     * @Param: 参数：itemId:项目ID,itemCode:项目编码,stockNum:需要占用库存数量
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/12 17:14
     * @Return: int
     **/
    Integer getStroStock(Map map);

    /**
    * @Method: updateStock
    * @Description: 占用库存或者解除占用的库存数据(stockNum为负数)
    * @Param: itemId:项目ID,itemCode:项目编码,stockNum:需要占用库存数量,hospCode:医院编码,bizId:库位ID
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/9/12 17:43
    * @Return: int
    **/
    int updateStockOccupy(List<Map<String,Object>> list);

    /**
     * @Description: 更新库存的占用库存数量
     *
     * @Param:
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/11/27 15:32
     * @Return: booleans
     */
    Boolean updateOccupy(@Param("stockDetailDTOList") List<StroStockDetailDTO> stockDetailDTOList);

    void updateStockByDetail(@Param("stroStockDetailDTOList") List<StroStockDetailDTO> stroStockDetailDTOList);

    /**
    * @Menthod insertStroStock
    * @Desrciption 新增入库记录
    *
    * @Param
    * [stroInRecordDtO]
    *
    * @Author jiahong.yang
    * @Date   2021/3/12 14:06
    * @Return int
    **/
    Integer insertStroStock(@Param("list") List<StroInRecordDTO> stroInRecordDtO);

    /**库存效期查询
    * @Method queryValidityWarningPage
    * @Desrciption
    * @param stroStockDTO
    * @Author liuqi1
    * @Date   2021/4/22 14:58
    * @Return java.util.List<cn.hsa.module.stro.stock.dto.StroStockDTO>
    **/
    List<StroStockDTO> queryValidityWarningPage(StroStockDTO stroStockDTO);

    /**
    * @Menthod queryStoclDetailByItemIds
    * @Desrciption 根据项目id,库位,批号分组查询库存明细
    *
    * @Param
    * [stroStockDetailDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/7 10:25
    * @Return java.util.List<cn.hsa.module.stro.stock.dto.StroStockDetailDTO>
    **/
    List<StroStockDetailDTO> queryStoclDetailByItemIds(StroStockDetailDTO stroStockDetailDTO);

    /**
    * @Menthod queryStockByBatchAll
    * @Desrciption 查询库存明细中批号汇总
    *
    * @Param
    * [stroStockDetailDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/6/2 14:54
    * @Return java.util.List<java.util.Map>
    **/
    List<StroStockDetailDTO> queryStockByBatchAll(StroStockDetailDTO stroStockDetailDTO);
    /**
     * @Method getTypeIdentityByBizId
     * @Desrciption 通过药房id获得药品大类
     * @Param [bizId]
     * @Author zhangguorui
     * @Date   2021/7/17 11:19
     * @Return java.lang.String
     */
    String getTypeIdentityByBizId(@Param("bizId") String bizId,@Param("hospCode") String hospCode);

    /**
    * @Menthod queryStockAllTime
    * @Desrciption 查询所有库存
    *
    * @Param
    * [stroStockDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/12/13 16:21
    * @Return
    **/
    List<StroStockDTO> queryStockAll(StroStockDTO stroStockDTO);

    /**
    * @Menthod queryStockTimeAll
    * @Desrciption 查询库存时间节点库存
    *
    * @Param
    * [stroStockDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/12/13 17:09
    * @Return java.util.List<cn.hsa.module.stro.stock.dto.StroStockDTO>
    **/
    List<StroStockDTO> queryStockTimeAll(StroStockDTO stroStockDTO);

    /**
    * @Menthod insertStockTime
    * @Desrciption
    *
    * @Param
    * [stroStockDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/12/14 10:48
    * @Return int
    **/
    int insertStockTime(@Param("list") List<StroStockDTO> stroStockDTOS);

    List<StroStockDetailDTO> getStroStockDetailIfNumShortage(Map map);
}
