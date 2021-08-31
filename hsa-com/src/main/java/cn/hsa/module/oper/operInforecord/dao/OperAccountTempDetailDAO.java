package cn.hsa.module.oper.operInforecord.dao;

import cn.hsa.module.oper.operInforecord.dto.OperAccountTempDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 *@Package_name: cn.hsa.module.inpt.operInfoRecord.dao
 *@Class_name: OperAccountTempDetailDao
 *@Describe: 手术补记账模板明细(OperAccountTempDetailDTO)表数据库访问层
 *@Author: liuqi1
 *@Date: 2020-12-04 17:13:33
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OperAccountTempDetailDAO {


    /**
    * @Method queryOperAccountTempDetailDTOById
    * @Desrciption 单个查询
    * @param operAccountTempDetailDTO
    * @Author liuqi1
    * @Date   2020/12/5 9:50
    * @Return cn.hsa.module.oper.operInforecord.dto.OperAccountTempDetailDTO
    **/
    OperAccountTempDetailDTO queryOperAccountTempDetailDTOById(OperAccountTempDetailDTO operAccountTempDetailDTO);

    /**
    * @Method queryOperAccountTempDetailDTOPage
    * @Desrciption 分页查询
    * @param operAccountTempDetailDTO
    * @Author liuqi1
    * @Date   2020/12/5 9:51
    * @Return java.util.List<cn.hsa.module.oper.operInforecord.dto.OperAccountTempDetailDTO>
    **/
    List<OperAccountTempDetailDTO> queryOperAccountTempDetailDTOPage(OperAccountTempDetailDTO operAccountTempDetailDTO);


    /**
    * @Method insertOperAccountTempDetailDTO
    * @Desrciption 单个新增
    * @param operAccountTempDetailDTO
    * @Author liuqi1
    * @Date   2020/12/5 9:51
    * @Return int
    **/
    int insertOperAccountTempDetailDTO(OperAccountTempDetailDTO operAccountTempDetailDTO);

    /**
    * @Method insertOperAccountTempDetailDTOBatch
    * @Desrciption 批量新增
    * @param list
    * @Author liuqi1
    * @Date   2020/12/5 10:14
    * @Return int
    **/
    int insertOperAccountTempDetailDTOBatch(@Param("list") List<OperAccountTempDetailDTO> list);

    /**
    * @Method updateOperAccountTempDetail
    * @Desrciption 修改数据
    * @param operAccountTempDetailDTO
    * @Author liuqi1
    * @Date   2020/12/5 9:51
    * @Return int
    **/
    int updateOperAccountTempDetail(OperAccountTempDetailDTO operAccountTempDetailDTO);

    /**
    * @Method deleteOperAccountTempDetailById
    * @Desrciption 删除数据
    * @param operAccountTempDetailDTO
    * @Author liuqi1
    * @Date   2020/12/5 9:52
    * @Return int
    **/
    int deleteOperAccountTempDetailById(OperAccountTempDetailDTO operAccountTempDetailDTO);

}