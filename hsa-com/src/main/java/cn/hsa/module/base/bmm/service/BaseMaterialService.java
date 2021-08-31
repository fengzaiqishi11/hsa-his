package cn.hsa.module.base.bmm.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.bmm.service
 * @Class_name: BaseMaterialManagementService
 * @Describe: 材料信息Service接口层（提供给dubbo调用）
 * @Author: powersi
 * @Date: 2020/7/7 16:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-base")
public interface BaseMaterialService {

    /**
    * @Menthod getById
    * @Desrciption   根据主键ID查询材料分类信息
     * @param map id 材料信息表主键ID
    * @Author xingyu.xie
    * @Date   2020/7/7 16:17
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
    **/
    @PostMapping("/service/base/baseMaterial/getById")
    WrapperResponse<BaseMaterialDTO> getById(Map map);

    /**
     * @Method: getByCode
     * @Description: 根据编码获取材料信息
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/9 16:17
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    @PostMapping("/service/base/baseMaterial/getByCode")
    WrapperResponse<BaseMaterialDTO> getByCode(Map map);

    /**
     * @Menthod queryAll
     * @Desrciption   查询所有材料信息
     * @param map 医院编码
     * @Author xingyu.xie
     * @Date   2020/7/7 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     *
     * @return*/
    @PostMapping("/service/base/baseMaterial/queryAll")
    WrapperResponse<List<BaseMaterialDTO>> queryAll(Map map);

     /**
     * @Method queryBaseMaterialDTOs
     * @Desrciption 查询出符合条件的材料信息
     * @param map
     * @Author liuqi1
     * @Date   2020/9/3 15:13
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>>
     **/
    @PostMapping("/service/base/baseMaterial/queryBaseMaterialDTOs")
    WrapperResponse<List<BaseMaterialDTO>> queryBaseMaterialDTOs(Map<String,Object> map);

    /**
     * @Menthod queryPage
     * @Desrciption   分页查询材料信息
     * @param map 材料信息数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/7 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/service/base/baseMaterial/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Menthod save
     * @Desrciption  新增或修改材料分类
     * @param map 材料分类数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/base/baseMaterial/save")
    WrapperResponse<Boolean> save(Map map);

    /**
     * @Menthod updateList
     * @Desrciption 修改多条材料
     * @param map 材料分类数据参数对象和医院编码
     * @Author xingyu.xie
     * @Date   2020/8/24 15:32
     * @Return boolean
     **/
    @PostMapping("/service/base/baseMaterial/updateList")
    WrapperResponse<Boolean> updateList(Map map);

    /**
    * @Menthod updateStatus
    * @Desrciption   根据主键ID删除材料信息和
     * @param map  材料信息表主键
    * @Author xingyu.xie
    * @Date   2020/7/7 16:18
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/service/base/baseMaterial/updateStatus")
    WrapperResponse<Boolean> updateStatus(Map map);

    /**
     * @Menthod upLoad
     * @Desrciption   数据导入
     * @param map  材料信息表主键
     * @Author xingyu.xie
     * @Date   2020/7/7 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/base/baseMaterial/upLoad")
    WrapperResponse<Boolean> insertUpLoad(Map map);

    /**
     * @Method insertInsureDrugMatch
     * @Desrciption 医保统一支付平台： 同步材料数据到医保匹配表
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/20 11:05
     * @Return
     **/
    @PostMapping("/service/base/insertInsureMaterialMatch/upLoad")
    WrapperResponse<Boolean> insertInsureMaterialMatch(Map<String, Object> map);

    /**
     * @Menthod updateNationCodeById
     * @Desrciption  批量修改材料信息
     * @param map 材料信息数据传输对象List
     * @Author pengbo
     * @Date   2021/3/25 16:48
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/base/baseMaterial/updateNationCodeById")
    WrapperResponse<Boolean> updateNationCodeById(Map map);

    WrapperResponse<PageDTO> queryUnifiedPage(Map<String, Object> map);
}
