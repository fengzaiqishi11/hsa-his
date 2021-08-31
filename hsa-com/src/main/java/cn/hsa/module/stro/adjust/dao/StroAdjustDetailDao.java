package cn.hsa.module.stro.adjust.dao;

import cn.hsa.module.stro.adjust.dto.StroAdjustDTO;
import cn.hsa.module.stro.adjust.dto.StroAdjustDetailDTO;
import cn.hsa.module.stro.stock.dto.StroStockDTO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *@Package_name: cn.hsa.module.stro.adjust.dao
 *@Class_name: StroAdjustDao
 *@Describe: 药品调价明细
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020-07-31 14:56:01
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface StroAdjustDetailDao {

    /**
    * @Method queryStroAdjustDetailDtoById
    * @Desrciption 单个查询
    * @param stroAdjustDetailDTO
    * @Author liuqi1
    * @Date   2020/8/2 8:45
    * @Return cn.hsa.module.stro.adjust.dto.StroAdjustDetailDTO
    **/
    StroAdjustDetailDTO queryStroAdjustDetailDtoById(StroAdjustDetailDTO stroAdjustDetailDTO);

    /**
    * @Method queryStroAdjustDetailDtoPage
    * @Desrciption 根据单据id分页查询
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/2 8:46
    * @Return java.util.List<cn.hsa.module.stro.adjust.dto.StroAdjustDetailDTO>
    **/
    List<StroAdjustDetailDTO> queryStroAdjustDetailDtoPage(StroAdjustDTO stroAdjustDTO);

    /**
    * @Method insertStroAdjustDetailDTO
    * @Desrciption 批量新增
    * @param stroAdjustDetailDTOs
    * @Author liuqi1
    * @Date   2020/8/2 8:46
    * @Return int
    **/
    int insertStroAdjustDetailDTO(@Param("list") List<StroAdjustDetailDTO> stroAdjustDetailDTOs);

    /**
    * @Method deleteStroAdjustDetailDTO
    * @Desrciption 根据单据id删除
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/2 8:46
    * @Return int
    **/
    int deleteStroAdjustDetailDTO(StroAdjustDTO stroAdjustDTO);

    /**
    * @Method updateStroAdjustDetailDTO
    * @Desrciption 批量更新
    * @param stroAdjustDetailDTOs
    * @Author liuqi1
    * @Date   2020/8/2 8:46
    * @Return int
    **/
    int updateStroAdjustDetailDTO(@Param("list") List<StroAdjustDetailDTO> stroAdjustDetailDTOs);

    /**
    * @Method queryStroAdjustDetailDTOs
    * @Desrciption 根据调价单id集合获得调价明细集合
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/4 8:43
    * @Return java.util.List<cn.hsa.module.stro.adjust.dto.StroAdjustDetailDTO>
    **/
    List<StroAdjustDetailDTO> queryStroAdjustDetailDTOs(StroAdjustDTO stroAdjustDTO);

    /**
    * @Method queryAdjustStroStockDetailDTOs
    * @Desrciption 获得库存明细数据
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/9/28 15:42
    * @Return java.util.List<cn.hsa.module.stro.stock.dto.StroStockDetailDTO>
    **/
    List<StroStockDetailDTO> queryAdjustStroStockDetailDTOs(StroAdjustDTO stroAdjustDTO);

    /**
    * @Method adjustUpdateStock
    * @Desrciption  调价更新库存零售总金额、拆零总金额
    * @param stroAdjustDetailDTOs
    * @Author liuqi1
    * @Date   2020/8/3 19:21
    * @Return int
    **/
    int adjustUpdateStock(@Param("list") List<StroAdjustDetailDTO> stroAdjustDetailDTOs);

    /**
     * @Method adjustUpdateStockDetail
     * @Desrciption  调价更新库存明细零售单价、零售总金额、拆零单价
     * @param stroAdjustDetailDTOs
     * @Author liuqi1
     * @Date   2020/8/3 19:21
     * @Return int
     **/
    int adjustUpdateStockDetail(@Param("list") List<StroAdjustDetailDTO> stroAdjustDetailDTOs);

    /**
    * @Method adjustUpdateDrug
    * @Desrciption 调价更新药品单价、拆零单价
    * @param stroAdjustDetailDTOs
    * @Author liuqi1
    * @Date   2020/8/3 19:22
    * @Return int
    **/
    int adjustUpdateDrug(@Param("list") List<StroAdjustDetailDTO> stroAdjustDetailDTOs);

    /**
    * @Menthod adjustUpdateMaterial
    * @Desrciption 调价更新材料单价、拆零单价
    *
    * @Param
    * [stroAdjustDetailDTOs]
    *
    * @Author jiahong.yang
    * @Date   2021/3/4 15:29
    * @Return int
    **/
    int adjustUpdateMaterial(@Param("list") List<StroAdjustDetailDTO> stroAdjustDetailDTOs);

    /**
    * @Method adjustUpdateItem
    * @Desrciption 调价更新项目单价
    * @param stroAdjustDetailDTOs
    * @Author liuqi1
    * @Date   2020/8/3 19:23
    * @Return int
    **/
    int adjustUpdateItem(@Param("list") List<StroAdjustDetailDTO> stroAdjustDetailDTOs);

    /**
    * @Menthod queryStockSumNum
    * @Desrciption 查询库存中项目汇总
    *
    * @Param
    * [stroAdjustDetailDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/18 14:07
    * @Return java.util.List<cn.hsa.module.stro.stock.dto.StroStockDTO>
    **/
    List<StroStockDTO> queryStockSumNum(StroAdjustDetailDTO stroAdjustDetailDTO);

    /**
    * @Menthod updateAdjustDetailNum
    * @Desrciption 更新调价明细表中的总数量
    *
    * @Param
    * [stroAdjustDetailDTOs]
    *
    * @Author jiahong.yang
    * @Date   2021/5/18 14:48
    * @Return int
    **/
    int updateAdjustDetailNum(@Param("list") List<StroAdjustDetailDTO> stroAdjustDetailDTOs);

}
