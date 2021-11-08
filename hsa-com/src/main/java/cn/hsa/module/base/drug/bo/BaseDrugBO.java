package cn.hsa.module.base.drug.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.drug.bo
 * @Class_name:: BaseItemBO
 * @Description: 药品管理逻辑实现层
 * @Author: liaojunjie
 * @Date: 2020/7/16 14:23
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BaseDrugBO {

    /**
     * @Method getById
     * @Desrciption 通过id获取
     * @Param
     * [baseDrugDTO]
     * @Author liaojunjie
     * @Date   2020/7/16 14:24
     * @Return cn.hsa.module.base.drug.dto.baseDrug
     **/
    BaseDrugDTO getById(BaseDrugDTO baseDrugDTO);

    /**
     * @Method: getByCode
     * @Description:
     * @Param: [baseDrugDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/9 15:59
     * @Return: cn.hsa.module.base.drug.dto.BaseDrugDTO
     **/
    BaseDrugDTO getByCode(BaseDrugDTO baseDrugDTO);

    /**
     * @Method queryPage
     * @Desrciption 分页查询(默认状态为有效)
     * @Param
     * [baseDrugDTO]
     * @Author liaojunjie
     * @Date   2020/7/16 14:26
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryPage(BaseDrugDTO baseDrugDTO);

    /**
     * @Method queryAll
     * @Desrciption 查询某医院下的所有药品
     * @Param
     * [baseDrugDTO]
     * @Author liaojunjie
     * @Date   2020/7/16 11:10
     * @Return java.util.List<cn.hsa.module.base.drug.dto.baseDrug>
     **/
    List<BaseDrugDTO> queryAll(BaseDrugDTO baseDrugDTO);

    /**
     * @Method save()
     * @Description 插入/修改单条药品信息
     *
     * @Param
     * [baseDrugDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/8 8:57
     * @Return Boolean
     **/
    Boolean save(BaseDrugDTO baseDrugDTO);

    /**
     * @Method updateStatus
     * @Desrciption 修改有效标识状态
     * @Param
     * [baseDrugDTO]
     * @Author liaojunjie
     * @Date   2020/7/16 14:27
     * @Return java.lang.Boolean
     **/
    Boolean updateStatus(BaseDrugDTO baseDrugDTO);

    /**
     * @Method updateAllById
     * @Desrciption 通过ID数组修改所有药品的信息
     * @Param
     * [baseDrugDTO]
     * @Author liaojunjie
     * @Date   2020/8/14 10:20
     * @Return java.lang.Boolean
     **/
    Boolean updateAllById(List<BaseDrugDTO> baseDrugDTOS);

    /**
    * @Method queryStockDrugInfoOfDept
    * @Desrciption 查询某库位的项目(药品或材料)信息
    * @param baseDeptDTO
    * @Author liuqi1
    * @Date   2020/8/12 11:56
    * @Return java.util.List<cn.hsa.module.base.drug.dto.BaseDrugDTO>
    **/
    PageDTO queryStockItemInfoPage(BaseDeptDTO baseDeptDTO);

    Boolean insertUpload(Map map);


    /**
     * @param map
     * @Method insertInsureDrugMatch
     * @Desrciption 医保统一支付平台： 同步药品数据到医保匹配表
     * @Param
     * @Author fuhui
     * @Date 2021/3/20 11:05
     * @Return
     */
    Boolean insertInsureDrugMatch(Map<String, Object> map);
    /**
     * @Method updateBaseDrugS()
     * @Description 修改单条药品信息 (修改国家编码)
     *
     * @Param
     * [baseDrugDTO]
     *
     * @Author pengbo
     * @Date 2021/3/24 18:57
     * @Return WrapperResponse<Boolean>
     **/
    Boolean updateBaseDrugS(BaseDrugDTO baseDrugDTO);

    PageDTO queryUnifiedPage(BaseDrugDTO baseDrugDTO);
    /**
     * @Meth: queryEnableCancel
     * @Description: 查看是否可以作废药品
     *   1.判断费用表是否有未发药品。
     *   2.长期医嘱是否开了该药品.如果有,不允许作废.
     * @Param: [baseDrugDTO]
     * @return: java.lang.Boolean
     * @Author: zhangguorui
     * @Date: 2021/9/27
     */
    Boolean queryEnableCancel(BaseDrugDTO baseDrugDTO);
}
