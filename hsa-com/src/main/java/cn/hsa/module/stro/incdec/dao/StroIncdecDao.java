package cn.hsa.module.stro.incdec.dao;

import cn.hsa.module.stro.incdec.dto.StroIncdecDTO;

import java.util.List;



/**
 *@Package_name: cn.hsa.module.stro.incdec.dao
 *@Class_name: StroIncdecDetailDao
 *@Describe: 药品损益
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/11 08:53:31
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface StroIncdecDao {


    /**
     * @Method getStroIncdecDTOById
     * @Desrciption 单个查询
     * @param stroIncdecDTO
     * @Author liuqi1
     * @Date   2020-08-11 08:53:31
     * @Return cn.hsa.module.stro.adjust.entity.StroAdjustDO
     **/
    StroIncdecDTO getStroIncdecDTOById(StroIncdecDTO stroIncdecDTO);

    /**
     * @Method queryStroIncdecDTOPage
     * @Desrciption  分页查询
     * @param stroIncdecDTO
     * @Author liuqi1
     * @Date   2020-08-11 08:53:31
     * @Return java.util.List<cn.hsa.module.stro.adjust.dto.StroIncdecDTO>
     **/
    List<StroIncdecDTO> queryStroIncdecDTOPage(StroIncdecDTO stroIncdecDTO);

    /**
    * @Method queryStroIncdecDTOs
    * @Desrciption 根据条件查询
    * @param stroIncdecDTO
    * @Author liuqi1
    * @Date   2020/9/8 11:20
    * @Return java.util.List<cn.hsa.module.stro.incdec.dto.StroIncdecDTO>
    **/
    List<StroIncdecDTO> queryStroIncdecDTOs(StroIncdecDTO stroIncdecDTO);

    /**
     * @Method insertStroIncdecDTO
     * @Desrciption 单个新增
     * @param stroIncdecDTO
     * @Author liuqi1
     * @Date   2020-08-11 08:53:31
     * @Return int
     **/
    int insertStroIncdecDTO(StroIncdecDTO stroIncdecDTO);

    /**
     * @Method updateStroIncdecDTO
     * @Desrciption 单个修改
     * @param stroIncdecDTO
     * @Author liuqi1
     * @Date   2020-08-11 08:53:31
     * @Return int
     **/
    int updateStroIncdecDTO(StroIncdecDTO stroIncdecDTO);

    /**
     * @Method updateStroIncdecDTO
     * @Desrciption 批量修改(通过id集合修改审核状态)
     * @param stroIncdecDTO
     * @Author liuqi1
     * @Date   2020-08-11 08:53:31
     * @Return int
     **/
    int updateStroIncdecDTOByIds(StroIncdecDTO stroIncdecDTO);


}