package cn.hsa.module.stro.incdec.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.stro.incdec.dto.StroIncdecDTO;
import cn.hsa.module.stro.incdec.dto.StroIncdecDetailDTO;

import java.util.List;

/**
 *@Package_name: cn.hsa.module.stro.incdec.bo
 *@Class_name: StroIncdecBO
 *@Describe: 药库报损
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/11 9:25
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface StroIncdecBO {
    /**
     * @Method queryStroIncdecDaoPage
     * @Desrciption  分页查询
     * @param stroIncdecDTO
     * @Author liuqi1
     * @Date   2020/8/11 9:25
     * @Return java.util.List<cn.hsa.module.stro.adjust.dto.StroIncdecDTO>
     **/
    PageDTO queryStroIncdecDTOPage(StroIncdecDTO stroIncdecDTO);

    /**
     * @Method queryStroIncdecDTOById
     * @Desrciption 单个查询调价单
     * @param stroIncdecDTO
     * @Author liuqi1
     * @Date   2020/8/11 9:25
     * @Return cn.hsa.module.stro.adjust.dto.StroIncdecDTO
     **/
    StroIncdecDTO queryStroIncdecDTOById(StroIncdecDTO stroIncdecDTO);

    /**
     * @Method queryStroIncdecDetailDtoPage
     * @Desrciption 获得报损明细
     * @param stroIncdecDTO
     * @Author liuqi1
     * @Date   2020/8/11 9:25
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryStroIncdecDetailDtoPage(StroIncdecDTO stroIncdecDTO);

    /**
    * @Method queryStroIncdecDetailDtos
    * @Desrciption 根据条件查询出报损明细
    * @param stroIncdecDTO
    * @Author liuqi1
    * @Date   2020/9/8 13:40
    * @Return java.util.List<cn.hsa.module.stro.incdec.dto.StroIncdecDetailDTO>
    **/
    List<StroIncdecDetailDTO> queryStroIncdecDetailDtos(StroIncdecDTO stroIncdecDTO);
    
    /**
     * @Method insertStroIncdecDao
     * @Desrciption 新增或更新报损信息
     * @param stroIncdecDTO
     * @Author liuqi1
     * @Date   2020/8/11 9:25
     * @Return int
     **/
    boolean insertOrUpdateStroIncdecDTO(StroIncdecDTO stroIncdecDTO);

    /**
     * @Method updateStroIncdecDao
     * @Desrciption 批量审核或作废报损
     * @param stroIncdecDTO
     * @Author liuqi1
     * @Date   2020/8/11 9:25
     * @Return int
     **/
    boolean updateOrCancelStroIncdecDTO(StroIncdecDTO stroIncdecDTO);

}
