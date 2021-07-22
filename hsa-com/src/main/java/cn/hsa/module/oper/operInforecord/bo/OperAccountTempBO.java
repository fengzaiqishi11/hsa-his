package cn.hsa.module.oper.operInforecord.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.oper.operInforecord.dto.OperAccountTempDTO;
import cn.hsa.module.oper.operInforecord.dto.OperAccountTempDetailDTO;

import java.util.List;

/**
 *@Package_name: cn.hsa.module.oper.operInforecord.bo
 *@Class_name: OperAccountTempBo
 *@Describe: 手术补记账模板业务逻辑层
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/12/5 10:29
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OperAccountTempBO {

    /**
    * @Method queryOperAccountTempDTOPage
    * @Desrciption 手术模板分页查询
    * @param operAccountTempDTO
    * @Author liuqi1
    * @Date   2020/12/5 10:35
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryOperAccountTempDTOPage(OperAccountTempDTO operAccountTempDTO);

    /**
    * @Method queryOperAccountTempDetailDTOPage
    * @Desrciption 手术模板明细分页查询
    * @param operAccountTempDetailDTO
    * @Author liuqi1
    * @Date   2020/12/5 10:37
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryOperAccountTempDetailDTOPage(OperAccountTempDetailDTO operAccountTempDetailDTO);

    /**
     * @Method queryOperAccountTempDetailDTOPage
     * @Desrciption 手术模板明细分页查询
     * @param operAccountTempDetailDTO
     * @Author liuqi1
     * @Date   2020/12/5 10:37
     * @Return cn.hsa.base.PageDTO
     **/
    List<OperAccountTempDetailDTO> queryOperAccountTempDetailDTO(OperAccountTempDetailDTO operAccountTempDetailDTO);


    /**
    * @Method insertOperAccountTempDTO
    * @Desrciption 手术模板、明细新增
    * @param operAccountTempDTO
    * @Author liuqi1
    * @Date   2020/12/5 10:45
    * @Return java.lang.Boolean
    **/
    Boolean insertOperAccountTempDTO(OperAccountTempDTO operAccountTempDTO);

    /**
    * @Menthod deleteOperAccountTempDetailByTempId
    * @Desrciption  根据模版id删除模版和明细
     * @param operAccountTempDTO
    * @Author xingyu.xie
    * @Date   2021/1/22 11:17 
    * @Return java.lang.Boolean
    **/
    Boolean deleteOperAccountTempDTOById(OperAccountTempDTO operAccountTempDTO);


    /**
    * @Menthod deleteOperAccountTempDetailById
    * @Desrciption  保存模版内容和模版明细内容
     * @param operAccountTempDTO
    * @Author xingyu.xie
    * @Date   2021/1/22 11:18 
    * @Return java.lang.Boolean
    **/
    Boolean saveOperAccountTemp(OperAccountTempDTO operAccountTempDTO);


}
