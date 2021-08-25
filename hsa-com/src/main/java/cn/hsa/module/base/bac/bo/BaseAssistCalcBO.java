package cn.hsa.module.base.bac.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.bac.dto.BaseAssistCalcDTO;
import cn.hsa.module.base.bac.dto.BaseAssistCalcDetailDTO;

import java.util.List;

/**
 * @Package_ame: cn.hsa.module.base.syncassist.bo
 * @Class_name: BaseFinanceClassifyBO
 * @Description: 辅助计费业务bo
 * @Author: ljh
 * @Email:
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BaseAssistCalcBO {

    /**
     * @Method queryById()
     * @Description 根据主键ID查询=
     *
     * @Param
     * 1、id：=ID
     *
     * @Author ljh
     * @Date 2020/7/1 20:53
     * @Return SyncassistDTO
     **/
    BaseAssistCalcDTO queryById(String id, String hospCode);




    /**
     * @Method queryAll()
     * @Description 查询
     *
     * @Param
     * 1、SyncassistDTO
     *
     * @Author ljh
     * @Date 2020/7/1 20:53
     * @Return SyncassistDTO
     **/
    List<BaseAssistCalcDTO> queryAll(BaseAssistCalcDTO baseAssistCalcDTO);

    /**
     * @Method queryAll()
     * @Description  新增
     *
     * @Param
     * 1、SyncassistDTO
     *
     * @Author ljh
     * @Date 2020/7/1 20:53
     * @Return SyncassistDTO
     **/
    int insert(BaseAssistCalcDTO baseAssistCalcDTO);

    /**
     * @Method queryAll()
     * @Description  更新
     *
     * @Param
     * 1、SyncassistDTO
     *
     * @Author ljh
     * @Date 2020/7/1 20:53
     * @Return SyncassistDTO
     **/
    int update(BaseAssistCalcDTO baseAssistCalcDTO);

    /**
     * @Method queryAll()
     * @Description  删除
     *
     * @Param
     * 1、SyncassistDTO
     *
     * @Author ljh
     * @Date 2020/7/1 20:53
     * @Return SyncassistDTO
     **/
    int deleteById(String id, String hospCode);



    /**
     * @Method queryPage()
     * @Description 分页
     *
     * @Param
     * 1、baseFinanceClassifyDTO：辅助计费分类数据参数对象
     *
     * @Author LJH
     * @Date 2020/7/1 20:55
     * @Return PageDTO
     **/
    PageDTO queryPage(BaseAssistCalcDTO baseAssistCalcDTO);



    /**
     * @Method queryPage()
     * @Description 分页
     *
     * @Param
     * SyncassistDetailDTO
     *
     * @Author ljh
     * @Date 2020/7/1 20:55
     * @Return PageDTO
     **/
    PageDTO detailqueryPage(BaseAssistCalcDetailDTO baseAssistCalcDetailDTO);

    /**
     * @Package_name: cn.hsa.module.base.bac.bo
     * @Class_name:: BaseAssistCalcBO
     * @Description: 更新状态
     * @Author: ljh
     * @Date: 2020/8/26 19:01
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    int updateStatus(BaseAssistCalcDTO baseAssistCalcDTO);

    /**
     * @Method: queryAssists
     * @Description: 查询辅助计费
     * @Param: [baseAssistCalcDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/21 14:14
     * @Return: java.util.List<cn.hsa.module.base.bac.dto.BaseAssistCalcDTO>
     **/
    List<BaseAssistCalcDTO> queryAssists(BaseAssistCalcDTO baseAssistCalcDTO);

    /**
     * @Method: queryAssistDetails
     * @Description: 根据编码获取辅助计费明细
     * @Param: [baseAssistCalcDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/21 15:31
     * @Return: java.util.List<cn.hsa.module.base.bac.dto.BaseAssistCalcDetailDTO>
     **/
    List<BaseAssistCalcDetailDTO> queryAssistDetails(BaseAssistCalcDTO baseAssistCalcDTO);
}
