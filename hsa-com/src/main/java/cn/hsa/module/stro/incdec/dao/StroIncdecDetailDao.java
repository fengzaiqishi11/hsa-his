package cn.hsa.module.stro.incdec.dao;

import cn.hsa.module.stro.incdec.dto.StroIncdecDTO;
import cn.hsa.module.stro.incdec.dto.StroIncdecDetailDTO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *@Package_name: cn.hsa.module.stro.incdec.dao
 *@Class_name: StroIncdecDetailDao
 *@Describe: 药品损益明细
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/11 9:21
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface StroIncdecDetailDao {

    /**
     * @Method queryStroIncdecDetailDTOById
     * @Desrciption 单个查询
     * @param stroIncdecDTO
     * @Author liuqi1
     * @Date   2020-08-11 09:17:28
     * @Return cn.hsa.module.stro.incdec.dto.StroIncdecDetailDTO
     **/
    StroIncdecDetailDTO queryStroIncdecDetailDTOById(StroIncdecDTO stroIncdecDTO);

    /**
     * @Method queryStroIncdecDetailDTOPage
     * @Desrciption 根据单据id分页查询
     * @param stroIncdecDTO
     * @Author liuqi1
     * @Date   2020-08-11 09:17:28
     * @Return java.util.List<cn.hsa.module.stro.incdec.dto.StroIncdecDetailDTO>
     **/
    List<StroIncdecDetailDTO> queryStroIncdecDetailDTOPage(StroIncdecDTO stroIncdecDTO);

    /**
     * @Method queryStroIncdecDetailDTOs
     * @Desrciption 根据条件查询
     * @param stroIncdecDTO
     * @Author liuqi1
     * @Date   2020-08-11 09:17:28
     * @Return java.util.List<cn.hsa.module.stro.incdec.dto.StroIncdecDetailDTO>
     **/
    List<StroIncdecDetailDTO> queryStroIncdecDetailDTOs(StroIncdecDTO stroIncdecDTO);

    
    /**
     * @Method insertStroIncdecDetailDTO
     * @Desrciption 批量新增
     * @param stroIncdecDetailDTOs
     * @Author liuqi1
     * @Date   2020-08-11 09:17:28
     * @Return int
     **/
    int insertStroIncdecDetailDTO(@Param("list") List<StroIncdecDetailDTO> stroIncdecDetailDTOs);

    /**
     * @Method deleteStroIncdecDetailDTO
     * @Desrciption 根据单据id删除
     * @param stroIncdecDTO
     * @Author liuqi1
     * @Date   2020-08-11 09:17:28
     * @Return int
     **/
    int deleteStroIncdecDetailDTO(StroIncdecDTO stroIncdecDTO);

    /**
     * @Method updateStroIncdecDetailDTO
     * @Desrciption 批量更新
     * @param stroIncdecDetailDTOs
     * @Author liuqi1
     * @Date   2020-08-11 09:17:28
     * @Return int
     **/
    int updateStroIncdecDetailDTO(@Param("list") List<StroIncdecDetailDTO> stroIncdecDetailDTOs);

    /**
    * @Method queryStroStockDetailDTOs
    * @Desrciption 获得库存明细数据
    * @param stroIncdecDTO
    * @Author liuqi1
    * @Date   2020/8/12 16:24
    * @Return java.util.List<cn.hsa.module.stro.stock.dto.StroStockDetailDTO>
    **/
    List<StroStockDetailDTO> queryStroStockDetailDTOs(StroIncdecDTO stroIncdecDTO);
}