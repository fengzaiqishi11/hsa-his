package cn.hsa.module.stro.adjust.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.stro.adjust.dto.StroAdjustDTO;
import cn.hsa.module.stro.adjust.dto.StroAdjustDetailDTO;

import java.util.List;

/**
 *@Package_name: cn.hsa.module.stro.adjust.bo
 *@Class_name: StroAdjustBO 药品调价
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/1 18:21
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface StroAdjustBO {

    /**
     * @Method queryStroAdjustDaoPage
     * @Desrciption  分页查询
     * @param stroAdjustDTO
     * @Author liuqi1
     * @Date   2020/8/1 18:56
     * @Return java.util.List<cn.hsa.module.stro.adjust.dto.StroAdjustDTO>
     **/
    PageDTO queryStroAdjustDtoPage(StroAdjustDTO stroAdjustDTO);

    /**
    * @Menthod queryStroAdjustDtoAll
    * @Desrciption 询所有调价单 根据id
    *
    * @Param
    * [stroAdjustDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/18 15:47
    * @Return java.util.List<cn.hsa.module.stro.adjust.dto.StroAdjustDTO>
    **/
    List<StroAdjustDTO> queryStroAdjustDtoAll(StroAdjustDTO stroAdjustDTO);

    /**
    * @Method queryStroAdjustDtoById
    * @Desrciption 单个查询调价单
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/5 11:07
    * @Return cn.hsa.module.stro.adjust.dto.StroAdjustDTO
    **/
    StroAdjustDTO queryStroAdjustDtoById(StroAdjustDTO stroAdjustDTO);

    /**
    * @Method queryStroAdjustDetailDtoPage
    * @Desrciption 获得调价明细
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/4 10:13
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryStroAdjustDetailDtoPage(StroAdjustDTO stroAdjustDTO);

    /**
    * @Method queryStroAdjustDetailDtos
    * @Desrciption 根据条件查询出调价明细
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/9/8 13:53
    * @Return java.util.List<cn.hsa.module.stro.adjust.dto.StroAdjustDetailDTO>
    **/
    List<StroAdjustDetailDTO> queryStroAdjustDetailDtos(StroAdjustDTO stroAdjustDTO);

    /**
     * @Method insertStroAdjustDao
     * @Desrciption 新增或更新调价信息
     * @param stroAdjustDTO
     * @Author liuqi1
     * @Date   2020/8/1 18:56
     * @Return int
     **/
    boolean insertOrUpdateStroAdjustDto(StroAdjustDTO stroAdjustDTO);

    /**
     * @Method updateStroAdjustDao
     * @Desrciption 批量审核或作废调价
     * @param stroAdjustDTO
     * @Author liuqi1
     * @Date   2020/8/1 18:57
     * @Return int
     **/
    boolean updateOrCancelStroAdjustDto(StroAdjustDTO stroAdjustDTO);

}
