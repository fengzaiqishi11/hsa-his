package cn.hsa.module.stro.adjust.dao;

import cn.hsa.module.stro.adjust.dto.StroAdjustDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *@Package_name: cn.hsa.module.stro.adjust.dao
 *@Class_name: StroAdjustDao
 *@Describe: 药品调价
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020-07-31 14:56:01
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface StroAdjustDao {

    /**
    * @Method getStroAdjustDaoById
    * @Desrciption 单个查询
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/1 18:56
    * @Return cn.hsa.module.stro.adjust.entity.StroAdjustDO
    **/
    StroAdjustDTO getStroAdjustDtoById(StroAdjustDTO stroAdjustDTO);

    /**
    * @Method queryStroAdjustDaoPage
    * @Desrciption  分页查询
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/1 18:56
    * @Return java.util.List<cn.hsa.module.stro.adjust.dto.StroAdjustDTO>
    **/
    List<StroAdjustDTO> queryStroAdjustDtoPage(StroAdjustDTO stroAdjustDTO);

    /**
    * @Method queryStroAdjustDtos
    * @Desrciption 根据条件查询
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/9/8 10:35
    * @Return java.util.List<cn.hsa.module.stro.adjust.dto.StroAdjustDTO>
    **/
    List<StroAdjustDTO> queryStroAdjustDtos(StroAdjustDTO stroAdjustDTO);

    /**
    * @Method insertStroAdjustDao
    * @Desrciption 单个新增
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/1 18:56
    * @Return int
    **/
    int insertStroAdjustDto(StroAdjustDTO stroAdjustDTO);

    /**
    * @Method updateStroAdjustDao
    * @Desrciption 调价修改
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/1 18:57
    * @Return int
    **/
    int updateStroAdjustDto(StroAdjustDTO stroAdjustDTO);

    /**
    * @Method updateStroAdjustDto
    * @Desrciption 批量修改(通过id集合修改审核状态)
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/4 9:06
    * @Return int
    **/
    int updateStroAdjustDtoByIds(StroAdjustDTO stroAdjustDTO);

    /**
    * @Menthod updateAdjustPriceById
    * @Desrciption 回写调价主表金额信息
    *
    * @Param
    * [stroAdjustDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/18 14:42
    * @Return int
    **/
    int updateAdjustPriceById(@Param("list") List<StroAdjustDTO> stroAdjustDTOS);

}
