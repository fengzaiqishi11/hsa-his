package cn.hsa.module.stro.stroin.dao;

import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.module.stro.stroin.dto.StroInDTO;
import cn.hsa.module.stro.stroin.dto.StroInDetailDTO;
import cn.hsa.module.stro.stroin.dto.StroInRecordDTO;
import cn.hsa.module.stro.stroout.dto.StroOutDTO;
import cn.hsa.module.stro.stroout.dto.StroOutDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.store.instore.dao
 * @Class_name: StroInDao
 * @Describe: 入库数据接口层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/21 20:39
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface StroInDAO {

    /**
     * @Menthod getById()
     * @Desrciption 根据主键和医院编码查询入库信息
     * @Param [1. stroInDTO]
     * @Author jiahong.yang
     * @Date 2020/7/25 14:15
     * @Return cn.hsa.module.stro.outinstro.dto.StroInDTO
     **/
    StroInDTO getById(StroInDTO stroInDTO);

    /**
     * @Menthod queryStroInPage()
     * @Desrciption 按条件查询药品入库信息
     * @Param [1. stroInDTO]
     * @Author jiahong.yang
     * @Date 2020/7/23 15:16
     * @Return java.util.List<cn.hsa.module.stro.comm.dto.StroInDTO>
     **/
    List<StroInDTO> queryStroInPage(StroInDTO stroInDTO);

    /**
     * @Menthod isExitAudit
     * @Desrciption 判断传过来的数据是否有已经审核和作废的
     * @Param [stroInDTO]
     * @Author jiahong.yang
     * @Date 2020/9/2 11:32
     * @Return java.lang.Integer
     **/
    Integer isExitAudit(StroInDTO stroInDTO);

    /**
     * @Menthod queryAll()
     * @Desrciption 查询全部药品入库信息
     * @Param [1. stroInDTO]
     * @Author jiahong.yang
     * @Date 2020/7/23 15:19
     * @Return java.util.List<cn.hsa.module.stro.comm.dto.StroInDTO>
     **/
    List<StroInDTO> queryAll(StroInDTO stroInDTO);

    /**
     * @Menthod insert()
     * @Desrciption 插入一条入库单据信息
     * @Param [1. btroStroInDTO]
     * @Author jiahong.yang
     * @Date 2020/7/25 13:52
     * @Return java.lang.Integer
     **/
    Integer insertStroIn(StroInDTO stroInDTO);

    /**
     * @Menthod update()
     * @Desrciption 修改一条入库单据信息
     * @Param [stroInDTO]
     * @Author jiahong.yang
     * @Date 2020/7/25 13:54
     * @Return java.lang.Integer
     **/
    Integer updateStroIn(StroInDTO stroInDTO);

    /**
     * @Menthod updateAuditCode
     * @Desrciption 批量审核或者批量作废
     * @Param [1. stroInDTO]
     * @Author jiahong.yang
     * @Date 2020/7/27 17:25
     * @Return java.lang.Integer
     **/
    Integer updateAuditCode(StroInDTO stroInDTO);

    /**
     * @Menthod queryStroInDetailAll()
     * @Desrciption 根据条件查询所有入库明细信息
     * @Param [stroStroInDetailDTO]
     * @Author jiahong.yang
     * @Date 2020/7/27 19:21
     * @Return java.util.List<cn.hsa.module.stro.outinstro.dto.StroInDetailDTO>
     **/
    List<StroInDetailDTO> queryStroInDetailAll(StroInDetailDTO stroStroInDetailDTO);

    /**
     * @Menthod queryStroInDetailByOrder
     * @Desrciption
     * @Param [stroStroInDetailDTO]
     * @Author jiahong.yang
     * @Date 2020/8/10 13:35
     * @Return java.util.List<cn.hsa.module.stro.outinstro.dto.StroInDetailDTO>
     **/
    List<StroStockDetailDTO> queryStroInDetailByOrder(StroInDTO stroInDTO);

    /**
    * @Menthod queryStroInDetailByOrder1
    * @Desrciption 用于退供应商和评级出库
    *
    * @Param
    * [stroInDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/14 14:53
    * @Return java.util.List<cn.hsa.module.stro.stock.dto.StroStockDetailDTO>
    **/
    List<StroStockDetailDTO> queryStroInDetailByOrderOut(StroInDTO stroInDTO);

    /**
     * @Menthod insertStroInDetail()
     * @Desrciption 批量插入入库明细
     * @Param [stroStroInDetailDTOS]
     * @Author jiahong.yang
     * @Date 2020/7/27 19:22
     * @Return java.lang.Integer
     **/
    Integer insertStroInDetail(@Param("list") List<StroInDetailDTO> stroStroInDetailDTOS);

    /**
     * @Menthod updateStroInDetail()
     * @Desrciption 批量更新入库明细
     * @Param [1. stroStroInDetailDTOS]
     * @Author jiahong.yang
     * @Date 2020/7/27 19:23
     * @Return java.lang.Integer
     **/
    Integer updateStroInDetail(@Param("list") List<StroInDetailDTO> stroStroInDetailDTOS);

    /**
     * @Menthod deleteStroInDetail()
     * @Desrciption 批量删除入库明细
     * @Param [stroStroInDetailDTOS]
     * @Author jiahong.yang
     * @Date 2020/7/28 17:29
     * @Return java.lang.Integer
     **/
    Integer deleteStroInDetail(StroInDTO stroInDTO);

    /**
     * @Menthod queryDrugPage()
     * @Desrciption 获取全部药品填充下拉表单
     * @Param [1. BaseDrugDTO]
     * @Author jiahong.yang
     * @Date 2020/7/29 17:34
     * @Return java.util.List<cn.hsa.module.base.drug.dto.BaseDrugDTO>
     **/
    List<BaseDrugDTO> queryDrugPage(BaseDrugDTO BaseDrugDTO);

    /**
     * @Menthod queryMaterialPage()
     * @Desrciption 获取全部材料填充下拉表单
     * @Param [1. material]
     * @Author jiahong.yang
     * @Date 2020/7/29 17:38
     * @Return java.util.List<javafx.scene.paint.Material>
     **/
    List<BaseDrugDTO> queryMaterialPage(BaseMaterialDTO baseMaterialDTO);


    List<BaseDrugDTO> queryMandB(BaseDrugDTO baseDrugDTO);

    /**
     * @Method insertStroDetail
     * @Desrciption 批量插入入库明细(仅限同级调拨使用)
     * @Param [stroOutDetailDTOS]
     * @Author liaojunjie
     * @Date 2020/11/2 14:50
     * @Return java.lang.Integer
     **/
    Integer insertStroDetail(@Param("list") List<StroOutDetailDTO> stroOutDetailDTOS);

    /**
     * @Method insertStro
     * @Desrciption 插入单条入库信息(仅限同级调拨使用)
     * @Param [stroOutDTO]
     * @Author liaojunjie
     * @Date 2020/11/2 14:51
     * @Return java.lang.Integer
     **/
    Integer insertStro(StroOutDTO stroOutDTO);

    List<StroInDetailDTO> queryStroInDetailAllByOrderNo(StroInDetailDTO stroStroInDetailDTO);

    StroInRecordDTO queryStroInRecord(StroInRecordDTO stroInRecordDTO);
    /**
     * @Meth: queryStroInPageForExprot
     * @Description: 查询入库单
     * @Param: [stroInDetailDTO]
     * @return: java.util.List<cn.hsa.module.stro.stroin.dto.StroInDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/17
     */
    List<StroInDTO> queryStroInPageForExprot(StroInDetailDTO stroInDetailDTO);
    /**
     * @Meth:queryStroinDetailForExprot
     * @Description: 批量查询明细为了 批量导出
     * @Param: [stroInDetailDTO]
     * @return: java.util.List<cn.hsa.module.stro.stroin.dto.StroInDetailDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/17
     */
    List<StroInDetailDTO> queryStroinDetailForExprot(StroInDetailDTO stroInDetailDTO);
    /**
     * @param stroInDTO
     * @Menthod updateStroInFk()
     * @Desrciption 修改财务付款状态
     * @Param [baseDrugDTO]
     * @Author pengbo
     * @Date 2022/04/19 17:36
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     */
    int updateStroInFk(StroInDTO stroInDTO);
}
