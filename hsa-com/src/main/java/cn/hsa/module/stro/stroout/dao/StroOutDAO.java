package cn.hsa.module.stro.stroout.dao;


import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.module.stro.stroin.dto.StroInDetailDTO;
import cn.hsa.module.stro.stroout.dto.StroOutDTO;
import cn.hsa.module.stro.stroout.dto.StroOutDetailDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.stro.outinStro.stroOut.dao
 * @Class_name:: StroOutinDao
 * @Description:  出库数据访问层接口
 * @Author: liaojunjie
 * @Date: 2020/7/20 17:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface StroOutDAO {

    /**
     * @Method getById
     * @Desrciption 通过id查询信息
     * @Param
     * [stroOutinDTO]
     * @Author liaojunjie
     * @Date   2020/7/30 19:22
     * @Return cn.hsa.module.stro.outinstro.dto.StroOutinDTO
     **/
    StroOutDTO getById(StroOutDTO stroOutDTO);

    /**
     * @Method queryByIds
     * @Desrciption 通过多个ID查询多个信息
     * @Param
     * [stroOutDTO]
     * @Author liaojunjie
     * @Date   2020/8/30 15:51
     * @Return java.util.List<cn.hsa.module.stro.stroout.dto.StroOutDTO>
     **/
    List<StroOutDTO>queryByIds(StroOutDTO stroOutDTO);

    /**
     * @Method queryPage
     * @Desrciption 分页查询所有出库单信息
     * @Param
     * [stroOutinDTO]
     * @Author liaojunjie
     * @Date   2020/7/30 19:22
     * @Return java.util.List<cn.hsa.module.stro.outinstro.dto.StroOutinDTO>
     **/
    List<StroOutDTO> queryPage(StroOutDTO stroOutDTO);

    /**
     * @Method queryAll
     * @Desrciption 查询所有出库单信息
     * @Param
     * [stroOutinDTO]
     * @Author liaojunjie
     * @Date   2020/7/30 19:22
     * @Return java.util.List<cn.hsa.module.stro.outinstro.dto.StroOutDTO>
     **/
    List<StroOutDTO> queryAll(StroOutDTO stroOutDTO);

    /**
     * @Method queryAllDetails
     * @Desrciption 查询所有出库明细信息
     * @Param
     * [stroOutinDTO]
     * @Author liaojunjie
     * @Date   2020/7/30 19:22
     * @Return java.util.List<cn.hsa.module.stro.outinstro.dto.StroOutDetailDTO>
     **/
    List<StroOutDetailDTO>queryAllDetails (StroOutDTO stroOutDTO);

    /**
     * @Method insert
     * @Desrciption   插入新的出库记录
     * @Param
     * [stroOutinDTO]
     * @Author liaojunjie
     * @Date   2020/7/30 19:23
     * @Return java.lang.Integer
     **/
    Integer insert(StroOutDTO stroOutDTO);

    /**
     * @Method insertDetails
     * @Desrciption 插入新的出库明细记录(需要提前遍历插入单据号)
     * @Param
     * [stroOutinDetaiDTOS]
     * @Author liaojunjie
     * @Date   2020/7/30 19:23
     * @Return java.lang.Integer
     **/
    Integer insertDetails(List<StroOutDetailDTO> stroOutDetailDTOS);

    /**
     * @Method update
     * @Desrciption 修改信息
     * @Param
     * [btroOutinDTO]
     * @Author liaojunjie
     * @Date   2020/7/30 19:23
     * @Return java.lang.Integer
     **/
    Integer update(StroOutDTO stroOutDTO);

    /**
     * @Method updateOutinDetail
     * @Desrciption 修改出库明细信息
     * @Param
     * [stroOutinDetaiDTOS]
     * @Author liaojunjie
     * @Date   2020/7/30 19:24
     * @Return java.lang.Integer
     **/
    Integer updateDetail(List<StroOutDetailDTO> stroOutDetailDTOS);

    /**
     * @Method updateAuditCode
     * @Desrciption 修改审核标识（0未审核，1已审核，2作废）
     * @Param
     * [stroOutinDTO]
     * @Author liaojunjie
     * @Date   2020/7/30 19:24
     * @Return java.lang.Integer
     **/
    Integer updateAuditCode(StroOutDTO stroOutDTO);

    /**
     * @Method deleteOutinDetail
     * @Desrciption 批量删除出库明细信息
     * @Param
     * [stroOutinDTO]
     * @Author liaojunjie
     * @Date   2020/7/30 19:24
     * @Return java.lang.Integer
     **/
    Integer deleteOutDetail(StroOutDTO stroOutDTO);

    /**
     * @Method deleteByStroOut
     * @Desrciption 删除明细通过主表id
     * @Param
     * [stroOutDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 15:17
     * @Return java.lang.Integer
     **/
    Integer deleteByStroOut(StroOutDTO stroOutDTO);

//    /**
//     * @Method queryDrugStock
//     * @Desrciption  查询药品库存
//     * @Param
//     * [stroStockDTO]
//     * @Author liaojunjie
//     * @Date   2020/8/12 15:59
//     * @Return java.util.List<cn.hsa.module.stro.stock.dto.StroStockDTO>
//     **/
//    List<StroStockDetailDTO> queryDrugStock(StroOutDTO stroOutDTO);
//
//    /**
//     * @Method queryMaterialStock
//     * @Desrciption  查询材料库存
//     * @Param
//     * [stroStockDTO]
//     * @Author liaojunjie
//     * @Date   2020/8/12 15:59
//     * @Return java.util.List<cn.hsa.module.stro.stock.dto.StroStockDTO>
//     **/
//    List<StroStockDTO> queryMaterialStock(StroOutDTO stroOutDTO);

    /**
     * @Method queryStockByIds
     * @Desrciption 通过ids查询库存
     * @Param
     * [stroOutinDTO]
     * @Author liaojunjie
     * @Date   2020/8/12 16:10
     * @Return java.util.List<cn.hsa.module.stro.stock.dto.StroStockDTO>
     **/
    List<StroStockDetailDTO> queryStockByIds(StroOutDTO stroOutDTO);

    /**
     * @Method queryIsAuditNo
     * @Desrciption 查询是否存在已审核的整单出库单号
     * @Param
     * [stroOutDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 10:46
     * @Return java.lang.Integer
     **/
    Integer queryIsAuditNo(StroOutDTO stroOutDTO);

    Integer queryIsAuditNo1(StroOutDTO stroOutDTO);

    /**
     * @Method queryStroOutNoDetail
     * @Desrciption 根据出库单查库存明细
     * @Param
     * [stroInDetailDTOS]
     * @Author liaojunjie
     * @Date   2020/12/16 10:46
     * @Return java.util.List<cn.hsa.module.stro.stock.dto.StroStockDetailDTO>
     **/
    List<StroStockDetailDTO> queryStroOutNoDetail(List<StroInDetailDTO> stroInDetailDTOS);

    List<StroStockDetailDTO> queryDetailStock(List<StroOutDetailDTO> stroOutDetailDTOS);
    /**
     * @Method queryStroOutNoDetails
     * @Desrciption 根据出库单查库存明细
     * @Param
     * [stroOutDetailDTOS]
     * @Author liaojunjie
     * @Date   2020/12/16 10:47
     * @Return java.util.List<cn.hsa.module.stro.stock.dto.StroStockDetailDTO>
     **/
    List<StroStockDetailDTO> queryStroOutNoDetails(List<StroOutDetailDTO> stroOutDetailDTOS);

    /**
    * @Menthod insertStroOutAll
    * @Desrciption 批量新增
    *
    * @Param
    * [stroOutDTOS]
    *
    * @Author jiahong.yang
    * @Date   2021/5/12 10:56
    * @Return java.lang.Boolean
    **/
    Boolean insertStroOutAll(List<StroOutDTO> stroOutDTOS);
    /**
     * @Meth: queryStroOutDetail
     * @Description: 通过出库id查询出库明细单
     * @Param: [stroOutDTO]
     * @return: java.util.List<cn.hsa.module.stro.stroout.dto.StroOutDetailDTO>
     * @Author: zhangguorui
     * @Date: 2021/12/14
     */
    List<StroOutDetailDTO> queryStroOutDetail(StroOutDetailDTO stroOutDetailDTO);
}
