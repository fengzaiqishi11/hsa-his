package cn.hsa.module.oper.operInforecord.dao;

import cn.hsa.module.oper.operInforecord.dto.OperAccountTempDTO;

import java.util.List;


/**
 *@Package_name: cn.hsa.module.inpt.operInfoRecord.dao
 *@Class_name: OperAccountTempDao
 *@Describe: 手术补记账模板(OperAccountTempDTO)表数据库访问层
 *@Author: liuqi1
 *@Date: 2020-12-04 17:13:33
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OperAccountTempDAO {

    /**
    * @Method queryOperAccountTempDTOById
    * @Desrciption 单个查询
    * @param operAccountTempDTO
    * @Author liuqi1
    * @Date   2020/12/5 9:47
    * @Return cn.hsa.module.oper.operInforecord.dto.OperAccountTempDTO
    **/
    OperAccountTempDTO queryOperAccountTempDTOById(OperAccountTempDTO operAccountTempDTO);


    /**
    * @Method queryOperAccountTempDTOPage
    * @Desrciption 分页查询
    * @param operAccountTempDTO
    * @Author liuqi1
    * @Date   2020/12/5 9:48
    * @Return java.util.List<cn.hsa.module.oper.operInforecord.dto.OperAccountTempDTO>
    **/
    List<OperAccountTempDTO> queryOperAccountTempDTOPage(OperAccountTempDTO operAccountTempDTO);

    /**
    * @Method insertOperAccountTempDTO
    * @Desrciption 单个新增
    * @param operAccountTempDTO
    * @Author liuqi1
    * @Date   2020/12/5 9:49
    * @Return int
    **/
    int insertOperAccountTempDTO(OperAccountTempDTO operAccountTempDTO);

    /**
    * @Method updateOperAccountTempDTO
    * @Desrciption 修改数据
    * @param operAccountTempDTO
    * @Author liuqi1
    * @Date   2020/12/5 9:49
    * @Return int
    **/
    int updateOperAccountTempDTO(OperAccountTempDTO operAccountTempDTO);

    /**
    * @Method deleteOperAccountTempDTOById
    * @Desrciption 删除数据
    * @param operAccountTempDTO
    * @Author liuqi1
    * @Date   2020/12/5 9:50
    * @Return int
    **/
    int deleteOperAccountTempDTOById(OperAccountTempDTO operAccountTempDTO);

    /**
     * @Method deleteOperAccountTempDTOById
     * @Desrciption 删除数据
     * @param operAccountTempDTO
     * @Author liuqi1
     * @Date   2020/12/5 9:50
     * @Return int
     **/
    int deleteOperAccountTempDetailByTempId(OperAccountTempDTO operAccountTempDTO);

}